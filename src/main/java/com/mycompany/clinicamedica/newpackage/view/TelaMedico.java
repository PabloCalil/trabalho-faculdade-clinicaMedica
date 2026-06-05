package com.mycompany.clinicamedica.newpackage.view; // <-- Alinhado com a sua árvore de arquivos!

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelaMedico extends JFrame {

    private JButton btnChamarPaciente;
    private JButton btnConsultarProntuario;
    private JButton btnEmitirPrescricao;
    private JButton btnMinhaAgenda;
    private JButton btnVoltar;

    public TelaMedico() {
        setTitle("🏥 Sistema Clínica Médica - Painel do Especialista");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setResizable(false);

        // PALETA DE CORES TERROSAS PADRONIZADA
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

        // Cabeçalho Clínico
        JLabel lblTitulo = new JLabel("Painel de Atendimento Médico");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(60, 40, 600, 50);
        painelFundo.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Módulo do Médico — Prontuários, Consultas e Prescrições");
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

        // Botões do Menu Médico em formato HTML nativo
        btnChamarPaciente = configurarBotaoMenu("<html><center><font size='6'>🔊</font><br><br><b>Chamar Próximo</b><br><font size='3'>Painel de chamada da sala de espera</font></center></html>", corDestaqueGold, corMarromEscuro);
        painelMenu.add(btnChamarPaciente);

        btnConsultarProntuario = configurarBotaoMenu("<html><center><font size='6'>🗂️</font><br><br><b>Histórico & Prontuários</b><br><font size='3'>Buscar fichas clínicas e evolução médica</font></center></html>", corDestaqueGold, corMarromEscuro);
        painelMenu.add(btnConsultarProntuario);

        btnEmitirPrescricao = configurarBotaoMenu("<html><center><font size='6'>✍️</font><br><br><b>Prescrever / Atestados</b><br><font size='3'>Gerar receitas digitais e relatórios</font></center></html>", corDestaqueGold, corMarromEscuro);
        painelMenu.add(btnEmitirPrescricao);

        btnMinhaAgenda = configurarBotaoMenu("<html><center><font size='6'>📆</font><br><br><b>Minha Agenda</b><br><font size='3'>Visualizar pacientes agendados para hoje</font></center></html>", corDestaqueGold, corMarromEscuro);
        painelMenu.add(btnMinhaAgenda);

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
        // COMPORTAMENTOS / EVENTOS DOS BOTÕES
        // ====================================================================
        
        btnChamarPaciente.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Painel: Próximo paciente painelizado na TV da sala de espera.", "Painel de Chamadas", JOptionPane.INFORMATION_MESSAGE);
        });

        btnConsultarProntuario.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Buscando histórico clínico do paciente no banco de dados.", "Prontuário Eletrônico", JOptionPane.INFORMATION_MESSAGE);
        });

        btnEmitirPrescricao.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Carregando o gerador de Receituários, Atestados e Exames.", "Módulo de Prescrição", JOptionPane.INFORMATION_MESSAGE);
        });

        btnMinhaAgenda.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Exibindo as consultas confirmadas para o seu CRM hoje.", "Minha Agenda Diária", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnVoltar.addActionListener(e -> this.dispose());
    }

    /**
     * Auxiliar de estilização dos botões para manter a integridade visual estável no Maven.
     */
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
            new TelaMedico().setVisible(true);
        });
    }
}
