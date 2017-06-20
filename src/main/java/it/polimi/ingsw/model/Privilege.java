package it.polimi.ingsw.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Privilege {

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

}
