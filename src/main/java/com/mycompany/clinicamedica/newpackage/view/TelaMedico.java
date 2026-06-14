package com.mycompany.clinicamedica.newpackage.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TelaMedico extends JFrame {
    private String nomeDoMedicoLogado;
    private JTable tabela;

    public TelaMedico(String nomeMedico, String especialidade) {
        this.nomeDoMedicoLogado = nomeMedico;
        setTitle("VITA — Ambiente do Profissional Clínico");
        setSize(1024, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Paleta de Cores Premium (Marrom e Dourado VITA)
        Color marromEscuro = new Color(61, 28, 6);
        Color corGold      = new Color(193, 158, 103);
        Color fundoClaro   = new Color(244, 241, 234);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(fundoClaro);
        setContentPane(principal);

        // --- HEADER MÉDICO ---
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
        btnSair.addActionListener(e -> {
            this.dispose();
            new TelaLogin().setVisible(true);
        });
        header.add(btnSair, BorderLayout.EAST);
        principal.add(header, BorderLayout.NORTH);

        // --- CORPO OPERACIONAL ---
        JPanel corpo = new JPanel(new BorderLayout(20, 20));
        corpo.setBackground(fundoClaro);
        corpo.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel lblTabela = new JLabel("Fila de Atendimento Ocupacional / Consultas do Dia:");
        lblTabela.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTabela.setForeground(marromEscuro);
        
        JPanel pnlTabela = new JPanel(new BorderLayout(0, 10));
        pnlTabela.setBackground(fundoClaro);
        pnlTabela.add(lblTabela, BorderLayout.NORTH);

        // Listagem da fila diária (Populada pela Secretaria)
        String[] colunas = {"Horário", "Paciente", "Status Presença", "Convênio"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        modelo.addRow(new Object[]{"08:30", "Carlos Augusto Silva", "Aguardando", "Unimed"});
        modelo.addRow(new Object[]{"09:15", "Mariana Costa Souza", "Em Triagem", "Particular"});
        modelo.addRow(new Object[]{"10:00", "Roberto Alves Pereira", "Agendado", "Bradesco Saúde"});
        
        tabela = new JTable(modelo);
        tabela.setRowHeight(30);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(corGold));
        pnlTabela.add(scroll, BorderLayout.CENTER);
        
        corpo.add(pnlTabela, BorderLayout.CENTER);

        // Painel Lateral de Cards de Ação (Visual Limpo e Sem Caracteres Quebrados)
        JPanel acoes = new JPanel(new GridLayout(3, 1, 0, 15));
        acoes.setBackground(fundoClaro);
        acoes.setPreferredSize(new Dimension(250, 0));

        JButton btnProntuario = criarBotaoClinico("Chamar Prontuário");
        JButton btnHistorico   = criarBotaoClinico("Histórico Clínico");
        JButton btnReceita    = criarBotaoClinico("Emitir Receita / Atestado");

        acoes.add(btnProntuario); 
        acoes.add(btnHistorico); 
        acoes.add(btnReceita);
        corpo.add(acoes, BorderLayout.EAST);

        principal.add(corpo, BorderLayout.CENTER);

        // --- CONFIGURAÇÃO DOS EVENTOS CLÍNICOS ---
        
        // LÓGICA DINÂMICA: Mapeia a tabela e envia os nomes cadastrados para a caixa de escolha
        btnProntuario.addActionListener(e -> {
            int totalPacientes = tabela.getRowCount();
            String[] listaPacientes = new String[totalPacientes];
            
            // Lê dinamicamente a coluna 1 ("Paciente") de cada linha da tabela
            for (int i = 0; i < totalPacientes; i++) {
                listaPacientes[i] = tabela.getValueAt(i, 1).toString();
            }
            
            // Tratamento de segurança caso a fila esteja vazia
            if (totalPacientes == 0) {
                listaPacientes = new String[]{"Nenhum paciente agendado"};
            }

            // Abre a janela de confirmação por Caixa de Escolha
            new TelaSelecionarPaciente(nomeDoMedicoLogado, listaPacientes).setVisible(true);
        });

        btnHistorico.addActionListener(e -> {
            String paciente = obterPacienteSelecionado();
            new TelaHistoricoClinico(paciente).setVisible(true);
        });

        btnReceita.addActionListener(e -> {
            String paciente = obterPacienteSelecionado();
            new TelaEmitirReceita(paciente, nomeDoMedicoLogado).setVisible(true);
        });
    }

    // Auxiliar para pegar o paciente selecionado com clique ou retornar o primeiro da fila caso nenhum esteja selecionado
    private String obterPacienteSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            return tabela.getValueAt(0, 1).toString();
        }
        return tabela.getValueAt(linha, 1).toString();
    }

    private JButton criarBotaoClinico(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(Color.WHITE);
        b.setForeground(new Color(44, 37, 32));
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBorder(new LineBorder(new Color(193, 158, 103), 1, true));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}