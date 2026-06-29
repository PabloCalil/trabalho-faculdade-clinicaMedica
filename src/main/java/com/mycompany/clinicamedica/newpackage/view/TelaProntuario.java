package com.mycompany.clinicamedica.newpackage.view;

import Services.Prontuario;
import Services.ProntuarioDAO;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class TelaProntuario extends JFrame {

    private final ProntuarioDAO dao = new ProntuarioDAO();
    private final int idPaciente;
    private int idProntuarioExistente = -1;

    private JTextArea txtHda, txtHpp, txtExameFisico, txtConduta;
    private JTextField tPA, tFC, tTemp, tCid;

    public TelaProntuario(String nomePaciente, String nomeMedico, int idPaciente) {
        this.idPaciente = idPaciente;

        setTitle("VITA v2.0 - Prontuário Eletrônico e Anamnese");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        Color marromEscuro = new Color(61, 28, 6);
        Color corGold      = new Color(193, 158, 103);
        Color fundoClaro   = new Color(244, 241, 234);

        JPanel p = new JPanel(null);
        p.setBackground(fundoClaro);
        setContentPane(p);

        // --- HEADER ---
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 800, 80);
        header.setBackground(marromEscuro);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, corGold));
        p.add(header);

        JLabel lblTitulo = new JLabel("REGISTRO DE ANAMNESE HOSPITALAR");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 15, 500, 25);
        header.add(lblTitulo);

        JLabel lblSub = new JLabel("Paciente: " + nomePaciente.toUpperCase()
                + "  |  Médico Assistente: Dr(a). " + nomeMedico);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(corGold);
        lblSub.setBounds(30, 43, 700, 20);
        header.add(lblSub);

        // --- BLOCO 1: HISTÓRICO ---
        JPanel pnlHistorico = new JPanel(null);
        pnlHistorico.setBounds(30, 100, 725, 160);
        pnlHistorico.setBackground(fundoClaro);
        pnlHistorico.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(corGold), "1. Histórico e Antecedentes",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), marromEscuro));
        p.add(pnlHistorico);

        JLabel l1 = new JLabel("Queixa Principal e HDA:");
        l1.setBounds(15, 20, 320, 20);
        pnlHistorico.add(l1);

        txtHda = new JTextArea();
        txtHda.setLineWrap(true);
        txtHda.setWrapStyleWord(true);
        JScrollPane scrHda = new JScrollPane(txtHda);
        scrHda.setBounds(15, 45, 330, 95);
        pnlHistorico.add(scrHda);

        JLabel l2 = new JLabel("HPP / Alergias / Medicamentos contínuos:");
        l2.setBounds(375, 20, 320, 20);
        pnlHistorico.add(l2);

        txtHpp = new JTextArea();
        txtHpp.setLineWrap(true);
        txtHpp.setWrapStyleWord(true);
        JScrollPane scrHpp = new JScrollPane(txtHpp);
        scrHpp.setBounds(375, 45, 330, 95);
        pnlHistorico.add(scrHpp);

        // --- BLOCO 2: SINAIS VITAIS ---
        JPanel pnlExame = new JPanel(null);
        pnlExame.setBounds(30, 275, 725, 140);
        pnlExame.setBackground(fundoClaro);
        pnlExame.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(corGold), "2. Sinais Vitais e Exame Físico",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), marromEscuro));
        p.add(pnlExame);

        JLabel lPA = new JLabel("P.A. (mmHg):");
        lPA.setBounds(20, 25, 90, 20);
        pnlExame.add(lPA);
        tPA = new JTextField("120/80");
        tPA.setBounds(20, 45, 90, 25);
        pnlExame.add(tPA);

        JLabel lFC = new JLabel("F.C. (bpm):");
        lFC.setBounds(130, 25, 90, 20);
        pnlExame.add(lFC);
        tFC = new JTextField("72");
        tFC.setBounds(130, 45, 90, 25);
        pnlExame.add(tFC);

        JLabel lTemp = new JLabel("Temp. (°C):");
        lTemp.setBounds(240, 25, 90, 20);
        pnlExame.add(lTemp);
        tTemp = new JTextField("36.5");
        tTemp.setBounds(240, 45, 90, 25);
        pnlExame.add(tTemp);

        JLabel lDescExame = new JLabel("Anotações do Exame Clínico:");
        lDescExame.setBounds(375, 15, 330, 20);
        pnlExame.add(lDescExame);

        txtExameFisico = new JTextArea();
        txtExameFisico.setLineWrap(true);
        txtExameFisico.setWrapStyleWord(true);
        JScrollPane scrExame = new JScrollPane(txtExameFisico);
        scrExame.setBounds(375, 40, 330, 80);
        pnlExame.add(scrExame);

        // --- BLOCO 3: DIAGNÓSTICO ---
        JPanel pnlConduta = new JPanel(null);
        pnlConduta.setBounds(30, 430, 725, 150);
        pnlConduta.setBackground(fundoClaro);
        pnlConduta.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(corGold), "3. Impressão Diagnóstica e Conduta",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), marromEscuro));
        p.add(pnlConduta);

        JLabel lCid = new JLabel("Hipótese Diagnóstica (CID-10):");
        lCid.setBounds(15, 25, 300, 20);
        pnlConduta.add(lCid);
        tCid = new JTextField();
        tCid.setBounds(15, 45, 330, 25);
        pnlConduta.add(tCid);

        JLabel lCondutaTexto = new JLabel("Plano de Tratamento / Recomendações:");
        lCondutaTexto.setBounds(375, 20, 330, 20);
        pnlConduta.add(lCondutaTexto);

        txtConduta = new JTextArea();
        txtConduta.setLineWrap(true);
        txtConduta.setWrapStyleWord(true);
        JScrollPane scrConduta = new JScrollPane(txtConduta);
        scrConduta.setBounds(375, 45, 330, 90);
        pnlConduta.add(scrConduta);

        // --- BOTÃO SALVAR ---
        JButton btnFinalizar = new JButton(
                "CONCLUIR ATENDIMENTO E ASSINAR PRONTUÁRIO ELETRÔNICO");
        btnFinalizar.setBounds(30, 600, 725, 45);
        btnFinalizar.setBackground(corGold);
        btnFinalizar.setForeground(marromEscuro);
        btnFinalizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnFinalizar.setFocusPainted(false);
        btnFinalizar.setOpaque(true);
        btnFinalizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        p.add(btnFinalizar);

        // Carrega prontuário existente se houver
        carregarProntuarioExistente();

        btnFinalizar.addActionListener(e -> salvarProntuario());
    }

    private void carregarProntuarioExistente() {
        Prontuario pron = dao.buscarPorPaciente(idPaciente);
        if (pron != null) {
            idProntuarioExistente = pron.getIdProntuario();
            txtHda.setText(nvl(pron.getHistorico()));
            txtHpp.setText(nvl(pron.getAlergias()));
            tPA.setText(nvl(pron.getPressaoArterial()));
            tFC.setText(nvl(pron.getFrequenciaCardiaca()));
            tTemp.setText(nvl(pron.getTemperatura()));
            txtExameFisico.setText(nvl(pron.getExameFisico()));
            tCid.setText(nvl(pron.getCid10()));
            txtConduta.setText(nvl(pron.getConduta()));
        }
    }

    private void salvarProntuario() {
        if (txtHda.getText().trim().isEmpty() || txtConduta.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Campos obrigatórios (HDA e Conduta) não preenchidos.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Monta o objeto Prontuario com cada campo separado
        Prontuario pron = new Prontuario();
        pron.setIdPaciente(idPaciente);
        pron.setHistorico(txtHda.getText().trim());
        pron.setAlergias(txtHpp.getText().trim());
        pron.setPressaoArterial(tPA.getText().trim());
        pron.setFrequenciaCardiaca(tFC.getText().trim());
        pron.setTemperatura(tTemp.getText().trim());
        pron.setExameFisico(txtExameFisico.getText().trim());
        pron.setCid10(tCid.getText().trim());
        pron.setConduta(txtConduta.getText().trim());

        boolean sucesso;
        if (idProntuarioExistente > 0) {
            pron.setIdProntuario(idProntuarioExistente);
            sucesso = dao.atualizar(pron);
        } else {
            sucesso = dao.inserir(pron);
        }

        if (sucesso) {
            JOptionPane.showMessageDialog(this,
                "Prontuário salvo com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar prontuário.",
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Auxiliar para evitar NullPointerException ao carregar campos
    private String nvl(String valor) {
        return valor != null ? valor : "";
    }
}