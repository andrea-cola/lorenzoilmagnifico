package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by lorenzo on 07/07/17.
 */
public class DiceTest {
    @Test
    public void setValues() throws Exception {
        Dice dice = new Dice();
        dice.setValues();

        for (Map.Entry<FamilyMemberColor, Integer> entry : dice.getValues().entrySet()){
            if (entry.getKey().equals(FamilyMemberColor.NEUTRAL))
                assertEquals(0, (int)entry.getValue());
            else
                assertTrue(entry.getValue() >= 1 && entry.getValue() <= 6);
        }
    }

    @Test
    public void getValues() throws Exception {
        Dice dice = new Dice();
        assertTrue(dice.getValues().get(FamilyMemberColor.BLACK) == null);

    }

}