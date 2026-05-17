package view;
import java.util.Arrays;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TelaPromocao extends JFrame {
    private final String nomeUsuario;

    // Dados mockados — futuramente substituir por List<Promocao> via service/DAO
    private final List<DadosPromocao> lista = Arrays.asList(
            new DadosPromocao("Lasanha à Bolonhesa",   "Restaurante da Mara",     "Massas",    "40% OFF", "R$ 28,00", "R$ 16,80", "até 20h00", "Rua das Flores, 45",    new Color(0xE7, 0x4C, 0x3C)),
            new DadosPromocao("Pizza Marguerita",       "Pizzaria Bela Napoli",    "Pizzas",    "30% OFF", "R$ 45,00", "R$ 31,50", "até 21h30", "Av. Central, 120",      new Color(0xE6, 0x7E, 0x22)),
            new DadosPromocao("Pastéis Assortidos",     "Pastelaria Crocante",     "Salgados",  "35% OFF", "R$ 20,00", "R$ 13,00", "até 19h00", "Rua do Mercado, 33",    new Color(0xF3, 0x9C, 0x12)),
            new DadosPromocao("Bolo de Cenoura",        "Confeitaria Doce Arte",   "Bolos",     "50% OFF", "R$ 18,00", "R$  9,00", "até 18h00", "Rua do Comércio, 8",   new Color(0x8E, 0x44, 0xAD)),
            new DadosPromocao("Salada Completa",        "Restaurante NaturalVida", "Saladas",   "25% OFF", "R$ 22,00", "R$ 16,50", "até 17h30", "Av. Saúde, 200",        new Color(0x27, 0xAE, 0x60))
    );

    // Referência ao painel de listagem para atualização dinâmica (filtros futuros)
    private JPanel painelListagem;

    public TelaPromocao(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    // CONFIGURAÇÃO DA JANELA
    private void configurarJanela() {
        setTitle("ReAlimenta | Promoções");
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

    // SIDEBAR — mesma estrutura da Dashboard
    private JPanel criarSidebar() {
        JPanel sidebar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(
                        0, 0, EstiloReAlimenta.VERDE_PRINCIPAL,
                        0, getHeight(), new Color(0x0D, 0x4A, 0x2C)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(28, 16, 28, 16));

        sidebar.add(EstiloReAlimenta.criarLogoMaca());
        sidebar.add(Box.createVerticalStrut(8));

        JLabel lblNome = new JLabel("ReAlimenta");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblNome.setForeground(EstiloReAlimenta.BRANCO);
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblNome);
        sidebar.add(Box.createVerticalStrut(4));

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

        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.HOME, "Início", new Runnable() {
            @Override
            public void run() {navegarDashboard();}})
        );
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.TAG, "Promoções"));
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
            protected void paintComponent(Graphics g) {Graphics2D g2 = (Graphics2D) g.create();g2.setColor(new Color(255,255,255,45));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
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

        conteudo.add(criarHeaderPromocoes());
        conteudo.add(Box.createVerticalStrut(20));
        conteudo.add(criarPainelFiltros());
        conteudo.add(Box.createVerticalStrut(20));

        // Painel de listagem — referenciado para futura atualização via filtros
        painelListagem = criarListagemPromocoes(lista);
        conteudo.add(painelListagem);
        conteudo.add(Box.createVerticalStrut(24));

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(EstiloReAlimenta.FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        return scroll;
    }

    // HEADER
    private JPanel criarHeaderPromocoes() {
        JPanel header = new JPanel(new BorderLayout(0, 4));
        header.setOpaque(false);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JPanel esq = new JPanel();
        esq.setOpaque(false);
        esq.setLayout(new BoxLayout(esq, BoxLayout.Y_AXIS));

        JPanel titLinha = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        titLinha.setOpaque(false);
        titLinha.add(EstiloReAlimenta.criarIcone(FontAwesomeSolid.TAG, 20, EstiloReAlimenta.VERDE_PRINCIPAL));
        JLabel lblTit = new JLabel("Promoções");
        lblTit.setFont(EstiloReAlimenta.FONTE_TITULO);
        lblTit.setForeground(EstiloReAlimenta.TEXTO);
        titLinha.add(lblTit);
        esq.add(titLinha);

        JLabel lblSub = new JLabel("Aproveite alimentos com descontos imperdíveis e ajude a combater o desperdício.");
        lblSub.setFont(EstiloReAlimenta.FONTE_SUBTITULO);
        lblSub.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        esq.add(lblSub);

        header.add(esq, BorderLayout.WEST);

        // Contador de resultados (direita)
        JLabel lblQtd = new JLabel(lista.size() + " promoções encontradas");
        lblQtd.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblQtd.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        header.add(lblQtd, BorderLayout.EAST);

        return header;
    }

    // PAINEL DE FILTROS
    private JPanel criarPainelFiltros() {
        RoundedPanel painel = new RoundedPanel(10, EstiloReAlimenta.BRANCO);
        painel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));

        // Campo pesquisa
        JPanel wrapperPesquisa = criarWrapperFiltro();
        wrapperPesquisa.add(EstiloReAlimenta.criarIcone(FontAwesomeSolid.SEARCH, 13, EstiloReAlimenta.TEXTO_SUAVE), BorderLayout.WEST);
        JTextField campoPesquisa = new JTextField();
        campoPesquisa.setFont(EstiloReAlimenta.FONTE_CAMPO);
        campoPesquisa.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        campoPesquisa.setOpaque(false);
        campoPesquisa.setForeground(new Color(0xA0, 0xAE, 0xC0));
        campoPesquisa.setText("Buscar promoção...");
        campoPesquisa.setColumns(16);
        campoPesquisa.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (campoPesquisa.getText().equals("Buscar promoção...")) {
                    campoPesquisa.setText("");
                    campoPesquisa.setForeground(EstiloReAlimenta.TEXTO);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (campoPesquisa.getText().isEmpty()) {
                    campoPesquisa.setText("Buscar promoção...");
                    campoPesquisa.setForeground(new Color(0xA0, 0xAE, 0xC0));
                }
            }
        });
        wrapperPesquisa.add(campoPesquisa, BorderLayout.CENTER);
        painel.add(wrapperPesquisa);

        // Combo categoria
        painel.add(criarComboFiltro("Categoria", new String[]{"Todas", "Massas", "Pizzas", "Salgados", "Bolos", "Saladas"}));

        // Combo ordenação
        painel.add(criarComboFiltro("Ordenar por", new String[]{"Maior desconto", "Menor preço", "Mais recente"}));

        // Botão filtrar
        JButton btnFiltrar = new JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloReAlimenta.VERDE_CLARO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnFiltrar.setLayout(new FlowLayout(FlowLayout.CENTER, 6, 0));
        FontIcon icoFiltro = FontIcon.of(FontAwesomeSolid.FILTER, 13);
        icoFiltro.setIconColor(EstiloReAlimenta.VERDE_PRINCIPAL);
        btnFiltrar.setIcon(icoFiltro);
        btnFiltrar.setText("Filtros");
        btnFiltrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnFiltrar.setForeground(EstiloReAlimenta.VERDE_PRINCIPAL);
        btnFiltrar.setBorderPainted(false);
        btnFiltrar.setContentAreaFilled(false);
        btnFiltrar.setFocusPainted(false);
        btnFiltrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnFiltrar.setPreferredSize(new Dimension(100, 36));
        painel.add(btnFiltrar);

        return painel;
    }

    private JPanel criarWrapperFiltro() {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloReAlimenta.FUNDO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(EstiloReAlimenta.BORDA);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        p.setPreferredSize(new Dimension(230, 36));
        return p;
    }

    private JPanel criarComboFiltro(String label, String[] opcoes) {
        JPanel p = new JPanel(new BorderLayout(6, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloReAlimenta.FUNDO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(EstiloReAlimenta.BORDA);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 6));
        p.setPreferredSize(new Dimension(170, 36));

        JLabel lbl = new JLabel(label + ":");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lbl.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        p.add(lbl, BorderLayout.WEST);

        JComboBox<String> combo = new JComboBox<>(opcoes);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setForeground(EstiloReAlimenta.TEXTO);
        combo.setBorder(BorderFactory.createEmptyBorder());
        combo.setOpaque(false);
        p.add(combo, BorderLayout.CENTER);

        return p;
    }

    // LISTAGEM DE PROMOÇÕES
    private JPanel criarListagemPromocoes(List<DadosPromocao> lista) {
        JPanel painel = new JPanel();
        painel.setOpaque(false);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        if (lista.isEmpty()) {
            JLabel lblVazio = new JLabel("Nenhuma promoção encontrada.");
            lblVazio.setFont(EstiloReAlimenta.FONTE_SUBTITULO);
            lblVazio.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
            lblVazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            painel.add(Box.createVerticalStrut(40));
            painel.add(lblVazio);
            return painel;
        }

        for (DadosPromocao d : lista) {
            painel.add(criarCardPromocao(d));
            painel.add(Box.createVerticalStrut(14));
        }
        return painel;
    }

    private JPanel criarCardPromocao(DadosPromocao d) {
        RoundedPanel card = new RoundedPanel(12, EstiloReAlimenta.BRANCO) {
            boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hover = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                for (int i = 5; i > 0; i--) {
                    g2.setColor(new Color(0, 0, 0, hover ? 11 : 6));
                    g2.fillRoundRect(i, i, getWidth() - i * 2, getHeight() - i * 2, 12 + i, 12 + i);
                }
                g2.setColor(hover ? new Color(0xF8, 0xFF, 0xFA) : EstiloReAlimenta.BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setLayout(new BorderLayout(0, 0));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Faixa colorida lateral com ícone (substitui imagem)
        JPanel faixa = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(d.corIcone);
                g2.fillRoundRect(0, 0, getWidth() + 20, getHeight(), 12, 12);
                g2.dispose();
            }
        };
        faixa.setPreferredSize(new Dimension(80, 0));
        faixa.setLayout(new GridBagLayout());
        faixa.add(EstiloReAlimenta.criarIcone(FontAwesomeSolid.UTENSILS, 26, Color.WHITE));
        card.add(faixa, BorderLayout.WEST);

        // Informações centrais
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 10));

        // Linha: nome + badge
        JPanel linNome = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        linNome.setOpaque(false);
        JLabel lblNome = new JLabel(d.nome);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNome.setForeground(EstiloReAlimenta.TEXTO);
        linNome.add(lblNome);
        linNome.add(criarBadge(d.desconto, d.corIcone));
        info.add(linNome);

        JLabel lblLoja = new JLabel(d.loja + "  ·  " + d.categoria);
        lblLoja.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLoja.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        info.add(lblLoja);

        info.add(Box.createVerticalStrut(6));

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        rodape.setOpaque(false);
        rodape.add(criarIconeLinha(FontAwesomeSolid.CLOCK, d.horario));
        rodape.add(criarIconeLinha(FontAwesomeSolid.MAP_MARKER_ALT, d.endereco));
        info.add(rodape);

        card.add(info, BorderLayout.CENTER);

        // Painel direito: preços + botão
        JPanel dir = new JPanel();
        dir.setOpaque(false);
        dir.setLayout(new BoxLayout(dir, BoxLayout.Y_AXIS));
        dir.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 20));
        dir.setPreferredSize(new Dimension(160, 0));

        // Preço antigo riscado
        JLabel lblAntigo = new JLabel("<html><strike>" + d.precoAntigo + "</strike></html>");
        lblAntigo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAntigo.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        lblAntigo.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(lblAntigo);

        // Preço atual em destaque
        JLabel lblAtual = new JLabel(d.precoAtual);
        lblAtual.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblAtual.setForeground(EstiloReAlimenta.VERDE_PRINCIPAL);
        lblAtual.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(lblAtual);

        dir.add(Box.createVerticalGlue());

        JButton btnVer = EstiloReAlimenta.criarBotaoPrimario("Ver detalhes", e -> mostrarDetalhes(d));
        btnVer.setMaximumSize(new Dimension(140, 34));
        btnVer.setPreferredSize(new Dimension(140, 34));
        btnVer.setAlignmentX(Component.RIGHT_ALIGNMENT);
        dir.add(btnVer);

        card.add(dir, BorderLayout.EAST);
        return card;
    }

    // HELPERS
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

    // AÇÕES
    private void mostrarDetalhes(DadosPromocao d) {
        JOptionPane.showMessageDialog(this,
                "Promoção: " + d.nome
                        + "\nEstabelecimento: " + d.loja
                        + "\nCategoria: " + d.categoria
                        + "\nDesconto: " + d.desconto
                        + "\nDe: " + d.precoAntigo + "  →  Por: " + d.precoAtual
                        + "\nRetirada: " + d.horario
                        + "\nEndereço: " + d.endereco
                        + "\n\n(Reserva via backend — em desenvolvimento)",
                "Detalhes da Promoção", JOptionPane.INFORMATION_MESSAGE);
    }

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

    // RECORD — substitua futuramente por model/Promocao.java
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

        public DadosPromocao(String nome, String loja, String categoria, String desconto, String precoAntigo, String precoAtual, String horario, String endereco, Color corIcone){
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
}