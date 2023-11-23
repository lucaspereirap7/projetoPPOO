public class Item {
    
    private int id;
    private String nomeItem;
    private String descricao;

    public Item(int id, String nomeItem, String descricao){
        id = this.id;
        nomeItem = this.nomeItem;
        descricao = this.descricao;
    }

    //Método para retornar o id do item
    public int getId(){
        return id;
    }
    //Método para poder retornar o nomedoItem
    public String getNomeItem(){
        return nomeItem;
    }
    //Método para poder retornar a descricao do item
    public String getDescricao(){
        return descricao;
    }
}
