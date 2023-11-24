/**
 * Esta classe eh responsável pelos corredores que conterão guardas 
 *
 * @author Matheus de Paula Megale
 * @version 23/11/2023
 */

public class Corredor extends Ambiente{
    //atributos:
    private boolean temGuarda;

    //métodos:
    public Corredor(String descricao, int ID){
        super(descricao, ID);
        temGuarda = false;
    }

    @Override
    public void setFoiSorteado(boolean foiSorteado){
        temGuarda = foiSorteado;
    }

    @Override
    public boolean getFoiSorteado(){
        return temGuarda;
    }
}
