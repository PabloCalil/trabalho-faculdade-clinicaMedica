package com.mycompany.clinicamedica.newpackage.view;

import com.mycompany.clinicamedica.newpackage.view.Admin.TelaAdmin;
import com.mycompany.clinicamedica.newpackage.view.Medico.TelaMedico;
import com.mycompany.clinicamedica.newpackage.view.Secretaria.TelaSecretaria;
import com.mycompany.clinicamedica.newpackage.view.ui.Tema;
import Services.BDSConnection;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TelaLogin extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;

    public TelaLogin() {
        setTitle("Health Equilibrium - Autenticação Corporativa");
        setSize(460, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Color marromEscuro = Tema.MARROM_ESCURO;
        Color corGold      = Tema.GOLD;
        Color campoFundo   = Tema.TOM_MEDIO;

        JPanel p = new JPanel(null);
        p.setBackground(marromEscuro);
        setContentPane(p);

        // --- HEADER ---
        JPanel pnlHeader = new JPanel(null);
        pnlHeader.setBounds(0, 0, 460, 120);
        pnlHeader.setBackground(new Color(43, 19, 4));
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, corGold));
        p.add(pnlHeader);

        JLabel lblLogo = new JLabel("HEALTH EQUILIBRIUM", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblLogo.setForeground(corGold);
        lblLogo.setBounds(0, 30, 460, 34);
        pnlHeader.add(lblLogo);

        JLabel lblSub = new JLabel("CLÍNICA MÉDICA PREMIUM", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSub.setForeground(Color.LIGHT_GRAY);
        lblSub.setBounds(0, 68, 460, 20);
        pnlHeader.add(lblSub);

        // --- CAMPOS ---
        JLabel l1 = new JLabel("Usuário / Operador:");
        l1.setForeground(Color.LIGHT_GRAY);
        l1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l1.setBounds(45, 160, 360, 20);
        p.add(l1);

        txtUser = new JTextField();
        txtUser.setBounds(45, 185, 360, 40);
        txtUser.setBackground(campoFundo);
        txtUser.setForeground(Color.WHITE);
        txtUser.setCaretColor(Color.WHITE);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUser.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(corGold, 1),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        p.add(txtUser);

        JLabel l2 = new JLabel("Chave de Acesso:");
        l2.setForeground(Color.LIGHT_GRAY);
        l2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l2.setBounds(45, 255, 360, 20);
        p.add(l2);

        txtPass = new JPasswordField();
        txtPass.setBounds(45, 280, 360, 40);
        txtPass.setBackground(campoFundo);
        txtPass.setForeground(Color.WHITE);
        txtPass.setCaretColor(Color.WHITE);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(corGold, 1),
            BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        p.add(txtPass);

        // --- BOTÃO ---
        JButton btnEntrar = new JButton("ACESSAR PAINEL");
        btnEntrar.setBounds(45, 370, 360, 48);
        btnEntrar.setBackground(corGold);
        btnEntrar.setForeground(marromEscuro);
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setOpaque(true);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        p.add(btnEntrar);

        JLabel lblSuporte = new JLabel("Suporte Corporativo: suporte@healthequilibrium.com", SwingConstants.CENTER);
        lblSuporte.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSuporte.setForeground(Color.GRAY);
        lblSuporte.setBounds(0, 480, 460, 20);
        p.add(lblSuporte);

        // --- AUTENTICAÇÃO VIA BANCO ---
        btnEntrar.addActionListener(e -> autenticar());
        txtPass.addActionListener(e -> autenticar()); // Enter na senha também loga
    }

    private void autenticar() {
        String login = txtUser.getText().trim();
        String senha = new String(txtPass.getPassword()).trim();

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Preencha usuário e senha.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Query que já traz os dados do médico (LEFT JOIN) caso seja médico
        String sql = "SELECT u.idUsuario, u.nome, u.perfil, u.ativo, "
                   + "m.crm, e.nome AS especialidade "
                   + "FROM usuario u "
                   + "LEFT JOIN medico m ON m.idUsuario = u.idUsuario "
                   + "LEFT JOIN especialidade e ON e.idEspecialidade = m.idEspecialidade "
                   + "WHERE u.login = ? AND u.senha = ?";

        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this,
                        "Usuário ou senha incorretos.", "Acesso Recusado",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verifica se está ativo
                if (!rs.getBoolean("ativo")) {
                    JOptionPane.showMessageDialog(this,
                        "Usuário inativo. Entre em contato com o administrador.",
                        "Acesso Negado", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int    idUsuario    = rs.getInt("idUsuario");
                String nome         = rs.getString("nome");
                String perfil       = rs.getString("perfil");
                String especialidade = rs.getString("especialidade");

                this.dispose();

                String pNorm = perfil.toLowerCase()
                               .replace("é", "e").replace("á", "a");

                if (pNorm.contains("admin") || pNorm.contains("adm")) {
                    new TelaAdmin().setVisible(true);

                } else if (pNorm.contains("medico") || pNorm.contains("medic")) {
                    String esp = especialidade != null ? especialidade : "Não informada";
                    new TelaMedico(nome, esp, idUsuario).setVisible(true);

                } else if (pNorm.contains("secretaria") || pNorm.contains("secre")) {
                    new TelaSecretaria().setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null,
                        "Perfil '" + perfil + "' não tem tela configurada.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao conectar ao banco: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}