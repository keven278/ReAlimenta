package view;
import dao.UsuarioDAO;
import model.Comerciante;
import model.Consumidor;
import model.Usuario;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaLogin extends JFrame {
    private JTextField     campoEmail;
    private JPasswordField campoSenha;
    private JCheckBox      checkLembrar;

    public TelaLogin() {
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    // CONFIGURAÇÃO DA JANELA
    private void configurarJanela() {
        setTitle("ReAlimenta | Sistema de Doação de Alimentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 580);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(EstiloReAlimenta.FUNDO);
        setLayout(new BorderLayout());
    }

    private void construirInterface() {
        add(criarPainelLateral(), BorderLayout.WEST);
        add(criarPainelCentral(), BorderLayout.CENTER);
    }

    // PAINEL LATERAL (SIDEBAR VERDE)
    private JPanel criarPainelLateral() {
        JPanel painel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(
                        0, 0, EstiloReAlimenta.VERDE_PRINCIPAL,
                        0, getHeight(), new Color(0x0D, 0x4A, 0x2C)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painel.setPreferredSize(new Dimension(220, 0));
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        // Logo maçã desenhada via Graphics2D
        painel.add(EstiloReAlimenta.criarLogoMaca());
        painel.add(Box.createVerticalStrut(8));

        // Nome do sistema
        JLabel lblNome = new JLabel("ReAlimenta");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblNome.setForeground(EstiloReAlimenta.BRANCO);
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(lblNome);
        painel.add(Box.createVerticalStrut(6));

        // Separador sutil
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 60));
        sep.setMaximumSize(new Dimension(160, 1));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(sep);
        painel.add(Box.createVerticalStrut(20));

        // Itens de menu com Ikonli — sem PNG
        painel.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.HOME, "Início", () -> {}));
        painel.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.INFO_CIRCLE, "Sobre o Projeto", this::mostrarSobreProjeto));
        painel.add(EstiloReAlimenta.criarItemMenu(FontAwesomeSolid.QUESTION_CIRCLE, "Como Funciona", this::mostrarComoFunciona));
        painel.add(Box.createVerticalGlue());

        // Versão
        JLabel lblVersao = new JLabel("⛨  Versão 1.0.0");
        lblVersao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblVersao.setForeground(new Color(255, 255, 255, 130));
        lblVersao.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(lblVersao);

        return painel;
    }

    //  PAINEL CENTRAL
    private JPanel criarPainelCentral() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(EstiloReAlimenta.FUNDO);
        painel.add(criarCardLogin());
        return painel;
    }

    private JPanel criarCardLogin() {
        RoundedPanel card = new RoundedPanel(16, EstiloReAlimenta.BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(36, 40, 36, 40));
        card.setPreferredSize(new Dimension(420, 460));

        // Cabeçalho do card
        JLabel lblBemVindo = new JLabel("Bem-vindo(a)!");
        lblBemVindo.setFont(EstiloReAlimenta.FONTE_TITULO);
        lblBemVindo.setForeground(EstiloReAlimenta.TEXTO);
        lblBemVindo.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblBemVindo);

        JLabel lblSub = new JLabel("Faça login para continuar no ReAlimenta");
        lblSub.setFont(EstiloReAlimenta.FONTE_SUBTITULO);
        lblSub.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblSub);
        card.add(Box.createVerticalStrut(24));

        // Login — ícone via Ikonli
        card.add(EstiloReAlimenta.criarLabel("Login"));
        card.add(Box.createVerticalStrut(5));
        campoEmail = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(
                campoEmail, "Digite seu E-mail", FontAwesomeSolid.USER));
        card.add(Box.createVerticalStrut(14));

        // SENHA — ícone LOCK + olho já encapsulados em criarCampoSenha
        card.add(EstiloReAlimenta.criarLabel("Senha"));
        card.add(Box.createVerticalStrut(5));
        campoSenha = new JPasswordField();
        card.add(EstiloReAlimenta.criarCampoSenha(campoSenha, "Digite sua senha"));
        card.add(Box.createVerticalStrut(10));

        // Lembrar-me
        checkLembrar = new JCheckBox("Lembrar-me neste dispositivo");
        checkLembrar.setFont(EstiloReAlimenta.FONTE_SUBTITULO);
        checkLembrar.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        checkLembrar.setOpaque(false);
        checkLembrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(checkLembrar);
        card.add(Box.createVerticalStrut(20));

        // Botão Entrar
        card.add(EstiloReAlimenta.criarBotaoPrimario("Entrar", e -> realizarLogin()));
        card.add(Box.createVerticalStrut(12));

        // Separador "ou"
        card.add(EstiloReAlimenta.criarSeparadorOu());
        card.add(Box.createVerticalStrut(12));

        // Botões de cadastro
        card.add(EstiloReAlimenta.criarBotaoSecundario(
                "Cadastrar como Consumidor", e -> abrirTelaCadastroConsumidor()));
        card.add(Box.createVerticalStrut(8));
        card.add(EstiloReAlimenta.criarBotaoSecundario(
                "Cadastrar como Comerciante", e -> abrirTelaCadastroComerciante()));

        return card;
    }

    // AÇÕES
    private void realizarLogin() {
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha o e-mail e a senha para continuar.",
                    "Campos obrigatórios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.login(email, senha);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this,
                    "E-mail ou senha inválidos.",
                    "Erro ao entrar",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Login realizado com sucesso!",
                "Bem-vindo(a)!",
                JOptionPane.INFORMATION_MESSAGE);

        if (usuario instanceof Consumidor) {
            new TelaDashboardConsumidor(usuario.getNome());
            dispose();

        } else if (usuario instanceof Comerciante) {
            new TelaDashboardComerciante((Comerciante) usuario);
            dispose();
        }
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
}