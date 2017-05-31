package it.polimi.ingsw.model;

import sun.jvm.hotspot.types.PointerType;

/**
 * Created by lorenzo on 23/05/17.
 */
public class Point {

    private PointType type;

    private Integer amount;

    public Point(PointType type, Integer amount){
        this.type = type;
        this.amount = amount;
    }

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
