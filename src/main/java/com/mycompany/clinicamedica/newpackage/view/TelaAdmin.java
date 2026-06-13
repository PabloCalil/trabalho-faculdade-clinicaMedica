package com.mycompany.clinicamedica.newpackage.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelaAdmin extends JFrame {
    private Color marromEscuro = new Color(61, 28, 6);
    private Color corGold      = new Color(193, 158, 103);
    private Color fundoClaro   = new Color(244, 241, 234);
    private Color textoEscuro  = new Color(44, 37, 32);

    public TelaAdmin() {
        setTitle("VITA v2.0 - Painel Administrativo");
        setSize(1024, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(fundoClaro);
        setContentPane(painelPrincipal);

        // --- TOPO/HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(marromEscuro);
        header.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        JLabel lblTitulo = new JLabel("Clínica VITA — Módulo Master");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        header.add(lblTitulo, BorderLayout.WEST);

        JButton btnSair = new JButton("Sair do Sistema");
        btnSair.setBackground(new Color(180, 70, 70));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSair.setFocusPainted(false);
        btnSair.addActionListener(e -> {
            this.dispose();
            new TelaLogin().setVisible(true);
        });
        header.add(btnSair, BorderLayout.EAST);
        painelPrincipal.add(header, BorderLayout.NORTH);

        // --- MENU INTERNO ---
        JPanel gridMenu = new JPanel(new GridLayout(2, 2, 25, 25));
        gridMenu.setBackground(fundoClaro);
        gridMenu.setBorder(new EmptyBorder(40, 40, 40, 40));

        JButton btnUser = criarCardBotao("<html><b>👥 Gestão de Usuários</b><br><font size='3' color='#8C7E74'>Controle perfis, acessos e destinos</font></html>");
        JButton btnEsp  = criarCardBotao("<html><b>⚙️ Especialidades Clínicas</b><br><font size='3' color='#8C7E74'>Gerencie as áreas de atendimento médico</font></html>");
        JButton btnFin  = criarCardBotao("<html><b>📊 Relatórios Master</b><br><font size='3' color='#8C7E74'>Faturamento clínico e repasses</font></html>");
        JButton btnLog  = criarCardBotao("<html><b>📜 Logs de Segurança</b><br><font size='3' color='#8C7E74'>Histórico e rastreio de auditoria</font></html>");

        // CORRIGIDO: Adicionando um por um sem travar o layout
        gridMenu.add(btnUser); 
        gridMenu.add(btnEsp); 
        gridMenu.add(btnFin); 
        gridMenu.add(btnLog);
        painelPrincipal.add(gridMenu, BorderLayout.CENTER);

        // Ações dos botões
        btnUser.addActionListener(e -> new TelaGerenciarUsuarios().setVisible(true));
        btnEsp.addActionListener(e -> new TelaCadastrarEspecialidade().setVisible(true));
        
        btnFin.addActionListener(e -> JOptionPane.showMessageDialog(this, "Acessando relatórios financeiros...", "Módulo Financeiro", JOptionPane.INFORMATION_MESSAGE));
        btnLog.addActionListener(e -> JOptionPane.showMessageDialog(this, "Exibindo trilhas de auditoria...", "Segurança", JOptionPane.INFORMATION_MESSAGE));
    }

    private JButton criarCardBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(corGold, 1, true),
            new EmptyBorder(20, 30, 20, 30)
        ));
        btn.setBackground(Color.WHITE);
        btn.setForeground(textoEscuro);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}