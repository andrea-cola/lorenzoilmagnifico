package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.ActionType;
import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;

import java.util.Iterator;
import java.util.Map;

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
     *Method to run the effect of the card.
     * @param player
     */
    @Override
    public void runEffect(Player player) {
        player.getPersonalBoard().setHarvestProductionDiceValueBonus(this.actionType, diceValueBonus);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        String description = this.effectType + "\n";
        description.concat("Action type: " + actionType + "\n Value: " + diceValueBonus + "\n");
        return description;
    }
}