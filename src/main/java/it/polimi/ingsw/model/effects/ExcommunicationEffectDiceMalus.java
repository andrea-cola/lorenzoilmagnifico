package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.Player;

/**
 * This class represents the excommunication effect that sets a family member dice malus
 */
public class ExcommunicationEffectDiceMalus extends ExcommunicationEffect {

    /**
     * Malus for black family member
     */
    private int blackDiceMalus;

    /**
     * Malus for white family member
     */
    private int whiteDiceMalus;

    /**
     * Malus for orange family member
     */
    private int orangeDiceMalus;

    /**
     * Class constructor
     */
    public ExcommunicationEffectDiceMalus(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    /**
     * Method to run the effect of the card
     */
    public void runEffect(Player player){
        player.getPersonalBoard().getFamilyMember().decreaseFamilyMemberValue(FamilyMemberColor.BLACK, blackDiceMalus);
        player.getPersonalBoard().getFamilyMember().decreaseFamilyMemberValue(FamilyMemberColor.WHITE, whiteDiceMalus);
        player.getPersonalBoard().getFamilyMember().decreaseFamilyMemberValue(FamilyMemberColor.ORANGE, orangeDiceMalus);
    }

    public String getDescription(){
        return "white dice -" + this.whiteDiceMalus + ", black dice -" + this.blackDiceMalus + ", orange dice -" + this.orangeDiceMalus;
    }


}
