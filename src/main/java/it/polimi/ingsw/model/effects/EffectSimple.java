package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.CouncilPrivilege;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PointsAndResources;

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
     * Council privilege object.
     */
    private CouncilPrivilege councilPrivilege;

    /**
     * Class constructor.
     */
    public EffectSimple(){
        this.valuable = new PointsAndResources();
        this.councilPrivilege = new CouncilPrivilege();
        super.effectType = this.getClass().getSimpleName();
    }

    ///////WARNING: These methods are used just to create the cards for JSON, delete them after their creation
    public void setValuable(PointsAndResources valuable){
        this.valuable = valuable;
    }

    public void setCouncilPrivilege(CouncilPrivilege privilege){
        this.councilPrivilege = privilege;
    }
    ////////END WARNING

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
        System.out.print("run immediate effect simple");
    }

}
