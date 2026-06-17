package view;
import util.EstiloReAlimenta;
import util.EstiloReAlimenta.RoundedPanel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaCadastroComerciante extends JFrame {
    private final JFrame   telaPai;
    private JTextField     campoProprietario;
    private JTextField     campoLoja;
    private JTextField     campoCnpj;
    private JTextField     campoEndereco;
    private JTextField     campoTelefone;
    private JTextField     campoEmail;
    private JPasswordField campoSenha;
    private JPasswordField campoConfirmar;

    public TelaCadastroComerciante(JFrame telaPai) {
        this.telaPai = telaPai;
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    // CONFIGURAÇÃO DA JANELA
    private void configurarJanela() {
        setTitle("ReAlimenta | Cadastro de Comerciante");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { voltarLogin(); }
        });
        setSize(580, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(EstiloReAlimenta.FUNDO);
        setLayout(new BorderLayout());
    }

    private void construirInterface() {
        // Cabeçalho com ícone FontAwesome STORE — sem PNG, sem emoji
        add(EstiloReAlimenta.criarCabecalho(
                "Cadastro de Comerciante",
                "Preencha os dados abaixo para criar sua conta",
                FontAwesomeSolid.STORE,
                this::voltarLogin
        ), BorderLayout.NORTH);
        add(EstiloReAlimenta.criarAreaScroll(criarCardFormulario()), BorderLayout.CENTER);
    }

    // CARD DE FORMULÁRIO
    private JPanel criarCardFormulario() {
        RoundedPanel card = new RoundedPanel(16, EstiloReAlimenta.BRANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(32, 36, 32, 36));
        card.setPreferredSize(new Dimension(460, 680));

        // NOME DO PROPRIETÁRIO
        card.add(EstiloReAlimenta.criarLabel("Nome do Proprietário"));
        card.add(Box.createVerticalStrut(5));
        campoProprietario = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(campoProprietario, "Digite seu nome completo", FontAwesomeSolid.USER));
        card.add(Box.createVerticalStrut(13));

        // NOME DA LOJA
        card.add(EstiloReAlimenta.criarLabel("Nome da Loja"));
        card.add(Box.createVerticalStrut(5));
        campoLoja = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(campoLoja, "Digite o nome da sua loja", FontAwesomeSolid.STORE));
        card.add(Box.createVerticalStrut(13));

        // CNPJ
        card.add(EstiloReAlimenta.criarLabel("CNPJ"));
        card.add(Box.createVerticalStrut(5));
        campoCnpj = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(campoCnpj, "Digite seu CNPJ", FontAwesomeSolid.ID_CARD));
        card.add(Box.createVerticalStrut(13));

        // ENDEREÇO
        card.add(EstiloReAlimenta.criarLabel("Endereço"));
        card.add(Box.createVerticalStrut(5));
        campoEndereco = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(campoEndereco, "Digite o endereço completo da loja", FontAwesomeSolid.MAP_MARKER_ALT));
        card.add(Box.createVerticalStrut(13));

        // TELEFONE
        card.add(EstiloReAlimenta.criarLabel("Telefone"));
        card.add(Box.createVerticalStrut(5));
        campoTelefone = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(campoTelefone, "Digite o telefone de contato", FontAwesomeSolid.PHONE));
        card.add(Box.createVerticalStrut(13));

        // EMAIL
        card.add(EstiloReAlimenta.criarLabel("E-mail"));
        card.add(Box.createVerticalStrut(5));
        campoEmail = new JTextField();
        card.add(EstiloReAlimenta.criarCampoTexto(campoEmail, "Digite seu e-mail", FontAwesomeSolid.ENVELOPE));
        card.add(Box.createVerticalStrut(13));

        // SENHA — LOCK + EYE encapsulados em criarCampoSenha
        card.add(EstiloReAlimenta.criarLabel("Senha"));
        card.add(Box.createVerticalStrut(5));
        campoSenha = new JPasswordField();
        card.add(EstiloReAlimenta.criarCampoSenha(campoSenha, "Digite sua senha"));
        card.add(Box.createVerticalStrut(13));

        // CONFIRMAR SENHA
        card.add(EstiloReAlimenta.criarLabel("Confirmar Senha"));
        card.add(Box.createVerticalStrut(5));
        campoConfirmar = new JPasswordField();
        card.add(EstiloReAlimenta.criarCampoSenha(campoConfirmar, "Confirme sua senha"));
        card.add(Box.createVerticalStrut(22));

        // BOTÃO REGISTRAR
        card.add(EstiloReAlimenta.criarBotaoPrimario("Registrar", e -> realizarCadastro()));
        card.add(Box.createVerticalStrut(16));

        // Link voltar
        card.add(criarLinkVoltar());
        return card;
    }

    // LINK VOLTAR
    private JPanel criarLinkVoltar() {
        JPanel linha = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        linha.setOpaque(false);
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTexto = new JLabel("Já tem uma conta?");
        lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTexto.setForeground(EstiloReAlimenta.TEXTO_SUAVE);
        JLabel lblLink = new JLabel("<html><u>Voltar para o login</u></html>");
        lblLink.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblLink.setForeground(EstiloReAlimenta.VERDE_SECUNDARIO);
        lblLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLink.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { voltarLogin(); }
        });
        linha.add(lblTexto);
        linha.add(lblLink);
        return linha;
    }

    // AÇÕES
    private void realizarCadastro() {
        String prop     = campoProprietario.getText().trim();
        String loja     = campoLoja.getText().trim();
        String cnpj     = campoCnpj.getText().trim();
        String endereco = campoEndereco.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String email = campoEmail.getText().trim();
        String senha    = new String(campoSenha.getPassword());
        String confirma = new String(campoConfirmar.getPassword());

        if (prop.isEmpty() || prop.equals("Digite seu nome completo")) {
            mostrarErro("O campo Nome do Proprietário é obrigatório."); return;
        }
        if (loja.isEmpty() || loja.equals("Digite o nome da sua loja")) {
            mostrarErro("O campo Nome da Loja é obrigatório."); return;
        }
        if (cnpj.isEmpty() || cnpj.equals("Digite seu CNPJ")) {
            mostrarErro("O campo CNPJ é obrigatório."); return;
        }
        if (endereco.isEmpty() || endereco.equals("Digite o endereço completo da loja")) {
            mostrarErro("O campo Endereço é obrigatório."); return;
        }
        if (telefone.isEmpty() || telefone.equals("Digite o telefone de contato")) {
            mostrarErro("O campo Telefone é obrigatório."); return;
        }
        if (email.isEmpty() || email.equals("Digite o email da conta")) {
            mostrarErro("O campo E-mail é obrigatorio."); return;
        }
        if (senha.length() < 6) {
            mostrarErro("A senha deve ter pelo menos 6 caracteres."); return;
        }
        if (!senha.equals(confirma)) {
            mostrarErro("As senhas não coincidem."); return;
        }
        JOptionPane.showMessageDialog(this,
                "Comerciante cadastrado com sucesso!\n(Integração com banco de dados pendente)",
                "Cadastro Realizado", JOptionPane.INFORMATION_MESSAGE);
        voltarLogin();
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    private void voltarLogin() {
        dispose();
        if (telaPai != null) telaPai.setVisible(true);
    }
}