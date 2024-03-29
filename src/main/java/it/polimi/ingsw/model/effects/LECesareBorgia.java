package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;

/**
 * This class represents the leader effect that sets to zero the military points required to get more green development cards
 */
public class LECesareBorgia extends LeaderEffect{

    /**
     * Class constructor
     */
    public LECesareBorgia(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Method to run the effect of the card.
     * With this effect you don’t need to satisfy the Military Points requirement when you take Territory Cards.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        int maxCard = player.getPersonalBoard().getMaxNumberOfCardPerType();
        int[] newMilitaryPointsRequirements = player.getPersonalBoard().getGreenCardsMilitaryPointsRequirements();
        for (int i = 0; i <= maxCard; i++)
            newMilitaryPointsRequirements[i] = 0;
        player.getPersonalBoard().setGreenCardsMilitaryPointsRequirements(newMilitaryPointsRequirements);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "You do not have to meet the requirement of military points when you pick up land cards.";
    }
}
