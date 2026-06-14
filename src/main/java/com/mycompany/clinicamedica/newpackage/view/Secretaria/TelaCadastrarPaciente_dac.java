package com.mycompany.clinicamedica.newpackage.view.Secretaria;

import Services.BDSConnection;
import Services.Paciente;
import Services.PacienteDAO;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelaCadastrarPaciente_dac extends JFrame {

    private JTextField txtNome, txtCPF, txtTelefone, txtNascimento, txtEndereco, txtCarteirinha;
    private JComboBox<String> cbSexo, cbConvenio;
    private JButton btnSalvar, btnLimpar, btnVoltar;

    private final Color corCremeClaro   = new Color(251, 251, 250);
    private final Color corDestaqueGold = new Color(193, 158, 103);
    private final Color corTomMedio     = new Color(110, 102, 95);
    private final Color corRotuloCinza  = new Color(180, 169, 158);
    private final Color corMarromEscuro = new Color(61, 28, 6);

    public TelaCadastrarPaciente_dac() {
        setTitle("🏥 Sistema Clínica Médica - Novo Cadastro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 750);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(corTomMedio);
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        JLabel lblTitulo = new JLabel("Cadastro de Novo Paciente");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(50, 30, 500, 40);
        painelFundo.add(lblTitulo);

        JPanel painelForm = new JPanel();
        painelForm.setBackground(corMarromEscuro);
        painelForm.setLayout(null);
        painelForm.setBounds(50, 100, 780, 550);
        painelForm.setBorder(new LineBorder(corDestaqueGold, 1, true));
        painelFundo.add(painelForm);

        Font fonteLabel = new Font("Segoe UI", Font.PLAIN, 14);

        // --- Nome Completo ---
        JLabel lblNome = new JLabel("Nome Completo:");
        lblNome.setFont(fonteLabel);
        lblNome.setForeground(corRotuloCinza);
        lblNome.setBounds(40, 25, 200, 20);
        painelForm.add(lblNome);

        txtNome = criarCampoTexto();
        txtNome.setBounds(40, 48, 700, 35);
        painelForm.add(txtNome);

        // --- CPF | Telefone ---
        JLabel lblCPF = new JLabel("CPF:");
        lblCPF.setFont(fonteLabel);
        lblCPF.setForeground(corRotuloCinza);
        lblCPF.setBounds(40, 100, 100, 20);
        painelForm.add(lblCPF);

        txtCPF = criarCampoTexto();
        txtCPF.setBounds(40, 123, 330, 35);
        painelForm.add(txtCPF);

        JLabel lblTelefone = new JLabel("Telefone / WhatsApp:");
        lblTelefone.setFont(fonteLabel);
        lblTelefone.setForeground(corRotuloCinza);
        lblTelefone.setBounds(410, 100, 200, 20);
        painelForm.add(lblTelefone);

        txtTelefone = criarCampoTexto();
        txtTelefone.setBounds(410, 123, 330, 35);
        painelForm.add(txtTelefone);

        // --- Data Nascimento | Sexo ---
        JLabel lblNasc = new JLabel("Data de Nascimento:");
        lblNasc.setFont(fonteLabel);
        lblNasc.setForeground(corRotuloCinza);
        lblNasc.setBounds(40, 175, 200, 20);
        painelForm.add(lblNasc);

        txtNascimento = criarCampoTexto();
        txtNascimento.setBounds(40, 198, 330, 35);
        painelForm.add(txtNascimento);

        JLabel lblSexo = new JLabel("Sexo:");
        lblSexo.setFont(fonteLabel);
        lblSexo.setForeground(corRotuloCinza);
        lblSexo.setBounds(410, 175, 100, 20);
        painelForm.add(lblSexo);

        cbSexo = new JComboBox<>(new String[]{"Selecione", "Masculino", "Feminino", "Outro"});
        cbSexo.setBounds(410, 198, 330, 35);
        estilizarCombo(cbSexo);
        painelForm.add(cbSexo);

        // --- Endereço ---
        JLabel lblEnd = new JLabel("Endereço Residencial:");
        lblEnd.setFont(fonteLabel);
        lblEnd.setForeground(corRotuloCinza);
        lblEnd.setBounds(40, 250, 200, 20);
        painelForm.add(lblEnd);

        txtEndereco = criarCampoTexto();
        txtEndereco.setBounds(40, 273, 700, 35);
        painelForm.add(txtEndereco);

        // --- Convênio | Carteirinha ---
        JLabel lblConvenio = new JLabel("Convênio:");
        lblConvenio.setFont(fonteLabel);
        lblConvenio.setForeground(corRotuloCinza);
        lblConvenio.setBounds(40, 325, 200, 20);
        painelForm.add(lblConvenio);

        cbConvenio = new JComboBox<>();
        cbConvenio.setBounds(40, 348, 330, 35);
        estilizarCombo(cbConvenio);
        painelForm.add(cbConvenio);
        carregarConvenios();

        JLabel lblCarteirinha = new JLabel("Número da Carteirinha:");
        lblCarteirinha.setFont(fonteLabel);
        lblCarteirinha.setForeground(corRotuloCinza);
        lblCarteirinha.setBounds(410, 325, 220, 20);
        painelForm.add(lblCarteirinha);

        txtCarteirinha = criarCampoTexto();
        txtCarteirinha.setBounds(410, 348, 330, 35);
        painelForm.add(txtCarteirinha);

        // --- Botões ---
        btnSalvar = new JButton("Salvar Cadastro");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.setBackground(corDestaqueGold);
        btnSalvar.setForeground(corMarromEscuro);
        btnSalvar.setBounds(580, 460, 160, 45);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setOpaque(true);
        btnSalvar.setBorderPainted(true);
        btnSalvar.setBorder(new LineBorder(corDestaqueGold.darker(), 1));
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelForm.add(btnSalvar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLimpar.setBackground(corTomMedio);
        btnLimpar.setForeground(corCremeClaro);
        btnLimpar.setBounds(400, 460, 160, 45);
        btnLimpar.setFocusPainted(false);
        btnLimpar.setOpaque(true);
        btnLimpar.setBorderPainted(true);
        btnLimpar.setBorder(new LineBorder(corRotuloCinza, 1));
        btnLimpar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelForm.add(btnLimpar);

        btnVoltar = new JButton("← Voltar ao Menu");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setBackground(new Color(180, 70, 70));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(50, 670, 160, 35);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setOpaque(true);
        btnVoltar.setBorderPainted(true);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnVoltar);

        // ====================================================================
        // EVENTOS
        // ====================================================================
        btnSalvar.addActionListener(e -> salvarPaciente());

        btnLimpar.addActionListener(e -> {
            txtNome.setText("");
            txtCPF.setText("");
            txtTelefone.setText("");
            txtNascimento.setText("");
            txtEndereco.setText("");
            txtCarteirinha.setText("");
            cbSexo.setSelectedIndex(0);
            cbConvenio.setSelectedIndex(0);
        });

        btnVoltar.addActionListener(e -> this.dispose());
    }

    // ====================================================================
    // RENDERER CUSTOMIZADO PARA COMBOBOX
    // ====================================================================
    private void estilizarCombo(JComboBox<String> combo) {
        combo.setBackground(corTomMedio);
        combo.setForeground(corCremeClaro);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBorder(new LineBorder(corDestaqueGold, 1));
        combo.setOpaque(true);

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setOpaque(true);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                label.setBorder(new EmptyBorder(0, 8, 0, 0));

                if (isSelected) {
                    label.setBackground(corDestaqueGold);
                    label.setForeground(corMarromEscuro);
                } else {
                    label.setBackground(corTomMedio);
                    label.setForeground(corCremeClaro);
                }
                return label;
            }
        });
    }

    private void carregarConvenios() {
        cbConvenio.addItem("Sem convênio");
        String sql = "SELECT nome FROM convenio ORDER BY nome";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cbConvenio.addItem(rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar convênios: " + e.getMessage());
        }
    }

    private void salvarPaciente() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, insira o nome completo do paciente.",
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cbSexo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                "Por favor, selecione o sexo do paciente.",
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Paciente paciente = new Paciente();
        paciente.setNome(txtNome.getText().trim());
        paciente.setCpf(txtCPF.getText().trim());
        paciente.setTelefone(txtTelefone.getText().trim());
        paciente.setDataNascimento(txtNascimento.getText().trim());
        paciente.setEndereco(txtEndereco.getText().trim());
        paciente.setSexo(cbSexo.getSelectedItem().toString());
        paciente.setNumeroCarteirinha(txtCarteirinha.getText().trim());

        String convenioSelecionado = cbConvenio.getSelectedItem().toString();
        if (!"Sem convênio".equals(convenioSelecionado)) {
            PacienteDAO dao = new PacienteDAO();
            int idConvenio = dao.buscarIdConvenio(convenioSelecionado);
            paciente.setIdConvenio(idConvenio);
        }

        PacienteDAO dao = new PacienteDAO();
        dao.inserir(paciente);

        JOptionPane.showMessageDialog(this,
            "Paciente " + paciente.getNome() + " cadastrado com sucesso!",
            "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        this.dispose();
    }

    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField();
        campo.setBackground(corTomMedio);
        campo.setForeground(corCremeClaro);
        campo.setCaretColor(corCremeClaro);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(corTomMedio.brighter(), 1),
                new EmptyBorder(0, 10, 0, 10)
        ));
        return campo;
    }

    public static void main(String[] args) {
        try {
            // CrossPlatform respeita as cores definidas no código
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> new TelaCadastrarPaciente_dac().setVisible(true));
    }
}