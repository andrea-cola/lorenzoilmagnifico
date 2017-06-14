package it.polimi.ingsw.model;

<<<<<<< HEAD
=======
/**
 * This class represent a council privilege.
 */
>>>>>>> c942eee6c0968bf3dbcf79534b020de9956d2bf4
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
