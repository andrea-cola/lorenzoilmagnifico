package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;

/**
 * This class represents the excommunication effect for development cards
 */
public class ExcommunicationEffectDevCard extends ExcommunicationEffect {

    /**
     * The color of the development card
     */
    private DevelopmentCardColor developmentCardColor;

    /**
     * Dice malus
     */
    private int diceMalus;

    /**
     * Class constructor
     */
    public ExcommunicationEffectDevCard(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Method to run the effect of the card
     */
    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setDevelopmentCardDiceMalus(this.developmentCardColor, this.diceMalus);
    }

    public String getDescription() {
        return "Dice malus = " + diceMalus + " for all card with following color " + this.developmentCardColor;
    }

}
