package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

/**
 * This class represents the excommunication effect to skip the first turn
 */
public class ExcommunicationEffectSkipFirstTurn extends ExcommunicationEffect {

    /**
     * Class constructor
     */
    public ExcommunicationEffectSkipFirstTurn(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Method to run the effect of the card
     * @param player
     */
    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setSkipFirstTurn(true);
    }

    public String getDescription(){
        return "The player have to skip the first turn";
    }
}
