package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Player;

public class LECesareBorgia extends LeaderEffect{

    public LECesareBorgia(){
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
        return "You do not have to meet the requirement of military points when you pick up land leaderCards.";
    }
}
