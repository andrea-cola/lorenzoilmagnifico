package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import java.util.Map;

/**
 * This class represent the immediate simple effect.
 * A immediate simple effect allow the player to get resources and points.
 */
public class LESimple extends LeaderEffect{

    /**
     * Points and resources that user obtain.
     */
    private PointsAndResources valuable;

    /**
     * Council privilege object.
     */
    private CouncilPrivilege councilPrivilege;

    /**
     * Class constructor.
     */
    public LESimple(){
        super.effectType = this.getClass().getSimpleName();
        this.valuable = new PointsAndResources();
        this.councilPrivilege = new CouncilPrivilege();
    }

    /**
     * Return points and resources of the effect.
     * @return points and resources.
     */
    public PointsAndResources getValuable(){
        return this.valuable;
    }

    /**
     * Return council privilege object.
     * @return council privilege object.
     */
    public CouncilPrivilege getCouncilPrivilege(){
        return this.councilPrivilege;
    }

    /**
     * Method to run the effect.
     * @param player that takes advantage of the effect.
     */
    @Override
    public void runEffect(Player player){
        //updates player's resources
        for (Map.Entry<ResourceType, Integer> entry: this.valuable.getResources().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }

        //updates player's points
        for (Map.Entry<PointType, Integer> entry: this.valuable.getPoints().entrySet()) {
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        String header = this.effectType + "\n";
        String resources = "Resources:\n" + valuable.toString();
        String privilege = councilPrivilege.toString();
        return new StringBuilder(header).append(resources).append(privilege).toString();
    }
}
