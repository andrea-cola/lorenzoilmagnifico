package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

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
    public void runEffect(Player player) {

    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        return "You can place your family members in busy action spaces.";
    }
}
