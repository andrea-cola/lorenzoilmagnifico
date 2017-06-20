package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.ActionType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;

public class LEHarvestProductionSimple extends LeaderEffect{

    /**
     * Dice value to run the permanent effect.
     */
    private int diceActionValue;

    /**
     * Type of the action that activate the effect.
     */
    private ActionType actionType;

    /**
     * Class constructor.
     */
    public LEHarvestProductionSimple(){
        super.effectType = this.getClass().getSimpleName();
    }

    /**
     * Set the dice value to activate the action
     * of the permanent effect.
     * @param value of the dice.
     */
    public void setDiceActionValue(int value){
        this.diceActionValue = value;
    }

    /**
     * Get the dice value to active the action
     * of the permanent effect.
     * @return value of the dice.
     */
    public int getDiceActionValue(){
        return this.diceActionValue;
    }

    /**
     * Set the action type of the permanent effect.
     * @param type of action.
     */
    public void setActionType(ActionType type){
        this.actionType = type;
    }

    /**
     * Get the action type of the permanent effect.
     * @return type of the action.
     */
    public ActionType getActionType(){
        return this.actionType;
    }

    /**
     * Run the effect.
     * @param player
     */
    @Override
    public void runEffect(Player player){

    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        String header = this.effectType + "\n";
        String actionTypeAndValue = "Action type: " + actionType + "\nValue: " + diceActionValue + "\n";
        return new StringBuilder(header).append(actionTypeAndValue).toString();
    }

}
