package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.ExcommunicationEffect;

import java.io.Serializable;

public class ExcommunicationCard implements Serializable{

    private int period;

    private int cardID;

    private ExcommunicationEffect effect;

    public int getCardID(){
        return this.cardID;
    }

    public int getPeriod(){
        return this.period;
    }

    public void setEffect(ExcommunicationEffect effect){
        this.effect = effect;
    }

    public ExcommunicationEffect getEffect(){
        return this.effect;
    }

    public String toString(){
        return new StringBuilder("Period: " + period + " -> " + effect.getDescription() + "\n").toString();
    }

}
