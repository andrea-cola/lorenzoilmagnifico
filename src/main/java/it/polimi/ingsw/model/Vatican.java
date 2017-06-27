package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

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
