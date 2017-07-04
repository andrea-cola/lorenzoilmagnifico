package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;

/**
 * This class gives a bonus to all dices
 */
public class LEDiceBonus extends LeaderEffect {

    /**
     * White dice bonus value.
     */
    private int whiteDice;

    /**
     * Black dice bonus value.
     */
    private int blackDice;

    /**
     * Orange dice bonus value.
     */
    private int orangeDice;

    /**
     * Class constructor.
     */
    public LEDiceBonus(){
        super.setEffectType(this.getClass().getSimpleName());
        this.whiteDice = 0;
        this.blackDice = 0;
        this.orangeDice = 0;
    }

    /**
     * Method to run the effect of the card.
     * @param player is gaining benefit from the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(FamilyMemberColor.WHITE, whiteDice);
        player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(FamilyMemberColor.BLACK, blackDice);
        player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(FamilyMemberColor.ORANGE, orangeDice);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString(){
        return "white dice bonus =" + whiteDice + " black dice bonus = " + blackDice + " orange dice bonus = " + orangeDice;
    }
}
