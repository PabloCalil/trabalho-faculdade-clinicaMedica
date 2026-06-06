/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PacienteDAO {

    public void inserir(Paciente paciente) {

        // Converte de dd/MM/yyyy para yyyy-MM-dd (formato do MySQL)
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

        String sql = "INSERT INTO paciente (nome, cpf, dataNascimento, telefone) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, dataFormatada);
            stmt.setString(4, paciente.getTelefone());

            stmt.executeUpdate();
            System.out.println("Paciente inserido com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir paciente: " + e.getMessage());
        }
    }
}
