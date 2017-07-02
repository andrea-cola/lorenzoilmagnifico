package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;
import it.polimi.ingsw.model.ResourceType;

import java.util.Map;

public class ExcommunicationEffectDevCardLoseVictoryPoints extends ExcommunicationEffect {

    private PointsAndResources cardValuables;

    public void setCardValuables(PointsAndResources valuables){
        this.cardValuables = valuables;
    }

    public PointsAndResources getCardValuables(){
        return this.cardValuables;
    }

    public ExcommunicationEffectDevCardLoseVictoryPoints(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void runEffect(Player player){
        for (Map.Entry<ResourceType, Integer> entry : this.cardValuables.getResources().entrySet()){
            player.getPersonalBoard().getExcommunicationValues().setFinalResourcesDevCardIndexMalus(entry.getKey(), entry.getValue());
        }
    }

    public String getDescription(){
        return "These resources won't assign final victory points: ( " + this.cardValuables + " ) ";
    }
}
