package com.mycompany.clinicamedica.newpackage.view.Secretaria;

import Services.BDSConnection;
import Services.ConsultaDAO;
import Services.Paciente;
import Services.PacienteDAO;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class TelaConsultarEscalaMedica extends JFrame {

    private JComboBox<String> cbMedico;
    private JSpinner spinnerData;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JLabel lblResumo;
    private JButton btnGerar, btnAgendar, btnCheckin;

    private final List<Integer> idsMedicos = new ArrayList<>();
    private final Map<String, Integer> idConsultaPorHorario = new LinkedHashMap<>();
    private final Map<String, String> pacientePorHorario   = new LinkedHashMap<>();

    private static final Color MARROM  = new Color(61, 28, 6);
    private static final Color GOLD    = new Color(193, 158, 103);
    private static final Color MEDIO   = new Color(110, 102, 95);
    private static final Color CREME   = new Color(251, 251, 250);
    private static final Color ROTULO  = new Color(180, 169, 158);
    private static final Color LIVRE   = new Color(210, 235, 210);
    private static final Color OCUPADO = new Color(245, 220, 215);
    private static final Color PRESENTE = new Color(200, 220, 245);

    private static final String[] HORARIOS = {
        "08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30",
        "13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30"
    };

    public TelaConsultarEscalaMedica() {
        setTitle("VITA — Agenda Médica");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1060, 730);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel fundo = new JPanel(null);
        fundo.setBackground(MEDIO);
        setContentPane(fundo);

        // ── HEADER ────────────────────────────────────────────────────────────
        JPanel header = new JPanel(null);
        header.setBackground(MARROM);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, GOLD));
        header.setBounds(0, 0, 1060, 70);
        fundo.add(header);

        JLabel lblTitulo = new JLabel("Agenda Médica — Agendamento e Check-in");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(30, 18, 650, 32);
        header.add(lblTitulo);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBounds(940, 20, 90, 30);
        btnFechar.setBackground(new Color(180, 70, 70));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.addActionListener(e -> dispose());
        header.add(btnFechar);

        // ── FILTROS ───────────────────────────────────────────────────────────
        JPanel pFiltros = new JPanel(null);
        pFiltros.setBackground(new Color(45, 20, 4));
        pFiltros.setBorder(new LineBorder(GOLD, 1, true));
        pFiltros.setBounds(20, 85, 1020, 75);
        fundo.add(pFiltros);

        JLabel lMedico = rotulo("Médico / Especialista:");
        lMedico.setBounds(20, 8, 200, 18);
        pFiltros.add(lMedico);

        cbMedico = new JComboBox<>();
        cbMedico.setBackground(MEDIO);
        cbMedico.setForeground(CREME);
        cbMedico.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbMedico.setBorder(new LineBorder(GOLD, 1));
        cbMedico.setBounds(20, 30, 380, 32);
        pFiltros.add(cbMedico);

        JLabel lData = rotulo("Data:");
        lData.setBounds(430, 8, 80, 18);
        pFiltros.add(lData);

        SpinnerDateModel dateModel = new SpinnerDateModel(new java.util.Date(), null, null, Calendar.DAY_OF_MONTH);
        spinnerData = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy");
        spinnerData.setEditor(dateEditor);
        dateEditor.getTextField().setBackground(MEDIO);
        dateEditor.getTextField().setForeground(CREME);
        dateEditor.getTextField().setCaretColor(CREME);
        dateEditor.getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinnerData.setBackground(MEDIO);
        spinnerData.setBorder(new LineBorder(GOLD, 1));
        spinnerData.setBounds(430, 30, 160, 32);
        pFiltros.add(spinnerData);

        btnGerar = botaoPrimario("Ver Agenda");
        btnGerar.setBounds(870, 22, 130, 36);
        pFiltros.add(btnGerar);

        // ── LEGENDA ───────────────────────────────────────────────────────────
        JPanel pLegenda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        pLegenda.setBackground(MEDIO);
        pLegenda.setBounds(20, 168, 500, 28);
        fundo.add(pLegenda);
        pLegenda.add(chip(LIVRE,    "Livre"));
        pLegenda.add(chip(OCUPADO,  "Ocupado"));
        pLegenda.add(chip(PRESENTE, "Presente"));

        // ── TABELA ────────────────────────────────────────────────────────────
        String[] cols = {"Horário", "Situação", "Paciente", "Convênio", "Status"};
        modelo = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modelo);
        tabela.setRowHeight(30);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setGridColor(new Color(200, 195, 185));
        tabela.setSelectionBackground(GOLD);
        tabela.setSelectionForeground(MARROM);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(MARROM);
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getColumnModel().getColumn(0).setMaxWidth(80);
        tabela.getColumnModel().getColumn(1).setMaxWidth(90);
        tabela.getColumnModel().getColumn(4).setMaxWidth(110);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (!sel) {
                    String sit = String.valueOf(t.getValueAt(row, 1));
                    String sts = String.valueOf(t.getValueAt(row, 4));
                    if ("Livre".equals(sit))          c.setBackground(LIVRE);
                    else if ("Presente".equals(sts))  c.setBackground(PRESENTE);
                    else                               c.setBackground(OCUPADO);
                    c.setForeground(MARROM);
                }
                return c;
            }
        };
        for (int i = 0; i < tabela.getColumnCount(); i++)
            tabela.getColumnModel().getColumn(i).setCellRenderer(renderer);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 200, 1020, 400);
        scroll.setBorder(new LineBorder(GOLD, 1));
        fundo.add(scroll);

        // ── RODAPÉ ────────────────────────────────────────────────────────────
        lblResumo = new JLabel("Selecione o médico, a data e clique em \"Ver Agenda\".");
        lblResumo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblResumo.setForeground(CREME);
        lblResumo.setBounds(20, 610, 560, 22);
        fundo.add(lblResumo);

        JButton btnAtualizar = botaoSecundario("Atualizar");
        btnAtualizar.setBounds(590, 603, 110, 36);
        fundo.add(btnAtualizar);

        btnCheckin = new JButton("✔  Confirmar Presença");
        btnCheckin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCheckin.setBackground(new Color(55, 130, 55));
        btnCheckin.setForeground(Color.WHITE);
        btnCheckin.setBounds(715, 603, 190, 36);
        btnCheckin.setFocusPainted(false);
        btnCheckin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCheckin.setEnabled(false);
        fundo.add(btnCheckin);

        btnAgendar = botaoPrimario("Agendar Consulta");
        btnAgendar.setBounds(920, 603, 160, 36);
        btnAgendar.setEnabled(false);
        fundo.add(btnAgendar);

        carregarMedicos();
        configurarEventos(btnAtualizar);
    }

    // ── CARGA INICIAL ──────────────────────────────────────────────────────────

    private void carregarMedicos() {
        cbMedico.addItem("Selecione o médico...");
        idsMedicos.add(0);
        String sql = "SELECT u.idUsuario, u.nome, e.nome AS especialidade "
                   + "FROM usuario u JOIN medico m ON m.idUsuario = u.idUsuario "
                   + "LEFT JOIN especialidade e ON e.idEspecialidade = m.idEspecialidade "
                   + "WHERE u.ativo = 1 ORDER BY u.nome";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
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

    // ── EVENTOS ────────────────────────────────────────────────────────────────

    private void configurarEventos(JButton btnAtualizar) {
        btnGerar.addActionListener(e -> gerarEscala());
        btnAtualizar.addActionListener(e -> gerarEscala());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tabela.getSelectedRow();
            if (row < 0) { btnAgendar.setEnabled(false); btnCheckin.setEnabled(false); return; }
            String sit = (String) modelo.getValueAt(row, 1);
            String sts = (String) modelo.getValueAt(row, 4);
            btnAgendar.setEnabled("Livre".equals(sit));
            btnCheckin.setEnabled("Ocupado".equals(sit) && !"Presente".equals(sts));
        });

        btnAgendar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) return;
            String horario = (String) modelo.getValueAt(row, 0);
            int idMedico = idsMedicos.get(cbMedico.getSelectedIndex());
            String dataBr = new SimpleDateFormat("dd/MM/yyyy")
                .format(((SpinnerDateModel) spinnerData.getModel()).getDate());
            abrirDialogAgendar(idMedico, (String) cbMedico.getSelectedItem(), dataBr, horario);
        });

        btnCheckin.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) return;
            String horario  = (String) modelo.getValueAt(row, 0);
            String paciente = (String) modelo.getValueAt(row, 2);
            Integer idConsulta = idConsultaPorHorario.get(horario);
            if (idConsulta == null) return;

            int ok = JOptionPane.showConfirmDialog(this,
                "Confirmar chegada de: " + paciente + "?",
                "Check-in", JOptionPane.YES_NO_OPTION);
            if (ok != JOptionPane.YES_OPTION) return;

            if (new ConsultaDAO().atualizarStatus(idConsulta, "Presente")) {
                JOptionPane.showMessageDialog(this,
                    paciente + " registrado como Presente.", "Check-in realizado",
                    JOptionPane.INFORMATION_MESSAGE);
                gerarEscala();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar. Verifique a conexão.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // ── GERAR GRADE ────────────────────────────────────────────────────────────

    private void gerarEscala() {
        if (cbMedico.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um médico.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idMedico = idsMedicos.get(cbMedico.getSelectedIndex());
        String dataSQL = new SimpleDateFormat("yyyy-MM-dd")
            .format(((SpinnerDateModel) spinnerData.getModel()).getDate());
        String dataBr  = new SimpleDateFormat("dd/MM/yyyy")
            .format(((SpinnerDateModel) spinnerData.getModel()).getDate());

        idConsultaPorHorario.clear();
        pacientePorHorario.clear();

        // Mapa horario → dados da consulta
        Map<String, Object[]> agendados = new LinkedHashMap<>();
        String sql = "SELECT c.idConsulta, TIME(c.dataHora) AS hora, "
                   + "p.nome AS paciente, cv.nome AS convenio, c.status "
                   + "FROM consulta c "
                   + "JOIN paciente p ON p.idPaciente = c.idPaciente "
                   + "LEFT JOIN convenio cv ON cv.idConvenio = c.idConvenio "
                   + "WHERE c.idUsuario = ? AND DATE(c.dataHora) = ? "
                   + "AND c.status != 'Cancelado'";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, idMedico);
            st.setString(2, dataSQL);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String hora = rs.getString("hora").substring(0, 5);
                    String conv = rs.getString("convenio");
                    agendados.put(hora, new Object[]{
                        rs.getInt("idConsulta"),
                        rs.getString("paciente"),
                        conv != null ? conv : "Particular",
                        rs.getString("status")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar agenda: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        modelo.setRowCount(0);
        int livres = 0, ocupados = 0;
        for (String h : HORARIOS) {
            if (agendados.containsKey(h)) {
                Object[] d = agendados.get(h);
                int idC = (Integer) d[0];
                String pac = (String) d[1];
                String conv = (String) d[2];
                String sts  = (String) d[3];
                modelo.addRow(new Object[]{h, "Ocupado", pac, conv, sts});
                idConsultaPorHorario.put(h, idC);
                pacientePorHorario.put(h, pac);
                ocupados++;
            } else {
                modelo.addRow(new Object[]{h, "Livre", "—", "—", "—"});
                livres++;
            }
        }

        btnAgendar.setEnabled(false);
        btnCheckin.setEnabled(false);
        tabela.clearSelection();

        lblResumo.setText(String.format("%s — %s  |  %d livre(s)  |  %d ocupado(s)",
            cbMedico.getSelectedItem(), dataBr, livres, ocupados));
    }

    // ── DIALOG DE AGENDAMENTO ──────────────────────────────────────────────────

    private void abrirDialogAgendar(int idMedico, String nomeMedico, String dataBr, String horario) {
        JDialog dlg = new JDialog(this, "Agendar Consulta", true);
        dlg.setSize(480, 380);
        dlg.setLocationRelativeTo(this);
        dlg.setResizable(false);

        JPanel p = new JPanel(null);
        p.setBackground(MARROM);
        dlg.setContentPane(p);

        // Informações fixas
        addInfoLabel(p, "Médico:",   nomeMedico, 30, 20);
        addInfoLabel(p, "Data:",     dataBr,     30, 70);
        addInfoLabel(p, "Horário:",  horario,    30, 120);

        // Paciente
        JLabel lPac = rotulo("Paciente:");
        lPac.setBounds(30, 175, 200, 18);
        p.add(lPac);

        JComboBox<String> cbPaciente = new JComboBox<>();
        cbPaciente.setBackground(MEDIO);
        cbPaciente.setForeground(CREME);
        cbPaciente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbPaciente.setBorder(new LineBorder(GOLD, 1));
        cbPaciente.setBounds(30, 197, 400, 32);
        p.add(cbPaciente);

        // Convênio
        JLabel lConv = rotulo("Convênio:");
        lConv.setBounds(30, 242, 200, 18);
        p.add(lConv);

        JComboBox<String> cbConvenio = new JComboBox<>();
        cbConvenio.setBackground(MEDIO);
        cbConvenio.setForeground(CREME);
        cbConvenio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbConvenio.setBorder(new LineBorder(GOLD, 1));
        cbConvenio.setBounds(30, 264, 400, 32);
        p.add(cbConvenio);

        // Carrega pacientes e convênios
        List<Integer> idsPacientes = new ArrayList<>();
        List<Integer> idsConvenios = new ArrayList<>();

        cbPaciente.addItem("Selecione o paciente...");
        idsPacientes.add(0);
        for (Paciente pac : new PacienteDAO().listarTodos()) {
            cbPaciente.addItem(pac.getNome());
            idsPacientes.add(pac.getIdPaciente());
        }

        cbConvenio.addItem("Sem convênio (Particular)");
        idsConvenios.add(0);
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement st = conn.prepareStatement("SELECT idConvenio, nome FROM convenio ORDER BY nome");
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                cbConvenio.addItem(rs.getString("nome"));
                idsConvenios.add(rs.getInt("idConvenio"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar convênios: " + e.getMessage());
        }

        // Botões
        JButton btnCancelar = botaoSecundario("Cancelar");
        btnCancelar.setBounds(160, 315, 120, 36);
        p.add(btnCancelar);

        JButton btnConfirmar = botaoPrimario("Confirmar");
        btnConfirmar.setBounds(295, 315, 135, 36);
        p.add(btnConfirmar);

        btnCancelar.addActionListener(e -> dlg.dispose());

        btnConfirmar.addActionListener(e -> {
            if (cbPaciente.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(dlg, "Selecione o paciente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idPac  = idsPacientes.get(cbPaciente.getSelectedIndex());
            int idConv = idsConvenios.get(cbConvenio.getSelectedIndex());

            String dataSQL = converterData(dataBr);
            String dataHora = dataSQL + " " + horario + ":00";

            boolean ok = new ConsultaDAO().inserir(idPac, idMedico, idConv > 0 ? idConv : null, dataHora);
            if (ok) {
                JOptionPane.showMessageDialog(dlg,
                    "Consulta agendada!\n" + cbPaciente.getSelectedItem() + " — " + dataBr + " às " + horario,
                    "Agendado", JOptionPane.INFORMATION_MESSAGE);
                dlg.dispose();
                gerarEscala();
            } else {
                JOptionPane.showMessageDialog(dlg, "Erro ao salvar. Verifique a conexão.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        dlg.setVisible(true);
    }

    // ── UTILITÁRIOS ────────────────────────────────────────────────────────────

    private String converterData(String dataBr) {
        try {
            LocalDate ld = LocalDate.parse(dataBr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) { return null; }
    }

    private void addInfoLabel(JPanel p, String rotulo, String valor, int x, int y) {
        JLabel lRot = new JLabel(rotulo);
        lRot.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lRot.setForeground(ROTULO);
        lRot.setBounds(x, y, 120, 16);
        p.add(lRot);
        JLabel lVal = new JLabel(valor);
        lVal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lVal.setForeground(CREME);
        lVal.setBounds(x, y + 18, 400, 22);
        p.add(lVal);
    }

    private JLabel rotulo(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(ROTULO);
        return l;
    }

    private JLabel chip(Color bg, String texto) {
        JLabel l = new JLabel("  " + texto + "  ");
        l.setOpaque(true);
        l.setBackground(bg);
        l.setForeground(MARROM);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setBorder(BorderFactory.createLineBorder(ROTULO));
        return l;
    }

    private JButton botaoPrimario(String t) {
        JButton b = new JButton(t);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBackground(GOLD);
        b.setForeground(MARROM);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(GOLD.darker(), 1));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JButton botaoSecundario(String t) {
        JButton b = new JButton(t);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setBackground(MEDIO);
        b.setForeground(CREME);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(ROTULO, 1));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}
