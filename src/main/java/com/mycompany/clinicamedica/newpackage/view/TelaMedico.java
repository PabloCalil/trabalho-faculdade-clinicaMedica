package com.mycompany.clinicamedica.newpackage.view;

import Services.Consulta;
import Services.ConsultaDAO;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class TelaMedico extends JFrame {

    private final String nomeDoMedicoLogado;
    private final int idUsuarioLogado;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private final ConsultaDAO consultaDAO = new ConsultaDAO();

    // Lista completa carregada do banco (para filtro local)
    private List<Consulta> listaCompleta;

    private static final String[] STATUS_OPCOES = {
        "Agendado", "Aguardando", "Em Atendimento", "Finalizado", "Cancelado"
    };

    private static final String[] STATUS_FILTRO = {
        "Todos", "Agendado", "Aguardando", "Em Atendimento", "Finalizado", "Cancelado"
    };

    private final Color marromEscuro = new Color(61, 28, 6);
    private final Color corGold      = new Color(193, 158, 103);
    private final Color fundoClaro   = new Color(244, 241, 234);
    private final Color corTomMedio  = new Color(110, 102, 95);

    public TelaMedico(String nomeMedico, String especialidade, int idUsuario) {
        this.nomeDoMedicoLogado = nomeMedico;
        this.idUsuarioLogado    = idUsuario;

        setTitle("VITA — Ambiente do Profissional Clínico");
        setSize(1100, 660);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(fundoClaro);
        setContentPane(principal);

        // ================================================================
        // HEADER
        // ================================================================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(marromEscuro);
        header.setBorder(new EmptyBorder(15, 40, 15, 40));

        JPanel txtHeader = new JPanel(new GridLayout(2, 1));
        txtHeader.setBackground(marromEscuro);

        JLabel lblNome = new JLabel("Dr(a). " + nomeMedico.toUpperCase());
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblNome.setForeground(Color.WHITE);

        JLabel lblEsp = new JLabel("Especialidade: " + especialidade);
        lblEsp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEsp.setForeground(corGold);

        txtHeader.add(lblNome);
        txtHeader.add(lblEsp);
        header.add(txtHeader, BorderLayout.WEST);

        JButton btnSair = new JButton("Desconectar");
        btnSair.setBackground(new Color(180, 70, 70));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSair.setFocusPainted(false);
        btnSair.setOpaque(true);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> {
            this.dispose();
            new TelaLogin().setVisible(true);
        });
        header.add(btnSair, BorderLayout.EAST);
        principal.add(header, BorderLayout.NORTH);

        // ================================================================
        // CORPO
        // ================================================================
        JPanel corpo = new JPanel(new BorderLayout(20, 20));
        corpo.setBackground(fundoClaro);
        corpo.setBorder(new EmptyBorder(25, 40, 25, 40));

        // --- Topo da tabela ---
        JPanel topoTabela = new JPanel(new BorderLayout());
        topoTabela.setBackground(fundoClaro);

        JLabel lblTabela = new JLabel("Fila de Atendimento — " + java.time.LocalDate.now());
        lblTabela.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTabela.setForeground(marromEscuro);
        topoTabela.add(lblTabela, BorderLayout.WEST);

        JPanel painelBotoesTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoesTopo.setBackground(fundoClaro);

        // ComboBox de filtro
        JComboBox<String> cbFiltro = new JComboBox<>(STATUS_FILTRO);
        cbFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbFiltro.setPreferredSize(new Dimension(160, 32));
        cbFiltro.setToolTipText("Filtrar fila por status");

        // ComboBox de alteração de status
        JComboBox<String> cbStatus = new JComboBox<>(STATUS_OPCOES);
        cbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbStatus.setPreferredSize(new Dimension(160, 32));

        JButton btnAlterarStatus = new JButton("Alterar Status");
        btnAlterarStatus.setBackground(corGold);
        btnAlterarStatus.setForeground(marromEscuro);
        btnAlterarStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAlterarStatus.setFocusPainted(false);
        btnAlterarStatus.setOpaque(true);
        btnAlterarStatus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAlterarStatus.setBorder(new LineBorder(corGold.darker(), 1));

        JButton btnAtualizar = new JButton("↻ Atualizar Fila");
        btnAtualizar.setBackground(corTomMedio);
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setOpaque(true);
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painelBotoesTopo.add(new JLabel("Filtrar:"));
        painelBotoesTopo.add(cbFiltro);
        painelBotoesTopo.add(new JLabel("Status:"));
        painelBotoesTopo.add(cbStatus);
        painelBotoesTopo.add(btnAlterarStatus);
        painelBotoesTopo.add(btnAtualizar);
        topoTabela.add(painelBotoesTopo, BorderLayout.EAST);

        JPanel pnlTabela = new JPanel(new BorderLayout(0, 10));
        pnlTabela.setBackground(fundoClaro);
        pnlTabela.add(topoTabela, BorderLayout.NORTH);

        // ================================================================
        // TABELA — 6 colunas: ID, Horário, Paciente, Status, Convênio, idPaciente
        // ================================================================
        String[] colunas = {"ID", "Horário", "Paciente", "Status", "Convênio", "idPaciente"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                String status = modeloTabela.getValueAt(row, 3) != null
                              ? modeloTabela.getValueAt(row, 3).toString() : "";
                if (!isRowSelected(row)) {
                    switch (status) {
                        case "Em Atendimento" -> c.setBackground(new Color(255, 243, 205));
                        case "Finalizado"     -> c.setBackground(new Color(220, 240, 220));
                        case "Cancelado"      -> c.setBackground(new Color(250, 220, 220));
                        case "Aguardando"     -> c.setBackground(new Color(235, 245, 255));
                        default               -> c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        };

        tabela.setRowHeight(30);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setSelectionBackground(corGold);
        tabela.setSelectionForeground(marromEscuro);
        tabela.getTableHeader().setBackground(marromEscuro);
        tabela.getTableHeader().setForeground(corGold);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        // Oculta coluna ID (0) e idPaciente (5)
        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        tabela.getColumnModel().getColumn(0).setWidth(0);
        tabela.getColumnModel().getColumn(5).setMinWidth(0);
        tabela.getColumnModel().getColumn(5).setMaxWidth(0);
        tabela.getColumnModel().getColumn(5).setWidth(0);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(250);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(130);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(150);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(corGold));
        pnlTabela.add(scroll, BorderLayout.CENTER);
        corpo.add(pnlTabela, BorderLayout.CENTER);

        // ================================================================
        // PAINEL LATERAL DE AÇÕES
        // ================================================================
        JPanel acoes = new JPanel(new GridLayout(3, 1, 0, 15));
        acoes.setBackground(fundoClaro);
        acoes.setPreferredSize(new Dimension(220, 0));

        JButton btnProntuario = criarBotaoClinico("Chamar Prontuário");
        JButton btnHistorico  = criarBotaoClinico("Histórico Clínico");
        JButton btnReceita    = criarBotaoClinico("Emitir Receita");

        acoes.add(btnProntuario);
        acoes.add(btnHistorico);
        acoes.add(btnReceita);
        corpo.add(acoes, BorderLayout.EAST);

        principal.add(corpo, BorderLayout.CENTER);

        // ================================================================
        // EVENTOS
        // ================================================================
        carregarFila();

        // Filtro por status — aplica sobre a lista já carregada
        cbFiltro.addActionListener(e -> {
            String filtro = cbFiltro.getSelectedItem().toString();
            aplicarFiltro(filtro);
        });

        btnAtualizar.addActionListener(e -> {
            cbFiltro.setSelectedIndex(0); // reseta filtro ao atualizar
            carregarFila();
        });

        btnAlterarStatus.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione uma consulta na tabela para alterar o status.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idConsulta    = (int) modeloTabela.getValueAt(linha, 0);
            String novoStatus = cbStatus.getSelectedItem().toString();

            if (consultaDAO.atualizarStatus(idConsulta, novoStatus)) {
                JOptionPane.showMessageDialog(this,
                    "Status atualizado para '" + novoStatus + "' com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarFila();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erro ao atualizar status.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnProntuario.addActionListener(e -> {
            if (modeloTabela.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                    "Nenhum paciente na fila.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Map<String, Integer> mapa = new LinkedHashMap<>();
            for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                String nome = modeloTabela.getValueAt(i, 2).toString();
                int idPac   = (int) modeloTabela.getValueAt(i, 5); // coluna oculta idPaciente
                mapa.put(nome, idPac);
            }
            new TelaSelecionarPaciente(nomeDoMedicoLogado, mapa).setVisible(true);
        });

        btnHistorico.addActionListener(e -> {
            String paciente = obterPacienteSelecionado();
            if (paciente == null) return;
            new TelaHistoricoClinico(paciente).setVisible(true);
        });

        btnReceita.addActionListener(e -> {
            String paciente = obterPacienteSelecionado();
            if (paciente == null) return;
            new TelaEmitirReceita(paciente, nomeDoMedicoLogado).setVisible(true);
        });
    }

    // ================================================================
    // CARREGAR FILA DO BANCO
    // ================================================================
    private void carregarFila() {
        modeloTabela.setRowCount(0);
        listaCompleta = consultaDAO.listarFilaDoDia(idUsuarioLogado);

        if (listaCompleta.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nenhuma consulta agendada para hoje.",
                "Fila Vazia", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        popularTabela(listaCompleta);
    }

    // ================================================================
    // FILTRO LOCAL POR STATUS
    // ================================================================
    private void aplicarFiltro(String filtro) {
        modeloTabela.setRowCount(0);
        if (listaCompleta == null) return;

        for (Consulta c : listaCompleta) {
            if (filtro.equals("Todos") || filtro.equals(c.getStatus())) {
                adicionarLinhaTabela(c);
            }
        }
    }

    private void popularTabela(List<Consulta> lista) {
        for (Consulta c : lista) {
            adicionarLinhaTabela(c);
        }
    }

    private void adicionarLinhaTabela(Consulta c) {
        String hora = c.getDataHora().length() >= 16
                    ? c.getDataHora().substring(11, 16)
                    : c.getDataHora();
        modeloTabela.addRow(new Object[]{
            c.getIdConsulta(),
            hora,
            c.getNomePaciente(),
            c.getStatus(),
            c.getNomeConvenio() != null ? c.getNomeConvenio() : "Particular",
            c.getIdPaciente()   // coluna oculta — índice 5
        });
    }

    private String obterPacienteSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um paciente na tabela.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return modeloTabela.getValueAt(linha, 2).toString();
    }

    private JButton criarBotaoClinico(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(Color.WHITE);
        b.setForeground(new Color(44, 37, 32));
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBorder(new LineBorder(corGold, 1, true));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}