import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jogador extends Personagem {
    private ArrayList<Item> inventario;

    public Jogador(String nomePersonagem) {
        super(nomePersonagem);
        inventario = new ArrayList<Item>();
    }

    public void adicionarItemInventario(Item item) {
        inventario.add(item);
    }

    public List<Item> getArrayListInventario() {
        return Collections.unmodifiableList(inventario);
    }
    
    public String getNomePersonagem() {
        return getNomePersonagem();
    }
}