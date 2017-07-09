package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;

/**
 * This class gives a bonus to the neutral family member
 */
public class LENeutralBonus extends LeaderEffect{

    /**
     * Bonus of neutral family member.
     */
    private int bonus;

    /**
     * Class constructor.
     */
    public LENeutralBonus(){
        super.setEffectType(this.getClass().getSimpleName());
        bonus = 0;
    }

    /**
     * Method to run the effect of the card.
     * @param player is gaining benefit of the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(FamilyMemberColor.NEUTRAL, bonus);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "Neutral family member value increased by " + bonus;
    }
}
