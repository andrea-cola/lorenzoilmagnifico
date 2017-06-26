package it.polimi.ingsw.model;

import java.io.Serializable;

public class Privilege implements Serializable{

    private PointsAndResources valuables;

    private boolean isAvailablePrivilege;

    public Privilege(PointsAndResources valuables, boolean isAvailablePrivilege){
        this.isAvailablePrivilege = isAvailablePrivilege;
        this.valuables = valuables;
    }

    public PointsAndResources getValuables(){
        return this.valuables;
    }

    public void setNotAvailablePrivilege(){
        this.isAvailablePrivilege = false;
    }

    public boolean isAvailable(){
        return this.isAvailablePrivilege;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(valuables.toString());
        return stringBuilder.toString();
    }

}

