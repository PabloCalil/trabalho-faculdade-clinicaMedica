package com.mycompany.clinicamedica.newpackage.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TelaCadastrarEspecialidade extends JFrame {
    private JTextField txtNovaEsp;
    private DefaultTableModel modelo;

    public TelaCadastrarEspecialidade() {
        setTitle("Configurações - Áreas Clínicas");
        setSize(560, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color marromEscuro = new Color(61, 28, 6);
        Color corGold      = new Color(193, 158, 103);

        JPanel principal = new JPanel(new BorderLayout(0, 15));
        principal.setBackground(new Color(244, 241, 234));
        principal.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(principal);

        // --- ENTRADA DE DADOS ---
        JPanel topo = new JPanel(new BorderLayout(10, 0));
        topo.setBackground(marromEscuro);
        topo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(corGold, 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel lbl = new JLabel("Nova Especialidade: ");
        lbl.setForeground(Color.WHITE);
        txtNovaEsp = new JTextField();
        txtNovaEsp.setBackground(new Color(110, 102, 95));
        txtNovaEsp.setForeground(Color.WHITE);
        txtNovaEsp.setCaretColor(Color.WHITE);

        topo.add(lbl, BorderLayout.WEST);
        topo.add(txtNovaEsp, BorderLayout.CENTER);

        JButton btnAdd = new JButton("Adicionar");
        btnAdd.setBackground(corGold);
        btnAdd.setForeground(marromEscuro);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 12));
        topo.add(btnAdd, BorderLayout.EAST);
        principal.add(topo, BorderLayout.NORTH);

        // --- TABELA DE ÁREAS MÉDICAS ---
        String[] col = {"Especialidades Operacionais Ativas"};
        modelo = new DefaultTableModel(col, 0);
        JTable tabela = new JTable(modelo);
        tabela.setRowHeight(28);
        
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(corGold));
        principal.add(scroll, BorderLayout.CENTER);

        atualizarTabela();

        btnAdd.addActionListener(e -> {
            String nova = txtNovaEsp.getText().trim();
            if(nova.isEmpty()) return;

            if(GerenciadorAutenticacao.listaEspecialidades.contains(nova)) {
                JOptionPane.showMessageDialog(this, "Especialidade já listada no banco.");
                return;
            }

            GerenciadorAutenticacao.listaEspecialidades.add(nova);
            atualizarTabela();
            txtNovaEsp.setText("");
        });
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        for(String esp : GerenciadorAutenticacao.listaEspecialidades) {
            modelo.addRow(new Object[]{esp});
        }
    }
}