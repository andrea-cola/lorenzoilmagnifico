package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;

/**
 * This class represents the excommunication effect that disable victory points for development cards owned
 */
public class ExcommunicationEffectNoVictoryPoints extends ExcommunicationEffect {

    /**
     * Development card color
     */
    private DevelopmentCardColor developmentCardColor;

    /**
     * Class constructor
     */
    public ExcommunicationEffectNoVictoryPoints(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Method to run the effect of the card
     */
    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setDevelopmentCardGetFinalPoints(this.developmentCardColor, false);
    }

    public String getDescription(){
        return "Card of the following colors won't assign victory points: " + this.developmentCardColor.toString().toLowerCase();
    }
}
