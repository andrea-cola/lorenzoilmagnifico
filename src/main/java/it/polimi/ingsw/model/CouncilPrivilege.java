package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * This class represent a council privilege.
 */
public class CouncilPrivilege implements Serializable{

    /**
     * The number of council privileges the player can choose
     */
    private int numberOfCouncilPrivileges;

    /**
     * Privileges array
     */
    private Privilege[] privileges = new Privilege[5];

    /**
     * Class constructor
     * @param numberOfCouncilPrivileges The number of council privileges the player can choose
     */
    public CouncilPrivilege(int numberOfCouncilPrivileges){
        this.numberOfCouncilPrivileges = numberOfCouncilPrivileges;

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
     * This method returns the number of council privileges
     * @return the number of council privileges
     */
    public int getNumberOfCouncilPrivileges(){
        return this.numberOfCouncilPrivileges;
    }

    /**
     * This method returns the array of privileges
     * @return the array of council privileges
     */
    public Privilege[] getPrivileges(){
        return this.privileges;
    }

    @Override
    public String toString(){
        return "Council privilege number: " + numberOfCouncilPrivileges + "\n";
    }

    /**
     * This method is used to choose the council privilege
     * @param player the player that wants to perform the action
     * @param informationCallback interface to manage actions that requires multiple interactions with the user
     */
    public void chooseCouncilPrivilege(Player player, InformationCallback informationCallback){
        List<Privilege> choices = informationCallback.chooseCouncilPrivilege("council-privilege", this);
        for(Privilege privilege : choices){
            for (Map.Entry<ResourceType, Integer> entry: privilege.getValuables().getResources().entrySet()) {
                if(entry.getValue() > 0) {
                    player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
                    player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalResourcesMalus().get(entry.getKey()));
                }
            }

            for (Map.Entry<PointType, Integer> entry: privilege.getValuables().getPoints().entrySet()) {
                if(entry.getValue() > 0) {
                    player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
                    player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalPointsMalus().get(entry.getKey()));
                }
            }
        }
    }
}
