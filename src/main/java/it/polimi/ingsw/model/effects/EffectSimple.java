package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import javafx.util.Pair;

import java.util.Iterator;
import java.util.Map;

/**
 * This class represent the immediate simple effect.
 * A immediate simple effect allow the player to get resources and points.
 */
public class EffectSimple extends Effect{

    /**
     * Points and resources that user obtain.
     */
    private PointsAndResources valuable;

    /**
     * Number of council privileges
     */
    private int numberOfCouncilPrivileges;

    /**
     * Class constructor.
     */
    public EffectSimple(){
        this.valuable = new PointsAndResources();

        super.effectType = this.getClass().getSimpleName();
    }

    /**
     * Return points and resources of the effect.
     * @return points and resources.
     */
    public PointsAndResources getValuable(){
        return this.valuable;
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

        //logica di gestione del privilegio del consiglio
        if (this.numberOfCouncilPrivileges > 0){
            CouncilPrivilege councilPrivilege = new CouncilPrivilege();
            for (int i = 0; i < this.numberOfCouncilPrivileges; i++){
                //TODO implementare logica di gestione della scelta dei privilegi

            }
        }

    }

    /**
     * Get a description of the current effect.
     */
    @Override
    public String getDescription() {
        String header = this.effectType + "\n";
        String resources = "Resources:\n" + valuable.toString();
        String privilege = "Council privileges: " + numberOfCouncilPrivileges;
        return new StringBuilder(header).append(resources).append(privilege).toString();
    }
}
