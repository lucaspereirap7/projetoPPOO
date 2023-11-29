import java.util.ArrayList;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A classe Jogo representa o ambiente e as regras do jogo "Estelar: A Grande Fuga".
 * Ela controla a lógica do jogo, incluindo a criação de ambientes, a leitura de perguntas
 * de um arquivo, a inicialização de temporizadores e a interação com o jogador.
 * @author Eduardo Ruan Guimaraes Fonseca, Lucas de Oliveira Pereira, 
 *         Matheus de Paula Megale, Renan Augusto da Silva
 * @version 1.0
 * @since 29/11/2023
 */

public class Jogo {

    /**
     * Objeto responsável por analisar os comandos inseridos pelo jogador.
     */
    private Analisador analisador;
    
    /**
     * Ambiente atual em que o jogador se encontra.
     */
    private Ambiente ambienteAtual;

    /**
     * Lista de questões que podem ser sorteadas durante o jogo.
     */
    private ArrayList<Questao> listaDeQuestoes;

    /**
     * Lista de salas presentes no jogo.
     */
    private ArrayList<Sala> listaDeSalas;

    /**
     * Lista de corredores presentes no jogo.
     */
    private ArrayList<Corredor> listaDeCorredores;
    
    /**
     * Indica se o jogo foi terminado.
     */
    private boolean terminado;

    /**
     * Objeto que representa o jogador.
     */
    private Jogador jogador;

    /**
     * Objeto Timer para gerenciar o tempo do jogo.
     */
    private Timer timer;

     /**
     * Tempo, em segundos, que o jogador tem para ganhar o jogo.
     */
    private int segundos;

    /**
     * String que contém as tarefas a serem realizadas pelo jogador.
     */
    private String tarefas;

    /**
     * Interface gráfica do jogo.
     */
    private TelaJogo telaJogo;


    /**
     * Construtor da classe Jogo.
     * Inicializa as variáveis do jogo, cria ambientes, lê perguntas de um arquivo,
     * sorteia salas que terão itens e tarefas, sorteia corredores que terão guardas,
     * inicializa o temporizador e imprime na interface.
     */
    public Jogo() {
        terminado = false; // A variável "terminado" determina quando o programa encerrará. Como o jogo está começando, ela é inicializa com false
        
        telaJogo = new TelaJogo(); // Criando um objeto TelaJogo
        telaJogo.exibir(); // Chama o método exibir(), que exibe a interfa gráfica

        tarefas = "\n\nTAREFAS A REALIZAR: \n\n"; // Inicializa a String 'tarefas' com um título e depois concatena com as descrições 

        segundos = 5; // Quantos segundos o jogador tera para ganhar

        timer = new Timer(); // Criando um objeto Timer
        analisador = new Analisador(telaJogo); // Criando um objeto Analisador
        jogador = new Jogador(); // Criando um objeto Jogador
        
        listaDeSalas = new ArrayList<>(); // Criando um ArrayList de Salas
        listaDeCorredores = new ArrayList<>(); // Criando um ArrayList de Corredores
        listaDeQuestoes = new ArrayList<>(); // Criando um ArrayList de Questoes

        criarAmbientes(); // Criando os ambientes do jogo
        lerArquivo("Perguntas.txt"); // Fazendo a leitura do arquivo que contém as questoes
        sortearSalaQueTeraoItens(); // Sorteando as salas que terao tarefas a serem realizadas
        sortearCorredoresQueTeraoGuardas(); // Sorteando os corredores que terao guardas (só pode ser os superiores )
        imprimirNaInterface(); // Método que imprime informações iniciais na interface gráfica.
        

    }
    
    /**
     * Método responsável por imprimir informações iniciais na interface gráfica.
     */
    private void imprimirNaInterface(){
        telaJogo.imprimirTarefas(tarefas); // Imprime as tarefas a serem realizadas para o usuário na interface gráfica
        telaJogo.imprimirSegundos("\n\n\n\n\n        TEMPO RESTANTE: " + formatarTempo(segundos)); // imprimi o tempo que o usuário tem para jogar
    }
    
    /**
     * Inicia o jogo, exibindo as boas-vindas na interface gráfica.
     * Aguarda que o jogador digite "iniciar" para iniciar o cronômetro e a fase de jogo.
     * Durante a espera, exibe mensagens na interface informando as instruções ao jogador.
     * Se o jogador digitar "iniciar", exibe uma mensagem adicional e chama o método jogar() para iniciar a fase de jogo.
     * Se o jogador digitar algo diferente de "iniciar", exibe uma mensagem de digitação inválida.
     */
    public void iniciarJogo(){ 
        imprimirBoasVindas(); // Imprime as boas vindas para o usuário na interce gráfica
        String resposta = "";
        telaJogo.imprimir("\n");
        telaJogo.imprimir("Digite 'iniciar' para que o cronometro inicialize" + "\n");
        while(resposta != "iniciar" && segundos > 0){ // Aguarda que o usuário digite "iniciar" para que o jogo comece.
            resposta = telaJogo.getComando();
            if(resposta.equals("iniciar")){
                telaJogo.imprimir("Digite 'ajuda' se voce precisar de ajuda. \n");
                jogar(); // Chama o método jogar() para iniciar a fase de jogo.
            }else {
                telaJogo.imprimir("Digitacao invalida" + "\n"); // Caso o usuário digite qualquer coisa que não seja "iniciar"
            }
        }
    }
    
    /**
     * Inicia um temporizador (timer) que executa uma tarefa (task) em intervalos regulares.
     * Utiliza o método scheduleAtFixedRate do objeto Timer para agendar uma tarefa de execução repetida em intervalos fixos.
     * A tarefa é definida como uma instância anônima de TimerTask, sobrescrevendo o método run().
     * O método run() será executado a cada intervalo definido pelo scheduleAtFixedRate.
     * Verifica se ainda há tempo restante (segundos > 0); caso positivo, decrementa os segundos e imprime o tempo restante na interface gráfica.
     * Se o tempo acabar, imprime a mensagem de derrota e encerra a execução da tarefa agendada, impedindo que seja executada novamente.
     * Executa a cada 1000 milissegundos (1 segundo).
     */
    public void iniciarTimer() { // Este método inicia um temporizador (timer) que executa uma tarefa (task) em intervalos regulares.
        timer.scheduleAtFixedRate(new TimerTask() { // O método scheduleAtFixedRate do objeto Timer.
                                                    // Agenda uma tarefa para execução repetida em intervalos fixos.
                                                    // A tarefa é definida como uma instância anônima de TimerTask.
            @Override // Método run() está sobrescrevendo um método da classe anônima TimerTask.
            public void run() { // Este método será executado a cada intervalo definido pelo scheduleAtFixedRate.
                if (segundos > 0) { // Verififica se ainda há tempo restante, caso tenha o decrementa .
                    segundos--;
                    telaJogo.imprimirSegundos("\n\n\n\n\n        TEMPO RESTANTE: " + formatarTempo(segundos)); //formata o tempo em minutos e segundos
                } else {
                    telaJogo.imprimirSegundos("\n\n\n\n\n       VOCE PERDEU!!");
                    parar(); // Encerra a execução da tarefa agendada e impede que ela seja executada novamente.
                }
            }
        }, 0, 1000); // Executar a cada 1000 milissegundos (1 segundo).
    }
    
    /**
     * Para o temporizador, encerrando a execução da tarefa agendada.
     */
    private void parar() {
        timer.cancel(); // Chama o método cancel() do objeto Timer, encerrando a execução da tarefa agendada
    }

    /**
     * Formata o tempo em minutos e segundos.
     * @param segundos Tempo em segundos.
     * @return Tempo formatado (MM:SS).
     */
    public String formatarTempo(int segundos) { // Formata o tempo para ser impresso
        int minutos = segundos / 60;
        int segundosRestantes = segundos % 60;
        return String.format("%02d:%02d", minutos, segundosRestantes);
    }
    
    /**
     * Lê as perguntas de um arquivo e armazena em uma lista de questões.
     * @param nomeArquivo Nome do arquivo que contém as perguntas.
     * @throws FileNotFoundException Se o arquivo com o nome especificado não for encontrado.
     * @throws IOException Se houver um problema na leitura do arquivo.
     */
    private void lerArquivo(String nomeArquivo) { // Este método é responsável por fazer a leitura do arquivo e
                                                  // armazenar as perguntas e repostas dentro do ArrayList
        try (BufferedReader arq = new BufferedReader(new FileReader(nomeArquivo))) {
            
            String linha = arq.readLine(); // O método readLine retorna uma string com a linha lida do arquivo

            while (linha != null) {
                String[] campos = linha.split(","); // Divide a linha usando ',' como delimitador.

                Questao questao = new Questao(campos[0], campos[1]); // Cria um objeto Questao com os campos da linha e adiciona à lista de questões.

                listaDeQuestoes.add(questao);

                linha = arq.readLine(); // A variável linha receberá valor null quando o arquivo chegar ao fim
            }

        } catch (FileNotFoundException e) {
            System.out.println("Impossível abrir o arquivo " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Problema na leitura do arquivo " + nomeArquivo);
        }
    }
    
    /**
     * Sorteia 5 salas que terão Itens e Tarefas a serem realizadas.
     * Utiliza o método de sorteio para obter IDs de salas no intervalo [2, 26].
     * Os IDs sorteados correspondem às salas que terão Itens e Tarefas.
     * Apenas salas com IDs pares no intervalo [2, 26] podem ser sorteadas.
     * Os IDs sorteados têm seus atributos "foiSorteado" definidos como true, indicando que terão Itens e Tarefas.
     * O método também chama o método tarefasRealizar() para verificar as tarefas a serem realizadas em cada sala sorteada.
     */
    private void sortearSalaQueTeraoItens() { // Sorteia 5 ambientes que terão Itens e Tarefas a serem realizadas
        Random random = new Random();
        ArrayList<Integer> numerosSorteados = new ArrayList<>(); // ArrayList para guardar os números sorteados
        while (numerosSorteados.size() < 5) { // Sorteia números pares dentro do intervalo [2, 26]
            int numeroAleatorio = 2 + 2 * random.nextInt(12); // Multiplica por 2 para ter certeza que serão números pares, somo 2 para ter certeza que o menor número possível seja 2
            if (!numerosSorteados.contains(numeroAleatorio)) { //Impede que tenha números repetidos no ArrayList
                numerosSorteados.add(numeroAleatorio); // Adiciona o número sorteado no ArrayList
            }
        }
        for (int i = 0; i < numerosSorteados.size(); i++) { // Percorro o ArrayList de números dos sorteados
            int num = numerosSorteados.get(i); // Pego um número do ArrayList de números dos sorteados
            for (int j = 0; j < listaDeSalas.size(); j++) { //  o ArrayList de salas existentes
                Ambiente ambiente = listaDeSalas.get(j); // Pego uma sala do ArrayList de salas existentes
                if (ambiente.getID() == (num)) { // Verifico se o ID da minha sala é igual ao um número sorteado
                    ambiente.setFoiSorteado(true); // Caso ele seja igual, chamo um método que "seta" como true aquela sala, dizendo que ela foi sorteada
                    tarefasRealizar(ambiente); // Chamo método tarefasRealizar() para verificar qual tarefa eu tenho naquela sala
                }
            }
        }
    }
    
    /**
     * Método responsável por concatenar as tarefas que serão realizadas pelo usuário.
     * @param ambiente Ambiente que contém a tarefa a ser realizada.
     */
    private void tarefasRealizar(Ambiente ambiente) {// Método responsável por concatenar as tarefas que serão realizadas pelo usuário.
    
        // Converte o ambiente para o tipo Sala, assumindo que o ambiente seja realmente do tipo Sala.
        Sala sala = (Sala) ambiente;
    
        // Concatena a tarefa relacionada ao item presente na sala ao conjunto de tarefas.
        tarefas += "\n" + sala.getTarefaRelacionadaAoItem() + "\n";
    }
    

    /**
     * Sorteia dois corredores (superiores e/ou inferiores) que terão guardas.
     * Utiliza o método de sorteio para obter IDs de corredores no intervalo [3, 9].
     * Os IDs sorteados correspondem aos corredores que podem ter guardas.
     * Apenas corredores com IDs ímpares no intervalo [3, 9] podem ser sorteados.
     * Os corredores sorteados têm seus atributos "foiSorteado" definidos como true, indicando que terão guardas.
     */
    private void sortearCorredoresQueTeraoGuardas() { // Apenas dois corredores (superiores e/ou inferiores) terão
                                                      // guardas
        ArrayList<Integer> sorteados = new ArrayList<>(); // ArrayList para guardar os valores dos ID's sorteados
        Random random = new Random();
        while (sorteados.size() < 2) { // Este while irá sortear dois valores ímpares no intervalo [3, 9] 
                                       // que correspondem aos ID's dos corredores que podem ter guardas
            int numeroAleatorio = (2 * random.nextInt(3)) + 3; // Neste trecho, apenas os valores ímpares no intervalo [3, 9] podem ser sorteados
            if (!sorteados.contains(numeroAleatorio)) { // Verificação para saber se o número sorteado já foi sorteado antes (para não adicionarmos valores iguais no Array de sorteados)
                sorteados.add(numeroAleatorio); // Adiciona o valor sorteado no ArrayList de sorteados
            }
        }

        for (int i = 0; i < sorteados.size(); i++) { // Percorre o ArrayList de sorteados 
            int num = sorteados.get(i); // Criamos uma variável para guardar o valor da posição "i" do ArrayList de sorteados
            for (int j = 0; j < listaDeCorredores.size(); j++) { // Percorre o ArrayList de Corredores
                Ambiente ambiente = listaDeCorredores.get(j); // Criamos uma variável do tipo estático Ambiente para guardar o objeto da posição "j" do ArrayList de Corredores
                if (ambiente.getID() == (num)) { // Se o ID da variável Ambiente for igual ao valor do numero sorteado, então esse ambiente, que é um corredor, terá um guarda
                    ambiente.setFoiSorteado(true); // Define o atributo booleano "temGuarda" do objeto como true
                }
            }
        }
    }

    /**
     * Cria os ambientes do jogo, incluindo salas e corredores, ajustando suas conexões e itens.
     * As salas já serão instanciadas com seus respectivos itens e tarefas relacionadas com esses itens
     * Mas tanto os itens quanto as tarefas só serão disponivéis para os ambientes que forem sorteados
     * As salas e corredores são instanciados e adicionados às listas correspondentes.
     * As saídas entre ambientes são ajustadas para conectar os diferentes espaços no jogo.
     * O ambiente inicial é definido como a sala de prisão.
     */
    private void criarAmbientes() {
        Sala prisao, refeitorio, escudos, navegacao, armas, o2, eletrica, administracao, garagem;
        Sala comunicacoes, seguranca, motorSuperior, motorInferior, reator;
        Corredor corredorSD, corredorSE, corredorID, corredorIE, corredorPrinc, corredorAdm, corredorReator;

        // Instanciando e adicionando as salas
        prisao = new Sala("na prisao", 1, null);
        listaDeSalas.add(prisao);
        refeitorio = new Sala("no refeitorio", 2, new Item("Mantimentos", "Pegue mantimentos no refetorio"));
        listaDeSalas.add(refeitorio);
        escudos = new Sala("na sala de escudos", 4, new Item("Colete", "Pegue o colete nos escudos"));
        listaDeSalas.add(escudos);
        navegacao = new Sala("na navegacao", 6, new Item("Mapa", "Pegue o mapa na navegacao"));
        listaDeSalas.add(navegacao);
        armas = new Sala("na sala de armas", 8, new Item("Arma", "Pegue uma arma na sala de armas"));
        listaDeSalas.add(armas);
        o2 = new Sala("na sala de oxigênio", 10, new Item("Galao de oxigênio", "Pegue o galao de oxigênio na o2"));
        listaDeSalas.add(o2);
        eletrica = new Sala("na sala de elétrica", 12, new Item("Bateria", "Pegue a bateria na eletrica"));
        listaDeSalas.add(eletrica);
        administracao = new Sala("na sala de administracao", 14, new Item("Codigo de segurança", "Pegue o codigo de segurança na administracao"));
        listaDeSalas.add(administracao);
        garagem = new Sala("na garagem", 16, new Item("Chave da nave", "Pegue a chave da nave na garagem"));
        listaDeSalas.add(garagem);
        comunicacoes = new Sala("na sala de comunicacoes", 18, new Item("Radio", "Pegue o radio nas comunicacoes"));
        listaDeSalas.add(comunicacoes);
        seguranca = new Sala("na sala de seguranca", 20, new Item("Lanterna", "Pegue a lanterna na sala de seguranca"));
        listaDeSalas.add(seguranca);
        motorSuperior = new Sala("no motor superior", 22, new Item("Gasolina", "Pegue a gasolina no motor superior"));
        listaDeSalas.add(motorSuperior);
        motorInferior = new Sala("no motor inferior", 24, new Item("Chave de fenda", "Pegue uma chave de fenda no motor inferior"));
        listaDeSalas.add(motorInferior);
        reator = new Sala("no reator", 26, new Item("Motor", "Pegue o motor no reator"));
        listaDeSalas.add(reator);

        // Instanciando e adicionando os corredores
        corredorSD = new Corredor("no corredor superior direito", 3);
        listaDeCorredores.add(corredorSD);
        corredorSE = new Corredor("no corredor superior esquerdo", 5);
        listaDeCorredores.add(corredorSE);
        corredorID = new Corredor("no corredor inferior direito", 7);
        listaDeCorredores.add(corredorID);
        corredorIE = new Corredor("no corredor inferior esquerdo", 9);
        listaDeCorredores.add(corredorIE);
        corredorPrinc = new Corredor("no corredor principal", 11);
        listaDeCorredores.add(corredorPrinc);
        corredorAdm = new Corredor("no corredor da administracao", 13);
        listaDeCorredores.add(corredorAdm);
        corredorReator = new Corredor("no corredor do reator", 15);
        listaDeCorredores.add(corredorReator);

        // Ajustando as saídas entre os ambientes
        prisao.ajustarSaida("refeitorio", refeitorio);

        refeitorio.ajustarSaida("esquerda", corredorSE);
        refeitorio.ajustarSaida("direita", corredorSD);
        refeitorio.ajustarSaida("baixo", corredorAdm);
        refeitorio.ajustarSaida("prisao", prisao);

        escudos.ajustarSaida("direita", corredorSE);
        escudos.ajustarSaida("baixo", corredorPrinc);

        navegacao.ajustarSaida("direita", corredorPrinc);
        navegacao.ajustarSaida("reator", reator);

        armas.ajustarSaida("cima", corredorPrinc);
        armas.ajustarSaida("direita", corredorIE);

        eletrica.ajustarSaida("baixo", corredorIE);
        eletrica.ajustarSaida("administracao", administracao);

        garagem.ajustarSaida("esquerda", corredorIE);
        garagem.ajustarSaida("direita", corredorID);
        garagem.ajustarSaida("cima", corredorAdm);
        garagem.ajustarSaida("comunicacoes", comunicacoes);

        motorSuperior.ajustarSaida("esquerda", corredorSD);
        motorSuperior.ajustarSaida("baixo", corredorReator);

        motorInferior.ajustarSaida("cima", corredorReator);
        motorInferior.ajustarSaida("esquerda", corredorID);

        reator.ajustarSaida("esquerda", corredorReator);
        reator.ajustarSaida("navegacao", navegacao);

        seguranca.ajustarSaida("direita", corredorReator);

        comunicacoes.ajustarSaida("baixo", corredorID);
        comunicacoes.ajustarSaida("garagem", garagem);

        o2.ajustarSaida("esquerda", corredorPrinc);

        administracao.ajustarSaida("direita", corredorAdm);
        administracao.ajustarSaida("eletrica", eletrica);

        corredorSD.ajustarSaida("esquerda", refeitorio);
        corredorSD.ajustarSaida("direita", motorSuperior);

        corredorID.ajustarSaida("direita", motorInferior);
        corredorID.ajustarSaida("cima", comunicacoes);
        corredorID.ajustarSaida("esquerda", garagem);

        corredorSE.ajustarSaida("esquerda", escudos);
        corredorSE.ajustarSaida("direita", refeitorio);

        corredorIE.ajustarSaida("direita", garagem);
        corredorIE.ajustarSaida("esquerda", armas);
        corredorIE.ajustarSaida("cima", eletrica);

        corredorReator.ajustarSaida("esquerda", seguranca);
        corredorReator.ajustarSaida("direita", reator);
        corredorReator.ajustarSaida("cima", motorSuperior);
        corredorReator.ajustarSaida("baixo", motorInferior);

        corredorPrinc.ajustarSaida("cima", escudos);
        corredorPrinc.ajustarSaida("baixo", armas);
        corredorPrinc.ajustarSaida("esquerda", navegacao);
        corredorPrinc.ajustarSaida("direita", o2);

        corredorAdm.ajustarSaida("baixo", garagem);
        corredorAdm.ajustarSaida("cima", refeitorio);
        corredorAdm.ajustarSaida("esquerda", administracao);

        ambienteAtual = prisao; // Define a sala de prisão como o ambiente inicial do jogo
    }

    /**
     * Inicia a fase de jogo, iniciando o temporizador e executando um loop até que o jogo seja concluído.
     * Dentro do loop, obtém o comando do jogador através do Analisador, processa o comando e verifica se o jogo deve ser encerrado.
     * O jogo é encerrado se o jogador realizar todas as tarefas, se o tempo se esgotar ou se for explicitamente terminado.
     * Após a conclusão do jogo, exibe uma mensagem de agradecimento ao jogador.
     */
    public void jogar() {
        iniciarTimer(); // Inicia o temporizador

        while (!terminado) { // Loop principal do jogo
            Comando comando = analisador.pegarComando(); // Obtém o comando do jogador
            terminado = processarComando(comando); // Processa o comando e verifica se o jogo deve ser encerrado

            if(segundos == 0){ // Verifica se o tempo esgotou
                terminado = true;
            }
        }
         telaJogo.imprimir("Obrigado por jogar. Ate mais!" + "\n"); // Exibe mensagem de agradecimento ao jogador
    }

    /**
     * Imprime uma mensagem de boas-vindas ao jogador, fornecendo informações sobre o jogo e sua premissa.
     * Chama o método para imprimir a localização atual do jogador.
     */
    public void imprimirBoasVindas() {
        telaJogo.imprimir("\n" + "Olá, jogador" + "\n");
        telaJogo.imprimir("\n");
        telaJogo.imprimir("Bem-vindo ao Estelar: A Grande Fuga!" + "\n");
        telaJogo.imprimir("Estelar: A Grande Fuga eh um novo jogo de aventura, incrivelmente legal. Fuja enquanto há tempo!");
        telaJogo.imprimir("\n");

        imprimirLocalizacaoAtual(); // Chama o método para imprimir a localização atual do jogador.
    }


    /**
     * Este método é responsável por processar o comando recebido e executar a ação correspondente,
     * baseando-se na palavra-chave do comando. Ele lida com diferentes comandos, como ajuda, ir para um ambiente,
     * sair do jogo, observar a localização atual, realizar uma tarefa, espiar, verificar o inventário e pegar a mininave.
     *
     * @param comando O comando a ser processado.
     * @return Retorna true se o jogador deseja sair do jogo; caso contrário, retorna false.
     */
    private boolean processarComando(Comando comando) {
        boolean querSair = false;

        if (comando.ehDesconhecido()) { // Verifica se o comando é desconhecido
            telaJogo.imprimir("Eu nao entendi o que voce disse..." + "\n");
            return false;
        }
        String palavraDeComando = comando.getPalavraDeComando();
        switch (palavraDeComando) { // Estrutura de seleção switch para lidar com diferentes comandos
            case "ajuda":
                imprimirAjuda();
                break;
            case "ir":
                irParaAmbiente(comando);
                querSair = terminado;
                break;
            case "sair":
                querSair = sair(comando);
                break;
            case "observar":
                imprimirLocalizacaoAtual();
                break;
            case "realizar":
                realizarTarefa();
                querSair = terminado;
                break;
            case "espiar":
                espiar(comando);
                break;
            case "inventario":
                olharInventario();
            break;
            case "mininave":
                querSair = pegarMininave();
            break;
            default:
                break;
        }

        return querSair;
    }

    /**
     * Realiza a ação de pegar a mininave, verificando se o jogador está na sala correta
     * e se possui todos os itens necessários para concluir o jogo. Caso o jogador tenha
     * todos os itens, imprime uma mensagem de vitória e encerra o jogo. Se o jogador não
     * estiver na sala correta ou não tiver todos os itens, imprime mensagens indicando
     * a situação e continua o jogo.
     *
    * @return Verdadeiro se o jogador concluiu o jogo (pegou a mininave), falso caso contrário.
    */
    private boolean pegarMininave(){
        if(ambienteAtual.getID() == 16){// Verifica se o jogador está na garagem (ID 16)
            if(jogador.getArrayListInventario().size() < 5){// Verifica se o jogador possui todos os itens necessários no inventário
                telaJogo.imprimir("Voce ainda nao tem todos os itens necessários para fugir!" + "\n");
                return false;
            }else{
                telaJogo.imprimir("Parabens, YOUUUU WON!!!" + "\n"); // Mensagem para indicar que o jogador ganhou 
                segundos = 0; // Define segundos como 0 para encerrar o jogo
                return true;
            }
        }else{
            telaJogo.imprimir("A mininave esta na garagem!" + "\n");
            return false;
        }
    }

    /**
     * Exibe o conteúdo do inventário do jogador.
     * Se o inventário estiver vazio, imprime uma mensagem indicando isso.
     * Caso contrário, mostra a quantidade de itens no inventário e lista os itens.
     */
    private void olharInventario(){
        if(jogador.getArrayListInventario().size() == 0){ // Verifica se o inventário do jogador está vazio
           telaJogo.imprimir("Inventario vazio" + "\n");
        } else{
            // Imprime a quantidade de itens no inventário e lista os itens
            telaJogo.imprimir("Voce possui " + jogador.getArrayListInventario().size() +" no inventario, aqui esta:" + "\n");
            for(Item i : jogador.getArrayListInventario()){
                telaJogo.imprimir(i.getNomeItem() + "  ");
            }
            telaJogo.imprimir("\n");
        }
    }

    /**
     * Espia em uma direção específica a partir do ambiente atual.
     * Se a direção fornecida existir como saída no ambiente atual,
     * verifica se há alienígenas naquele ambiente e imprime a informação correspondente.
     * Caso contrário, imprime uma mensagem indicando que a direção é inexistente.
     *
 * @param comando O comando que contém a direção para espiar.
 */
    private void espiar(Comando comando){
        // Verifica se o comando possui uma segunda palavra (direção)
        if (comando.temSegundaPalavra()) {
            String direcao = comando.getSegundaPalavra();
            if(ambienteAtual.verificaSaida(direcao)){ // Verifica se a direção fornecida existe como saída no ambiente atual
                verificarAlienigenasAmbiente(direcao);
            } else{
                telaJogo.imprimir("Direção inexistente" + "\n");
            }
        } else {
            telaJogo.imprimir("Espiar para onde?" + "\n"); // Caso não haja segunda palavra (direção) no comando
        }
    }

    /**
     * Realiza a tarefa associada ao ambiente atual, se houver.
     * Se o ambiente atual for um corredor, exibe uma mensagem informando que não há tarefas nos corredores.
     * Se o ambiente atual não tiver sido sorteado, informa que não há tarefa nesta sala.
     * Caso contrário, exibe informações sobre a tarefa, incluindo a pergunta associada e solicita a resposta do jogador.
     * Se o jogador responder corretamente, recebe o item associado à tarefa.
     * Caso contrário, o jogo é encerrado.
     */
    private void realizarTarefa() {
        if (ambienteAtual instanceof Corredor) { // Verifica se o ambiente atual é um corredor
            telaJogo.imprimir("Não há tarefas nos corredores!" + "\n");
        } else if (!ambienteAtual.getFoiSorteado()) { // Verifica se o ambiente atual não foi sorteado
            telaJogo.imprimir("Não há tarefa nesta sala!" + "\n");
        } else {
            Sala salaAtual = (Sala) ambienteAtual;

            telaJogo.imprimir("Voce possui uma tarefa neste ambiente!" + "\n");
            telaJogo.imprimir("Ao realizar essa tarefa voce coletara o item: " + salaAtual.getNomeItem() + "\n");
            telaJogo.imprimir("A tarefa eh o seguinte, voce tera que responder corretamente a seguinte questao: " + "\n");

            Questao tarefa = sortearQuestao(); // Sorteia uma questão

            telaJogo.imprimir(tarefa.getPergunta() + "\n"); // Exibe a pergunta ao jogador

            String respostaTarefa = telaJogo.getComando(); // Obtém a resposta do jogador

            if (tarefa.acertou(respostaTarefa)) { // Se o jogador acertar a resposta
                telaJogo.imprimir("Parabens voce acertou! Receba o item " + salaAtual.getNomeItem() + "\n"); 
                ambienteAtual.setFoiSorteado(false); // A tarefa não estará mais disponível quando o jogador retornar a este ambiente
                jogador.adicionarItemInventario(salaAtual.getItem()); // Adiciona o item ao inventário do jogador
            } else {// Se o jogador errar a resposta
                telaJogo.imprimir("Voce perdeu, a resposta esta incorreta!" + "\n");
                acabarJogo(true); // Encerra o jogo
            }
        }
    }

    /**
     * Verifica a presença de alienígenas no ambiente para a direção específica indicada.
     * Se o ambiente para o qual o usuário está prestes a se mover for um corredor, verifica se esse corredor
     * possui alienígenas sorteado. Exibe mensagens indicando a presença ou ausência de alienígenas no ambiente.
     *
     * @param direcao A direção para a qual o usuário está prestes a se mover.
     */
    private void verificarAlienigenasAmbiente(String direcao) {
        // Verifica se o ambiente em que o usuário está prestes a entrar é um corredor.
        if (ambienteAtual.getAmbiente(direcao) instanceof Corredor) {
            // Verifica se o corredor em que o usuário está prestes a entrar possui alienígena.
            if (((Corredor) ambienteAtual.getAmbiente(direcao)).getFoiSorteado()) {
                telaJogo.imprimir("Possui alienigena no ambiente, cuidado!" + "\n");
            } else {
                telaJogo.imprimir("Tudo tranquilo, não possui alienígena no ambiente, pode seguir em frente!" + "\n");
            }
        } else {
            telaJogo.imprimir("Zero perigos por aqui..." + "\n");
        }
    }

   /**
     * Finaliza o jogo conforme a condição especificada.
     *
     * @param finalizar Indica se o jogo deve ser finalizado (true) ou não (false).
     *
     * Este método é chamado no método realizarTarefa() e no irParaAmbiente(Comando comando). Sua função é definir o valor do
     * atributo booleano 'terminado' como true, encerrando o jogo. Quando o método realizarTarefa() é concluído, o controle
     * retorna ao método processarComando(). Se o jogador errar a resposta da pergunta, 'terminado' é definido como true,
     * e no método processarComando(), uma variável local recebe o valor de 'terminado', que neste caso será true, retornando
     * ao método jogar() encerrando o jogo. Já no método irParaAmbiente(Comando comando), caso o usuário entre em corredor que
     * possui guarda ele perde, uma variável local de processarComando() recebe o valor de 'terminado', que neste caso será
     * true, voltando ao método jogar(), encerrando o jogo.
     */
    private void acabarJogo(boolean finalizar) { 
        this.terminado = finalizar;
    }

    /**
     * Sorteia uma questão aleatória da lista de questões.
     *
     * @return Uma questão aleatória.
     *
     * Este método utiliza um gerador de números aleatórios para obter um índice aleatório na lista de questões.
     * A questão correspondente a esse índice é recuperada, removida da lista para evitar repetições durante a execução do jogo
     * e retornada como resultado do método.
     */
    private Questao sortearQuestao() {
        Random questaoAleatoria = new Random();
        int numeroAleatorio = questaoAleatoria.nextInt(listaDeQuestoes.size() - 1);
        Questao questao = listaDeQuestoes.get(numeroAleatorio); // Variável temporária do tipo Questao
        removerQuestao(numeroAleatorio); // Garante que não teremos questões repetidas durante a execução do jogo
        return questao;
    }

    /**
     * Remove a questão da lista de questões com base no índice fornecido.
     *
     * @param numeroAleatorio O índice da questão a ser removida.
     *
     * Este método remove a questão da lista de questões com base no índice fornecido.
     * Ele é usado para garantir que não haja repetições de questões durante a execução do jogo.
     */
    private void removerQuestao(int numeroAleatorio) {
        listaDeQuestoes.remove(numeroAleatorio);
    }

    /**
     * Imprime mensagens de ajuda para o jogador.
     * Informa sobre o objetivo do jogo, fornece dicas e lista os comandos disponíveis.
     */
    private void imprimirAjuda() {
        // Informa ao jogador sobre o objetivo do jogo
        telaJogo.imprimir("Voce esta tentando fugir desses terríveis alienígenas!" + "\n");
        telaJogo.imprimir("Fuja enquanto há tempo!" + "\n");
        telaJogo.imprimir("\n");

        // Fornece informações sobre os comandos disponíveis
        telaJogo.imprimir("Suas palavras de comando sao:" + "\n");
        telaJogo.imprimir(analisador.getComandos() + "\n");
    }

    /**
     * Move o jogador para o próximo ambiente com base no comando fornecido.
     *
     * @param comando O comando inserido pelo jogador para indicar a direção desejada.
     */
    private void irParaAmbiente(Comando comando) {
        if (!comando.temSegundaPalavra()) { // Verifica se o comando possui uma segunda palavra (direção)
            // Se não há segunda palavra, o jogador não especificou a direção
            telaJogo.imprimir("Ir pra onde?" + "\n");
            return;
        }
        // Obtém a direção do comando
        String direcao = comando.getSegundaPalavra();

        // Obtém o próximo ambiente com base na direção especificada
        Ambiente proximoAmbiente = null;
        if (direcao != "") { // Verifica se há passagem para o próximo ambiente
            proximoAmbiente = ambienteAtual.getAmbiente(direcao);
        }

        if (proximoAmbiente == null) {
            telaJogo.imprimir("Nao ha passagem!" + "\n");
        }else if (proximoAmbiente instanceof Corredor) { // Se o próximo ambiente for um corredor, verifica se o jogador teve sorte
            if (((Corredor) proximoAmbiente).getFoiSorteado()) {
                telaJogo.imprimir("Voce perdeu! Um alienígena te matou!" + "\n");
                acabarJogo(true);
                segundos = 0;
            }else{
                ambienteAtual = proximoAmbiente;
                imprimirLocalizacaoAtual();
            }
        }else{ // Move o jogador para o próximo ambiente
            ambienteAtual = proximoAmbiente;
            imprimirLocalizacaoAtual(); // Imprime a localizacao atual
        }
    }

    private void imprimirLocalizacaoAtual() {

        telaJogo.imprimir("Voce esta " + ambienteAtual.getDescricao() + "\n");
        telaJogo.imprimir("Saidas: " + "\n");
        telaJogo.imprimir(ambienteAtual.getSaida() + "\n");
        telaJogo.imprimir("\n");
    }

    private boolean sair(Comando comando) {
        if (comando.temSegundaPalavra()) {
            telaJogo.imprimir("Sair o que?" + "\n");
            return false;
        } else {
            segundos = 0;
            return true;
        }
    }
    
}