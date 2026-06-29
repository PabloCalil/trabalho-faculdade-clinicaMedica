package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProntuarioDAO {

    public Prontuario buscarPorPaciente(int idPaciente) {
        String sql = "SELECT * FROM prontuario WHERE idPaciente = ? "
                   + "ORDER BY dataCriacao DESC LIMIT 1";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaciente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Prontuario p = new Prontuario();
                    p.setIdProntuario(rs.getInt("idprontuario"));
                    p.setIdPaciente(rs.getInt("idpaciente"));
                    p.setHistorico(rs.getString("historico"));
                    p.setAlergias(rs.getString("alergias"));
                    p.setDataCriacao(rs.getString("dataCriacao"));
                    p.setPressaoArterial(rs.getString("pressaoArterial"));
                    p.setFrequenciaCardiaca(rs.getString("frequenciaCardiaca"));
                    p.setTemperatura(rs.getString("temperatura"));
                    p.setExameFisico(rs.getString("exameFisico"));
                    p.setCid10(rs.getString("cid10"));
                    p.setConduta(rs.getString("conduta"));
                    return p;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar prontuário: " + e.getMessage());
        }
        return null;
    }

    public boolean inserir(Prontuario p) {
        String sql = "INSERT INTO prontuario "
                   + "(idPaciente, historico, alergias, dataCriacao, "
                   + "pressaoArterial, frequenciaCardiaca, temperatura, "
                   + "exameFisico, cid10, conduta) "
                   + "VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?)";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdPaciente());
            stmt.setString(2, p.getHistorico());
            stmt.setString(3, p.getAlergias());
            stmt.setString(4, p.getPressaoArterial());
            stmt.setString(5, p.getFrequenciaCardiaca());
            stmt.setString(6, p.getTemperatura());
            stmt.setString(7, p.getExameFisico());
            stmt.setString(8, p.getCid10());
            stmt.setString(9, p.getConduta());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir prontuário: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Prontuario p) {
        String sql = "UPDATE prontuario SET "
                   + "historico = ?, alergias = ?, "
                   + "pressaoArterial = ?, frequenciaCardiaca = ?, temperatura = ?, "
                   + "exameFisico = ?, cid10 = ?, conduta = ? "
                   + "WHERE idprontuario = ?";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getHistorico());
            stmt.setString(2, p.getAlergias());
            stmt.setString(3, p.getPressaoArterial());
            stmt.setString(4, p.getFrequenciaCardiaca());
            stmt.setString(5, p.getTemperatura());
            stmt.setString(6, p.getExameFisico());
            stmt.setString(7, p.getCid10());
            stmt.setString(8, p.getConduta());
            stmt.setInt(9, p.getIdProntuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar prontuário: " + e.getMessage());
            return false;
        }
    }
}