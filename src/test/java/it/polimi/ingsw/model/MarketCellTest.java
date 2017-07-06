package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.effects.EffectSimple;
import org.junit.Test;

import static org.junit.Assert.*;


public class MarketCellTest {
    @Test
    public void isEmpty() throws Exception {
        EffectSimple effectSimple = new EffectSimple();
        MarketCell cell = new MarketCell(1, effectSimple);

        assertTrue(cell.isEmpty());
    }

    @Test
    public void setEmpty() throws Exception {
    }

    @Test
    public void isAccessible() throws Exception {
        EffectSimple effectSimple = new EffectSimple();
        MarketCell cell = new MarketCell(1, effectSimple);

        assertTrue(cell.isAccessible());
    }

    @Test
    public void setNotAccessible() throws Exception {
        EffectSimple effectSimple = new EffectSimple();
        MarketCell cell = new MarketCell(1, effectSimple);

        cell.setNotAccessible();

        assertTrue(!cell.isEmpty());
        assertTrue(!cell.isAccessible());
    }

    @Test
    public void getMarketCellImmediateEffect() throws Exception {
    }

    @Test
    public void getMinFamilyMemberValue() throws Exception {
        EffectSimple effectSimple = new EffectSimple();
        MarketCell cell = new MarketCell(1, effectSimple);

        int minFamilyMemberValue = cell.getMinFamilyMemberValue();

        assertEquals(1, minFamilyMemberValue);
    }

    @Test
    public void familyMemberCanBePlaced() throws Exception {
        Player player = new Player();

        EffectSimple effectSimple = new EffectSimple();

        MarketCell cell = new MarketCell(1, effectSimple);

        int servants = 1;

        FamilyMemberColor familyMemberColor = FamilyMemberColor.ORANGE;

        try {
            cell.familyMemberCanBePlaced(player, familyMemberColor, servants);
        }catch (GameException e){
            System.out.print(e.getError());
        }

        int index = player.getPersonalBoard().getFamilyMembersUsed().size();

        assertTrue(player.getPersonalBoard().getFamilyMembersUsed().get(index - 1).equals(FamilyMemberColor.ORANGE));
        assertTrue(!cell.isEmpty());
    }

}