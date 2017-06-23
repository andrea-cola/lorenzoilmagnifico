package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.InformationCallback;

import java.util.Map;

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
     * Method to run the effect of the card.
     * @param player that takes advantage of the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {

        //Set multiplicator value
        Integer multiplicatorValue = new Integer(0);

        //check if the multiplicator object is a card or a resource/point
        if (this.cardOrResources){
            //card
            switch (this.cardColorRequisite){
                case GREEN:
                    multiplicatorValue = player.getPersonalBoard().getTerritoryCards().size();
                    break;
                case YELLOW:
                    multiplicatorValue = player.getPersonalBoard().getBuildingCards().size();
                    break;
                case BLUE:
                    multiplicatorValue = player.getPersonalBoard().getCharacterCards().size();
                    break;
                case PURPLE:
                    multiplicatorValue = player.getPersonalBoard().getVentureCards().size();
                    break;
            }
        }else{
            //resource

        }

        //run effect
        //updates player's resources
        for (Map.Entry<ResourceType, Integer> entry: this.valuable.getResources().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue() * multiplicatorValue);
        }

        //updates player's points
        for (Map.Entry<PointType, Integer> entry: this.valuable.getPoints().entrySet()){
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue() * multiplicatorValue);
        }

    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.effectType + ": ");
        if(actionType != null) {
            stringBuilder.append(actionType.toString() + " ");
            stringBuilder.append("dice: " + diceActionValue + " ");
        }
        if(cardOrResources)
            stringBuilder.append("card color: " + cardColorRequisite + " ");
        else
            stringBuilder.append("resources to pay: " + valuableRequisite.toString());
        return stringBuilder.toString();
    }
}
