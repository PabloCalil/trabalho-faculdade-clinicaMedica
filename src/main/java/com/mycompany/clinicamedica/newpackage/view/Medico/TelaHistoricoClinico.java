package com.mycompany.clinicamedica.newpackage.view.Medico;

import Services.Prontuario;
import Services.ProntuarioDAO;
import com.mycompany.clinicamedica.newpackage.view.ui.Tema;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TelaHistoricoClinico extends JFrame {

    private final ProntuarioDAO dao = new ProntuarioDAO();
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private final int idPaciente;

    private final Color marromEscuro = Tema.MARROM_ESCURO;
    private final Color corGold      = Tema.GOLD;
    private final Color fundoClaro   = Tema.FUNDO_CLARO;

    public TelaHistoricoClinico(String nomePaciente, int idPaciente) {
        this.idPaciente = idPaciente;

        setTitle("Health Equilibrium - Linha do Tempo e Histórico Clínico");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel p = new JPanel(null);
        p.setBackground(fundoClaro);
        setContentPane(p);

        // --- HEADER ---
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 750, 70);
        header.setBackground(marromEscuro);
        p.add(header);

        JLabel lblTitulo = new JLabel("HISTÓRICO DE EVOLUÇÕES: " + nomePaciente.toUpperCase());
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(20, 22, 710, 25);
        header.add(lblTitulo);

        // --- TABELA ---
        String[] colunas = {"Data", "CID-10", "HDA (Queixa)", "Conduta", "PA", "FC", "Temp"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(28);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setBackground(fundoClaro);
        tabela.setSelectionBackground(corGold);
        tabela.setSelectionForeground(marromEscuro);
        tabela.getTableHeader().setBackground(marromEscuro);
        tabela.getTableHeader().setForeground(corGold);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabela.getColumnModel().getColumn(0).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(180);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(50);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(25, 90, 700, 330);
        scroll.setBorder(new LineBorder(corGold));
        p.add(scroll);

        // --- PAINEL DETALHE (ao clicar na linha) ---
        JTextArea txtDetalhe = new JTextArea();
        txtDetalhe.setEditable(false);
        txtDetalhe.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDetalhe.setBackground(new Color(255, 252, 245));
        txtDetalhe.setLineWrap(true);
        txtDetalhe.setWrapStyleWord(true);
        txtDetalhe.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(corGold), "Detalhes do Registro Selecionado"));

        JScrollPane scrollDetalhe = new JScrollPane(txtDetalhe);
        scrollDetalhe.setBounds(25, 430, 700, 70);
        scrollDetalhe.setBorder(new LineBorder(corGold));
        p.add(scrollDetalhe);

        // --- BOTÃO FECHAR ---
        JButton btnFechar = new JButton("FECHAR HISTÓRICO");
        btnFechar.setBounds(25, 508, 700, 35);
        btnFechar.setBackground(Color.WHITE);
        btnFechar.setForeground(marromEscuro);
        btnFechar.setBorder(new LineBorder(corGold, 1));
        btnFechar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnFechar.setFocusPainted(false);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        p.add(btnFechar);

        // ================================================================
        // EVENTOS
        // ================================================================
        carregarHistorico();

        // Ao selecionar linha mostra detalhes completos
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tabela.getSelectedRow();
            if (row < 0) return;

            String hda     = nvl(modeloTabela.getValueAt(row, 2));
            String conduta = nvl(modeloTabela.getValueAt(row, 3));
            txtDetalhe.setText("HDA: " + hda + "\nConduta: " + conduta);
        });

        btnFechar.addActionListener(e -> this.dispose());
    }

    private void carregarHistorico() {
        modeloTabela.setRowCount(0);
        List<Prontuario> lista = dao.listarHistoricoPorPaciente(idPaciente);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nenhum registro clínico encontrado para este paciente.",
                "Histórico Vazio", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Prontuario pron : lista) {
            // Formata a data para exibição
            String data = pron.getDataCriacao() != null
                        ? pron.getDataCriacao().substring(0, 10) : "-";

            // Resume HDA para caber na coluna
            String hdaResumida = pron.getHistorico() != null && pron.getHistorico().length() > 40
                               ? pron.getHistorico().substring(0, 40) + "..."
                               : nvl(pron.getHistorico());

            String condutaResumida = pron.getConduta() != null && pron.getConduta().length() > 40
                                   ? pron.getConduta().substring(0, 40) + "..."
                                   : nvl(pron.getConduta());

            modeloTabela.addRow(new Object[]{
                data,
                nvl(pron.getCid10()),
                hdaResumida,
                condutaResumida,
                nvl(pron.getPressaoArterial()),
                nvl(pron.getFrequenciaCardiaca()),
                nvl(pron.getTemperatura())
            });
        }
    }

    private String nvl(Object valor) {
        return valor != null ? valor.toString() : "-";
    }
}