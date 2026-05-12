package view;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class TelaCadastroConsumidor extends JFrame {
    private final JFrame telaPai;
    private JTextField   campoNome;
    private JTextField   campoCpf;
    private JPasswordField campoSenha;
    private JPasswordField campoConfirmar;

    public TelaCadastroConsumidor(JFrame telaPai) {
        this.telaPai = telaPai;
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    // Configurações básicas do JFrame
    private void configurarJanela() {
        setTitle("ReAlimenta | Cadastro de Consumidor");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { voltarLogin(); }
        });
        setSize(560, 640);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(TelaLogin.FUNDO);
        setLayout(new BorderLayout());
    }

    // Monta header + área de conteúdo
    private void construirInterface() {
        add(criarCabecalho(), BorderLayout.NORTH);
        add(criarAreaConteudo(), BorderLayout.CENTER);
    }

    // CABEÇALHO
    // Faixa verde escuro no topo com botão voltar e título
    private JPanel criarCabecalho() {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, TelaLogin.VERDE_PRINCIPAL,
                        getWidth(), 0, new Color(0x1A, 0x7A, 0x4A));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 76));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Botão voltar
        JButton btnVoltar = criarBotaoVoltar();
        header.add(btnVoltar, BorderLayout.WEST);

        // Bloco central: título + subtítulo
        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("Cadastro de Consumidor");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(TelaLogin.BRANCO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Preencha os dados abaixo para criar sua conta");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(255, 255, 255, 200));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        centro.add(Box.createVerticalGlue());
        centro.add(lblTitulo);
        centro.add(Box.createVerticalStrut(2));
        centro.add(lblSub);
        centro.add(Box.createVerticalGlue());
        header.add(centro, BorderLayout.CENTER);

        // Ícone do usuário no canto direito
        JLabel icone = new JLabel("👤");
        icone.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        icone.setForeground(new Color(255, 255, 255, 180));
        header.add(icone, BorderLayout.EAST);
        return header;
    }
    // botão "← Voltar" estilizado
    private JButton criarBotaoVoltar() {
        JButton btn = new JButton("←") {
            boolean hover = false;
            { addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
            }); }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (hover) {
                    g2.setColor(new Color(255, 255, 255, 40));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }
                g2.setColor(TelaLogin.BRANCO);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth()  - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(40, 40));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> voltarLogin());
        return btn;
    }

    // CONTEÚDO

    // Área scrollável com o card de formulário
    private JScrollPane criarAreaConteudo() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(TelaLogin.FUNDO);
        wrapper.setBorder(BorderFactory.createEmptyBorder(24, 0, 24, 0));
        wrapper.add(criarCardFormulario());

        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(TelaLogin.FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    // Card branco com campos do formulário
    private JPanel criarCardFormulario() {
        TelaLogin.RoundedPanel card =
                new TelaLogin.RoundedPanel(16, TelaLogin.BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(
                BorderFactory.createEmptyBorder(32, 36, 32, 36)
        );
        card.setPreferredSize(new Dimension(460, 520));

        // NOME COMPLETO
        card.add(criarLabel("Nome Completo"));
        card.add(Box.createVerticalStrut(5));
        campoNome = new JTextField();
        card.add(criarCampoTexto(campoNome, "Digite seu nome completo", "assets/icons/user.png"));
        card.add(Box.createVerticalStrut(13));

        // CPF
        card.add(criarLabel("CPF"));
        card.add(Box.createVerticalStrut(5));
        campoCpf = new JTextField();
        card.add(criarCampoTexto(campoCpf, "Digite seu CPF", "assets/icons/document.png"));
        card.add(Box.createVerticalStrut(13));

        // SENHA
        card.add(criarLabel("Senha"));
        card.add(Box.createVerticalStrut(5));
        campoSenha = new JPasswordField();
        card.add(criarCampoSenha(campoSenha, "Digite sua senha"));
        card.add(Box.createVerticalStrut(13));

        // CONFIRMAR SENHA
        card.add(criarLabel("Confirmar Senha"));
        card.add(Box.createVerticalStrut(5));
        campoConfirmar = new JPasswordField();
        card.add(criarCampoSenha(campoConfirmar, "Confirme sua senha"));
        card.add(Box.createVerticalStrut(20));

        // MENSAGEM SENHA
        JLabel lblSenha = new JLabel(
                "Sua senha deve ter pelo menos 6 caracteres."
        );
        lblSenha.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSenha.setForeground(TelaLogin.TEXTO_SUAVE);
        lblSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblSenha);
        card.add(Box.createVerticalStrut(20));

        // BOTÃO REGISTRAR
        card.add(
                criarBotaoPrimario("Registrar", e -> realizarCadastro())
        );
        card.add(Box.createVerticalStrut(16));

        // LINK LOGIN
        card.add(criarLinkVoltar());

        return card;
    }
    // Caixa verde clara com aviso de segurança
    private JPanel criarAlertaSeguranca() {
        JPanel alerta = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(TelaLogin.VERDE_CLARO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(new Color(TelaLogin.VERDE_SECUNDARIO.getRed(),
                        TelaLogin.VERDE_SECUNDARIO.getGreen(),
                        TelaLogin.VERDE_SECUNDARIO.getBlue(), 120));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        alerta.setOpaque(false);
        alerta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        alerta.setAlignmentX(Component.LEFT_ALIGNMENT);
        alerta.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        JLabel icone = new JLabel("🛡");
        icone.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        icone.setForeground(TelaLogin.VERDE_PRINCIPAL);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel lblTit = new JLabel("Segurança");
        lblTit.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTit.setForeground(TelaLogin.VERDE_PRINCIPAL);

        JLabel lblMsg = new JLabel("Sua senha deve ter pelo menos 6 caracteres.");
        lblMsg.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblMsg.setForeground(new Color(0x1A, 0x5C, 0x36));

        textos.add(lblTit);
        textos.add(lblMsg);

        alerta.add(icone, BorderLayout.WEST);
        alerta.add(textos, BorderLayout.CENTER);
        return alerta;
    }

    // Link "Já tem uma conta? Voltar para o login"
    private JPanel criarLinkVoltar() {
        JPanel linha = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        linha.setOpaque(false);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTexto = new JLabel("Já tem uma conta?");
        lblTexto.setFont(TelaLogin.FONTE_LINK);
        lblTexto.setForeground(TelaLogin.TEXTO_SUAVE);

        JLabel lblLink = new JLabel("<html><u>Voltar para o login</u></html>");
        lblLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblLink.setForeground(TelaLogin.VERDE_SECUNDARIO);
        lblLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLink.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { voltarLogin(); }
        });

        linha.add(lblTexto);
        linha.add(lblLink);
        return linha;
    }

    // COMPONENTES

    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(TelaLogin.FONTE_LABEL);
        lbl.setForeground(TelaLogin.TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

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

        JPanel w = criarWrapper();

        ImageIcon icon = new ImageIcon(caminhoIcone);

        Image img = icon.getImage().getScaledInstance(
                20,
                20,
                Image.SCALE_SMOOTH
        );

        JLabel lb = new JLabel(new ImageIcon(img));

        w.add(lb, BorderLayout.WEST);
        w.add(campo, BorderLayout.CENTER);

        return w;
    }

    private JPanel criarCampoSenha(JPasswordField campo, String placeholder) {

        campo.setFont(TelaLogin.FONTE_CAMPO);
        campo.setForeground(TelaLogin.TEXTO);
        campo.setCaretColor(TelaLogin.VERDE_PRINCIPAL);
        campo.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
        campo.setOpaque(false);
        campo.setEchoChar('●');

        JPanel w = criarWrapper();

        // Ícone cadeado
        ImageIcon lockIcon = new ImageIcon("assets/icons/lock.png");

        Image lockImg = lockIcon.getImage().getScaledInstance(
                20,
                20,
                Image.SCALE_SMOOTH
        );

        JLabel lb = new JLabel(new ImageIcon(lockImg));

        // Ícone olho
        ImageIcon eyeIcon = new ImageIcon("assets/icons/eye.png");

        Image eyeImg = eyeIcon.getImage().getScaledInstance(
                18,
                18,
                Image.SCALE_SMOOTH
        );

        JLabel olho = new JLabel(new ImageIcon(eyeImg));

        olho.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        olho.addMouseListener(new MouseAdapter() {

            boolean visivel = false;

            @Override
            public void mouseClicked(MouseEvent e) {

                visivel = !visivel;

                campo.setEchoChar(visivel ? (char) 0 : '●');
            }
        });

        w.add(lb, BorderLayout.WEST);
        w.add(campo, BorderLayout.CENTER);
        w.add(olho, BorderLayout.EAST);

        return w;
    }

    private JPanel criarWrapper() {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(TelaLogin.BRANCO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(TelaLogin.BORDA);
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

    private JButton criarBotaoPrimario(String texto, ActionListener acao) {
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
                g2.setColor(hover ? TelaLogin.VERDE_SECUNDARIO : TelaLogin.VERDE_PRINCIPAL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(TelaLogin.BRANCO);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth()  - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btn.setFont(TelaLogin.FONTE_BOTAO);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(acao);
        return btn;
    }

    // AÇÕES

    // Valida campos e exibe resultado
    private void realizarCadastro() {
        String nome     = campoNome.getText().trim();
        String cpf      = campoCpf.getText().trim();
        String senha    = new String(campoSenha.getPassword());
        String confirma = new String(campoConfirmar.getPassword());

        // Ignora placeholders
        if (nome.equals("Digite seu nome completo") || nome.isEmpty()) {
            mostrarErro("O campo Nome Completo é obrigatório."); return;
        }
        if (cpf.equals("Digite seu CPF") || cpf.isEmpty()) {
            mostrarErro("O campo CPF é obrigatório."); return;
        }
        if (senha.length() < 6) {
            mostrarErro("A senha deve ter pelo menos 6 caracteres."); return;
        }
        if (!senha.equals(confirma)) {
            mostrarErro("As senhas não coincidem."); return;
        }

        JOptionPane.showMessageDialog(this,
                "Consumidor cadastrado com sucesso!\n(Integração com banco de dados pendente)",
                "Cadastro Realizado", JOptionPane.INFORMATION_MESSAGE);
        voltarLogin();
    }

    private void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    // Fecha esta tela e reexibe a TelaLogin
    private void voltarLogin() {
        dispose();
        if (telaPai != null) telaPai.setVisible(true);
    }
}