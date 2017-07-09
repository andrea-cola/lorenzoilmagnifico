package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * This class manages the state of FamilyMembers
 */
public class FamilyMember implements Serializable{

    /**
     * All the family members
     */
    private Map<FamilyMemberColor, Integer> members;

    /**
     * Class constructor
     */
    public FamilyMember(){
        this.members = new EnumMap<>(FamilyMemberColor.class);
        for(FamilyMemberColor familyMemberColor : FamilyMemberColor.values())
            members.put(familyMemberColor, 0);
    }

    /**
     * Set family members
     * @param members
     */
    public void setMembers(Map<FamilyMemberColor, Integer> members){
        for(Map.Entry pair : members.entrySet())
            this.members.put((FamilyMemberColor)pair.getKey(), (int)pair.getValue());
    }

    /**
     * Get family members
     * @return
     */
    public Map<FamilyMemberColor, Integer> getMembers(){
        return this.members;
    }

    /**
     * Set a particular value for a particular player
     * @param color
     * @param value
     */
    public void setFamilyMemberValue(FamilyMemberColor color, int value){
        this.members.replace(color, value);
    }

    /**
     * Method to increase the family member value
     * @param color
     * @param value
     */
    public void increaseFamilyMemberValue(FamilyMemberColor color, int value){
        this.members.put(color, this.members.get(color) + value);
    }

    /**
     * Method to decrease the family member value
     * @param color
     * @param value
     */
    public void decreaseFamilyMemberValue(FamilyMemberColor color, int value){
        this.members.put(color, this.members.get(color) - value);
    }

}
