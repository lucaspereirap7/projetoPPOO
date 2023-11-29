/**
 * A classe Programa contém o método main e é responsável por iniciar o jogo.
 * Cria um objeto da classe Jogo e chama o método iniciarJogo para começar a execução do jogo.
 * 
 * Ponto de entrada principal do programa.
 * 
 * @author Eduardo Ruan Guimaraes Fonseca, Lucas de Oliveira Pereira, 
 *         Matheus de Paula Megale, Renan Augusto da Silva
 * @version 29/11/2023
 */
public class Programa {

    public static void main(String[] args) {
        Jogo jogo = new Jogo(); // Criamos um objeto jogo
        jogo.iniciarJogo();
    }
}
