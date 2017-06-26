package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represent the vatican abstraction.
 */
public class Vatican implements Serializable{

    /**
     * Array of excommunication leaderCards. Each card is used in only one age.
     * The length of this array is equal the number of ages.
     */
    private ExcommunicationCard[] excommunicationCards;

    /**
     * This is the array of bonus reached
     */
    private int[] victoryPointsBonus;

    public Vatican(ExcommunicationCard[] card, int[] victoryPointsBonus){
        this.excommunicationCards = card;
        this.victoryPointsBonus = victoryPointsBonus;
    }

    /**
     * Get excommunication card from the array.
     * @param age of the game.
     * @return excommunication card of the period.
     */
    public ExcommunicationCard getExcommunicationCard(int age){
        return this.excommunicationCards[age - 1];
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

    public ExcommunicationCard[] getExcommunicationCards(){
        return this.excommunicationCards;
    }
}
