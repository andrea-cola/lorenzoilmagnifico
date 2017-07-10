package it.polimi.ingsw.model;

/**
 * This class represents the action itself
 */
public class Action{

    /**
     * Type of action
     */
    private ActionType type;

    /**
     * Action amount
     */
    private Integer amount;

    /**
     * This method returns an amount for the action
     * @return amount
     */
    public Integer getAmount(){
        return this.amount;
    }

    /**
     * This method set an amount for the action
     * @param amount of the action
     */
    public void setAmount(Integer amount){
        this.amount = amount;
    }

    /**
     * This method return the type of an action
     * @return action type
     */
    public ActionType getType(){
        return this.type;
    }

}
