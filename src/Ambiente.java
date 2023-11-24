import java.util.*;

public class Ambiente {
    private String descricao;
    private HashMap<String, Ambiente> saidas;

    public Ambiente(String descricao) {
        this.descricao = descricao;
        saidas = new HashMap<String, Ambiente>();
    }

    public void ajustarSaida(String direcao, Ambiente ambiente) {
       saidas.put(direcao, ambiente);
    }

    public Ambiente getAmbiente(String direcao) {
        return saidas.get(direcao);
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

}
