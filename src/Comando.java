public class Comando
{
    private String palavraDeComando;
    private String segundaPalavra;

    public  Comando(String primeiraPalavra, String segundaPalavra)
    {
        palavraDeComando = primeiraPalavra;
        this.segundaPalavra = segundaPalavra;
    }

    public String getPalavraDeComando()
    {
        return palavraDeComando;
    }

    public String getSegundaPalavra()
    {
        return segundaPalavra;
    }

    public boolean ehDesconhecido()
    {
        return (palavraDeComando == null);
    }

    public boolean temSegundaPalavra()
    {
        return (segundaPalavra != null);
    }
}

