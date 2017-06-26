package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.ActionType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;

import java.util.Map;


public class ExcommunicationEffectHarvestProduction extends ExcommunicationEffect {

    int harvestProductionDiceMalus;

    ActionType actionType;

    public void setHarvestProductionDiceMalus(int malus){
        this.harvestProductionDiceMalus = malus;
    }

    public int getHarvestProductionDiceMalus(){
        return this.harvestProductionDiceMalus;
    }

    public void setActionType(ActionType type){
        this.actionType = type;
    }

    public ActionType getActionType(){
        return this.actionType;
    }

    public ExcommunicationEffectHarvestProduction(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setHarvestProductionDiceMalus(this.actionType, this.harvestProductionDiceMalus);
    }

    public String getDescription(){
        String header = this.effectType + "\n";
        String actionType = "Type:\n" + this.actionType.toString();
        String malus = "Valuables malus:\n" + this.harvestProductionDiceMalus;
        return new StringBuilder(header).append(actionType).append(malus).toString();
    }

}
