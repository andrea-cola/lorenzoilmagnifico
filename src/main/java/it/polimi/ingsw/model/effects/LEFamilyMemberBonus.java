package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;

public class LEFamilyMemberBonus extends LeaderEffect{

    /**
     * Dice bonus value.
     */
    private int bonus;

    /**
     * Class constructor.
     */
    public LEFamilyMemberBonus(){
        super.setEffectType(this.getClass().getSimpleName());
        this.bonus = 0;
    }

    /**
     * Method to run the effect of the card.
     * @param player is gaining benefit from the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        FamilyMemberColor familyMemberColor = informationCallback.choiceLeaderDice("federico-de-montefeltro");
        player.getPersonalBoard().getFamilyMember().setFamilyMemberValue(familyMemberColor, bonus);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "One family member value is increased by " + bonus;
    }
}
