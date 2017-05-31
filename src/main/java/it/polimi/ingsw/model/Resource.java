package it.polimi.ingsw.model;

/**
 * Created by lorenzo on 23/05/17.
 */
public class Resource {

    private ResourceType type;

    private Integer amount;

    public Resource(ResourceType type, Integer amount){
        this.type = type;
        this.amount = amount;
    }

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
