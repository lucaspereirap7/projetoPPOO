import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class TelaJogo {
    private JFrame janelaPrincipal;
    
    public TelaJogo(){
        janelaPrincipal = new JFrame("Estelar: A grande fuga!");
        inicializarJanela();
    }

    //Método inicializa o tamanho da janela
    private void inicializarJanela(){
        janelaPrincipal.setSize(1100,700);
        janelaPrincipal.setLayout(new BorderLayout());

        JPanel painelEsquerda = new JPanel();
        painelEsquerda.setLayout(new BoxLayout(painelEsquerda, BoxLayout.Y_AXIS));
        janelaPrincipal.add(painelEsquerda, BorderLayout.WEST);

        JPanel painelDireita = new JPanel();
        painelDireita.setLayout(new BoxLayout(painelDireita, BoxLayout.X_AXIS));
        janelaPrincipal.add(painelDireita, BorderLayout.EAST);
    }
    //Método utilizado para exibir
    public void exibirJanela(){
        janelaPrincipal.setVisible(true);
    }
}
