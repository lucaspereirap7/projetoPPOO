import java.util.*;

/**
 * A classe abstrata Ambiente representa um ambiente genérico em um sistema.
 * Cada ambiente possui uma descrição, um identificador único (ID) e possíveis saídas
 * para outros ambientes em diferentes direções.
 * @author Eduardo Ruan Guimaraes Fonseca, Lucas de Oliveira Pereira, 
 *         Matheus de Paula Megale, Renan Augusto da Silva
 * @since 29/11/2023
 */


public abstract class Ambiente {
    
    /** Descrição do ambiente. */
    private String descricao;
    
    /** Identificador único do ambiente. */
    private int ID;
    
    /** HashMap das saídas para outros ambientes. */
    private HashMap<String, Ambiente> saidas;

    /**
     * Construtor para criar um novo ambiente.
     *
     * @param descricao A descrição do ambiente.
     * @param ID O identificador único do ambiente.
     */
    public Ambiente(String descricao, int ID) {
        this.descricao = descricao;
        this.ID = ID;
        saidas = new HashMap<String, Ambiente>();
    }

    /**
     * Ajusta a saída para um ambiente em uma direção específica.
     *
     * @param direcao A direção para a qual a saída está sendo ajustada.
     * @param ambiente O ambiente para o qual a saída está sendo ajustada.
     */
    public void ajustarSaida(String direcao, Ambiente ambiente) {
       saidas.put(direcao, ambiente);
    }

    /**
     * Obtém o ambiente na direção especificada.
     *
     * @param direcao A direção desejada.
     * @return O ambiente na direção especificada, ou null se não houver ambiente nessa direção.
     */
    public Ambiente getAmbiente(String direcao) {
        return saidas.get(direcao);
    }

    /**
     * Obtém o identificador único do ambiente.
     *
     * @return O identificador único do ambiente.
     */
    public int getIDAmbiente(){
        return ID;
    }

    /**
     * Obtém uma representação textual das saídas disponíveis neste ambiente.
     *
     * @return Uma string contendo as direções das saídas disponíveis.
     */
    public String getSaida(){
        String saidasAmbiente = "";
        for (String direcao : saidas.keySet()) {
            saidasAmbiente = saidasAmbiente + direcao + " ";
        }
        return saidasAmbiente; //contaneta as possíveis saídas com espaçoe entre elas
    }

    /**
     * Verifica se há uma saída na direção especificada.
     *
     * @param saida A direção a ser verificada.
     * @return true se houver uma saída nessa direção, false caso contrário.
     */
    public boolean verificaSaida(String saida) {
        if(saidas.containsKey(saida)){
            return true;
        }
        return false;
    }
    
    /**
     * Obtém a descrição do ambiente.
     *
     * @return A descrição do ambiente.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Obtém o ID do ambiente.
     *
     * @return O ID do ambiente.
     */
    public int getID(){
        return ID;
    }

    /**
     * Define se o ambiente foi sorteado. Se ele for uma instância de Sala, o sorteio
     * será para saber se ele terá alguma tarefa. Se ele for uma intância de Corredor,
     * o sorteio será para saber se ele terá algum guarda. 
     * @param foiSorteado true se o ambiente foi sorteado, false caso contrário.
     */
    public abstract void setFoiSorteado(boolean foiSorteado);

    /**
     * Obtém se o ambiente foi sorteado. Se for uma Sala e tiver tarefa, retornará true,
     * caso contrário, retornará false. Se for um Corredor e tiver guarda, retornará true,
     * caso contrário, retornará false.
     *
     * @return true se o ambiente foi sorteado, false caso contrário.
     */
    public abstract boolean getFoiSorteado();

}
