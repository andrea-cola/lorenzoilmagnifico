package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.ActionType;
import it.polimi.ingsw.model.CouncilPrivilege;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;

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
     * Council privilege earned.
     */
    private CouncilPrivilege councilPrivilege;

    public EffectHarvestProductionExchange(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void setCouncilPrivilege(CouncilPrivilege privilege){
        this.councilPrivilege = privilege;
    }

    public CouncilPrivilege getCouncilPrivilege(){
        return this.councilPrivilege;
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
     * Method to execute the effect of the card.
     * @param player that takes advatange of the effect.
     */
    @Override
    public void runEffect(Player player) {

    }
}
