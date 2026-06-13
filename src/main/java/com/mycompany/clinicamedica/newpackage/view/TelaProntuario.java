package com.mycompany.clinicamedica.newpackage.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class TelaProntuario extends JFrame {
    
    public TelaProntuario(String nomePaciente, String nomeMedico) {
        setTitle("VITA v2.0 - Prontuário Eletrônico e Anamnese");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        Color marromEscuro  = new Color(61, 28, 6);
        Color corGold       = new Color(193, 158, 103);
        Color fundoClaro    = new Color(244, 241, 234);

        JPanel p = new JPanel(null);
        p.setBackground(fundoClaro);
        setContentPane(p);

        // --- CABEÇALHO CORPORATIVO ---
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

        JLabel lblSub = new JLabel("Paciente: " + nomePaciente.toUpperCase() + "  |  Médico Assistente: Dr(a). " + nomeMedico);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(corGold);
        lblSub.setBounds(30, 43, 700, 20);
        header.add(lblSub);

        // --- BLOCO 1: HISTÓRICO CLÍNICO DO PACIENTE (HDA E ANTECEDENTES) ---
        JPanel pnlHistorico = new JPanel(null);
        pnlHistorico.setBounds(30, 100, 725, 160);
        pnlHistorico.setBackground(fundoClaro);
        pnlHistorico.setBorder(BorderFactory.createTitledBorder(new LineBorder(corGold), "1. Histórico e Antecedentes", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), marromEscuro));
        p.add(pnlHistorico);

        JLabel l1 = new JLabel("Queixa Principal e HDA (Histórico da Doença Atual):");
        l1.setBounds(15, 20, 320, 20);
        pnlHistorico.add(l1);
        
        JTextArea txtHda = new JTextArea();
        txtHda.setLineWrap(true); txtHda.setWrapStyleWord(true);
        JScrollPane scrHda = new JScrollPane(txtHda);
        scrHda.setBounds(15, 45, 330, 95);
        pnlHistorico.add(scrHda);

        JLabel l2 = new JLabel("HPP / Alergias / Medicamentos em Uso continuamente:");
        l2.setBounds(375, 20, 320, 20);
        pnlHistorico.add(l2);

        JTextArea txtHpp = new JTextArea();
        txtHpp.setLineWrap(true); txtHpp.setWrapStyleWord(true);
        JScrollPane scrHpp = new JScrollPane(txtHpp);
        scrHpp.setBounds(375, 45, 330, 95);
        pnlHistorico.add(scrHpp);

        // --- BLOCO 2: SINAIS VITAIS E EXAME FÍSICO ---
        JPanel pnlExame = new JPanel(null);
        pnlExame.setBounds(30, 275, 725, 140);
        pnlExame.setBackground(fundoClaro);
        pnlExame.setBorder(BorderFactory.createTitledBorder(new LineBorder(corGold), "2. Triagem Sinais Vitais e Exame Físico", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), marromEscuro));
        p.add(pnlExame);

        JLabel lPA = new JLabel("P.A. (mmHg):"); lPA.setBounds(20, 25, 90, 20); pnlExame.add(lPA);
        JTextField tPA = new JTextField("120/80"); tPA.setBounds(20, 45, 90, 25); pnlExame.add(tPA);

        JLabel lFC = new JLabel("F.C. (bpm):"); lFC.setBounds(130, 25, 90, 20); pnlExame.add(lFC);
        JTextField tFC = new JTextField("72"); tFC.setBounds(130, 45, 90, 25); pnlExame.add(tFC);

        JLabel lTemp = new JLabel("Temp. (°C):"); lTemp.setBounds(240, 25, 90, 20); pnlExame.add(lTemp);
        JTextField tTemp = new JTextField("36.5"); tTemp.setBounds(240, 45, 90, 25); pnlExame.add(tTemp);

        JLabel lDescExame = new JLabel("Anotações Gerais do Exame Clínico / Biomicroscopia / Fundoscopia:");
        lDescExame.setBounds(375, 15, 330, 20);
        pnlExame.add(lDescExame);

        JTextArea txtExameFisico = new JTextArea();
        txtExameFisico.setLineWrap(true); txtExameFisico.setWrapStyleWord(true);
        JScrollPane scrExame = new JScrollPane(txtExameFisico);
        scrExame.setBounds(375, 40, 330, 80);
        pnlExame.add(scrExame);

        // --- BLOCO 3: CONCLUSÃO, DIAGNÓSTICO E CONDUTA ---
        JPanel pnlConduta = new JPanel(null);
        pnlConduta.setBounds(30, 430, 725, 150);
        pnlConduta.setBackground(fundoClaro);
        pnlConduta.setBorder(BorderFactory.createTitledBorder(new LineBorder(corGold), "3. Impressão Diagnóstica e Conduta Terapêutica", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12), marromEscuro));
        p.add(pnlConduta);

        JLabel lCid = new JLabel("Hipótese Diagnóstica Primária (CID-10):");
        lCid.setBounds(15, 25, 300, 20);
        pnlConduta.add(lCid);
        JTextField tCid = new JTextField();
        tCid.setBounds(15, 45, 330, 25);
        pnlConduta.add(tCid);

        JLabel lCondutaTexto = new JLabel("Plano de Tratamento / Prescrição Ambulatorial / Recomendações:");
        lCondutaTexto.setBounds(375, 20, 330, 20);
        pnlConduta.add(lCondutaTexto);

        JTextArea txtConduta = new JTextArea();
        txtConduta.setLineWrap(true); txtConduta.setWrapStyleWord(true);
        JScrollPane scrConduta = new JScrollPane(txtConduta);
        scrConduta.setBounds(375, 45, 330, 90);
        pnlConduta.add(scrConduta);

        // --- BOTÃO DE FECHAMENTO ASSINADO ---
        JButton btnFinalizar = new JButton("CONCLUIR ATENDIMENTO E ASSINAR PRONTUÁRIO ELETRÔNICO");
        btnFinalizar.setBounds(30, 600, 725, 45);
        btnFinalizar.setBackground(corGold);
        btnFinalizar.setForeground(marromEscuro);
        btnFinalizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnFinalizar.setFocusPainted(false);
        p.add(btnFinalizar);

        btnFinalizar.addActionListener(e -> {
            if(txtHda.getText().trim().isEmpty() || txtConduta.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Campos obrigatórios de evolução clínica (HDA e Conduta) não preenchidos.", "Aviso de Segurança", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Prontuário salvo no banco VITA corporativo.\nSinais vitais processados com sucesso.", "Sessão Encerrada", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        });
    }
}