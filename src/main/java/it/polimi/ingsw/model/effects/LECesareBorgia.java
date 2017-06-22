package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;

public class LECesareBorgia extends LeaderEffect{

    public LECesareBorgia(){
        super.effectType = this.getClass().getSimpleName();
    }

    /**
     * Method to run the effect of the card.
     * With this effect you don’t need to satisfy the Military Points requirement when you take Territory Cards.
     * @param player
     */
    @Override
    public void runEffect(Player player, MainBoard mainBoard) {
        int maxCard = player.getPersonalBoard().getMaxNumberOfCardPerType();
        for (int i = 0; i < maxCard; i++){
            player.getPersonalBoard().setGreenCardsMilitaryPointsRequirements(i, 0);
        }
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        return "You do not have to meet the requirement of military points when you pick up land cards.";
    }
}
