package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represent the vatican abstraction.
 */
public class Vatican implements Serializable{

    /**
     * Array of excommunication cards. Each card is used in only one age.
     * The length of this array is equal the number of ages.
     */
    private ExcommunicationCard[] excommunicationCards;

    /**
     * This is the array of bonus reached
     */
    private int[] victoryPointsBonus;

    /**
     * Set excommunication cards.
     * @param excommunicationCards to set.
     */
    public void setExcommunicationCards(ExcommunicationCard[] excommunicationCards){
        this.excommunicationCards = excommunicationCards;
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
     * Set the array of victory points bonus.
     * @param victoryPointsBonus to set.
     */
    public void setVictoryPointsBonus(int[] victoryPointsBonus){
        this.victoryPointsBonus = victoryPointsBonus;
    }

    /**
     * Get the bonus correspondents to faith points.
     * @param faithPoints accumulated.
     * @return amount of victory points.
     */
    public int getVictoryPointsBonus(int faithPoints){
        return victoryPointsBonus[faithPoints];
    }
}
