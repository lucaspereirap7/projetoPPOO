/**
 * A classe Comando representa um comando inserido pelo usuário.
 * Cada comando é composto por uma palavra de comando e, opcionalmente,
 * uma segunda palavra.
 */

public class Comando
{
     /** A palavra de comando inserida pelo usuário. */
    private String palavraDeComando;

    /** A segunda palavra associada ao comando (pode ser nula). */
    private String segundaPalavra;

    /**
     * Cria um novo objeto Comando.
     *
     * @param primeiraPalavra A palavra de comando fornecida pelo usuário.
     * @param segundaPalavra A segunda palavra associada ao comando (pode ser nula).
     */
    public  Comando(String primeiraPalavra, String segundaPalavra)
    {
        palavraDeComando = primeiraPalavra;
        this.segundaPalavra = segundaPalavra;
    }

    /**
     * Obtém a palavra de comando.
     *
     * @return A palavra de comando.
     */
    public String getPalavraDeComando()
    {
        return palavraDeComando;
    }

    /**
     * Obtém a segunda palavra associada ao comando.
     *
     * @return A segunda palavra associada ao comando (pode ser nula).
     */
    public String getSegundaPalavra()
    {
        return segundaPalavra;
    }

    /**
     * Verifica se a palavra de comando é desconhecida.
     *
     * @return true se a palavra de comando for desconhecida, false caso contrário.
     */
    public boolean ehDesconhecido()
    {
        return (palavraDeComando == null);
    }

    /**
     * Verifica se o comando possui uma segunda palavra.
     *
     * @return true se houver uma segunda palavra, false caso contrário.
     */
    public boolean temSegundaPalavra()
    {
        return (segundaPalavra != null);
    }
}

