package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class represent the vatican abstraction.
 */
public class Vatican implements Serializable{

    /**
     * The excommunication card you get if you don't sustain Vatican
     */
    private ExcommunicationCard[] excommunicationCards;

    private int[] excommunicationCheckPoints;

    public Vatican(int[] excommunicationCheckPoints){
        this.excommunicationCheckPoints = excommunicationCheckPoints;
    }

    /**
     * Get excommunication card
     */
    public ExcommunicationCard getExcommunicationCard(int atIndex){
        return this.excommunicationCards[atIndex];
    }

    /**
     * Set excommunication cards
     */
    public void setExcommunicationCards(ExcommunicationCard[] cards){
        this.excommunicationCards = cards;
    }

    public int getExcommunicationCheckPoint(int period) {
        return this.excommunicationCheckPoints[period - 1];
    }

    public int[] getExcommunicationCheckPoints(){
        return this.excommunicationCheckPoints;
    }

}
