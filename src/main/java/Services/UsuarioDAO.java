package Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // ================================================================
    // INSERIR (cadastro novo)
    // ================================================================
    public boolean inserir(String nome, String login, String senha, String perfil,
                           String cpf, String telefone, String endereco,
                           String crm, int idEspecialidade) {
        Connection conn = null;
        try {
            conn = BDSConnection.getConexao();
            conn.setAutoCommit(false);

            String sqlUsuario = "INSERT INTO usuario (nome, login, senha, perfil, ativo, cpf, telefone, endereco) "
                              + "VALUES (?, ?, ?, ?, 1, ?, ?, ?)";

            int idGerado = 0;
            try (PreparedStatement stmt = conn.prepareStatement(sqlUsuario,
                    Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nome);
                stmt.setString(2, login);
                stmt.setString(3, senha);
                stmt.setString(4, perfil);
                stmt.setString(5, cpf);
                stmt.setString(6, telefone);
                stmt.setString(7, endereco);
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) idGerado = rs.getInt(1);
                }
            }

            if ("Médico".equals(perfil) && idGerado > 0) {
                String sqlMedico = "INSERT INTO medico (idUsuario, crm, idEspecialidade) VALUES (?, ?, ?)";
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
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) { conn.setAutoCommit(true); conn.close(); }
            } catch (SQLException e) {}
        }
    }

    // ================================================================
    // LISTAR COM FILTROS
    // ================================================================
    public List<Usuario> listar(String filtroTexto, String perfil, Integer ativo) {
        List<Usuario> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT idUsuario, nome, cpf, login, perfil, ativo FROM usuario WHERE 1=1 "
        );

        if (filtroTexto != null && !filtroTexto.trim().isEmpty())
            sql.append("AND (nome LIKE ? OR cpf LIKE ? OR login LIKE ?) ");
        if (perfil != null) sql.append("AND perfil = ? ");
        if (ativo != null)  sql.append("AND ativo = ? ");
        sql.append("ORDER BY nome");

        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int i = 1;
            if (filtroTexto != null && !filtroTexto.trim().isEmpty()) {
                String like = "%" + filtroTexto + "%";
                stmt.setString(i++, like);
                stmt.setString(i++, like);
                stmt.setString(i++, like);
            }
            if (perfil != null) stmt.setString(i++, perfil);
            if (ativo != null)  stmt.setInt(i++, ativo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("idUsuario"));
                    u.setNome(rs.getString("nome"));
                    u.setCpf(rs.getString("cpf"));
                    u.setLogin(rs.getString("login"));
                    u.setPerfil(rs.getString("perfil"));
                    u.setAtivo(rs.getBoolean("ativo"));
                    lista.add(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        return lista;
    }

    // ================================================================
    // BUSCAR POR ID (completo, incluindo médico)
    // ================================================================
    public Usuario buscarPorId(int idUsuario) {
        String sql = "SELECT u.*, m.crm, m.idEspecialidade "
                   + "FROM usuario u "
                   + "LEFT JOIN medico m ON m.idUsuario = u.idUsuario "
                   + "WHERE u.idUsuario = ?";

        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("idUsuario"));
                    u.setNome(rs.getString("nome"));
                    u.setLogin(rs.getString("login"));
                    u.setPerfil(rs.getString("perfil"));
                    u.setCpf(rs.getString("cpf"));
                    u.setTelefone(rs.getString("telefone"));
                    u.setEndereco(rs.getString("endereco"));
                    u.setAtivo(rs.getBoolean("ativo"));
                    u.setCrm(rs.getString("crm"));
                    u.setIdEspecialidade(rs.getInt("idEspecialidade"));
                    return u;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    // ================================================================
    // ATUALIZAR
    // ================================================================
    public boolean atualizar(Usuario u) {
        Connection conn = null;
        try {
            conn = BDSConnection.getConexao();
            conn.setAutoCommit(false);

            String sqlUsuario = "UPDATE usuario SET nome=?, login=?, cpf=?, telefone=?, endereco=?, perfil=? "
                              + "WHERE idUsuario=?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlUsuario)) {
                stmt.setString(1, u.getNome());
                stmt.setString(2, u.getLogin());
                stmt.setString(3, u.getCpf());
                stmt.setString(4, u.getTelefone());
                stmt.setString(5, u.getEndereco());
                stmt.setString(6, u.getPerfil());
                stmt.setInt(7, u.getIdUsuario());
                stmt.executeUpdate();
            }

            if ("Médico".equals(u.getPerfil())) {
                // Verifica se já existe na tabela medico
                String check = "SELECT COUNT(*) FROM medico WHERE idUsuario=?";
                boolean existe = false;
                try (PreparedStatement stmt = conn.prepareStatement(check)) {
                    stmt.setInt(1, u.getIdUsuario());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) existe = rs.getInt(1) > 0;
                    }
                }

                if (existe) {
                    String sqlMedico = "UPDATE medico SET crm=?, idEspecialidade=? WHERE idUsuario=?";
                    try (PreparedStatement stmt = conn.prepareStatement(sqlMedico)) {
                        stmt.setString(1, u.getCrm());
                        stmt.setInt(2, u.getIdEspecialidade());
                        stmt.setInt(3, u.getIdUsuario());
                        stmt.executeUpdate();
                    }
                } else {
                    String sqlMedico = "INSERT INTO medico (idUsuario, crm, idEspecialidade) VALUES (?,?,?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sqlMedico)) {
                        stmt.setInt(1, u.getIdUsuario());
                        stmt.setString(2, u.getCrm());
                        stmt.setInt(3, u.getIdEspecialidade());
                        stmt.executeUpdate();
                    }
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) { conn.setAutoCommit(true); conn.close(); }
            } catch (SQLException e) {}
        }
    }

    // ================================================================
    // ATIVAR / INATIVAR
    // ================================================================
    public boolean setAtivo(int idUsuario, boolean ativo) {
        String sql = "UPDATE usuario SET ativo=? WHERE idUsuario=?";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ativo ? 1 : 0);
            stmt.setInt(2, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao alterar status: " + e.getMessage());
            return false;
        }
    }

    // ================================================================
    // ALTERAR SENHA
    // ================================================================
    public boolean alterarSenha(int idUsuario, String novaSenha) {
        String sql = "UPDATE usuario SET senha=? WHERE idUsuario=?";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setInt(2, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao alterar senha: " + e.getMessage());
            return false;
        }
    }

    // ================================================================
    // BUSCAR ID ESPECIALIDADE (usado no cadastro)
    // ================================================================
    public int buscarIdEspecialidade(String nomeEspecialidade) {
        String sql = "SELECT idEspecialidade FROM especialidade WHERE nome = ?";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeEspecialidade);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("idEspecialidade");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar especialidade: " + e.getMessage());
        }
        return -1;
    }
}