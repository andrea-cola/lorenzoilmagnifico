package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.EffectHarvestProductionSimple;
import it.polimi.ingsw.model.effects.EffectSimple;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by lorenzo on 07/07/17.
 */
public class DevelopmentCardTest {
    @Test
    public void getId() throws Exception {
        String name = "Avamposto Commerciale";
        int id = 1;
        int period = 1;
        PointsAndResources cost = new PointsAndResources();
        int militaryPointsRequired = 0;
        EffectSimple immediateEffect = new EffectSimple();
        EffectHarvestProductionSimple permanentEffect = new EffectHarvestProductionSimple();

        DevelopmentCard card = new DevelopmentCard(name, id, period, DevelopmentCardColor.GREEN, cost,
                false, militaryPointsRequired, immediateEffect, permanentEffect);

        assertEquals(1, card.getId());
    }

    @Test
    public void getPeriod() throws Exception {
        String name = "Avamposto Commerciale";
        int id = 1;
        int period = 1;
        PointsAndResources cost = new PointsAndResources();
        int militaryPointsRequired = 0;
        EffectSimple immediateEffect = new EffectSimple();
        EffectHarvestProductionSimple permanentEffect = new EffectHarvestProductionSimple();

        DevelopmentCard card = new DevelopmentCard(name, id, period, DevelopmentCardColor.GREEN, cost,
                false, militaryPointsRequired, immediateEffect, permanentEffect);

        assertEquals(1, card.getPeriod());
    }

    @Test
    public void getName() throws Exception {
        String name = "Avamposto Commerciale";
        int id = 1;
        int period = 1;
        PointsAndResources cost = new PointsAndResources();
        int militaryPointsRequired = 0;
        EffectSimple immediateEffect = new EffectSimple();
        EffectHarvestProductionSimple permanentEffect = new EffectHarvestProductionSimple();

        DevelopmentCard card = new DevelopmentCard(name, id, period, DevelopmentCardColor.GREEN, cost,
                false, militaryPointsRequired, immediateEffect, permanentEffect);

        assertEquals("Avamposto Commerciale", card.getName());
    }

    @Test
    public void getColor() throws Exception {
        String name = "Avamposto Commerciale";
        int id = 1;
        int period = 1;
        PointsAndResources cost = new PointsAndResources();
        int militaryPointsRequired = 0;
        EffectSimple immediateEffect = new EffectSimple();
        EffectHarvestProductionSimple permanentEffect = new EffectHarvestProductionSimple();

        DevelopmentCard card = new DevelopmentCard(name, id, period, DevelopmentCardColor.GREEN, cost,
                false, militaryPointsRequired, immediateEffect, permanentEffect);

        assertEquals(DevelopmentCardColor.GREEN, card.getColor());
    }

    @Test
    public void getCost() throws Exception {
        String name = "Avamposto Commerciale";
        int id = 1;
        int period = 1;
        PointsAndResources cost = new PointsAndResources();
        int militaryPointsRequired = 0;
        EffectSimple immediateEffect = new EffectSimple();
        EffectHarvestProductionSimple permanentEffect = new EffectHarvestProductionSimple();

        DevelopmentCard card = new DevelopmentCard(name, id, period, DevelopmentCardColor.GREEN, cost,
                false, militaryPointsRequired, immediateEffect, permanentEffect);

        for (Map.Entry<ResourceType, Integer> entry : card.getCost().getResources().entrySet()){
            assertEquals(0, (int)entry.getValue());
        }
    }

    @Test
    public void getImmediateEffect() throws Exception {
        String name = "Avamposto Commerciale";
        int id = 1;
        int period = 1;
        PointsAndResources cost = new PointsAndResources();
        int militaryPointsRequired = 0;
        EffectSimple immediateEffect = new EffectSimple();
        EffectHarvestProductionSimple permanentEffect = new EffectHarvestProductionSimple();

        DevelopmentCard card = new DevelopmentCard(name, id, period, DevelopmentCardColor.GREEN, cost,
                false, militaryPointsRequired, immediateEffect, permanentEffect);

        EffectSimple effect = new EffectSimple();
        assertTrue(effect.getClass().equals(card.getImmediateEffect().getClass()));
    }

    @Test
    public void getPermanentEffect() throws Exception {
        String name = "Avamposto Commerciale";
        int id = 1;
        int period = 1;
        PointsAndResources cost = new PointsAndResources();
        int militaryPointsRequired = 0;
        EffectSimple immediateEffect = new EffectSimple();
        EffectHarvestProductionSimple permanentEffect = new EffectHarvestProductionSimple();

        DevelopmentCard card = new DevelopmentCard(name, id, period, DevelopmentCardColor.GREEN, cost,
                false, militaryPointsRequired, immediateEffect, permanentEffect);

        EffectHarvestProductionSimple effect = new EffectHarvestProductionSimple();
        assertTrue(effect.getClass().equals(card.getPermanentEffect().getClass()));
    }

    @Test
    public void getMultipleRequisiteSelectionEnabled() throws Exception {
        String name = "Avamposto Commerciale";
        int id = 1;
        int period = 1;
        PointsAndResources cost = new PointsAndResources();
        int militaryPointsRequired = 0;
        EffectSimple immediateEffect = new EffectSimple();
        EffectHarvestProductionSimple permanentEffect = new EffectHarvestProductionSimple();

        DevelopmentCard card = new DevelopmentCard(name, id, period, DevelopmentCardColor.GREEN, cost,
                false, militaryPointsRequired, immediateEffect, permanentEffect);
        assertFalse(card.getMultipleRequisiteSelectionEnabled());
    }

    @Test
    public void getMilitaryPointsRequired() throws Exception {
        String name = "Avamposto Commerciale";
        int id = 1;
        int period = 1;
        PointsAndResources cost = new PointsAndResources();
        int militaryPointsRequired = 0;
        EffectSimple immediateEffect = new EffectSimple();
        EffectHarvestProductionSimple permanentEffect = new EffectHarvestProductionSimple();

        DevelopmentCard card = new DevelopmentCard(name, id, period, DevelopmentCardColor.GREEN, cost,
                false, militaryPointsRequired, immediateEffect, permanentEffect);
        assertEquals(0, card.getMilitaryPointsRequired());
    }


    @Test
    public void payCost() throws Exception {
    }

}