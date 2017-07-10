package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.EffectHarvestProductionSimple;
import it.polimi.ingsw.model.effects.EffectSimple;
import org.junit.Test;

import static org.junit.Assert.*;


public class TowerTest {
    @Test
    public void isFree() throws Exception {
        Tower tower = new Tower(4);

        assertTrue(tower.isFree());
    }

    @Test
    public void setTowerCell() throws Exception {
        Tower tower = new Tower(4);

        //create a development card
        String name = "Possedimento";
        int id = 13;
        int period = 2;
        DevelopmentCardColor cardColor = DevelopmentCardColor.GREEN;
        PointsAndResources cost = new PointsAndResources();
        boolean multipleRequisiteSelectionEnabled = false;
        int militaryPointsRequired = 0;
        Effect immediateEffect = new EffectSimple();
        Effect permanentEffect = new EffectHarvestProductionSimple();

        DevelopmentCard card = new DevelopmentCard(name, id, period, cardColor, cost, multipleRequisiteSelectionEnabled,
                militaryPointsRequired, immediateEffect, permanentEffect);

        tower.setTowerCell(0, card);
    }

}