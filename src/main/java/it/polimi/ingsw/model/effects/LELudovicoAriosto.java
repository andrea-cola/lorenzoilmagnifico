package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;
import sun.applet.Main;

public class LELudovicoAriosto extends LeaderEffect {

    public LELudovicoAriosto(){
        super.effectType = this.getClass().getSimpleName();
    }
    /**
     * Method to run the effect of the card.
     *
     * @param player
     */
    @Override
    public void runEffect(Player player, MainBoard mainBoard) {
        mainBoard.getHarvest().setEmpty(true);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        return "You can place your family members in busy action spaces.";
    }
}
