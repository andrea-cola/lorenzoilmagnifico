package it.polimi.ingsw.model;

/**
 * Created by lorenzo on 23/05/17.
 */
public class PermanentEffectHarvestProductionConversion extends PermanentEffect {

    private Requisite input1;

    private Requisite input2;

    private Requisite output1;

    private Requisite output2;

    public void runEffect(){
        System.out.print("run permanent effect harvest/production conversion");
    }

}
