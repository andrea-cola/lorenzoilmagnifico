package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class represent the vatican.
 */
public class Vatican implements Serializable{

    /**
     * Excommunication cards you get if you don't sustain Vatican.
     */
    private ExcommunicationCard[] excommunicationCards;

    /**
     * Array of excommunication checkpoints to avoid the penalty.
     */
    private int[] excommunicationCheckPoints;

    /**
     * Class constructor.
     * @param excommunicationCheckPoints to set.
     */
    /*package-local*/ Vatican(int[] excommunicationCheckPoints){
        this.excommunicationCheckPoints = excommunicationCheckPoints;
    }

    /**
     * Get the excommunication card per period.
     * @param atIndex period of the game.
     * @return excommunicaiton card.
     */
    public ExcommunicationCard getExcommunicationCard(int atIndex){
        return this.excommunicationCards[atIndex];
    }

    /**
     * Set excommunication card array.
     * @param excommunicationCards array.
     */
    public void setExcommunicationCards(ExcommunicationCard[] excommunicationCards){
        this.excommunicationCards = excommunicationCards;
    }

    /**
     * Get excommunication points limit to avoid penalty.
     * @param period of game.
     * @return amount of points.
     */
    public int getExcommunicationCheckPoint(int period) {
        return this.excommunicationCheckPoints[period - 1];
    }

    /**
     * Get excommunication cards array.
     * @return excommunication cards array.
     */
    /*package-local*/ ExcommunicationCard[] getExcommunicationCards(){
        return this.excommunicationCards;
    }

    /**
     * Get excommunication points array.
     * @return excommunication points array.
     */
    /*package-local*/ int[] getExcommunicationCheckPoints(){
        return this.excommunicationCheckPoints;
    }

}
