package it.polimi.ingsw.model;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PointsAndResourcesTest {
    @Test
    public void increase() throws Exception {
        PointsAndResources valuables = new PointsAndResources();

        valuables.increase(ResourceType.COIN, 4);
        int result = valuables.getResources().get(ResourceType.COIN);
        assertEquals(4, result);

        valuables.increase(PointType.FAITH, 3);
        int result2 = valuables.getPoints().get(PointType.FAITH);
        assertEquals(3, result2);
    }

    @Test
    public void decrease() throws Exception {
        PointsAndResources valuables = new PointsAndResources();

        valuables.decrease(ResourceType.COIN, 3);
        int result = valuables.getResources().get(ResourceType.COIN);
        assertEquals(-3, result);

        valuables.decrease(PointType.FAITH, 10);
        int result2 = valuables.getPoints().get(PointType.FAITH);
        assertEquals(-10, result2);
    }

    @Test
    public void checkDecrease() throws Exception {
        PointsAndResources valuablesOwned = new PointsAndResources();
        valuablesOwned.increase(ResourceType.COIN, 5);
        valuablesOwned.increase(PointType.FAITH, 3);

        PointsAndResources valuablesToPay = new PointsAndResources();
        valuablesToPay.increase(ResourceType.COIN, 11);
        valuablesToPay.increase(PointType.FAITH, 1);

        assertFalse(valuablesOwned.checkDecrease(valuablesToPay));

        valuablesOwned.increase(ResourceType.COIN, 10);

        assertTrue(valuablesOwned.checkDecrease(valuablesToPay));
    }

    @Test
    public void decreaseAll() throws Exception {
        PointsAndResources valuables = new PointsAndResources();
        valuables.increase(ResourceType.COIN, 10);
        valuables.increase(PointType.FAITH, 10);

        PointsAndResources valuablesToDecrease = new PointsAndResources();
        valuablesToDecrease.increase(ResourceType.COIN, 3);
        valuablesToDecrease.increase(PointType.FAITH, 8);

        valuables.decreaseAll(valuablesToDecrease);

        int result = valuables.getResources().get(ResourceType.COIN);
        int result2 = valuables.getPoints().get(PointType.FAITH);

        assertEquals(7, result);
        assertEquals(2, result2);

    }

    @Test
    public void getResources() throws Exception {
        PointsAndResources valuables = new PointsAndResources();
        int result = valuables.getResources().get(ResourceType.COIN);
        assertEquals(0, result);
    }

    @Test
    public void getPoints() throws Exception {
        PointsAndResources valuables = new PointsAndResources();
        int result = valuables.getPoints().get(PointType.FAITH);
        assertEquals(0, result);
    }

}