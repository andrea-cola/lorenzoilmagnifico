package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.InformationCallback;

import java.io.Serializable;

/**
 * This class is the abstraction of all effects.
 */
public abstract class Effect implements Serializable{

    /**
     * Effect type
     */
    private String effectType;

    /**
     * Method to run the effect of the card.
     * @param player
     */
    public abstract void runEffect(Player player, InformationCallback informationCallback);

    /**
     * Method to set the effect type
     * @param effectType
     */
    public void setEffectType(String effectType){
        this.effectType = effectType;
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public abstract String toString();

}
