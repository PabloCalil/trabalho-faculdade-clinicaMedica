package com.mycompany.clinicamedica.newpackage.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TelaGerenciarUsuarios extends JFrame {

    private JTextField txtNovoUsuario;
    private JPasswordField txtNovaSenha;
    private JComboBox<String> cbPerfil;
    private JButton btnSalvarUsuario, btnExcluirUsuario, btnVoltar;
    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabela;

    public TelaGerenciarUsuarios() {
        setTitle("🏥 Sistema Clínica Médica - Controle de Acessos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 680);
        setLocationRelativeTo(null);
        setResizable(false);

        // PALETA TERROSA DO SEU PROJETO
        Color corCremeClaro   = new Color(251, 251, 250);
        Color corDestaqueGold = new Color(193, 158, 103);
        Color corTomMedio     = new Color(110, 102, 95);
        Color corRotuloCinza  = new Color(180, 169, 158);
        Color corMarromEscuro = new Color(61, 28, 6);

        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(corTomMedio);
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        JLabel lblTitulo = new JLabel("Gerenciamento de Usuários e Perfis");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(50, 25, 600, 40);
        painelFundo.add(lblTitulo);

        // ====================================================================
        // PAINEL DE CADASTRO DE PERFIL (Esquerda)
        // ====================================================================
        JPanel painelCadastro = new JPanel();
        painelCadastro.setBackground(corMarromEscuro);
        painelCadastro.setBounds(50, 90, 380, 430);
        painelCadastro.setLayout(null);
        painelCadastro.setBorder(new LineBorder(corDestaqueGold, 1, true));
        painelFundo.add(painelCadastro);

        Font fonteLabel = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel lblUser = new JLabel("Nome de Usuário (Login):");
        lblUser.setFont(fonteLabel);
        lblUser.setForeground(corRotuloCinza);
        lblUser.setBounds(30, 30, 200, 20);
        painelCadastro.add(lblUser);

        txtNovoUsuario = new JTextField();
        txtNovoUsuario.setBounds(30, 55, 320, 35);
        txtNovoUsuario.setBackground(corTomMedio);
        txtNovoUsuario.setForeground(corCremeClaro);
        txtNovoUsuario.setBorder(new LineBorder(corDestaqueGold, 1));
        painelCadastro.add(txtNovoUsuario);

        JLabel lblPass = new JLabel("Senha de Acesso:");
        lblPass.setFont(fonteLabel);
        lblPass.setForeground(corRotuloCinza);
        lblPass.setBounds(30, 110, 200, 20);
        painelCadastro.add(lblPass);

        txtNovaSenha = new JPasswordField();
        txtNovaSenha.setBounds(30, 135, 320, 35);
        txtNovaSenha.setBackground(corTomMedio);
        txtNovaSenha.setForeground(corCremeClaro);
        txtNovaSenha.setBorder(new LineBorder(corDestaqueGold, 1));
        painelCadastro.add(txtNovaSenha);

        // O Ponto Chave: Definição do Perfil e do Caminho
        JLabel lblPerfil = new JLabel("Perfil / Nível de Acesso:");
        lblPerfil.setFont(fonteLabel);
        lblPerfil.setForeground(corRotuloCinza);
        lblPerfil.setBounds(30, 190, 200, 20);
        painelCadastro.add(lblPerfil);

        cbPerfil = new JComboBox<>(new String[]{"Selecione o Caminho...", "Médico", "Secretária", "Administrador"});
        cbPerfil.setBounds(30, 215, 320, 35);
        cbPerfil.setBackground(corTomMedio);
        cbPerfil.setForeground(corCremeClaro);
        cbPerfil.setBorder(new LineBorder(corDestaqueGold, 1));
        painelCadastro.add(cbPerfil);

        btnSalvarUsuario = new JButton("Vincular Perfil");
        btnSalvarUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvarUsuario.setBackground(corDestaqueGold);
        btnSalvarUsuario.setForeground(corMarromEscuro);
        btnSalvarUsuario.setBounds(30, 300, 320, 40);
        btnSalvarUsuario.setFocusPainted(false);
        btnSalvarUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelCadastro.add(btnSalvarUsuario);

        btnExcluirUsuario = new JButton("Remover Acesso");
        btnExcluirUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnExcluirUsuario.setBackground(new Color(180, 70, 70));
        btnExcluirUsuario.setForeground(Color.WHITE);
        btnExcluirUsuario.setBounds(30, 355, 320, 35);
        btnExcluirUsuario.setFocusPainted(false);
        btnExcluirUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelCadastro.add(btnExcluirUsuario);

        // ====================================================================
        // TABELA DE USUÁRIOS ATIVOS (Direita)
        // ====================================================================
        String[] colunas = {"Usuário", "Perfil Vinculado", "Destino do Sistema"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaUsuarios = new JTable(modeloTabela);

        // Mock de dados iniciais salvos
        modeloTabela.addRow(new Object[]{"ana.secretaria", "Secretária", "TelaSecretaria.java"});
        modeloTabela.addRow(new Object[]{"dr.roberto", "Médico", "TelaMedico.java"});
        modeloTabela.addRow(new Object[]{"pablo.admin", "Administrador", "TelaAdministrador.java"});

        JScrollPane scrollTabela = new JScrollPane(tabelaUsuarios);
        scrollTabela.setBounds(460, 90, 480, 430);
        scrollTabela.setBorder(new LineBorder(corDestaqueGold, 1));
        tabelaUsuarios.setBackground(corCremeClaro);
        tabelaUsuarios.setRowHeight(25);
        painelFundo.add(scrollTabela);

        // Botão Voltar
        btnVoltar = new JButton("← Voltar ao Menu Admin");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setBackground(new Color(110, 102, 95).darker());
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(50, 550, 200, 35);
        btnVoltar.setFocusPainted(false);
        painelFundo.add(btnVoltar);

        // ====================================================================
        // AÇÕES DO ADMINISTRADOR
        // ====================================================================
        btnSalvarUsuario.addActionListener(e -> {
            String usuario = txtNovoUsuario.getText().trim();
            String senha = new String(txtNovaSenha.getPassword()).trim();
            int perfilSelecionado = cbPerfil.getSelectedIndex();

            if (usuario.isEmpty() || senha.isEmpty() || perfilSelecionado == 0) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos e selecione um perfil válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                String perfilStr = cbPerfil.getSelectedItem().toString();
                String caminhoDestino = "";

                // Define dinamicamente o destino do arquivo baseado na escolha do Admin
                switch (perfilStr) {
                    case "Médico": caminhoDestino = "TelaMedico.java"; break;
                    case "Secretária": caminhoDestino = "TelaSecretaria.java"; break;
                    case "Administrador": caminhoDestino = "TelaAdministrador.java"; break;
                }

                modeloTabela.addRow(new Object[]{usuario, perfilStr, caminhoDestino});
                JOptionPane.showMessageDialog(this, "Perfil corporativo criado!\nUsuário '" + usuario + "' direcionado para " + caminhoDestino, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                // Limpa campos
                txtNovoUsuario.setText("");
                txtNovaSenha.setText("");
                cbPerfil.setSelectedIndex(0);
            }
        });

        btnExcluirUsuario.addActionListener(e -> {
            int linha = tabelaUsuarios.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário na tabela para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                modeloTabela.removeRow(linha);
                JOptionPane.showMessageDialog(this, "Acesso revogado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> this.dispose());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaGerenciarUsuarios().setVisible(true));
    }
}