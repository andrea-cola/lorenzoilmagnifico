package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;

import java.util.ArrayList;

/**
 * This class represents tower cell abstraction.
 */
public class TowerCell {

    /**
     * Development card assigned to this cell.
     */
    private DevelopmentCard developmentCard;

    /**
     * Immediate effect of the cell.
     */
    private Effect towerCellImmediateEffect;

    /**
     * Family member that could be placed in the cell.
     */
    private ArrayList<FamilyMember> familyMembers;

    /**
     * Min dice value to place a family member in the cell.
     */
    private int minFamilyMemberValue;

    public void setMinFamilyMemberValue(Integer value){
        this.minFamilyMemberValue = value;
    }

    /**
     * Set development card in the cell.
     * @param card of the cell.
     */
    public void setDevelopmentCard(DevelopmentCard card){
        this.developmentCard = card;
    }

    /**
     * Place a family member in the cell.
     * @param familyMember to be placed.
     */
    public void setFamilyMember(FamilyMember familyMember){
        this.familyMembers.add(familyMember);
    }

    /**
     * Check if there is another family member placed in the cell.
     * @return a boolean.
     */
    public boolean isEmpty(){
        if(familyMembers.isEmpty())
            return true;
        return false;
    }

    /**
     * Return the development card assigned to the cell.
     * @return a development card.
     */
    public DevelopmentCard getDevelopmentCard(){
        return this.developmentCard;
    }

    /**
     * Return dice value of the cell.
     * @return an integer.
     */
    public int getMinFamilyMemberValue(){
        return this.minFamilyMemberValue;
    }

}
