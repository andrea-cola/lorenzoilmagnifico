package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

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
    private Map<PointsAndResources, Boolean> privileges = new HashMap<>();


    public CouncilPrivilege(){

        PointsAndResources valuables1 = new PointsAndResources();
        valuables1.increase(ResourceType.STONE, 1);
        valuables1.increase(ResourceType.WOOD, 1);
        this.privileges.put(valuables1, true);

        PointsAndResources valuables2 = new PointsAndResources();
        valuables2.increase(ResourceType.SERVANT, 2);
        this.privileges.put(valuables2, true);

        PointsAndResources valuables3 = new PointsAndResources();
        valuables3.increase(ResourceType.COIN, 2);
        this.privileges.put(valuables3, true);

        PointsAndResources valuables4 = new PointsAndResources();
        valuables4.increase(PointType.MILITARY, 2);
        this.privileges.put(valuables4, true);

        PointsAndResources valuables5 = new PointsAndResources();
        valuables5.increase(PointType.FAITH, 1);
        this.privileges.put(valuables5, true);

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
     * Get a specific privilege based on its valuables. If the returned value is false, the user has to choose another privilege
     * @param valuables
     * @return
     */
    public Boolean privilegeAvailable(PointsAndResources valuables){
        if (this.privileges.get(valuables)){
            //change council privilege flag inside the hashmap
            this.privileges.replace(valuables, false);
            return true;
        }
        return false;
    }
}
