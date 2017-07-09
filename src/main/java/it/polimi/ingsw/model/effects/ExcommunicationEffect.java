package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

import java.io.Serializable;

/**
 *  This class is the abstraction of all excommunication effects.
 */
public abstract class ExcommunicationEffect implements Serializable {

    /**
     * Effect type
     */
    private String effectType;

    /**
     * Set type of the effect.
     * @param effectType of the effect.
     */
    public void setEffectType(String effectType){
        this.effectType = effectType;
    }

    /**
     * Get type of the effect.
     * @return effect type.
     */
    public String getEffectType(){
        return this.effectType;
    }

    /**
     * Method to run the effect of the card.
     */
    public abstract void runEffect(Player player);

    /**
     * Get a description of the current effect.
     */
    public abstract String getDescription();

}