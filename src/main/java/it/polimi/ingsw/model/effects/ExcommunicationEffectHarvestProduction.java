package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.ActionType;
import it.polimi.ingsw.model.Player;

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
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setHarvestProductionDiceMalus(this.actionType, this.harvestProductionDiceMalus);
    }

    public String getDescription(){
        return "Malus on " + this.actionType.toString().toLowerCase() + " ( " + this.harvestProductionDiceMalus + " ) ";
    }

}
