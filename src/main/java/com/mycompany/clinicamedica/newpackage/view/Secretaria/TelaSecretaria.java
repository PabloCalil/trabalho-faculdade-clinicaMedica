package com.mycompany.clinicamedica.newpackage.view.Secretaria;

import Services.BDSConnection;
import com.mycompany.clinicamedica.newpackage.view.TelaLogin;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class TelaSecretaria extends JFrame {

    private DefaultTableModel modeloTabela;
    private JLabel lblContadores;

    private static final Color MARROM  = new Color(61, 28, 6);
    private static final Color GOLD    = new Color(193, 158, 103);
    private static final Color FUNDO   = new Color(244, 241, 234);
    private static final Color MEDIO   = new Color(110, 102, 95);
    private static final Color CREME   = new Color(251, 251, 250);
    private static final Color ROTULO  = new Color(180, 169, 158);

    public TelaSecretaria() {
        setTitle("VITA — Painel de Recepção e Atendimento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(MEDIO);
        setContentPane(principal);

        principal.add(criarHeader(), BorderLayout.NORTH);
        principal.add(criarCorpo(), BorderLayout.CENTER);

        carregarConsultas();
    }

    // ── HEADER ────────────────────────────────────────────────────────────────
    private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MARROM);
        header.setBorder(new EmptyBorder(18, 40, 18, 40));

        JPanel txtHeader = new JPanel(new GridLayout(2, 1));
        txtHeader.setBackground(MARROM);

        JLabel lblTitulo = new JLabel("Atendimento e Recepção Central — VITA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblData = new JLabel("Hoje: " + java.time.LocalDate.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblData.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblData.setForeground(GOLD);

        txtHeader.add(lblTitulo);
        txtHeader.add(lblData);
        header.add(txtHeader, BorderLayout.WEST);

        JButton btnSair = new JButton("Logout");
        btnSair.setBackground(new Color(180, 70, 70));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSair.setFocusPainted(false);
        btnSair.setOpaque(true);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> { dispose(); new TelaLogin().setVisible(true); });
        header.add(btnSair, BorderLayout.EAST);

        return header;
    }

    // ── CORPO ─────────────────────────────────────────────────────────────────
    private JPanel criarCorpo() {
        JPanel corpo = new JPanel(new BorderLayout(20, 0));
        corpo.setBackground(MEDIO);
        corpo.setBorder(new EmptyBorder(25, 30, 25, 30));

        corpo.add(criarMenuAcoes(), BorderLayout.WEST);
        corpo.add(criarPainelMonitoramento(), BorderLayout.CENTER);

        return corpo;
    }

    // ── MENU LATERAL ──────────────────────────────────────────────────────────
    private JPanel criarMenuAcoes() {
        JPanel painel = new JPanel();
        painel.setBackground(MARROM);
        painel.setLayout(null);
        painel.setPreferredSize(new Dimension(220, 0));
        painel.setBorder(new LineBorder(GOLD, 1, true));

        JLabel lblMenu = new JLabel("Ações");
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMenu.setForeground(GOLD);
        lblMenu.setBounds(20, 20, 180, 25);
        painel.add(lblMenu);

        JButton btnNovaConsulta = criarBotaoMenu("Marcar Consulta");
        btnNovaConsulta.setBounds(15, 60, 190, 42);
        painel.add(btnNovaConsulta);

        JButton btnFiltrar = criarBotaoMenu("Filtrar Consultas");
        btnFiltrar.setBounds(15, 115, 190, 42);
        painel.add(btnFiltrar);

        JButton btnNovoPac = criarBotaoMenu("Cadastrar Paciente");
        btnNovoPac.setBounds(15, 170, 190, 42);
        painel.add(btnNovoPac);

        JButton btnAgenda = criarBotaoMenu("Agenda Médica");
        btnAgenda.setBounds(15, 225, 190, 42);
        painel.add(btnAgenda);

        btnNovaConsulta.addActionListener(e -> new TelaAgendarConsulta().setVisible(true));
        btnFiltrar.addActionListener(e -> new TelaFiltrarConsultas().setVisible(true));
        btnNovoPac.addActionListener(e ->
        new com.mycompany.clinicamedica.newpackage.view.Secretaria.TelaCadastrarPaciente_dac().setVisible(true));
        btnAgenda.addActionListener(e -> new TelaAgendaMedica().setVisible(true));

        return painel;
    }

    // ── PAINEL DE MONITORAMENTO ───────────────────────────────────────────────
    private JPanel criarPainelMonitoramento() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBackground(MARROM);
        painel.setBorder(new LineBorder(GOLD, 1, true));

        // Topo
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(MARROM);
        topBar.setBorder(new EmptyBorder(15, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Consultas do Dia — Todos os Médicos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(CREME);
        topBar.add(lblTitulo, BorderLayout.WEST);

        JButton btnAtualizar = new JButton("↻ Atualizar");
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAtualizar.setBackground(MEDIO);
        btnAtualizar.setForeground(CREME);
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setOpaque(true);
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAtualizar.setBorder(new LineBorder(GOLD, 1, true));
        btnAtualizar.addActionListener(e -> carregarConsultas());
        topBar.add(btnAtualizar, BorderLayout.EAST);

        painel.add(topBar, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"Horário", "Paciente", "Médico / Especialidade", "Status", "Convênio"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabela = new JTable(modeloTabela);
        tabela.setRowHeight(30);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setBackground(CREME);
        tabela.setGridColor(new Color(220, 215, 205));
        tabela.setShowHorizontalLines(true);
        tabela.setShowVerticalLines(false);
        tabela.setSelectionBackground(GOLD);
        tabela.setSelectionForeground(MARROM);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(MARROM);
        tabela.getTableHeader().setForeground(GOLD);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(220);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(130);

        // Renderer colorido por status
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (!sel) {
                    String status = String.valueOf(t.getValueAt(row, 3));
                    switch (status) {
                        case "Em Atendimento" -> { c.setBackground(new Color(255, 243, 205)); c.setForeground(new Color(120, 80, 0)); }
                        case "Finalizado"     -> { c.setBackground(new Color(220, 240, 220)); c.setForeground(new Color(30, 100, 30)); }
                        case "Aguardando"     -> { c.setBackground(new Color(235, 245, 255)); c.setForeground(new Color(30, 60, 120)); }
                        case "Agendado"       -> { c.setBackground(CREME);                   c.setForeground(MARROM); }
                        default               -> { c.setBackground(Color.WHITE);              c.setForeground(Color.DARK_GRAY); }
                    }
                }
                return c;
            }
        };
        for (int i = 0; i < tabela.getColumnCount(); i++)
            tabela.getColumnModel().getColumn(i).setCellRenderer(renderer);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new EmptyBorder(0, 15, 0, 15));
        painel.add(scroll, BorderLayout.CENTER);

        // Rodapé
        lblContadores = new JLabel(" ");
        lblContadores.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblContadores.setForeground(ROTULO);
        lblContadores.setBorder(new EmptyBorder(8, 20, 12, 20));
        painel.add(lblContadores, BorderLayout.SOUTH);

        return painel;
    }

    // ── CARGA DO BANCO ────────────────────────────────────────────────────────
    private void carregarConsultas() {
        modeloTabela.setRowCount(0);

        String sql = "SELECT TIME(c.dataHora) AS horario, "
                   + "p.nome AS paciente, "
                   + "u.nome AS medico, "
                   + "e.nome AS especialidade, "
                   + "c.status, "
                   + "cv.nome AS convenio "
                   + "FROM consulta c "
                   + "JOIN paciente p ON p.idPaciente = c.idPaciente "
                   + "JOIN usuario u ON u.idUsuario = c.idUsuario "
                   + "LEFT JOIN medico m ON m.idUsuario = u.idUsuario "
                   + "LEFT JOIN especialidade e ON e.idEspecialidade = m.idEspecialidade "
                   + "LEFT JOIN convenio cv ON cv.idConvenio = c.idConvenio "
                   + "WHERE DATE(c.dataHora) = CURDATE() "
                   + "AND c.status != 'Cancelado' "
                   + "ORDER BY c.dataHora";

        int total = 0, agendados = 0, emAtendimento = 0, finalizados = 0;

        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                String hora = rs.getString("horario");
                if (hora != null && hora.length() > 5) hora = hora.substring(0, 5);

                String esp    = rs.getString("especialidade");
                String medico = rs.getString("medico")
                              + (esp != null ? " (" + esp + ")" : "");
                String status  = rs.getString("status");
                String convenio = rs.getString("convenio");

                modeloTabela.addRow(new Object[]{
                    hora,
                    rs.getString("paciente"),
                    medico,
                    status,
                    convenio != null ? convenio : "Particular"
                });

                total++;
                switch (status) {
                    case "Agendado", "Aguardando" -> agendados++;
                    case "Em Atendimento"          -> emAtendimento++;
                    case "Finalizado"              -> finalizados++;
                }
            }

            lblContadores.setText(total == 0
                ? "Nenhuma consulta registrada para hoje."
                : String.format("Total: %d  |  Agendados: %d  |  Em Atendimento: %d  |  Finalizados: %d",
                    total, agendados, emAtendimento, finalizados));

        } catch (SQLException e) {
            lblContadores.setText("Erro ao carregar: " + e.getMessage());
        }
    }

    private JButton criarBotaoMenu(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(MEDIO);
        b.setForeground(CREME);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBorder(new LineBorder(GOLD, 1, true));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}