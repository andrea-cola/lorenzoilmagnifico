package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.ExcommunicationEffect;

import java.io.Serializable;

/**
 * This class represent the abstraction of the excommunication card.
 */
public class ExcommunicationCard implements Serializable{

    /**
     * The period in which the card has to be used
     */
    private int period;

    /**
     * Card ID
     */
    private int cardID;

    /**
     * The excommunication effect of the card
     */
    private ExcommunicationEffect effect;

    /**
     * Method to get the ID of the card
     * @return
     */
    public int getCardID(){
        return this.cardID;
    }

    /**
     * Method to get the period of the card
     * @return
     */
    public int getPeriod(){
        return this.period;
    }

    /**
     * Method to set the excommunication effect of the card
     * @param effect
     */
    public void setEffect(ExcommunicationEffect effect){
        this.effect = effect;
    }

    /**
     * Method to get the excommunication effect of the card
     * @return
     */
    public ExcommunicationEffect getEffect(){
        return this.effect;
    }

    public String toString(){
        return new StringBuilder("Period: " + period + " -> " + effect.getDescription() + "\n").toString();
    }

}
