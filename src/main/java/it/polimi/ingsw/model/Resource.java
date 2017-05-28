package it.polimi.ingsw.model;

/**
 * Created by lorenzo on 23/05/17.
 */
public class Resource {

    private enum ResourceType{
        WOOD,
        STONE,
        COIN,
        SERVANT
    }

    private ResourceType type;

    private Integer amount;

    public void setAmount(Integer amount){
        this.amount = amount;
    }

    public Integer getAmount(){
        return this.amount;
    }

    public ResourceType getType(){
        return this.type;
    }
}
