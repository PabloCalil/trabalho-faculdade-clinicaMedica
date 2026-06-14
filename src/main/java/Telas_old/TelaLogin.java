package Telas_old; // <-- Ajustado para a sua árvore de arquivos!

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelaLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnLogin;

    public TelaLogin() {
        setTitle("🏥 Sistema Clínica Médica - Autenticação");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 580);
        setLocationRelativeTo(null);
        setResizable(false);

        // PALETA DE CORES TERROSAS PADRONIZADA DO PROJETO
        Color corCremeClaro   = new Color(251, 251, 250); // #FBFBFA
        Color corDestaqueGold = new Color(193, 158, 103); // #C19E67
        Color corTomMedio     = new Color(110, 102, 95);  // #6E665F
        Color corRotuloCinza  = new Color(180, 169, 158); // #B4A99E
        Color corMarromEscuro = new Color(61, 28, 6);     // #3D1C06

        // Painel Principal dividindo a tela (Design Moderno de Duas Colunas)
        JPanel painelFundo = new JPanel();
        painelFundo.setBackground(corTomMedio);
        painelFundo.setLayout(null);
        setContentPane(painelFundo);

        // ====================================================================
        // PAINEL ESQUERDO (Apresentação Visual / Conceito)
        // ====================================================================
        JPanel painelEsquerdo = new JPanel();
        painelEsquerdo.setBackground(corMarromEscuro);
        painelEsquerdo.setBounds(0, 0, 400, 580);
        painelEsquerdo.setLayout(null);
        painelEsquerdo.setBorder(new LineBorder(corDestaqueGold, 1));
        painelFundo.add(painelEsquerdo);

        JLabel lblLogoIcone = new JLabel("🏥");
        lblLogoIcone.setFont(new Font("Segoe UI", Font.PLAIN, 70));
        lblLogoIcone.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogoIcone.setBounds(100, 150, 200, 80);
        painelEsquerdo.add(lblLogoIcone);

        JLabel lblNomeClinica = new JLabel("Butões de Lavanda");
        lblNomeClinica.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblNomeClinica.setForeground(corDestaqueGold);
        lblNomeClinica.setHorizontalAlignment(SwingConstants.CENTER);
        lblNomeClinica.setBounds(50, 240, 300, 45);
        painelEsquerdo.add(lblNomeClinica);

        JLabel lblSubClinica = new JLabel("Centro de Atendimento Médico");
        lblSubClinica.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubClinica.setForeground(corCremeClaro);
        lblSubClinica.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubClinica.setBounds(50, 290, 300, 20);
        painelEsquerdo.add(lblSubClinica);

        // ====================================================================
        // PAINEL DIREITO (Formulário de Acesso)
        // ====================================================================
        JLabel lblBoasVindas = new JLabel("Sign In");
        lblBoasVindas.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBoasVindas.setForeground(corCremeClaro);
        lblBoasVindas.setBounds(460, 60, 200, 40);
        painelFundo.add(lblBoasVindas);

        JLabel lblInstrucao = new JLabel("Insira suas credenciais corporativas para acessar.");
        lblInstrucao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblInstrucao.setForeground(corRotuloCinza);
        lblInstrucao.setBounds(460, 105, 350, 20);
        painelFundo.add(lblInstrucao);

        // Campo Usuário
        JLabel lblUsuario = new JLabel("Nome de Usuário / Identificador:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setForeground(corRotuloCinza);
        lblUsuario.setBounds(460, 160, 300, 20);
        painelFundo.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(460, 185, 330, 40);
        txtUsuario.setBackground(corMarromEscuro);
        txtUsuario.setForeground(corCremeClaro);
        txtUsuario.setCaretColor(corCremeClaro);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(corDestaqueGold, 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));
        painelFundo.add(txtUsuario);

        // Campo Senha
        JLabel lblSenha = new JLabel("Senha de Acesso:");
        lblSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSenha.setForeground(corRotuloCinza);
        lblSenha.setBounds(460, 250, 200, 20);
        painelFundo.add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(460, 275, 330, 40);
        txtSenha.setBackground(corMarromEscuro);
        txtSenha.setForeground(corCremeClaro);
        txtSenha.setCaretColor(corCremeClaro);
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(corDestaqueGold, 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));
        painelFundo.add(txtSenha);

        // Botão de Entrada Seguro (Sem overrides problemáticos de mouse listener)
        btnLogin = new JButton("SIGN IN");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(corDestaqueGold);
        btnLogin.setForeground(corMarromEscuro);
        btnLogin.setBounds(460, 360, 330, 45);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(new LineBorder(corDestaqueGold.darker(), 1, true));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelFundo.add(btnLogin);

        // Nota de Rodapé
        JLabel lblSuporte = new JLabel("Esqueceu suas credenciais? Contate o TI.");
        lblSuporte.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblSuporte.setForeground(corRotuloCinza);
        lblSuporte.setBounds(460, 420, 300, 20);
        painelFundo.add(lblSuporte);

        // ====================================================================
        // CONTROLE DE FLUXO / DIRECIONAMENTO INTELIGENTE
        // ====================================================================
        btnLogin.addActionListener(e -> {
            String user = txtUsuario.getText().trim();
            String password = new String(txtSenha.getPassword());

            if (user.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, digite as credenciais.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            } 
            // 1. Fluxo de Entrada do Médico
            else if (user.equalsIgnoreCase("medico")) {
                JOptionPane.showMessageDialog(this, "Acesso Médico Autorizado! Bem-vindo, Doutor.", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
                new TelaMedico().setVisible(true);
                this.dispose(); // Fecha o login
            } 
            // 2. Fluxo de Entrada da Secretaria (Qualquer outro usuário digitado para testes)
            else {
                JOptionPane.showMessageDialog(this, "Acesso Administrativo Autorizado! Painel da Secretaria liberado.", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
                new TelaSecretaria().setVisible(true);
                this.dispose(); // Fecha o login
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}