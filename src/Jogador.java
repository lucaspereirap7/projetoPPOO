import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A classe Jogador representa o jogador no jogo, incluindo o inventário de itens.
 * O jogador pode adicionar itens ao inventário e obter uma lista não modificável dos itens presentes.
 * 
 * @author Lucas de Oliveira Pereira
 * @since 29/11/2023
 */

public class Jogador{

    /** O inventário que armazena os itens do jogador. */
    private ArrayList<Item> inventario;

    /**
     * Cria um novo objeto Jogador com um inventário vazio.
     */
    public Jogador() {
        inventario = new ArrayList<Item>();
    }

    /**
     * Adiciona um item ao inventário do jogador.
     *
     * @param item O item a ser adicionado ao inventário.
     */
    public void adicionarItemInventario(Item item) {
        inventario.add(item);
    }

    /**
     * Obtém uma lista não modificável dos itens no inventário do jogador.
     *
     * @return Uma lista não modificável dos itens no inventário do jogador.
     */
    public List<Item> getArrayListInventario() {
        return Collections.unmodifiableList(inventario);
    }

}