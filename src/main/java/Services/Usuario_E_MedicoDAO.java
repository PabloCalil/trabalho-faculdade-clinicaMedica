package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario_E_MedicoDAO {

    public boolean inserir(String nome, String login, String senha, String perfil,
                           String cpf, String telefone, String endereco,
                           String crm, int idEspecialidade) {

        Connection conn = null;

        try {
            conn = BDSConnection.getConexao();
            conn.setAutoCommit(false);

            // 1. Insere na tabela USUARIO
            String sqlUsuario = "INSERT INTO usuario (nome, login, senha, perfil, ativo, cpf, telefone, endereco) "
                              + "VALUES (?, ?, ?, ?, 1, ?, ?, ?)";

            int idGerado = 0;

            try (PreparedStatement stmt = conn.prepareStatement(sqlUsuario,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, nome);
                stmt.setString(2, login);
                stmt.setString(3, senha);
                stmt.setString(4, perfil);
                stmt.setString(5, cpf);
                stmt.setString(6, telefone);
                stmt.setString(7, endereco);
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGerado = rs.getInt(1);
                    }
                }
            }

            // 2. Se for Médico, insere também na tabela MEDICO
            if ("Médico".equals(perfil)) {
                String sqlMedico = "INSERT INTO medico (idUsuario, crm, idEspecialidade) "
                                 + "VALUES (?, ?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement(sqlMedico)) {
                    stmt.setInt(1, idGerado);
                    stmt.setString(2, crm);
                    stmt.setInt(3, idEspecialidade);
                    stmt.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Erro no rollback: " + ex.getMessage());
            }
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            return false;

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public int buscarIdEspecialidade(String nomeEspecialidade) {
        String sql = "SELECT idEspecialidade FROM especialidade WHERE nome = ?";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeEspecialidade);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idEspecialidade");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar especialidade: " + e.getMessage());
        }
        return -1;
    }
}