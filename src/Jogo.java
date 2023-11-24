import java.util.ArrayList;
import java.io.*;
import java.util.List;
import java.util.Collections;
import java.util.Random;

public class Jogo {
    private Analisador analisador;
    private Ambiente ambienteAtual;
    private ArrayList<Questao> listaDeQuestoes;
    private ArrayList<Ambiente> listaDeAmbientes;

    public Jogo() {
        listaDeAmbientes = new ArrayList<>();
        criarAmbientes();
        analisador = new Analisador();
        listaDeQuestoes = new ArrayList<>();
        lerArquivo("Perguntas.txt");
        sortearAmbientes();
        sortearCorredoresQueTeraoGuardas();
    }

    private void lerArquivo(String nomeArquivo){ //este método é responsável por fazer a leitura do arquivo e armazenar as perguntas e repostas dentro do ArrayList  
        try(BufferedReader arq = new BufferedReader(new FileReader(nomeArquivo))){
            //BufferedReader arq = new BufferedReader(new FileReader(nomeArquivo));

            String linha = arq.readLine(); //o método readLine retorna uma string com a linha lida do arquivo

            while(linha != null){
                String[] campos = linha.split(",");

                Questao questao = new Questao(campos[0], campos[1]);

                listaDeQuestoes.add(questao);

                linha = arq.readLine(); //a variável linha receberá valor null quando o arquivo chegar ao fim
            }
            //arq.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Impossível abrir o arquivo " + nomeArquivo);
        }
        catch(IOException e){
            System.out.println("Problema na leitura do arquivo " + nomeArquivo);
        }                              
    }

    public List<Questao> getQuestoes(){
        return Collections.unmodifiableList(listaDeQuestoes);
    }

    private void sortearAmbientes(){
        Random random = new Random();
        ArrayList<Integer> numerosSorteados = new ArrayList<>();
        while (numerosSorteados.size() < 5) {
            int numeroAleatorio = 2 + 2 * random.nextInt(12);
            if (!numerosSorteados.contains(numeroAleatorio)) {
                numerosSorteados.add(numeroAleatorio);
            }
        }
        for(int i = 0; i < numerosSorteados.size(); i++) {
            int num = numerosSorteados.get(i);
            for(int j = 0; j < listaDeAmbientes.size(); j++) {
                Ambiente ambiente = listaDeAmbientes.get(j);
                if(ambiente.getID() == (num)){
                    ambiente.setFoiSorteado(true);
                }
            }
        }

        for (Ambiente a : listaDeAmbientes) {
            if(a.getFoiSorteado() == true)
            System.out.println("Foi sorteado o ambiente " + a.getDescricao());
        }
    }

    private void sortearCorredoresQueTeraoGuardas(){ //apenas dois corredores (superiores e/ou inferiores) terão guardas
        ArrayList<Integer> sorteados = new ArrayList<>();
        Random random = new Random();
        while(sorteados.size() < 2){
            int numeroAleatorio = (2 * random.nextInt(3)) + 3;
            if(!sorteados.contains(numeroAleatorio)){
                sorteados.add(numeroAleatorio);
            }
        }

        for (int i = 0; i < sorteados.size(); i++) {
            int num = sorteados.get(i);
            for (int j = 0; j < listaDeAmbientes.size(); j++) {
                Ambiente ambiente = listaDeAmbientes.get(j);
                if(ambiente.getID() == (num)){
                    ambiente.setFoiSorteado(true);
                }
            }
        }

        for(Ambiente a: listaDeAmbientes){
            if(a.getFoiSorteado()){
                System.out.println("Foi sorteado o corredor: " + a.getDescricao());
            }
        }
        
    }

    private void criarAmbientes() {
        Ambiente prisao, refeitorio, escudos, navegacao, armas, o2, eletrica, administracao, garagem;
        Ambiente comunicacoes, seguranca, motorSuperior, motorInferior, reator;
        Ambiente corredorSD, corredorSE, corredorID, corredorIE, corredorPrinc, corredorAdm, corredorReator;

        // criando os ambientes
        prisao = new Sala("na prisao", 1);
        listaDeAmbientes.add(prisao);
        refeitorio = new Sala("no refeitorio", 2);
        listaDeAmbientes.add(refeitorio);
        escudos = new Sala("na sala de escudos", 4);
        listaDeAmbientes.add(escudos);
        navegacao = new Sala("na navegacao", 6);
        listaDeAmbientes.add(navegacao);
        armas = new Sala("na sala de armas", 8);
        listaDeAmbientes.add(armas);
        o2 = new Sala("na sala de oxigêniona", 10);
        listaDeAmbientes.add(o2);
        eletrica = new Sala("na sala de elétrica", 12);
        listaDeAmbientes.add(eletrica);
        administracao = new Sala("na sala de administracao", 14);
        listaDeAmbientes.add(administracao);
        garagem = new Sala("na garagem", 16);
        listaDeAmbientes.add(garagem);
        comunicacoes = new Sala("na sala de comunicacoes", 18);
        listaDeAmbientes.add(comunicacoes);
        seguranca = new Sala("na sala de seguranca", 20);
        listaDeAmbientes.add(seguranca);
        motorSuperior = new Sala("no motor superior", 22);
        listaDeAmbientes.add(motorSuperior);
        motorInferior = new Sala("no motor inferior", 24);
        listaDeAmbientes.add(motorInferior);
        reator = new Sala("no reator", 26);
        listaDeAmbientes.add(reator);
        //Corredores
        corredorSD = new Corredor("no corredor superior direito",3);
        listaDeAmbientes.add(corredorSD);
        corredorSE = new Corredor("no corredor superior esquerdo",5);
        listaDeAmbientes.add(corredorSE);
        corredorID = new Corredor("no corredor inferior direito", 7);
        listaDeAmbientes.add(corredorID);
        corredorIE = new Corredor("no corredor inferior esquerdo", 9);
        listaDeAmbientes.add(corredorIE);
        corredorPrinc = new Corredor("no corredor principal", 11);
        listaDeAmbientes.add(corredorPrinc);
        corredorAdm = new Corredor("no corredor da administracao", 13);
        listaDeAmbientes.add(corredorAdm);
        corredorReator = new Corredor("no corredor do reator", 15);
        listaDeAmbientes.add(corredorReator);
                
        //ajustando as saídas
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

    public void jogar() {
        imprimirBoasVindas();
        boolean terminado = false;
        while (!terminado) {
            Comando comando = analisador.pegarComando();
            terminado = processarComando(comando);
        }
        System.out.println("Obrigado por jogar. Ate mais!");
    }

    private void imprimirBoasVindas() {
        System.out.println();
        System.out.println("Bem-vindo ao Estelar: A Grande Fuga!");
        System.out.println("Estelar: A Grande Fuga eh um novo jogo de aventura, incrivelmente legal. Fuja enquanto há tempo!");
        System.out.println("Digite 'ajuda' se voce precisar de ajuda.");
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
                break;
            case "sair":
                querSair = sair(comando);
                break;
            case "observar":
                imprimirLocalizacaoAtual();
                break;
            case "realizar":
                fazerTarefa();
                break;
            default:
                break;
        }

        return querSair;
    }
    
    private void fazerTarefa(){
        System.out.println("Em manutenção...");
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
        } else {
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