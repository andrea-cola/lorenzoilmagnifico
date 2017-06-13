package it.polimi.ingsw.model;

/**
 * This class represent a council privilege.
 */
public class CouncilPrivilege {

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
     * Get number of pribileges.
     * @return number of privileges.
     */
    public int getNumberOfCouncilPrivileges(){
        return this.numberOfCouncilPrivileges;
    }

}
