package com.mycompany.clinicamedica.newpackage.view.Medico;

import com.mycompany.clinicamedica.newpackage.view.ui.Tema;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TelaEmitirReceita extends JFrame {
    public TelaEmitirReceita(String nomePaciente, String nomeMedico) {
        setTitle("Health Equilibrium - Emissor de Documentos Digitais");
        setSize(550, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        Color marromEscuro  = Tema.MARROM_ESCURO;
        Color corGold       = Tema.GOLD;
        Color fundoClaro    = Tema.FUNDO_CLARO;

        JPanel p = new JPanel(null);
        p.setBackground(fundoClaro);
        setContentPane(p);

        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 550, 70);
        header.setBackground(marromEscuro);
        p.add(header);

        JLabel lblTitulo = new JLabel("PRESCRIÇÃO DIGITAL & ATESTADOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(20, 22, 510, 25);
        header.add(lblTitulo);

        JLabel lblInfo = new JLabel("Paciente Selecionado: " + nomePaciente);
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblInfo.setForeground(marromEscuro);
        lblInfo.setBounds(30, 90, 490, 20);
        p.add(lblInfo);

        // Seleção de Tipo de Documento
        JLabel lblTipo = new JLabel("Tipo de Emissão:");
        lblTipo.setBounds(30, 125, 120, 25);
        p.add(lblTipo);

        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Receita Médica Comum", "Receita de Controle Especial", "Atestado Médico de Afastamento"});
        cbTipo.setBounds(150, 125, 370, 25);
        cbTipo.setBorder(new LineBorder(corGold));
        p.add(cbTipo);

        // Corpo do Texto
        JLabel lblCorpo = new JLabel("Prescrição / Justificativa Médica:");
        lblCorpo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCorpo.setBounds(30, 175, 490, 20);
        p.add(lblCorpo);

        JTextArea txtTexto = new JTextArea();
        txtTexto.setText("Exemplo:\n1. Paracetamol 750mg ----------- 1 cp de 6h em 6h se dor ou febre.\n2. Amoxicilina 500mg ----------- 1 cp de 8h em 8h por 7 dias.");
        txtTexto.setLineWrap(true);
        txtTexto.setWrapStyleWord(true);
        JScrollPane scrollTexto = new JScrollPane(txtTexto);
        scrollTexto.setBounds(30, 200, 490, 230);
        scrollTexto.setBorder(new LineBorder(corGold, 1));
        p.add(scrollTexto);

        // Botão Emitir
        JButton btnImprimir = new JButton("EMITIR E IMPRIMIR DOCUMENTO VIA RECONHECIMENTO");
        btnImprimir.setBounds(30, 455, 490, 45);
        btnImprimir.setBackground(marromEscuro);
        btnImprimir.setForeground(Color.WHITE);
        btnImprimir.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnImprimir.setFocusPainted(false);
        p.add(btnImprimir);

        btnImprimir.addActionListener(e -> {
            String doc = cbTipo.getSelectedItem().toString();
            JOptionPane.showMessageDialog(this, doc + " gerada com sucesso para o paciente " + nomePaciente + "!\nEmitido por: Dr(a). " + nomeMedico, "Documento Emitido", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        });
    }
}