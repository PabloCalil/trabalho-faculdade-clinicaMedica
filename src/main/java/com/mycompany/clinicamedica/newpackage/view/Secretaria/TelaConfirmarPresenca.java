package com.mycompany.clinicamedica.newpackage.view.Secretaria;

import Services.Consulta;
import Services.ConsultaDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TelaConfirmarPresenca extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private List<Consulta> consultas;

    private static final Color MARROM = new Color(61, 28, 6);
    private static final Color GOLD   = new Color(193, 158, 103);
    private static final Color FUNDO  = new Color(244, 241, 234);

    public TelaConfirmarPresenca() {
        setTitle("VITA — Confirmar Presença (Check-in)");
        setSize(960, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(FUNDO);
        setContentPane(principal);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MARROM);
        header.setBorder(new EmptyBorder(18, 40, 18, 40));

        JLabel lblTitulo = new JLabel("Check-in — Confirmar Chegada do Paciente");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        header.add(lblTitulo, BorderLayout.WEST);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBackground(new Color(180, 70, 70));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.addActionListener(e -> dispose());
        header.add(btnFechar, BorderLayout.EAST);

        principal.add(header, BorderLayout.NORTH);

        // Corpo
        JPanel corpo = new JPanel(new BorderLayout(0, 14));
        corpo.setBackground(FUNDO);
        corpo.setBorder(new EmptyBorder(24, 40, 24, 40));

        JLabel lblInfo = new JLabel("Consultas agendadas para hoje — selecione e confirme a chegada:");
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblInfo.setForeground(MARROM);
        corpo.add(lblInfo, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"#", "Paciente", "Médico", "Horário", "Convênio", "Status"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        tabela = new JTable(modelo);
        tabela.setRowHeight(30);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(MARROM);
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.setSelectionBackground(GOLD);
        tabela.setSelectionForeground(MARROM);
        tabela.setGridColor(new Color(220, 215, 205));
        tabela.getColumnModel().getColumn(0).setMaxWidth(45);
        tabela.getColumnModel().getColumn(3).setMaxWidth(90);
        tabela.getColumnModel().getColumn(5).setMaxWidth(110);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(GOLD));
        corpo.add(scroll, BorderLayout.CENTER);

        // Rodapé com botões
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rodape.setBackground(FUNDO);

        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.setBackground(MARROM);
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAtualizar.addActionListener(e -> carregarConsultas());

        JButton btnConfirmar = new JButton("✔  Confirmar Presença");
        btnConfirmar.setBackground(new Color(60, 140, 60));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmar.addActionListener(e -> confirmarPresenca());

        rodape.add(btnAtualizar);
        rodape.add(btnConfirmar);
        corpo.add(rodape, BorderLayout.SOUTH);

        principal.add(corpo, BorderLayout.CENTER);

        carregarConsultas();
    }

    private void carregarConsultas() {
        modelo.setRowCount(0);
        consultas = new ConsultaDAO().listarParaCheckin();

        if (consultas.isEmpty()) {
            modelo.addRow(new Object[]{"", "Nenhuma consulta pendente de check-in para hoje.", "", "", "", ""});
            return;
        }

        int i = 1;
        for (Consulta c : consultas) {
            String horario = c.getDataHora() != null && c.getDataHora().length() >= 16
                    ? c.getDataHora().substring(11, 16)
                    : c.getDataHora();
            String convenio = c.getNomeConvenio() != null ? c.getNomeConvenio() : "Particular";
            modelo.addRow(new Object[]{i++, c.getNomePaciente(), c.getNomeMedico(), horario, convenio, c.getStatus()});
        }
    }

    private void confirmarPresenca() {
        int linha = tabela.getSelectedRow();
        if (linha < 0 || consultas == null || consultas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Selecione uma consulta na lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Consulta c = consultas.get(linha);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Confirmar chegada de: " + c.getNomePaciente() + "?",
            "Confirmar Presença", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = new ConsultaDAO().atualizarStatus(c.getIdConsulta(), "Presente");
        if (ok) {
            JOptionPane.showMessageDialog(this,
                "Presença confirmada!\n" + c.getNomePaciente() + " registrado como Presente.",
                "Check-in realizado", JOptionPane.INFORMATION_MESSAGE);
            carregarConsultas();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao atualizar status. Verifique a conexão.",
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
