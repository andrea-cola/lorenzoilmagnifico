package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

/**
 * Created by lorenzo on 25/06/17.
 */
public class ExcommunicationEffectSkipFirstTurn extends ExcommunicationEffect {

    public ExcommunicationEffectSkipFirstTurn(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setSkipFirstTurn(true);
    }

    public String getDescription(){
        String header = this.effectType + "\n";
        String description = "The player have to skip the first turn \n";
        return new StringBuilder(header).append(description).toString();
    }
}
