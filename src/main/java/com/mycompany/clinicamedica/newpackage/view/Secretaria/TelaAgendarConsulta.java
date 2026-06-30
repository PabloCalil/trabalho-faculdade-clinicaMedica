package com.mycompany.clinicamedica.newpackage.view.Secretaria;

import Services.BDSConnection;
import Services.ConsultaDAO;
import Services.Paciente;
import Services.PacienteDAO;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelaAgendarConsulta extends JFrame {

    private JComboBox<String> cbPaciente, cbMedico, cbConvenio;
    private JTextField txtData, txtHorario;
    private JButton btnAgendar, btnLimpar, btnVoltar;

    private final List<Integer> idsPacientes  = new ArrayList<>();
    private final List<Integer> idsMedicos    = new ArrayList<>();
    private final List<Integer> idsConvenios  = new ArrayList<>();

    private static final Color MARROM  = new Color(61, 28, 6);
    private static final Color GOLD    = new Color(193, 158, 103);
    private static final Color MEDIO   = new Color(110, 102, 95);
    private static final Color CREME   = new Color(251, 251, 250);
    private static final Color ROTULO  = new Color(180, 169, 158);

    public TelaAgendarConsulta() {
        setTitle("VITA — Agendar Nova Consulta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel fundo = new JPanel(null);
        fundo.setBackground(MEDIO);
        setContentPane(fundo);

        JLabel lblTitulo = new JLabel("Agendar Nova Consulta");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(CREME);
        lblTitulo.setBounds(50, 20, 500, 38);
        fundo.add(lblTitulo);

        JPanel form = new JPanel(null);
        form.setBackground(MARROM);
        form.setBounds(50, 75, 790, 480);
        form.setBorder(new LineBorder(GOLD, 1, true));
        fundo.add(form);

        // Paciente
        addLabel(form, "Paciente:", 40, 20);
        cbPaciente = criarCombo();
        cbPaciente.setBounds(40, 45, 710, 35);
        form.add(cbPaciente);

        // Médico
        addLabel(form, "Médico / Especialista:", 40, 100);
        cbMedico = criarCombo();
        cbMedico.setBounds(40, 125, 330, 35);
        form.add(cbMedico);

        // Convênio
        addLabel(form, "Convênio:", 410, 100);
        cbConvenio = criarCombo();
        cbConvenio.setBounds(410, 125, 340, 35);
        form.add(cbConvenio);

        // Data
        addLabel(form, "Data (DD/MM/AAAA):", 40, 180);
        txtData = criarCampo();
        txtData.setBounds(40, 205, 330, 35);
        form.add(txtData);

        // Horário
        addLabel(form, "Horário (HH:MM):", 410, 180);
        txtHorario = criarCampo();
        txtHorario.setBounds(410, 205, 340, 35);
        form.add(txtHorario);

        // Aviso sobre horário
        JLabel lblAviso = new JLabel("Informe um horário disponível na agenda do médico selecionado.");
        lblAviso.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblAviso.setForeground(ROTULO);
        lblAviso.setBounds(40, 245, 680, 20);
        form.add(lblAviso);

        // Botões internos
        btnLimpar = criarBotaoSecundario("Limpar");
        btnLimpar.setBounds(410, 415, 160, 42);
        form.add(btnLimpar);

        btnAgendar = criarBotaoPrimario("Confirmar Agendamento");
        btnAgendar.setBounds(580, 415, 170, 42);
        form.add(btnAgendar);

        // Voltar (fora do form)
        btnVoltar = new JButton("← Voltar");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setBackground(new Color(180, 70, 70));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(50, 575, 130, 35);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fundo.add(btnVoltar);

        carregarDados();
        configurarEventos();
    }

    private void carregarDados() {
        // Pacientes
        cbPaciente.addItem("Selecione o paciente...");
        idsPacientes.add(0);
        for (Paciente p : new PacienteDAO().listarTodos()) {
            cbPaciente.addItem(p.getNome());
            idsPacientes.add(p.getIdPaciente());
        }

        // Médicos
        cbMedico.addItem("Selecione o médico...");
        idsMedicos.add(0);
        String sqlMedicos = "SELECT u.idUsuario, u.nome, e.nome AS especialidade "
                          + "FROM usuario u "
                          + "JOIN medico m ON m.idUsuario = u.idUsuario "
                          + "LEFT JOIN especialidade e ON e.idEspecialidade = m.idEspecialidade "
                          + "WHERE u.ativo = 1 ORDER BY u.nome";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sqlMedicos);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String esp = rs.getString("especialidade");
                String label = rs.getString("nome") + (esp != null ? " (" + esp + ")" : "");
                cbMedico.addItem(label);
                idsMedicos.add(rs.getInt("idUsuario"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar médicos: " + e.getMessage());
        }

        // Convênios
        cbConvenio.addItem("Sem convênio (Particular)");
        idsConvenios.add(0);
        String sqlConvenios = "SELECT idConvenio, nome FROM convenio ORDER BY nome";
        try (Connection conn = BDSConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sqlConvenios);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cbConvenio.addItem(rs.getString("nome"));
                idsConvenios.add(rs.getInt("idConvenio"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar convênios: " + e.getMessage());
        }
    }

    private void configurarEventos() {
        btnAgendar.addActionListener(e -> agendar());

        btnLimpar.addActionListener(e -> {
            cbPaciente.setSelectedIndex(0);
            cbMedico.setSelectedIndex(0);
            cbConvenio.setSelectedIndex(0);
            txtData.setText("");
            txtHorario.setText("");
        });

        btnVoltar.addActionListener(e -> dispose());
    }

    private void agendar() {
        int idxPaciente = cbPaciente.getSelectedIndex();
        int idxMedico   = cbMedico.getSelectedIndex();
        String data     = txtData.getText().trim();
        String hora     = txtHorario.getText().trim();

        if (idxPaciente == 0 || idxMedico == 0 || data.isEmpty() || hora.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Preencha Paciente, Médico, Data e Horário.", "Campos obrigatórios",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String dataHora = converterDataHora(data, hora);
        if (dataHora == null) {
            JOptionPane.showMessageDialog(this,
                "Data ou horário inválido.\nUse DD/MM/AAAA e HH:MM.", "Formato inválido",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idPaciente = idsPacientes.get(idxPaciente);
        int idMedico   = idsMedicos.get(idxMedico);
        int idConvenio = idsConvenios.get(cbConvenio.getSelectedIndex());

        boolean ok = new ConsultaDAO().inserir(idPaciente, idMedico, idConvenio > 0 ? idConvenio : null, dataHora);

        if (ok) {
            JOptionPane.showMessageDialog(this,
                "Consulta agendada com sucesso!\n\n"
                + "Paciente: " + cbPaciente.getSelectedItem() + "\n"
                + "Médico: "   + cbMedico.getSelectedItem()   + "\n"
                + "Data/Hora: " + data + " às " + hora,
                "Agendado", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar no banco de dados. Verifique a conexão.",
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String converterDataHora(String data, String hora) {
        try {
            LocalDate ld = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String[] partes = hora.split(":");
            if (partes.length != 2) return null;
            Integer.parseInt(partes[0]);
            Integer.parseInt(partes[1]);
            return ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + hora + ":00";
        } catch (Exception e) {
            return null;
        }
    }

    private void addLabel(JPanel pai, String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(ROTULO);
        lbl.setBounds(x, y, 350, 20);
        pai.add(lbl);
    }

    private JComboBox<String> criarCombo() {
        JComboBox<String> cb = new JComboBox<>();
        cb.setBackground(MEDIO);
        cb.setForeground(CREME);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cb.setBorder(new LineBorder(GOLD, 1));
        return cb;
    }

    private JTextField criarCampo() {
        JTextField f = new JTextField();
        f.setBackground(MEDIO);
        f.setForeground(CREME);
        f.setCaretColor(CREME);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(MEDIO.brighter(), 1),
            new EmptyBorder(0, 10, 0, 10)));
        return f;
    }

    private JButton criarBotaoPrimario(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBackground(GOLD);
        b.setForeground(MARROM);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(GOLD.darker(), 1));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JButton criarBotaoSecundario(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setBackground(MEDIO);
        b.setForeground(CREME);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(ROTULO, 1));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}
