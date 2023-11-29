/**
 * A classe Questao é responsável por representar as questões a serem respondidas pelo jogador.
 * Estas questões são sorteadas quando o jogador chega a um ambiente que contém uma missão.
 * 
 * Cada questão possui uma pergunta e uma resposta associada.
 * 
 * @author Matheus de Paula Megale
 * @version 1.0
 * @since 29/11/2023
 */

public class Questao {
    /** A pergunta associada à questão. */
    private String pergunta;

     /** A resposta correta à pergunta. */
    private String resposta;

    /**
     * Cria um novo objeto Questao.
     *
     * @param pergunta A pergunta a ser associada à questão.
     * @param resposta A resposta correta à pergunta.
     */
    public Questao(String pergunta, String resposta){
        this.pergunta = pergunta;
        this.resposta = resposta;
    }

    /**
     * Obtém a pergunta associada à questão.
     *
     * @return A pergunta associada à questão.
     */
    public String getPergunta(){
        return pergunta;
    }

    /**
     * Obtém a resposta correta à pergunta.
     *
     * @return A resposta correta à pergunta.
     */
    public String getResposta(){
        return resposta;
    }

    /**
     * Verifica se o jogador acertou a questão comparando a resposta fornecida com a resposta correta.
     *
     * @param resposta A resposta fornecida pelo jogador.
     * @return true se o jogador acertou, false se errou.
     */
    public boolean acertou(String resposta){
        if(this.resposta.equals(resposta)){ //se a resposta do jogador for igual à resposta da pergunta, então ele acertou
            return true;
        }
        return false;
    }
}
