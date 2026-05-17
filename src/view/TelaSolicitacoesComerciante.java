package view;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class TelaSolicitacoesComerciante extends JFrame {
    // Constantes de status
    private static final String STATUS_PENDENTE = "Pendente";
    private static final String STATUS_ACEITA   = "Aceita";
    private static final String STATUS_RETIRADA = "Retirada";
    private static final String STATUS_RECUSADA = "Recusada";
    private static final String[] OPCOES_FILTRO = {
            "Todos", STATUS_PENDENTE, STATUS_ACEITA, STATUS_RETIRADA, STATUS_RECUSADA
    };

    // Estado da tela
    private final List<Solicitacao> listaSolicitacoes = new ArrayList<Solicitacao>();
    private final List<Solicitacao> listaFiltrada     = new ArrayList<Solicitacao>();
    private JComboBox<String> campoFiltro;
    private JPanel            painelLista;

    // Referências aos contadores dos cards de resumo
    private JLabel lblQtdPendentes;
    private JLabel lblQtdAceitas;
    private JLabel lblQtdRetiradas;
    private JLabel lblQtdRecusadas;

    // Construtor
    public TelaSolicitacoesComerciante() {
        popularMocks();
        listaFiltrada.addAll(listaSolicitacoes);
        configurarJanela();
        construirLayout();
        setVisible(true);
    }

    // Configuração do JFrame
    private void configurarJanela() {
        setTitle("ReAlimenta | Solicitações de Retirada");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setSize(1280, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(EstiloReAlimenta.FUNDO);
    }

    // Layout principal
    private void construirLayout() {
        setLayout(new BorderLayout());
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
                g2.setPaint(new GradientPaint(0, 0, EstiloReAlimenta.VERDE_PRINCIPAL, 0, getHeight(), new Color(0x0A, 0x3D, 0x26)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(24, 12, 24, 12));

        JLabel logo = EstiloReAlimenta.criarLogoMaca();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(8));

        JLabel nomeSistema = new JLabel("ReAlimenta");
        nomeSistema.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nomeSistema.setForeground(EstiloReAlimenta.BRANCO);
        nomeSistema.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(nomeSistema);

        JLabel perfil = new JLabel("Comerciante");
        perfil.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        perfil.setForeground(new Color(255, 255, 255, 180));
        perfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(perfil);
        sidebar.add(Box.createVerticalStrut(32));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 40));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(16));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.HOME, "Dashboard", this::navegarDashboard));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.APPLE_ALT, "Alimentos", this::navegarAlimentos));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.TAG, "Promoções", this::navegarPromocoes));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.HAND_HOLDING_HEART, "Doações", this::navegarDoacoes));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.INBOX, "Solicitações"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.BELL, "Notificações", this::navegarNotificacoes));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(Box.createVerticalGlue());

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(255, 255, 255, 40));
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sidebar.add(sep2);
        sidebar.add(Box.createVerticalStrut(12));

        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.SIGN_OUT_ALT, "Sair", this::sair));
        return sidebar;
    }
    // Item de menu com destaque de fundo — tela atual.
    // Item ativo: reutiliza EstiloReAlimenta.criarItemMenu() + destaque de borda
    private JPanel criarItemMenuAtivo(FontAwesomeSolid icone, String texto) {
        JPanel item = EstiloReAlimenta.criarItemMenu(icone, texto, null);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1, true),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)));
        return item;
    }

    // CONTEÚDO CENTRAL
    private JScrollPane criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(EstiloReAlimenta.FUNDO);
        conteudo.setBorder(new EmptyBorder(32, 32, 32, 32));

        conteudo.add(criarCabecalhoPagina());
        conteudo.add(Box.createVerticalStrut(24));
        conteudo.add(criarLinhaResumo());
        conteudo.add(Box.createVerticalStrut(24));
        conteudo.add(criarBarraFiltro());
        conteudo.add(Box.createVerticalStrut(12));
        conteudo.add(criarCabecalhoColunas());
        conteudo.add(Box.createVerticalStrut(6));

        painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
        painelLista.setOpaque(false);
        renderizarLista();
        conteudo.add(painelLista);

        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(EstiloReAlimenta.FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }
    // Cabeçalho da página
    private JPanel criarCabecalhoPagina() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(EstiloReAlimenta.criarTitulo("Solicitações de Retirada"));
        p.add(Box.createVerticalStrut(4));
        p.add(EstiloReAlimenta.criarSubtitulo("Gerencie as solicitações feitas pelos consumidores para suas doações."));
        return p;
    }
    // Cards de resumo com contadores
    private JPanel criarLinhaResumo() {
        JPanel linha = new JPanel(new GridLayout(1, 4, 16, 0));
        linha.setOpaque(false);
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Arrays auxiliares para capturar as referências de JLabel
        final JLabel[] refs = new JLabel[4];

        linha.add(montarCardResumo("Pendentes", contarPorStatus(STATUS_PENDENTE), "Aguardando resposta", FontAwesomeSolid.CLOCK, new Color(0xD9, 0x77, 0x07), refs, 0));
        linha.add(montarCardResumo("Aceitas", contarPorStatus(STATUS_ACEITA), "Confirmadas pelo comerciante", FontAwesomeSolid.CHECK_CIRCLE, new Color(0x16, 0xA3, 0x4A), refs, 1));
        linha.add(montarCardResumo("Retiradas", contarPorStatus(STATUS_RETIRADA), "Concluídas com sucesso", FontAwesomeSolid.BOX_OPEN, new Color(0x25, 0x63, 0xEB), refs, 2));
        linha.add(montarCardResumo("Recusadas", contarPorStatus(STATUS_RECUSADA), "Não atendidas", FontAwesomeSolid.TIMES_CIRCLE, new Color(0xDC, 0x26, 0x26), refs, 3));
        lblQtdPendentes = refs[0];
        lblQtdAceitas   = refs[1];
        lblQtdRetiradas = refs[2];
        lblQtdRecusadas = refs[3];

        return linha;
    }

    // Monta um card de resumo e armazena o JLabel do contador em refs[idx].
    private JPanel montarCardResumo(String titulo, int quantidade, String descricao, FontAwesomeSolid icone, Color cor, JLabel[] refs, int idx) {
        RoundedPanel card = new RoundedPanel(14, EstiloReAlimenta.BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(18, 20, 14, 20));

        // Topo: título (esq) + ícone (dir)
        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitulo.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        topo.add(lblTitulo, BorderLayout.WEST);
        topo.add(EstiloReAlimenta.criarIcone(icone, 18, cor), BorderLayout.EAST);
        card.add(topo);
        card.add(Box.createVerticalStrut(8));

        // Contador grande
        JLabel lblQtd = new JLabel(String.valueOf(quantidade));
        lblQtd.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblQtd.setForeground(EstiloReAlimenta.TEXTO);
        lblQtd.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblQtd);

        // Descrição pequena
        JLabel lblDesc = new JLabel(descricao);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDesc.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblDesc);
        card.add(Box.createVerticalStrut(10));

        // Barra colorida no rodapé
        JPanel barra = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(cor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                g2.dispose();
            }
        };
        barra.setOpaque(false);
        barra.setMaximumSize(new Dimension(Integer.MAX_VALUE, 5));
        barra.setPreferredSize(new Dimension(0, 5));
        barra.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(barra);

        refs[idx] = lblQtd;
        return card;
    }

    // Barra de filtro por status
    private JPanel criarBarraFiltro() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        barra.setOpaque(false);
        barra.setAlignmentX(Component.LEFT_ALIGNMENT);
        barra.add(EstiloReAlimenta.criarLabel("Filtrar por status:"));

        campoFiltro = new JComboBox<String>(OPCOES_FILTRO);
        campoFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campoFiltro.setBackground(EstiloReAlimenta.BRANCO);
        campoFiltro.setPreferredSize(new Dimension(190, 38));
        // TODO: adicionar filtros por data e nome de alimento futuramente
        campoFiltro.addActionListener(e -> filtrarLista());
        barra.add(campoFiltro);

        return barra;
    }

    // Cabeçalho das colunas
    private JPanel criarCabecalhoColunas() {
        JPanel header = new JPanel(new GridLayout(1, 6, 8, 0));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 16, 4, 16));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] colunas = { "Consumidor", "Alimento", "Qtd.", "Data / Horário", "Status", "Ações" };
        for (String col : colunas) {
            JLabel lbl = new JLabel(col);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
            header.add(lbl);
        }
        return header;
    }

    // RENDERIZAÇÃO DA LISTA
    private void renderizarLista() {
        painelLista.removeAll();

        if (listaFiltrada.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma solicitação encontrada para o filtro selecionado.");
            vazio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            vazio.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
            vazio.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelLista.add(Box.createVerticalStrut(32));
            painelLista.add(vazio);
        } else {
            for (Solicitacao s : listaFiltrada) {
                painelLista.add(criarLinhaSolicitacao(s));
                painelLista.add(Box.createVerticalStrut(8));
            }
        }

        painelLista.revalidate();
        painelLista.repaint();
    }

    // Linha / card de uma solicitação
    private JPanel criarLinhaSolicitacao(Solicitacao s) {
        Color fundoCard = STATUS_PENDENTE.equals(s.status)
                ? new Color(0xFF, 0xFD, 0xF0)
                : EstiloReAlimenta.BRANCO;
        RoundedPanel linha = new RoundedPanel(12, fundoCard);
        linha.setLayout(new GridLayout(1, 6, 8, 0));
        linha.setBorder(new EmptyBorder(14, 16, 14, 16));
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 78));
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha.add(criarCelulaConsumidor(s));
        linha.add(criarCelulaAlimento(s));

        // Quantidade
        JLabel lblQtd = new JLabel(s.quantidade + " un.");
        lblQtd.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblQtd.setForeground(EstiloReAlimenta.TEXTO);
        linha.add(lblQtd);
        linha.add(criarCelulaData(s));

        // Badge status
        JPanel celStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        celStatus.setOpaque(false);
        celStatus.add(criarBadgeStatus(s.status));
        linha.add(celStatus);
        linha.add(criarCelulaAcoes(s));

        return linha;
    }

    private JPanel criarCelulaConsumidor(Solicitacao s) {
        JPanel cel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        cel.setOpaque(false);
        cel.add(criarAvatar(s.nomeConsumidor));

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel lblNome = new JLabel(s.nomeConsumidor);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNome.setForeground(EstiloReAlimenta.TEXTO);

        JLabel lblTel = new JLabel(s.telefone);
        lblTel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblTel.setForeground(EstiloReAlimenta.TEXTO_SUAVE);

        info.add(lblNome);
        info.add(lblTel);
        cel.add(info);
        return cel;
    }

    private JPanel criarCelulaAlimento(Solicitacao s) {
        JPanel cel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        cel.setOpaque(false);
        cel.add(EstiloReAlimenta.criarIcone(FontAwesomeSolid.APPLE_ALT, 16, EstiloReAlimenta.VERDE_PRINCIPAL));
        JLabel lbl = new JLabel(s.nomeAlimento);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(EstiloReAlimenta.TEXTO);
        cel.add(lbl);
        return cel;
    }

    private JPanel criarCelulaData(Solicitacao s) {
        JPanel cel = new JPanel();
        cel.setOpaque(false);
        cel.setLayout(new BoxLayout(cel, BoxLayout.Y_AXIS));

        JLabel lblData = new JLabel(s.dataSolicitacao);
        lblData.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblData.setForeground(EstiloReAlimenta.TEXTO);

        JLabel lblHorario = new JLabel(s.horarioRetirada);
        lblHorario.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblHorario.setForeground(EstiloReAlimenta.TEXTO_SUAVE);

        cel.add(lblData);
        cel.add(lblHorario);
        return cel;
    }

    // Avatar circular com inicial do consumidor
    private JLabel criarAvatar(String nomeConsumidor) {
        final String inicial = (nomeConsumidor != null && !nomeConsumidor.isEmpty()) ? String.valueOf(nomeConsumidor.charAt(0)).toUpperCase() : "?";

        JLabel avatar = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloReAlimenta.VERDE_CLARO);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(EstiloReAlimenta.VERDE_PRINCIPAL);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 15));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(inicial, (getWidth()  - fm.stringWidth(inicial)) / 2, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        Dimension d = new Dimension(36, 36);
        avatar.setPreferredSize(d);
        avatar.setMinimumSize(d);
        avatar.setMaximumSize(d);
        return avatar;
    }

    // Badge colorido de status
    private JLabel criarBadgeStatus(String status) {
        JLabel badge = new JLabel(status);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(3, 10, 3, 10));

        if      (STATUS_PENDENTE.equals(status)) badge.setBackground(new Color(0xD9, 0x77, 0x07));
        else if (STATUS_ACEITA  .equals(status)) badge.setBackground(new Color(0x16, 0xA3, 0x4A));
        else if (STATUS_RETIRADA.equals(status)) badge.setBackground(new Color(0x25, 0x63, 0xEB));
        else if (STATUS_RECUSADA.equals(status)) badge.setBackground(new Color(0x6B, 0x72, 0x80));
        else                                     badge.setBackground(EstiloReAlimenta.TEXTO_SUAVE);

        return badge;
    }

    // Célula de ações — condicional por status
    private JPanel criarCelulaAcoes(Solicitacao s) {
        JPanel cel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
        cel.setOpaque(false);

        if (STATUS_PENDENTE.equals(s.status)) {
            cel.add(criarBotaoAcao("Aceitar",  FontAwesomeSolid.CHECK,
                    new Color(0x16, 0xA3, 0x4A), () -> aceitarSolicitacao(s)));
            cel.add(criarBotaoAcao("Recusar",  FontAwesomeSolid.TIMES,
                    new Color(0xDC, 0x26, 0x26), () -> recusarSolicitacao(s)));

        } else if (STATUS_ACEITA.equals(s.status)) {
            cel.add(criarBotaoAcao("Confirmar Retirada", FontAwesomeSolid.BOX_OPEN,
                    new Color(0x25, 0x63, 0xEB), () -> marcarRetirada(s)));

        } else if (STATUS_RETIRADA.equals(s.status)) {
            cel.add(criarRotuloConclusao("Retirada concluída",
                    FontAwesomeSolid.CHECK_DOUBLE, new Color(0x25, 0x63, 0xEB)));

        } else if (STATUS_RECUSADA.equals(s.status)) {
            cel.add(criarRotuloConclusao("Solicitação recusada",
                    FontAwesomeSolid.BAN, EstiloReAlimenta.TEXTO_SUAVE));
        }

        return cel;
    }

    /** Botão de ação com borda colorida e hover translúcido. */
    private JButton criarBotaoAcao(String texto, FontAwesomeSolid icone, Color cor, Runnable acao) {
        JButton btn = new JButton() {
            boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hover = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                int alpha = hover ? 55 : 22;
                g2.setColor(new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), alpha));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(cor);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setIcon(FontIcon.of(icone, 12, cor));
        btn.setText("  " + texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setForeground(cor);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(128, 30));
        btn.addActionListener(e -> acao.run());
        return btn;
    }

    // Painel com ícone + texto para status terminal (Retirada / Recusada).
    private JPanel criarRotuloConclusao(String texto, FontAwesomeSolid icone, Color cor) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.setOpaque(false);
        p.add(EstiloReAlimenta.criarIcone(icone, 14, cor));
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(cor);
        p.add(lbl);
        return p;
    }

    // AÇÕES — preparadas para integração com DAO
    private void aceitarSolicitacao(Solicitacao s) {
        int res = JOptionPane.showConfirmDialog(this,
                "<html>Aceitar a solicitação de <b>" + s.nomeConsumidor
                        + "</b> para <b>" + s.nomeAlimento + "</b>?</html>",
                "Confirmar aceitação",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res != JOptionPane.YES_OPTION) return;
        s.status = STATUS_ACEITA;
        // TODO: integrar SolicitacaoDAO
        // TODO: atualizar status no banco
        // SolicitacaoDAO.atualizarStatus(s.id, STATUS_ACEITA);

        // TODO: enviar notificação ao consumidor
        // NotificacaoService.notificarConsumidor(s.idConsumidor,
        //         "Sua solicitação para '" + s.nomeAlimento + "' foi aceita!");
        // TODO: atualizar tabela automaticamente via pool de dados
        atualizarContadores();
        filtrarLista();
    }

    private void recusarSolicitacao(Solicitacao s) {
        int res = JOptionPane.showConfirmDialog(this,
                "<html>Recusar a solicitação de <b>" + s.nomeConsumidor
                        + "</b> para <b>" + s.nomeAlimento + "</b>?</html>",
                "Confirmar recusa",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (res != JOptionPane.YES_OPTION) return;
        s.status = STATUS_RECUSADA;

        // TODO: integrar SolicitacaoDAO
        // TODO: atualizar status no banco
        // SolicitacaoDAO.atualizarStatus(s.id, STATUS_RECUSADA);
        // TODO: enviar notificação ao consumidor
        // NotificacaoService.notificarConsumidor(s.idConsumidor,
        //         "Infelizmente sua solicitação para '" + s.nomeAlimento + "' não pôde ser atendida.");
        // TODO: atualizar tabela automaticamente via pool de dados
        atualizarContadores();
        filtrarLista();
    }

    private void marcarRetirada(Solicitacao s) {
        int res = JOptionPane.showConfirmDialog(this,
                "<html>Confirmar que <b>" + s.nomeConsumidor
                        + "</b> retirou <b>" + s.nomeAlimento + "</b>?</html>",
                "Confirmar retirada",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res != JOptionPane.YES_OPTION) return;
        s.status = STATUS_RETIRADA;

        // TODO: integrar SolicitacaoDAO
        // TODO: atualizar status no banco
        // SolicitacaoDAO.atualizarStatus(s.id, STATUS_RETIRADA);
        // TODO: enviar notificação ao consumidor
        // NotificacaoService.notificarConsumidor(s.idConsumidor,
        //         "Retirada de '" + s.nomeAlimento + "' confirmada. Obrigado!");
        // TODO: atualizar tabela automaticamente via pool de dados

        atualizarContadores();
        filtrarLista();
    }

    // FILTRAGEM E CONTADORES
    private void filtrarLista() {
        String filtro = (String) campoFiltro.getSelectedItem();
        listaFiltrada.clear();
        for (Solicitacao s : listaSolicitacoes) {
            if ("Todos".equals(filtro) || filtro.equals(s.status)) {
                listaFiltrada.add(s);
            }
        }
        renderizarLista();
    }
    private void atualizarContadores() {
        if (lblQtdPendentes != null)
            lblQtdPendentes.setText(String.valueOf(contarPorStatus(STATUS_PENDENTE)));
        if (lblQtdAceitas != null)
            lblQtdAceitas  .setText(String.valueOf(contarPorStatus(STATUS_ACEITA)));
        if (lblQtdRetiradas != null)
            lblQtdRetiradas.setText(String.valueOf(contarPorStatus(STATUS_RETIRADA)));
        if (lblQtdRecusadas != null)
            lblQtdRecusadas.setText(String.valueOf(contarPorStatus(STATUS_RECUSADA)));
    }
    private int contarPorStatus(String status) {
        int count = 0;
        for (Solicitacao s : listaSolicitacoes) {
            if (status.equals(s.status)) count++;
        }
        return count;
    }

    // MOCK DE DADOS
    // Substituir por: SolicitacaoDAO.listarPorComerciante(idComerciante)
    private void popularMocks() {
        // TODO: carregar solicitações do banco
        // TODO: integrar SolicitacaoDAO

        listaSolicitacoes.add(new Solicitacao(1, 101, 201,
                "João da Silva",   "(94) 99101-2233",
                "Marmita de Feijoada", 2,
                "17/05/2025", "12:00 - 13:00", STATUS_PENDENTE, ""));

        listaSolicitacoes.add(new Solicitacao(2, 102, 202,
                "Maria Oliveira",  "(94) 98877-5544",
                "Salada Completa",     1,
                "17/05/2025", "15:00 - 16:00", STATUS_PENDENTE, ""));

        listaSolicitacoes.add(new Solicitacao(3, 103, 203,
                "Carlos Mendes",   "(94) 99234-6677",
                "Sopa de Legumes",     3,
                "16/05/2025", "11:00 - 12:00", STATUS_ACEITA, ""));

        listaSolicitacoes.add(new Solicitacao(4, 104, 204,
                "Ana Costa",       "(94) 99312-8899",
                "Frango Assado",       1,
                "15/05/2025", "09:00 - 10:00", STATUS_RETIRADA, ""));

        listaSolicitacoes.add(new Solicitacao(5, 105, 205,
                "Pedro Souza",     "(94) 99456-1122",
                "Arroz e Feijão",      4,
                "14/05/2025", "17:00 - 18:00", STATUS_RECUSADA, ""));

        listaSolicitacoes.add(new Solicitacao(6, 106, 201,
                "Lúcia Ferreira",  "(94) 99567-3344",
                "Marmita de Feijoada", 1,
                "17/05/2025", "13:00 - 14:00", STATUS_PENDENTE, ""));

        listaSolicitacoes.add(new Solicitacao(7, 107, 206,
                "Roberto Lima",    "(94) 99678-5566",
                "Pão Artesanal",       6,
                "16/05/2025", "08:00 - 09:00", STATUS_ACEITA, ""));
    }

    // MODELO INTERNO
    // Mover para model/Solicitacao.java futuramente.
    //
    // Tabela MySQL futura: solicitacoes
    //   id            INT AUTO_INCREMENT PK
    //   id_consumidor INT FK → consumidores.id
    //   id_doacao     INT FK → doacoes.id
    //   status        ENUM('Pendente','Aceita','Retirada','Recusada')
    //   data_solicitacao DATE
    //   horario_retirada VARCHAR(20)
    //   observacoes   TEXT

    public static class Solicitacao {
        public int    id;
        public int    idConsumidor;
        public int    idDoacao;
        public String nomeConsumidor;   // virá de JOIN com consumidores
        public String telefone;         // idem
        public String nomeAlimento;     // virá de JOIN com doacoes + alimentos
        public int    quantidade;
        public String dataSolicitacao;  // futuro LocalDate
        public String horarioRetirada;
        public String status;
        public String observacoes;

        public Solicitacao(int id, int idConsumidor, int idDoacao, String nomeConsumidor, String telefone, String nomeAlimento, int quantidade, String dataSolicitacao, String horarioRetirada, String status, String observacoes) {
            this.id               = id;
            this.idConsumidor     = idConsumidor;
            this.idDoacao         = idDoacao;
            this.nomeConsumidor   = nomeConsumidor;
            this.telefone         = telefone;
            this.nomeAlimento     = nomeAlimento;
            this.quantidade       = quantidade;
            this.dataSolicitacao  = dataSolicitacao;
            this.horarioRetirada  = horarioRetirada;
            this.status           = status;
            this.observacoes      = observacoes;
        }
    }
    // Navegação
    private void navegarDashboard()    { dispose(); new TelaDashboardComerciante(); }
    private void navegarAlimentos()    { dispose(); new TelaCadastroAlimento(); }
    private void navegarPromocoes()    { dispose(); new TelaMinhasPromocoes(); }
    private void navegarDoacoes()      { dispose(); new TelaDoacoesComerciante(); }
    private void navegarSolicitacoes() { dispose(); new TelaSolicitacoesComerciante(); }
    private void navegarNotificacoes() { dispose(); new TelaNotificacoesComerciante(); }
    private void sair()                { dispose(); new TelaLogin(); }

}