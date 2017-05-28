package it.polimi.ingsw.model;

import java.awt.*;

/**
 * Created by lorenzo on 23/05/17.
 */
public class DevelopmentCard {

    private String name;

    private DevelopmentCardColor color;

    private DevelopmentCardColor cardColor;

    private Requisite requisite;

    private ImmediateEffect immediateEffect;

    private PermanentEffect permanentEffect;

    public DevelopmentCard(DevelopmentCardColor color){
        this.color = color;
    }

    public String getName(){
        return this.name;
    }

    public DevelopmentCardColor getColor(){
        return  this.color;
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


}
