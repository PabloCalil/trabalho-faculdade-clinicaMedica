package com.mycompany.clinicamedica.newpackage.view.Medico;

import com.mycompany.clinicamedica.newpackage.view.ui.Tema;
import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TelaSelecionarPaciente extends JFrame {

    private JComboBox<String> cbPacientes;
    private String nomeMedicoLogado;
    private Map<String, int[]> mapaPacientes; // nome → [idPaciente, idConsulta]

    public TelaSelecionarPaciente(String nomeMedico, Map<String, int[]> pacientes) {
        this.nomeMedicoLogado = nomeMedico;
        this.mapaPacientes    = pacientes;

        setTitle("Health Equilibrium - Seleção de Prontuário");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        Color marromEscuro = Tema.MARROM_ESCURO;
        Color corGold      = Tema.GOLD;
        Color fundoClaro   = Tema.FUNDO_CLARO;

        JPanel p = new JPanel(null);
        p.setBackground(fundoClaro);
        setContentPane(p);

        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 450, 50);
        header.setBackground(marromEscuro);
        p.add(header);

        JLabel lblTitulo = new JLabel("INICIAR CONSULTA MÉDICA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 15, 450, 20);
        header.add(lblTitulo);

        JLabel lblInstrucao = new JLabel("Selecione o paciente ativo da fila (Triagem):");
        lblInstrucao.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblInstrucao.setForeground(marromEscuro);
        lblInstrucao.setBounds(35, 75, 380, 20);
        p.add(lblInstrucao);

        // Popula o combo com os nomes do mapa
        cbPacientes = new JComboBox<>(pacientes.keySet().toArray(new String[0]));
        cbPacientes.setBounds(35, 100, 380, 35);
        cbPacientes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbPacientes.setBackground(Color.WHITE);
        cbPacientes.setBorder(new LineBorder(corGold, 1));
        p.add(cbPacientes);

        JButton btnAbrir = new JButton("ABRIR ANAMNESE E PRONTUÁRIO");
        btnAbrir.setBounds(35, 155, 380, 40);
        btnAbrir.setBackground(corGold);
        btnAbrir.setForeground(marromEscuro);
        btnAbrir.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAbrir.setFocusPainted(false);
        btnAbrir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        p.add(btnAbrir);

        btnAbrir.addActionListener(e -> {
            if (cbPacientes.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this,
                    "Nenhum paciente selecionado ou fila vazia.",
                    "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nomePaciente = cbPacientes.getSelectedItem().toString();
            int[] ids           = mapaPacientes.get(nomePaciente);
            int idPaciente      = ids[0];
            int idConsulta      = ids[1];

            this.dispose();
            new TelaProntuario(nomePaciente, nomeMedicoLogado, idPaciente, idConsulta).setVisible(true);
        });
    }
}