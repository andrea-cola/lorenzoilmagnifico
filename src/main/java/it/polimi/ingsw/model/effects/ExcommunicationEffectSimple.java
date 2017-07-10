package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointType;
import it.polimi.ingsw.model.PointsAndResources;
import it.polimi.ingsw.model.ResourceType;

import java.util.Map;

/**
 * This class represents the excommunication effect for valuables malus
 */
public class ExcommunicationEffectSimple extends ExcommunicationEffect{

    /**
     * Valuables malus
     */
    private PointsAndResources valuablesMalus;

    /**
     * Class constructor
     */
    public ExcommunicationEffectSimple(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Method to run the effect of the card
     * @param player
     */
    public void runEffect(Player player){
        //update resources malus
        for (Map.Entry<ResourceType, Integer> entry: this.valuablesMalus.getResources().entrySet()) {
            player.getPersonalBoard().getExcommunicationValues().increaseNormalValuablesMalus(entry.getKey(), entry.getValue());
        }

        //update points malus
        for (Map.Entry<PointType, Integer> entry: this.valuablesMalus.getPoints().entrySet()) {
            player.getPersonalBoard().getExcommunicationValues().increaseNormalValuablesMalus(entry.getKey(), entry.getValue());
        }
    }

    public String getDescription(){
        return "Valuables malus: " + this.valuablesMalus.toString();
    }
}
