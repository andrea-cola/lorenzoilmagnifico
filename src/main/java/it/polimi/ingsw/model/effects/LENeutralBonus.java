package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;

/**
 * This class gives a bonus to the neutral familiy member
 */
public class LENeutralBonus extends LeaderEffect{

    private int bonus;

    public LENeutralBonus(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void setBonus(int value){
        this.bonus = value;
    }

    public int getBonus(){
        return this.bonus;
    }

    /**
     * Method to run the effect of the card.
     *
     * @param player
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
