package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.EffectHarvestProductionSimple;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lorenzo on 07/07/17.
 */
public class ActionSpaceExtendedTest {
    @Test
    public void setNotAccessible() throws Exception {
        EffectHarvestProductionSimple effect = new EffectHarvestProductionSimple();
        ActionSpaceExtended actionSpace = new ActionSpaceExtended(ActionType.PRODUCTION, 3, effect);
        assertTrue(actionSpace.isAccessible());
        actionSpace.setNotAccessible();
        assertFalse(actionSpace.isAccessible());
    }

    @Test
    public void isAccessible() throws Exception {
        EffectHarvestProductionSimple effect = new EffectHarvestProductionSimple();
        ActionSpaceExtended actionSpace = new ActionSpaceExtended(ActionType.PRODUCTION, 3, effect);
        assertTrue(actionSpace.isAccessible());
    }

    @Test
    public void getActionSpaceType() throws Exception {
        EffectHarvestProductionSimple effect = new EffectHarvestProductionSimple();
        ActionSpaceExtended actionSpace = new ActionSpaceExtended(ActionType.PRODUCTION, 3, effect);
        assertEquals(ActionType.PRODUCTION, actionSpace.getActionSpaceType());
    }

    @Test
    public void getDiceValueMalus() throws Exception {
        EffectHarvestProductionSimple effect = new EffectHarvestProductionSimple();
        ActionSpaceExtended actionSpace = new ActionSpaceExtended(ActionType.PRODUCTION, 3, effect);

        assertEquals(3, actionSpace.getDiceValueMalus());
    }

    @Test
    public void getEffect() throws Exception {

    }

    @Test
    public void checkAccessibility() throws Exception {

    }

    @Test
    public void familyMemberCanBePlaced() throws Exception {
    }

    @Test
    public void reset() throws Exception {
    }

}