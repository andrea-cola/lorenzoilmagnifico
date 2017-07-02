package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

public class ExcommunicationEffectNumberOfSlaves extends ExcommunicationEffect{

    private int numberOfSlaves;

    public void setNumberOfSlaves(int numberOfSlaves){
        this.numberOfSlaves = numberOfSlaves;
    }

    public ExcommunicationEffectNumberOfSlaves(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setNumberOfSlaves(this.numberOfSlaves);
    }

    public String getDescription(){
        return "Number of slaves to increase family member value: " + this.numberOfSlaves;
    }

}
