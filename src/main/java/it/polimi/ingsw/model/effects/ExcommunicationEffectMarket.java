package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

public class ExcommunicationEffectMarket extends ExcommunicationEffect {

    public ExcommunicationEffectMarket(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setMarketIsAvailable(false);
    }

    public String getDescription(){
        return "Market not accessible";
    }
}
