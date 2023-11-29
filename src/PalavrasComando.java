/**
 * A classe PalavrasComando é responsável por armazenar e verificar palavras de comando válidas.
 * Contém um vetor constante de palavras de comando e métodos para obter a lista de comandos
 * e verificar se uma dada string é uma palavra de comando válida.
 * 
 * Exemplos de comandos: "ir", "sair", "ajuda", "observar", "realizar", "espiar", "inventario", "mininave".
 * 
 * @author Eduardo Ruan Guimaraes Fonseca, Lucas de Oliveira Pereira, 
 *         Matheus de Paula Megale, Renan Augusto da Silva
 * @version 29/11/2023
 */

public class PalavrasComando
{
    /** Um vetor constante que guarda todas as palavras de comandos válidas. */
    private static final String[] comandosValidos = {
        "ir", "sair", "ajuda", "observar", "realizar", "espiar", "inventario", "mininave"
    };

    /**
     * Construtor padrão da classe PalavrasComando.
     * Inicializa as palavras de comando (não há ações específicas neste momento).
     */
    public PalavrasComando()
    {
        // nada a fazer no momento...
    }

    /**
     * Obtém uma string contendo todos os comandos válidos separados por espaços.
     *
     * @return Uma string contendo todos os comandos válidos.
     */
    public String getComandos(){
        String comandos = "";
        for(String comando : comandosValidos){
            comandos = comandos + comando + " ";
        }
        return comandos;
    }

    /**
     * Verifica se uma dada string é uma palavra de comando válida.
     *
     * @param umaString A string a ser verificada como comando válido.
     * @return true se a string fornecida é um comando válido, false se não é.
     */
    public boolean ehComando(String umaString)
    {
        for(int i = 0; i < comandosValidos.length; i++) {
            if(comandosValidos[i].equals(umaString))
                return true;
        }
        // se chegamos aqui, a string nao foi encontrada nos comandos.
        return false;
    }
}
