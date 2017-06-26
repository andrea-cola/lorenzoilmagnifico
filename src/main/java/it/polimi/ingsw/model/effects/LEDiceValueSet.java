package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.Player;

/**
 * This class sets all dices to a particular value
 */
public class LEDiceValueSet extends LeaderEffect{

    private int whiteDice;

    private int blackDice;

    private int orangeDice;

    public LEDiceValueSet(){
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
        player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(FamilyMemberColor.WHITE, whiteDice);
        player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(FamilyMemberColor.BLACK, blackDice);
        player.getPersonalBoard().getFamilyMember().increaseFamilyMemberValue(FamilyMemberColor.ORANGE, orangeDice);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        String header = this.effectType + "\n";
        String resources = "White dice:\n" + whiteDice;
        String resources2 = "Black dice:\n" + blackDice;
        String resources3 = "Orange dice:\n" + orangeDice;
        return new StringBuilder(header).append(resources).append(resources2).append(resources3).toString();
    }
}
