package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

import java.util.Iterator;
import java.util.Map;

/**
 * This class represent the effect that exchange resources and points with more options.
 */
public class EffectHarvestProductionExchange extends Effect{

    /**
     * Dice value to run the permanent effect.
     */
    private int diceActionValue;

    /**
     * Type of the action that activate the effect.
     */
    private ActionType actionType;

    /**
     * Resources and points to pay.
     */
    private PointsAndResources[] valuableToPay;

    /**
     * Resources and points earnead for each block of resources and points payed.
     */
    private PointsAndResources[] valuableEarned;

    /**
     * Number of council privileges
     */
    private int numberOfCouncilPrivileges;

    public EffectHarvestProductionExchange(){

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
     * Set resources and points that have to be payed.
     * @param valuable of resources and points.
     */
    public void setValuableToPay(PointsAndResources[] valuable){
        this.valuableToPay = valuable;
    }

    /**
     * Get resources and points that have to be payed.
     * @return resources and points.
     */
    public PointsAndResources[] getValuableToPay(){
        return this.valuableToPay;
    }

    /**
     * Set resources and points earned.
     * @param valuable of resources and points.
     */
    public void setValuableEarned(PointsAndResources[] valuable){
        this.valuableEarned = valuable;
    }

    /**
     * Get resources and points earned.
     * @return resources and points.
     */
    public PointsAndResources[] getValuableEarned(){
        return this.valuableEarned;
    }

    /**
     * Method to run the effect of the card.
     * @param player that takes advatange of the effect.
     */
    @Override
    public void runEffect(Player player) {
        //lancio notifica con observer il cui scopo è recepire la scelta dell'utente su quale effetto eseguire (intero)
        int choice = 0;

        //PAY
        //updates player's resources
        for (Map.Entry<ResourceType, Integer> entry: this.valuableToPay[choice].getResources().entrySet()) {
            player.getPersonalBoard().getValuables().decrease(entry.getKey(), entry.getValue());
        }

        //updates player's points
        for (Map.Entry<PointType, Integer> entry: this.valuableToPay[choice].getPoints().entrySet()) {
            player.getPersonalBoard().getValuables().decrease(entry.getKey(), entry.getValue());
        }

        //EARN
        //updates player's resources
        for (Map.Entry<ResourceType, Integer> entry: this.valuableEarned[choice].getResources().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }

        //updates player's points
        for (Map.Entry<PointType, Integer> entry: this.valuableEarned[choice].getPoints().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        String description = this.effectType + "\n";
        description.concat("Action type: " + actionType + "\n Value: " + diceActionValue + "\n");
        description.concat("Resources to pay:\n");
        return description;
    }


}
