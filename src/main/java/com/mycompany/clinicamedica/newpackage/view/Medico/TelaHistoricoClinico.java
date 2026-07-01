package com.mycompany.clinicamedica.newpackage.view.Medico;

import Services.Prontuario;
import Services.ProntuarioDAO;
import Services.Receita;
import Services.ReceituarioDAO;
import com.mycompany.clinicamedica.newpackage.view.ui.Tema;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TelaHistoricoClinico extends JFrame {

    private final ProntuarioDAO dao = new ProntuarioDAO();
    private final ReceituarioDAO receituarioDAO = new ReceituarioDAO();
    private DefaultTableModel modeloTabela;
    private DefaultTableModel modeloReceitas;
    private JTable tabela;
    private JTable tabelaReceitas;
    private final int idPaciente;

    private final Color marromEscuro = Tema.MARROM_ESCURO;
    private final Color corGold      = Tema.GOLD;
    private final Color fundoClaro   = Tema.FUNDO_CLARO;

    public TelaHistoricoClinico(String nomePaciente, int idPaciente) {
        this.idPaciente = idPaciente;

        setTitle("Health Equilibrium - Linha do Tempo e Histórico Clínico");
        setSize(780, 660);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel p = new JPanel(null);
        p.setBackground(fundoClaro);
        setContentPane(p);

        // --- HEADER ---
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 780, 70);
        header.setBackground(marromEscuro);
        p.add(header);

        JLabel lblTitulo = new JLabel("HISTÓRICO DE EVOLUÇÕES: " + nomePaciente.toUpperCase());
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(20, 22, 740, 25);
        header.add(lblTitulo);

        JLabel lblSecEvolucoes = new JLabel("Evoluções / Prontuários");
        lblSecEvolucoes.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSecEvolucoes.setForeground(marromEscuro);
        lblSecEvolucoes.setBounds(25, 78, 400, 18);
        p.add(lblSecEvolucoes);

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
        scroll.setBounds(25, 98, 730, 230);
        scroll.setBorder(new LineBorder(corGold));
        p.add(scroll);

        // --- SEÇÃO RECEITAS ---
        JLabel lblSecReceitas = new JLabel("Receitas / Documentos Emitidos");
        lblSecReceitas.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSecReceitas.setForeground(marromEscuro);
        lblSecReceitas.setBounds(25, 338, 400, 18);
        p.add(lblSecReceitas);

        String[] colReceitas = {"Data", "Documento Emitido"};
        modeloReceitas = new DefaultTableModel(colReceitas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaReceitas = new JTable(modeloReceitas);
        tabelaReceitas.setRowHeight(26);
        tabelaReceitas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaReceitas.setBackground(fundoClaro);
        tabelaReceitas.setSelectionBackground(corGold);
        tabelaReceitas.setSelectionForeground(marromEscuro);
        tabelaReceitas.getTableHeader().setBackground(marromEscuro);
        tabelaReceitas.getTableHeader().setForeground(corGold);
        tabelaReceitas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabelaReceitas.getColumnModel().getColumn(0).setPreferredWidth(90);
        tabelaReceitas.getColumnModel().getColumn(0).setMaxWidth(110);
        tabelaReceitas.getColumnModel().getColumn(1).setPreferredWidth(620);

        JScrollPane scrollReceitas = new JScrollPane(tabelaReceitas);
        scrollReceitas.setBounds(25, 358, 730, 190);
        scrollReceitas.setBorder(new LineBorder(corGold));
        p.add(scrollReceitas);

        // --- BOTÃO FECHAR ---
        JButton btnFechar = new JButton("FECHAR HISTÓRICO");
        btnFechar.setBounds(25, 565, 730, 40);
        btnFechar.setBackground(Color.WHITE);
        btnFechar.setForeground(marromEscuro);
        btnFechar.setBorder(new LineBorder(corGold, 1));
        btnFechar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnFechar.setFocusPainted(false);
        btnFechar.setOpaque(true);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        p.add(btnFechar);

        // ================================================================
        // EVENTOS
        // ================================================================
        carregarHistorico();
        carregarReceitas();

        btnFechar.addActionListener(e -> this.dispose());
    }

    private void carregarHistorico() {
        modeloTabela.setRowCount(0);
        List<Prontuario> lista = dao.listarHistoricoPorPaciente(idPaciente);

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

    private void carregarReceitas() {
        modeloReceitas.setRowCount(0);
        List<Receita> receitas = receituarioDAO.listarPorPaciente(idPaciente);
        for (Receita r : receitas) {
            String data = r.getDataEmissao() != null && r.getDataEmissao().length() >= 10
                        ? r.getDataEmissao().substring(0, 10) : nvl(r.getDataEmissao());
            modeloReceitas.addRow(new Object[]{ data, nvl(r.getMedicamentos()) });
        }
    }

    private String nvl(Object valor) {
        return valor != null ? valor.toString() : "-";
    }
}