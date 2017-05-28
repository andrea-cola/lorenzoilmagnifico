package it.polimi.ingsw.model;

/**
 * Created by lorenzo on 23/05/17.
 */
public class Point {

    private enum PointType{
        VICTORY,
        MILITARY,
        FAITH
    }

    private PointType type;

    private Integer amount;

    public void setAmount(Integer amount){
        this.amount = amount;
    }

    public Integer getAmount(){
        return this.amount;
    }

    public PointType getType(){
        return this.type;
    }

}
