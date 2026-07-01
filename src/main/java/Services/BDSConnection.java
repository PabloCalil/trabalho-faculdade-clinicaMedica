/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDSConnection {

    private static final String URL    = "jdbc:mysql://100.86.225.41:3306/health_equilibrium";
    private static final String USUARIO = "diego";
    private static final String SENHA   = "@#$TrabalhoADS2026";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}