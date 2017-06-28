package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.ExcommunicationEffect;

import java.io.Serializable;

public class ExcommunicationCard implements Serializable{

    private int period;

    private int cardID;

    private ExcommunicationEffect effect;

    public void setPeriod(int period){
        this.period = period;
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

    public void setCardID(int id){
        this.cardID = id;
    }

    public int getCardID(){
        return this.cardID;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[Period]: " + period + "\n");
        stringBuilder.append("[Effect]: " + effect.getDescription() + "\n");
        return stringBuilder.toString();
    }

}
