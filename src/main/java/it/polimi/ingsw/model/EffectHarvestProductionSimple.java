package it.polimi.ingsw.model;

/**
 * Created by lorenzo on 01/06/17.
 */
public class EffectHarvestProductionSimple extends Effect{

    private Integer minDiceValue;

    private ActionType actionType;

    private PointsAndResources valuable;

    private CouncilPrivilege councilPrivilege;

    public EffectHarvestProductionSimple(){
        this.valuable = new PointsAndResources();
        this.councilPrivilege = new CouncilPrivilege();
    }

    public void setMinDiceValue(Integer index){
        this.minDiceValue = index;
    }

    public Integer getMinDiceValue(){
        return this.minDiceValue;
    }

    public void setActionType(ActionType type){
        this.actionType = type;
    }

    public ActionType getActionType(){
        return this.actionType;
    }

    public void setValuable(PointsAndResources valuable){
        this.valuable = valuable;
    }

    public PointsAndResources getValuable(){
        return this.valuable;
    }

    public void runEffect(){
        System.out.print("run permanent effect harvest/production simple");
    }

    public void setCouncilPrivilege(CouncilPrivilege councilPrivilege){
        this.councilPrivilege = councilPrivilege;
    }

    public CouncilPrivilege getCouncilPrivilege(){
        return this.councilPrivilege;
    }

    public void runEffect(Player player){
        System.out.print("run permanent effect harvest/production simple");
    }


}
