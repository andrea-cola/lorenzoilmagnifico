package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class represent the vatican abstraction.
 */
public class Vatican implements Serializable{


    private ExcommunicationCard[] excommunicationCards;

    private int[] excommunicationCheckPoints;

    public Vatican(int[] excommunicationCheckPoints){
        this.excommunicationCheckPoints = excommunicationCheckPoints;
    }

    public ExcommunicationCard getExcommunicationCard(int atIndex){
        return this.excommunicationCards[atIndex];
    }

    public ExcommunicationCard[] getExcommunicationCards(){
        return this.excommunicationCards;
    }

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
