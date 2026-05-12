package view;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class TelaLogin extends JFrame {
    // Paleta de cores
    static final Color VERDE_PRINCIPAL  = new Color(0x16, 0x68, 0x3F);
    static final Color VERDE_SECUNDARIO = new Color(0x4C, 0xAF, 0x68);
    static final Color VERDE_CLARO      = new Color(0xEB, 0xF5, 0xE9);
    static final Color FUNDO            = new Color(0xF5, 0xF6, 0xF7);
    static final Color TEXTO            = new Color(0x33, 0x33, 0x33);
    static final Color TEXTO_SUAVE      = new Color(0x6B, 0x72, 0x80);
    static final Color DESTAQUE         = new Color(0xF2, 0x8C, 0x28);
    static final Color BRANCO           = Color.WHITE;
    static final Color BORDA            = new Color(0xE2, 0xE8, 0xF0);
    //  Fontes
    static final Font FONTE_TITULO   = new Font("Segoe UI", Font.BOLD,  22);
    static final Font FONTE_SUBTIT   = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONTE_LABEL    = new Font("Segoe UI", Font.BOLD,  12);
    static final Font FONTE_CAMPO    = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONTE_BOTAO    = new Font("Segoe UI", Font.BOLD,  13);
    static final Font FONTE_MENU     = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONTE_LINK     = new Font("Segoe UI", Font.PLAIN, 12);

    private JTextField campoCpfCnpj;
    private JPasswordField campoSenha;
    private JCheckBox checkLembrar;

    public TelaLogin() {
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    // Configurações básicas do JFrame
    private void configurarJanela() {
        setTitle("ReAlimenta | Sistema de Doação de Alimentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 580);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(FUNDO);
        setLayout(new BorderLayout());
    }

    // Monta todos os painéis da tela
    private void construirInterface() {
        add(criarPainelLateral(), BorderLayout.WEST);
        add(criarPainelCentral(), BorderLayout.CENTER);
    }

    //  PAINEL LATERAL
    // Painel verde escuro com logo, nome e menu
    private JPanel criarPainelLateral() {
        JPanel painel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Gradiente sutil do verde principal
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, VERDE_PRINCIPAL,
                        0, getHeight(), new Color(0x0D, 0x4A, 0x2C));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painel.setPreferredSize(new Dimension(220, 0));
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        // Logo maçã
        painel.add(criarLogoMaca());
        painel.add(Box.createVerticalStrut(8));

        // Nome do sistema
        JLabel lblNome = new JLabel("ReAlimenta");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblNome.setForeground(BRANCO);
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(lblNome);

        painel.add(Box.createVerticalStrut(6));
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 60));
        sep.setMaximumSize(new Dimension(160, 1));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(sep);
        painel.add(Box.createVerticalStrut(20));

        // Itens do menu
        painel.add(criarItemMenu("assets/icons/home.png", "Início",() -> {}));
        painel.add(criarItemMenu("assets/icons/info.png", "Sobre o Projeto", () -> mostrarSobreProjeto()));
        painel.add(criarItemMenu("assets/icons/help.png", "Como Funciona",   () -> mostrarComoFunciona()));
        painel.add(Box.createVerticalGlue());
        // Versão
        JLabel lblVersao = new JLabel("⛨  Versão 1.0.0");
        lblVersao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblVersao.setForeground(new Color(255, 255, 255, 130));
        lblVersao.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(lblVersao);
        return painel;
    }

    // Desenha a maçã vermelha com folha verde via Graphics2D
    private JLabel criarLogoMaca() {
        JLabel label = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cx = getWidth() / 2, cy = getHeight() / 2 + 4;
                // Corpo da maçã
                g2.setColor(new Color(0xE0, 0x30, 0x30));
                g2.fillOval(cx - 22, cy - 22, 44, 42);
                // Entalhe superior
                g2.setColor(VERDE_PRINCIPAL);
                g2.fillOval(cx - 8, cy - 27, 14, 12);
                g2.fillOval(cx - 4, cy - 27, 14, 12);
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
        label.setMaximumSize(new Dimension(70, 70));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // Cria um item de menu lateral com ícone e texto
    private JPanel criarItemMenu(
            String caminhoIcone,
            String texto,
            Runnable acao
    ) {
        JPanel item = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 8, 6)
        ) {
            boolean hover = false;
            {
                setOpaque(false);
                setCursor(
                        Cursor.getPredefinedCursor(
                                Cursor.HAND_CURSOR
                        )
                );
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
                        acao.run();
                    }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                if (hover) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(255, 255, 255, 30));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };

        item.setMaximumSize(new Dimension(180, 36));

        // ÍCONE
        ImageIcon icon = new ImageIcon(caminhoIcone);
        Image img = icon.getImage().getScaledInstance(
                18,
                18,
                Image.SCALE_SMOOTH
        );
        JLabel lblIcone = new JLabel(new ImageIcon(img));

        // TEXTO
        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(FONTE_MENU);
        lblTexto.setForeground(
                new Color(255, 255, 255, 220)
        );
        item.add(lblIcone);
        item.add(lblTexto);

        return item;
    }

    // PAINEL CENTRAL
    // Área central com fundo claro e card de login
    private JPanel criarPainelCentral() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(FUNDO);
        wrapper.add(criarCardLogin());
        return wrapper;
    }
    // Card branco arredondado com formulário de login
    private JPanel criarCardLogin() {
        JPanel card = new RoundedPanel(20, BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(
                BorderFactory.createEmptyBorder(36, 40, 36, 40)
        );
        card.setPreferredSize(new Dimension(380, 460));

        // TÍTULO
        JLabel lblTitulo = centrado(new JLabel("Bem-vindo(a)!"));
        lblTitulo.setFont(FONTE_TITULO);
        lblTitulo.setForeground(TEXTO);
        JLabel lblSub = centrado(new JLabel("Faça login para continuar"));
        lblSub.setFont(FONTE_SUBTIT);
        lblSub.setForeground(TEXTO_SUAVE);
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(4));
        card.add(lblSub);
        card.add(Box.createVerticalStrut(24));

        // CPF / CNPJ
        card.add(criarLabel("CPF ou CNPJ"));
        card.add(Box.createVerticalStrut(4));
        campoCpfCnpj = new JTextField();
        card.add(criarCampoTexto(campoCpfCnpj, "Digite seu CPF ou CNPJ", "assets/icons/user.png"));

        card.add(Box.createVerticalStrut(14));

        // SENHA
        card.add(criarLabel("Senha"));
        card.add(Box.createVerticalStrut(4));
        campoSenha = new JPasswordField();
        card.add(criarCampoSenha(campoSenha, "Digite sua senha"));
        card.add(Box.createVerticalStrut(10));

        // LEMBRAR / ESQUECEU
        card.add(criarLinhaLembrarEsqueceu());
        card.add(Box.createVerticalStrut(20));

        // BOTÃO ENTRAR
        card.add(
                criarBotaoPrimario("Entrar", e -> realizarLogin())
        );
        card.add(Box.createVerticalStrut(14));

        // SEPARADOR
        card.add(criarSeparadorOu());
        card.add(Box.createVerticalStrut(10));
        JLabel lblAinda =
                centrado(new JLabel("Ainda não tem uma conta?"));
        lblAinda.setFont(FONTE_SUBTIT);
        lblAinda.setForeground(TEXTO_SUAVE);
        card.add(lblAinda);
        card.add(Box.createVerticalStrut(10));

        // BOTÃO CONSUMIDOR
        card.add(
                criarBotaoSecundario("Cadastrar como Consumidor", e -> abrirTelaCadastroConsumidor())
        );
        card.add(Box.createVerticalStrut(8));

        // BOTÃO COMERCIANTE
        card.add(
                criarBotaoSecundario("Cadastrar como Comerciante", e -> abrirTelaCadastroComerciante())
        );

        return card;
    }

    // COMPONENTES REUTILIZÁVEIS
    //JLabel de label de campo
    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONTE_LABEL);
        lbl.setForeground(TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }
    // Campo de texto genérico com ícone-placeholder
    private JPanel criarCampoTexto(
            JTextField campo,
            String placeholder,
            String caminhoIcone
    ) {

        campo.setFont(TelaLogin.FONTE_CAMPO);
        campo.setForeground(TelaLogin.TEXTO);
        campo.setCaretColor(TelaLogin.VERDE_PRINCIPAL);
        campo.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
        campo.setOpaque(false);

        TelaLogin.adicionarPlaceholder(campo, placeholder);

        JPanel wrapper = criarWrapperCampo();

        ImageIcon icon = new ImageIcon(caminhoIcone);

        Image img = icon.getImage().getScaledInstance(
                20,
                20,
                Image.SCALE_SMOOTH
        );

        JLabel lblIcone =
                new JLabel(new ImageIcon(img));

        wrapper.add(lblIcone, BorderLayout.WEST);

        wrapper.add(campo, BorderLayout.CENTER);

        return wrapper;
    }

    // Campo de senha com botão mostrar/ocultar
    private JPanel criarCampoSenha(
            JPasswordField campo,
            String placeholder
    ) {

        campo.setFont(FONTE_CAMPO);
        campo.setForeground(TEXTO);
        campo.setCaretColor(VERDE_PRINCIPAL);
        campo.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
        campo.setOpaque(false);
        campo.setEchoChar('●');

        JPanel wrapper = criarWrapperCampo();

        // Ícone cadeado
        ImageIcon lockIcon =
                new ImageIcon("assets/icons/lock.png");
        Image lockImg = lockIcon.getImage().getScaledInstance(
                20,
                20,
                Image.SCALE_SMOOTH
        );
        JLabel lblIcone =
                new JLabel(new ImageIcon(lockImg));
        // Ícone olho
        ImageIcon eyeIcon =
                new ImageIcon("assets/icons/eye.png");
        Image eyeImg = eyeIcon.getImage().getScaledInstance(
                18,
                18,
                Image.SCALE_SMOOTH
        );
        JLabel olho =
                new JLabel(new ImageIcon(eyeImg));
        olho.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );
        olho.addMouseListener(new MouseAdapter() {

            boolean visivel = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                visivel = !visivel;
                campo.setEchoChar(
                        visivel ? (char) 0 : '●'
                );
            }
        });
        wrapper.add(lblIcone, BorderLayout.WEST);
        wrapper.add(campo, BorderLayout.CENTER);
        wrapper.add(olho, BorderLayout.EAST);
        return wrapper;
    }

    // Container arredondado para campos de input
    private JPanel criarWrapperCampo() {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(BORDA);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
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
    // Linha com checkbox "Lembrar-me" e link "Esqueceu sua senha?"
    private JPanel criarLinhaLembrarEsqueceu() {
        JPanel linha = new JPanel(new BorderLayout());
        linha.setOpaque(false);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);

        checkLembrar = new JCheckBox("Lembrar-me");
        checkLembrar.setFont(FONTE_LINK);
        checkLembrar.setForeground(TEXTO_SUAVE);
        checkLembrar.setOpaque(false);
        checkLembrar.setFocusPainted(false);

        JLabel linkEsqueceu = new JLabel("<html><u>Esqueceu sua senha?</u></html>");
        linkEsqueceu.setFont(FONTE_LINK);
        linkEsqueceu.setForeground(VERDE_SECUNDARIO);
        linkEsqueceu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkEsqueceu.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(TelaLogin.this,
                        "Entre em contato com o administrador do sistema.",
                        "Recuperação de Senha", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        linha.add(checkLembrar, BorderLayout.WEST);
        linha.add(linkEsqueceu, BorderLayout.EAST);
        return linha;
    }
    // Separador visual "── ou ──"
    private JPanel criarSeparadorOu() {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator esq = new JSeparator(); esq.setForeground(BORDA);
        JSeparator dir = new JSeparator(); dir.setForeground(BORDA);
        JLabel ou = new JLabel("ou"); ou.setFont(FONTE_SUBTIT); ou.setForeground(TEXTO_SUAVE);
        ou.setHorizontalAlignment(SwingConstants.CENTER);

        p.add(esq, BorderLayout.WEST);
        p.add(ou, BorderLayout.CENTER);
        p.add(dir, BorderLayout.EAST);
        return p;
    }
    // Botão primário verde com hover
    JButton criarBotaoPrimario(String texto, ActionListener acao) {
        JButton btn = new JButton(texto) {
            boolean hover = false;
            { addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
            }); }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color cor = hover ? VERDE_SECUNDARIO : VERDE_PRINCIPAL;
                g2.setColor(cor);
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
        btn.setForeground(BRANCO);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 42));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(acao);
        return btn;
    }
    // Botão secundário com borda verde
    JButton criarBotaoSecundario(String texto, ActionListener acao) {
        JButton btn = new JButton(texto) {
            boolean hover = false;
            { addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
            }); }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? VERDE_CLARO : BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(VERDE_PRINCIPAL);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 10, 10);
                g2.setColor(VERDE_PRINCIPAL);
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
        btn.setPreferredSize(new Dimension(0, 40));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(acao);
        return btn;
    }
    // AÇÕES
    private void realizarLogin() {
        String cpfCnpj = campoCpfCnpj.getText().trim();
        String senha   = new String(campoSenha.getPassword()).trim();
        if (cpfCnpj.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha o CPF/CNPJ e a senha para continuar.",
                    "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this,
                "Login realizado com sucesso!\n(Integração com backend pendente)",
                "Bem-vindo(a)!", JOptionPane.INFORMATION_MESSAGE);
    }
    private void abrirTelaCadastroConsumidor() {
        new TelaCadastroConsumidor(this);
        setVisible(false);
    }
    private void abrirTelaCadastroComerciante() {
        new TelaCadastroComerciante(this);
        setVisible(false);
    }
    private void mostrarSobreProjeto() {
        JOptionPane.showMessageDialog(this,
                "O ReAlimenta é um sistema desenvolvido\npara reduzir o desperdício de alimentos,\n"
                        + "conectando comerciantes que possuem\nalimentos próximos do vencimento com\n"
                        + "consumidores interessados em aproveitá-los\natravés de promoções e doações.\n\nVersão: 1.0.0",
                "Sobre o Projeto", JOptionPane.INFORMATION_MESSAGE);
    }
    private void mostrarComoFunciona() {
        JOptionPane.showMessageDialog(this,
                "1. Comerciantes cadastram alimentos.\n"
                        + "2. O sistema monitora a validade.\n"
                        + "3. O comerciante cria promoções ou doações.\n"
                        + "4. Consumidores visualizam e solicitam.\n"
                        + "5. O sistema organiza a fila automaticamente.\n"
                        + "6. Todos saem ganhando e o alimento não é desperdiçado!",
                "Como Funciona", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── UTILITÁRIOS ───────────────────────────────────────────────
    // Centraliza qualquer JLabel
    private <T extends JLabel> T centrado(T label) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
    // Adiciona texto placeholder a um JTextField
    static void adicionarPlaceholder(JTextField campo, String placeholder) {
        campo.setForeground(new Color(0xA0, 0xAE, 0xC0));
        campo.setText(placeholder);
        campo.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(new Color(0x33, 0x33, 0x33));
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(new Color(0xA0, 0xAE, 0xC0));
                }
            }
        });
    }

    // PAINEL ARREDONDADO
    // JPanel com cantos arredondados e sombra sutil
    static class RoundedPanel extends JPanel {
        private final int raio;
        private final Color cor;

        RoundedPanel(int raio, Color cor) {
            this.raio = raio;
            this.cor  = cor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Sombra
            for (int i = 6; i > 0; i--) {
                int alpha = (int)(40.0 / i);
                g2.setColor(new Color(0, 0, 0, alpha));
                g2.fillRoundRect(i, i, getWidth() - i * 2, getHeight() - i * 2, raio + i, raio + i);
            }
            // Fundo branco
            g2.setColor(cor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), raio, raio);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}