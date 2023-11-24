/**
 * Esta classe é responsável pelas questões a serem respondidas pelo jogador.
 * Elas serão sorteadas quando o jogador chegar ao ambiente que contém uma missão. 
 * 
 * Mais detalhes, se necessário.
 * 
 * @author Matheus de Paula Megale
 * @version 1.0
 * @since 22/11/2023
 */

public class Questao {
    private String pergunta;
    private String resposta;

    public Questao(String pergunta, String resposta){
        this.pergunta = pergunta;
        this.resposta = resposta;
    }

    public String getPergunta(){
        return pergunta;
    }

    public String getResposta(){
        return resposta;
    }

    public boolean acertou(String resposta){
        if(this.resposta.equals(resposta)){ //se a resposta do jogador for igual à resposta da pergunta, então ele acertou
            return true;
        }
        return false;
    }
}
