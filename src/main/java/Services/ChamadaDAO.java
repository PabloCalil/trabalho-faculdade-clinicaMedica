package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChamadaDAO {

    public boolean chamarPaciente(int idConsulta, int idPaciente) {
        String sql = "INSERT INTO chamada_paciente (idConsulta, idPaciente, status, dataChamada) "
                   + "VALUES (?, ?, 'Aguardando Chamada', NOW())";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idConsulta);
            stmt.setInt(2, idPaciente);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao chamar paciente: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarStatusChamada(int idChamada, String novoStatus) {
        String sql = "UPDATE chamada_paciente SET status = ? WHERE idChamada = ?";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoStatus);
            stmt.setInt(2, idChamada);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar chamada: " + e.getMessage());
            return false;
        }
    }
}