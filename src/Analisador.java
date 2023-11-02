import java.util.Scanner;

public class Analisador {
    private PalavrasComando palavrasDeComando; // guarda todas as palavras de comando validas
    private Scanner entrada; // origem da entrada de comandos

    /**
     * Cria um analisador para ler do terminal.
     */
    public Analisador() {
        palavrasDeComando = new PalavrasComando();
        entrada = new Scanner(System.in);
    }

    /**
     * @return O proximo comando do usuario
     */
    public Comando pegarComando() {
        String linha; // guardara uma linha inteira
        String palavra1 = null;
        String palavra2 = null;

        System.out.print("> "); // imprime o prompt

        linha = entrada.nextLine();

        // Tenta encontrar ate duas palavras na linha
        Scanner tokenizer = new Scanner(linha);
        if (tokenizer.hasNext()) {
            palavra1 = tokenizer.next(); // pega a primeira palavra
            if (tokenizer.hasNext()) {
                palavra2 = tokenizer.next(); // pega a segunda palavra
                // obs: nos simplesmente ignoramos o resto da linha.
            }
        }

        tokenizer.close();

        if (palavrasDeComando.ehComando(palavra1)) {
            return new Comando(palavra1, palavra2);
        } else {
            return new Comando(null, palavra2);
        }
    }

    public String getComandos() {
        return palavrasDeComando.getComandos();
    }
}
