package view;
import model.Comerciante;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.ParseException;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class TelaNovaDoacao extends JFrame {
    // Categorias — substituir por CategoriaDAO.listarTodas()
    private static final String[] CATEGORIAS = {
            "Selecionar categoria...", "Laticínios", "Padaria", "Frutas e Verduras",
            "Carnes", "Bebidas", "Grãos e Cereais", "Congelados", "Outros"
    };

    // Campos do formulário
    private JTextField           campoNomeAlimento;
    private JSpinner             campoQuantidade;
    private JFormattedTextField  campoDataLimite;
    private JTextField           campoHorario;
    private JComboBox<String>    campoCategoria;
    private JTextArea            campoObservacoes;

    // Upload de imagem
    private JLabel  previewImagem;
    private File    arquivoImagem;
    private JLabel  labelArquivo;

    // Construtor
    private Comerciante comerciante;
    public TelaNovaDoacao() {
        this.comerciante=comerciante;

        configurarJanela();
        construirLayout();
        setVisible(true);
    }

    // Configuração do JFrame
    private void configurarJanela() {
        setTitle("ReAlimenta | Nova Doação");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setSize(1280, 820);
        setLocationRelativeTo(null);
        getContentPane().setBackground(EstiloReAlimenta.FUNDO);
    }

    // Layout principal
    private void construirLayout() {
        setLayout(new BorderLayout());
        add(criarSidebar(), BorderLayout.WEST);
        add(criarConteudo(), BorderLayout.CENTER);
    }

    // Sidebar — Doações em destaque
    private JPanel criarSidebar() {
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(
                        0, 0, EstiloReAlimenta.VERDE_PRINCIPAL,
                        0, getHeight(), new Color(0x0A, 0x3D, 0x26)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(24, 12, 24, 12));

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
        sidebar.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.TAG, "Promoções", this::navegarPromocoes));
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(criarItemMenuAtivo(FontAwesomeSolid.HAND_HOLDING_HEART, "Doações"));
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

    // Item ativo: reutiliza EstiloReAlimenta.criarItemMenu() + destaque de borda
    private JPanel criarItemMenuAtivo(FontAwesomeSolid icone, String texto) {
        JPanel item = EstiloReAlimenta.criarItemMenu(icone, texto, null);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1, true),
                BorderFactory.createEmptyBorder(0, 8, 0, 8)));
        return item;
    }

    // Conteúdo com scroll
    private JScrollPane criarConteudo() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(EstiloReAlimenta.FUNDO);
        conteudo.setBorder(new EmptyBorder(32, 32, 32, 32));

        // Cabeçalho
        conteudo.add(criarCabecalhoPagina());
        conteudo.add(Box.createVerticalStrut(24));

        // Banner informativo verde-claro
        conteudo.add(criarBannerInformativo());
        conteudo.add(Box.createVerticalStrut(24));

        // Corpo: formulário (esq) + imagem (dir)
        JPanel corpo = new JPanel(new GridLayout(1, 2, 24, 0));
        corpo.setOpaque(false);
        corpo.setAlignmentX(Component.LEFT_ALIGNMENT);
        corpo.add(criarCardFormulario());
        corpo.add(criarCardImagem());
        conteudo.add(corpo);
        conteudo.add(Box.createVerticalStrut(24));

        // Rodapé com ações
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
        p.add(EstiloReAlimenta.criarTitulo("Nova Doação"));
        p.add(Box.createVerticalStrut(4));
        p.add(EstiloReAlimenta.criarSubtitulo("Preencha os dados do alimento que deseja disponibilizar para doação."));
        return p;
    }

    // Banner informativo
    private JPanel criarBannerInformativo() {
        JPanel banner = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(EstiloReAlimenta.VERDE_CLARO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(new Color(0x86, 0xEF, 0xAC));
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

        JLabel icone = EstiloReAlimenta.criarIcone(FontAwesomeSolid.INFO_CIRCLE, 16, EstiloReAlimenta.VERDE_PRINCIPAL);
        JLabel texto = new JLabel("Após publicação, consumidores poderão solicitar retirada.");
        texto.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        texto.setForeground(EstiloReAlimenta.VERDE_PRINCIPAL);

        banner.add(icone, BorderLayout.WEST);
        banner.add(texto, BorderLayout.CENTER);
        return banner;
    }

    // Card do formulário (coluna esquerda)
    private JPanel criarCardFormulario() {
        RoundedPanel card = new RoundedPanel(14, EstiloReAlimenta.BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(26, 26, 26, 26));

        JLabel tituloCard = new JLabel("Dados da Doação");
        tituloCard.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tituloCard.setForeground(EstiloReAlimenta.TEXTO);
        tituloCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(tituloCard);
        card.add(Box.createVerticalStrut(20));

        // Nome do alimento
        card.add(criarLabelObrigatorio("Nome do Alimento"));
        card.add(Box.createVerticalStrut(6));
        campoNomeAlimento = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(campoNomeAlimento, "Ex: Pão de Forma Integral", FontAwesomeSolid.CARROT));
        card.add(Box.createVerticalStrut(16));

        // — Categoria —
        card.add(criarLabelObrigatorio("Categoria"));
        card.add(Box.createVerticalStrut(6));
        campoCategoria = new JComboBox<String>(CATEGORIAS);
        campoCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campoCategoria.setBackground(EstiloReAlimenta.BRANCO);
        campoCategoria.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        campoCategoria.setAlignmentX(Component.LEFT_ALIGNMENT);
        // TODO: carregar via CategoriaDAO.listarTodas()
        card.add(campoCategoria);
        card.add(Box.createVerticalStrut(16));

        // — Quantidade e Data lado a lado —
        JPanel linhaQtdData = new JPanel(new GridLayout(1, 2, 16, 0));
        linhaQtdData.setOpaque(false);
        linhaQtdData.setAlignmentX(Component.LEFT_ALIGNMENT);
        linhaQtdData.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JPanel painelQtd = new JPanel();
        painelQtd.setOpaque(false);
        painelQtd.setLayout(new BoxLayout(painelQtd, BoxLayout.Y_AXIS));
        painelQtd.add(criarLabelObrigatorio("Quantidade"));
        painelQtd.add(Box.createVerticalStrut(6));
        SpinnerNumberModel modeloQtd = new SpinnerNumberModel(1, 1, 9999, 1);
        campoQuantidade = new JSpinner(modeloQtd);
        campoQuantidade.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campoQuantidade.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        campoQuantidade.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelQtd.add(campoQuantidade);

        JPanel painelData = new JPanel();
        painelData.setOpaque(false);
        painelData.setLayout(new BoxLayout(painelData, BoxLayout.Y_AXIS));
        painelData.add(criarLabelObrigatorio("Data Limite Retirada"));
        painelData.add(Box.createVerticalStrut(6));
        campoDataLimite = criarCampoData();
        painelData.add(criarWrapperData(campoDataLimite, FontAwesomeSolid.CALENDAR_ALT));

        linhaQtdData.add(painelQtd);
        linhaQtdData.add(painelData);
        card.add(linhaQtdData);
        card.add(Box.createVerticalStrut(16));

        // — Horário de retirada —
        card.add(criarLabelObrigatorio("Horário de Retirada"));
        card.add(Box.createVerticalStrut(6));
        campoHorario = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(campoHorario, "Ex: 14:00 - 18:00", FontAwesomeSolid.CLOCK));
        card.add(Box.createVerticalStrut(16));

        // — Observações —
        card.add(EstiloReAlimenta.criarLabel("Observações"));
        card.add(Box.createVerticalStrut(6));
        campoObservacoes = new JTextArea(4, 20);
        campoObservacoes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campoObservacoes.setForeground(new Color(0xA0, 0xAE, 0xC0));
        campoObservacoes.setText("Detalhes adicionais sobre a doação...");
        campoObservacoes.setLineWrap(true);
        campoObservacoes.setWrapStyleWord(true);
        campoObservacoes.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(EstiloReAlimenta.BORDA, 1, true), new EmptyBorder(8, 10, 8, 10)));
        campoObservacoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoObservacoes.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campoObservacoes.getText().equals("Detalhes adicionais sobre a doação...")) {
                    campoObservacoes.setText("");
                    campoObservacoes.setForeground(EstiloReAlimenta.TEXTO);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campoObservacoes.getText().trim().isEmpty()) {
                    campoObservacoes.setText("Detalhes adicionais sobre a doação...");
                    campoObservacoes.setForeground(new Color(0xA0, 0xAE, 0xC0));
                }
            }
        });

        JScrollPane scrollObs = new JScrollPane(campoObservacoes);
        scrollObs.setBorder(null);
        scrollObs.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollObs.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        card.add(scrollObs);

        card.add(Box.createVerticalGlue());
        return card;
    }

    // Card de upload de imagem (coluna direita)
    private JPanel criarCardImagem() {
        RoundedPanel card = new RoundedPanel(14, EstiloReAlimenta.BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(26, 26, 26, 26));

        JLabel tituloCard = new JLabel("Imagem do Alimento");
        tituloCard.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tituloCard.setForeground(EstiloReAlimenta.TEXTO);
        tituloCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(tituloCard);
        card.add(Box.createVerticalStrut(4));
        card.add(EstiloReAlimenta.criarSubtitulo("Adicione uma foto do alimento para facilitar a identificação."));
        card.add(Box.createVerticalStrut(18));

        // Área de preview
        previewImagem = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getIcon() == null) {
                    g2.setColor(EstiloReAlimenta.VERDE_CLARO);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                    float[] dash = {8f, 4f};
                    g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash, 0f));
                    g2.setColor(EstiloReAlimenta.VERDE_PRINCIPAL);
                    g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 14, 14);
                    // Ícone central
                    FontIcon fi = FontIcon.of(FontAwesomeSolid.IMAGE, 36,
                            new Color(EstiloReAlimenta.VERDE_PRINCIPAL.getRed(), EstiloReAlimenta.VERDE_PRINCIPAL.getGreen(), EstiloReAlimenta.VERDE_PRINCIPAL.getBlue(), 80));
                    fi.paintIcon(this, g2, (getWidth() - fi.getIconWidth()) / 2, (getHeight() - fi.getIconHeight()) / 2 - 10);
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    g2.setColor(EstiloReAlimenta.VERDE_PRINCIPAL);
                    String txt = "Clique para selecionar";
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(txt, (getWidth() - fm.stringWidth(txt)) / 2, getHeight() / 2 + 30);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        previewImagem.setHorizontalAlignment(SwingConstants.CENTER);
        previewImagem.setVerticalAlignment(SwingConstants.CENTER);
        previewImagem.setPreferredSize(new Dimension(0, 200));
        previewImagem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        previewImagem.setAlignmentX(Component.LEFT_ALIGNMENT);
        previewImagem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        previewImagem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { selecionarImagem(); }
        });
        card.add(previewImagem);
        card.add(Box.createVerticalStrut(10));

        // Nome do arquivo selecionado
        labelArquivo = new JLabel("Nenhum arquivo selecionado");
        labelArquivo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        labelArquivo.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        labelArquivo.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(labelArquivo);
        card.add(Box.createVerticalStrut(14));

        // Botões
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        btnRow.setOpaque(false);
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnSelecionar = EstiloReAlimenta.criarBotaoPrimario("Selecionar Imagem", e -> selecionarImagem());
        btnSelecionar.setPreferredSize(new Dimension(170, 40));
        JButton btnRemover = EstiloReAlimenta.criarBotaoSecundario("Remover", e -> removerImagem());
        btnRemover.setPreferredSize(new Dimension(100, 40));

        btnRow.add(btnSelecionar);
        btnRow.add(btnRemover);
        card.add(btnRow);
        card.add(Box.createVerticalStrut(16));

        // Dica
        JLabel dica = new JLabel("Formatos aceitos: JPG, PNG, WEBP — Máx. 5 MB");
        dica.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        dica.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        dica.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(dica);
        card.add(Box.createVerticalGlue());
        return card;
    }

    // Rodapé: Cancelar / Cadastrar Doação
    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rodape.setOpaque(false);
        rodape.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnCancelar = EstiloReAlimenta.criarBotaoSecundario("Cancelar", e -> navegarDoacoes());
        btnCancelar.setPreferredSize(new Dimension(140, 44));
        JButton btnCadastrar = EstiloReAlimenta.criarBotaoPrimario("Cadastrar Doação", e -> salvarDoacao());
        btnCadastrar.setPreferredSize(new Dimension(190, 44));

        rodape.add(btnCancelar);
        rodape.add(btnCadastrar);
        return rodape;
    }

    // Helpers visuais
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

    // Upload de imagem
    private void selecionarImagem() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Selecionar imagem do alimento");
        fc.setFileFilter(new FileNameExtensionFilter("Imagens (JPG, PNG, WEBP)", "jpg", "jpeg", "png", "webp"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            arquivoImagem = fc.getSelectedFile();
            labelArquivo.setText(arquivoImagem.getName());
            // TODO: fazer upload real para o servidor quando backend estiver integrado
            ImageIcon img = new ImageIcon(arquivoImagem.getAbsolutePath());
            int largura = previewImagem.getWidth() > 0 ? previewImagem.getWidth() : 300;
            Image scaled = img.getImage().getScaledInstance(largura, 190, Image.SCALE_SMOOTH);
            previewImagem.setIcon(new ImageIcon(scaled));
            previewImagem.repaint();
        }
    }

    private void removerImagem() {
        arquivoImagem = null;
        previewImagem.setIcon(null);
        labelArquivo.setText("Nenhum arquivo selecionado");
        previewImagem.repaint();
    }

    // Lógica de salvamento — preparada para DAO
    private void salvarDoacao() {
        // Coleta e validação dos campos
        String nome      = campoNomeAlimento.getText().trim();
        String categoria = (String) campoCategoria.getSelectedItem();
        int    quantidade = (Integer) campoQuantidade.getValue();
        String dataLimite = campoDataLimite.getText().trim();
        String horario   = campoHorario.getText().trim();

        if (nome.isEmpty() || nome.equals("Ex: Pão de Forma Integral")) {
            mostrarErro("Informe o nome do alimento.");
            return;
        }
        if ("Selecionar categoria...".equals(categoria)) {
            mostrarErro("Selecione uma categoria.");
            return;
        }
        if (dataLimite.contains("_") || dataLimite.isEmpty()) {
            mostrarErro("Informe a data limite de retirada no formato DD/MM/AAAA.");
            return;
        }
        if (horario.isEmpty() || horario.equals("Ex: 14:00 - 18:00")) {
            mostrarErro("Informe o horário de retirada.");
            return;
        }
        String observacoes = campoObservacoes.getText().trim();
        if (observacoes.equals("Detalhes adicionais sobre a doação...")) observacoes = "";

        // TODO: integrar DoacaoDAO
        // Doacao doacao = new Doacao();
        // doacao.setNomeAlimento(nome);
        // doacao.setCategoria(categoria);
        // doacao.setQuantidade(quantidade);
        // doacao.setDataLimite(DateUtil.parse(dataLimite));
        // doacao.setHorarioRetirada(horario);
        // doacao.setObservacoes(observacoes);
        // doacao.setStatus("Disponível");
        // if (arquivoImagem != null) {
        //     doacao.setImagemPath(ImagemUtil.salvar(arquivoImagem));
        // }
        // DoacaoDAO.salvar(doacao);

        JOptionPane.showMessageDialog(this, "Doação cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        navegarDoacoes();
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem,
                "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
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