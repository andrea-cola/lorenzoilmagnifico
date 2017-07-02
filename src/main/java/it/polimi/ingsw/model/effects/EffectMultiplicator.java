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
        super.setEffectType(this.getClass().getSimpleName());
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
     * Method to run the effect of the card.
     * @param player that takes advantage of the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {

        //Set multiplicator value
        int multiplicatorValue = 0;

        //check if the multiplicator object is a card or a resource/point
        if (this.cardOrResources){
            multiplicatorValue = player.getPersonalBoard().getCards(this.cardColorRequisite).size();
            System.out.println("MOLTIPLICA PER: " + multiplicatorValue);
        }else{
            for(Map.Entry<ResourceType, Integer> entry: this.valuable.getResources().entrySet())
                if(entry.getValue() != 0)
                    multiplicatorValue = entry.getValue();
            for(Map.Entry<PointType, Integer> entry: this.valuable.getPoints().entrySet())
                if(entry.getValue() != 0)
                    multiplicatorValue = entry.getValue();
        }

        //run effect
        //updates player's resources
        for (Map.Entry<ResourceType, Integer> entry: this.valuable.getResources().entrySet()) {
            //excommunication effect
            player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalResourcesMalus().get(entry.getKey()));
            //normal effect
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue() * multiplicatorValue);
        }

        //updates player's points
        for (Map.Entry<PointType, Integer> entry: this.valuable.getPoints().entrySet()){
            //excommunication effect
            player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalPointsMalus().get(entry.getKey()));
            //normal effect
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue() * multiplicatorValue);
        }
    }


    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if(actionType != null)
            stringBuilder.append(actionType.toString() + " with dice value = " + diceActionValue + ". Earn " + valuable.toString() + " ");
        if(cardOrResources)
            stringBuilder.append("for any card of these color: ( " + cardColorRequisite + " ) ");
        else
            stringBuilder.append("for any of this resource: ( " + valuableRequisite.toString() + " ) ");
        return stringBuilder.toString();
    }
}
