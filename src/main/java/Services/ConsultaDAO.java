package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    // Busca consultas do dia para um médico específico
    public List<Consulta> listarFilaDoDia(int idUsuario) {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT c.idConsulta, c.idPaciente, c.dataHora, c.status,  "
                   + "p.nome AS nomePaciente, "
                   + "cv.nome AS nomeConvenio "
                   + "FROM consulta c "
                   + "JOIN paciente p ON p.idPaciente = c.idPaciente "
                   + "LEFT JOIN convenio cv ON cv.idConvenio = c.idConvenio "
                   + "WHERE c.idUsuario = ? "
                   + "AND DATE(c.dataHora) = CURDATE() "
                   + "AND c.status != 'Cancelado' "
                   + "ORDER BY c.dataHora";

        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Consulta c = new Consulta();
                    c.setIdConsulta(rs.getInt("idConsulta"));
                    c.setDataHora(rs.getString("dataHora"));
                    c.setStatus(rs.getString("status"));
                    c.setNomePaciente(rs.getString("nomePaciente"));
                    c.setNomeConvenio(rs.getString("nomeConvenio"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar fila: " + e.getMessage());
        }
        return lista;
    }

    public boolean inserir(int idPaciente, int idMedico, Integer idConvenio, String dataHora) {
        String sql = "INSERT INTO consulta (idPaciente, idUsuario, idConvenio, dataHora, status) "
                   + "VALUES (?, ?, ?, ?, 'Agendado')";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaciente);
            stmt.setInt(2, idMedico);
            if (idConvenio != null && idConvenio > 0)
                stmt.setInt(3, idConvenio);
            else
                stmt.setNull(3, java.sql.Types.INTEGER);
            stmt.setString(4, dataHora);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir consulta: " + e.getMessage());
            return false;
        }
    }

    public List<Consulta> listarParaCheckin() {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT c.idConsulta, c.dataHora, c.status, "
                   + "p.nome AS nomePaciente, "
                   + "u.nome AS nomeMedico, "
                   + "cv.nome AS nomeConvenio "
                   + "FROM consulta c "
                   + "JOIN paciente p ON p.idPaciente = c.idPaciente "
                   + "JOIN usuario u ON u.idUsuario = c.idUsuario "
                   + "LEFT JOIN convenio cv ON cv.idConvenio = c.idConvenio "
                   + "WHERE DATE(c.dataHora) = CURDATE() "
                   + "AND c.status IN ('Agendado', 'Confirmado') "
                   + "ORDER BY c.dataHora";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Consulta c = new Consulta();
                c.setIdConsulta(rs.getInt("idConsulta"));
                c.setDataHora(rs.getString("dataHora"));
                c.setStatus(rs.getString("status"));
                c.setNomePaciente(rs.getString("nomePaciente"));
                c.setNomeMedico(rs.getString("nomeMedico"));
                c.setNomeConvenio(rs.getString("nomeConvenio"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar para check-in: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(int idConsulta, int idPaciente, int idMedico, Integer idConvenio, String dataHora) {
        String sql = "UPDATE consulta SET idPaciente=?, idUsuario=?, idConvenio=?, dataHora=? WHERE idConsulta=?";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaciente);
            stmt.setInt(2, idMedico);
            if (idConvenio != null && idConvenio > 0)
                stmt.setInt(3, idConvenio);
            else
                stmt.setNull(3, java.sql.Types.INTEGER);
            stmt.setString(4, dataHora);
            stmt.setInt(5, idConsulta);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar consulta: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarStatus(int idConsulta, String novoStatus) {
        String sql = "UPDATE consulta SET status = ? WHERE idConsulta = ?";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoStatus);
            stmt.setInt(2, idConsulta);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status: " + e.getMessage());
            return false;
        }
    }
}