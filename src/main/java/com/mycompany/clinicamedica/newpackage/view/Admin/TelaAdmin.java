package com.mycompany.clinicamedica.newpackage.view.Admin;

import com.mycompany.clinicamedica.newpackage.view.TelaLogin;
import com.mycompany.clinicamedica.newpackage.view.ui.Tema;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelaAdmin extends JFrame {
    private Color marromEscuro = Tema.MARROM_ESCURO;
    private Color corGold      = Tema.GOLD;
    private Color fundoClaro   = Tema.FUNDO_CLARO;
    private Color textoEscuro  = Tema.TEXTO_ESCURO;

    public TelaAdmin() {
        setTitle("Health Equilibrium - Painel Administrativo");
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
        
        JLabel lblTitulo = new JLabel("Health Equilibrium — Módulo Master");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        header.add(lblTitulo, BorderLayout.WEST);

        JButton btnSair = Tema.botaoPerigo("Sair do Sistema");
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

        JButton btnUser = criarCardBotao("<html><b>Cadastro de Usuários</b><br><font size='3' color='#8C7E74'>Controle perfis, acessos e destinos</font></html>");
        JButton btnEsp  = criarCardBotao("<html><b>Especialidades Clínicas</b><br><font size='3' color='#8C7E74'>Gerencie as áreas de atendimento médico</font></html>");
        JButton btnFin  = criarCardBotao("<html><b>Cadastro de Convenio</b><br><font size='3' color='#8C7E74'>Convenio Clinico e Parcerias</font></html>");
        JButton btnLog  = criarCardBotao("<html><b>Visão Geral de usuários e funcionários</b><br><font size='3' color='#8C7E74'>Exclusão e Manutenção de Usuários</font></html>");

        // CORRIGIDO: Adicionando um por um sem travar o layout
        gridMenu.add(btnUser); 
        gridMenu.add(btnEsp); 
        gridMenu.add(btnFin); 
        gridMenu.add(btnLog);
        painelPrincipal.add(gridMenu, BorderLayout.CENTER);

        // Ações dos botões
        btnUser.addActionListener(e -> new TelaCadastroUser_dac().setVisible(true));
        btnEsp.addActionListener(e -> new TelaCadastrarEspecialidade().setVisible(true));
        btnFin.addActionListener(e -> new TelaCadastrarConvenio().setVisible(true));
        btnLog.addActionListener(e -> new TelaGerenciarUsuarios().setVisible(true));
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