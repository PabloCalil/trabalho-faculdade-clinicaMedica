package com.mycompany.clinicamedica.newpackage.view.Secretaria;

import Services.BDSConnection;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TelaConsultarEscalaMedica extends JFrame {

    private JComboBox<String> cbMedico;
    private JTextField txtData;
    private JButton btnConsultar, btnAgendarLivre, btnVoltar;
    private JTable tabelaEscala;
    private DefaultTableModel modeloTabela;
    private JLabel lblResumo;

    private final List<Integer> idsMedicos = new ArrayList<>();

    private static final Color CREME  = new Color(251, 251, 250);
    private static final Color GOLD   = new Color(193, 158, 103);
    private static final Color MEDIO  = new Color(110, 102, 95);
    private static final Color ROTULO = new Color(180, 169, 158);
    private static final Color MARROM = new Color(61, 28, 6);
    private static final Color LIVRE  = new Color(225, 240, 220);
    private static final Color OCUPADO = new Color(245, 225, 220);

    private final String[] horarios = {
        "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
        "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"
    };

    public TelaConsultarEscalaMedica() {
        setTitle("VITA — Escala e Disponibilidade Médica");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel fundo = new JPanel(null);
        fundo.setBackground(MEDIO);
        setContentPane(fundo);

        JLabel lblTitulo = new JLabel("Escala e Disponibilidade Médica");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(CREME);
        lblTitulo.setBounds(50, 18, 600, 38);
        fundo.add(lblTitulo);

        JLabel lblSub = new JLabel("Selecione o médico e a data para ver os horários livres e ocupados.");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(ROTULO);
        lblSub.setBounds(50, 56, 700, 18);
        fundo.add(lblSub);

        // Painel de filtros
        JPanel filtros = new JPanel(null);
        filtros.setBackground(MARROM);
        filtros.setBounds(50, 85, 885, 80);
        filtros.setBorder(new LineBorder(GOLD, 1, true));
        fundo.add(filtros);

        JLabel lblMedico = new JLabel("Médico / Especialista:");
        lblMedico.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMedico.setForeground(ROTULO);
        lblMedico.setBounds(20, 10, 200, 20);
        filtros.add(lblMedico);

        cbMedico = new JComboBox<>();
        cbMedico.setBounds(20, 33, 360, 30);
        cbMedico.setBackground(MEDIO);
        cbMedico.setForeground(CREME);
        cbMedico.setBorder(new LineBorder(GOLD, 1));
        filtros.add(cbMedico);

        JLabel lblData = new JLabel("Data (DD/MM/AAAA):");
        lblData.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblData.setForeground(ROTULO);
        lblData.setBounds(410, 10, 200, 20);
        filtros.add(lblData);

        txtData = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtData.setBounds(410, 33, 180, 30);
        txtData.setBackground(MEDIO);
        txtData.setForeground(CREME);
        txtData.setCaretColor(CREME);
        txtData.setBorder(new LineBorder(GOLD, 1));
        filtros.add(txtData);

        btnConsultar = new JButton("Gerar Escala");
        btnConsultar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnConsultar.setBackground(GOLD);
        btnConsultar.setForeground(MARROM);
        btnConsultar.setBounds(700, 28, 160, 35);
        btnConsultar.setFocusPainted(false);
        btnConsultar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        filtros.add(btnConsultar);

        // Tabela
        String[] colunas = {"Horário", "Situação", "Paciente", "Convênio"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaEscala = new JTable(modeloTabela);
        tabelaEscala.setBackground(CREME);
        tabelaEscala.setGridColor(ROTULO);
        tabelaEscala.setRowHeight(28);
        tabelaEscala.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaEscala.getTableHeader().setReorderingAllowed(false);
        tabelaEscala.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabelaEscala.getColumnModel().getColumn(0).setMaxWidth(80);
        tabelaEscala.getColumnModel().getColumn(1).setMaxWidth(90);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (!sel) {
                    String sit = String.valueOf(t.getValueAt(row, 1));
                    c.setBackground("Livre".equals(sit) ? LIVRE : OCUPADO);
                    c.setForeground(MARROM);
                }
                return c;
            }
        };
        for (int i = 0; i < tabelaEscala.getColumnCount(); i++)
            tabelaEscala.getColumnModel().getColumn(i).setCellRenderer(renderer);

        JScrollPane scroll = new JScrollPane(tabelaEscala);
        scroll.setBounds(50, 185, 885, 370);
        scroll.setBorder(new LineBorder(GOLD, 1));
        fundo.add(scroll);

        // Legenda
        JPanel legenda = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 4));
        legenda.setBackground(MEDIO);
        legenda.setBounds(50, 560, 400, 28);
        fundo.add(legenda);
        legenda.add(legendaItem(LIVRE, "Livre"));
        legenda.add(legendaItem(OCUPADO, "Ocupado"));

        lblResumo = new JLabel("Selecione um médico e clique em \"Gerar Escala\".");
        lblResumo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblResumo.setForeground(CREME);
        lblResumo.setBounds(50, 562, 885, 22);
        fundo.add(lblResumo);

        // Botões rodapé
        btnVoltar = new JButton("← Voltar");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setBackground(new Color(180, 70, 70));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(50, 610, 130, 40);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fundo.add(btnVoltar);

        btnAgendarLivre = new JButton("Agendar neste Horário Livre");
        btnAgendarLivre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAgendarLivre.setBackground(MARROM);
        btnAgendarLivre.setForeground(CREME);
        btnAgendarLivre.setBounds(620, 610, 280, 40);
        btnAgendarLivre.setFocusPainted(false);
        btnAgendarLivre.setBorder(new LineBorder(GOLD, 1));
        btnAgendarLivre.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fundo.add(btnAgendarLivre);

        carregarMedicos();
        configurarEventos();
    }

    private void carregarMedicos() {
        cbMedico.addItem("Selecione o médico...");
        idsMedicos.add(0);

        String sql = "SELECT u.idUsuario, u.nome, e.nome AS especialidade "
                   + "FROM usuario u "
                   + "JOIN medico m ON m.idUsuario = u.idUsuario "
                   + "LEFT JOIN especialidade e ON e.idEspecialidade = m.idEspecialidade "
                   + "WHERE u.ativo = 1 ORDER BY u.nome";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String esp = rs.getString("especialidade");
                cbMedico.addItem(rs.getString("nome") + (esp != null ? " (" + esp + ")" : ""));
                idsMedicos.add(rs.getInt("idUsuario"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar médicos: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarEventos() {
        btnConsultar.addActionListener(e -> gerarEscala());

        btnAgendarLivre.addActionListener(e -> {
            int linha = tabelaEscala.getSelectedRow();
            if (linha < 0) {
                JOptionPane.showMessageDialog(this,
                    "Selecione um horário na tabela para agendar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!"Livre".equals(modeloTabela.getValueAt(linha, 1))) {
                JOptionPane.showMessageDialog(this,
                    "Este horário já está ocupado. Escolha um horário livre.",
                    "Horário Indisponível", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cbMedico.getSelectedIndex() == 0) return;

            String horarioSelecionado = (String) modeloTabela.getValueAt(linha, 0);
            int idMedico = idsMedicos.get(cbMedico.getSelectedIndex());
            String data  = txtData.getText().trim();

            TelaAgendarConsulta tela = new TelaAgendarConsulta();
            tela.preencherAgendamento(idMedico, data, horarioSelecionado);
            tela.setVisible(true);
        });

        btnVoltar.addActionListener(e -> dispose());
    }

    private void gerarEscala() {
        if (cbMedico.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um médico.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String dataTexto = txtData.getText().trim();
        String dataSQL = converterData(dataTexto);
        if (dataSQL == null) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use DD/MM/AAAA.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMedico = idsMedicos.get(cbMedico.getSelectedIndex());
        Map<String, String[]> ocupados = buscarConsultasBanco(idMedico, dataSQL);

        modeloTabela.setRowCount(0);
        int livres = 0, ocp = 0;
        for (String h : horarios) {
            if (ocupados.containsKey(h)) {
                String[] dados = ocupados.get(h);
                modeloTabela.addRow(new Object[]{h, "Ocupado", dados[0], dados[1]});
                ocp++;
            } else {
                modeloTabela.addRow(new Object[]{h, "Livre", "—", "—"});
                livres++;
            }
        }

        lblResumo.setText(String.format("%s — %s  |  %d livre(s)  |  %d ocupado(s)",
            cbMedico.getSelectedItem(), dataTexto, livres, ocp));
    }

    private Map<String, String[]> buscarConsultasBanco(int idMedico, String dataSQL) {
        Map<String, String[]> mapa = new LinkedHashMap<>();
        String sql = "SELECT TIME(c.dataHora) AS hora, p.nome AS paciente, cv.nome AS convenio "
                   + "FROM consulta c "
                   + "JOIN paciente p ON p.idPaciente = c.idPaciente "
                   + "LEFT JOIN convenio cv ON cv.idConvenio = c.idConvenio "
                   + "WHERE c.idUsuario = ? AND DATE(c.dataHora) = ? "
                   + "AND c.status != 'Cancelado'";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMedico);
            stmt.setString(2, dataSQL);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String hora = rs.getString("hora").substring(0, 5);
                    String conv = rs.getString("convenio");
                    mapa.put(hora, new String[]{rs.getString("paciente"), conv != null ? conv : "Particular"});
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar consultas: " + e.getMessage());
        }
        return mapa;
    }

    private String converterData(String data) {
        try {
            LocalDate ld = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return null;
        }
    }

    private JLabel legendaItem(Color cor, String texto) {
        JLabel lbl = new JLabel("  " + texto);
        lbl.setOpaque(true);
        lbl.setBackground(cor);
        lbl.setForeground(MARROM);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setBorder(BorderFactory.createLineBorder(ROTULO));
        return lbl;
    }
}
