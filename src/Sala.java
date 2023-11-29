/**
 * A classe Sala é responsável por representar ambientes que contêm itens e tarefas a serem realizadas.
 * Estende a classe abstrata Ambiente.
 * 
 * Cada sala possui uma descrição, um identificador único, um item associado e a informação sobre se há uma tarefa a ser realizada.
 * 
 * @author Eduardo Ruan Guimarães Fonsca
 * @version 23/11/2023
 */

public class Sala extends Ambiente{

    /** O item associado à sala. */
    private Item item;

    /** Indica se a sala tem uma tarefa a ser realizada. */
    private boolean temTarefa;

     /**
     * Cria um novo objeto Sala.
     *
     * @param descricao A descrição da sala.
     * @param ID O identificador único da sala.
     * @param item O item associado à sala.
     */
    public Sala(String descricao, int ID, Item item){
        super(descricao, ID);
        this.item = item;
        this.temTarefa = false;
    }

    /**
     * Define se a sala foi sorteada para conter uma tarefa.
     *
     * @param foiSorteado true se a sala foi sorteada, false caso contrário.
     */
    @Override
    public void setFoiSorteado(boolean foiSorteado){
        this.temTarefa = foiSorteado;
    }

    /**
     * Obtém se a sala foi sorteada para conter uma tarefa.
     *
     * @return true se a sala foi sorteada, false caso contrário.
     */
    @Override
    public boolean getFoiSorteado(){
        return temTarefa;
    }

     /**
     * Obtém o nome do item associado à sala.
     * Útil para reduzir o acoplamento em relação à classe Jogo.
     *
     * @return O nome do item associado à sala.
     */
    public String getNomeItem(){ 
        return item.getNomeItem();
    }

    /**
     * Obtém o item associado à sala.
     *
     * @return O item associado à sala.
     */
    public Item getItem(){
        return item;
    }

    /**
     * Obtém a descrição da tarefa relacionada ao item na sala.
     *
     * @return A descrição da tarefa relacionada ao item na sala.
     */
    public String getTarefaRelacionadaAoItem(){
        return item.getTarefaRelacionadaAoItem();
    }
}
