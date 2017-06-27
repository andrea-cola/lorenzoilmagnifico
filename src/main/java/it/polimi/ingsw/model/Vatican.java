package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represent the vatican abstraction.
 */
public class Vatican implements Serializable{

    /**
     * The excommunication card you get if you don't sustain Vatican
     */
    private ExcommunicationCard[] excommunicationCards;

    /**
     * This is the array of bonus reached
     */
    private int[] victoryPointsBonus;

    public Vatican(int[] victoryPointsBonus){
        this.victoryPointsBonus = victoryPointsBonus;
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

    /**
     * Get the bonus correspondents to faith points.
     * @param faithPoints accumulated.
     * @return amount of victory points.
     */
    public int getVictoryPointsBonus(int faithPoints){
        return victoryPointsBonus[faithPoints];
    }

    public int[] getVictoryPointsBonusArray(){
        return this.victoryPointsBonus;
    }

}
