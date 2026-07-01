package com.mycompany.clinicamedica.newpackage.view.Secretaria; // <-- Ajustado estritamente para o seu pacote real!

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TelaFiltrarConsultas extends JFrame {

    private JTextField txtDataFiltro;
    private JComboBox<String> cbMedicoFiltro;
    private JButton btnFiltrar, btnAtualizarStatus, btnVoltar;
    private JTable tabelaAgenda;
    private DefaultTableModel modeloTabela;

    public TelaFiltrarConsultas() {
        setTitle("🏥 Sistema Clínica Médica - Fluxo da Agenda Diária");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 680);
        setLocationRelativeTo(null);
        setResizable(false);

        // PALETA DE CORES TERROSAS DO SEU PROJETO
        Color corCremeClaro   = new Color(251, 251, 250); // #FBFBFA
        Color corDestaqueGold = new Color(193, 158, 103); // #C19E67
        Color corTomMedio     = new Color(110, 102, 95);  // #6E665F
        Color corRotuloCinza  = new Color(180, 169, 158); // #B4A99E
        Color corMarromEscuro = new Color(61, 28, 6);     // #3D1C06

        // Painel de Fundo
        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(corTomMedio);
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        // Título da Tela
        JLabel lblTitulo = new JLabel("Agenda e Fluxo de Consultas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(50, 25, 500, 40);
        painelFundo.add(lblTitulo);

        // ====================================================================
        // PAINEL DE FILTROS (Topo)
        // ====================================================================
        JPanel painelFiltros = new JPanel();
        painelFiltros.setBackground(corMarromEscuro);
        painelFiltros.setBounds(50, 85, 885, 80);
        painelFiltros.setLayout(null);
        painelFiltros.setBorder(new LineBorder(corDestaqueGold, 1, true));
        painelFundo.add(painelFiltros);

        Font fonteLabel = new Font("Segoe UI", Font.PLAIN, 13);

        // Filtro Data
        JLabel lblData = new JLabel("Data:");
        lblData.setFont(fonteLabel);
        lblData.setForeground(corRotuloCinza);
        lblData.setBounds(20, 12, 100, 20);
        painelFiltros.add(lblData);

        txtDataFiltro = new JTextField("25/05/2026");
        txtDataFiltro.setBounds(20, 35, 140, 30);
        txtDataFiltro.setBackground(corTomMedio);
        txtDataFiltro.setForeground(corCremeClaro);
        txtDataFiltro.setCaretColor(corCremeClaro);
        txtDataFiltro.setBorder(new LineBorder(corDestaqueGold, 1));
        painelFiltros.add(txtDataFiltro);

        // Filtro Médico
        JLabel lblMedico = new JLabel("Filtrar por Médico:");
        lblMedico.setFont(fonteLabel);
        lblMedico.setForeground(corRotuloCinza);
        lblMedico.setBounds(180, 12, 200, 20);
        painelFiltros.add(lblMedico);

        cbMedicoFiltro = new JComboBox<>(new String[]{
            "Todos os Médicos", 
            "Dr. Arnaldo Silva (Cardiologia)", 
            "Dra. Beatriz Costa (Pediatria)", 
            "Dr. Carlos Eduardo (Clínico Geral)"
        });
        cbMedicoFiltro.setBounds(180, 35, 280, 30);
        cbMedicoFiltro.setBackground(corTomMedio);
        cbMedicoFiltro.setForeground(corCremeClaro);
        cbMedicoFiltro.setBorder(new LineBorder(corDestaqueGold, 1));
        painelFiltros.add(cbMedicoFiltro);

        // Botão Filtrar
        btnFiltrar = new JButton("🔍 Filtrar Agenda");
        btnFiltrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnFiltrar.setBackground(corDestaqueGold);
        btnFiltrar.setForeground(corMarromEscuro);
        btnFiltrar.setBounds(700, 30, 160, 35);
        btnFiltrar.setFocusPainted(false);
        btnFiltrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFiltros.add(btnFiltrar);

        // ====================================================================
        // TABELA DE AGENDAMENTOS (Centro)
        // ====================================================================
        String[] colunas = {"Horário", "Paciente", "Médico / Especialidade", "Tipo", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaAgenda = new JTable(modeloTabela);

        // Simulando a listagem da agenda do dia
        modeloTabela.addRow(new Object[]{"08:00", "Carlos Eduardo Santos", "Dr. Arnaldo Silva", "Particular", "Finalizado"});
        modeloTabela.addRow(new Object[]{"09:00", "Ana Julia Ferreira", "Dra. Beatriz Costa", "Retorno", "Em Atendimento"});
        modeloTabela.addRow(new Object[]{"10:30", "Marcos Antônio Lima", "Dr. Carlos Eduardo", "Convênio", "Aguardando"});
        modeloTabela.addRow(new Object[]{"14:00", "Juliana Ribeiro Dias", "Dr. Arnaldo Silva", "Particular", "Aguardando"});

        JScrollPane barraRolagem = new JScrollPane(tabelaAgenda);
        barraRolagem.setBounds(50, 185, 885, 320);
        barraRolagem.setBorder(new LineBorder(corDestaqueGold, 1));
        tabelaAgenda.setBackground(corCremeClaro);
        tabelaAgenda.setGridColor(corRotuloCinza);
        tabelaAgenda.setRowHeight(28);
        tabelaAgenda.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        painelFundo.add(barraRolagem);

        // ====================================================================
        // BOTÕES DE RODA PÉ
        // ====================================================================
        btnAtualizarStatus = new JButton("Alterar Status da Consulta");
        btnAtualizarStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAtualizarStatus.setBackground(corMarromEscuro);
        btnAtualizarStatus.setForeground(corCremeClaro);
        btnAtualizarStatus.setBounds(695, 530, 240, 40);
        btnAtualizarStatus.setFocusPainted(false);
        btnAtualizarStatus.setBorder(new LineBorder(corDestaqueGold, 1));
        btnAtualizarStatus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnAtualizarStatus);

        btnVoltar = new JButton("← Voltar ao Menu");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setBackground(new Color(180, 70, 70));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(50, 530, 160, 40);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnVoltar);

        // ====================================================================
        // COMPORTAMENTO / EVENTOS
        // ====================================================================
        btnFiltrar.addActionListener(e -> {
            String med = cbMedicoFiltro.getSelectedItem().toString();
            JOptionPane.showMessageDialog(this, "Atualizando lista para " + med + " na data " + txtDataFiltro.getText(), "Busca Concluída", JOptionPane.INFORMATION_MESSAGE);
        });

        btnAtualizarStatus.addActionListener(e -> {
            int linha = tabelaAgenda.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione uma consulta na tabela para alterar o status.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                String paciente = tabelaAgenda.getValueAt(linha, 1).toString();
                String[] opcoes = {"Aguardando", "Em Atendimento", "Finalizado", "Cancelado"};
                
                String novoStatus = (String) JOptionPane.showInputDialog(
                        this, 
                        "Defina o novo estado da consulta de:\n" + paciente, 
                        "Atualizar Fluxo", 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, 
                        opcoes, 
                        opcoes[0]
                );
                
                if (novoStatus != null) {
                    tabelaAgenda.setValueAt(novoStatus, linha, 4);
                    JOptionPane.showMessageDialog(this, "Status atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnVoltar.addActionListener(e -> this.dispose());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new TelaFiltrarConsultas().setVisible(true);
        });
    }
}