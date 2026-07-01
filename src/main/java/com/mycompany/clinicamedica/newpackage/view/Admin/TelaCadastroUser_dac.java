package com.mycompany.clinicamedica.newpackage.view.Admin;

import Services.BDSConnection;
import Services.UsuarioDAO;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

public class TelaCadastroUser_dac extends JFrame {

    private JTextField txtNovoUsuario, txtNomeCompleto, txtEndereco, txtCrm;
    private JFormattedTextField txtCpf, txtTelefone;
    private JLabel lblCrm, lblEspecialidade;
    private JComboBox<String> cbEspecialidade;
    private JPasswordField txtNovaSenha;
    private JComboBox<String> cbPerfil;
    private JButton btnSalvarUsuario, btnNovaEspecialidade, btnVoltar;

    private final Color corCremeClaro   = new Color(251, 251, 250);
    private final Color corDestaqueGold = new Color(193, 158, 103);
    private final Color corTomMedio     = new Color(110, 102, 95);
    private final Color corRotuloCinza  = new Color(180, 169, 158);
    private final Color corMarromEscuro = new Color(61, 28, 6);

    public TelaCadastroUser_dac() {
        setTitle("Health Equilibrium - Controle de Acessos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(corTomMedio);
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        JLabel lblTitulo = new JLabel("Gerenciamento de Usuários e Perfis");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(50, 25, 700, 40);
        painelFundo.add(lblTitulo);

        JPanel painelCadastro = new JPanel();
        painelCadastro.setBackground(corMarromEscuro);
        painelCadastro.setBounds(50, 85, 790, 560);
        painelCadastro.setLayout(null);
        painelCadastro.setBorder(new LineBorder(corDestaqueGold, 1, true));
        painelFundo.add(painelCadastro);

        Font fonteLabel = new Font("Segoe UI", Font.PLAIN, 14);

        // --- Nome Completo ---
        JLabel lblNome = new JLabel("Nome Completo:");
        lblNome.setFont(fonteLabel);
        lblNome.setForeground(corRotuloCinza);
        lblNome.setBounds(30, 25, 200, 20);
        painelCadastro.add(lblNome);

        txtNomeCompleto = criarCampo();
        txtNomeCompleto.setBounds(30, 48, 730, 35);
        painelCadastro.add(txtNomeCompleto);

        // --- CPF | Telefone ---
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setFont(fonteLabel);
        lblCpf.setForeground(corRotuloCinza);
        lblCpf.setBounds(30, 100, 100, 20);
        painelCadastro.add(lblCpf);

        txtCpf = criarCampoMascara("###.###.###-##");
        txtCpf.setBounds(30, 123, 340, 35);
        painelCadastro.add(txtCpf);

        JLabel lblTel = new JLabel("Telefone:");
        lblTel.setFont(fonteLabel);
        lblTel.setForeground(corRotuloCinza);
        lblTel.setBounds(400, 100, 200, 20);
        painelCadastro.add(lblTel);

        txtTelefone = criarCampoMascara("(##) # ####-####");
        txtTelefone.setBounds(400, 123, 360, 35);
        painelCadastro.add(txtTelefone);

        // --- Endereço ---
        JLabel lblEnd = new JLabel("Endereço:");
        lblEnd.setFont(fonteLabel);
        lblEnd.setForeground(corRotuloCinza);
        lblEnd.setBounds(30, 175, 200, 20);
        painelCadastro.add(lblEnd);

        txtEndereco = criarCampo();
        txtEndereco.setBounds(30, 198, 730, 35);
        painelCadastro.add(txtEndereco);

        // --- Usuário | Senha ---
        JLabel lblUser = new JLabel("Nome de Usuário (Login):");
        lblUser.setFont(fonteLabel);
        lblUser.setForeground(corRotuloCinza);
        lblUser.setBounds(30, 250, 220, 20);
        painelCadastro.add(lblUser);

        txtNovoUsuario = criarCampo();
        txtNovoUsuario.setBounds(30, 273, 340, 35);
        painelCadastro.add(txtNovoUsuario);

        JLabel lblPass = new JLabel("Senha de Acesso:");
        lblPass.setFont(fonteLabel);
        lblPass.setForeground(corRotuloCinza);
        lblPass.setBounds(400, 250, 200, 20);
        painelCadastro.add(lblPass);

        txtNovaSenha = new JPasswordField();
        txtNovaSenha.setBounds(400, 273, 360, 35);
        txtNovaSenha.setBackground(corTomMedio);
        txtNovaSenha.setForeground(corCremeClaro);
        txtNovaSenha.setCaretColor(corCremeClaro);
        txtNovaSenha.setBorder(new LineBorder(corDestaqueGold, 1));
        painelCadastro.add(txtNovaSenha);

        // --- Perfil ---
        JLabel lblPerfil = new JLabel("Perfil / Nível de Acesso:");
        lblPerfil.setFont(fonteLabel);
        lblPerfil.setForeground(corRotuloCinza);
        lblPerfil.setBounds(30, 325, 220, 20);
        painelCadastro.add(lblPerfil);

        cbPerfil = new JComboBox<>(new String[]{"Selecione o Perfil...", "Médico", "Secretária", "Administrador"});
        cbPerfil.setBounds(30, 348, 340, 35);
        cbPerfil.setBackground(corTomMedio);
        cbPerfil.setForeground(corCremeClaro);
        cbPerfil.setBorder(new LineBorder(corDestaqueGold, 1));
        painelCadastro.add(cbPerfil);

        // --- CRM | Especialidade (apenas Médico) ---
        lblCrm = new JLabel("CRM:");
        lblCrm.setFont(fonteLabel);
        lblCrm.setForeground(corDestaqueGold);
        lblCrm.setBounds(30, 400, 100, 20);
        lblCrm.setVisible(false);
        painelCadastro.add(lblCrm);

        txtCrm = criarCampo();
        txtCrm.setBounds(30, 423, 340, 35);
        txtCrm.setVisible(false);
        painelCadastro.add(txtCrm);

        lblEspecialidade = new JLabel("Especialidade:");
        lblEspecialidade.setFont(fonteLabel);
        lblEspecialidade.setForeground(corDestaqueGold);
        lblEspecialidade.setBounds(400, 400, 200, 20);
        lblEspecialidade.setVisible(false);
        painelCadastro.add(lblEspecialidade);

        cbEspecialidade = new JComboBox<>();
        cbEspecialidade.setBounds(400, 423, 360, 35);
        cbEspecialidade.setBackground(corTomMedio);
        cbEspecialidade.setForeground(corCremeClaro);
        cbEspecialidade.setBorder(new LineBorder(corDestaqueGold, 1));
        cbEspecialidade.setVisible(false);
        painelCadastro.add(cbEspecialidade);

        // --- Botões ---
        btnSalvarUsuario = new JButton("Vincular Perfil");
        btnSalvarUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvarUsuario.setBackground(corDestaqueGold);
        btnSalvarUsuario.setForeground(corMarromEscuro);
        btnSalvarUsuario.setBounds(530, 495, 220, 45);
        btnSalvarUsuario.setFocusPainted(false);
        btnSalvarUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvarUsuario.setBorder(new LineBorder(corDestaqueGold.darker(), 1));
        painelCadastro.add(btnSalvarUsuario);

        btnNovaEspecialidade = new JButton("+ Especialidade");
        btnNovaEspecialidade.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnNovaEspecialidade.setBackground(corTomMedio);
        btnNovaEspecialidade.setForeground(corCremeClaro);
        btnNovaEspecialidade.setBounds(295, 495, 220, 45);
        btnNovaEspecialidade.setFocusPainted(false);
        btnNovaEspecialidade.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNovaEspecialidade.setBorder(new LineBorder(corDestaqueGold, 1));
        btnNovaEspecialidade.setVisible(false);
        painelCadastro.add(btnNovaEspecialidade);

        btnVoltar = new JButton("Voltar ao Menu Admin");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setBackground(new Color(110, 102, 95).darker());
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(50, 660, 200, 35);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnVoltar);

        // ====================================================================
        // EVENTOS
        // ====================================================================
        cbPerfil.addActionListener(e -> {
            boolean ehMedico = "Médico".equals(cbPerfil.getSelectedItem());
            lblCrm.setVisible(ehMedico);
            txtCrm.setVisible(ehMedico);
            lblEspecialidade.setVisible(ehMedico);
            cbEspecialidade.setVisible(ehMedico);
            btnNovaEspecialidade.setVisible(ehMedico);
            if (ehMedico) carregarEspecialidades();
            painelCadastro.repaint();
        });

        btnNovaEspecialidade.addActionListener(e -> abrirPopupNovaEspecialidade());

        btnSalvarUsuario.addActionListener(e -> {
            String nome     = txtNomeCompleto.getText().trim();
            String cpf      = txtCpf.getText().replaceAll("[^0-9]", "");
            String telefone = txtTelefone.getText().replaceAll("[^0-9]", "");
            String endereco = txtEndereco.getText().trim();
            String login    = txtNovoUsuario.getText().trim();
            String senha    = new String(txtNovaSenha.getPassword()).trim();
            int perfilIdx   = cbPerfil.getSelectedIndex();

            if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty()
                    || endereco.isEmpty() || login.isEmpty()
                    || senha.isEmpty() || perfilIdx == 0) {
                JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos obrigatórios e selecione um perfil.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String perfil = cbPerfil.getSelectedItem().toString();
            String crm = "";
            int idEspecialidade = -1;

            if ("Médico".equals(perfil)) {
                crm = txtCrm.getText().trim();
                if (crm.isEmpty() || cbEspecialidade.getSelectedIndex() < 0) {
                    JOptionPane.showMessageDialog(this,
                        "Para perfil Médico, preencha o CRM e selecione a Especialidade.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                UsuarioDAO dao = new UsuarioDAO();
                idEspecialidade = dao.buscarIdEspecialidade(
                    cbEspecialidade.getSelectedItem().toString()
                );
            }

            UsuarioDAO dao = new UsuarioDAO();
            boolean sucesso = dao.inserir(nome, login, senha, perfil,
                                          cpf, telefone, endereco,
                                          crm, idEspecialidade);

            if (sucesso) {
                JOptionPane.showMessageDialog(this,
                    "Usuário '" + login + "' cadastrado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar usuário. Verifique os dados e tente novamente.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> this.dispose());
    }

    private void carregarEspecialidades() {
        cbEspecialidade.removeAllItems();
        String sql = "SELECT nome FROM especialidade ORDER BY nome";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cbEspecialidade.addItem(rs.getString("nome"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar especialidades: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirPopupNovaEspecialidade() {
        JDialog popup = new JDialog(this, "Nova Especialidade", true);
        popup.setSize(420, 280);
        popup.setLocationRelativeTo(this);
        popup.setResizable(false);

        JPanel painel = new JPanel();
        painel.setBackground(corMarromEscuro);
        painel.setLayout(null);
        painel.setBorder(new LineBorder(corDestaqueGold, 1));
        popup.setContentPane(painel);

        Font fonteLabel = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel lblNome = new JLabel("Nome da Especialidade:");
        lblNome.setFont(fonteLabel);
        lblNome.setForeground(corRotuloCinza);
        lblNome.setBounds(25, 20, 220, 20);
        painel.add(lblNome);

        JTextField txtNomeEsp = new JTextField();
        txtNomeEsp.setBounds(25, 45, 360, 35);
        txtNomeEsp.setBackground(corTomMedio);
        txtNomeEsp.setForeground(corCremeClaro);
        txtNomeEsp.setCaretColor(corCremeClaro);
        txtNomeEsp.setBorder(new LineBorder(corDestaqueGold, 1));
        painel.add(txtNomeEsp);

        JLabel lblDesc = new JLabel("Descrição:");
        lblDesc.setFont(fonteLabel);
        lblDesc.setForeground(corRotuloCinza);
        lblDesc.setBounds(25, 95, 200, 20);
        painel.add(lblDesc);

        JTextField txtDescEsp = new JTextField();
        txtDescEsp.setBounds(25, 118, 360, 35);
        txtDescEsp.setBackground(corTomMedio);
        txtDescEsp.setForeground(corCremeClaro);
        txtDescEsp.setCaretColor(corCremeClaro);
        txtDescEsp.setBorder(new LineBorder(corDestaqueGold, 1));
        painel.add(txtDescEsp);

        JButton btnSalvarEsp = new JButton("Salvar Especialidade");
        btnSalvarEsp.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSalvarEsp.setBackground(corDestaqueGold);
        btnSalvarEsp.setForeground(corMarromEscuro);
        btnSalvarEsp.setBounds(25, 175, 360, 40);
        btnSalvarEsp.setFocusPainted(false);
        btnSalvarEsp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painel.add(btnSalvarEsp);

        btnSalvarEsp.addActionListener(e -> {
            String nome = txtNomeEsp.getText().trim();
            String desc = txtDescEsp.getText().trim();
            if (nome.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(popup,
                    "Preencha o nome e a descrição.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String sql = "INSERT INTO especialidade (nome, descricao) VALUES (?, ?)";
            try (Connection conn = BDSConnection.getConexao();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nome);
                stmt.setString(2, desc);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(popup,
                    "Especialidade '" + nome + "' cadastrada!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                popup.dispose();
                carregarEspecialidades();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(popup,
                    "Erro ao salvar: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        popup.setVisible(true);
    }

    private void limparCampos() {
        txtNomeCompleto.setText("");
        txtCpf.setValue(null);
        txtTelefone.setValue(null);
        txtEndereco.setText("");
        txtNovoUsuario.setText("");
        txtNovaSenha.setText("");
        txtCrm.setText("");
        cbEspecialidade.removeAllItems();
        cbPerfil.setSelectedIndex(0);
    }

    private JTextField criarCampo() {
        JTextField campo = new JTextField();
        campo.setBackground(corTomMedio);
        campo.setForeground(corCremeClaro);
        campo.setCaretColor(corCremeClaro);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(corDestaqueGold, 1),
                new EmptyBorder(0, 8, 0, 8)
        ));
        return campo;
    }

    private JFormattedTextField criarCampoMascara(String mascara) {
        try {
            MaskFormatter mf = new MaskFormatter(mascara);
            mf.setPlaceholderCharacter('_');
            JFormattedTextField campo = new JFormattedTextField(mf);
            campo.setBackground(corTomMedio);
            campo.setForeground(corCremeClaro);
            campo.setCaretColor(corCremeClaro);
            campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            campo.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(corDestaqueGold, 1),
                    new EmptyBorder(0, 8, 0, 8)
            ));
            return campo;
        } catch (Exception e) {
            return new JFormattedTextField();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastroUser_dac().setVisible(true));
    }
}