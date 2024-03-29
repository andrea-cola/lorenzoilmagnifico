package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.ActionType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.InformationCallback;

/**
 * This class represent the effect that gives a bonus for the harvest production
 */
public class EffectHarvestProductionBonus extends Effect{

    /**
     * Dice value bonus
     */
    private int diceValueBonus;

    /**
     * The action type
     */
    private ActionType actionType;

    /**
     * Class constructor.
     */
    public EffectHarvestProductionBonus(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Sets the action type
     * @param actionType
     */
    public void setActionType(ActionType actionType){
        this.actionType = actionType;
    }

    /**
     * Gets the action type
     * @return
     */
    public ActionType getActionType(){
        return this.actionType;
    }

    /**
     *Method to run the effect of the card.
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        int newValue = player.getPersonalBoard().getHarvestProductionDiceValueBonus().get(this.actionType) + this.diceValueBonus
                - player.getPersonalBoard().getExcommunicationValues().getHarvestProductionDiceMalus().get(actionType);
        player.getPersonalBoard().setHarvestProductionDiceValueBonus(this.actionType, newValue);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return new StringBuilder(actionType.toString().toLowerCase() + " has permanent bonus on dice value = " + diceValueBonus).toString();
    }
}