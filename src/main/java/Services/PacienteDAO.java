package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PacienteDAO {

    public void inserir(Paciente paciente) {

        String dataFormatada;
        try {
            DateTimeFormatter entrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter saida   = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate data = LocalDate.parse(paciente.getDataNascimento(), entrada);
            dataFormatada  = data.format(saida);
        } catch (DateTimeParseException e) {
            System.err.println("Data inválida: " + paciente.getDataNascimento());
            return;
        }

        String sql = "INSERT INTO paciente (nome, cpf, dataNascimento, telefone, idConvenio, numeroCarteirinha) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, dataFormatada);
            stmt.setString(4, paciente.getTelefone());

            if (paciente.getIdConvenio() > 0) {
                stmt.setInt(5, paciente.getIdConvenio());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }

            stmt.setString(6, paciente.getNumeroCarteirinha());
            stmt.executeUpdate();
            System.out.println("Paciente inserido com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir paciente: " + e.getMessage());
        }
    }

    public int buscarIdConvenio(String nomeConvenio) {
        String sql = "SELECT idConvenio FROM convenio WHERE nome = ?";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeConvenio);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("idConvenio");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar convênio: " + e.getMessage());
        }
        return -1;
    }
}