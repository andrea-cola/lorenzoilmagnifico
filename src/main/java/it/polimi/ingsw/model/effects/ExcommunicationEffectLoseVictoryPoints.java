package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointType;
import it.polimi.ingsw.model.PointsAndResources;
import it.polimi.ingsw.model.ResourceType;

import java.awt.*;
import java.util.Map;


public class ExcommunicationEffectLoseVictoryPoints extends ExcommunicationEffect{

    /**
     * For every resource owned, you lose one victory point
     */
    private PointsAndResources valuablesOwnedIndex;

    public void setValuablesOwnedIndex(PointsAndResources valuablesOwnedIndex){
        this.valuablesOwnedIndex = valuablesOwnedIndex;
    }

    public PointsAndResources getValuablesOwnedIndex(){
        return this.valuablesOwnedIndex;
    }


    public ExcommunicationEffectLoseVictoryPoints(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void runEffect(Player player){
        //update resources malus
        for (Map.Entry<ResourceType, Integer> entry: this.valuablesOwnedIndex.getResources().entrySet()) {
            player.getPersonalBoard().getExcommunicationValues().increaseFinalValuablesIndexMalus(entry.getKey(), entry.getValue());
        }

        //update points malus
        for (Map.Entry<PointType, Integer> entry: this.valuablesOwnedIndex.getPoints().entrySet()) {
            player.getPersonalBoard().getExcommunicationValues().increaseFinalValuablesIndexMalus(entry.getKey(), entry.getValue());
        }
    }

    public String getDescription(){
        String header = this.effectType + "\n";
        String toLose = "Valuables owned index: \n" + this.valuablesOwnedIndex;
        return new StringBuilder(header).append(toLose).toString();
    }

}
