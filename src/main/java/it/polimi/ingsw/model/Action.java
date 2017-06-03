package it.polimi.ingsw.model;

/**
 * Created by lorenzo on 23/05/17.
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
