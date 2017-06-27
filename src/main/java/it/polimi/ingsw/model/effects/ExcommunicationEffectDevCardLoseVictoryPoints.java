package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;
import it.polimi.ingsw.model.ResourceType;

import java.awt.*;
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
        super.effectType = this.getClass().getSimpleName();
    }

    public void runEffect(Player player){
        for (Map.Entry<ResourceType, Integer> entry : this.cardValuables.getResources().entrySet()){
            player.getPersonalBoard().getExcommunicationValues().setFinalResourcesDevCardIndexMalus(entry.getKey(), entry.getValue());
        }
    }

    public String getDescription(){
        String header = this.effectType + "\n";
        String cardValuables = "Valuables to lose index:\n" + this.cardValuables;
        return new StringBuilder(header).append(cardValuables).toString();
    }
}
