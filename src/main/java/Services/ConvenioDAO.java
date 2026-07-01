package Services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ConvenioDAO {

    public boolean inserir(String nome, String cnpj, String telefone, String validade) {
        String sql = "INSERT INTO convenio (nome, cnpj, telefone, validade) VALUES (?, ?, ?, ?)";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Remove qualquer caractere não numérico (mantém só dígitos no banco)
            String cnpjLimpo     = cnpj.replaceAll("\\D", "");
            String telefoneLimpo = telefone.replaceAll("\\D", "");

            // Converte dd/MM/yyyy -> java.sql.Date (yyyy-MM-dd)
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            java.util.Date dataUtil = sdf.parse(validade);
            Date dataSql = new Date(dataUtil.getTime());

            stmt.setString(1, nome);
            stmt.setString(2, cnpjLimpo);
            stmt.setString(3, telefoneLimpo);
            stmt.setDate(4, dataSql);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir convênio: " + e.getMessage());
            return false;
        } catch (java.text.ParseException e) {
            System.err.println("Data inválida. Use o formato dd/MM/yyyy: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int idConvenio) {
        String sql = "DELETE FROM convenio WHERE idConvenio = ?";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idConvenio);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir convênio: " + e.getMessage());
            return false;
        }
    }

    public ResultSet listarTodos() {
        String sql = "SELECT idConvenio, nome, cnpj, telefone, validade FROM convenio ORDER BY nome";
        try {
            Connection conn = BDSConnection.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Erro ao listar convênios: " + e.getMessage());
            return null;
        }
    }
}
