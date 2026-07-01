package com.mycompany.clinicamedica.newpackage.view.Secretaria;

import Services.BDSConnection;
import Services.ConsultaDAO;
import Services.Paciente;
import Services.PacienteDAO;
import com.mycompany.clinicamedica.newpackage.view.ui.Tema;
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

    // ── Filtros ───────────────────────────────────────────────────────────────
    private JComboBox<String> cbMedico;
    private JSpinner           spinnerData;

    // ── Tabela ────────────────────────────────────────────────────────────────
    private JTable             tabela;
    private DefaultTableModel  modelo;

    // ── Botões de ação ────────────────────────────────────────────────────────
    private JButton btnGerar, btnAgendar, btnCheckin, btnEditar, btnCancelar, btnAtualizar;

    // ── Rodapé ────────────────────────────────────────────────────────────────
    private JLabel lblResumo;

    // ── Estado ────────────────────────────────────────────────────────────────
    private final List<Integer>      idsMedicos             = new ArrayList<>();
    private final Map<String, Integer> idConsultaPorHorario = new LinkedHashMap<>();
    private final Map<String, String>  pacientePorHorario   = new LinkedHashMap<>();

    // ── Paleta ────────────────────────────────────────────────────────────────
    private static final Color MARROM  = Tema.MARROM_ESCURO;
    private static final Color GOLD    = Tema.GOLD;
    private static final Color MEDIO   = Tema.TOM_MEDIO;
    private static final Color CREME   = new Color(251, 251, 250);
    private static final Color ROTULO  = new Color(180, 169, 158);
    private static final Color C_LIVRE   = new Color(210, 235, 210);
    private static final Color C_OCUPADO = new Color(245, 220, 215);
    private static final Color C_PRESENTE = new Color(200, 220, 245);

    private static final String[] HORARIOS = {
        "08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30",
        "13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30"
    };

    // ═════════════════════════════════════════════════════════════════════════
    public TelaConsultarEscalaMedica() {
        setTitle("Health Equilibrium — Agenda Médica");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(860, 540));
        setSize(1080, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        // Raiz usa BorderLayout — a tabela ficará em CENTER e vai esticar
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(MEDIO);
        setContentPane(root);

        root.add(criarHeader(),   BorderLayout.NORTH);
        root.add(criarConteudo(), BorderLayout.CENTER);

        carregarMedicos();
        configurarEventos();
    }

    // ── HEADER (altura fixa, largura flexível) ────────────────────────────────
    private JPanel criarHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(MARROM);
        h.setBorder(new CompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, GOLD),
            new EmptyBorder(14, 28, 14, 28)));

        JLabel lbl = new JLabel("Agenda Médica — Agendamento e Atendimento");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setForeground(Color.WHITE);
        h.add(lbl, BorderLayout.WEST);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(180, 70, 70));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.addActionListener(e -> dispose());
        h.add(btnFechar, BorderLayout.EAST);

        return h;
    }

    // ── CONTEÚDO PRINCIPAL (BorderLayout) ─────────────────────────────────────
    private JPanel criarConteudo() {
        JPanel main = new JPanel(new BorderLayout(0, 10));
        main.setBackground(MEDIO);
        main.setBorder(new EmptyBorder(14, 18, 14, 18));

        main.add(criarFiltros(),   BorderLayout.NORTH);
        main.add(criarTabela(),    BorderLayout.CENTER);  // ← estica com a janela
        main.add(criarRodape(),    BorderLayout.SOUTH);

        return main;
    }

    // ── BARRA DE FILTROS ──────────────────────────────────────────────────────
    private JPanel criarFiltros() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(45, 20, 4));
        p.setBorder(new CompoundBorder(
            new LineBorder(GOLD, 1, true),
            new EmptyBorder(10, 16, 10, 16)));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(2, 6, 2, 6);
        g.fill   = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;

        // Linha de rótulos
        g.gridy = 0; g.gridx = 0; g.weightx = 0;
        p.add(rotulo("Médico / Especialista:"), g);
        g.gridx = 1; g.weightx = 0;
        p.add(rotulo("Data:"), g);

        // Linha de campos
        g.gridy = 1; g.gridx = 0; g.weightx = 1.0;
        cbMedico = new JComboBox<>();
        estilizarCombo(cbMedico);
        p.add(cbMedico, g);

        g.gridx = 1; g.weightx = 0;
        SpinnerDateModel dm = new SpinnerDateModel(new java.util.Date(), null, null, Calendar.DAY_OF_MONTH);
        spinnerData = new JSpinner(dm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy");
        spinnerData.setEditor(de);
        de.getTextField().setBackground(MEDIO);
        de.getTextField().setForeground(CREME);
        de.getTextField().setCaretColor(CREME);
        de.getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        spinnerData.setBackground(MEDIO);
        spinnerData.setBorder(new LineBorder(GOLD, 1));
        spinnerData.setPreferredSize(new Dimension(160, 32));
        p.add(spinnerData, g);

        g.gridx = 2; g.weightx = 0; g.gridheight = 2; g.anchor = GridBagConstraints.SOUTH;
        btnGerar = botaoPrimario("Ver Agenda");
        btnGerar.setPreferredSize(new Dimension(130, 32));
        p.add(btnGerar, g);

        return p;
    }

    // ── ÁREA DA TABELA (cresce com a janela) ──────────────────────────────────
    private JPanel criarTabela() {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setBackground(MEDIO);

        // Legenda
        JPanel legenda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        legenda.setBackground(MEDIO);
        legenda.add(chip(C_LIVRE,    "Livre"));
        legenda.add(chip(C_OCUPADO,  "Ocupado"));
        legenda.add(chip(C_PRESENTE, "Aguardando Chamada"));
        p.add(legenda, BorderLayout.NORTH);

        // Tabela
        String[] cols = {"Horário", "Situação", "Paciente", "Convênio", "Status"};
        modelo = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modelo);
        tabela.setRowHeight(28);
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
        tabela.getColumnModel().getColumn(4).setMaxWidth(120);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (!sel) {
                    String sit = String.valueOf(t.getValueAt(row, 1));
                    String sts = String.valueOf(t.getValueAt(row, 4));
                    if ("Livre".equals(sit))                     c.setBackground(C_LIVRE);
                    else if ("Aguardando Chamada".equals(sts))   c.setBackground(C_PRESENTE);
                    else                                         c.setBackground(C_OCUPADO);
                    c.setForeground(MARROM);
                }
                return c;
            }
        };
        for (int i = 0; i < tabela.getColumnCount(); i++)
            tabela.getColumnModel().getColumn(i).setCellRenderer(renderer);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(GOLD, 1));
        p.add(scroll, BorderLayout.CENTER);

        return p;
    }

    // ── RODAPÉ COM BOTÕES ─────────────────────────────────────────────────────
    private JPanel criarRodape() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(MEDIO);
        p.setBorder(new EmptyBorder(6, 0, 0, 0));

        lblResumo = new JLabel("Selecione o médico, a data e clique em \"Ver Agenda\".");
        lblResumo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblResumo.setForeground(CREME);
        p.add(lblResumo, BorderLayout.WEST);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.setBackground(MEDIO);

        btnAtualizar = botaoSecundario("Atualizar");
        btnCheckin  = new JButton("Confirmar Presença");
        btnCheckin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCheckin.setBackground(new Color(55, 130, 55));
        btnCheckin.setForeground(Color.WHITE);
        btnCheckin.setFocusPainted(false);
        btnCheckin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEditar   = botaoSecundario("Editar");
        btnCancelar = new JButton("Cancelar Consulta");
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancelar.setBackground(new Color(170, 50, 50));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAgendar  = botaoPrimario("Agendar");

        // Estado inicial: todos desabilitados (exceto Atualizar)
        btnCheckin.setEnabled(false);
        btnEditar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnAgendar.setEnabled(false);

        botoes.add(btnAtualizar);
        botoes.add(btnCancelar);
        botoes.add(btnEditar);
        botoes.add(btnCheckin);
        botoes.add(btnAgendar);
        p.add(botoes, BorderLayout.EAST);

        return p;
    }

    // ── CARGA DE MÉDICOS ──────────────────────────────────────────────────────
    private void carregarMedicos() {
        cbMedico.addItem("Selecione o médico...");
        idsMedicos.add(0);
        String sql = "SELECT u.idUsuario, u.nome, e.nome AS esp "
                   + "FROM usuario u JOIN medico m ON m.idUsuario = u.idUsuario "
                   + "LEFT JOIN especialidade e ON e.idEspecialidade = m.idEspecialidade "
                   + "WHERE u.ativo = 1 ORDER BY u.nome";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                String esp = rs.getString("esp");
                cbMedico.addItem(rs.getString("nome") + (esp != null ? " (" + esp + ")" : ""));
                idsMedicos.add(rs.getInt("idUsuario"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar médicos: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── EVENTOS ───────────────────────────────────────────────────────────────
    private void configurarEventos() {
        btnGerar.addActionListener(e -> gerarEscala());
        btnAtualizar.addActionListener(e -> gerarEscala());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            atualizarBotoes();
        });

        btnAgendar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) return;
            String horario = (String) modelo.getValueAt(row, 0);
            int idMedico = idsMedicos.get(cbMedico.getSelectedIndex());
            String dataBr = new SimpleDateFormat("dd/MM/yyyy")
                .format(((SpinnerDateModel) spinnerData.getModel()).getDate());
            abrirDialogAgendar(-1, idMedico, 0, 0, (String) cbMedico.getSelectedItem(), dataBr, horario);
        });

        btnCheckin.addActionListener(e -> confirmarPresenca());
        btnEditar.addActionListener(e -> editarConsulta());
        btnCancelar.addActionListener(e -> cancelarConsulta());
    }

    private void atualizarBotoes() {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            btnAgendar.setEnabled(false);
            btnCheckin.setEnabled(false);
            btnEditar.setEnabled(false);
            btnCancelar.setEnabled(false);
            return;
        }
        String sit = (String) modelo.getValueAt(row, 1);
        String sts = (String) modelo.getValueAt(row, 4);
        boolean livre     = "Livre".equals(sit);
        boolean concluido = "Consulta Finalizada".equals(sts);

        btnAgendar.setEnabled(livre);
        btnCheckin.setEnabled(!livre && "Agendado".equals(sts));
        btnEditar.setEnabled(!livre && !concluido);
        btnCancelar.setEnabled(!livre && !concluido);
    }

    // ── GERAR GRADE ───────────────────────────────────────────────────────────
    private void gerarEscala() {
        if (cbMedico.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um médico.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idMedico = idsMedicos.get(cbMedico.getSelectedIndex());
        java.util.Date dataSelecionada = ((SpinnerDateModel) spinnerData.getModel()).getDate();
        String dataSQL = new SimpleDateFormat("yyyy-MM-dd").format(dataSelecionada);
        String dataBr  = new SimpleDateFormat("dd/MM/yyyy").format(dataSelecionada);

        idConsultaPorHorario.clear();
        pacientePorHorario.clear();

        Map<String, Object[]> agendados = new LinkedHashMap<>();
        String sql = "SELECT c.idConsulta, TIME(c.dataHora) AS hora, "
                   + "p.nome AS paciente, cv.nome AS convenio, s.nome AS status "
                   + "FROM consulta c "
                   + "JOIN paciente p ON p.idPaciente = c.idPaciente "
                   + "JOIN status s ON s.idStatus = c.idStatus "
                   + "LEFT JOIN convenio cv ON cv.idConvenio = c.idConvenio "
                   + "WHERE c.idUsuario = ? AND DATE(c.dataHora) = ? AND s.nome != 'Cancelado'";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, idMedico);
            st.setString(2, dataSQL);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String hora = rs.getString("hora").substring(0, 5);
                    String conv = rs.getString("convenio");
                    agendados.put(hora, new Object[]{
                        rs.getInt("idConsulta"), rs.getString("paciente"),
                        conv != null ? conv : "Particular", rs.getString("status")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar agenda: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        modelo.setRowCount(0);
        int livres = 0, ocp = 0;
        for (String h : HORARIOS) {
            if (agendados.containsKey(h)) {
                Object[] d = agendados.get(h);
                modelo.addRow(new Object[]{h, "Ocupado", d[1], d[2], d[3]});
                idConsultaPorHorario.put(h, (Integer) d[0]);
                pacientePorHorario.put(h, (String) d[1]);
                ocp++;
            } else {
                modelo.addRow(new Object[]{h, "Livre", "—", "—", "—"});
                livres++;
            }
        }

        atualizarBotoes();
        tabela.clearSelection();
        lblResumo.setText(String.format("%s — %s  |  %d livre(s)  |  %d ocupado(s)",
            cbMedico.getSelectedItem(), dataBr, livres, ocp));
    }

    // ── CONFIRMAR PRESENÇA ────────────────────────────────────────────────────
    private void confirmarPresenca() {
        int row = tabela.getSelectedRow();
        if (row < 0) return;
        String horario  = (String) modelo.getValueAt(row, 0);
        String paciente = (String) modelo.getValueAt(row, 2);
        Integer idC = idConsultaPorHorario.get(horario);
        if (idC == null) return;

        int ok = JOptionPane.showConfirmDialog(this,
            "Confirmar chegada de: " + paciente + "?", "Check-in", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;

        if (new ConsultaDAO().atualizarStatus(idC, "Aguardando Chamada")) {
            JOptionPane.showMessageDialog(this, paciente + " registrado como presente (Aguardando Chamada).",
                "Check-in realizado", JOptionPane.INFORMATION_MESSAGE);
            gerarEscala();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar. Verifique a conexão.",
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── CANCELAR CONSULTA ─────────────────────────────────────────────────────
    private void cancelarConsulta() {
        int row = tabela.getSelectedRow();
        if (row < 0) return;
        String horario  = (String) modelo.getValueAt(row, 0);
        String paciente = (String) modelo.getValueAt(row, 2);
        Integer idC = idConsultaPorHorario.get(horario);
        if (idC == null) return;

        int ok = JOptionPane.showConfirmDialog(this,
            "Cancelar a consulta de " + paciente + " às " + horario + "?\nEssa ação não pode ser desfeita.",
            "Cancelar Consulta", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok != JOptionPane.YES_OPTION) return;

        if (new ConsultaDAO().atualizarStatus(idC, "Cancelado")) {
            JOptionPane.showMessageDialog(this, "Consulta cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            gerarEscala();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cancelar. Verifique a conexão.",
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── EDITAR CONSULTA ───────────────────────────────────────────────────────
    private void editarConsulta() {
        int row = tabela.getSelectedRow();
        if (row < 0) return;
        String horario = (String) modelo.getValueAt(row, 0);
        Integer idC = idConsultaPorHorario.get(horario);
        if (idC == null) return;

        int[] ids = buscarDadosConsulta(idC); // [idPaciente, idMedico, idConvenio]
        if (ids == null) return;

        java.util.Date dataSelecionada = ((SpinnerDateModel) spinnerData.getModel()).getDate();
        String dataBr = new SimpleDateFormat("dd/MM/yyyy").format(dataSelecionada);

        abrirDialogAgendar(idC, ids[1], ids[0], ids[2], (String) cbMedico.getSelectedItem(), dataBr, horario);
    }

    private int[] buscarDadosConsulta(int idConsulta) {
        String sql = "SELECT idPaciente, idUsuario, COALESCE(idConvenio, 0) AS idConvenio FROM consulta WHERE idConsulta = ?";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, idConsulta);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next())
                    return new int[]{rs.getInt("idPaciente"), rs.getInt("idUsuario"), rs.getInt("idConvenio")};
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar consulta: " + e.getMessage());
        }
        return null;
    }

    // ── DIALOG AGENDAR / EDITAR ───────────────────────────────────────────────
    /**
     * idConsultaEditar == -1 → novo agendamento; > 0 → edição.
     * Para novos, passe idPacienteAtual=0 e idConvenioAtual=0.
     */
    private void abrirDialogAgendar(int idConsultaEditar, int idMedicoAtual,
                                     int idPacienteAtual, int idConvenioAtual,
                                     String nomeMedico, String dataBr, String horarioAtual) {
        boolean editando = idConsultaEditar > 0;
        JDialog dlg = new JDialog(this, editando ? "Editar Consulta" : "Agendar Consulta", true);
        dlg.setSize(520, 470);
        dlg.setLocationRelativeTo(this);
        dlg.setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(MARROM);
        dlg.setContentPane(root);

        // Cabeçalho do dialog
        JPanel dlgHeader = new JPanel(new BorderLayout());
        dlgHeader.setBackground(new Color(45, 20, 4));
        dlgHeader.setBorder(new EmptyBorder(12, 20, 12, 20));
        JLabel dlgTitulo = new JLabel(editando ? "Editar dados da consulta" : "Nova Consulta");
        dlgTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dlgTitulo.setForeground(GOLD);
        dlgHeader.add(dlgTitulo, BorderLayout.WEST);
        root.add(dlgHeader, BorderLayout.NORTH);

        // Formulário
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(MARROM);
        form.setBorder(new EmptyBorder(16, 20, 8, 20));
        root.add(form, BorderLayout.CENTER);

        GridBagConstraints g = new GridBagConstraints();
        g.insets  = new Insets(4, 4, 4, 4);
        g.fill    = GridBagConstraints.HORIZONTAL;
        g.anchor  = GridBagConstraints.WEST;
        g.weightx = 1.0;

        // Médico (read-only — o médico é o da grade selecionada)
        g.gridx = 0; g.gridy = 0; g.gridwidth = 1; g.weightx = 0;
        form.add(rotulo("Médico:"), g);
        g.gridx = 1; g.weightx = 1.0; g.gridwidth = 3;
        JLabel lblMedicoVal = new JLabel(nomeMedico);
        lblMedicoVal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMedicoVal.setForeground(CREME);
        form.add(lblMedicoVal, g);

        // Data
        g.gridy = 1; g.gridx = 0; g.gridwidth = 1; g.weightx = 0;
        form.add(rotulo("Data:"), g);
        g.gridx = 1; g.weightx = 0.5; g.gridwidth = 1;
        SpinnerDateModel dmDlg = new SpinnerDateModel(
            parseDateBr(dataBr), null, null, Calendar.DAY_OF_MONTH);
        JSpinner spData = new JSpinner(dmDlg);
        JSpinner.DateEditor edData = new JSpinner.DateEditor(spData, "dd/MM/yyyy");
        spData.setEditor(edData);
        estilizarSpinner(spData, edData);
        form.add(spData, g);

        // Horário
        g.gridx = 2; g.weightx = 0;
        form.add(rotulo("Horário:"), g);
        g.gridx = 3; g.weightx = 0.5;
        JComboBox<String> cbHorario = new JComboBox<>(HORARIOS);
        cbHorario.setSelectedItem(horarioAtual);
        estilizarCombo(cbHorario);
        form.add(cbHorario, g);

        // Paciente
        g.gridy = 2; g.gridx = 0; g.gridwidth = 1; g.weightx = 0;
        form.add(rotulo("Paciente:"), g);
        g.gridx = 1; g.weightx = 1.0; g.gridwidth = 3;
        JComboBox<String> cbPaciente = new JComboBox<>();
        estilizarCombo(cbPaciente);
        form.add(cbPaciente, g);

        // Convênio
        g.gridy = 3; g.gridx = 0; g.gridwidth = 1; g.weightx = 0;
        form.add(rotulo("Convênio:"), g);
        g.gridx = 1; g.weightx = 1.0; g.gridwidth = 3;
        JComboBox<String> cbConvenio = new JComboBox<>();
        estilizarCombo(cbConvenio);
        form.add(cbConvenio, g);

        // Carrega listas do banco
        List<Integer> idsPac = new ArrayList<>(), idsConv = new ArrayList<>();

        cbPaciente.addItem("Selecione o paciente...");
        idsPac.add(0);
        int selPac = 0;
        List<Paciente> pacientes = new PacienteDAO().listarTodos();
        for (int i = 0; i < pacientes.size(); i++) {
            cbPaciente.addItem(pacientes.get(i).getNome());
            idsPac.add(pacientes.get(i).getIdPaciente());
            if (editando && pacientes.get(i).getIdPaciente() == idPacienteAtual) selPac = i + 1;
        }
        cbPaciente.setSelectedIndex(selPac);

        cbConvenio.addItem("Sem convênio (Particular)");
        idsConv.add(0);
        int selConv = 0;
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement st = conn.prepareStatement("SELECT idConvenio, nome FROM convenio ORDER BY nome");
             ResultSet rs = st.executeQuery()) {
            int idx = 1;
            while (rs.next()) {
                cbConvenio.addItem(rs.getString("nome"));
                idsConv.add(rs.getInt("idConvenio"));
                if (editando && rs.getInt("idConvenio") == idConvenioAtual) selConv = idx;
                idx++;
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao carregar convênios: " + ex.getMessage());
        }
        cbConvenio.setSelectedIndex(selConv);

        // Botões do dialog
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btns.setBackground(MARROM);
        JButton btnCancelarDlg = botaoSecundario("Cancelar");
        JButton btnConfirmar   = botaoPrimario(editando ? "Salvar Alterações" : "Confirmar");
        btns.add(btnCancelarDlg);
        btns.add(btnConfirmar);
        root.add(btns, BorderLayout.SOUTH);

        btnCancelarDlg.addActionListener(e -> dlg.dispose());

        btnConfirmar.addActionListener(e -> {
            if (cbPaciente.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(dlg, "Selecione o paciente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idPac  = idsPac.get(cbPaciente.getSelectedIndex());
            int idConv = idsConv.get(cbConvenio.getSelectedIndex());
            java.util.Date dataDlg = ((SpinnerDateModel) spData.getModel()).getDate();
            String dataSQL  = new SimpleDateFormat("yyyy-MM-dd").format(dataDlg);
            String horaSQL  = (String) cbHorario.getSelectedItem();
            String dataHora = dataSQL + " " + horaSQL + ":00";

            boolean ok;
            ConsultaDAO dao = new ConsultaDAO();
            if (editando) {
                ok = dao.atualizar(idConsultaEditar, idPac, idMedicoAtual, idConv > 0 ? idConv : null, dataHora);
            } else {
                ok = dao.inserir(idPac, idMedicoAtual, idConv > 0 ? idConv : null, dataHora);
            }

            if (ok) {
                JOptionPane.showMessageDialog(dlg,
                    (editando ? "Consulta atualizada!" : "Consulta agendada!") + "\n"
                    + cbPaciente.getSelectedItem() + " — "
                    + new SimpleDateFormat("dd/MM/yyyy").format(dataDlg) + " às " + horaSQL,
                    editando ? "Salvo" : "Agendado", JOptionPane.INFORMATION_MESSAGE);
                dlg.dispose();
                gerarEscala();
            } else {
                JOptionPane.showMessageDialog(dlg, "Erro ao salvar. Verifique a conexão.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        dlg.setVisible(true);
    }

    // ── UTILITÁRIOS ───────────────────────────────────────────────────────────

    private java.util.Date parseDateBr(String dataBr) {
        try { return new SimpleDateFormat("dd/MM/yyyy").parse(dataBr); }
        catch (Exception e) { return new java.util.Date(); }
    }

    private void estilizarCombo(JComboBox<?> cb) {
        cb.setBackground(MEDIO);
        cb.setForeground(CREME);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cb.setBorder(new LineBorder(GOLD, 1));
    }

    private void estilizarSpinner(JSpinner sp, JSpinner.DateEditor ed) {
        sp.setBackground(MEDIO);
        sp.setBorder(new LineBorder(GOLD, 1));
        ed.getTextField().setBackground(MEDIO);
        ed.getTextField().setForeground(CREME);
        ed.getTextField().setCaretColor(CREME);
        ed.getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 13));
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
