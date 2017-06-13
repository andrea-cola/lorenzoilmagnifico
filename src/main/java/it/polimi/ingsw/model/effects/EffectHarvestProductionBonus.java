package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.ActionType;
import it.polimi.ingsw.model.Player;

public class EffectHarvestProductionBonus extends Effect{

    private int diceValueBonus;

    private ActionType actionType;

    public EffectHarvestProductionBonus(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void setDiceValueBonus(int value){
        this.diceValueBonus = value;
    }

    public int getDiceValueBonus(){
        return this.diceValueBonus;
    }

    public void setActionType(ActionType actionType){
        this.actionType = actionType;
    }

    public ActionType getActionType(){
        return this.actionType;
    }

    /**
     * Method to execute the effect of the card.
     *
     * @param player
     */
    @Override
    public void runEffect(Player player) {

    }
}