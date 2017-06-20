package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;


public class FamilyMember {

    private Map<FamilyMemberColor, Integer> members;

    public FamilyMember(){
        this.members = new HashMap<>();
    }

    public void setMembers(Map<FamilyMemberColor, Integer> members){
        this.members = members;
    }

    public Map<FamilyMemberColor, Integer> getMembers(){
        return this.members;
    }

    public void increaseFamilyMemberValue(FamilyMemberColor color, Integer value){
        this.members.put(color, this.members.get(color) + value);
    }

    public void decreaseFamilyMemberValue(FamilyMemberColor color, Integer value){
        this.members.put(color, this.members.get(color) - value);
    }

}
