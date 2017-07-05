package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;

import it.polimi.ingsw.model.Player;

public class LELudovicoAriosto extends LeaderEffect {

    /**
     * Class constructor.
     */
    public LELudovicoAriosto(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Method to run the effect of the card.
     * @param player is gaining benefit of the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        player.getPersonalBoard().setAlwaysPlaceFamilyMemberInsideActionSpace(true);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "You can place your family members in busy action spaces.";
    }
}
