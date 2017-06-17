package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

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
    abstract public void runEffect(Player player);

}
