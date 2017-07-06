package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;


public class PrivilegeTest {
    @Test
    public void getValuables() throws Exception {
        PointsAndResources valuables = new PointsAndResources();
        valuables.increase(PointType.FAITH, 4);

        Privilege privilege = new Privilege(valuables, true);

        int result = privilege.getValuables().getPoints().get(PointType.FAITH);

        assertEquals(4, result);
    }

    @Test
    public void setNotAvailablePrivilege() throws Exception {
        PointsAndResources valuables = new PointsAndResources();
        valuables.increase(PointType.FAITH, 4);

        Privilege privilege = new Privilege(valuables, true);

        assertTrue(privilege.isAvailable());

        privilege.setNotAvailablePrivilege();

        assertFalse(privilege.isAvailable());
    }

    @Test
    public void isAvailable() throws Exception {
        PointsAndResources valuables = new PointsAndResources();
        valuables.increase(PointType.FAITH, 4);

        Privilege privilege = new Privilege(valuables, true);

        assertTrue(privilege.isAvailable());
    }

}