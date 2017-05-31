package it.polimi.ingsw.model;

import java.awt.*;

/**
 * Created by lorenzo on 23/05/17.
 */
public class DevelopmentCard {

    private String name;

    private Integer id;

    private Integer period;

    private DevelopmentCardColor cardColor;

    private Requisite requisite;

    private boolean multipleRequisiteSelectionEnabled;

    private ImmediateEffect immediateEffect;

    private PermanentEffect permanentEffect;

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

    public void setImmediateEffect(ImmediateEffect effect){
        this.immediateEffect = effect;
    }

    public void setPermanentEffect(PermanentEffect effect){
        this.permanentEffect = effect;
    }

    public void setRequisite(Requisite requisite){
        this.requisite = requisite;
    }

    public void setMultipleRequisiteSelectionEnabled(boolean flag){
        this.multipleRequisiteSelectionEnabled = flag;
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

    public Requisite getRequisite(){
        return this.requisite;
    }

    public ImmediateEffect getImmediateEffect(){
        return this.immediateEffect;
    }

    public PermanentEffect getPermanentEffect(){
        return this.permanentEffect;
    }

    public boolean getMultipleRequisiteSelectionEnabled(){
        return this.multipleRequisiteSelectionEnabled;
    }
}
