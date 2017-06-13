package it.polimi.ingsw.model;

/**
 *
 */
public class Action {

    private ActionType type;

    private Integer amount;

    public Integer getAmount(){
        return this.amount;
    }

    public void setAmount(Integer amount){
        this.amount = amount;
    }

    public ActionType getType(){
        return this.type;
    }

}
