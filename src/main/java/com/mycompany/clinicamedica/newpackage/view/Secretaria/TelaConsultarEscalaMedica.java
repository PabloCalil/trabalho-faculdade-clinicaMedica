package com.mycompany.clinicamedica.newpackage.view.Secretaria; // <-- Ajustado estritamente para o seu pacote real!

import java.awt.*;
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

    // PALETA DE CORES TERROSAS PADRONIZADA DO PROJETO
    private final Color corCremeClaro   = new Color(251, 251, 250); // #FBFBFA
    private final Color corDestaqueGold = new Color(193, 158, 103); // #C19E67
    private final Color corTomMedio     = new Color(110, 102, 95);  // #6E665F
    private final Color corRotuloCinza  = new Color(180, 169, 158); // #B4A99E
    private final Color corMarromEscuro = new Color(61, 28, 6);     // #3D1C06

    // Tons de apoio para situação dos horários
    private final Color corLivre   = new Color(225, 240, 220); // verde suave
    private final Color corOcupado = new Color(245, 225, 220); // terracota suave

    // Grade fixa de horários de atendimento da clínica
    private final String[] horarios = {
        "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
        "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"
    };

    public TelaConsultarEscalaMedica() {
        setTitle("🏥 Sistema Clínica Médica - Consultar Escala Médica");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel de Fundo
        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(corTomMedio);
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        // Título da Tela
        JLabel lblTitulo = new JLabel("Escala e Disponibilidade Médica");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(50, 25, 600, 40);
        painelFundo.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Verifique horários livres, pacientes agendados e a ocupação do profissional.");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(corRotuloCinza);
        lblSubtitulo.setBounds(50, 65, 700, 20);
        painelFundo.add(lblSubtitulo);

        // ====================================================================
        // PAINEL DE FILTROS (Topo)
        // ====================================================================
        JPanel painelFiltros = new JPanel();
        painelFiltros.setBackground(corMarromEscuro);
        painelFiltros.setBounds(50, 100, 885, 80);
        painelFiltros.setLayout(null);
        painelFiltros.setBorder(new LineBorder(corDestaqueGold, 1, true));
        painelFundo.add(painelFiltros);

        Font fonteLabel = new Font("Segoe UI", Font.PLAIN, 13);

        // Filtro Médico
        JLabel lblMedico = new JLabel("Médico / Especialista:");
        lblMedico.setFont(fonteLabel);
        lblMedico.setForeground(corRotuloCinza);
        lblMedico.setBounds(20, 12, 200, 20);
        painelFiltros.add(lblMedico);

        cbMedico = new JComboBox<>(new String[]{
            "Selecione o Médico",
            "Dr. Arnaldo Silva (Cardiologia)",
            "Dra. Beatriz Costa (Pediatria)",
            "Dr. Carlos Eduardo (Clínico Geral)"
        });
        cbMedico.setBounds(20, 35, 330, 30);
        cbMedico.setBackground(corTomMedio);
        cbMedico.setForeground(corCremeClaro);
        cbMedico.setBorder(new LineBorder(corDestaqueGold, 1));
        painelFiltros.add(cbMedico);

        // Filtro Data
        JLabel lblData = new JLabel("Data (DD/MM/AAAA):");
        lblData.setFont(fonteLabel);
        lblData.setForeground(corRotuloCinza);
        lblData.setBounds(380, 12, 200, 20);
        painelFiltros.add(lblData);

        txtData = new JTextField("25/05/2026");
        txtData.setBounds(380, 35, 160, 30);
        txtData.setBackground(corTomMedio);
        txtData.setForeground(corCremeClaro);
        txtData.setCaretColor(corCremeClaro);
        txtData.setBorder(new LineBorder(corDestaqueGold, 1));
        painelFiltros.add(txtData);

        // Botão Consultar
        btnConsultar = new JButton("🔍 Gerar Escala");
        btnConsultar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnConsultar.setBackground(corDestaqueGold);
        btnConsultar.setForeground(corMarromEscuro);
        btnConsultar.setBounds(700, 30, 160, 35);
        btnConsultar.setFocusPainted(false);
        btnConsultar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFiltros.add(btnConsultar);

        // ====================================================================
        // TABELA DA ESCALA (Centro)
        // ====================================================================
        String[] colunas = {"Horário", "Situação", "Paciente", "Tipo de Consulta"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false; // Tela apenas de consulta
            }
        };
        tabelaEscala = new JTable(modeloTabela);
        tabelaEscala.setBackground(corCremeClaro);
        tabelaEscala.setGridColor(corRotuloCinza);
        tabelaEscala.setRowHeight(28);
        tabelaEscala.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaEscala.getTableHeader().setReorderingAllowed(false);

        // Renderizador que pinta a linha conforme a situação do horário
        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tabela, Object valor,
                    boolean selecionado, boolean foco, int linha, int coluna) {
                Component c = super.getTableCellRendererComponent(tabela, valor, selecionado, foco, linha, coluna);
                if (!selecionado) {
                    String situacao = String.valueOf(tabela.getValueAt(linha, 1));
                    c.setBackground("Livre".equals(situacao) ? corLivre : corOcupado);
                    c.setForeground(corMarromEscuro);
                }
                return c;
            }
        };
        for (int i = 0; i < tabelaEscala.getColumnCount(); i++) {
            tabelaEscala.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }

        JScrollPane barraRolagem = new JScrollPane(tabelaEscala);
        barraRolagem.setBounds(50, 200, 885, 360);
        barraRolagem.setBorder(new LineBorder(corDestaqueGold, 1));
        painelFundo.add(barraRolagem);

        // Resumo de ocupação
        lblResumo = new JLabel("Selecione um médico e clique em \"Gerar Escala\" para visualizar a agenda.");
        lblResumo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblResumo.setForeground(corCremeClaro);
        lblResumo.setBounds(50, 570, 885, 25);
        painelFundo.add(lblResumo);

        // ====================================================================
        // BOTÕES DE RODAPÉ
        // ====================================================================
        btnAgendarLivre = new JButton("Agendar no Horário Livre");
        btnAgendarLivre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAgendarLivre.setBackground(corMarromEscuro);
        btnAgendarLivre.setForeground(corCremeClaro);
        btnAgendarLivre.setBounds(675, 610, 260, 40);
        btnAgendarLivre.setFocusPainted(false);
        btnAgendarLivre.setBorder(new LineBorder(corDestaqueGold, 1));
        btnAgendarLivre.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnAgendarLivre);

        btnVoltar = new JButton("← Voltar ao Menu");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setBackground(new Color(180, 70, 70));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(50, 610, 160, 40);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnVoltar);

        // ====================================================================
        // COMPORTAMENTO / EVENTOS
        // ====================================================================
        btnConsultar.addActionListener(e -> gerarEscala());

        btnAgendarLivre.addActionListener(e -> {
            int linha = tabelaEscala.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this,
                        "Selecione um horário livre na tabela para agendar.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String situacao = tabelaEscala.getValueAt(linha, 1).toString();
            if (!"Livre".equals(situacao)) {
                JOptionPane.showMessageDialog(this,
                        "Este horário já está ocupado. Escolha um horário livre.",
                        "Horário Indisponível", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Encaminha para a tela de agendamento já existente
            new TelaAgendarConsulta().setVisible(true);
        });

        btnVoltar.addActionListener(e -> this.dispose());
    }

    /**
     * Monta a grade de horários do médico selecionado, marcando cada slot como
     * "Livre" ou "Ocupado" (com o paciente e o tipo de consulta).
     */
    private void gerarEscala() {
        if (cbMedico.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um médico para gerar a escala.",
                    "Médico não informado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtData.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Informe a data da escala.",
                    "Data não informada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String medico = cbMedico.getSelectedItem().toString();
        String[][] ocupados = obterAgendamentos(medico);

        modeloTabela.setRowCount(0);
        int totalLivres = 0, totalOcupados = 0;

        for (String horario : horarios) {
            String[] reserva = buscarReserva(ocupados, horario);
            if (reserva == null) {
                modeloTabela.addRow(new Object[]{horario, "Livre", "—", "—"});
                totalLivres++;
            } else {
                modeloTabela.addRow(new Object[]{horario, "Ocupado", reserva[1], reserva[2]});
                totalOcupados++;
            }
        }

        lblResumo.setText(String.format(
                "Escala de %s em %s  —  %d horário(s) livre(s)  |  %d ocupado(s)",
                medico, txtData.getText().trim(), totalLivres, totalOcupados));
    }

    /** Procura uma reserva pelo horário dentro da lista de agendamentos do médico. */
    private String[] buscarReserva(String[][] agendamentos, String horario) {
        for (String[] linha : agendamentos) {
            if (linha[0].equals(horario)) {
                return linha;
            }
        }
        return null;
    }

    /**
     * Fonte de dados simulada dos agendamentos por médico, no mesmo padrão das
     * demais telas de consulta do projeto. Cada linha: {horário, paciente, tipo}.
     * Quando houver persistência (ConsultaDAO), este método é o ponto de troca.
     */
    private String[][] obterAgendamentos(String medico) {
        if (medico.startsWith("Dr. Arnaldo")) {
            return new String[][]{
                {"08:00", "Carlos Eduardo Santos", "Particular"},
                {"09:30", "Juliana Ribeiro Dias", "Convênio Médico"},
                {"14:00", "Marcos Antônio Lima", "Retorno"}
            };
        } else if (medico.startsWith("Dra. Beatriz")) {
            return new String[][]{
                {"09:00", "Ana Julia Ferreira", "Retorno"},
                {"10:00", "Alice Vieira Ramos", "Convênio Médico"},
                {"15:30", "Sofia Andrade Pinto", "Particular"}
            };
        } else if (medico.startsWith("Dr. Carlos")) {
            return new String[][]{
                {"08:30", "Mariana Costa Souza", "Particular"},
                {"10:30", "Roberto Nunes Aguiar", "Convênio Médico"},
                {"13:00", "Patrícia Gomes Reis", "Retorno"},
                {"16:00", "Fernando Lopes Cruz", "Particular"}
            };
        }
        return new String[][]{};
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new TelaConsultarEscalaMedica().setVisible(true);
        });
    }
}
