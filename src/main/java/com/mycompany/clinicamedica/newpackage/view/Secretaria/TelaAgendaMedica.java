package com.mycompany.clinicamedica.newpackage.view.Secretaria;

import Services.BDSConnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class TelaAgendaMedica extends JFrame {

    private DefaultTableModel modeloTabela;
    private JComboBox<String> cbMedico;
    private JTextField txtDataInicio, txtDataFim;

    private static final Color MARROM = new Color(61, 28, 6);
    private static final Color GOLD   = new Color(193, 158, 103);
    private static final Color MEDIO  = new Color(110, 102, 95);
    private static final Color CREME  = new Color(251, 251, 250);
    private static final Color ROTULO = new Color(180, 169, 158);

    public TelaAgendaMedica() {
        setTitle("🏥 VITA — Agenda Médica");
        setSize(1050, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(MEDIO);
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        JLabel lblTitulo = new JLabel("Agenda Médica");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(CREME);
        lblTitulo.setBounds(40, 20, 400, 40);
        painelFundo.add(lblTitulo);

        // Painel de filtros
        JPanel painelFiltros = new JPanel();
        painelFiltros.setBackground(MARROM);
        painelFiltros.setLayout(null);
        painelFiltros.setBounds(40, 75, 950, 100);
        painelFiltros.setBorder(new LineBorder(GOLD, 1, true));
        painelFundo.add(painelFiltros);

        Font fonteLabel = new Font("Segoe UI", Font.PLAIN, 13);

        JLabel lblMedico = new JLabel("Médico:");
        lblMedico.setFont(fonteLabel); lblMedico.setForeground(ROTULO);
        lblMedico.setBounds(20, 15, 80, 20);
        painelFiltros.add(lblMedico);

        cbMedico = new JComboBox<>();
        cbMedico.setBounds(20, 38, 270, 32);
        estilizarCombo(cbMedico);
        painelFiltros.add(cbMedico);

        JLabel lblDe = new JLabel("De (DD/MM/AAAA):");
        lblDe.setFont(fonteLabel); lblDe.setForeground(ROTULO);
        lblDe.setBounds(310, 15, 150, 20);
        painelFiltros.add(lblDe);

        txtDataInicio = criarCampo();
        txtDataInicio.setBounds(310, 38, 150, 32);
        painelFiltros.add(txtDataInicio);

        JLabel lblAte = new JLabel("Até (DD/MM/AAAA):");
        lblAte.setFont(fonteLabel); lblAte.setForeground(ROTULO);
        lblAte.setBounds(480, 15, 150, 20);
        painelFiltros.add(lblAte);

        txtDataFim = criarCampo();
        txtDataFim.setBounds(480, 38, 150, 32);
        painelFiltros.add(txtDataFim);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(650, 33, 100, 38);
        btnFiltrar.setBackground(GOLD);
        btnFiltrar.setForeground(MARROM);
        btnFiltrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnFiltrar.setFocusPainted(false);
        btnFiltrar.setOpaque(true);
        btnFiltrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFiltrar.setBorder(new LineBorder(GOLD.darker(), 1));
        painelFiltros.add(btnFiltrar);

        JButton btnHoje = new JButton("Hoje");
        btnHoje.setBounds(760, 33, 85, 38);
        btnHoje.setBackground(MEDIO);
        btnHoje.setForeground(CREME);
        btnHoje.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnHoje.setFocusPainted(false);
        btnHoje.setOpaque(true);
        btnHoje.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHoje.setBorder(new LineBorder(GOLD, 1));
        painelFiltros.add(btnHoje);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(855, 33, 80, 38);
        btnLimpar.setBackground(MEDIO);
        btnLimpar.setForeground(CREME);
        btnLimpar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLimpar.setFocusPainted(false);
        btnLimpar.setOpaque(true);
        btnLimpar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpar.setBorder(new LineBorder(GOLD, 1));
        painelFiltros.add(btnLimpar);

        // Tabela
        String[] colunas = {"Data", "Horário", "Paciente", "Médico / Especialidade", "Status", "Convênio"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabela = new JTable(modeloTabela);
        tabela.setRowHeight(28);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setBackground(CREME);
        tabela.setSelectionBackground(GOLD);
        tabela.setSelectionForeground(MARROM);
        tabela.getTableHeader().setBackground(MARROM);
        tabela.getTableHeader().setForeground(GOLD);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(220);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(120);

        // Renderer por status
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (!sel) {
                    String status = String.valueOf(t.getValueAt(row, 4));
                    switch (status) {
                        case "Em Atendimento" -> { c.setBackground(new Color(255, 243, 205)); c.setForeground(new Color(120, 80, 0)); }
                        case "Finalizado"     -> { c.setBackground(new Color(220, 240, 220)); c.setForeground(new Color(30, 100, 30)); }
                        case "Aguardando"     -> { c.setBackground(new Color(235, 245, 255)); c.setForeground(new Color(30, 60, 120)); }
                        case "Cancelado"      -> { c.setBackground(new Color(250, 220, 220)); c.setForeground(new Color(150, 30, 30)); }
                        default               -> { c.setBackground(CREME); c.setForeground(MARROM); }
                    }
                }
                return c;
            }
        };
        for (int i = 0; i < tabela.getColumnCount(); i++)
            tabela.getColumnModel().getColumn(i).setCellRenderer(renderer);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(40, 190, 950, 380);
        scroll.setBorder(new LineBorder(GOLD, 1));
        painelFundo.add(scroll);

        JButton btnFechar = new JButton("← Fechar");
        btnFechar.setBounds(40, 585, 130, 35);
        btnFechar.setBackground(new Color(180, 70, 70));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnFechar.setFocusPainted(false);
        btnFechar.setOpaque(true);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnFechar);

        // Eventos
        carregarMedicos();
        carregarAgenda(null, null, null);

        btnFiltrar.addActionListener(e -> {
            String medico = cbMedico.getSelectedIndex() == 0 ? null
                          : cbMedico.getSelectedItem().toString();
            String de  = txtDataInicio.getText().trim().isEmpty() ? null
                       : converterData(txtDataInicio.getText().trim());
            String ate = txtDataFim.getText().trim().isEmpty() ? null
                       : converterData(txtDataFim.getText().trim());
            carregarAgenda(medico, de, ate);
        });

        btnHoje.addActionListener(e -> {
            String hoje = java.time.LocalDate.now().toString();
            carregarAgenda(
                cbMedico.getSelectedIndex() == 0 ? null : cbMedico.getSelectedItem().toString(),
                hoje, hoje
            );
        });

        btnLimpar.addActionListener(e -> {
            cbMedico.setSelectedIndex(0);
            txtDataInicio.setText("");
            txtDataFim.setText("");
            carregarAgenda(null, null, null);
        });

        btnFechar.addActionListener(e -> this.dispose());
    }

    private void carregarMedicos() {
        cbMedico.addItem("Todos os médicos");
        String sql = "SELECT u.nome FROM usuario u "
                   + "JOIN medico m ON m.idUsuario = u.idUsuario "
                   + "WHERE u.ativo = 1 ORDER BY u.nome";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) cbMedico.addItem(rs.getString("nome"));
        } catch (SQLException e) {
            System.err.println("Erro ao carregar médicos: " + e.getMessage());
        }
    }

    private void carregarAgenda(String medico, String dataInicio, String dataFim) {
        modeloTabela.setRowCount(0);

        StringBuilder sql = new StringBuilder(
            "SELECT DATE(c.dataHora) AS data, TIME(c.dataHora) AS horario, "
          + "p.nome AS paciente, u.nome AS medico, e.nome AS especialidade, "
          + "c.status, cv.nome AS convenio "
          + "FROM consulta c "
          + "JOIN paciente p ON p.idPaciente = c.idPaciente "
          + "JOIN usuario u ON u.idUsuario = c.idUsuario "
          + "LEFT JOIN medico m ON m.idUsuario = u.idUsuario "
          + "LEFT JOIN especialidade e ON e.idEspecialidade = m.idEspecialidade "
          + "LEFT JOIN convenio cv ON cv.idConvenio = c.idConvenio "
          + "WHERE 1=1 "
        );

        if (medico != null)      sql.append("AND u.nome = ? ");
        if (dataInicio != null)  sql.append("AND DATE(c.dataHora) >= ? ");
        if (dataFim != null)     sql.append("AND DATE(c.dataHora) <= ? ");
        sql.append("ORDER BY c.dataHora");

        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement st = conn.prepareStatement(sql.toString())) {

            int i = 1;
            if (medico != null)     st.setString(i++, medico);
            if (dataInicio != null) st.setString(i++, dataInicio);
            if (dataFim != null)    st.setString(i++, dataFim);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String hora = rs.getString("horario");
                    if (hora != null && hora.length() > 5) hora = hora.substring(0, 5);
                    String esp    = rs.getString("especialidade");
                    String medNome = rs.getString("medico")
                                  + (esp != null ? " (" + esp + ")" : "");
                    modeloTabela.addRow(new Object[]{
                        rs.getString("data"),
                        hora,
                        rs.getString("paciente"),
                        medNome,
                        rs.getString("status"),
                        rs.getString("convenio") != null ? rs.getString("convenio") : "Particular"
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar agenda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String converterData(String data) {
        String[] p = data.split("/");
        return p[2] + "-" + p[1] + "-" + p[0];
    }

    private JTextField criarCampo() {
        JTextField campo = new JTextField();
        campo.setBackground(MEDIO);
        campo.setForeground(CREME);
        campo.setCaretColor(CREME);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(GOLD, 1), new EmptyBorder(0, 8, 0, 8)));
        return campo;
    }

    private void estilizarCombo(JComboBox<String> cb) {
        cb.setBackground(MEDIO);
        cb.setForeground(CREME);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cb.setBorder(new LineBorder(GOLD, 1));
        cb.setOpaque(true);
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setOpaque(true);
                label.setBorder(new EmptyBorder(0, 8, 0, 0));
                label.setBackground(isSelected ? GOLD : MEDIO);
                label.setForeground(isSelected ? MARROM : CREME);
                return label;
            }
        });
    }
}