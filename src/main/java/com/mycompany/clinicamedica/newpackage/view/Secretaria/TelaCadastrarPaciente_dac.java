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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

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
        setTitle("Health Equilibrium - Novo Cadastro");
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
        aplicarFiltroDigitos(txtCPF, 11);
        painelForm.add(txtCPF);

        JLabel lblContCPF = criarContador("0/11 dígitos");
        lblContCPF.setBounds(40, 160, 200, 16);
        painelForm.add(lblContCPF);
        vincularContador(txtCPF, lblContCPF, 11);

        JLabel lblTelefone = new JLabel("Telefone / WhatsApp:");
        lblTelefone.setFont(fonteLabel);
        lblTelefone.setForeground(corRotuloCinza);
        lblTelefone.setBounds(410, 100, 200, 20);
        painelForm.add(lblTelefone);

        txtTelefone = criarCampoTexto();
        txtTelefone.setBounds(410, 123, 330, 35);
        aplicarFiltroDigitos(txtTelefone, 11);
        painelForm.add(txtTelefone);

        JLabel lblContTel = criarContador("0/11 dígitos");
        lblContTel.setBounds(410, 160, 220, 16);
        painelForm.add(lblContTel);
        vincularContador(txtTelefone, lblContTel, 11);

        // --- Data Nascimento | Sexo ---
        JLabel lblNasc = new JLabel("Data de Nascimento:");
        lblNasc.setFont(fonteLabel);
        lblNasc.setForeground(corRotuloCinza);
        lblNasc.setBounds(40, 175, 200, 20);
        painelForm.add(lblNasc);

        txtNascimento = criarCampoTexto();
        txtNascimento.setBounds(40, 198, 330, 35);
        aplicarFiltroDigitos(txtNascimento, 8);
        painelForm.add(txtNascimento);

        JLabel lblContNasc = criarContador("0/8 dígitos  (DDMMAAAA)");
        lblContNasc.setBounds(40, 235, 260, 16);
        painelForm.add(lblContNasc);
        vincularContador(txtNascimento, lblContNasc, 8);

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
        lblEnd.setBounds(40, 262, 200, 20);
        painelForm.add(lblEnd);

        txtEndereco = criarCampoTexto();
        txtEndereco.setBounds(40, 285, 700, 35);
        painelForm.add(txtEndereco);

        // --- Convênio | Carteirinha ---
        JLabel lblConvenio = new JLabel("Convênio:");
        lblConvenio.setFont(fonteLabel);
        lblConvenio.setForeground(corRotuloCinza);
        lblConvenio.setBounds(40, 337, 200, 20);
        painelForm.add(lblConvenio);

        cbConvenio = new JComboBox<>();
        cbConvenio.setBounds(40, 360, 330, 35);
        estilizarCombo(cbConvenio);
        painelForm.add(cbConvenio);
        carregarConvenios();

        JLabel lblCarteirinha = new JLabel("Número da Carteirinha:");
        lblCarteirinha.setFont(fonteLabel);
        lblCarteirinha.setForeground(corRotuloCinza);
        lblCarteirinha.setBounds(410, 337, 220, 20);
        painelForm.add(lblCarteirinha);

        txtCarteirinha = criarCampoTexto();
        txtCarteirinha.setBounds(410, 360, 330, 35);
        aplicarFiltroDigitos(txtCarteirinha, 20);
        painelForm.add(txtCarteirinha);

        JLabel lblContCart = criarContador("0/20 dígitos");
        lblContCart.setBounds(410, 397, 200, 16);
        painelForm.add(lblContCart);
        vincularContador(txtCarteirinha, lblContCart, 20);

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

        btnVoltar = new JButton("Voltar ao Menu");
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

        String cpf = txtCPF.getText().trim();
        if (cpf.length() != 11) {
            JOptionPane.showMessageDialog(this,
                "CPF deve ter exatamente 11 dígitos. Você inseriu " + cpf.length() + ".",
                "CPF inválido", JOptionPane.ERROR_MESSAGE);
            txtCPF.requestFocus();
            return;
        }

        String telefone = txtTelefone.getText().trim();
        if (telefone.length() < 10 || telefone.length() > 11) {
            JOptionPane.showMessageDialog(this,
                "Telefone deve ter 10 ou 11 dígitos (com DDD). Você inseriu " + telefone.length() + ".",
                "Telefone inválido", JOptionPane.ERROR_MESSAGE);
            txtTelefone.requestFocus();
            return;
        }

        String nascRaw = txtNascimento.getText().trim();
        if (nascRaw.length() != 8) {
            JOptionPane.showMessageDialog(this,
                "Data de nascimento deve ter 8 dígitos (DDMMAAAA). Você inseriu " + nascRaw.length() + ".",
                "Data inválida", JOptionPane.ERROR_MESSAGE);
            txtNascimento.requestFocus();
            return;
        }
        String nascFormatado = nascRaw.substring(0, 2) + "/" + nascRaw.substring(2, 4) + "/" + nascRaw.substring(4, 8);
        try {
            LocalDate.parse(nascFormatado, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Data de nascimento inválida: " + nascFormatado + "\nVerifique dia, mês e ano.",
                "Data inválida", JOptionPane.ERROR_MESSAGE);
            txtNascimento.requestFocus();
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
        paciente.setDataNascimento(nascFormatado);
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

    private void aplicarFiltroDigitos(JTextField campo, int maxDigitos) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                    throws BadLocationException {
                if (text == null) return;
                String digitos = text.replaceAll("\\D", "");
                int restante = maxDigitos - fb.getDocument().getLength();
                if (restante > 0)
                    super.insertString(fb, offset, digitos.substring(0, Math.min(digitos.length(), restante)), attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text == null) text = "";
                String digitos = text.replaceAll("\\D", "");
                int restante = maxDigitos - (fb.getDocument().getLength() - length);
                if (restante > 0)
                    super.replace(fb, offset, length, digitos.substring(0, Math.min(digitos.length(), restante)), attrs);
            }
        });
    }

    private JLabel criarContador(String textoInicial) {
        JLabel lbl = new JLabel(textoInicial);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(corRotuloCinza);
        return lbl;
    }

    private void vincularContador(JTextField campo, JLabel contador, int max) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            private void atualizar() {
                int len = campo.getText().length();
                contador.setText(len + "/" + max + " dígitos");
                contador.setForeground(len == max ? new Color(80, 160, 80) :
                                       len  > 0   ? corRotuloCinza : corRotuloCinza);
            }
            @Override public void insertUpdate(DocumentEvent e)  { atualizar(); }
            @Override public void removeUpdate(DocumentEvent e)  { atualizar(); }
            @Override public void changedUpdate(DocumentEvent e) { atualizar(); }
        });
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