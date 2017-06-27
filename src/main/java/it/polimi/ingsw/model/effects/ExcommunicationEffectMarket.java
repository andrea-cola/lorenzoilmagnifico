package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

/**
 * Created by lorenzo on 25/06/17.
 */
public class ExcommunicationEffectMarket extends ExcommunicationEffect {

    public ExcommunicationEffectMarket(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setMarketIsAvailable(false);
    }

    public String getDescription(){
        String header = this.effectType + "\n";
        String stateMessage = "Market no more available";
        return new StringBuilder(header).append(stateMessage).toString();
    }
}
