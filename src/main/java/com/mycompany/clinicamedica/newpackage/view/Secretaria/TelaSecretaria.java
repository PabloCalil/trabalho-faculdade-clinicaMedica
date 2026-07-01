package com.mycompany.clinicamedica.newpackage.view.Secretaria;

import Services.BDSConnection;
import Services.ConsultaDAO;
import Services.StatusDAO;
import com.mycompany.clinicamedica.newpackage.view.TelaLogin;
import java.awt.*;
import java.sql.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class TelaSecretaria extends JFrame {

    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JLabel lblContadores;

    private final ConsultaDAO consultaDAO = new ConsultaDAO();
    private final StatusDAO   statusDAO   = new StatusDAO();

    private static final Color MARROM = new Color(61, 28, 6);
    private static final Color GOLD   = new Color(193, 158, 103);
    private static final Color FUNDO  = new Color(244, 241, 234);

    public TelaSecretaria() {
        setTitle("VITA — Painel de Recepção e Atendimento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(860, 520));
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(FUNDO);
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

        JLabel lblTitulo = new JLabel("Atendimento e Recepção Central — VITA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        header.add(lblTitulo, BorderLayout.WEST);

        JButton btnSair = new JButton("Logout");
        btnSair.setBackground(new Color(180, 70, 70));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> { dispose(); new TelaLogin().setVisible(true); });
        header.add(btnSair, BorderLayout.EAST);

        return header;
    }

    // ── CORPO ─────────────────────────────────────────────────────────────────

    private JPanel criarCorpo() {
        JPanel corpo = new JPanel(new BorderLayout(24, 0));
        corpo.setBackground(FUNDO);
        corpo.setBorder(new EmptyBorder(28, 36, 28, 36));

        corpo.add(criarMenuAcoes(), BorderLayout.WEST);
        corpo.add(criarPainelMonitoramento(), BorderLayout.CENTER);

        return corpo;
    }

    // ── MENU LATERAL ──────────────────────────────────────────────────────────

    private JPanel criarMenuAcoes() {
        JPanel painel = new JPanel(new BorderLayout(0, 0));
        painel.setBackground(FUNDO);
        painel.setPreferredSize(new Dimension(260, 0));

        JPanel btnBox = new JPanel(new GridLayout(2, 1, 0, 16));
        btnBox.setBackground(FUNDO);

        JButton btnNovoPac = criarBotao("Cadastrar Novo Paciente");
        JButton btnAgenda  = criarBotao("Agenda Médica");

        btnNovoPac.addActionListener(e -> new TelaCadastrarPaciente_dac().setVisible(true));
        btnAgenda.addActionListener(e -> new TelaConsultarEscalaMedica().setVisible(true));

        btnBox.add(btnNovoPac);
        btnBox.add(btnAgenda);

        painel.add(btnBox, BorderLayout.NORTH);
        return painel;
    }

    // ── PAINEL DE MONITORAMENTO ───────────────────────────────────────────────

    private JPanel criarPainelMonitoramento() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        painel.setBackground(FUNDO);

        // Cabeçalho da seção
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(FUNDO);

        JLabel lblTitulo = new JLabel("Consultas do Dia — Monitoramento em Tempo Real");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitulo.setForeground(MARROM);
        topBar.add(lblTitulo, BorderLayout.WEST);

        JPanel botoesTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botoesTopo.setBackground(FUNDO);

        JButton btnAlterarStatus = new JButton("Alterar Status");
        btnAlterarStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAlterarStatus.setBackground(GOLD);
        btnAlterarStatus.setForeground(MARROM);
        btnAlterarStatus.setFocusPainted(false);
        btnAlterarStatus.setOpaque(true);
        btnAlterarStatus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAlterarStatus.setBorder(new LineBorder(GOLD.darker(), 1, true));
        btnAlterarStatus.addActionListener(e -> alterarStatusSelecionado());

        JButton btnAtualizar = new JButton("↻  Atualizar");
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAtualizar.setBackground(MARROM);
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAtualizar.setBorder(new LineBorder(GOLD, 1, true));
        btnAtualizar.addActionListener(e -> carregarConsultas());

        botoesTopo.add(btnAlterarStatus);
        botoesTopo.add(btnAtualizar);
        topBar.add(botoesTopo, BorderLayout.EAST);

        painel.add(topBar, BorderLayout.NORTH);

        // Tabela — a coluna idConsulta fica oculta (o usuario nunca ve ids).
        String[] colunas = {"Paciente", "Médico / Especialidade", "Horário", "Status", "idConsulta"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(30);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setGridColor(new Color(220, 215, 205));
        tabela.setShowHorizontalLines(true);
        tabela.setShowVerticalLines(false);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(MARROM);
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getColumnModel().getColumn(2).setMaxWidth(90);
        tabela.getColumnModel().getColumn(3).setMaxWidth(150);
        // Oculta a coluna idConsulta (usada apenas internamente).
        TableColumn colId = tabela.getColumnModel().getColumn(4);
        colId.setMinWidth(0);
        colId.setMaxWidth(0);
        colId.setWidth(0);

        // Renderer colorido por status
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (!sel) {
                    String status = String.valueOf(t.getValueAt(row, 3));
                    switch (status) {
                        case "Em Atendimento"      -> { c.setBackground(new Color(210, 230, 250)); c.setForeground(new Color(20, 60, 120)); }
                        case "Agendado"            -> { c.setBackground(new Color(252, 248, 235)); c.setForeground(MARROM); }
                        case "Aguardando Chamada"  -> { c.setBackground(new Color(255, 250, 220)); c.setForeground(new Color(120, 90, 0)); }
                        case "Consulta Finalizada" -> { c.setBackground(new Color(235, 235, 235)); c.setForeground(Color.GRAY); }
                        case "Cancelado"           -> { c.setBackground(new Color(250, 225, 225)); c.setForeground(new Color(140, 40, 40)); }
                        default                    -> { c.setBackground(Color.WHITE); c.setForeground(Color.DARK_GRAY); }
                    }
                }
                return c;
            }
        };
        for (int i = 0; i < tabela.getColumnCount(); i++)
            tabela.getColumnModel().getColumn(i).setCellRenderer(renderer);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(GOLD));
        painel.add(scroll, BorderLayout.CENTER);

        // Rodapé com contadores
        lblContadores = new JLabel(" ");
        lblContadores.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblContadores.setForeground(new Color(120, 100, 80));
        painel.add(lblContadores, BorderLayout.SOUTH);

        return painel;
    }

    // ── CARGA DO BANCO ────────────────────────────────────────────────────────

    private void carregarConsultas() {
        modeloTabela.setRowCount(0);

        String sql = "SELECT c.idConsulta, p.nome AS paciente, "
                   + "u.nome AS medico, e.nome AS especialidade, "
                   + "TIME(c.dataHora) AS horario, s.nome AS status "
                   + "FROM consulta c "
                   + "JOIN paciente p ON p.idPaciente = c.idPaciente "
                   + "JOIN usuario u ON u.idUsuario = c.idUsuario "
                   + "JOIN status s ON s.idStatus = c.idStatus "
                   + "LEFT JOIN medico m ON m.idUsuario = u.idUsuario "
                   + "LEFT JOIN especialidade e ON e.idEspecialidade = m.idEspecialidade "
                   + "WHERE DATE(c.dataHora) = CURDATE() "
                   + "AND s.nome != 'Cancelado' "
                   + "ORDER BY c.dataHora";

        int total = 0, aguardando = 0, agendados = 0, emAtendimento = 0;

        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                int idConsulta = rs.getInt("idConsulta");
                String esp    = rs.getString("especialidade");
                String medico = rs.getString("medico") + (esp != null ? " (" + esp + ")" : "");
                String hora   = rs.getString("horario");
                if (hora != null && hora.length() > 5) hora = hora.substring(0, 5);
                String status = rs.getString("status");

                modeloTabela.addRow(new Object[]{rs.getString("paciente"), medico, hora, status, idConsulta});

                total++;
                switch (status) {
                    case "Aguardando Chamada" -> aguardando++;
                    case "Agendado"           -> agendados++;
                    case "Em Atendimento"     -> emAtendimento++;
                }
            }

            if (total == 0) {
                lblContadores.setText("Nenhuma consulta registrada para hoje.");
            } else {
                lblContadores.setText(String.format(
                    "Total hoje: %d  |  Agendados: %d  |  Aguardando chamada: %d  |  Em Atendimento: %d",
                    total, agendados, aguardando, emAtendimento));
            }

        } catch (SQLException e) {
            lblContadores.setText("Erro ao carregar: " + e.getMessage());
        }
    }

    // ── ALTERAR STATUS (somente a secretária avança o andamento) ────────────────

    private void alterarStatusSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione uma consulta na tabela para alterar o status.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idConsulta      = (int) modeloTabela.getValueAt(linha, 4);
        String paciente     = String.valueOf(modeloTabela.getValueAt(linha, 0));
        String statusAtual  = String.valueOf(modeloTabela.getValueAt(linha, 3));

        List<String> nomes = statusDAO.listarNomes();
        if (nomes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Não foi possível carregar a lista de status.",
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String novoStatus = (String) JOptionPane.showInputDialog(this,
            "Paciente: " + paciente + "\nStatus atual: " + statusAtual + "\n\nSelecione o novo status:",
            "Alterar Status da Consulta", JOptionPane.QUESTION_MESSAGE, null,
            nomes.toArray(new String[0]), statusAtual);

        if (novoStatus == null || novoStatus.equals(statusAtual)) return;

        if (consultaDAO.atualizarStatus(idConsulta, novoStatus)) {
            JOptionPane.showMessageDialog(this,
                "Status atualizado para '" + novoStatus + "'.",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarConsultas();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao atualizar status.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── UTILITÁRIOS ───────────────────────────────────────────────────────────

    private JButton criarBotao(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(MARROM);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBorder(new LineBorder(GOLD, 1, true));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}
