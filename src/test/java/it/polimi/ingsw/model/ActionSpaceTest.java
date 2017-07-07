package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.EffectHarvestProductionSimple;
import org.junit.Test;

import static org.junit.Assert.*;


public class ActionSpaceTest {
    @Test
    public void getActionSpaceType() throws Exception {
        EffectHarvestProductionSimple effect = new EffectHarvestProductionSimple();

        ActionSpace space1 = new ActionSpace(ActionType.HARVEST, effect);
        ActionSpace space2 = new ActionSpace(ActionType.PRODUCTION, effect);

        assertEquals(ActionType.HARVEST, space1.getActionSpaceType());
        assertEquals(ActionType.PRODUCTION, space2.getActionSpaceType());
    }

    @Test
    public void getActionSpaceEffect() throws Exception {

    }

    @Test
    public void isEmpty() throws Exception {
        EffectHarvestProductionSimple effect = new EffectHarvestProductionSimple();
        ActionSpace space = new ActionSpace(ActionType.HARVEST, effect);

        assertTrue(space.isEmpty());
    }

    @Test
    public void setEmpty() throws Exception {
        EffectHarvestProductionSimple effect = new EffectHarvestProductionSimple();
        ActionSpace space = new ActionSpace(ActionType.HARVEST, effect);

        space.setEmpty(false);

        assertTrue(!space.isEmpty());
    }

    @Test
    public void getFamilyMemberColor() throws Exception {
        Player player = new Player();

        EffectHarvestProductionSimple effect = new EffectHarvestProductionSimple();
        ActionSpace space = new ActionSpace(ActionType.HARVEST, effect);

        space.familyMemberCanBePlaced(player, FamilyMemberColor.ORANGE);

        FamilyMemberColor familyMemberColor = space.getFamilyMemberColor();

        assertEquals(FamilyMemberColor.ORANGE, familyMemberColor);
    }

    @Test(expected = java.lang.Exception.class)
    public void checkAccessibility() throws Exception {
        Player player = new Player();
        player.setUsername("player1");

        EffectHarvestProductionSimple effect = new EffectHarvestProductionSimple();
        ActionSpace space = new ActionSpace(ActionType.HARVEST, effect);

        space.familyMemberCanBePlaced(player, FamilyMemberColor.BLACK);

        space.checkAccessibility(player, FamilyMemberColor.ORANGE);
    }

    @Test
    public void familyMemberCanBePlaced() throws Exception {
        EffectHarvestProductionSimple effect = new EffectHarvestProductionSimple();

        ActionSpace space = new ActionSpace(ActionType.HARVEST, effect);

        Player player = new Player();

        space.familyMemberCanBePlaced(player, FamilyMemberColor.BLACK);

        assertTrue(!space.isEmpty());
    }

    @Test
    public void reset() throws Exception {
        EffectHarvestProductionSimple effect = new EffectHarvestProductionSimple();

        ActionSpace space = new ActionSpace(ActionType.HARVEST, effect);

        space.reset();

        assertTrue(space.isEmpty());
    }
}