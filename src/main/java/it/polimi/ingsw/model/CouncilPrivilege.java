package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represent a council privilege.
 */
public class CouncilPrivilege implements Serializable{

    /**
     * Number of council privileges the player has to choose.
     */
    private int numberOfCouncilPrivileges;

    /**
     * Array of privileges.
     */
    private Privilege[] privileges = new Privilege[5];


    public CouncilPrivilege(){

        PointsAndResources valuables1 = new PointsAndResources();
        valuables1.increase(ResourceType.STONE, 1);
        valuables1.increase(ResourceType.WOOD, 1);
        this.privileges[0] = new Privilege(valuables1, true);

        PointsAndResources valuables2 = new PointsAndResources();
        valuables2.increase(ResourceType.SERVANT, 2);
        this.privileges[1] = new Privilege(valuables2, true);

        PointsAndResources valuables3 = new PointsAndResources();
        valuables3.increase(ResourceType.COIN, 2);
        this.privileges[2] = new Privilege(valuables3, true);

        PointsAndResources valuables4 = new PointsAndResources();
        valuables4.increase(PointType.MILITARY, 2);
        this.privileges[3] = new Privilege(valuables4, true);

        PointsAndResources valuables5 = new PointsAndResources();
        valuables5.increase(PointType.FAITH, 1);
        this.privileges[4] = new Privilege(valuables5, true);

    }

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

    /**
     * Get privileges array
     * @return
     */
    public Privilege[] getPrivileges(){
        return this.privileges;
    }

    /**
     * Get a specific privilege based on its valuables. If the returned value is false, the user has to choose another privilege
     * @param privilegeIndex
     * @return
     */
    public Boolean privilegeAvailable(int privilegeIndex){
        if (this.privileges[privilegeIndex].getIsAvailablePrivilege()){
            //change council privilege flag inside the hashmap
            this.privileges[privilegeIndex].setIsAvailablePrivilege(false);
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "COUNCIL PRIVILEGE: " + numberOfCouncilPrivileges;
    }

    class Privilege implements Serializable {

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
}
