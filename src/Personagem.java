public abstract class Personagem {

    private String nomePersonagem;

    public Personagem(String nomePersonagem) {
        this.nomePersonagem = nomePersonagem;
    }

    public String getNome(){
        return nomePersonagem;
    }
}
