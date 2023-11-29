import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

/**
 * A classe TelaJogo é responsável por criar e gerenciar a interface gráfica do jogo.
 * Ela utiliza a biblioteca Swing para a criação de elementos gráficos como janelas, painéis e componentes de texto.
 * 
 * Possui elementos como áreas de texto para mensagens, campo de entrada para comandos, e labels para exibição de imagens.
 * 
 * Esta classe facilita a interação do jogador com o jogo, permitindo a entrada de comandos e exibindo informações relevantes.
 * 
 * Para interagir com a classe, é possível definir comandos usando o método setComando(String novoComando),
 * obter comandos com o método getComando(), imprimir mensagens na área de saída com o método imprimir(String mensagem),
 * imprimir tarefas com o método imprimirTarefas(String tarefas) e imprimir segundos com o método imprimirSegundos(String segundos).
 * 
 * @author Eduardo Ruan Guimaraes Fonseca
 * @version 29/11/2023
 */

public class TelaJogo{

     /** A janela principal do jogo. */
    private JFrame janelaPrincipal;

    /** A área de saída para mensagens. */
    private JTextArea output;

     /** O campo de entrada para comandos. */
    private JTextField input;

    /** A área para exibição de tarefas. */
    private JTextArea campoTarefas;

     /** A área para exibição de segundos. */
    private JTextArea campoTimer;

    /** O label para exibição do mapa. */
    private JLabel mapa;

     /** O label para exibição da imagem dos dutos. */
    private JLabel imagemDutos;

    /** O comando digitado pelo jogador. */
    private String comando;


    /**
     * Cria um novo objeto TelaJogo.
     * Inicializa os componentes gráficos, como janelas, áreas de texto, campos de entrada e labels.
     */
    public TelaJogo(){
        comando = null;
        
        janelaPrincipal = new JFrame("Estelar: A grande fuga!");
        mapa = new JLabel(new ImageIcon("img/mapa.png"));
        imagemDutos = new JLabel(new ImageIcon("img/Dutos.jpeg"));

        campoTarefas = new JTextArea();
        campoTimer = new JTextArea();
        output = new JTextArea();
        input = new JTextField();

        input.addActionListener( // Método adiciona um ouvinte de ação ao componente 'input'
            new ActionListener() { // Cria uma instância anônima da interface ActionListener
                @Override
                public void actionPerformed(ActionEvent e) { //Sobrescrevo o método actionPerformed da interface ActionListener
                    String novoComando = input.getText(); // Obtém o texto atual do componente 'input', entrada do usuário na interface gráfica
                    setComando(novoComando); // Atribui a nosso atributo 'comando', a String digitada pelo usuário
                    imprimir(novoComando + "\n"); // Imprimi o que o usuário digitou
                    input.setText(""); // Limpa a a barra de entrada do usuário assim que ele aperta a tecla ENTER
                }
            });

        inicializarJanela();
    }

    /**
     * Inicializa a janela principal e os elementos gráficos.
     * Define o layout da janela, cria os painéis, áreas de texto, campos de entrada e labels.
     */
    private void inicializarJanela(){
        // Configurações da janela
        janelaPrincipal.setSize(1400, 800);
        janelaPrincipal.setLayout(new BorderLayout());

        campoTarefas.setEditable(false);//nao permite digitação
        campoTimer.setEditable(false);
        output.setEditable(false);

        // Configurações do lado esquerdo
        JPanel painelEsquerda  = new JPanel(new GridLayout(2, 1));
        painelEsquerda.add(campoTimer);
        painelEsquerda.add(imagemDutos);
        janelaPrincipal.add(painelEsquerda, BorderLayout.WEST);

        // Configurações do lado direito
        JPanel painelDireita = new JPanel();
        painelDireita.setLayout(new BoxLayout(painelDireita, BoxLayout.Y_AXIS));
        painelDireita.add(campoTarefas);
        janelaPrincipal.add(painelDireita, BorderLayout.EAST);

        // Configurações do centro
        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new BoxLayout(painelCentro, BoxLayout.Y_AXIS));
        painelCentro.add(mapa);
        janelaPrincipal.add(painelCentro, BorderLayout.CENTER);

        // Configurações do inferior
        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new BoxLayout(painelInferior, BoxLayout.Y_AXIS));
        JScrollPane outputScrollPane = new JScrollPane(output);// Permite rolar o conteúdo de um componente, e neste caso, é o 'output'
        outputScrollPane.setPreferredSize(new Dimension(1380,140));// Define as dimensões preferenciais do JScrollPane
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);// Independentemente do conteúdo do componente ser ou não maior que a área visível, a barra de rolagem vertical será sempre exibida.
        input.setPreferredSize(new Dimension(20, 50));// Define as dimensões preferenciais do componente 'input'
        painelInferior.add(outputScrollPane);
        painelInferior.add(input);
        janelaPrincipal.add(painelInferior, BorderLayout.SOUTH);

        // Empacota a janela
        janelaPrincipal.pack();

    }

    /**
     * Obtém o comando digitado pelo jogador.
     * Aguarda até que um comando seja digitado.
     *
     * @return O comando digitado pelo jogador.
     * @throws InterruptedException Se ocorrer uma interrupção enquanto a thread estiver dormindo.
     */
    public String getComando() {
        while (comando == null) {// continua executando enquanto o valor da variável 'comando' for 'null'
            try {// tentativa de pausar a execução do código por 250 milissegundos usando Thread.sleep(250).
                Thread.sleep(250);//Thread.sleep é um método estático da classe Thread
                // Que pausa a execução da thread atual por um determinado número de milissegundos.
            } catch (InterruptedException e) {
                e.printStackTrace();
                // Se durante a pausa ocorrer uma interrupção, a exceção InterruptedException será lançada.
                // printStackTrace() é um método da classe Throwable
                // Que imprime a pilha de chamadas da exceção no console.
            }
        }

        String comandoEnviado = comando;// Após sair do loop
                                        // O valor atual da variável comando é atribuído à variável local comandoEnviado.
                                        // Isso captura o comando que foi digitado durante o período de espera.
        comando = null;// O valor da variável comando é reinicializado para null
                       // Isso prepara a variável para a próxima entrada do usuário.
        return comandoEnviado;
    } 

    /**
     * Define o comando digitado pelo jogador.
     *
     * @param novoComando O novo comando digitado.
     */
    public void setComando(String novoComando) {
        comando = novoComando;
    }

    /**
     * Imprime as tarefas na área de tarefas.
     *
     * @param tarefas As tarefas a serem impressas.
     */
    public void imprimirTarefas(String tarefas){
        campoTarefas.setText(tarefas);
    }

    /**
     * Imprime os segundos na área de timer.
     *
     * @param segundos Os segundos a serem impressos.
     */
    public void imprimirSegundos(String segundos){
        campoTimer.setText(segundos);
    }

    /**
     * Adiciona uma mensagem à área de saída (`output`) da interface gráfica.
     * A mensagem é anexada ao conteúdo existente na área de saída.
     * Além disso, a posição do cursor é ajustada para o final do texto, garantindo que a mensagem recém-adicionada seja visível.
     *
     * @param mensagem A mensagem a ser adicionada à área de saída.
     */
    public void imprimir(String mensagem){
        output.append(mensagem);
        output.setCaretPosition(output.getDocument().getLength());// é utilizada para ajustar a posição do cursor na área de texto.
        //output.getDocument() - Obtém o modelo de documento (responsável por armazenar o conteúdo textual da área).
        //.getLength() - Retorna o comprimento (número de caracteres) do conteúdo atual no modelo de documento.
        //.setCaretPosition(...): Define a posição do cursor na área de texto.
        //Irá definir a posição do cursor para o final da área de texto, garantindo que,
        //ao adicionar uma nova mensagem usando output.append(mensagem), 
        //o cursor seja movido para a última posição do texto. 
    }

    /**
     * Torna a janela principal visível.
     * Este método é usado para exibir a interface gráfica do jogo.
     * A visibilidade da janela é alterada para verdadeira, tornando-a visível para o usuário.
     */
    public void exibir(){
        janelaPrincipal.setVisible(true);
    }
}