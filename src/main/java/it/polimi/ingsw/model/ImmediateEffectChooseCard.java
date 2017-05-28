package it.polimi.ingsw.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by lorenzo on 23/05/17.
 */
public class ImmediateEffectChooseCard extends ImmediateEffect{

    private ArrayList<Color> colors;

    private ArrayList<Action> actions;

    private Integer maxDiceValue;

    public void setMaxDiceValue(Integer value){
        this.maxDiceValue = value;
    }

    public Integer getMaxDiceValue(){
        return this.maxDiceValue;
    }

    public void runEffect(){
        System.out.print("run immediate effect choose card");
    }

}
