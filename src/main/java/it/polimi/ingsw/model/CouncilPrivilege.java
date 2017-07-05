package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * This class represent a council privilege.
 */
public class CouncilPrivilege implements Serializable{

    private int numberOfCouncilPrivileges;

    private Privilege[] privileges = new Privilege[5];

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

    public int getNumberOfCouncilPrivileges(){
        return this.numberOfCouncilPrivileges;
    }

    public Privilege[] getPrivileges(){
        return this.privileges;
    }

    public void setNotAvailable(int index){
        privileges[index].setNotAvailablePrivilege();
    }

    @Override
    public String toString(){
        return "Council privilege number: " + numberOfCouncilPrivileges + "\n";
    }

    public void chooseCouncilPrivilege(Player player, InformationCallback informationCallback){
        List<Privilege> choices = informationCallback.chooseCouncilPrivilege("council-privilege", this);
        for(Privilege privilege : choices){
            //updates player's resources
            for (Map.Entry<ResourceType, Integer> entry: privilege.getValuables().getResources().entrySet()) {
                if(entry.getValue() > 0) {
                    player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
                    //excommunication effect
                    player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalResourcesMalus().get(entry.getKey()));
                }
            }
            //updates player's points
            for (Map.Entry<PointType, Integer> entry: privilege.getValuables().getPoints().entrySet()) {
                if(entry.getValue() > 0) {
                    player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
                    //excommunication effect
                    player.getPersonalBoard().getValuables().decrease(entry.getKey(), player.getPersonalBoard().getExcommunicationValues().getNormalPointsMalus().get(entry.getKey()));
                }
            }
        }
    }
}
