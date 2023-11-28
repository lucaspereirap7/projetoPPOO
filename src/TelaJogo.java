import javax.swing.border.Border;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.*;

public class TelaJogo {
    private JFrame janelaPrincipal;
    private JTextArea areaMensagens;
    private Insets margem;
    private JTextField campoEntrada;
    private JPanel painelAmbiente;
    private JLabel imagem;
    
    public TelaJogo(){
        janelaPrincipal = new JFrame("Estelar: A grande fuga!");
        inicializarJanela();
        criarPainelInferior();
        criarPainelAmbiente();
    }

    //Método inicializa o tamanho da janela
    private void inicializarJanela(){
        janelaPrincipal.setSize(1280, 720);
        janelaPrincipal.setLayout(new BorderLayout());

        JPanel painelEsquerda = new JPanel();
        painelEsquerda.setLayout(new BoxLayout(painelEsquerda, BoxLayout.Y_AXIS));
        janelaPrincipal.add(painelEsquerda, BorderLayout.WEST);

        JPanel painelDireita = new JPanel();
        painelDireita.setLayout(new BoxLayout(painelDireita, BoxLayout.X_AXIS));
        janelaPrincipal.add(painelDireita, BorderLayout.EAST);
    }
    //Método utilizado para exibir
    public void exibirJanela(){
        janelaPrincipal.setVisible(true);
    }

    private void criarPainelInferior() {
        JPanel painelInferior = new JPanel();
        painelInferior.setPreferredSize(new Dimension(1280,200));
        criarSubPainelMensagens(painelInferior);
        criarSubPainelEntrada(painelInferior);
        janelaPrincipal.add(painelInferior, BorderLayout.SOUTH);
    }

    private void criarSubPainelMensagens(JPanel painelPai) {
        JPanel painelMensagens = new JPanel();
        painelMensagens.setPreferredSize(new Dimension(1280,150));
        areaMensagens = new JTextArea();                
        areaMensagens.setLineWrap(true);      
        areaMensagens.setWrapStyleWord(true); 
        areaMensagens.setEditable(false); 
        areaMensagens.setMargin(margem);  
        areaMensagens.setFont(new Font("Consolas", Font.BOLD, 16));
        areaMensagens.setBackground(janelaPrincipal.getBackground());  
        JScrollPane areaMensagensScrollPane = new JScrollPane(areaMensagens);
        areaMensagensScrollPane.setPreferredSize(new Dimension(1280-20,140));
        areaMensagensScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);        
        painelMensagens.add(areaMensagensScrollPane);
        painelPai.add(painelMensagens);
    }

    private void criarSubPainelEntrada(JPanel painelPai) {
        JPanel painelEntrada = new JPanel();
        painelEntrada.setPreferredSize(new Dimension(1280,50));
        painelEntrada.add(new JLabel("Comando:"));
        campoEntrada = new JTextField();
        campoEntrada.setPreferredSize(new Dimension(600,30));
        campoEntrada.setFont(new Font("Consolas", Font.BOLD, 16));
        painelEntrada.add(campoEntrada);

        JButton botaoComando = new JButton("Enviar");
        painelEntrada.add(botaoComando);
        painelPai.add(painelEntrada);
    }

    private void criarPainelAmbiente() {
        // cria o painel do ambiente
        painelAmbiente = new JPanel();
        painelAmbiente.setPreferredSize(new Dimension(980, 500));
        painelAmbiente.setBorder(BorderFactory.createTitledBorder("Aeronave Espacial"));
        imagem.setIcon(new ImageIcon("mapa.png"));
        
        // cria o rótulo que é usado para mostrar a imagem do ambiente e o adiciona no painel.
        // Nesse momento ainda não há nenhuma imagem.
        imagem = new JLabel();
        painelAmbiente.add(imagem);
        
        // adiciona o painel do ambiente à janela
        janelaPrincipal.add(painelAmbiente, BorderLayout.CENTER);
    }
}
