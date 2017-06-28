package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class represent the vatican abstraction.
 */
public class Vatican implements Serializable{

    /**
<<<<<<< HEAD
     * Array of excommunication leaderCards. Each card is used in only one age.
     * The length of this array is equal the number of ages.
=======
     * The excommunication card you get if you don't sustain Vatican
>>>>>>> 805b963d384a061d6727e16ca7eea5f8bd6bf86d
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
