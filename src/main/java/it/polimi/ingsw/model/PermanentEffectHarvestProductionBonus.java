package it.polimi.ingsw.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by lorenzo on 23/05/17.
 */
public class PermanentEffectHarvestProductionBonus extends PermanentEffect {

    private ArrayList<Action> actionBonus;

    private ArrayList<Color> colors;

    private ArrayList<Resource> resources;

    private Integer diceBonus;


    public void setDiceBonus(Integer diceBonus){
        this.diceBonus = diceBonus;
    }

    public Integer getDiceBonus(){
        return this.diceBonus;
    }

    public void runEffect(){
        System.out.print("run permanent effect harvest/production bonus");
    }
}
