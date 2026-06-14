package com.mycompany.clinicamedica.newpackage.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TelaSelecionarPaciente extends JFrame {
    private JComboBox<String> cbPacientes;
    private String nomeMedicoLogado;

    // Modificado: O construtor agora recebe os pacientes que vieram da tabela da secretaria
    public TelaSelecionarPaciente(String nomeMedico, String[] pacientesAgendados) {
        this.nomeMedicoLogado = nomeMedico;
        
        setTitle("VITA - Seleção de Prontuário");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        Color marromEscuro  = new Color(61, 28, 6);
        Color corGold       = new Color(193, 158, 103);
        Color fundoClaro    = new Color(244, 241, 234);

        JPanel p = new JPanel(null);
        p.setBackground(fundoClaro);
        setContentPane(p);

        // Cabeçalho
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 450, 50);
        header.setBackground(marromEscuro);
        p.add(header);

        JLabel lblTitulo = new JLabel("INICIAR CONSULTA MÉDICA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 15, 450, 20);
        header.add(lblTitulo);

        // Caixa de Escolha (Combobox)
        JLabel lblInstrucao = new JLabel("Selecione o paciente ativo da fila (Triagem):");
        lblInstrucao.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblInstrucao.setForeground(marromEscuro);
        lblInstrucao.setBounds(35, 75, 380, 20);
        p.add(lblInstrucao);

        // Inicializa o JComboBox com a lista dinâmica vinda da secretaria
        cbPacientes = new JComboBox<>(pacientesAgendados);
        cbPacientes.setBounds(35, 100, 380, 35);
        cbPacientes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbPacientes.setBackground(Color.WHITE);
        cbPacientes.setBorder(new LineBorder(corGold, 1));
        p.add(cbPacientes);

        // Botão de Confirmação
        JButton btnAbrir = new JButton("ABRIR ANAMNESE E PRONTUÁRIO");
        btnAbrir.setBounds(35, 155, 380, 40);
        btnAbrir.setBackground(corGold);
        btnAbrir.setForeground(marromEscuro);
        btnAbrir.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAbrir.setFocusPainted(false);
        btnAbrir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        p.add(btnAbrir);

        // Ação
        btnAbrir.addActionListener(e -> {
            if (cbPacientes.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Nenhum paciente selecionado ou fila vazia.", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String pacienteSelecionado = cbPacientes.getSelectedItem().toString();
            this.dispose(); 
            
            // Abre o prontuário profissional enviando o nome escolhido
            new TelaProntuario(pacienteSelecionado, nomeMedicoLogado).setVisible(true); 
        });
    }
}