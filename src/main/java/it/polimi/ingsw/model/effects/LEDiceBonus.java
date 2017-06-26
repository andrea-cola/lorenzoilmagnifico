package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;
import javafx.beans.property.MapProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * This class gives a bonus to all dices
 */
public class LEDiceBonus extends LeaderEffect {

    private int whiteDice;

    private int blackDice;

    private int orangeDice;

    public LEDiceBonus(){
        super.effectType = this.getClass().getSimpleName();
    }

    public void setWhiteDice(int whiteDice){
        this.whiteDice = whiteDice;
    }

    public void setBlackDice(int blackDice){
        this.blackDice = blackDice;
    }

    public void setOrangeDice(int orangeDice){
        this.orangeDice = orangeDice;
    }

    public int getWhiteDice(){
        return this.whiteDice;
    }

    public int getBlackDice(){
        return this.getBlackDice();
    }

    public int getOrangeDice(){
        return this.getOrangeDice();
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
    public String getDescription() {
        String header = this.effectType + "\n";
        String resources = "White dice bonus:\n" + whiteDice;
        String resources2 = "Black dice bonus:\n" + blackDice;
        String resources3 = "Orange dice bonus:\n" + orangeDice;
        return new StringBuilder(header).append(resources).append(resources2).append(resources3).toString();
    }
}
