package com.mycompany.clinicamedica.newpackage.view; // <-- Ajustado estritamente para a sua árvore de ficheiros!

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelaSecretaria extends JFrame {

    private JButton btnCadastrarPaciente;
    private JButton btnConsultarPaciente;
    private JButton btnMarcarConsulta;
    private JButton btnConsultarConsultas;
    private JButton btnVoltar;

    public TelaSecretaria() {
        setTitle("🏥 Sistema Clínica Médica - Painel da Secretaria");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setResizable(false);

        // PALETA DE CORES TERROSAS VALIDADAS
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

        // Cabeçalho institucional
        JLabel lblTitulo = new JLabel("Painel de Controle Administrativo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(60, 40, 600, 50);
        painelFundo.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Módulo da Secretaria — Gerenciamento e Atendimento");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitulo.setForeground(corRotuloCinza);
        lblSubtitulo.setBounds(60, 90, 500, 25);
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

        // Botões do Menu em formato HTML estruturado
        btnCadastrarPaciente = configurarBotaoMenu("<html><center><font size='6'>➕</font><br><br><b>Cadastrar Paciente</b><br><font size='3'>Inserir novas fichas no sistema</font></center></html>", corDestaqueGold, corMarromEscuro);
        painelMenu.add(btnCadastrarPaciente);

        btnConsultarPaciente = configurarBotaoMenu("<html><center><font size='6'>🔍</font><br><br><b>Consultar Paciente</b><br><font size='3'>Buscar cadastros e históricos</font></center></html>", corDestaqueGold, corMarromEscuro);
        painelMenu.add(btnConsultarPaciente);

        btnMarcarConsulta = configurarBotaoMenu("<html><center><font size='6'>📅</font><br><br><b>Marcar Consulta / Retorno</b><br><font size='3'>Agendar novos horários médicos</font></center></html>", corDestaqueGold, corMarromEscuro);
        painelMenu.add(btnMarcarConsulta);

        btnConsultarConsultas = configurarBotaoMenu("<html><center><font size='6'>📋</font><br><br><b>Consultar Consultas</b><br><font size='3'>Visualizar fluxo da agenda do dia</font></center></html>", corDestaqueGold, corMarromEscuro);
        painelMenu.add(btnConsultarConsultas);

        // Botão de Logout / Voltar
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
        // MAPEAMENTO DOS DIRECIONAMENTOS (Caminhos Corretos)
        // ====================================================================
        
        // 1. Aponta para TelaCadastrarPaciente
        btnCadastrarPaciente.addActionListener(e -> {
            new TelaCadastrarPaciente().setVisible(true);
        });

        // 2. Aponta para TelaConsultarPaciente
        btnConsultarPaciente.addActionListener(e -> {
            new TelaConsultarPaciente().setVisible(true);
        });

        // 3. Aponta para TelaAgendarConsulta
        btnMarcarConsulta.addActionListener(e -> {
            new TelaAgendarConsulta().setVisible(true);
        });

        // 4. Fluxo da Agenda do Dia
        // Procure e altere para este na sua TelaSecretaria.java:
        btnConsultarConsultas.addActionListener(e -> {
            new TelaConsultarConsultas().setVisible(true);
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
            new TelaSecretaria().setVisible(true);
        });
    }
}