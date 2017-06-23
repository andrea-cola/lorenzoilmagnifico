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
    public String effectType;

    /**
     * Method to run the effect of the card.
     * @param player
     */
    abstract public void runEffect(Player player, InformationCallback informationCallback);

    /**
     * Get a description of the current effect.
     */
    @Override
    abstract public String toString();

}
