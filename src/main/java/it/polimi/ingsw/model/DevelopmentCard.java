package it.polimi.ingsw.model;

import java.awt.*;
import java.util.Map;

/**
 * Created by lorenzo on 23/05/17.
 */
public class DevelopmentCard {

    private String name;

    private Integer id;

    private Integer period;

    private DevelopmentCardColor cardColor;

    private PointsAndResources cost;

    private boolean multipleRequisiteSelectionEnabled;

    private Integer militaryPointsRequired;

    private EffectSimple immediateEffect;

    private EffectFinalPoints permanentEffect;

    public DevelopmentCard(){ }


    ///////WARNING: These methods are used just to create the cards for JSON, delete them after their creation
    public void setId(Integer id){
        this.id = id;
    }

    public void setPeriod(Integer period){
        this.period = period;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCardColor(DevelopmentCardColor color){
        this.cardColor = color;
    }

    public void setImmediateEffect(EffectSimple effect){
        this.immediateEffect = effect;
    }

    public void setPermanentEffect(EffectFinalPoints effect){
        this.permanentEffect = effect;
    }

    public void setCost(PointsAndResources cost){
        this.cost = cost;
    }

    public void setCost(PointType type, Integer value){
        this.cost.increase(type, value);
    }

    public void setMultipleRequisiteSelectionEnabled(boolean flag){
        this.multipleRequisiteSelectionEnabled = flag;
    }

    public void setMilitaryPointsRequired(Integer pointsRequired){
        this.militaryPointsRequired = pointsRequired;
    }
    ///////

    public Integer getId(){
        return this.id;
    }

    public Integer getPeriod(){
        return this.period;
    }

    public String getName(){
        return this.name;
    }

    public DevelopmentCardColor getColor(){
        return  this.cardColor;
    }

    public PointsAndResources getCost(){
        return this.cost;
    }

    public EffectSimple getImmediateEffect(){
        return this.immediateEffect;
    }

    public EffectFinalPoints getPermanentEffect(){
        return this.permanentEffect;
    }

    public boolean getMultipleRequisiteSelectionEnabled(){
        return this.multipleRequisiteSelectionEnabled;
    }

    public Integer getMilitaryPointsRequired(){
        return this.militaryPointsRequired;
    }
}
