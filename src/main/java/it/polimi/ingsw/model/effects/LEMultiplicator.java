package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;

public class LEMultiplicator extends LeaderEffect{

    private int multiplicator;

    public LEMultiplicator(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void setMultiplicator(int value){
        this.multiplicator = value;
    }

    public int getMultiplicator(){
        return this.multiplicator;
    }

    /**
     * Method to run the effect of the card.
     *
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {

    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "Multiply all resources earned per " + multiplicator;
    }
}
