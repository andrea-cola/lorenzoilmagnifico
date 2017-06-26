package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointType;

import java.util.Map;

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
        super.effectType = this.getClass().getSimpleName();
    }

    public void runEffect(Player player){
        player.getPersonalBoard().getFamilyMember().decreaseFamilyMemberValue(FamilyMemberColor.BLACK, blackDiceMalus);
        player.getPersonalBoard().getFamilyMember().decreaseFamilyMemberValue(FamilyMemberColor.WHITE, whiteDiceMalus);
        player.getPersonalBoard().getFamilyMember().decreaseFamilyMemberValue(FamilyMemberColor.ORANGE, orangeDiceMalus);
    }

    public String getDescription(){
        String header = this.effectType + "\n";
        String malus1 = "White dice malus: \n" + this.whiteDiceMalus;
        String malus2 = "Black dice malus: \n" + this.blackDiceMalus;
        String malus3 = "Orange dice malus: \n" + this.orangeDiceMalus;
        return new StringBuilder(header).append(malus1).append(malus2).append(malus3).toString();
    }


}
