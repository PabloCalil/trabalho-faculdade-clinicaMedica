package com.mycompany.clinicamedica.newpackage.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TelaLogin extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;

    public TelaLogin() {
        // Configurações básicas da janela estruturada
        setTitle("VITA v2.0 - Autenticação Corporativa");
        setSize(460, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Paleta de Cores Premium (Marrom e Dourado VITA)
        Color marromEscuro  = new Color(61, 28, 6);
        Color corGold       = new Color(193, 158, 103);
        Color campoFundo    = new Color(110, 102, 95);

        // Painel Principal em Layout Absoluto para fixação de componentes
        JPanel p = new JPanel(null);
        p.setBackground(marromEscuro);
        setContentPane(p);

        // --- PAINEL DO CABEÇALHO ---
        JPanel pnlHeader = new JPanel(null);
        pnlHeader.setBounds(0, 0, 460, 120);
        pnlHeader.setBackground(new Color(43, 19, 4)); 
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, corGold));
        p.add(pnlHeader);

        JLabel lblLogo = new JLabel("V I T A", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblLogo.setForeground(corGold);
        lblLogo.setBounds(0, 25, 460, 40);
        pnlHeader.add(lblLogo);

        JLabel lblSub = new JLabel("CLÍNICA MÉDICA PREMIUM", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblSub.setForeground(Color.LIGHT_GRAY);
        lblSub.setBounds(0, 65, 460, 20);
        pnlHeader.add(lblSub);

        // --- CAMPO: USUÁRIO ---
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
            BorderFactory.createEmptyBorder(0, 10, 0, 10) // Padding interno contra travamento de texto
        ));
        p.add(txtUser);

        // --- CAMPO: SENHA ---
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

        // --- BOTÃO DE ENTRAR ---
        JButton btnEntrar = new JButton("ACESSAR PAINEL");
        btnEntrar.setBounds(45, 370, 360, 48);
        btnEntrar.setBackground(corGold);
        btnEntrar.setForeground(marromEscuro);
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        p.add(btnEntrar);

        // --- SUPORTE INFERIOR ---
        JLabel lblSuporte = new JLabel("Suporte Corporativo: suporte@vitaclinica.com", SwingConstants.CENTER);
        lblSuporte.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSuporte.setForeground(Color.GRAY);
        lblSuporte.setBounds(0, 480, 460, 20);
        p.add(lblSuporte);

        // --- LÓGICA DE AUTENTICAÇÃO INTEGRADA E CORRIGIDA ---
        btnEntrar.addActionListener(e -> {
            String usuarioDigitado = txtUser.getText().trim();
            String senhaDigitada = new String(txtPass.getPassword()).trim();
            boolean autenticado = false;

            for (GerenciadorAutenticacao.Usuario u : GerenciadorAutenticacao.bancoUsuarios) {
                // Validação de segurança primária
                if (u.login.equalsIgnoreCase(usuarioDigitado) && u.senha.equals(senhaDigitada)) {
                    autenticado = true;
                    this.dispose(); // Encerra a visualização do login
                    
                    // Normalização do texto para evitar conflito de encoding/acentos
                    String pNormalizado = u.perfil.toLowerCase();
                    
                    if (pNormalizado.contains("admin") || pNormalizado.contains("adm")) {
                        new TelaAdmin().setVisible(true);
                    } else if (pNormalizado.contains("medico") || pNormalizado.contains("médico") || pNormalizado.contains("medic")) {
                        new TelaMedico(u.login, u.especialidade).setVisible(true);
                    } else if (pNormalizado.contains("secretaria") || pNormalizado.contains("secretária") || pNormalizado.contains("secre")) {
                        new TelaSecretaria().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Perfil verificado: " + u.perfil + ", mas a tela correspondente não foi achada.");
                    }
                    break;
                }
            }

            if (!autenticado) {
                JOptionPane.showMessageDialog(this, "Usuário ou chave de acesso incorretos.", "Acesso Recusado", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        // Look and Feel multiplataforma estável
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}