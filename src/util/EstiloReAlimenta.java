package util;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public final class EstiloReAlimenta {
    // CORES
    public static final Color VERDE_PRINCIPAL  = new Color(0x0E, 0x5F, 0x3B);
    public static final Color VERDE_SECUNDARIO = new Color(0x1F, 0x7A, 0x4D);
    public static final Color VERDE_CLARO      = new Color(0xEB, 0xF5, 0xE9);
    public static final Color FUNDO            = new Color(0xF5, 0xF7, 0xF6);
    public static final Color BRANCO           = Color.WHITE;
    public static final Color TEXTO            = new Color(0x1E, 0x1E, 0x1E);
    public static final Color TEXTO_SUAVE      = new Color(0x6B, 0x72, 0x80);
    public static final Color BORDA            = new Color(0xE2, 0xE8, 0xF0);
    // 2. FONTES
    public static final Font FONTE_TITULO    = new Font("Segoe UI", Font.BOLD,  22);
    public static final Font FONTE_SUBTITULO = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONTE_LABEL     = new Font("Segoe UI", Font.BOLD,  12);
    public static final Font FONTE_CAMPO     = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONTE_BOTAO     = new Font("Segoe UI", Font.BOLD,  13);
    public static final Font FONTE_MENU      = new Font("Segoe UI", Font.PLAIN, 13);

    // Construtor privado — classe apenas estática
    private EstiloReAlimenta() {}

    // CLASSE INTERNA—RoundedPanel
    public static class RoundedPanel extends JPanel {
        private final int   raio;
        private final Color cor;
        public RoundedPanel(int raio, Color cor) {
            this.raio = raio;
            this.cor  = cor;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            // Sombra suave em camadas
            for (int i = 6; i > 0; i--) {
                int alpha = (int) (40.0 / i);
                g2.setColor(new Color(0, 0, 0, alpha));
                g2.fillRoundRect(i, i, getWidth()  - i * 2, getHeight() - i * 2, raio + i, raio + i);}
            // Fundo do card
            g2.setColor(cor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), raio, raio);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // criarBotaoPrimario — botão verde preenchido com hover
    public static JButton criarBotaoPrimario(String texto, ActionListener acao) {
        JButton btn = new JButton(texto) {
            boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hover = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? VERDE_SECUNDARIO : VERDE_PRINCIPAL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(BRANCO);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth()  - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        btn.setFont(FONTE_BOTAO);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setPreferredSize(new Dimension(0, 42));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (acao != null) btn.addActionListener(acao);
        return btn;
    }

    // criarBotaoSecundario — botão com borda verde e fundo branco
    public static JButton criarBotaoSecundario(String texto, ActionListener acao) {
        JButton btn = new JButton(texto) {
            boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hover = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? VERDE_CLARO : BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(VERDE_PRINCIPAL);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth()  - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        btn.setFont(FONTE_BOTAO);
        btn.setForeground(VERDE_PRINCIPAL);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setPreferredSize(new Dimension(0, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (acao != null) btn.addActionListener(acao);
        return btn;
    }

    // ÍCONES IKONLI
    public static JLabel criarIcone(
            FontAwesomeSolid icone,
            int tamanho,
            Color cor
    ) {
        FontIcon icon = FontIcon.of(icone, tamanho);
        icon.setIconColor(cor);
        JLabel lbl = new JLabel(icon);
        lbl.setOpaque(false);
        return lbl;
    }

    // criarCampoTexto — wrapper arredondado com ícone PNG e campo
    public static JPanel criarCampoTexto(JTextField campo, String placeholder, FontAwesomeSolid icone) {
        estilizarCampo(campo);
        adicionarPlaceholder(campo, placeholder);
        JPanel w = criarWrapper();
        w.add(criarIcone(icone, 16, TEXTO_SUAVE), BorderLayout.WEST);
        w.add(campo, BorderLayout.CENTER);
        return w;
    }

    // criarCampoSenha — wrapper arredondado com ícone cadeado + olho
    public static JPanel criarCampoSenha(JPasswordField campo, String placeholder) {
        estilizarCampoSenha(campo, placeholder);
        JPanel w = criarWrapper();
        w.add(criarIcone(FontAwesomeSolid.LOCK, 16, TEXTO_SUAVE), BorderLayout.WEST);
        w.add(campo, BorderLayout.CENTER);
        w.add(criarBotaoOlho(campo), BorderLayout.EAST);
        return w;
    }

    // criarLabel — label padrão para rótulos de campos
    public static JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONTE_LABEL);
        lbl.setForeground(TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    // criarTitulo — label grande para títulos de tela
    public static JLabel criarTitulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONTE_TITULO);
        lbl.setForeground(TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    // criarSubtitulo — label menor para subtítulos / dicas
    public static JLabel criarSubtitulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONTE_SUBTITULO);
        lbl.setForeground(TEXTO_SUAVE);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    // adicionarPlaceholder — texto cinza que desaparece ao focar
    public static void adicionarPlaceholder(JTextField campo, String placeholder) {
        campo.setForeground(new Color(0xA0, 0xAE, 0xC0));
        campo.setText(placeholder);
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(TEXTO);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(new Color(0xA0, 0xAE, 0xC0));
                }
            }
        });
    }

    // ÍCONES PNG com redimensionamento automático
    public static JLabel carregarIcone(String caminho, int tamanho) {
        try {
            ImageIcon icon = new ImageIcon(caminho);
            if (icon.getIconWidth() <= 0) return new JLabel();
            Image img = icon.getImage().getScaledInstance(
                    tamanho, tamanho, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        } catch (Exception e) {
            return new JLabel();
        }
    }

    // Cabeçalho padrão para telas de cadastro

    public static JPanel criarCabecalho(String titulo, String subtitulo, FontAwesomeSolid iconeDir, Runnable acaoVoltar) {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(
                        new GradientPaint(0, 0, VERDE_PRINCIPAL, getWidth(), 0, new Color(0x1A, 0x7A, 0x4A))
                );
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 76));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // BOTÃO VOLTAR
        header.add(criarBotaoVoltar(acaoVoltar), BorderLayout.WEST);

        // CENTRO
        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(BRANCO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(255,255,255,200));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        centro.add(Box.createVerticalGlue());
        centro.add(lblTitulo);
        centro.add(Box.createVerticalStrut(2));
        centro.add(lblSub);
        centro.add(Box.createVerticalGlue());
        header.add(centro, BorderLayout.CENTER);

        // ÍCONE DIREITO
        if (iconeDir != null) {
            JLabel ico = criarIcone(iconeDir, 22, BRANCO);
            header.add(ico, BorderLayout.EAST);
        }
        return header;
    }

    // LOGO MAÇÃ — desenhada via Graphics2D
    public static JLabel criarLogoMaca() {
        JLabel label = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cx = getWidth() / 2, cy = getHeight() / 2 + 4;

                // Corpo vermelho
                g2.setColor(new Color(0xE0, 0x30, 0x30));
                g2.fillOval(cx - 22, cy - 22, 44, 42);
                // Entalhe superior
                g2.setColor(VERDE_PRINCIPAL);
                g2.fillOval(cx - 8,  cy - 27, 14, 12);
                g2.fillOval(cx - 4,  cy - 27, 14, 12);
                // Folha
                g2.setColor(VERDE_SECUNDARIO);
                Path2D folha = new Path2D.Double();
                folha.moveTo(cx + 2, cy - 24);
                folha.curveTo(cx + 18, cy - 36, cx + 26, cy - 20, cx + 6, cy - 18);
                folha.closePath();
                g2.fill(folha);
                // Brilho
                g2.setColor(new Color(255, 255, 255, 70));
                g2.fillOval(cx - 14, cy - 16, 10, 14);
                g2.dispose();
            }
        };
        label.setPreferredSize(new Dimension(70, 70));
        label.setMaximumSize (new Dimension(70, 70));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

// ITEM DE MENU LATERAL com IKONLI
    public static JPanel criarItemMenu(FontAwesomeSolid icone, String texto, Runnable acao) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8)) {
            boolean hover = false;
            {
                setOpaque(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        hover = true;
                        repaint();
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        hover = false;
                        repaint();
                    }
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (acao != null) {acao.run();}
                    }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                if (hover) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(255, 255, 255, 30));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10
                    );
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        item.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        // ÍCONE
        JLabel lblIcone = criarIcone(icone, 16, BRANCO);

        // TEXTO
        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(FONTE_MENU);
        lblTexto.setForeground(BRANCO);

        // ADD
        item.add(lblIcone);
        item.add(lblTexto);
        return item;
    }

    // SEPARADOR "ou" — linha horizontal com texto central
    public static JPanel criarSeparadorOu() {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator esq = new JSeparator(); esq.setForeground(BORDA);
        JSeparator dir = new JSeparator(); dir.setForeground(BORDA);

        JLabel ou = new JLabel("ou");
        ou.setFont(FONTE_SUBTITULO);
        ou.setForeground(TEXTO_SUAVE);
        ou.setHorizontalAlignment(SwingConstants.CENTER);

        p.add(esq, BorderLayout.WEST);
        p.add(ou,  BorderLayout.CENTER);
        p.add(dir, BorderLayout.EAST);
        return p;
    }


    // ÁREA DE CONTEÚDO SCROLLÁVEL padronizada
    public static JScrollPane criarAreaScroll(JPanel conteudo) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(FUNDO);
        wrapper.setBorder(BorderFactory.createEmptyBorder(24, 0, 24, 0));
        wrapper.add(conteudo);

        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        return scroll;
    }

    // Wrapper arredondado com borda — usado como fundo de campos
    static JPanel criarWrapper() {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(BORDA);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        return p;
    }

    // Aplica estilo padrão a um JTextField
    private static void estilizarCampo(JTextField campo) {
        campo.setFont(FONTE_CAMPO);
        campo.setForeground(TEXTO);
        campo.setCaretColor(VERDE_PRINCIPAL);
        campo.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
        campo.setOpaque(false);
    }

    // Aplica estilo padrão a um JPasswordField
    private static void estilizarCampoSenha(JPasswordField campo, String placeholder) {
        estilizarCampo(campo);
        campo.setEchoChar('●');
        adicionarPlaceholder(campo, placeholder);
    }

    // Botão olho para alternar visibilidade de senha
    private static JLabel criarBotaoOlho(JPasswordField campo) {
        JLabel olho = criarIcone(FontAwesomeSolid.EYE, 16, TEXTO_SUAVE);
        olho.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        olho.addMouseListener(new MouseAdapter() {
            boolean visivel = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                visivel = !visivel;
                campo.setEchoChar(visivel ? (char) 0 : '●');
            }
        });
        return olho;
    }

    // Botão ← Voltar para cabeçalhos
    private static JButton criarBotaoVoltar(Runnable acao) {
        JButton btn = new JButton();
        btn.setIcon(FontIcon.of(FontAwesomeSolid.ARROW_LEFT, 18, BRANCO));
        btn.setPreferredSize(new Dimension(40, 40));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        btn.setOpaque(true);
                        btn.setBackground(new Color(255, 255, 255, 40));
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        btn.setOpaque(false);
                        btn.setBackground(null);
                    }
                }
        );
        btn.addActionListener(
                e -> {
                    if (acao != null) {
                        acao.run();
                    }
                }
        );
        return btn;
    }
}