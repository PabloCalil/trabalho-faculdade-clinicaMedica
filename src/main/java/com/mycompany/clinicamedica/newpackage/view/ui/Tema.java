package com.mycompany.clinicamedica.newpackage.view.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;

/**
 * Design system da aplicacao Health Equilibrium.
 *
 * Centraliza paleta de cores, fontes, dimensoes e fabricas de componentes
 * (botoes, cabecalho, tabela) para que TODAS as telas sigam o mesmo padrao
 * visual — tomando a TelaMedico como referencia.
 *
 * Regra de proporcao dos botoes: altura fixa (ALT_BOTAO) combinada com a
 * fonte FONTE_BOTAO, garantindo harmonia entre o texto e o tamanho do botao.
 */
public final class Tema {

    private Tema() {}

    // ── Identidade ──────────────────────────────────────────────────────────
    public static final String NOME_EMPRESA = "Health Equilibrium";

    // ── Paleta ──────────────────────────────────────────────────────────────
    public static final Color MARROM_ESCURO = new Color(61, 28, 6);
    public static final Color GOLD           = new Color(193, 158, 103);
    public static final Color FUNDO_CLARO     = new Color(244, 241, 234);
    public static final Color TOM_MEDIO       = new Color(110, 102, 95);
    public static final Color TEXTO_ESCURO    = new Color(44, 37, 32);
    public static final Color VERMELHO        = new Color(180, 70, 70);
    public static final Color BRANCO          = Color.WHITE;

    // ── Fontes ──────────────────────────────────────────────────────────────
    public static final Font FONTE_TITULO    = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONTE_SUBTITULO  = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONTE_SECAO       = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FONTE_BOTAO       = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONTE_TABELA       = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONTE_TABELA_HEADER = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONTE_ROTULO       = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONTE_CAMPO         = new Font("Segoe UI", Font.PLAIN, 14);

    // ── Dimensoes de botao (proporcao harmoniosa com FONTE_BOTAO) ────────────
    public static final int ALT_BOTAO = 40;
    public static final Dimension BOTAO_GRANDE = new Dimension(240, ALT_BOTAO); // menus laterais
    public static final Dimension BOTAO_MEDIO  = new Dimension(160, ALT_BOTAO); // barras de acao

    // ── Botoes ──────────────────────────────────────────────────────────────

    /** Botao de destaque: fundo dourado, texto marrom. Acao principal. */
    public static JButton botaoPrimario(String texto) {
        JButton b = base(texto);
        b.setBackground(GOLD);
        b.setForeground(MARROM_ESCURO);
        b.setBorder(new LineBorder(GOLD.darker(), 1, true));
        return b;
    }

    /** Botao secundario: fundo branco, texto marrom, borda dourada. */
    public static JButton botaoSecundario(String texto) {
        JButton b = base(texto);
        b.setBackground(BRANCO);
        b.setForeground(TEXTO_ESCURO);
        b.setBorder(new LineBorder(GOLD, 1, true));
        return b;
    }

    /** Botao neutro: fundo marrom, texto branco. Navegacao/menus. */
    public static JButton botaoNeutro(String texto) {
        JButton b = base(texto);
        b.setBackground(MARROM_ESCURO);
        b.setForeground(BRANCO);
        b.setBorder(new LineBorder(GOLD, 1, true));
        return b;
    }

    /** Botao de saida/acao destrutiva: fundo vermelho, texto branco. */
    public static JButton botaoPerigo(String texto) {
        JButton b = base(texto);
        b.setBackground(VERMELHO);
        b.setForeground(BRANCO);
        b.setBorder(new LineBorder(VERMELHO.darker(), 1, true));
        return b;
    }

    private static JButton base(String texto) {
        JButton b = new JButton(texto);
        b.setFont(FONTE_BOTAO);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(BOTAO_MEDIO);
        return b;
    }

    /** Ajusta o tamanho de um botao mantendo a altura padrao do tema. */
    public static JButton comLargura(JButton b, int largura) {
        b.setPreferredSize(new Dimension(largura, ALT_BOTAO));
        return b;
    }

    // ── Cabecalho padrao ────────────────────────────────────────────────────

    /**
     * Cabecalho marrom com titulo e subtitulo a esquerda e um componente
     * opcional a direita (ex.: botao Desconectar/Voltar).
     */
    public static JPanel cabecalho(String titulo, String subtitulo, JComponent direita) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MARROM_ESCURO);
        header.setBorder(new EmptyBorder(15, 40, 15, 40));

        JPanel txt = new JPanel(new GridLayout(subtitulo == null ? 1 : 2, 1));
        txt.setBackground(MARROM_ESCURO);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(FONTE_TITULO);
        lblTitulo.setForeground(BRANCO);
        txt.add(lblTitulo);

        if (subtitulo != null) {
            JLabel lblSub = new JLabel(subtitulo);
            lblSub.setFont(FONTE_SUBTITULO);
            lblSub.setForeground(GOLD);
            txt.add(lblSub);
        }
        header.add(txt, BorderLayout.WEST);

        if (direita != null) header.add(direita, BorderLayout.EAST);
        return header;
    }

    // ── Tabela ──────────────────────────────────────────────────────────────

    /** Aplica o estilo padrao (cores, fontes, selecao) a uma JTable. */
    public static void estilizarTabela(JTable tabela) {
        tabela.setRowHeight(30);
        tabela.setFont(FONTE_TABELA);
        tabela.setSelectionBackground(GOLD);
        tabela.setSelectionForeground(MARROM_ESCURO);
        tabela.setGridColor(new Color(220, 215, 205));
        JTableHeader h = tabela.getTableHeader();
        h.setBackground(MARROM_ESCURO);
        h.setForeground(GOLD);
        h.setFont(FONTE_TABELA_HEADER);
        h.setReorderingAllowed(false);
    }

    /** Borda dourada padrao para envolver componentes (ex.: JScrollPane). */
    public static LineBorder bordaGold() {
        return new LineBorder(GOLD);
    }
}
