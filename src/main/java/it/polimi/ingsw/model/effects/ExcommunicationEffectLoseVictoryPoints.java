package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointType;
import it.polimi.ingsw.model.PointsAndResources;
import it.polimi.ingsw.model.ResourceType;

import java.util.Map;

/**
 * This class represents the excommunication effect to decrease final victory points based on valuables owned at the end of the match
 */
public class ExcommunicationEffectLoseVictoryPoints extends ExcommunicationEffect{

    /**
     * For every resource owned, you lose one victory point
     */
    private PointsAndResources valuablesOwnedIndex;

    /**
     * Class constructor
     */
    public ExcommunicationEffectLoseVictoryPoints(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Method to run the effect of the card
     */
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
        return "Lose victory points: " + this.valuablesOwnedIndex;
    }

}
