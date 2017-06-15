package it.polimi.ingsw.model;

/**
 * This class represent family member abstraction.
 */
public class FamilyMember {

    private String player;

    private FamilyMemberColor familyMemberColor;

    private Integer familyMemberValue;

    public FamilyMember(FamilyMemberColor color){
        this.familyMemberColor = color;
        this.familyMemberValue = new Integer(0);
    }

    public FamilyMemberColor getFamilyMemberColor(){
        return this.familyMemberColor;
    }

    public Integer getFamilyMemberValue(){
        return this.familyMemberValue;
    }

    public void setFamilyMemberValue(int value){
        this.familyMemberValue = value;
    }

}
