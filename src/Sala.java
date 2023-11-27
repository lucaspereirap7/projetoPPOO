/**
 * Esta classe eh responsável pelas salas que conterão Itens e Tarefas a serem realizadas 
 *
 * @author Eduardo Ruan Guimarães Fonsca
 * @version 23/11/2023
 */

public class Sala extends Ambiente{
    private Item item;
    private boolean temTarefa;

    public Sala(String descricao, int ID, Item item){
        super(descricao, ID);
        this.item = item;
        this.temTarefa = false;
    }

    @Override
    public void setFoiSorteado(boolean foiSorteado){
        this.temTarefa = foiSorteado;
    }

    @Override
    public boolean getFoiSorteado(){
        return temTarefa;
    }

    public String getNomeItem(){  //no método realizarTarefa() da classe Jogo, precisamos do nome do Item. Como não há objetos do tipo Item na classe Jogo, para reduzirmos o acoplamento, pegaremos o nome do Item por meio deste método
        return item.getNomeItem();
    }

    public Item getItem(){
        return item;
    }
}
