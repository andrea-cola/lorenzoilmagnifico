package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;

public class LEMultiplicator extends LeaderEffect{

    /**
     * Resource multiplicator factor.
     */
    private int multiplicator;

    /**
     * Class constructor.
     */
    public LEMultiplicator(){
        super.setEffectType(this.getClass().getSimpleName());
        multiplicator = 0;
    }

    /**
     * Method to run the effect of the card.
     * @param player is gaining benefit of the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        // do nothing because this effect his handle in Game.java
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "Multiply all resources earned per " + multiplicator;
    }
}
