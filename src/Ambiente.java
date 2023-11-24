import java.util.*;

public abstract class Ambiente {

    private String descricao;
    private int ID;

    private HashMap<String, Ambiente> saidas;

    public Ambiente(String descricao, int ID) {
        this.descricao = descricao;
        this.ID = ID;
        saidas = new HashMap<String, Ambiente>();
    }

    public void ajustarSaida(String direcao, Ambiente ambiente) {
       saidas.put(direcao, ambiente);
    }

    public Ambiente getAmbiente(String direcao) {
        return saidas.get(direcao);
    }

    public int getIDAmbiente(){
        return ID;
    }

    public String getSaida(){
        String saidasAmbiente = "";
        for (String direcao : saidas.keySet()) {
            saidasAmbiente = saidasAmbiente + direcao + " ";
        }
        return saidasAmbiente;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public int getID(){
        return ID;
    }

    public abstract void setFoiSorteado(boolean foiSorteado);
    public abstract boolean getFoiSorteado();

}
