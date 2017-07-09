package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

/**
 * This class represents the excommunication effect to set the number of slaves necessary to increase the family member's value of 1
 */
public class ExcommunicationEffectNumberOfSlaves extends ExcommunicationEffect{

    /**
     * Number of slaves necessary to increase family member's value of 1
     */
    private int numberOfSlaves;

    /**
     *  Class constructor
     */
    public ExcommunicationEffectNumberOfSlaves(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Method to run the effect of the card
     * @param player
     */
    public void runEffect(Player player){
        player.getPersonalBoard().getExcommunicationValues().setNumberOfSlaves(this.numberOfSlaves);
    }

    public String getDescription(){
        return "Number of slaves to increase family member value: " + this.numberOfSlaves;
    }

}
