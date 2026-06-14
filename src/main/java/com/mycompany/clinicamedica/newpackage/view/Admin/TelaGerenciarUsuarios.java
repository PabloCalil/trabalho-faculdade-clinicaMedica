package com.mycompany.clinicamedica.newpackage.view.Admin;

import Services.BDSConnection;
import Services.Usuario;
import Services.UsuarioDAO;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class TelaGerenciarUsuarios extends JFrame {

    private final UsuarioDAO dao = new UsuarioDAO();

    // Filtros
    private JTextField txtFiltro;
    private JComboBox<String> cbFiltroPerfil, cbFiltroAtivo;

    // Tabela
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    // Edição
    private JTextField txtNome, txtLogin, txtEndereco, txtCrm;
    private JFormattedTextField txtCpf, txtTelefone;
    private JComboBox<String> cbPerfil, cbEspecialidade;
    private JLabel lblCrm, lblEspecialidade;
    private JButton btnSalvar, btnAtivar, btnSenha, btnVoltar;

    private Integer idSelecionado = null;
    private Map<String, Integer> mapaEspecialidades = new LinkedHashMap<>();

    private final Color corCremeClaro   = new Color(251, 251, 250);
    private final Color corDestaqueGold = new Color(193, 158, 103);
    private final Color corTomMedio     = new Color(110, 102, 95);
    private final Color corRotuloCinza  = new Color(180, 169, 158);
    private final Color corMarromEscuro = new Color(61, 28, 6);
    private final Color corVermelho     = new Color(180, 70, 70);
    private final Color corVerde        = new Color(60, 140, 60);

    public TelaGerenciarUsuarios() {
        setTitle("🏥 Sistema Clínica Médica - Gerenciar Usuários");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(corTomMedio);
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        // Título
        JLabel lblTitulo = new JLabel("Gerenciar Usuários");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(30, 20, 500, 40);
        painelFundo.add(lblTitulo);

        // ================================================================
        // PAINEL ESQUERDO — filtros + tabela
        // ================================================================
        JPanel painelLista = new JPanel();
        painelLista.setBackground(corMarromEscuro);
        painelLista.setLayout(null);
        painelLista.setBounds(20, 75, 540, 570);
        painelLista.setBorder(new LineBorder(corDestaqueGold, 1, true));
        painelFundo.add(painelLista);

        Font fonteLabel = new Font("Segoe UI", Font.PLAIN, 13);

        // Filtro texto
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(fonteLabel);
        lblBuscar.setForeground(corRotuloCinza);
        lblBuscar.setBounds(15, 15, 60, 20);
        painelLista.add(lblBuscar);

        txtFiltro = criarCampo();
        txtFiltro.setBounds(15, 38, 240, 32);
        painelLista.add(txtFiltro);

        // Filtro perfil
        JLabel lblFPerfil = new JLabel("Perfil:");
        lblFPerfil.setFont(fonteLabel);
        lblFPerfil.setForeground(corRotuloCinza);
        lblFPerfil.setBounds(270, 15, 60, 20);
        painelLista.add(lblFPerfil);

        cbFiltroPerfil = new JComboBox<>(new String[]{"Todos", "Médico", "Secretária", "Administrador"});
        cbFiltroPerfil.setBounds(270, 38, 120, 32);
        estilizarCombo(cbFiltroPerfil);
        painelLista.add(cbFiltroPerfil);

        // Filtro ativo
        JLabel lblFAtivo = new JLabel("Status:");
        lblFAtivo.setFont(fonteLabel);
        lblFAtivo.setForeground(corRotuloCinza);
        lblFAtivo.setBounds(405, 15, 60, 20);
        painelLista.add(lblFAtivo);

        cbFiltroAtivo = new JComboBox<>(new String[]{"Todos", "Ativos", "Inativos"});
        cbFiltroAtivo.setBounds(405, 38, 115, 32);
        estilizarCombo(cbFiltroAtivo);
        painelLista.add(cbFiltroAtivo);

        // Tabela
        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Login", "Perfil", "Ativo"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(26);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setBackground(corCremeClaro);
        tabela.setSelectionBackground(corDestaqueGold);
        tabela.setSelectionForeground(corMarromEscuro);
        tabela.getTableHeader().setBackground(corMarromEscuro);
        tabela.getTableHeader().setForeground(corDestaqueGold);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.getColumnModel().getColumn(0).setMaxWidth(40);
        tabela.getColumnModel().getColumn(4).setMaxWidth(55);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(15, 85, 510, 465);
        scroll.setBorder(new LineBorder(corDestaqueGold, 1));
        painelLista.add(scroll);

        // ================================================================
        // PAINEL DIREITO — edição
        // ================================================================
        JPanel painelEdicao = new JPanel();
        painelEdicao.setBackground(corMarromEscuro);
        painelEdicao.setLayout(null);
        painelEdicao.setBounds(575, 75, 600, 570);
        painelEdicao.setBorder(new LineBorder(corDestaqueGold, 1, true));
        painelFundo.add(painelEdicao);

        JLabel lblEdicaoTitulo = new JLabel("Dados do Usuário Selecionado");
        lblEdicaoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEdicaoTitulo.setForeground(corDestaqueGold);
        lblEdicaoTitulo.setBounds(20, 15, 400, 25);
        painelEdicao.add(lblEdicaoTitulo);

        // Nome | Login
        JLabel lblNome = new JLabel("Nome Completo:");
        lblNome.setFont(fonteLabel); lblNome.setForeground(corRotuloCinza);
        lblNome.setBounds(20, 55, 200, 20);
        painelEdicao.add(lblNome);

        txtNome = criarCampo();
        txtNome.setBounds(20, 78, 270, 32);
        painelEdicao.add(txtNome);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setFont(fonteLabel); lblLogin.setForeground(corRotuloCinza);
        lblLogin.setBounds(310, 55, 100, 20);
        painelEdicao.add(lblLogin);

        txtLogin = criarCampo();
        txtLogin.setBounds(310, 78, 265, 32);
        painelEdicao.add(txtLogin);

        // CPF | Telefone
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setFont(fonteLabel); lblCpf.setForeground(corRotuloCinza);
        lblCpf.setBounds(20, 125, 100, 20);
        painelEdicao.add(lblCpf);

        txtCpf = criarCampoMascara("###.###.###-##");
        txtCpf.setBounds(20, 148, 270, 32);
        painelEdicao.add(txtCpf);

        JLabel lblTel = new JLabel("Telefone:");
        lblTel.setFont(fonteLabel); lblTel.setForeground(corRotuloCinza);
        lblTel.setBounds(310, 125, 100, 20);
        painelEdicao.add(lblTel);

        txtTelefone = criarCampoMascara("(##) # ####-####");
        txtTelefone.setBounds(310, 148, 265, 32);
        painelEdicao.add(txtTelefone);

        // Endereço
        JLabel lblEnd = new JLabel("Endereço:");
        lblEnd.setFont(fonteLabel); lblEnd.setForeground(corRotuloCinza);
        lblEnd.setBounds(20, 195, 200, 20);
        painelEdicao.add(lblEnd);

        txtEndereco = criarCampo();
        txtEndereco.setBounds(20, 218, 555, 32);
        painelEdicao.add(txtEndereco);

        // Perfil
        JLabel lblPerfil = new JLabel("Perfil:");
        lblPerfil.setFont(fonteLabel); lblPerfil.setForeground(corRotuloCinza);
        lblPerfil.setBounds(20, 265, 100, 20);
        painelEdicao.add(lblPerfil);

        cbPerfil = new JComboBox<>(new String[]{"Médico", "Secretária", "Administrador"});
        cbPerfil.setBounds(20, 288, 270, 32);
        estilizarCombo(cbPerfil);
        painelEdicao.add(cbPerfil);

        // CRM | Especialidade (apenas médico)
        lblCrm = new JLabel("CRM:");
        lblCrm.setFont(fonteLabel); lblCrm.setForeground(corDestaqueGold);
        lblCrm.setBounds(20, 335, 100, 20);
        lblCrm.setVisible(false);
        painelEdicao.add(lblCrm);

        txtCrm = criarCampo();
        txtCrm.setBounds(20, 358, 270, 32);
        txtCrm.setVisible(false);
        painelEdicao.add(txtCrm);

        lblEspecialidade = new JLabel("Especialidade:");
        lblEspecialidade.setFont(fonteLabel); lblEspecialidade.setForeground(corDestaqueGold);
        lblEspecialidade.setBounds(310, 335, 200, 20);
        lblEspecialidade.setVisible(false);
        painelEdicao.add(lblEspecialidade);

        cbEspecialidade = new JComboBox<>();
        cbEspecialidade.setBounds(310, 358, 265, 32);
        cbEspecialidade.setVisible(false);
        estilizarCombo(cbEspecialidade);
        painelEdicao.add(cbEspecialidade);

        // Botões de ação
        btnSalvar = criarBotao("Salvar Alterações", corDestaqueGold, corMarromEscuro);
        btnSalvar.setBounds(310, 490, 255, 42);
        btnSalvar.setEnabled(false);
        painelEdicao.add(btnSalvar);

        btnAtivar = criarBotao("Inativar", corVermelho, Color.WHITE);
        btnAtivar.setBounds(160, 490, 135, 42);
        btnAtivar.setEnabled(false);
        painelEdicao.add(btnAtivar);

        btnSenha = criarBotao("Alterar Senha", corTomMedio, corCremeClaro);
        btnSenha.setBounds(20, 490, 130, 42);
        btnSenha.setEnabled(false);
        painelEdicao.add(btnSenha);

        // Botão voltar
        btnVoltar = criarBotao("← Voltar", corTomMedio.darker(), Color.WHITE);
        btnVoltar.setBounds(20, 635, 150, 35);
        btnVoltar.setEnabled(true);
        painelFundo.add(btnVoltar);

        // ================================================================
        // EVENTOS
        // ================================================================
        carregarEspecialidades();
        carregarTabela();
        limparSelecao();

        txtFiltro.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { carregarTabela(); }
        });

        cbFiltroPerfil.addActionListener(e -> carregarTabela());
        cbFiltroAtivo.addActionListener(e -> carregarTabela());

        cbPerfil.addActionListener(e -> atualizarVisibilidadeMedico());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) carregarSelecionado();
        });

        btnSalvar.addActionListener(e -> salvar());
        btnAtivar.addActionListener(e -> alternarAtivo());
        btnSenha.addActionListener(e -> alterarSenha());
        btnVoltar.addActionListener(e -> this.dispose());
    }

    // ================================================================
    // CARREGAR ESPECIALIDADES
    // ================================================================
    private void carregarEspecialidades() {
        mapaEspecialidades.clear();
        cbEspecialidade.removeAllItems();
        String sql = "SELECT idEspecialidade, nome FROM especialidade ORDER BY nome";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nome = rs.getString("nome");
                int id = rs.getInt("idEspecialidade");
                mapaEspecialidades.put(nome, id);
                cbEspecialidade.addItem(nome);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar especialidades: " + e.getMessage());
        }
    }

    // ================================================================
    // CARREGAR TABELA
    // ================================================================
    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        String texto  = txtFiltro.getText().trim();
        String perfil = cbFiltroPerfil.getSelectedIndex() == 0 ? null
                      : cbFiltroPerfil.getSelectedItem().toString();
        Integer ativo = null;
        if (cbFiltroAtivo.getSelectedIndex() == 1) ativo = 1;
        if (cbFiltroAtivo.getSelectedIndex() == 2) ativo = 0;

        List<Usuario> lista = dao.listar(texto, perfil, ativo);
        for (Usuario u : lista) {
            modeloTabela.addRow(new Object[]{
                u.getIdUsuario(), u.getNome(), u.getLogin(),
                u.getPerfil(), u.isAtivo() ? "Sim" : "Não"
            });
        }
    }

    // ================================================================
    // CARREGAR SELECIONADO
    // ================================================================
    private void carregarSelecionado() {
        int row = tabela.getSelectedRow();
        if (row < 0) { limparSelecao(); return; }

        int id = (int) modeloTabela.getValueAt(row, 0);
        Usuario u = dao.buscarPorId(id);
        if (u == null) return;

        idSelecionado = u.getIdUsuario();
        txtNome.setText(u.getNome());
        txtLogin.setText(u.getLogin());
        txtEndereco.setText(u.getEndereco() != null ? u.getEndereco() : "");
        txtCrm.setText(u.getCrm() != null ? u.getCrm() : "");

        try { txtCpf.setText(u.getCpf() != null ? u.getCpf() : ""); }
        catch (Exception ex) { txtCpf.setValue(u.getCpf()); }

        try { txtTelefone.setText(u.getTelefone() != null ? u.getTelefone() : ""); }
        catch (Exception ex) { txtTelefone.setValue(u.getTelefone()); }

        cbPerfil.setSelectedItem(u.getPerfil());

        if (u.getIdEspecialidade() > 0) {
            for (Map.Entry<String, Integer> entry : mapaEspecialidades.entrySet()) {
                if (entry.getValue() == u.getIdEspecialidade()) {
                    cbEspecialidade.setSelectedItem(entry.getKey());
                    break;
                }
            }
        }

        atualizarVisibilidadeMedico();

        boolean ativo = u.isAtivo();
        btnAtivar.setText(ativo ? "Inativar" : "Ativar");
        btnAtivar.setBackground(ativo ? corVermelho : corVerde);

        btnSalvar.setEnabled(true);
        btnAtivar.setEnabled(true);
        btnSenha.setEnabled(true);
    }

    private void limparSelecao() {
        idSelecionado = null;
        txtNome.setText(""); txtLogin.setText("");
        txtEndereco.setText(""); txtCrm.setText("");
        txtCpf.setValue(null); txtTelefone.setValue(null);
        cbPerfil.setSelectedIndex(0);
        atualizarVisibilidadeMedico();
        btnSalvar.setEnabled(false);
        btnAtivar.setEnabled(false);
        btnSenha.setEnabled(false);
        btnAtivar.setText("Inativar");
        btnAtivar.setBackground(corVermelho);
    }

    private void atualizarVisibilidadeMedico() {
        boolean medico = "Médico".equals(cbPerfil.getSelectedItem());
        lblCrm.setVisible(medico);
        txtCrm.setVisible(medico);
        lblEspecialidade.setVisible(medico);
        cbEspecialidade.setVisible(medico);
    }

    // ================================================================
    // AÇÕES
    // ================================================================
    private void salvar() {
        if (idSelecionado == null) return;

        Usuario u = new Usuario();
        u.setIdUsuario(idSelecionado);
        u.setNome(txtNome.getText().trim());
        u.setLogin(txtLogin.getText().trim());
        u.setEndereco(txtEndereco.getText().trim());
        u.setCpf(txtCpf.getText().replaceAll("[^0-9]", ""));
        u.setTelefone(txtTelefone.getText().replaceAll("[^0-9]", ""));
        u.setPerfil(cbPerfil.getSelectedItem().toString());

        if ("Médico".equals(u.getPerfil())) {
            u.setCrm(txtCrm.getText().trim());
            String espNome = cbEspecialidade.getSelectedItem() != null
                           ? cbEspecialidade.getSelectedItem().toString() : "";
            u.setIdEspecialidade(mapaEspecialidades.getOrDefault(espNome, 0));

            if (u.getCrm().isEmpty() || u.getIdEspecialidade() == 0) {
                JOptionPane.showMessageDialog(this,
                    "Informe CRM e Especialidade para perfil Médico.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (dao.atualizar(u)) {
            JOptionPane.showMessageDialog(this,
                "Usuário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao atualizar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alternarAtivo() {
        if (idSelecionado == null) return;
        boolean novoEstado = "Ativar".equals(btnAtivar.getText());
        String acao = novoEstado ? "ativar" : "inativar";

        int op = JOptionPane.showConfirmDialog(this,
            "Deseja realmente " + acao + " este usuário?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op != JOptionPane.YES_OPTION) return;

        if (dao.setAtivo(idSelecionado, novoEstado)) {
            carregarTabela();
            limparSelecao();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao alterar status.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarSenha() {
        if (idSelecionado == null) return;
        JPasswordField pf = new JPasswordField();
        pf.setPreferredSize(new Dimension(250, 30));
        int op = JOptionPane.showConfirmDialog(this, pf,
            "Digite a nova senha:", JOptionPane.OK_CANCEL_OPTION);
        if (op != JOptionPane.OK_OPTION) return;

        String nova = new String(pf.getPassword()).trim();
        if (nova.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Senha não pode ser vazia.");
            return;
        }
        if (dao.alterarSenha(idSelecionado, nova)) {
            JOptionPane.showMessageDialog(this,
                "Senha alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao alterar senha.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ================================================================
    // AUXILIARES DE UI
    // ================================================================
    private JTextField criarCampo() {
        JTextField campo = new JTextField();
        campo.setBackground(corTomMedio);
        campo.setForeground(corCremeClaro);
        campo.setCaretColor(corCremeClaro);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
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
            campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            campo.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(corDestaqueGold, 1),
                    new EmptyBorder(0, 8, 0, 8)
            ));
            return campo;
        } catch (Exception e) {
            return new JFormattedTextField();
        }
    }

    private void estilizarCombo(JComboBox<String> combo) {
        combo.setBackground(corTomMedio);
        combo.setForeground(corCremeClaro);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBorder(new LineBorder(corDestaqueGold, 1));
        combo.setOpaque(true);
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setOpaque(true);
                label.setBorder(new EmptyBorder(0, 8, 0, 0));
                label.setBackground(isSelected ? corDestaqueGold : corTomMedio);
                label.setForeground(isSelected ? corMarromEscuro : corCremeClaro);
                return label;
            }
        });
    }

    private JButton criarBotao(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new LineBorder(bg.darker(), 1));
        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new TelaGerenciarUsuarios().setVisible(true));
    }
}