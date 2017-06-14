package it.polimi.ingsw.model;

/**
 * This class represents the family member
 */
public class FamilyMember {
    /**
     * Color of the family member
     */
    private FamilyMemberColor familyMemberColor;

    /**
     * Value of the family member
     */
    private Integer familyMemberValue;

    /**
     * Constructor
     * @param color of the member
     */
    public FamilyMember(FamilyMemberColor color){
        this.familyMemberColor = color;
        this.familyMemberValue = new Integer(0);
    }

    /**
     * Get the color of the family member
     * @return the color
     */
    public FamilyMemberColor getFamilyMemberColor(){
        return this.familyMemberColor;
    }

    /**
     * Get the value of the family member
     * @return the value
     */
    public Integer getFamilyMemberValue(){
        return this.familyMemberValue;
    }

    /**
     * Set the value of the family member
     * @param value
     */
    public void setFamilyMemberValue(int value){
        this.familyMemberValue = value;
    }

}
