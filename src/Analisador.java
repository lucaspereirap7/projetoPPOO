import java.util.Scanner;

/**
 * A classe Analisador é responsável por analisar os comandos fornecidos pelo usuário
 * durante a execução do jogo.
 * @author Eduardo Ruan Guimaraes Fonseca, Lucas de Oliveira Pereira, 
 *         Matheus de Paula Megale, Renan Augusto da Silva
 * @since 29/11/2023
 */

public class Analisador {
    /** Guarda todas as palavras de comando válidas. */
    private PalavrasComando palavrasDeComando; 

    /** Origem da entrada de comandos. */
    private TelaJogo telaJogo;

    /**
     * Cria um analisador para ler comandos da tela do jogo.
     *
     * @param telaJogo A tela do jogo da qual os comandos serão lidos.
     */
    public Analisador(TelaJogo telaJogo) {
        palavrasDeComando = new PalavrasComando();
        this.telaJogo = telaJogo;   
    }

    /**
     * Obtém o comando do usuário.
     *
     * @return O comando do usuário.
     */
    public Comando pegarComando() {
        String linha; // guardar uma linha inteira
        String palavra1 = null;
        String palavra2 = null;

        linha = telaJogo.getComando(); // linha recebe toda a String que o usuário digitar na interface gráfica

        // Tenta encontrar ate duas palavras na linha
        Scanner tokenizer = new Scanner(linha);
        if (tokenizer.hasNext()) {// O método hasNext() retorna true se houver mais tokens (palavra ou parte de uma linha) disponíveis e false se não houver.
            palavra1 = tokenizer.next(); // pega a primeira palavra. O método next() do Scanner lê e retorna o próximo token como uma string.
            if (tokenizer.hasNext()) {
                palavra2 = tokenizer.next(); // pega a segunda palavra
                // obs: nos simplesmente ignoramos o resto da linha.
            }
        }

        tokenizer.close();
        
        if (palavrasDeComando.ehComando(palavra1)) {// Verifica se a primeira palavra é um comando válido
            return new Comando(palavra1, palavra2);
        } else {// Caso não seja um comando válido, retorna um Comando com a primeira palavra como nula
            return new Comando(null, palavra2);
        }
    }

    /**
     * Obtém uma representação dos comandos disponíveis.
     *
     * @return Uma string contendo os comandos disponíveis.
     */
    public String getComandos() {
        return palavrasDeComando.getComandos();
    }
}
