package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

public class ExcommunicationEffectSkipFirstTurn extends ExcommunicationEffect {

    public ExcommunicationEffectSkipFirstTurn(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setSkipFirstTurn(true);
    }

    public String getDescription(){
        return "The player have to skip the first turn";
    }
}
