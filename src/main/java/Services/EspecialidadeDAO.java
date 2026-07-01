/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadeDAO {

    public static class Especialidade {
        public int id;
        public String nome;
        public Especialidade(int id, String nome) { this.id = id; this.nome = nome; }
        @Override public String toString() { return nome; }
    }

    public List<Especialidade> listar() {
        List<Especialidade> lista = new ArrayList<>();
        String sql = "SELECT idEspecialidade, nome FROM especialidade ORDER BY nome";
        try (Connection c = BDSConnection.getConexao();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Especialidade(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
