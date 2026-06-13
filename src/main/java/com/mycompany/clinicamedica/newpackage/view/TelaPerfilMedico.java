package com.mycompany.clinicamedica.newpackage.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelaPerfilMedico extends JFrame {
    private String loginRef;
    private JTextField txtCRM;
    private JComboBox<String> cbEsp;

    public TelaPerfilMedico(String login) {
        this.loginRef = login;
        setTitle("Credenciais Clínicas — " + login);
        setSize(440, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color marromEscuro = new Color(61, 28, 6);
        Color corGold      = new Color(193, 158, 103);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(marromEscuro);
        p.setBorder(new EmptyBorder(30, 30, 30, 30));
        setContentPane(p);

        JLabel lblInfo = new JLabel("Configuração do Perfil Clínico");
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblInfo.setForeground(corGold);
        p.add(lblInfo); p.add(Box.createVerticalStrut(25));

        JLabel l1 = new JLabel("Número do CRM (com UF):");
        l1.setForeground(Color.LIGHT_GRAY);
        p.add(l1); p.add(Box.createVerticalStrut(5));
        txtCRM = new JTextField();
        txtCRM.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));
        txtCRM.setBackground(new Color(110, 102, 95));
        txtCRM.setForeground(Color.WHITE);
        txtCRM.setCaretColor(Color.WHITE);
        p.add(txtCRM); p.add(Box.createVerticalStrut(20));

        JLabel l2 = new JLabel("Especialidade Médica:");
        l2.setForeground(Color.LIGHT_GRAY);
        p.add(l2); p.add(Box.createVerticalStrut(5));
        cbEsp = new JComboBox<>();
        cbEsp.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));
        cbEsp.setBackground(new Color(110, 102, 95));
        cbEsp.setForeground(Color.WHITE);
        cbEsp.addItem("Selecione...");
        for (String esp : GerenciadorAutenticacao.listaEspecialidades) {
            cbEsp.addItem(esp);
        }
        p.add(cbEsp); p.add(Box.createVerticalStrut(35));

        JButton btnFinalizar = new JButton("Finalizar Vínculo Profissional");
        btnFinalizar.setMaximumSize(new Dimension(Short.MAX_VALUE, 45));
        btnFinalizar.setBackground(corGold);
        btnFinalizar.setForeground(marromEscuro);
        btnFinalizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnFinalizar.setFocusPainted(false);
        p.add(btnFinalizar);

        btnFinalizar.addActionListener(e -> {
            String crm = txtCRM.getText().trim();
            if(crm.isEmpty() || cbEsp.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Insira os dados médicos corretamente.");
                return;
            }

            for(GerenciadorAutenticacao.Usuario u : GerenciadorAutenticacao.bancoUsuarios) {
                if(u.login.equals(loginRef)) {
                    u.crm = crm;
                    u.especialidade = cbEsp.getSelectedItem().toString();
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "CRM e especialidade anexados com sucesso!");
            this.dispose();
        });
    }
}