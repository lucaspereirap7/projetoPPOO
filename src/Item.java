public class Item {
    
    private String nomeItem;
    private String tarefaQueOItemRealiza;

    public Item(String nomeItem, String tarefaQueOItemRealiza){
        this.nomeItem = nomeItem;
        this.tarefaQueOItemRealiza = tarefaQueOItemRealiza;
    }

    //Método para poder retornar o nomedoItem
    public String getNomeItem(){
        return nomeItem;
    }
    //Método para poder retornar a descricao do item
    public String getTarefaQueOItemRealiza(){
        return tarefaQueOItemRealiza;
    }
}
