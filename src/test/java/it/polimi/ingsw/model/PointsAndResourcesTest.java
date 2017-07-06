package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;


public class PointsAndResourcesTest {
    @Test
    public void increase() throws Exception {
        PointsAndResources value = new PointsAndResources();
        value.increase(ResourceType.COIN, 1);

        int result = value.getResources().get(ResourceType.COIN);

        assertEquals(1, result);
    }

    @Test
    public void increase1() throws Exception {
        PointsAndResources value = new PointsAndResources();

        value.increase(PointType.VICTORY, 1);

        int result = value.getPoints().get(PointType.VICTORY);

        assertEquals(1, result);
    }

    @Test
    public void decrease() throws Exception {
        PointsAndResources value = new PointsAndResources();
        value.increase(ResourceType.COIN, 5);
        value.decrease(ResourceType.COIN, 3);

        int result = value.getResources().get(ResourceType.COIN);

        assertEquals(2, result);
    }

    @Test
    public void decrease1() throws Exception {
        PointsAndResources value = new PointsAndResources();
        value.increase(PointType.VICTORY, 5);
        value.decrease(PointType.VICTORY, 3);

        int result = value.getPoints().get(PointType.VICTORY);

        assertEquals(2, result);
    }

    @Test(expected = java.lang.Exception.class)
    public void checkDecrease() throws Exception{
        PointsAndResources valuablesOwned = new PointsAndResources();
        valuablesOwned.increase(ResourceType.COIN, 3);

        PointsAndResources valuablesToPay = new PointsAndResources();
        valuablesToPay.increase(ResourceType.COIN, 4);

        valuablesOwned.checkDecrease(valuablesToPay);
    }



    @Test
    public void getResources() throws Exception {
    }

    @Test
    public void getPoints() throws Exception {
    }


}