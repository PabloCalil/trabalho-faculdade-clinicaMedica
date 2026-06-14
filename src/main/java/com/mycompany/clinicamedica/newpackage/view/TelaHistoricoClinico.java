package com.mycompany.clinicamedica.newpackage.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TelaHistoricoClinico extends JFrame {
    public TelaHistoricoClinico(String nomePaciente) {
        setTitle("VITA - Linha do Tempo e Histórico Clínico");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        Color marromEscuro  = new Color(61, 28, 6);
        Color corGold       = new Color(193, 158, 103);
        Color fundoClaro    = new Color(244, 241, 234);

        JPanel p = new JPanel(null);
        p.setBackground(fundoClaro);
        setContentPane(p);

        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 650, 70);
        header.setBackground(marromEscuro);
        p.add(header);

        JLabel lblTitulo = new JLabel("HISTÓRICO DE EVOLUÇÕES: " + nomePaciente.toUpperCase());
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(20, 22, 610, 25);
        header.add(lblTitulo);

        // Tabela Histórica
        String[] colunas = {"Data Consulta", "Profissional", "Especialidade", "Diagnóstico (CID)"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        
        // Simulação de dados anteriores reais
        modelo.addRow(new Object[]{"14/01/2025", "Dr. Roberto Medico", "Clínico Geral", "Exame de Rotina (Z00.0)"});
        modelo.addRow(new Object[]{"08/09/2025", "Dra. Ana Costa", "Cardiologia", "Hipertensão Essencial (I10)"});
        modelo.addRow(new Object[]{"02/03/2026", "Dr. Roberto Medico", "Clínico Geral", "Faringite Aguda (J02.9)"});

        JTable tabela = new JTable(modelo);
        tabela.setRowHeight(28);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(25, 100, 585, 240);
        scroll.setBorder(new LineBorder(corGold));
        p.add(scroll);

        JButton btnFechar = new JButton("FECHAR HISTÓRICO");
        btnFechar.setBounds(25, 360, 585, 40);
        btnFechar.setBackground(Color.WHITE);
        btnFechar.setForeground(marromEscuro);
        btnFechar.setBorder(new LineBorder(corGold, 1));
        btnFechar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnFechar.setFocusPainted(false);
        p.add(btnFechar);

        btnFechar.addActionListener(e -> this.dispose());
    }
}