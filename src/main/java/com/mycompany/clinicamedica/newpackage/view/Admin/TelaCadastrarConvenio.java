package com.mycompany.clinicamedica.newpackage.view.Admin;

import Services.ConvenioDAO;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class TelaCadastrarConvenio extends JFrame {

    private JTextField txtNome;
    private JFormattedTextField txtCnpj, txtTelefone, txtValidade;
    private JButton btnAdicionar, btnExcluir, btnVoltar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ConvenioDAO dao;

    private final Color corCremeClaro   = new Color(251, 251, 250);
    private final Color corDestaqueGold = new Color(193, 158, 103);
    private final Color corTomMedio     = new Color(110, 102, 95);
    private final Color corRotuloCinza  = new Color(180, 169, 158);
    private final Color corMarromEscuro = new Color(61, 28, 6);

    public TelaCadastrarConvenio() {
        setTitle("Health Equilibrium - Convênios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        dao = new ConvenioDAO();

        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(corTomMedio);
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        // --- Título ---
        JLabel lblTitulo = new JLabel("Cadastro de Convênios");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(50, 25, 600, 40);
        painelFundo.add(lblTitulo);

        // ====================================================================
        // PAINEL DE FORMULÁRIO
        // ====================================================================
        JPanel painelForm = new JPanel();
        painelForm.setBackground(corMarromEscuro);
        painelForm.setLayout(null);
        painelForm.setBounds(50, 85, 790, 210);
        painelForm.setBorder(new LineBorder(corDestaqueGold, 1, true));
        painelFundo.add(painelForm);

        Font fonteLabel = new Font("Segoe UI", Font.PLAIN, 14);

        // --- Linha 1: Nome | CNPJ ---
        JLabel lblNome = new JLabel("Nome do Convênio:");
        lblNome.setFont(fonteLabel);
        lblNome.setForeground(corRotuloCinza);
        lblNome.setBounds(30, 20, 200, 20);
        painelForm.add(lblNome);

        txtNome = criarCampo();
        txtNome.setBounds(30, 43, 340, 35);
        painelForm.add(txtNome);

        JLabel lblCnpj = new JLabel("CNPJ:");
        lblCnpj.setFont(fonteLabel);
        lblCnpj.setForeground(corRotuloCinza);
        lblCnpj.setBounds(400, 20, 200, 20);
        painelForm.add(lblCnpj);

        txtCnpj = criarCampoFormatado("##.###.###/####-##");
        txtCnpj.setBounds(400, 43, 360, 35);
        painelForm.add(txtCnpj);

        // --- Linha 2: Telefone | Validade ---
        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setFont(fonteLabel);
        lblTelefone.setForeground(corRotuloCinza);
        lblTelefone.setBounds(30, 95, 200, 20);
        painelForm.add(lblTelefone);

        txtTelefone = criarCampoFormatado("(##) # ####-####");
        txtTelefone.setBounds(30, 118, 340, 35);
        painelForm.add(txtTelefone);

        JLabel lblValidade = new JLabel("Validade (dd/MM/yyyy):");
        lblValidade.setFont(fonteLabel);
        lblValidade.setForeground(corRotuloCinza);
        lblValidade.setBounds(400, 95, 220, 20);
        painelForm.add(lblValidade);

        txtValidade = criarCampoFormatado("##/##/####");
        txtValidade.setBounds(400, 118, 360, 35);
        painelForm.add(txtValidade);

        // --- Botões do formulário ---
        btnAdicionar = new JButton("+ Adicionar Convênio");
        btnAdicionar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAdicionar.setBackground(corDestaqueGold);
        btnAdicionar.setForeground(corMarromEscuro);
        btnAdicionar.setBounds(30, 165, 220, 38);
        btnAdicionar.setFocusPainted(false);
        btnAdicionar.setOpaque(true);
        btnAdicionar.setBorderPainted(true);
        btnAdicionar.setBorder(new LineBorder(corDestaqueGold.darker(), 1));
        btnAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelForm.add(btnAdicionar);

        btnExcluir = new JButton("Excluir Selecionado");
        btnExcluir.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnExcluir.setBackground(new Color(180, 70, 70));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setBounds(270, 165, 220, 38);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setOpaque(true);
        btnExcluir.setBorderPainted(true);
        btnExcluir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelForm.add(btnExcluir);

        // ====================================================================
        // TABELA
        // ====================================================================
        String[] colunas = {"ID", "Nome", "CNPJ", "Telefone", "Validade"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(28);
        tabela.setBackground(corCremeClaro);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setSelectionBackground(corDestaqueGold);
        tabela.setSelectionForeground(corMarromEscuro);
        tabela.getTableHeader().setBackground(corMarromEscuro);
        tabela.getTableHeader().setForeground(corDestaqueGold);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(50, 315, 790, 300);
        scroll.setBorder(new LineBorder(corDestaqueGold, 1));
        painelFundo.add(scroll);

        // --- Botão Voltar ---
        btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setBackground(corTomMedio.darker());
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(50, 630, 180, 35);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setOpaque(true);
        btnVoltar.setBorderPainted(true);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnVoltar);

        // ====================================================================
        // EVENTOS
        // ====================================================================
        carregarTabela();

        btnAdicionar.addActionListener(e -> {
            String nome     = txtNome.getText().trim();
            String cnpj     = txtCnpj.getText();
            String telefone = txtTelefone.getText();
            String validade = txtValidade.getText();

            // Validação: campos vazios ou ainda com placeholder "_"
            if (nome.isEmpty()
                    || cnpj.contains("_") || cnpj.trim().isEmpty()
                    || telefone.contains("_") || telefone.trim().isEmpty()
                    || validade.contains("_") || validade.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos corretamente.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dao.inserir(nome, cnpj, telefone, validade)) {
                JOptionPane.showMessageDialog(this,
                    "Convênio '" + nome + "' cadastrado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                txtNome.setText("");
                txtCnpj.setValue(null);
                txtTelefone.setValue(null);
                txtValidade.setValue(null);
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar convênio. Verifique a data e tente novamente.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione um convênio na tabela para excluir.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id      = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            String nome = modeloTabela.getValueAt(linhaSelecionada, 1).toString();

            int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir o convênio '" + nome + "'?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

            if (confirmacao != JOptionPane.YES_OPTION) return;

            if (dao.excluir(id)) {
                JOptionPane.showMessageDialog(this,
                    "Convênio '" + nome + "' excluído com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erro ao excluir. Verifique se há pacientes vinculados a este convênio.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> this.dispose());
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        SimpleDateFormat sdfBr = new SimpleDateFormat("dd/MM/yyyy");
        try (ResultSet rs = dao.listarTodos()) {
            if (rs == null) return;
            while (rs.next()) {
                String cnpjDb    = rs.getString("cnpj");
                String telDb     = rs.getString("telefone");
                java.sql.Date dt = rs.getDate("validade");

                modeloTabela.addRow(new Object[]{
                    rs.getInt("idConvenio"),
                    rs.getString("nome"),
                    formatarCnpj(cnpjDb),
                    formatarTelefone(telDb),
                    dt == null ? "" : sdfBr.format(dt)
                });
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar tabela: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // Helpers de criação de campos
    // ------------------------------------------------------------------
    private JTextField criarCampo() {
        JTextField campo = new JTextField();
        estilizarCampo(campo);
        return campo;
    }

    private JFormattedTextField criarCampoFormatado(String mascara) {
        JFormattedTextField campo;
        try {
            MaskFormatter mf = new MaskFormatter(mascara);
            mf.setPlaceholderCharacter('_');
            campo = new JFormattedTextField(mf);
        } catch (java.text.ParseException e) {
            campo = new JFormattedTextField();
        }
        estilizarCampo(campo);
        return campo;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setBackground(corTomMedio);
        campo.setForeground(corCremeClaro);
        campo.setCaretColor(corCremeClaro);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(corDestaqueGold, 1),
                new EmptyBorder(0, 8, 0, 8)
        ));
    }

    // ------------------------------------------------------------------
    // Helpers de formatação para exibição
    // ------------------------------------------------------------------
    private String formatarCnpj(String s) {
        if (s == null) return "";
        s = s.replaceAll("\\D", "");
        if (s.length() != 14) return s;
        return s.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }

    private String formatarTelefone(String s) {
        if (s == null) return "";
        s = s.replaceAll("\\D", "");
        if (s.length() == 11) return s.replaceFirst("(\\d{2})(\\d{1})(\\d{4})(\\d{4})", "($1) $2 $3-$4");
        if (s.length() == 10) return s.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        return s;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> new TelaCadastrarConvenio().setVisible(true));
    }
}
