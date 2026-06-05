package com.mycompany.clinicamedica.newpackage.view; // <-- Alinhado estritamente com a sua árvore!

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelaAdmin extends JFrame {

    private JButton btnGerenciarUsuarios;
    private JButton btnRelatoriosFinanceiros;
    private JButton btnConfiguracoes;
    private JButton btnLogsAuditoria;
    private JButton btnVoltar;

    public TelaAdmin() {
        setTitle("🏥 Sistema Clínica Médica - Painel do Administrador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setResizable(false);

        // PALETA DE CORES TERROSAS PADRONIZADA DO PROJETO
        Color corCremeClaro   = new Color(251, 251, 250); // #FBFBFA
        Color corDestaqueGold = new Color(193, 158, 103); // #C19E67
        Color corTomMedio     = new Color(110, 102, 95);  // #6E665F
        Color corRotuloCinza  = new Color(180, 169, 158); // #B4A99E
        Color corMarromEscuro = new Color(61, 28, 6);     // #3D1C06

        // Painel Principal
        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(corTomMedio);
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        // Cabeçalho Administrativo
        JLabel lblTitulo = new JLabel("Painel de Controle do Administrador");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(60, 40, 700, 50);
        painelFundo.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Módulo Master — Gestão de Pessoal, Finanças, Segurança e Infraestrutura");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitulo.setForeground(corRotuloCinza);
        lblSubtitulo.setBounds(60, 90, 650, 25);
        painelFundo.add(lblSubtitulo);

        // ====================================================================
        // PAINEL CENTRAL DO MENU (Grade Modular)
        // ====================================================================
        JPanel painelMenu = new JPanel();
        painelMenu.setBackground(corMarromEscuro);
        painelMenu.setBorder(new LineBorder(corDestaqueGold, 2, true));
        painelMenu.setLayout(new GridLayout(2, 2, 30, 30));
        painelMenu.setBounds(60, 150, 960, 380);
        
        painelMenu.setBorder(BorderFactory.createCompoundBorder(
                painelMenu.getBorder(), 
                new EmptyBorder(25, 25, 25, 25)
        ));
        painelFundo.add(painelMenu);

        // Botões do Menu Administrativo (HTML nativo seguro para compilação Maven)
        btnGerenciarUsuarios = configurarBotaoMenu("<html><center><font size='6'>👥</font><br><br><b>Gerenciar Usuários</b><br><font size='3'>Controlar acessos de Médicos e Secretárias</font></center></html>", corDestaqueGold, corMarromEscuro);
        painelMenu.add(btnGerenciarUsuarios);

        btnRelatoriosFinanceiros = configurarBotaoMenu("<html><center><font size='6'>📊</font><br><br><b>Relatórios & Finanças</b><br><font size='3'>Auditar faturamento e guias de convênio</font></center></html>", corDestaqueGold, corMarromEscuro);
        painelMenu.add(btnRelatoriosFinanceiros);

        btnConfiguracoes = configurarBotaoMenu("<html><center><font size='6'>⚙️</font><br><br><b>Configurações Gerais</b><br><font size='3'>Gerenciar Backups e parâmetros do sistema</font></center></html>", corDestaqueGold, corMarromEscuro);
        painelMenu.add(btnConfiguracoes);

        btnLogsAuditoria = configurarBotaoMenu("<html><center><font size='6'>📜</font><br><br><b>Logs de Auditoria</b><br><font size='3'>Rastrear ações e histórico de segurança</font></center></html>", corDestaqueGold, corMarromEscuro);
        painelMenu.add(btnLogsAuditoria);

        // Botão de Saída
        btnVoltar = new JButton("← Desconectar");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVoltar.setBackground(new Color(180, 70, 70));
        btnVoltar.setForeground(corCremeClaro);
        btnVoltar.setBounds(60, 565, 180, 40);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorder(new LineBorder(Color.WHITE, 1, true));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnVoltar);

        // ====================================================================
        // COMPORTAMENTOS / EVENTOS DOS BOTÕES
        // ====================================================================
        
        // Procure e altere essa ação dentro da sua TelaAdministrador.java:
btnGerenciarUsuarios.addActionListener(e -> {
    new TelaGerenciarUsuarios().setVisible(true);
});

        btnRelatoriosFinanceiros.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Gerando gráficos corporativos de faturamento e fluxo mensal.", "Inteligência Financeira", JOptionPane.INFORMATION_MESSAGE);
        });

        btnConfiguracoes.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Acessando diretórios de banco de dados e rotinas de Backup.", "Infraestrutura de TI", JOptionPane.INFORMATION_MESSAGE);
        });

        btnLogsAuditoria.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Exibindo registro histórico de modificações e acessos dos usuários.", "Auditoria de Segurança", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnVoltar.addActionListener(e -> this.dispose());
    }

    private JButton configurarBotaoMenu(String textoHTML, Color fundo, Color texto) {
        JButton botao = new JButton(textoHTML);
        botao.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        botao.setBackground(fundo);
        botao.setForeground(texto);
        botao.setFocusPainted(false);
        botao.setBorder(new LineBorder(fundo.darker(), 1, true));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new TelaAdmin().setVisible(true);
        });
    }
}