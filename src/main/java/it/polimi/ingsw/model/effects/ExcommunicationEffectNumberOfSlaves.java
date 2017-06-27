package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

/**
 * Created by lorenzo on 25/06/17.
 */
public class ExcommunicationEffectNumberOfSlaves extends ExcommunicationEffect{

    private int numberOfSlaves;

    public void setNumberOfSlaves(int numberOfSlaves){
        this.numberOfSlaves = numberOfSlaves;
    }

    public ExcommunicationEffectNumberOfSlaves(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setNumberOfSlaves(this.numberOfSlaves);
    }

    public String getDescription(){
        String header = this.effectType + "\n";
        String number = "Number of slaves to increase family member value:\n" + this.numberOfSlaves;
        return new StringBuilder(header).append(number).toString();
    }

}
