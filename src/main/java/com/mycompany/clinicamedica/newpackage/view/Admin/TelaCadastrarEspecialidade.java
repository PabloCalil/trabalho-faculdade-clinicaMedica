/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinicamedica.newpackage.view.Admin;

import Services.BDSConnection;
import com.mycompany.clinicamedica.newpackage.view.ui.Tema;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TelaCadastrarEspecialidade extends JFrame {

    private JTextField txtNovaEsp, txtDescricao;
    private DefaultTableModel modelo;

    private final Color marromEscuro = Tema.MARROM_ESCURO;
    private final Color corGold      = Tema.GOLD;
    private final Color corTexto     = Tema.TEXTO_ESCURO;
    private final Color corCreme     = Tema.FUNDO_CLARO;
    private final Color corRotulo    = new Color(90, 80, 70);

    public TelaCadastrarEspecialidade() {
        setTitle("Health Equilibrium - Áreas Clínicas");
        setSize(760, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(corCreme);
        setContentPane(root);

        // ====================================================================
        // CABEÇALHO PADRÃO (mesmo layout/paleta das demais telas)
        // ====================================================================
        JButton btnVoltar = Tema.botaoPerigo("Voltar");
        btnVoltar.addActionListener(e -> dispose());
        root.add(Tema.cabecalho("Especialidades Clínicas — Áreas Clínicas", null, btnVoltar),
                 BorderLayout.NORTH);

        JPanel principal = new JPanel(new BorderLayout(0, 15));
        principal.setBackground(corCreme);
        principal.setBorder(new EmptyBorder(20, 40, 20, 40));
        root.add(principal, BorderLayout.CENTER);

        // ====================================================================
        // PAINEL DE ENTRADA (cartão branco)
        // ====================================================================
        JPanel painelEntrada = new JPanel();
        painelEntrada.setBackground(Color.WHITE);
        painelEntrada.setLayout(null);
        painelEntrada.setPreferredSize(new Dimension(0, 150));
        painelEntrada.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(corGold, 1, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        principal.add(painelEntrada, BorderLayout.NORTH);

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 13);

        JLabel lblNome = new JLabel("Nome da Especialidade:");
        lblNome.setFont(fonteLabel);
        lblNome.setForeground(corRotulo);
        lblNome.setBounds(15, 10, 200, 20);
        painelEntrada.add(lblNome);

        txtNovaEsp = new JTextField();
        txtNovaEsp.setBounds(15, 33, 380, 33);
        txtNovaEsp.setBackground(Color.WHITE);
        txtNovaEsp.setForeground(corTexto);
        txtNovaEsp.setCaretColor(corTexto);
        txtNovaEsp.setBorder(new LineBorder(corGold, 1));
        painelEntrada.add(txtNovaEsp);

        JLabel lblDesc = new JLabel("Descrição:");
        lblDesc.setFont(fonteLabel);
        lblDesc.setForeground(corRotulo);
        lblDesc.setBounds(15, 75, 200, 20);
        painelEntrada.add(lblDesc);

        txtDescricao = new JTextField();
        txtDescricao.setBounds(15, 95, 380, 33);
        txtDescricao.setBackground(Color.WHITE);
        txtDescricao.setForeground(corTexto);
        txtDescricao.setCaretColor(corTexto);
        txtDescricao.setBorder(new LineBorder(corGold, 1));
        painelEntrada.add(txtDescricao);

        JButton btnAdd = new JButton("Adicionar");
        btnAdd.setBounds(415, 33, 180, 33);
        btnAdd.setBackground(corGold);
        btnAdd.setForeground(marromEscuro);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAdd.setFocusPainted(false);
        btnAdd.setOpaque(true);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setBorder(new LineBorder(corGold.darker(), 1));
        painelEntrada.add(btnAdd);

        JButton btnExcluir = new JButton("Excluir Especialidade");
        btnExcluir.setBounds(415, 95, 180, 33);
        btnExcluir.setBackground(new Color(180, 70, 70));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnExcluir.setFocusPainted(false);
        btnExcluir.setOpaque(true);
        btnExcluir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelEntrada.add(btnExcluir);

        // ====================================================================
        // TABELA
        // ====================================================================
        String[] colunas = {"ID", "Especialidade", "Descrição"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição direta na tabela
            }
        };

        JTable tabela = new JTable(modelo);
        Tema.estilizarTabela(tabela);

        // Largura das colunas
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(350);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(corGold));
        principal.add(scroll, BorderLayout.CENTER);

        // ====================================================================
        // EVENTOS
        // ====================================================================
        carregarTabela();

        btnAdd.addActionListener(e -> {
            String nome = txtNovaEsp.getText().trim();
            String desc = txtDescricao.getText().trim();

            if (nome.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Preencha o nome e a descrição.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "INSERT INTO especialidade (nome, descricao) VALUES (?, ?)";
            try (Connection conn = BDSConnection.getConexao();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, nome);
                stmt.setString(2, desc);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this,
                    "Especialidade '" + nome + "' cadastrada com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                txtNovaEsp.setText("");
                txtDescricao.setText("");
                carregarTabela();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao salvar: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione uma especialidade na tabela para excluir.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = (int) modelo.getValueAt(linhaSelecionada, 0);
            String nome = modelo.getValueAt(linhaSelecionada, 1).toString();

            int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir a especialidade '" + nome + "'?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

            if (confirmacao != JOptionPane.YES_OPTION) return;

            String sql = "DELETE FROM especialidade WHERE idEspecialidade = ?";
            try (Connection conn = BDSConnection.getConexao();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this,
                    "Especialidade '" + nome + "' excluída com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                carregarTabela();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao excluir: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // ====================================================================
    // CARREGA DO BANCO
    // ====================================================================
    private void carregarTabela() {
        modelo.setRowCount(0);
        String sql = "SELECT idEspecialidade, nome, descricao FROM especialidade ORDER BY nome";

        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("idEspecialidade"),
                    rs.getString("nome"),
                    rs.getString("descricao")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar especialidades: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastrarEspecialidade().setVisible(true));
    }
}