package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.ActionType;
import it.polimi.ingsw.model.Player;

/**
 * This class represents the excommunication effect for harvest/production dice value
 */
public class ExcommunicationEffectHarvestProduction extends ExcommunicationEffect {

    /**
     * Dice value
     */
    int harvestProductionDiceMalus;

    /**
     * Action type
     */
    ActionType actionType;

    /**
     * Set action type
     */
    public void setActionType(ActionType type){
        this.actionType = type;
    }

    /**
     * Get action type
     * @return
     */
    public ActionType getActionType(){
        return this.actionType;
    }

    /**
     * Class constructor
     */
    public ExcommunicationEffectHarvestProduction(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Method to run the effect of the card
     */
    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setHarvestProductionDiceMalus(this.actionType, this.harvestProductionDiceMalus);
    }

    public String getDescription(){
        return "Malus on dice value = " + this.harvestProductionDiceMalus + " on " + this.actionType.toString().toLowerCase();
    }

}
