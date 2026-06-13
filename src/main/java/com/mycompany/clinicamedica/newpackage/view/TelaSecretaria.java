package com.mycompany.clinicamedica.newpackage.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TelaSecretaria extends JFrame {
    public TelaSecretaria() {
        setTitle("VITA — Painel de Recepção e Atendimento");
        setSize(1024, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color marromEscuro = new Color(61, 28, 6);
        Color corGold      = new Color(193, 158, 103);
        Color fundoClaro   = new Color(244, 241, 234);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(fundoClaro);
        setContentPane(principal);

        // --- HEADER SECRETÁRIA ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(marromEscuro);
        header.setBorder(new EmptyBorder(20, 40, 20, 40));

        JLabel lblTitulo = new JLabel("Atendimento e Recepção Central — VITA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        header.add(lblTitulo, BorderLayout.WEST);

        JButton btnSair = new JButton("Logout");
        btnSair.setBackground(new Color(180, 70, 70));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.addActionListener(e -> {
            this.dispose();
            new TelaLogin().setVisible(true);
        });
        header.add(btnSair, BorderLayout.EAST);
        principal.add(header, BorderLayout.NORTH);

        // --- CORPO OPERACIONAL ---
        JPanel corpo = new JPanel(new BorderLayout(30, 0));
        corpo.setBackground(fundoClaro);
        corpo.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Lateral de Ações Rápidas
        JPanel menuAcoes = new JPanel(new GridLayout(4, 1, 0, 20));
        menuAcoes.setBackground(fundoClaro);
        menuAcoes.setPreferredSize(new Dimension(280, 0));

        JButton btnNovoPac = criarBotaoRecepcao("👤 Cadastrar Novo Paciente");
        JButton btnAgendar = criarBotaoRecepcao("📅 Agendar Nova Consulta");
        JButton btnCheckin = criarBotaoRecepcao("✅ Confirmar Presença (Check-in)");
        JButton btnMedicos = criarBotaoRecepcao("🩺 Consultar Escala Médica");

        menuAcoes.add(btnNovoPac); menuAcoes.add(btnAgendar); menuAcoes.add(btnCheckin); menuAcoes.add(btnMedicos);
        corpo.add(menuAcoes, BorderLayout.WEST);

        // Central: Visualizador da Agenda Ativa
        JPanel pnlAgenda = new JPanel(new BorderLayout(0, 10));
        pnlAgenda.setBackground(fundoClaro);
        
        JLabel lblAgenda = new JLabel("Grade Geral de Consultas Ativas / Monitoramento:");
        lblAgenda.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblAgenda.setForeground(marromEscuro);
        pnlAgenda.add(lblAgenda, BorderLayout.NORTH);

        String[] colunas = {"Paciente", "Médico / Especialidade", "Horário", "Status Fila"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        modelo.addRow(new Object[]{"Carlos Augusto Silva", "Dr. Medico (Clínico Geral)", "08:30", "Em Atendimento"});
        modelo.addRow(new Object[]{"Mariana Costa Souza", "Dr. Medico (Clínico Geral)", "09:15", "Aguardando"});
        modelo.addRow(new Object[]{"Alice Vieira Ramos", "Dra. Ana (Pediatria)", "10:30", "Agendado"});

        JTable tabela = new JTable(modelo);
        tabela.setRowHeight(30);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(corGold));
        pnlAgenda.add(scroll, BorderLayout.CENTER);

        corpo.add(pnlAgenda, BorderLayout.CENTER);
        principal.add(corpo, BorderLayout.CENTER);

        // Cliques das Funções
        btnNovoPac.addActionListener(e -> JOptionPane.showMessageDialog(this, "Redirecionando para a ficha de cadastro de prontuário físico de paciente..."));
        btnAgendar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrindo calendário mestre para bloqueio de horários clínicos..."));
        btnCheckin.addActionListener(e -> JOptionPane.showMessageDialog(this, "Disparando atualização de status do paciente para a sala do médico!"));
        btnMedicos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Exibindo corpo de médicos ativos e especialidades integradas..."));
    }

    private JButton criarBotaoRecepcao(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(new Color(61, 28, 6));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBorder(new LineBorder(new Color(193, 158, 103), 1, true));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}