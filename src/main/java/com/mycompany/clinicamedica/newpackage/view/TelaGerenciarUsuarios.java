package com.mycompany.clinicamedica.newpackage.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TelaGerenciarUsuarios extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JComboBox<String> cbPerfil;
    private DefaultTableModel modelo;

    public TelaGerenciarUsuarios() {
        setTitle("Gerenciamento de Colaboradores — VITA");
        setSize(980, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color marromEscuro = new Color(61, 28, 6);
        Color corGold      = new Color(193, 158, 103);

        JPanel principal = new JPanel(new BorderLayout(20, 0));
        principal.setBackground(new Color(244, 241, 234));
        principal.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(principal);

        // --- FORMULÁRIO LATERAL ORGANIZADO EM BOX ---
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(marromEscuro);
        form.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(corGold, 1, true),
            new EmptyBorder(25, 20, 25, 20)
        ));
        form.setPreferredSize(new Dimension(320, 500));

        JLabel titleForm = new JLabel("Novo Colaborador");
        titleForm.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleForm.setForeground(corGold);
        form.add(titleForm); form.add(Box.createVerticalStrut(25));

        txtUser = adicionarCampo(form, "Nome de Usuário (Login):");
        
        txtPass = new JPasswordField();
        txtPass.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));
        txtPass.setBackground(new Color(110, 102, 95));
        txtPass.setForeground(Color.WHITE);
        txtPass.setCaretColor(Color.WHITE);
        form.add(new JLabel("Senha de Acesso:")); form.add(Box.createVerticalStrut(5));
        form.add(txtPass); form.add(Box.createVerticalStrut(15));

        cbPerfil = new JComboBox<>(new String[]{"Selecione...", "Médico", "Secretária", "Administrador"});
        cbPerfil.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));
        cbPerfil.setBackground(new Color(110, 102, 95));
        cbPerfil.setForeground(Color.WHITE);
        form.add(new JLabel("Perfil Organizacional:")); form.add(Box.createVerticalStrut(5));
        form.add(cbPerfil); form.add(Box.createVerticalStrut(30));

        JButton btnSalvar = new JButton("Salvar Perfil");
        btnSalvar.setMaximumSize(new Dimension(Short.MAX_VALUE, 45));
        btnSalvar.setBackground(corGold);
        btnSalvar.setForeground(marromEscuro);
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.setFocusPainted(false);
        form.add(btnSalvar);
        principal.add(form, BorderLayout.WEST);

        // --- TABELA DE EXIBIÇÃO ---
        String[] colunas = {"Usuário", "Perfil Vinculado", "Status"};
        modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);
        tabela.setRowHeight(30);
        tabela.setGridColor(new Color(230, 225, 215));
        
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(corGold));
        principal.add(scroll, BorderLayout.CENTER);

        // Alimentação estática vinda do gerenciador
        for(GerenciadorAutenticacao.Usuario u : GerenciadorAutenticacao.bancoUsuarios) {
            modelo.addRow(new Object[]{u.login, u.perfil, "Ativo"});
        }

        btnSalvar.addActionListener(e -> {
            String login = txtUser.getText().trim();
            String senha = new String(txtPass.getPassword()).trim();
            String perfil = cbPerfil.getSelectedItem().toString();

            if(login.isEmpty() || cbPerfil.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Preencha todos os parâmetros adequadamente.");
                return;
            }

            GerenciadorAutenticacao.Usuario novo = new GerenciadorAutenticacao.Usuario(login, senha, perfil);
            GerenciadorAutenticacao.bancoUsuarios.add(novo);
            modelo.addRow(new Object[]{login, perfil, "Ativo"});

            if(perfil.equals("Médico")) {
                new TelaPerfilMedico(login).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuário registrado com sucesso!");
            }
            txtUser.setText(""); txtPass.setText(""); cbPerfil.setSelectedIndex(0);
        });
    }

    private JTextField adicionarCampo(JPanel painel, String label) {
        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.LIGHT_GRAY);
        JTextField txt = new JTextField();
        txt.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));
        txt.setBackground(new Color(110, 102, 95));
        txt.setForeground(Color.WHITE);
        txt.setCaretColor(Color.WHITE);
        painel.add(lbl); painel.add(Box.createVerticalStrut(5));
        painel.add(txt); painel.add(Box.createVerticalStrut(15));
        return txt;
    }
}