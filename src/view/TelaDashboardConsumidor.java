package view;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaDashboardConsumidor extends JFrame {
    private final String nomeUsuario;

    // Item de menu ativo (para highlight futuro)
    private JPanel itemAtivo;
    public TelaDashboardConsumidor(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    //  CONFIGURAÇÃO DA JANELA
    private void configurarJanela() {
        setTitle("ReAlimenta | Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(860, 560));
        getContentPane().setBackground(EstiloReAlimenta.FUNDO);
        setLayout(new BorderLayout());
    }

    private void construirInterface() {
        add(criarSidebar(), BorderLayout.WEST);
        add(criarConteudo(), BorderLayout.CENTER);
    }

    // SIDEBAR
    private JPanel criarSidebar() {
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, EstiloReAlimenta.VERDE_PRINCIPAL, 0, getHeight(), new Color(0x0D, 0x4A, 0x2C)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(28, 16, 28, 16));

        // Logo + nome
        sidebar.add(EstiloReAlimenta.criarLogoMaca());
        sidebar.add(Box.createVerticalStrut(8));
        JLabel lblNome = new JLabel("ReAlimenta");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblNome.setForeground(EstiloReAlimenta.BRANCO);
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblNome);
        sidebar.add(Box.createVerticalStrut(4));

        // Avatar e usuário
        JLabel lblUsuario = new JLabel(nomeUsuario);
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUsuario.setForeground(new Color(255, 255, 255, 180));
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblUsuario);
        sidebar.add(Box.createVerticalStrut(6));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 50));
        sep.setMaximumSize(new Dimension(180, 1));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(18));

        // Itens de navegação
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.HOME, "Início"));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.TAG, "Promoções", new Runnable() {
            @Override
            public void run() {navegarPromocoes();}})
        );
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.GIFT, "Doações", new Runnable() {
            @Override
            public void run() {navegarDoacoes();}})
        );
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.CLIPBOARD_LIST, "Minhas Solicitações", new Runnable() {
            @Override
            public void run() {navegarSolicitacoes();}})
        );
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.BELL, "Notificações", new Runnable() {
            @Override
            public void run() {navegarNotificacoes();}})
        );

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.SIGN_OUT_ALT, "Sair", this::confirmarSaida));

        // Versão
        sidebar.add(Box.createVerticalStrut(10));
        JLabel lblVersao = new JLabel("⛨  Versão 1.0.0");
        lblVersao.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblVersao.setForeground(new Color(255, 255, 255, 100));
        lblVersao.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblVersao);

        return sidebar;
    }

    private JPanel criarItemMenuAtivo(FontAwesomeSolid icone, String texto) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(255,255,255,45));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 1);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        item.setOpaque(false);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        item.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        item.add(EstiloReAlimenta.criarIcone(icone, 16, EstiloReAlimenta.BRANCO));
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(EstiloReAlimenta.BRANCO);
        item.add(lbl);

        return item;
    }

    // CONTEÚDO PRINCIPAL
    private JComponent criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(EstiloReAlimenta.FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));

        conteudo.add(criarHeaderDashboard());
        conteudo.add(Box.createVerticalStrut(24));
        conteudo.add(criarCardsResumo());
        conteudo.add(Box.createVerticalStrut(28));
        conteudo.add(criarSecaoTitulo("🏷️  Promoções em Destaque", FontAwesomeSolid.TAG));
        conteudo.add(Box.createVerticalStrut(12));
        conteudo.add(criarCardsPromocoes());
        conteudo.add(Box.createVerticalStrut(28));
        conteudo.add(criarSecaoTitulo("🎁  Doações em Destaque", FontAwesomeSolid.GIFT));
        conteudo.add(Box.createVerticalStrut(12));
        conteudo.add(criarCardsDoacoes());
        conteudo.add(Box.createVerticalStrut(20));

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(EstiloReAlimenta.FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        return scroll;
    }

    //  HEADER DASHBOARD
    private JPanel criarHeaderDashboard() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Saudação
        JPanel esq = new JPanel();
        esq.setOpaque(false);
        esq.setLayout(new BoxLayout(esq, BoxLayout.Y_AXIS));

        JLabel lblOla = new JLabel("Olá, " + nomeUsuario + " 👋");
        lblOla.setFont(EstiloReAlimenta.FONTE_TITULO);
        lblOla.setForeground(EstiloReAlimenta.TEXTO);
        esq.add(lblOla);

        JLabel lblSub = new JLabel("Bem-vindo(a) de volta ao ReAlimenta.");
        lblSub.setFont(EstiloReAlimenta.FONTE_SUBTITULO);
        lblSub.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        esq.add(lblSub);

        header.add(esq, BorderLayout.WEST);

        // Ícone notificação (direita)
        JLabel icoBell = EstiloReAlimenta.criarIcone(FontAwesomeSolid.BELL, 22, EstiloReAlimenta.VERDE_PRINCIPAL);
        icoBell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        icoBell.setToolTipText("Notificações");
        header.add(icoBell, BorderLayout.EAST);

        return header;
    }

    // CARDS RESUMO
    private JPanel criarCardsResumo() {
        JPanel linha = new JPanel(new GridLayout(1, 4, 16, 0));
        linha.setOpaque(false);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        linha.add(criarCardResumo(FontAwesomeSolid.TAG,           new Color(0x1F, 0x7A, 0x4D), "12", "Promoções\nDisponíveis"));
        linha.add(criarCardResumo(FontAwesomeSolid.GIFT,          new Color(0x2E, 0x86, 0xC1), "7",  "Doações\nDisponíveis"));
        linha.add(criarCardResumo(FontAwesomeSolid.HOURGLASS_HALF,new Color(0xD4, 0x7F, 0x00), "3",  "Solicitações\nAtivas"));
        linha.add(criarCardResumo(FontAwesomeSolid.CHECK_CIRCLE,  new Color(0x27, 0xAE, 0x60), "18", "Concluídas"));

        return linha;
    }

    private JPanel criarCardResumo(FontAwesomeSolid icone, Color cor, String numero, String descricao) {
        RoundedPanel card = new RoundedPanel(12, EstiloReAlimenta.BRANCO) {
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
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // sombra
                for (int i = 5; i > 0; i--) {
                    g2.setColor(new Color(0, 0, 0, hover ? 12 : 7));
                    g2.fillRoundRect(i, i, getWidth() - i * 2, getHeight() - i * 2, 12 + i, 12 + i);
                }
                g2.setColor(hover ? new Color(0xF8, 0xFF, 0xF9) : EstiloReAlimenta.BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setLayout(new BorderLayout(10, 0));
        card.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));

        // Ícone colorido à esquerda
        JLabel ico = EstiloReAlimenta.criarIcone(icone, 28, cor);
        card.add(ico, BorderLayout.WEST);

        // Número + descrição
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel lblNum = new JLabel(numero);
        lblNum.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblNum.setForeground(EstiloReAlimenta.TEXTO);
        info.add(lblNum);

        for (String linha : descricao.split("\n")) {
            JLabel l = new JLabel(linha);
            l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            l.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
            info.add(l);
        }
        card.add(info, BorderLayout.CENTER);
        return card;
    }

    // ── TÍTULO DE SEÇÃO ──────────────────────────────────────────
    private JPanel criarSecaoTitulo(String texto, FontAwesomeSolid icone) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        p.add(EstiloReAlimenta.criarIcone(icone, 16, EstiloReAlimenta.VERDE_PRINCIPAL));

        JLabel lbl = new JLabel(texto.replaceAll("^.{3}", "").trim()); // remove emoji duplicado se vier
        lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setForeground(EstiloReAlimenta.TEXTO);
        p.add(lbl);
        return p;
    }

    // ── CARDS PROMOÇÕES ──────────────────────────────────────────
    private static class DadosPromocao {
        String nome;
        String loja;
        String categoria;
        String desconto;
        String precoAntigo;
        String precoAtual;
        String horario;
        String endereco;
        Color corIcone;

        public DadosPromocao(String nome, String loja, String categoria, String desconto, String precoAntigo, String precoAtual, String horario, String endereco, Color corIcone) {
            this.nome = nome;
            this.loja = loja;
            this.categoria = categoria;
            this.desconto = desconto;
            this.precoAntigo = precoAntigo;
            this.precoAtual = precoAtual;
            this.horario = horario;
            this.endereco = endereco;
            this.corIcone = corIcone;
        }
    }

    private JPanel criarCardsPromocoes() {
        List<DadosPromocao> lista = Arrays.asList(
                new DadosPromocao("Lasanha à Bolonhesa", "Restaurante da Mara", "Massas",
                        "40% OFF", "R$ 28,00", "R$ 16,80", "até 20h00", "Rua das Flores, 45", new Color(0xE74C3C)),
                new DadosPromocao("Pizza Marguerita", "Pizzaria Bela Napoli", "Pizzas",
                        "30% OFF", "R$ 45,00", "R$ 31,50", "até 21h30", "Av. Central, 120", new Color(0xE67E22)),
                new DadosPromocao("Bolo de Cenoura", "Confeitaria Doce Arte", "Bolos",
                        "50% OFF", "R$ 18,00", "R$  9,00", "até 18h00", "Rua do Comércio, 8", new Color(0xF39C12))
        );

        JPanel painel = new JPanel();
        painel.setOpaque(false);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        for (DadosPromocao d : lista) {
            painel.add(criarCardPromocaoHorizontal(d));
            painel.add(Box.createVerticalStrut(12));
        }
        return painel;
    }

    private JPanel criarCardPromocaoHorizontal(DadosPromocao d) {
        RoundedPanel card = new RoundedPanel(12, EstiloReAlimenta.BRANCO);
        card.setLayout(new BorderLayout(0, 0));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        card.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Faixa colorida lateral (substitui imagem)
        JPanel faixa = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(d.corIcone);
                g2.fillRoundRect(0, 0, getWidth() + 20, getHeight(), 12, 12);
                g2.dispose();
            }
        };
        faixa.setPreferredSize(new Dimension(72, 0));
        faixa.setLayout(new GridBagLayout());
        faixa.add(EstiloReAlimenta.criarIcone(FontAwesomeSolid.UTENSILS, 24, Color.WHITE));

        card.add(faixa, BorderLayout.WEST);

        // Info central
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 12));

        JLabel lblNome = new JLabel(d.nome);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNome.setForeground(EstiloReAlimenta.TEXTO);
        info.add(lblNome);

        JLabel lblLoja = new JLabel(d.loja + "  ·  " + d.categoria);
        lblLoja.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblLoja.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        info.add(lblLoja);

        info.add(Box.createVerticalStrut(4));

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rodape.setOpaque(false);
        rodape.add(criarIconeLinha(FontAwesomeSolid.CLOCK, d.horario));
        rodape.add(criarIconeLinha(FontAwesomeSolid.MAP_MARKER_ALT, d.endereco));
        info.add(rodape);

        card.add(info, BorderLayout.CENTER);

        // Preços + badge + botão (direita)
        JPanel dir = new JPanel();
        dir.setOpaque(false);
        dir.setLayout(new BoxLayout(dir, BoxLayout.Y_AXIS));
        dir.setBorder(BorderFactory.createEmptyBorder(10, 4, 10, 16));
        dir.setPreferredSize(new Dimension(150, 0));

        // Badge desconto
        JLabel badge = criarBadge(d.desconto, d.corIcone);
        badge.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(badge);
        dir.add(Box.createVerticalStrut(4));

        // Preço antigo riscado
        JLabel lblAntigo = new JLabel("<html><strike>" + d.precoAntigo + "</strike></html>");
        lblAntigo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblAntigo.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        lblAntigo.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(lblAntigo);

        // Preço atual
        JLabel lblAtual = new JLabel(d.precoAtual);
        lblAtual.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblAtual.setForeground(EstiloReAlimenta.VERDE_PRINCIPAL);
        lblAtual.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(lblAtual);

        dir.add(Box.createVerticalGlue());

        JButton btnVer = EstiloReAlimenta.criarBotaoPrimario("Ver detalhes", e -> {});
        btnVer.setMaximumSize(new Dimension(130, 34));
        btnVer.setPreferredSize(new Dimension(130, 34));
        btnVer.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(btnVer);

        card.add(dir, BorderLayout.EAST);
        return card;
    }

    // CARDS DOAÇÕES
    private static class DadosDoacao {
        String nome;
        String loja;
        String quantidade;
        String horario;
        String endereco;
        Color cor;

        public DadosDoacao(String nome, String loja, String quantidade, String horario, String endereco, Color cor) {
            this.nome = nome;
            this.loja = loja;
            this.quantidade = quantidade;
            this.horario = horario;
            this.endereco = endereco;
            this.cor = cor;
        }
    }

    private JPanel criarCardsDoacoes() {
        List<DadosDoacao> lista = Arrays.asList(
                new DadosDoacao("Bananas Maduras", "Hortifruti do Zé", "~2 kg disponíveis",
                        "até 18h00", "Feira Central, Banca 12", new Color(0xF1, 0xC4, 0x0F)),
                new DadosDoacao("Pães Variados", "Padaria Pão de Ouro", "~30 unidades",
                        "até 17h30", "Rua do Pão, 77", new Color(0xE6, 0x7E, 0x22)),
                new DadosDoacao("Saladas Diversas", "Restaurante NaturalVida", "~1,5 kg",
                        "até 19h00", "Av. Saúde, 200", new Color(0x27, 0xAE, 0x60))
        );

        JPanel painel = new JPanel();
        painel.setOpaque(false);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        for (DadosDoacao d : lista) {
            painel.add(criarCardDoacaoHorizontal(d));
            painel.add(Box.createVerticalStrut(12));
        }
        return painel;
    }

    private JPanel criarCardDoacaoHorizontal(DadosDoacao d) {
        RoundedPanel card = new RoundedPanel(12, EstiloReAlimenta.BRANCO);
        card.setLayout(new BorderLayout());
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Faixa colorida
        JPanel faixa = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(d.cor);
                g2.fillRoundRect(0, 0, getWidth() + 20, getHeight(), 12, 12);
                g2.dispose();
            }
        };
        faixa.setPreferredSize(new Dimension(72, 0));
        faixa.setLayout(new GridBagLayout());
        faixa.add(EstiloReAlimenta.criarIcone(FontAwesomeSolid.GIFT, 24, Color.WHITE));
        card.add(faixa, BorderLayout.WEST);

        // Info
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 12));

        JLabel lblNome = new JLabel(d.nome);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNome.setForeground(EstiloReAlimenta.TEXTO);
        info.add(lblNome);

        JLabel lblLoja = new JLabel(d.loja + "  ·  " + d.quantidade);
        lblLoja.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblLoja.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        info.add(lblLoja);

        info.add(Box.createVerticalStrut(4));
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rodape.setOpaque(false);
        rodape.add(criarIconeLinha(FontAwesomeSolid.CLOCK, d.horario));
        rodape.add(criarIconeLinha(FontAwesomeSolid.MAP_MARKER_ALT, d.endereco));
        info.add(rodape);

        card.add(info, BorderLayout.CENTER);

        // Botão solicitar
        JPanel dir = new JPanel(new GridBagLayout());
        dir.setOpaque(false);
        dir.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 16));
        dir.setPreferredSize(new Dimension(140, 0));
        JButton btnSol = EstiloReAlimenta.criarBotaoPrimario("Solicitar", e -> {});
        btnSol.setMaximumSize(new Dimension(120, 34));
        btnSol.setPreferredSize(new Dimension(120, 34));
        dir.add(btnSol);
        card.add(dir, BorderLayout.EAST);

        return card;
    }

    // ── HELPERS ──────────────────────────────────────────────────
    private JLabel criarBadge(String texto, Color cor) {
        JLabel badge = new JLabel(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(cor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setOpaque(false);
        badge.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        return badge;
    }

    private JPanel criarIconeLinha(FontAwesomeSolid icone, String texto) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        p.setOpaque(false);
        p.add(EstiloReAlimenta.criarIcone(icone, 11, EstiloReAlimenta.TEXTO_SUAVE));
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        p.add(lbl);
        return p;
    }

    // NAVEGAÇÃO
    private void navegarDashboard() {dispose();new TelaDashboardConsumidor(nomeUsuario);}
    private void navegarPromocoes() {dispose();new TelaPromocao(nomeUsuario);}
    private void navegarDoacoes() {dispose();new TelaDoacoes(nomeUsuario);}
    private void navegarSolicitacoes() {dispose();new TelaMinhasSolicitacoes(nomeUsuario);}
    private void navegarNotificacoes() {dispose();new TelaNotificacoes(nomeUsuario);}

    private void confirmarSaida() {
        int op = JOptionPane.showConfirmDialog(this,
                "Deseja realmente sair do ReAlimenta?",
                "Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (op == JOptionPane.YES_OPTION) {
            dispose();
            new TelaLogin();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new TelaDashboardConsumidor("Keven");}
        });
       }

}