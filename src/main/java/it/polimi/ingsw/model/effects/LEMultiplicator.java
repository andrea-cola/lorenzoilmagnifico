package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;

import java.util.Map;

public class LEMultiplicator extends LeaderEffect{

    private int multiplicator;

    public LEMultiplicator(){
        super.effectType = this.getClass().getSimpleName();
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
    public void runEffect(Player player, MainBoard mainBoard) {

    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        return "Moltiplica tutte le risorse per " + multiplicator + ", DA SISTEMARE";
    }
}
