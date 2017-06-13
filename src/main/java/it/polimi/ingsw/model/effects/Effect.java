package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

/**
 * This class is the abstraction of all effects.
 */
public abstract class Effect {

    public String effectType;

    /**
     * Method to execute the effect of the card.
     * @param player
     */
    abstract public void runEffect(Player player);

}
