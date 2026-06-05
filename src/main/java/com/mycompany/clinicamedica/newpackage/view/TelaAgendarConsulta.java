package com.mycompany.clinicamedica.newpackage.view; // <-- Ajustado estritamente para o seu pacote real!

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelaAgendarConsulta extends JFrame {

    // Componentes do Formulário
    private JTextField txtPacienteNome, txtDataConsulta, txtHorario;
    private JComboBox<String> cbMedico, cbTipoConsulta;
    private JTextArea txtObservacoes;
    private JButton btnAgendar, btnLimpar, btnVoltar;

    public TelaAgendarConsulta() {
        setTitle("🏥 Sistema Clínica Médica - Agendamento de Consultas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 720);
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
        JLabel lblTitulo = new JLabel("Agendar Nova Consulta");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(50, 25, 500, 40);
        painelFundo.add(lblTitulo);

        // ====================================================================
        // FORMULÁRIO DE AGENDAMENTO (Painel Central Escuro)
        // ====================================================================
        JPanel painelForm = new JPanel();
        painelForm.setBackground(corMarromEscuro);
        painelForm.setLayout(null);
        painelForm.setBounds(50, 90, 780, 500);
        painelForm.setBorder(new LineBorder(corDestaqueGold, 1, true));
        painelFundo.add(painelForm);

        Font fonteLabel = new Font("Segoe UI", Font.PLAIN, 14);

        // --- Linha 1: Nome do Paciente ---
        JLabel lblPaciente = new JLabel("Nome do Paciente:");
        lblPaciente.setFont(fonteLabel);
        lblPaciente.setForeground(corRotuloCinza);
        lblPaciente.setBounds(40, 25, 200, 20);
        painelForm.add(lblPaciente);

        txtPacienteNome = criarCampoTexto(corTomMedio, corCremeClaro);
        txtPacienteNome.setBounds(40, 50, 700, 35);
        painelForm.add(txtPacienteNome);

        // --- Linha 2: Médico Especialista e Tipo ---
        JLabel lblMedico = new JLabel("Médico / Especialista:");
        lblMedico.setFont(fonteLabel);
        lblMedico.setForeground(corRotuloCinza);
        lblMedico.setBounds(40, 105, 200, 20);
        painelForm.add(lblMedico);

        cbMedico = new JComboBox<>(new String[]{
            "Selecione o Médico", 
            "Dr. Arnaldo Silva (Cardiologia)", 
            "Dra. Beatriz Costa (Pediatria)", 
            "Dr. Carlos Eduardo (Clínico Geral)"
        });
        cbMedico.setBounds(40, 130, 330, 35);
        cbMedico.setBackground(corTomMedio);
        cbMedico.setForeground(corCremeClaro);
        cbMedico.setBorder(new LineBorder(corDestaqueGold, 1));
        painelForm.add(cbMedico);

        JLabel lblTipo = new JLabel("Tipo de Agendamento:");
        lblTipo.setFont(fonteLabel);
        lblTipo.setForeground(corRotuloCinza);
        lblTipo.setBounds(410, 105, 200, 20);
        painelForm.add(lblTipo);

        cbTipoConsulta = new JComboBox<>(new String[]{"Consulta Particular", "Retorno", "Convênio Médico"});
        cbTipoConsulta.setBounds(410, 130, 330, 35);
        cbTipoConsulta.setBackground(corTomMedio);
        cbTipoConsulta.setForeground(corCremeClaro);
        cbTipoConsulta.setBorder(new LineBorder(corDestaqueGold, 1));
        painelForm.add(cbTipoConsulta);

        // --- Linha 3: Data e Horário ---
        JLabel lblData = new JLabel("Data da Consulta (DD/MM/AAAA):");
        lblData.setFont(fonteLabel);
        lblData.setForeground(corRotuloCinza);
        lblData.setBounds(40, 185, 250, 20);
        painelForm.add(lblData);

        txtDataConsulta = criarCampoTexto(corTomMedio, corCremeClaro);
        txtDataConsulta.setBounds(40, 210, 330, 35);
        painelForm.add(txtDataConsulta);

        // Preenche com uma data futura padrão sugerida
        txtDataConsulta.setText("25/05/2026");

        JLabel lblHorario = new JLabel("Horário (HH:MM):");
        lblHorario.setFont(fonteLabel);
        lblHorario.setForeground(corRotuloCinza);
        lblHorario.setBounds(410, 185, 200, 20);
        painelForm.add(lblHorario);

        txtHorario = criarCampoTexto(corTomMedio, corCremeClaro);
        txtHorario.setBounds(410, 210, 330, 35);
        painelForm.add(txtHorario);

        // --- Linha 4: Observações / Sintomas ---
        JLabel lblObs = new JLabel("Observações Clínicas / Recomendações:");
        lblObs.setFont(fonteLabel);
        lblObs.setForeground(corRotuloCinza);
        lblObs.setBounds(40, 265, 300, 20);
        painelForm.add(lblObs);

        txtObservacoes = new JTextArea();
        txtObservacoes.setBackground(corTomMedio);
        txtObservacoes.setForeground(corCremeClaro);
        txtObservacoes.setCaretColor(corCremeClaro);
        txtObservacoes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        
        JScrollPane scrollObs = new JScrollPane(txtObservacoes);
        scrollObs.setBounds(40, 290, 700, 100);
        scrollObs.setBorder(new LineBorder(new Color(110, 102, 95).brighter(), 1));
        painelForm.add(scrollObs);

        // ====================================================================
        // BOTÕES DE AÇÃO DO FORMULÁRIO
        // ====================================================================
        btnAgendar = new JButton("Confirmar Agenda");
        btnAgendar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAgendar.setBackground(corDestaqueGold);
        btnAgendar.setForeground(corMarromEscuro);
        btnAgendar.setBounds(580, 425, 160, 45);
        btnAgendar.setFocusPainted(false);
        btnAgendar.setBorder(new LineBorder(corDestaqueGold.darker(), 1));
        btnAgendar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelForm.add(btnAgendar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLimpar.setBackground(corTomMedio);
        btnLimpar.setForeground(corCremeClaro);
        btnLimpar.setBounds(400, 425, 160, 45);
        btnLimpar.setFocusPainted(false);
        btnLimpar.setBorder(new LineBorder(corRotuloCinza, 1));
        btnLimpar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelForm.add(btnLimpar);

        // Botão Externo para Sair / Voltar
        btnVoltar = new JButton("← Voltar ao Menu");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setBackground(new Color(180, 70, 70));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(50, 615, 160, 35);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnVoltar);

        // ====================================================================
        // COMPORTAMENTO / EVENTOS
        // ====================================================================
        btnAgendar.addActionListener(e -> {
            String nome = txtPacienteNome.getText().trim();
            String data = txtDataConsulta.getText().trim();
            String hora = txtHorario.getText().trim();

            if (nome.isEmpty() || cbMedico.getSelectedIndex() == 0 || data.isEmpty() || hora.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o Paciente, Médico, Data e Horário obrigatoriamente.", "Campos em Falta", JOptionPane.ERROR_MESSAGE);
            } else {
                String mensagemSucesso = String.format(
                    "Consulta reservada com sucesso!\n\nPaciente: %s\nMédico: %s\nData: %s às %s", 
                    nome, cbMedico.getSelectedItem(), data, hora
                );
                JOptionPane.showMessageDialog(this, mensagemSucesso, "Sucesso no Agendamento", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
        });

        btnLimpar.addActionListener(e -> {
            txtPacienteNome.setText("");
            txtDataConsulta.setText("25/05/2026");
            txtHorario.setText("");
            txtObservacoes.setText("");
            cbMedico.setSelectedIndex(0);
            cbTipoConsulta.setSelectedIndex(0);
        });

        btnVoltar.addActionListener(e -> this.dispose());
    }

    private JTextField criarCampoTexto(Color fundo, Color texto) {
        JTextField campo = new JTextField();
        campo.setBackground(fundo);
        campo.setForeground(texto);
        campo.setCaretColor(texto);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(110, 102, 95).brighter(), 1),
                new EmptyBorder(0, 10, 0, 10)
        ));
        return campo;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new TelaAgendarConsulta().setVisible(true);
        });
    }
}