package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Acesso ao receituário. Uma receita pertence a um atendimento, que por sua vez
 * pertence a uma consulta:  consulta -> atendimento -> receituario.
 */
public class ReceituarioDAO {

    /** Retorna o atendimento da consulta, criando um se ainda não existir. */
    private int obterOuCriarAtendimento(Connection conn, int idConsulta) throws SQLException {
        String sel = "SELECT idatendimento FROM atendimento WHERE idconsulta = ? "
                   + "ORDER BY idatendimento DESC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sel)) {
            ps.setInt(1, idConsulta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        String ins = "INSERT INTO atendimento (idconsulta, dataAtendimento) VALUES (?, CURDATE())";
        try (PreparedStatement ps = conn.prepareStatement(ins, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idConsulta);
            ps.executeUpdate();
            try (ResultSet gk = ps.getGeneratedKeys()) {
                if (gk.next()) return gk.getInt(1);
            }
        }
        return -1;
    }

    /** Grava uma receita para a consulta informada (garante o atendimento). */
    public boolean inserir(int idConsulta, String medicamentos) {
        try (Connection conn = BDSConnection.getConexao()) {
            int idAtendimento = obterOuCriarAtendimento(conn, idConsulta);
            if (idAtendimento <= 0) return false;

            String ins = "INSERT INTO receituario (idatendimento, medicamentos, dataemissao) "
                       + "VALUES (?, ?, CURDATE())";
            try (PreparedStatement ps = conn.prepareStatement(ins)) {
                ps.setInt(1, idAtendimento);
                ps.setString(2, medicamentos);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir receituário: " + e.getMessage());
            return false;
        }
    }

    /** Lista as receitas emitidas para um paciente (mais recentes primeiro). */
    public List<Receita> listarPorPaciente(int idPaciente) {
        List<Receita> lista = new ArrayList<>();
        String sql = "SELECT r.dataemissao, r.medicamentos "
                   + "FROM receituario r "
                   + "JOIN atendimento a ON a.idatendimento = r.idatendimento "
                   + "JOIN consulta c ON c.idConsulta = a.idconsulta "
                   + "WHERE c.idPaciente = ? "
                   + "ORDER BY r.dataemissao DESC, r.idReceituario DESC";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receita r = new Receita();
                    r.setDataEmissao(rs.getString("dataemissao"));
                    r.setMedicamentos(rs.getString("medicamentos"));
                    lista.add(r);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar receituário: " + e.getMessage());
        }
        return lista;
    }
}
