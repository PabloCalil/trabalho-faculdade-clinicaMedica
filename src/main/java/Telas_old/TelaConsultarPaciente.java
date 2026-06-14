package Telas_old; // <-- Ajustado exatamente para a sua árvore!

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TelaConsultarPaciente extends JFrame {

    private JTextField txtBusca;
    private JButton btnBuscar, btnVerHistorico, btnVoltar;
    private JTable tabelaPacientes;
    private DefaultTableModel modeloTabela;

    public TelaConsultarPaciente() {
        setTitle("🏥 Sistema Clínica Médica - Consultar Pacientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 680);
        setLocationRelativeTo(null);
        setResizable(false);

        // PALETA DE CORES TERROSAS VALIDADAS DO PROJETO
        Color corCremeClaro   = new Color(251, 251, 250); // #FBFBFA
        Color corDestaqueGold = new Color(193, 158, 103); // #C19E67
        Color corTomMedio     = new Color(110, 102, 95);  // #6E665F
        Color corRotuloCinza  = new Color(180, 169, 158); // #B4A99E
        Color corMarromEscuro = new Color(61, 28, 6);     // #3D1C06

        // Painel de Fundo
        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(corTomMedio);
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        // Título da Tela
        JLabel lblTitulo = new JLabel("Consulta de Pacientes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(corCremeClaro);
        lblTitulo.setBounds(50, 30, 500, 40);
        painelFundo.add(lblTitulo);

        // ====================================================================
        // BARRA DE PESQUISA (Topo do painel)
        // ====================================================================
        JLabel lblBusca = new JLabel("Buscar por Nome ou CPF:");
        lblBusca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblBusca.setForeground(corCremeClaro);
        lblBusca.setBounds(50, 95, 200, 20);
        painelFundo.add(lblBusca);

        txtBusca = new JTextField();
        txtBusca.setBounds(50, 120, 640, 40);
        txtBusca.setBackground(corMarromEscuro);
        txtBusca.setForeground(corCremeClaro);
        txtBusca.setCaretColor(corCremeClaro);
        txtBusca.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtBusca.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(corDestaqueGold, 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));
        painelFundo.add(txtBusca);

        btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscar.setBackground(corDestaqueGold);
        btnBuscar.setForeground(corMarromEscuro);
        btnBuscar.setBounds(705, 120, 180, 40);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorder(new LineBorder(corDestaqueGold.darker(), 1));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnBuscar);

        // ====================================================================
        // TABELA DE RESULTADOS (Centro)
        // ====================================================================
        String[] colunas = {"ID", "Nome do Paciente", "CPF", "Telefone", "Data Nasc."};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaPacientes = new JTable(modeloTabela);
        
        // Dados fictícios simulados para popular a tabela de cara
        modeloTabela.addRow(new Object[]{"1", "Carlos Eduardo Santos", "123.456.789-00", "(34) 99123-4567", "12/05/1988"});
        modeloTabela.addRow(new Object[]{"2", "Ana Julia Ferreira", "987.654.321-11", "(34) 98877-6655", "23/10/1995"});
        modeloTabela.addRow(new Object[]{"3", "Marcos Antônio Lima", "456.123.789-55", "(34) 99200-1122", "04/02/1973"});

        JScrollPane barraRolagem = new JScrollPane(tabelaPacientes);
        barraRolagem.setBounds(50, 190, 835, 330);
        barraRolagem.setBorder(new LineBorder(corDestaqueGold, 1));
        tabelaPacientes.setBackground(corCremeClaro);
        tabelaPacientes.setGridColor(corRotuloCinza);
        tabelaPacientes.setRowHeight(25);
        painelFundo.add(barraRolagem);

        // ====================================================================
        // BOTÕES DE RODA PÉ
        // ====================================================================
        btnVerHistorico = new JButton("Visualizar Prontuário");
        btnVerHistorico.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVerHistorico.setBackground(corMarromEscuro);
        btnVerHistorico.setForeground(corCremeClaro);
        btnVerHistorico.setBounds(705, 540, 180, 40);
        btnVerHistorico.setFocusPainted(false);
        btnVerHistorico.setBorder(new LineBorder(corDestaqueGold, 1));
        btnVerHistorico.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnVerHistorico);

        btnVoltar = new JButton("← Voltar ao Menu");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVoltar.setBackground(new Color(180, 70, 70));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBounds(50, 540, 160, 40);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnVoltar);

        // ====================================================================
        // COMPORTAMENTOS / EVENTOS
        // ====================================================================
        btnBuscar.addActionListener(e -> {
            String termoBusca = txtBusca.getText().trim();
            if (termoBusca.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Exibindo todos os registros ativos.", "Filtro", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Filtrando resultados por: " + termoBusca, "Pesquisa Realizada", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnVerHistorico.addActionListener(e -> {
            int linhaSelecionada = tabelaPacientes.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um paciente na tabela primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                String nome = tabelaPacientes.getValueAt(linhaSelecionada, 1).toString();
                JOptionPane.showMessageDialog(this, "Abrindo prontuário e histórico de: " + nome, "Prontuário Clínico", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> this.dispose());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new TelaConsultarPaciente().setVisible(true);
        });
    }
}