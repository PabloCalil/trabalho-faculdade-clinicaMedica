package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Acesso a tabela `status`. As telas trabalham sempre com o NOME do status;
 * o idStatus e um detalhe interno do banco e nunca e exibido ao usuario.
 */
public class StatusDAO {

    /** Lista os nomes de status na ordem definida no banco (idStatus). */
    public List<String> listarNomes() {
        List<String> nomes = new ArrayList<>();
        String sql = "SELECT nome FROM status ORDER BY idStatus";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                nomes.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar status: " + e.getMessage());
        }
        return nomes;
    }

    /** Retorna o idStatus correspondente ao nome, ou -1 se nao existir. */
    public int buscarIdPorNome(String nome) {
        String sql = "SELECT idStatus FROM status WHERE nome = ?";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("idStatus");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar id do status: " + e.getMessage());
        }
        return -1;
    }
}
