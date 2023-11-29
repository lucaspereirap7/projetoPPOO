/**
 * A classe Item representa um objeto que pode ser encontrado ou utilizado no jogo.
 * Cada item possui um nome e uma descrição da tarefa relacionada a ele.
 * Exemplos de itens podem incluir chaves, mapas, ou qualquer outro objeto relevante
 * para a jogabilidade.
 * 
 * @author Renan Augusto da Silva
 * @version 29/11/2023
 */
public class Item {
    
    /** O nome do item. */
    private String nomeItem;

    /** A descrição da tarefa relacionada ao item. */
    private String tarefaRelacionadaAoItem;

    /**
     * Cria um novo objeto Item.
     *
     * @param nomeItem O nome do item.
     * @param tarefaRelacionadaAoItem A descrição da tarefa relacionada ao item.
     */
    public Item(String nomeItem, String tarefaRelacionadaAoItem){
        this.nomeItem = nomeItem;
        this.tarefaRelacionadaAoItem = tarefaRelacionadaAoItem;
    }

    /**
     * Obtém o nome do item.
     *
     * @return O nome do item.
     */
    public String getNomeItem(){
        return nomeItem;
    }
    
    /**
     * Obtém a descrição da tarefa relacionada ao item.
     *
     * @return A descrição da tarefa relacionada ao item.
     */
    public String getTarefaRelacionadaAoItem(){
        return tarefaRelacionadaAoItem;
    }
}
