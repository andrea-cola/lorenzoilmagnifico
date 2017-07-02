package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.Player;

public class ExcommunicationEffectDiceMalus extends ExcommunicationEffect {

    private int blackDiceMalus;

    private int whiteDiceMalus;

    private int orangeDiceMalus;

    public void setBlackDiceMalus(int malus){
        this.blackDiceMalus = malus;
    }

    public int getBlackDiceMalus(){
        return this.blackDiceMalus;
    }

    public void setWhiteDiceMalus(int malus){
        this.whiteDiceMalus = malus;
    }

    public int getWhiteDiceMalus(){
        return this.whiteDiceMalus;
    }

    public void setOrangeDIceMalus(int malus){
        this.orangeDiceMalus = malus;
    }

    public int getOrangeDIceMalus(){
        return this.orangeDiceMalus;
    }

    public ExcommunicationEffectDiceMalus(){
        super.setEffectType(this.getClass().getSimpleName());
    }

    public void runEffect(Player player){
        player.getPersonalBoard().getFamilyMember().decreaseFamilyMemberValue(FamilyMemberColor.BLACK, blackDiceMalus);
        player.getPersonalBoard().getFamilyMember().decreaseFamilyMemberValue(FamilyMemberColor.WHITE, whiteDiceMalus);
        player.getPersonalBoard().getFamilyMember().decreaseFamilyMemberValue(FamilyMemberColor.ORANGE, orangeDiceMalus);
    }

    public String getDescription(){
        return "White dice -" + this.whiteDiceMalus + ", Black dice -" + this.blackDiceMalus + ", Orange dice -" + this.orangeDiceMalus;
    }


}
