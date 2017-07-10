package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.EffectSimple;
import org.junit.Test;

import static org.junit.Assert.*;


public class CouncilPalaceTest {
    @Test
    public void getMinFamilyMemberDiceValue() throws Exception {
        EffectSimple effect = new EffectSimple();
        CouncilPalace councilPalace = new CouncilPalace(1, effect);

        int result = councilPalace.getMinFamilyMemberDiceValue();

        assertEquals(1, result);
    }

    @Test
    public void getImmediateEffect() throws Exception {
        EffectSimple effect = new EffectSimple();
        CouncilPalace councilPalace = new CouncilPalace(1, effect);

        assertTrue(councilPalace.getImmediateEffect().getClass().equals(EffectSimple.class));
    }

    @Test
    public void fifoAddPlayer() throws Exception {
        Player player = new Player();

        EffectSimple effect = new EffectSimple();
        CouncilPalace councilPalace = new CouncilPalace(1, effect);

        councilPalace.fifoAddPlayer(player);

        assertTrue(councilPalace.getNewOrder().size() > 0);
    }


    @Test
    public void resetFifo() throws Exception {
        Player player = new Player();

        EffectSimple effect = new EffectSimple();
        CouncilPalace councilPalace = new CouncilPalace(1, effect);

        councilPalace.fifoAddPlayer(player);

        assertTrue(councilPalace.getNewOrder().size() > 0);

        councilPalace.resetFifo();

        assertTrue(councilPalace.getNewOrder().size() == 0);
    }

    @Test
    public void fifoGetPlayer() throws Exception {
        Player player = new Player();

        EffectSimple effect = new EffectSimple();
        CouncilPalace councilPalace = new CouncilPalace(1, effect);

        councilPalace.fifoAddPlayer(player);

        int size = councilPalace.getNewOrder().size();

        councilPalace.fifoGetPlayer();

        assertEquals(size - 1, councilPalace.getNewOrder().size());
    }

    @Test(expected = java.lang.Exception.class)
    public void familyMemberCanBePlaced() throws Exception {
        Player player = new Player();

        EffectSimple effect = new EffectSimple();
        CouncilPalace councilPalace = new CouncilPalace(1, effect);

        councilPalace.familyMemberCanBePlaced(player, FamilyMemberColor.ORANGE, 0);
    }

}