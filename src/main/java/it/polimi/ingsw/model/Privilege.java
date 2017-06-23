package it.polimi.ingsw.model;

import java.io.Serializable;

public class Privilege implements Serializable{

    private PointsAndResources valuables;

    private Boolean isAvailablePrivilege;

    public Privilege(PointsAndResources valuables, Boolean isAvailablePrivilege){
        this.isAvailablePrivilege = isAvailablePrivilege;
        this.valuables = valuables;
    }

    public PointsAndResources getValuables(){
        return this.valuables;
    }

    public void setIsAvailablePrivilege(Boolean updatedValue){
        this.isAvailablePrivilege = updatedValue;
    }

    public Boolean getIsAvailablePrivilege(){
        return this.isAvailablePrivilege;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(valuables.toString());
        return stringBuilder.toString();
    }

}
