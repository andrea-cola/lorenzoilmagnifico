package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExcommunicationValuesTest {
    @Test
    public void increaseNormalValuablesMalus() throws Exception {
        ExcommunicationValues values = new ExcommunicationValues();

        values.increaseNormalValuablesMalus(ResourceType.COIN, 3);
        values.increaseNormalValuablesMalus(PointType.FAITH, 5);

        int result = values.getNormalResourcesMalus().get(ResourceType.COIN);
        int result2 = values.getNormalPointsMalus().get(PointType.FAITH);

        assertEquals(3, result);
        assertEquals(5, result2);
    }


    @Test
    public void increaseFinalValuablesIndexMalus() throws Exception {
        ExcommunicationValues values = new ExcommunicationValues();

        values.increaseFinalValuablesIndexMalus(ResourceType.COIN, 10);
        values.increaseFinalValuablesIndexMalus(PointType.FAITH, 4);

        int result = values.getFinalResourcesIndexMalus().get(ResourceType.COIN);
        int result2 = values.getFinalPointsIndexMalus().get(PointType.FAITH);

        assertEquals(10, result);
        assertEquals(4, result2);
    }

    @Test
    public void setHarvestProductionDiceMalus() throws Exception {
        ExcommunicationValues values = new ExcommunicationValues();

        values.setHarvestProductionDiceMalus(ActionType.HARVEST, 2);

        int result = values.getHarvestProductionDiceMalus().get(ActionType.HARVEST);

        assertEquals(2, result);
    }

    @Test
    public void setDevelopmentCardDiceMalus() throws Exception {
        ExcommunicationValues values = new ExcommunicationValues();

        values.setDevelopmentCardDiceMalus(DevelopmentCardColor.GREEN, 3);

        int result = values.getDevelopmentCardDiceMalus().get(DevelopmentCardColor.GREEN);

        assertEquals(3, result);
    }

    @Test
    public void setDevelopmentCardGetFinalPoints() throws Exception {
        ExcommunicationValues values = new ExcommunicationValues();

        values.setDevelopmentCardGetFinalPoints(DevelopmentCardColor.GREEN, false);

        Boolean result = values.getDevelopmentCardGetFinalPoints().get(DevelopmentCardColor.GREEN);

        assertFalse(result);
    }

    @Test
    public void setFinalResourcesDevCardIndexMalus() throws Exception {
        ExcommunicationValues values = new ExcommunicationValues();

        values.setFinalResourcesDevCardIndexMalus(ResourceType.STONE, 4);

        int result = values.getFinalResourcesDevCardIndexMalus().get(ResourceType.STONE);

        assertEquals(4, result);
    }

    @Test
    public void setMarketIsAvailable() throws Exception {
        ExcommunicationValues values = new ExcommunicationValues();

        values.setMarketIsAvailable(true);
        assertTrue(values.getMarketIsAvailable());
        values.setMarketIsAvailable(false);
        assertFalse(values.getMarketIsAvailable());
    }

    @Test
    public void setSkipFirstTurn() throws Exception {
        ExcommunicationValues values = new ExcommunicationValues();

        values.setSkipFirstTurn(true);
        assertTrue(values.getSkipFirstTurn());
        values.setSkipFirstTurn(false);
        assertFalse(values.getSkipFirstTurn());

    }

    @Test
    public void setNumberOfSlaves() throws Exception {
        ExcommunicationValues values = new ExcommunicationValues();

        values.setNumberOfSlaves(4);

        int result = values.getNumberOfSlaves();

        assertEquals(4, result);
    }

    @Test
    public void getNormalPointsMalus() throws Exception {
    }

    @Test
    public void getNormalResourcesMalus() throws Exception {
    }

    @Test
    public void getHarvestProductionDiceMalus() throws Exception {
    }

    @Test
    public void getDevelopmentCardDiceMalus() throws Exception {
    }

    @Test
    public void getMarketIsAvailable() throws Exception {
    }

    @Test
    public void getNumberOfSlaves() throws Exception {
    }

    @Test
    public void getDevelopmentCardGetFinalPoints() throws Exception {
    }

    @Test
    public void getFinalPointsIndexMalus() throws Exception {
    }

    @Test
    public void getSkipFirstTurn() throws Exception {
    }

    @Test
    public void getFinalResourcesIndexMalus() throws Exception {
    }

    @Test
    public void getFinalResourcesDevCardIndexMalus() throws Exception {
    }

}