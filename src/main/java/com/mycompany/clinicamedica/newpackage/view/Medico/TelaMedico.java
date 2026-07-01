package com.mycompany.clinicamedica.newpackage.view.Medico;

import Services.ChamadaDAO;
import Services.Consulta;
import Services.ConsultaDAO;
import Services.StatusDAO;
import com.mycompany.clinicamedica.newpackage.view.TelaLogin;
import com.mycompany.clinicamedica.newpackage.view.ui.Tema;
import java.awt.*;
import java.util.ArrayList;
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
    private final ChamadaDAO chamadaDAO   = new ChamadaDAO();
    private final StatusDAO  statusDAO    = new StatusDAO();

    private List<Consulta> listaCompleta;

    // Opcao usada apenas para limpar o filtro e exibir toda a fila.
    private static final String FILTRO_TODOS = "Todos";

    private final Color marromEscuro = Tema.MARROM_ESCURO;
    private final Color corGold      = Tema.GOLD;
    private final Color fundoClaro   = Tema.FUNDO_CLARO;
    private final Color corTomMedio  = Tema.TOM_MEDIO;

    public TelaMedico(String nomeMedico, String especialidade, int idUsuario) {
        this.nomeDoMedicoLogado = nomeMedico;
        this.idUsuarioLogado    = idUsuario;

        setTitle("Health Equilibrium — Ambiente do Profissional Clínico");
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

        JLabel lblTabela = new JLabel("Fila - " + java.time.LocalDate.now());
        lblTabela.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTabela.setForeground(marromEscuro);
        topoTabela.add(lblTabela, BorderLayout.WEST);

        JPanel painelBotoesTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoesTopo.setBackground(fundoClaro);

        // O medico apenas visualiza/filtra: o combo serve so para filtrar a fila.
        List<String> opcoesFiltro = new ArrayList<>();
        opcoesFiltro.add(FILTRO_TODOS);
        opcoesFiltro.addAll(statusDAO.listarNomes());
        JComboBox<String> cbStatus = new JComboBox<>(opcoesFiltro.toArray(new String[0]));
        cbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbStatus.setPreferredSize(new Dimension(180, 32));

        Dimension tamBotao = new Dimension(140, 32);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(corGold);
        btnFiltrar.setForeground(marromEscuro);
        btnFiltrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnFiltrar.setFocusPainted(false);
        btnFiltrar.setOpaque(true);
        btnFiltrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFiltrar.setBorder(new LineBorder(corGold.darker(), 1));
        btnFiltrar.setPreferredSize(tamBotao);

        JButton btnAtualizar = new JButton("Atualizar Fila");
        btnAtualizar.setBackground(corTomMedio);
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setOpaque(true);
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAtualizar.setPreferredSize(tamBotao);

        painelBotoesTopo.add(new JLabel("Status:"));
        painelBotoesTopo.add(cbStatus);
        painelBotoesTopo.add(btnFiltrar);
        painelBotoesTopo.add(btnAtualizar);
        topoTabela.add(painelBotoesTopo, BorderLayout.EAST);

        JPanel pnlTabela = new JPanel(new BorderLayout(0, 10));
        pnlTabela.setBackground(fundoClaro);
        pnlTabela.add(topoTabela, BorderLayout.NORTH);

        // ================================================================
        // TABELA
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
                        case "Em Atendimento"      -> c.setBackground(new Color(255, 243, 205));
                        case "Consulta Finalizada" -> c.setBackground(new Color(220, 240, 220));
                        case "Cancelado"           -> c.setBackground(new Color(250, 220, 220));
                        case "Aguardando Chamada"  -> c.setBackground(new Color(235, 245, 255));
                        default                    -> c.setBackground(Color.WHITE);
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
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        tabela.getColumnModel().getColumn(0).setWidth(0);
        tabela.getColumnModel().getColumn(5).setMinWidth(0);
        tabela.getColumnModel().getColumn(5).setMaxWidth(0);
        tabela.getColumnModel().getColumn(5).setWidth(0);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(300);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(200);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(corGold));
        pnlTabela.add(scroll, BorderLayout.CENTER);
        corpo.add(pnlTabela, BorderLayout.CENTER);

        // ================================================================
        // PAINEL LATERAL — 4 botões
        // ================================================================
        JPanel acoes = new JPanel(new GridLayout(4, 1, 0, 15));
        acoes.setBackground(fundoClaro);
        acoes.setPreferredSize(new Dimension(220, 0));

        JButton btnProntuario     = criarBotaoClinico("Chamar Prontuário");
        JButton btnHistorico      = criarBotaoClinico("Histórico Clínico");
        JButton btnReceita        = criarBotaoClinico("Emitir Receita");
        JButton btnChamarPaciente = criarBotaoClinico("Chamar Paciente");
        btnChamarPaciente.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnChamarPaciente.setBorder(new LineBorder(corGold.darker(), 1, true));
        btnChamarPaciente.setFocusPainted(false);
        btnChamarPaciente.setOpaque(true);
        btnChamarPaciente.setCursor(new Cursor(Cursor.HAND_CURSOR));

        acoes.add(btnProntuario);
        acoes.add(btnHistorico);
        acoes.add(btnReceita);
        acoes.add(btnChamarPaciente);
        corpo.add(acoes, BorderLayout.EAST);

        principal.add(corpo, BorderLayout.CENTER);

        // ================================================================
        // EVENTOS
        // ================================================================
        carregarFila();

        btnAtualizar.addActionListener(e -> carregarFila());

        btnFiltrar.addActionListener(e -> {
            String filtro = cbStatus.getSelectedItem().toString();
            modeloTabela.setRowCount(0);
            if (listaCompleta == null) return;
            for (Consulta c : listaCompleta) {
                if (FILTRO_TODOS.equals(filtro) || filtro.equals(c.getStatus())) {
                    adicionarLinhaTabela(c);
                }
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
                int idPac   = (int) modeloTabela.getValueAt(i, 5);
                mapa.put(nome, idPac);
            }
            new TelaSelecionarPaciente(nomeDoMedicoLogado, mapa).setVisible(true);
        });

        btnHistorico.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione um paciente na tabela.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nomePaciente = modeloTabela.getValueAt(linha, 2).toString();
            int idPac           = (int) modeloTabela.getValueAt(linha, 5);
            new TelaHistoricoClinico(nomePaciente, idPac).setVisible(true);
        });

        btnReceita.addActionListener(e -> {
            String paciente = obterPacienteSelecionado();
            if (paciente == null) return;
            new TelaEmitirReceita(paciente, nomeDoMedicoLogado).setVisible(true);
        });

        btnChamarPaciente.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione um paciente na fila para chamar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idConsulta = (int) modeloTabela.getValueAt(linha, 0);
            int idPaciente = (int) modeloTabela.getValueAt(linha, 5);
            String nome    = modeloTabela.getValueAt(linha, 2).toString();
            String status  = modeloTabela.getValueAt(linha, 3).toString();

            // O medico so pode chamar quem ainda nao teve o atendimento iniciado.
            if (!status.equals("Agendado")) {
                JOptionPane.showMessageDialog(this,
                    "Só é possível chamar pacientes com status 'Agendado'.\n"
                    + "Este paciente está com status '" + status + "'.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(this,
                "Chamar o paciente " + nome + " para a consulta?",
                "Confirmar Chamada", JOptionPane.YES_NO_OPTION);

            if (confirmacao != JOptionPane.YES_OPTION) return;

            // Registra a chamada e sinaliza a secretaria via status "Aguardando Chamada".
            // A partir daqui, apenas a secretaria altera o andamento da consulta.
            if (chamadaDAO.chamarPaciente(idConsulta, idPaciente)) {
                consultaDAO.atualizarStatus(idConsulta, "Aguardando Chamada");
                JOptionPane.showMessageDialog(this,
                    "Paciente " + nome + " chamado com sucesso!\nA secretária será notificada.",
                    "Chamada Registrada", JOptionPane.INFORMATION_MESSAGE);
                carregarFila();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erro ao registrar chamada.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // ================================================================
    // MÉTODOS AUXILIARES
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
            c.getIdPaciente()
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
