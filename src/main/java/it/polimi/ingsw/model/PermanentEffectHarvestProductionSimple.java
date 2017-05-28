package it.polimi.ingsw.model;

/**
 * Created by lorenzo on 23/05/17.
 */
public class PermanentEffectHarvestProductionSimple extends PermanentEffect {

    private Integer harvestProductionIndex;

    private ImmediateEffect immediateEffect;

    public void setHarvestProductionIndex(Integer index){
        this.harvestProductionIndex = index;
    }

    public Integer getHarvestProductionIndex(){
        return this.harvestProductionIndex;
    }

    public void runEffect(){
        System.out.print("run permanent effect harvest/production simple");
    }
}
