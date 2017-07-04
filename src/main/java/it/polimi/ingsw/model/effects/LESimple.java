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
    private int numberOfCouncilPrivileges;

    /**
     * Class constructor.
     */
    public LESimple(){
        super.setEffectType(this.getClass().getSimpleName());
        this.valuable = new PointsAndResources();
        this.numberOfCouncilPrivileges = 0;
    }

    /**
     * Method to run the effect.
     * @param player that takes advantage of the effect.
     */
    @Override
    public void runEffect(Player player, InformationCallback informationCallback){
        for (Map.Entry<ResourceType, Integer> entry: this.valuable.getResources().entrySet())
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());

        for (Map.Entry<PointType, Integer> entry: this.valuable.getPoints().entrySet())
            player.getPersonalBoard().getValuables().increase(entry.getKey(), entry.getValue());

        CouncilPrivilege councilPrivilege = new CouncilPrivilege(numberOfCouncilPrivileges);
        councilPrivilege.chooseCouncilPrivilege(player, informationCallback);
    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String toString() {
        return "Earn resources: " + valuable.toString() + "council privileges: " + numberOfCouncilPrivileges;
    }
}
