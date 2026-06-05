package com.mycompany.clinicamedica.newpackage.view; // <-- Ajustado para o seu pacote real!

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelaCadastrarPaciente extends JFrame {

    // Campos do Formulário
    private JTextField txtNome, txtCPF, txtTelefone, txtNascimento, txtEndereco;
    private JComboBox<String> cbSexo;
    private JButton btnSalvar, btnLimpar, btnVoltar;

    public TelaCadastrarPaciente() {
        setTitle("🏥 Sistema Clínica Médica - Novo Cadastro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
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
        JLabel lblTitulo = new JLabel("Cadastro de Novo Paciente");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(50, 30, 500, 40);
        painelFundo.add(lblTitulo);

        // ====================================================================
        // FORMULÁRIO (Painel Central Escuro)
        // ====================================================================
        JPanel painelForm = new JPanel();
        painelForm.setBackground(corMarromEscuro);
        painelForm.setLayout(null);
        painelForm.setBounds(50, 100, 780, 450);
        painelForm.setBorder(new LineBorder(corDestaqueGold, 1, true));
        painelFundo.add(painelForm);

        Font fonteLabel = new Font("Segoe UI", Font.PLAIN, 14);
        
        // --- Linha 1: Nome Completo ---
        JLabel lblNome = new JLabel("Nome Completo:");
        lblNome.setFont(fonteLabel);
        lblNome.setForeground(corRotuloCinza);
        lblNome.setBounds(40, 30, 200, 20);
        painelForm.add(lblNome);

        txtNome = criarCampoTexto(corTomMedio, corCremeClaro);
        txtNome.setBounds(40, 55, 700, 35);
        painelForm.add(txtNome);

        // --- Linha 2: CPF e Telefone ---
        JLabel lblCPF = new JLabel("CPF:");
        lblCPF.setFont(fonteLabel);
        lblCPF.setForeground(corRotuloCinza);
        lblCPF.setBounds(40, 110, 100, 20);
        painelForm.add(lblCPF);

        txtCPF = criarCampoTexto(corTomMedio, corCremeClaro);
        txtCPF.setBounds(40, 135, 330, 35);
        painelForm.add(txtCPF);

        JLabel lblTelefone = new JLabel("Telefone / WhatsApp:");
        lblTelefone.setFont(fonteLabel);
        lblTelefone.setForeground(corRotuloCinza);
        lblTelefone.setBounds(410, 110, 200, 20);
        painelForm.add(lblTelefone);

        txtTelefone = criarCampoTexto(corTomMedio, corCremeClaro);
        txtTelefone.setBounds(410, 135, 330, 35);
        painelForm.add(txtTelefone);

        // --- Linha 3: Data Nascimento e Sexo ---
        JLabel lblNasc = new JLabel("Data de Nascimento:");
        lblNasc.setFont(fonteLabel);
        lblNasc.setForeground(corRotuloCinza);
        lblNasc.setBounds(40, 190, 200, 20);
        painelForm.add(lblNasc);

        txtNascimento = criarCampoTexto(corTomMedio, corCremeClaro);
        txtNascimento.setBounds(40, 215, 330, 35);
        painelForm.add(txtNascimento);

        JLabel lblSexo = new JLabel("Sexo:");
        lblSexo.setFont(fonteLabel);
        lblSexo.setForeground(corRotuloCinza);
        lblSexo.setBounds(410, 190, 100, 20);
        painelForm.add(lblSexo);

        cbSexo = new JComboBox<>(new String[]{"Selecione", "Masculino", "Feminino", "Outro"});
        cbSexo.setBounds(410, 215, 330, 35);
        cbSexo.setBackground(corTomMedio);
        cbSexo.setForeground(corCremeClaro);
        cbSexo.setBorder(new LineBorder(corDestaqueGold, 1));
        painelForm.add(cbSexo);

        // --- Linha 4: Endereço ---
        JLabel lblEnd = new JLabel("Endereço Residencial:");
        lblEnd.setFont(fonteLabel);
        lblEnd.setForeground(corRotuloCinza);
        lblEnd.setBounds(40, 270, 200, 20);
        painelForm.add(lblEnd);

        txtEndereco = criarCampoTexto(corTomMedio, corCremeClaro);
        txtEndereco.setBounds(40, 295, 700, 35);
        painelForm.add(txtEndereco);

        // ====================================================================
        // BOTÕES DE AÇÃO DO FORMULÁRIO
        // ====================================================================
        btnSalvar = new JButton("Salvar Cadastro");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.setBackground(corDestaqueGold);
        btnSalvar.setForeground(corMarromEscuro);
        btnSalvar.setBounds(580, 370, 160, 45);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorder(new LineBorder(corDestaqueGold.darker(), 1));
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelForm.add(btnSalvar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLimpar.setBackground(corTomMedio);
        btnLimpar.setForeground(corCremeClaro);
        btnLimpar.setBounds(400, 370, 160, 45);
        btnLimpar.setFocusPainted(false);
        btnLimpar.setBorder(new LineBorder(corRotuloCinza, 1));
        btnLimpar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelForm.add(btnLimpar);

        // Botão Voltar (Alocado na parte inferior esquerda externa)
        btnVoltar = new JButton("← Voltar ao Menu");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setBackground(new Color(180, 70, 70));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(50, 580, 160, 35);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnVoltar);

        // ====================================================================
        // COMPORTAMENTO / EVENTOS
        // ====================================================================
        btnSalvar.addActionListener(e -> {
            if(txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, insira o nome completo do paciente.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Paciente " + txtNome.getText() + " gravado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
        });

        btnLimpar.addActionListener(e -> {
            txtNome.setText("");
            txtCPF.setText("");
            txtTelefone.setText("");
            txtNascimento.setText("");
            txtEndereco.setText("");
            cbSexo.setSelectedIndex(0);
        });

        btnVoltar.addActionListener(e -> this.dispose());
    }

    /**
     * Auxiliar técnico para padronizar os campos de inserção sem erros
     */
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
            new TelaCadastrarPaciente().setVisible(true);
        });
    }
}