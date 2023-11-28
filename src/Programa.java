
public class Programa {

	public static void main(String[] args) {
		Jogo jogo = new Jogo(); //criamos um objeto jogo
		TelaJogo telaJogo = new TelaJogo();
		telaJogo.exibirJanela();
		jogo.iniciarJogo();
	}

}
