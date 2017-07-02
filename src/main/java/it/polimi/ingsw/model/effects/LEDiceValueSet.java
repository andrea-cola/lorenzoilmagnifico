package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * This class sets all dices to a particular value
 */
public class LEDiceValueSet extends LeaderEffect{

    private int whiteDice;

    private int blackDice;

    private int orangeDice;

    public LEDiceValueSet(){
        super.setEffectType(this.getClass().getSimpleName());
        this.orangeDice = 0;
        this.blackDice = 0;
        this.whiteDice = 0;
    }

    /**
     * Method to run the effect of the card.
     *
     * @param player
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback) {
        Map<FamilyMemberColor, Integer> members = new HashMap<>();
        members.put(FamilyMemberColor.WHITE, whiteDice);
        members.put(FamilyMemberColor.ORANGE, orangeDice);
        members.put(FamilyMemberColor.BLACK, blackDice);
        members.put(FamilyMemberColor.NEUTRAL, 0);
        player.getPersonalBoard().getFamilyMember().setMembers(members);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        String header = this.getEffectType() + "\n";
        String resources = "White dice:\n" + whiteDice;
        String resources2 = "Black dice:\n" + blackDice;
        String resources3 = "Orange dice:\n" + orangeDice;
        return new StringBuilder(header).append(resources).append(resources2).append(resources3).toString();
    }
}
