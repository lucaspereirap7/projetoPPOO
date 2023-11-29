/**
 * A classe Corredor representa um tipo específico de ambiente que contém guardas.
 * Estende a classe abstrata Ambiente.
 * Cada corredor pode ou não conter um guarda, dependendo do sorteio.
 *
 * @author Matheus de Paula Megale
 * @version 23/11/2023
 */

public class Corredor extends Ambiente{
    
    /** Indica se o corredor contém um guarda. */
    private boolean temGuarda;

    /**
     * Cria um novo objeto Corredor.
     *
     * @param descricao A descrição do corredor.
     * @param ID O identificador único do corredor.
     */
    public Corredor(String descricao, int ID){
        super(descricao, ID);
        temGuarda = false;
    }

    /**
     * Define se o corredor foi sorteado para conter um guarda.
     *
     * @param foiSorteado true se o corredor foi sorteado, false caso contrário.
     */
    @Override
    public void setFoiSorteado(boolean foiSorteado){
        temGuarda = foiSorteado;
    }

    /**
     * Obtém se o corredor foi sorteado para conter um guarda.
     *
     * @return true se o corredor foi sorteado, false caso contrário.
     */
    @Override
    public boolean getFoiSorteado(){
        return temGuarda;
    }
}
