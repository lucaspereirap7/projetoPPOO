public class Jogo {
    private Analisador analisador;
    private Ambiente ambienteAtual;

    public Jogo() {
        criarAmbientes();
        analisador = new Analisador();
    }

    private void criarAmbientes() {
        Ambiente prisao, refeitorio, escudos, navegacao, armas, o2, eletrica, administracao, garagem;
        Ambiente comunicacoes, seguranca, motorSuperior, motorInferior, reator;
        Ambiente corredorSD, corredorSE, corredorID, corredorIE, corredorPrinc, corredorAdm, corredorReator;

        // cria os ambientes
        prisao = new Ambiente("na prisao");
        refeitorio = new Ambiente("no refeitorio");
        escudos = new Ambiente("na sala de escudos");
        navegacao = new Ambiente("na navegacao");
        armas = new Ambiente("na sala de armas");
        o2 = new Ambiente("na sala de oxigêniona");
        eletrica = new Ambiente("na sala de elétrica");
        administracao = new Ambiente("na sala de administracao");
        garagem = new Ambiente("na garagem");
        comunicacoes = new Ambiente("na sala de comunicacoes");
        seguranca = new Ambiente("na sala de seguranca");
        motorSuperior = new Ambiente("no motor superior");
        motorInferior = new Ambiente("no motor inferior");
        reator = new Ambiente("no reator");
        //Corredores
        corredorSD = new Ambiente("no corredor superior direito");
        corredorSE = new Ambiente("no corredor superior esquerdo");
        corredorID = new Ambiente("no corredor inferior direito");
        corredorIE = new Ambiente("no corredor inferior esquerdo");
        corredorPrinc = new Ambiente("no corredor principal");
        corredorAdm = new Ambiente("no corredor da administracao");
        corredorReator = new Ambiente("no corredor do reator");
                
        //ajustando saídas
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
        System.out.println("Voce esta perdido. Voce esta sozinho. Voce caminha");
        System.out.println("pela universidade.");
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