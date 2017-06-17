package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by lorenzo on 16/06/17.
 */
public class Dice {

    /**
     * Map of all dices and relative random values
     */
    private Map<FamilyMemberColor, Integer> values;

    public Dice(){
        this.values = new HashMap<>();
    }

    /**
     * Set random value for dices
     */
    public void setValues(){
        Random randomValue = new Random();
        int minValue = 1;
        int maxValue = 6;

        this.values.put(FamilyMemberColor.BLACK, randomValue.nextInt((maxValue - minValue) + 1) + minValue);
        this.values.put(FamilyMemberColor.ORANGE, randomValue.nextInt((maxValue - minValue) + 1) + minValue);
        this.values.put(FamilyMemberColor.WHITE, randomValue.nextInt((maxValue - minValue) + 1) + minValue);
        this.values.put(FamilyMemberColor.NEUTRAL, 0);
    }

    /**
     * Get dices' value
     */
    public Map<FamilyMemberColor, Integer> getValues(){
        return this.values;
    }

}
