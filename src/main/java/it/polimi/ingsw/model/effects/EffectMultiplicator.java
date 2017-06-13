package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.ActionType;
import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;

/**
 * This class represent a multiplicator effect. This type of method allow the player
 * to get resources or points for each card of a specified color.
 */
public class EffectMultiplicator extends Effect{

    /**
     * Dice value to run the permanent effect.
     */
    private int diceActionValue;

    /**
     * Type of the action that activate the effect.
     */
    private ActionType actionType;

    /**
     * Points and resources that could be earned each time the effect respect the requisite.
     */
    private PointsAndResources valuable;

    /**
     * Color requisite.
     */
    private DevelopmentCardColor cardColorRequisite;

    /**
     * Points and resources requested to activate the effect.
     */
    private PointsAndResources valuableRequisite;

    /**
     * This flag indicates if the effect works on card color or resources amount.
     */
    private boolean cardOrResources;

    /**
     * Class constructor.
     */
    public EffectMultiplicator(){
        super.effectType = this.getClass().getSimpleName();
    }

    /**
     * Set the flag.
     * @param flag for card or resources.
     */
    public void setCardOrResources(boolean flag){
        this.cardOrResources = flag;
    }

    /**
     * Get the flag.
     * @return boolean flag.
     */
    public boolean getCardOrABoolean(){
        return this.cardOrResources;
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
     * Set resources and points of the permanent effect.
     * @param valuable resources and points.
     */
    public void setValuable(PointsAndResources valuable){
        this.valuable = valuable;
    }

    /**
     * Get resources and points of the permanent effect.
     * @return resources and points.
     */
    public PointsAndResources getValuable(){
        return this.valuable;
    }

    /**
     * Set the color of the card.
     * @param color requisite.
     */
    public void setCardColorRequisite(DevelopmentCardColor color){
        this.cardColorRequisite = color;
    }

    /**
     * Get the color of the card.
     * @return
     */
    public DevelopmentCardColor getCardColorRequisite(){
        return this.cardColorRequisite;
    }

    /**
     * Set resources and points of the permanent effect.
     * @param valuable resources and points.
     */
    public void setValuableRequisite(PointsAndResources valuable){
        this.valuableRequisite = valuable;
    }

    /**
     * Get resources and points of the permanent effect.
     * @return resources and points.
     */
    public PointsAndResources getValuableRequisite(){
        return this.valuableRequisite;
    }


    /**
     * Method to execute the effect of the card.
     * @param player that takes advantage of the effect.
     */
    @Override
    public void runEffect(Player player) {

    }
}
