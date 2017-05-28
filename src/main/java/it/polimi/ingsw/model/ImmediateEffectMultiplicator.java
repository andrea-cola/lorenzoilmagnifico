package it.polimi.ingsw.model;

/**
 * Created by lorenzo on 23/05/17.
 */
public class ImmediateEffectMultiplicator extends ImmediateEffect {

    private Integer amount;

    public void setAmount(Integer amount){
        this.amount = amount;
    }

    public Integer getAmount(){
        return this.amount;
    }

    public void runEffect(){
        System.out.print("run immediate effect moltiplicator");
    }

}
