/**
 * Esta classe eh responsável pelas salas que conterão Itens e Tarefas a serem realizadas 
 *
 * @author Eduardo Ruan Guimarães Fonsca
 * @version 23/11/2023
 */

public class Sala extends Ambiente{
    private boolean temItem;

    public Sala(String descricao, int ID){
        super(descricao, ID);
        this.temItem = false;
    }

    @Override
    public void setFoiSorteado(boolean foiSorteado){
        this.temItem = foiSorteado;
    }

    @Override
    public boolean getFoiSorteado(){
        return temItem;
    }
}
