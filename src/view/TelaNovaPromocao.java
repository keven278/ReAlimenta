package view;
import model.Comerciante;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class TelaNovaPromocao extends JFrame {
    // Mock de alimentos — substituir por AlimentoDAO.listarTodos()
    private static final String[] ALIMENTOS_MOCK = {
            "Selecionar alimento...",
            "Pão de Forma Integral",
            "Iogurte Natural",
            "Leite Integral 1L",
            "Maçã Gala",
            "Peito de Frango",
            "Arroz Parboilizado 5kg"
    };

    // Campos do formulário
    private JComboBox<String>    campoAlimento;
    private JTextField           campoDesconto;
    private JFormattedTextField  campoDataInicio;
    private JFormattedTextField  campoDataFim;
    private JTextArea            campoObservacoes;

    // Construtor
    private Comerciante comerciante;
    public TelaNovaPromocao(Comerciante comerciante) {
        this.comerciante = comerciante;
        configurarJanela();
        construirLayout();
        setVisible(true);
    }

    // Configuração do JFrame
    private void configurarJanela() {
        setTitle("ReAlimenta | Nova Promoção");
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

    // Sidebar — Promoções em destaque
    // Item ativo: reutiliza EstiloReAlimenta.criarItemMenu() + destaque de borda
    private JPanel criarItemMenuAtivo(FontAwesomeSolid icone, String texto) {
        JPanel item = EstiloReAlimenta.criarItemMenu(icone, texto, null);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1, true),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)));
        return item;
    }

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

        // Logo + nome
        JLabel logo = EstiloReAlimenta.criarLogoMaca();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nomeSistema = new JLabel("ReAlimenta");
        nomeSistema.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nomeSistema.setForeground(EstiloReAlimenta.BRANCO);
        nomeSistema.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel perfil = new JLabel("Comerciante");
        perfil.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        perfil.setForeground(new Color(255, 255, 255, 180));
        perfil.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(nomeSistema);
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
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.TAG, "Promoções"));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.HAND_HOLDING_HEART, "Doações", this::navegarDoacoes));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.INBOX, "Solicitações", this::navegarSolicitacoes));
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

    // Conteúdo central com scroll
    private JScrollPane criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(EstiloReAlimenta.FUNDO);
        conteudo.setBorder(new EmptyBorder(32, 32, 32, 32));

        // Cabeçalho da página
        conteudo.add(criarCabecalhoPagina());
        conteudo.add(Box.createVerticalStrut(24));

        // Mensagem informativa amarela
        conteudo.add(criarBannerInformativo());
        conteudo.add(Box.createVerticalStrut(24));

        // Card do formulário
        conteudo.add(criarCardFormulario());
        conteudo.add(Box.createVerticalStrut(24));

        // Rodapé com botões
        conteudo.add(criarRodape());

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

        JLabel titulo = EstiloReAlimenta.criarTitulo("Nova Promoção");
        JLabel sub    = EstiloReAlimenta.criarSubtitulo("Crie uma promoção vinculada a um alimento cadastrado.");
        p.add(titulo);
        p.add(Box.createVerticalStrut(4));
        p.add(sub);
        return p;
    }

    // Banner informativo amarelo
    private JPanel criarBannerInformativo() {
        JPanel banner = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xFF, 0xF9, 0xC4));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(new Color(0xF5, 0xC5, 0x18));
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        banner.setOpaque(false);
        banner.setBorder(new EmptyBorder(12, 16, 12, 16));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        banner.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icone = EstiloReAlimenta.criarIcone(FontAwesomeSolid.INFO_CIRCLE, 16, new Color(0xB4, 0x5A, 0x09));
        JLabel texto = new JLabel("A promoção ficará visível para consumidores durante o período informado.");
        texto.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        texto.setForeground(new Color(0x78, 0x35, 0x00));

        banner.add(icone, BorderLayout.WEST);
        banner.add(texto, BorderLayout.CENTER);
        return banner;
    }

    // Card principal do formulário
    private JPanel criarCardFormulario() {
        RoundedPanel card = new RoundedPanel(14, EstiloReAlimenta.BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(28, 28, 28, 28));

        JLabel tituloCard = new JLabel("Dados da Promoção");
        tituloCard.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tituloCard.setForeground(EstiloReAlimenta.TEXTO);
        tituloCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(tituloCard);
        card.add(Box.createVerticalStrut(20));

        // — Alimento (ComboBox) —
        card.add(criarLabelObrigatorio("Alimento"));
        card.add(Box.createVerticalStrut(6));
        campoAlimento = new JComboBox<String>(ALIMENTOS_MOCK);
        campoAlimento.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campoAlimento.setBackground(EstiloReAlimenta.BRANCO);
        campoAlimento.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        campoAlimento.setAlignmentX(Component.LEFT_ALIGNMENT);
        // TODO: carregar alimentos via AlimentoDAO.listarTodos()
        card.add(campoAlimento);
        card.add(Box.createVerticalStrut(6));
        JLabel dicaAlimento = new JLabel("Selecione um alimento previamente cadastrado.");
        dicaAlimento.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        dicaAlimento.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        dicaAlimento.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(dicaAlimento);
        card.add(Box.createVerticalStrut(18));

        // — Desconto (%) —
        card.add(criarLabelObrigatorio("Desconto (%)"));
        card.add(Box.createVerticalStrut(6));
        campoDesconto = new JTextField();
        JPanel wrapperDesconto = EstiloReAlimenta.criarCampoTexto(campoDesconto, "Ex: 20", FontAwesomeSolid.PERCENTAGE);
        card.add(wrapperDesconto);
        card.add(Box.createVerticalStrut(18));

        // — Datas lado a lado —
        JPanel linhaData = new JPanel(new GridLayout(1, 2, 20, 0));
        linhaData.setOpaque(false);
        linhaData.setAlignmentX(Component.LEFT_ALIGNMENT);
        linhaData.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Data Início
        JPanel painelInicio = new JPanel();
        painelInicio.setOpaque(false);
        painelInicio.setLayout(new BoxLayout(painelInicio, BoxLayout.Y_AXIS));
        painelInicio.add(criarLabelObrigatorio("Data de Início"));
        painelInicio.add(Box.createVerticalStrut(6));
        campoDataInicio = criarCampoData();
        JPanel wrapperInicio = criarWrapperData(campoDataInicio, FontAwesomeSolid.CALENDAR_ALT);
        painelInicio.add(wrapperInicio);

        // Data Fim
        JPanel painelFim = new JPanel();
        painelFim.setOpaque(false);
        painelFim.setLayout(new BoxLayout(painelFim, BoxLayout.Y_AXIS));
        painelFim.add(criarLabelObrigatorio("Data de Fim"));
        painelFim.add(Box.createVerticalStrut(6));
        campoDataFim = criarCampoData();
        JPanel wrapperFim = criarWrapperData(campoDataFim, FontAwesomeSolid.CALENDAR_CHECK);
        painelFim.add(wrapperFim);

        linhaData.add(painelInicio);
        linhaData.add(painelFim);
        card.add(linhaData);
        card.add(Box.createVerticalStrut(18));

        // — Observações —
        card.add(EstiloReAlimenta.criarLabel("Observações"));
        card.add(Box.createVerticalStrut(6));
        campoObservacoes = new JTextArea(4, 20);
        campoObservacoes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campoObservacoes.setForeground(EstiloReAlimenta.TEXTO);
        campoObservacoes.setLineWrap(true);
        campoObservacoes.setWrapStyleWord(true);
        campoObservacoes.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloReAlimenta.BORDA, 1, true),
                new EmptyBorder(8, 10, 8, 10)));
        campoObservacoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        EstiloReAlimenta.adicionarPlaceholder(
                new JTextField(), ""); // placeholder manual abaixo
        campoObservacoes.setForeground(new Color(0xA0, 0xAE, 0xC0));
        campoObservacoes.setText("Informações adicionais sobre a promoção...");
        campoObservacoes.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campoObservacoes.getText().equals("Informações adicionais sobre a promoção...")) {
                    campoObservacoes.setText("");
                    campoObservacoes.setForeground(EstiloReAlimenta.TEXTO);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campoObservacoes.getText().trim().isEmpty()) {
                    campoObservacoes.setText("Informações adicionais sobre a promoção...");
                    campoObservacoes.setForeground(new Color(0xA0, 0xAE, 0xC0));
                }
            }
        });

        JScrollPane scrollObs = new JScrollPane(campoObservacoes);
        scrollObs.setBorder(null);
        scrollObs.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollObs.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        card.add(scrollObs);

        return card;
    }

    // Rodapé com botões Cancelar / Cadastrar
    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rodape.setOpaque(false);
        rodape.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnCancelar = EstiloReAlimenta.criarBotaoSecundario("Cancelar", e -> navegarPromocoes());
        btnCancelar.setPreferredSize(new Dimension(140, 44));
        JButton btnCadastrar = EstiloReAlimenta.criarBotaoPrimario("Cadastrar Promoção", e -> salvarPromocao());
        btnCadastrar.setPreferredSize(new Dimension(200, 44));

        rodape.add(btnCancelar);
        rodape.add(btnCadastrar);
        return rodape;
    }

    // Helpers visuais
    // Label com asterisco vermelho indicando campo obrigatório
    private JPanel criarLabelObrigatorio(String texto) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

        JLabel lbl = EstiloReAlimenta.criarLabel(texto);
        JLabel ast = new JLabel("*");
        ast.setFont(new Font("Segoe UI", Font.BOLD, 13));
        ast.setForeground(new Color(0xDC, 0x26, 0x26));

        p.add(lbl);
        p.add(ast);
        return p;
    }

    // JFormattedTextField com máscara DD/MM/AAAA
    private JFormattedTextField criarCampoData() {
        JFormattedTextField campo;
        try {
            MaskFormatter fmt = new MaskFormatter("##/##/####");
            fmt.setPlaceholderCharacter('_');
            campo = new JFormattedTextField(fmt);
        } catch (ParseException ex) {
            campo = new JFormattedTextField();
        }
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setForeground(EstiloReAlimenta.TEXTO);
        campo.setCaretColor(EstiloReAlimenta.VERDE_PRINCIPAL);
        campo.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
        campo.setOpaque(false);
        return campo;
    }

    // Wrapper arredondado para JFormattedTextField com ícone
    private JPanel criarWrapperData(JFormattedTextField campo, FontAwesomeSolid icone) {
        JPanel w = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloReAlimenta.BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(EstiloReAlimenta.BORDA);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        w.setOpaque(false);
        w.setBorder(new EmptyBorder(8, 12, 8, 12));
        w.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        w.setAlignmentX(Component.LEFT_ALIGNMENT);
        w.add(EstiloReAlimenta.criarIcone(icone, 16, EstiloReAlimenta.TEXTO_SUAVE), BorderLayout.WEST);
        w.add(campo, BorderLayout.CENTER);
        return w;
    }

    // Lógica de salvamento — preparada para DAO
    private void salvarPromocao() {
        // Validação básica dos campos obrigatórios
        String alimento  = (String) campoAlimento.getSelectedItem();
        String desconto  = campoDesconto.getText().trim();
        String dataInicio = campoDataInicio.getText().trim();
        String dataFim    = campoDataFim.getText().trim();

        if (alimento == null || alimento.equals("Selecionar alimento...")) {
            mostrarErro("Selecione um alimento para a promoção.");
            return;
        }
        if (desconto.isEmpty()) {
            mostrarErro("Informe o percentual de desconto.");
            return;
        }
        try {
            int pct = Integer.parseInt(desconto);
            if (pct <= 0 || pct > 100) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            mostrarErro("O desconto deve ser um número inteiro entre 1 e 100.");
            return;
        }
        if (dataInicio.contains("_") || dataInicio.isEmpty()) {
            mostrarErro("Informe a data de início no formato DD/MM/AAAA.");
            return;
        }
        if (dataFim.contains("_") || dataFim.isEmpty()) {
            mostrarErro("Informe a data de fim no formato DD/MM/AAAA.");
            return;
        }

        String observacoes = campoObservacoes.getText().trim();
        if (observacoes.equals("Informações adicionais sobre a promoção...")) {
            observacoes = "";
        }

        // TODO: criar objeto Promocao e persistir via PromocaoDAO
        // Promocao promocao = new Promocao();
        // promocao.setAlimentoId(AlimentoDAO.buscarIdPorNome(alimento));
        // promocao.setDesconto(Integer.parseInt(desconto));
        // promocao.setDataInicio(DateUtil.parse(dataInicio));
        // promocao.setDataFim(DateUtil.parse(dataFim));
        // promocao.setObservacoes(observacoes);
        // PromocaoDAO.salvar(promocao);

        JOptionPane.showMessageDialog(this, "Promoção cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        navegarPromocoes();
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
    }

    // Navegação
    private void navegarDashboard()    { dispose(); new TelaDashboardComerciante(comerciante); }
    private void navegarAlimentos()    { dispose(); new TelaCadastroAlimento(comerciante); }
    private void navegarPromocoes()    { dispose(); new TelaMinhasPromocoes(comerciante); }
    private void navegarDoacoes()      { dispose(); new TelaDoacoesComerciante(comerciante); }
    private void navegarSolicitacoes() { dispose(); new TelaSolicitacoesComerciante(comerciante); }
    private void navegarNotificacoes() { dispose(); new TelaNotificacoesComerciante(comerciante); }
    private void sair()                { dispose(); new TelaLogin(); }

}