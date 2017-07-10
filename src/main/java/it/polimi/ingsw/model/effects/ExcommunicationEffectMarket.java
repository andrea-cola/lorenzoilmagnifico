package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

/**
 * This class represents the excommunication effect for market accessibility
 */
public class ExcommunicationEffectMarket extends ExcommunicationEffect {

    /**
     * Class constructor
     */
    public ExcommunicationEffectMarket(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Method to run the effect of the card
     */
    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setMarketIsAvailable(false);
    }

    public String getDescription(){
        return "Market not accessible";
    }
}
