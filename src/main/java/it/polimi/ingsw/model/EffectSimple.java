package it.polimi.ingsw.model;

/**
 * Created by lorenzo on 01/06/17.
 */
public class EffectSimple extends Effect{

    private PointsAndResources valuable;

    private CouncilPrivilege councilPrivilege;

    public EffectSimple(){
        this.valuable = new PointsAndResources();
        this.councilPrivilege = new CouncilPrivilege();
    }

    ///////WARNING: These methods are used just to create the cards for JSON, delete them after their creation
    public void setValuable(PointsAndResources valuable){
        this.valuable = valuable;
    }

    public void setCouncilPrivilege(CouncilPrivilege privilege){
        this.councilPrivilege = privilege;
    }
    ////////

    public PointsAndResources getValuable(){
        return this.valuable;
    }

    public CouncilPrivilege getCouncilPrivilege(){
        return this.councilPrivilege;
    }



    public void runEffect(Player player){
        System.out.print("run immediate effect simple");
    }

}
