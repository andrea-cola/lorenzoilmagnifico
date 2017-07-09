package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;

/**
 * This class represents leader card effect.
 */
public abstract class LeaderEffect implements Serializable{

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
     * Get a description of the current effect.
     */
    public abstract String toString();

    /**
     * Method to set the effect type
     * @param effectType
     */
    public void setEffectType(String effectType){
        this.effectType = effectType;
    }

}
