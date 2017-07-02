package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.ActionType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.InformationCallback;

public class EffectHarvestProductionBonus extends Effect{

    private int diceValueBonus;

    private ActionType actionType;

    /**
     * Class constructor.
     */
    public EffectHarvestProductionBonus(){
        super.setEffectType(this.getClass().getSimpleName());
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
     *Method to run the effect of the card.
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        int newValue = player.getPersonalBoard().getHarvestProductionDiceValueBonus().get(this.actionType) + this.diceValueBonus;
        player.getPersonalBoard().setHarvestProductionDiceValueBonus(this.actionType, newValue);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return new StringBuilder().append(actionType.toString() + "( bonus: " + diceValueBonus + " )").toString();
    }
}