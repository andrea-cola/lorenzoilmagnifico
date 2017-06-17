package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class represent a council privilege.
 */
public class CouncilPrivilege implements Serializable{

    /**
     * Number of council privileges.
     */
    private int numberOfCouncilPrivileges;

    /**
     * Array of benefits.
     */
    private PointsAndResources[] options;

    /**
     * Set number of council privileges.
     * @param amount of council privileges.
     */
    public void setNumberOfCouncilPrivileges(int amount){
        this.numberOfCouncilPrivileges = amount;
    }

    /**
     * Get number of privileges.
     * @return number of privileges.
     */
    public int getNumberOfCouncilPrivileges(){
        return this.numberOfCouncilPrivileges;
    }

}
