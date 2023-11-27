import java.util.ArrayList;
import java.io.*;
//import java.util.List;
//import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Jogo {
    private Analisador analisador;
    private Ambiente ambienteAtual;
    private ArrayList<Questao> listaDeQuestoes;
    private ArrayList<Sala> listaDeSalas;
    private ArrayList<Corredor> listaDeCorredores;
    private Scanner entrada;
    private boolean terminado;
    private Jogador jogador;
    private Timer timer;
    private int segundos;

    public Jogo() {
        terminado = false; //a variável "terminado" determina quando o programa encerrará. como o jogo está começando, ela é inicializa com false
        
        segundos = 10; //quantos segundos o jogador tera para ganhar
        timer = new Timer();

        analisador = new Analisador(); //criando um objeto analisador

        entrada = new Scanner(System.in);
        jogador = new Jogador(lerNomeJogador()); //criando um objeto jogador
        
        listaDeSalas = new ArrayList<>(); //criando um ArrayList de Salas
        listaDeCorredores = new ArrayList<>(); //criando um ArrayList de Corredores
        listaDeQuestoes = new ArrayList<>(); //criando um ArrayList de Questoes

        criarAmbientes(); //criando os ambientes do jogo
        lerArquivo("Perguntas.txt"); //fazendo a leitura do arquivo que contém as questoes
        sortearSalaQueTeraoItens(); //sorteando as salas que terao tarefas a serem realizadas
        sortearCorredoresQueTeraoGuardas(); //sorteando os corredores que terao guardas (só pode ser os superiores )
        //imprimirConteudo();//retirar

    }

    public void iniciarJogo(){ 
        imprimirBoasVindas();
        String resposta = "";
        System.out.println();
        System.out.println("Digite 'iniciar' para que o cronometro inicialize");
        while(resposta != "iniciar"){
            resposta = entrada.nextLine();
            if(resposta.equals("iniciar")){
                System.out.println("Digite 'ajuda' se voce precisar de ajuda. \n");
                jogar();
            }else {
                System.out.println("Digitacao invalida");
            }
        }
    }

    private void iniciarTimer() {
        timer.scheduleAtFixedRate(new TimerTask() { //um objeto Timer agenda uma tarefa (TimerTask)
                                                    //que implementa o método run() da interface TimerTask
            @Override //indica que está substituindo o método run() da classe TimerTask
            public void run() { //este método será executado a cada intervalo definido pelo scheduleAtFixedRate
                if (segundos > 0) {
                    segundos--;
                    System.out.println(formatarTempo(segundos)); //formata o tempo em minutos e segundos
                } else {
                    parar();
                    System.out.println("O SEU TEMPO ACABOU!!!!!!!");
                    System.exit(0);
                }
            }
        }, 0, 1000); // Executar a cada 1000 milissegundos (1 segundo)
    }

    private void parar() {
        timer.cancel(); //chama o método cancel() do objeto Timer, encerrando a execução da tarefa agendada
    }

    private String formatarTempo(int segundos) {
        int minutos = segundos / 60;
        int segundosRestantes = segundos % 60;
        return String.format("%02d:%02d", minutos, segundosRestantes);
    }
    

    private String lerNomeJogador(){
        System.out.print("Para iniciarmos, por favor digite seu nome: ");
        return entrada.nextLine();
    }


    private void lerArquivo(String nomeArquivo) { // este método é responsável por fazer a leitura do arquivo e
                                                  // armazenar as perguntas e repostas dentro do ArrayList
        try (BufferedReader arq = new BufferedReader(new FileReader(nomeArquivo))) {
            // BufferedReader arq = new BufferedReader(new FileReader(nomeArquivo));

            String linha = arq.readLine(); // o método readLine retorna uma string com a linha lida do arquivo

            while (linha != null) {
                String[] campos = linha.split(",");

                Questao questao = new Questao(campos[0], campos[1]);

                listaDeQuestoes.add(questao);

                linha = arq.readLine(); // a variável linha receberá valor null quando o arquivo chegar ao fim
            }
            // arq.close();
        } catch (FileNotFoundException e) {
            System.out.println("Impossível abrir o arquivo " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Problema na leitura do arquivo " + nomeArquivo);
        }
    }

    private void imprimirConteudo() {
        for (Questao questao : listaDeQuestoes) {
            System.out.println("Pergunta: " + questao.getPergunta() + ", Resposta: " + questao.getResposta());
        }
    }

    /*
    public List<Questao> getQuestoes() {
        return Collections.unmodifiableList(listaDeQuestoes);
    }
    */

    private void sortearSalaQueTeraoItens() { // sorteia 5 ambientes que terão Itens e Tarefas a serem realizadas
        Random random = new Random();
        ArrayList<Integer> numerosSorteados = new ArrayList<>();
        while (numerosSorteados.size() < 5) { // sorteia números pares dentro do intervalo [2, 26]
            int numeroAleatorio = 2 + 2 * random.nextInt(12);
            if (!numerosSorteados.contains(numeroAleatorio)) {
                numerosSorteados.add(numeroAleatorio);
            }
        }
        for (int i = 0; i < numerosSorteados.size(); i++) {
            int num = numerosSorteados.get(i);
            for (int j = 0; j < listaDeSalas.size(); j++) {
                Ambiente ambiente = listaDeSalas.get(j);
                if (ambiente.getID() == (num)) {
                    ambiente.setFoiSorteado(true);
                }
            }
        }
        // retirar esse for depois
        for (Ambiente a : listaDeSalas) {
            if (a.getFoiSorteado() == true)
                System.out.println("Foi sorteado o ambiente " + a.getDescricao());
        }
    }

    private void sortearCorredoresQueTeraoGuardas() { // apenas dois corredores (superiores e/ou inferiores) terão
                                                      // guardas
        ArrayList<Integer> sorteados = new ArrayList<>();
        Random random = new Random();
        while (sorteados.size() < 2) { // este while irá sortear dois valores (3, 5, 7 ou 9) que correspondem aos ID's
                                       // dos corredores que podem ter guardas
            int numeroAleatorio = (2 * random.nextInt(3)) + 3;
            if (!sorteados.contains(numeroAleatorio)) {
                sorteados.add(numeroAleatorio);
            }
        }

        for (int i = 0; i < sorteados.size(); i++) {
            int num = sorteados.get(i);
            for (int j = 0; j < listaDeCorredores.size(); j++) {
                Ambiente ambiente = listaDeCorredores.get(j);
                if (ambiente.getID() == (num)) {
                    ambiente.setFoiSorteado(true);
                }
            }
        }
        // retirar esse for depois
        for (Corredor a : listaDeCorredores) {
            if (a.getFoiSorteado()) {
                System.out.println("Foi sorteado o corredor e que tem guarda: " + a.getDescricao());
            }
        }

    }

    private void criarAmbientes() {
        Sala prisao, refeitorio, escudos, navegacao, armas, o2, eletrica, administracao, garagem;
        Sala comunicacoes, seguranca, motorSuperior, motorInferior, reator;
        Corredor corredorSD, corredorSE, corredorID, corredorIE, corredorPrinc, corredorAdm, corredorReator;

        // Salas
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
        o2 = new Sala("na sala de oxigênio", 10, new Item("Galao de oxigênio", "Pegue o galao de oxigêio na o2"));
        listaDeSalas.add(o2);
        eletrica = new Sala("na sala de elétrica", 12, new Item("Bateria", "Colete uma bateria na eletrica"));
        listaDeSalas.add(eletrica);
        administracao = new Sala("na sala de administracao", 14, new Item("Codigo de segurança", "Pegue o codigo de segurança na"));
        listaDeSalas.add(administracao);
        garagem = new Sala("na garagem", 16, new Item("Chave da nave", "Pegue a chave da nave na garagem"));
        listaDeSalas.add(garagem);
        comunicacoes = new Sala("na sala de comunicacoes", 18, new Item("Radio", "Peg"));
        listaDeSalas.add(comunicacoes);
        seguranca = new Sala("na sala de seguranca", 20, new Item("Lanterna", "Colete uma lanterna na sala de seguranca"));
        listaDeSalas.add(seguranca);
        motorSuperior = new Sala("no motor superior", 22, new Item("Gasolina", "Pegue a gasolina no motor superior"));
        listaDeSalas.add(motorSuperior);
        motorInferior = new Sala("no motor inferior", 24, new Item("Chave de fenda", "Pegue uma chave de fenda no motor inferior"));
        listaDeSalas.add(motorInferior);
        reator = new Sala("no reator", 26, new Item("Motor", "Pegue o motor no reator"));
        listaDeSalas.add(reator);

        // Corredores
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

        // ajustando as saídas
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

        ambienteAtual = prisao; // o jogo comeca dentro da prisão
    }

    private void jogar() {
        //imprimirBoasVindas();
        // boolean terminado = false;
        iniciarTimer();//iniciar timer
        while (!terminado) {
            //iniciarTimer();//iniciar timer
            Comando comando = analisador.pegarComando();
            terminado = processarComando(comando);
        }
        System.out.println("Obrigado por jogar. Ate mais!");
    }

    private void imprimirBoasVindas() {
        System.out.println("Olá, " + jogador.getNome());
        System.out.println();
        System.out.println("Bem-vindo ao Estelar: A Grande Fuga!");
        System.out.println(
                "Estelar: A Grande Fuga eh um novo jogo de aventura, incrivelmente legal. Fuja enquanto há tempo!");
        //System.out.println("Digite 'ajuda' se voce precisar de ajuda.");
        System.out.println();

        imprimirLocalizacaoAtual();
    }

    private boolean processarComando(Comando comando) {
        boolean querSair = false;

        if (comando.ehDesconhecido()) {
            System.out.println("Eu nao entendi o que voce disse...");
            return false;
        }
        String palavraDeComando = comando.getPalavraDeComando();
        switch (palavraDeComando) {
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

    private boolean pegarMininave(){
        if(ambienteAtual.getID() == 16){
            if(jogador.getArrayListInventario().size() < 5){
                System.out.println("Voce ainda nao tem todos os itens necessários para fugir!");
                return false;
            }else{
                System.out.println("Parabens, " + jogador.getNome() + ", YOUUUU WON!!!");
                segundos = 0;
                return true;
            }
        }else{
            System.out.println("A mininave esta na garagem!");
            return false;
        }
    }

    private void olharInventario(){
        if(jogador.getArrayListInventario().size() == 0){
            System.out.println("Inventario vazio");
        } else{
            System.out.println("Voce possui " + jogador.getArrayListInventario().size() +" no inventario, aqui esta:");
            for(Item i : jogador.getArrayListInventario()){
                System.out.print(i.getNomeItem() + "  ");
            }
            System.out.println();
        }
    }
    private void espiar(Comando comando){
        if (comando.temSegundaPalavra()) {
            String direcao = comando.getSegundaPalavra();
            if(ambienteAtual.verificaSaida(direcao)){
                verificarAlienigenasAmbiente(direcao);
            } else{
                System.out.println("Direção inexistente");
            }
        } else {
            System.out.println("Espiar para onde?");
        }
    }

    private void realizarTarefa() {
        if (ambienteAtual instanceof Corredor) { // os corredores nunca possuem tarefas, logo, se o jogador selecionar a
                                                 // palavra de comando "realizar" estando em um corredor, a seguinte
                                                 // mensagem aparecerá:
            System.out.println("Não há tarefas nos corredores!");
        } else if (!ambienteAtual.getFoiSorteado()) {
            System.out.println("Não há tarefa nesta sala!");
        } else {
            Sala salaAtual = (Sala) ambienteAtual;
            System.out.println("Voce possui uma tarefa neste ambiente!");
            System.out.println("Ao realizar essa tarefa voce coletara o item: " + salaAtual.getNomeItem());
            System.out.println("A tarefa eh o seguinte, voce tera que responder corretamente a seguinte questao: ");
            Questao tarefa = sortearQuestao();
            System.out.println(tarefa.getPergunta());
            String respostaTarefa = entrada.nextLine();// resposta do usuario
            if (tarefa.acertou(respostaTarefa)) {
                System.out.println("Parabens voce acertou! Receba o item " + salaAtual.getNomeItem());
                ambienteAtual.setFoiSorteado(false);// isso serve para que, quando o jogador retornar a este ambiente,
                jogador.adicionarItemInventario(salaAtual.getItem());                                    // sua tarefa não estará mais disponível
            } else {
                System.out.println("Voce perdeu, a resposta esta incorreta!");
                acabarJogo(true);
            }
        }
    }

    // Método criado para verificar se tem alienigena no ambiente
    private void verificarAlienigenasAmbiente(String direcao) {
        // Verifica se o ambiente em que o usuário a entrar é um corredor..
        if (ambienteAtual.getAmbiente(direcao) instanceof Corredor) {
            // Verifica se o ambiente em que o usuário vai entrar possuem alienigina.
            if (((Corredor) ambienteAtual.getAmbiente(direcao)).getFoiSorteado()) {
                System.out.println("Possui alienigena no ambiente, cuidado!");
            } else {
                System.out.println("Tudo tranquilo, não possui alienígena no ambiente, pode seguir em frente!");
            }
        } else {
            System.out.println("Zero perigos por aqui...");
        }
    }

    private void acabarJogo(boolean finalizar) {
        this.terminado = finalizar;
    }

    private Questao sortearQuestao() {
        Random questaoAleatoria = new Random();
        int numeroAleatorio = questaoAleatoria.nextInt(listaDeQuestoes.size() - 1);
        Questao questao = listaDeQuestoes.get(numeroAleatorio); // variavel temporaria do tipo Questao
        removerQuestao(numeroAleatorio); // garante que não teremos questoes repetidas durante a execucao do jogo
        return questao;
    }

    private void removerQuestao(int numeroAleatorio) {
        listaDeQuestoes.remove(numeroAleatorio);
    }

    private void imprimirAjuda() {
        System.out.println("Voce esta tentando fugir desses terríveis alienígenas!");
        System.out.println("Fuja enquanto há tempo!");
        System.out.println();
        System.out.println("Suas palavras de comando sao:");
        System.out.println(analisador.getComandos());
    }

    private void irParaAmbiente(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            // se nao ha segunda palavra, nao sabemos pra onde ir...
            System.out.println("Ir pra onde?");
            return;
        }

        String direcao = comando.getSegundaPalavra();

        Ambiente proximoAmbiente = null;
        if (direcao != "") {
            proximoAmbiente = ambienteAtual.getAmbiente(direcao);
        }

        if (proximoAmbiente == null) {
            System.out.println("Nao ha passagem!");
        }else if (proximoAmbiente instanceof Corredor) {
            if (((Corredor) proximoAmbiente).getFoiSorteado()) { // para saber se tem guarda
                System.out.println("Voce perdeu! Um alienígena te matou!");
                acabarJogo(true);
            }else{
                ambienteAtual = proximoAmbiente;
                imprimirLocalizacaoAtual();
            }
        }else{
            ambienteAtual = proximoAmbiente;
            imprimirLocalizacaoAtual();
        }
    }

    private void imprimirLocalizacaoAtual() {
        System.out.println("Voce esta " + ambienteAtual.getDescricao());
        System.out.print("Saidas: ");
        System.out.print(ambienteAtual.getSaida());
        System.out.println();
    }

    private boolean sair(Comando comando) {
        if (comando.temSegundaPalavra()) {
            System.out.println("Sair o que?");
            return false;
        } else {
            return true;
        }
    }
    
}