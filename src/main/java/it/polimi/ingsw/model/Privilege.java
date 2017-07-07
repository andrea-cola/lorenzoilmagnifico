package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class that represents each privilege in the game.
 */
public class Privilege implements Serializable{

    /**
     * Valuable to earn if the privilege is chosen.
     */
    private PointsAndResources valuables;

    /**
     * Flag that indicates if the privilege is available to choose.
     */
    private boolean isAvailablePrivilege;

    /**
     * Class constructor.
     * @param valuables to earn.
     * @param isAvailablePrivilege flag.
     */
    /*package-local*/ Privilege(PointsAndResources valuables, boolean isAvailablePrivilege){
        this.isAvailablePrivilege = isAvailablePrivilege;
        this.valuables = valuables;
    }

    /**
     * Get valuable to earn.
     * @return valuable to earn.
     */
    public PointsAndResources getValuables(){
        return this.valuables;
    }

    /**
     * Set privilege not available.
     */
    public void setNotAvailablePrivilege(){
        this.isAvailablePrivilege = false;
    }

    /**
     * Return available flag.
     * @return boolean flag.
     */
    public boolean isAvailable(){
        return this.isAvailablePrivilege;
    }

    @Override
    public String toString(){
        return valuables.toString();
    }

}

