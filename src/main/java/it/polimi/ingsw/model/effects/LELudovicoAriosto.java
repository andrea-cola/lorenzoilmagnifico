package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.InformationCallback;

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
    public void runEffect(Player player, InformationCallback informationCallback) {
        //TODO remember to set to false the flag at the end of each turn
        player.getPersonalBoard().setAlwaysPlaceFamilyMemberInsideActionSpace(true);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        return "You can place your family members in busy action spaces.";
    }
}
