package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;
import it.polimi.ingsw.model.ResourceType;

import java.util.Map;

/**
 * This class represents the excommunication effect for losing victory points based on development card resources
 */
public class ExcommunicationEffectDevCardLoseVictoryPoints extends ExcommunicationEffect {

    /**
     * Card valuables
     */
    private PointsAndResources cardValuables;

    /**
     * Class constructor
     */
    public ExcommunicationEffectDevCardLoseVictoryPoints(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void setCardValuables(PointsAndResources r){
        this.cardValuables = r;
    }
    /**
     * Method to run the effect of the card
     */
    public void runEffect(Player player){
        for (Map.Entry<ResourceType, Integer> entry : this.cardValuables.getResources().entrySet()){
            player.getPersonalBoard().getExcommunicationValues().setFinalResourcesDevCardIndexMalus(entry.getKey(), entry.getValue());
        }
    }

    public String getDescription(){
        return "These resources won't assign final victory points: " + this.cardValuables;
    }
}
