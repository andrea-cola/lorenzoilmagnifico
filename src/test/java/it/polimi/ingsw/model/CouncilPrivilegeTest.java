package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CouncilPrivilegeTest {
    @Test
    public void getNumberOfCouncilPrivileges() throws Exception {
        CouncilPrivilege councilPrivilege = new CouncilPrivilege(2);

        int result = councilPrivilege.getNumberOfCouncilPrivileges();

        assertEquals(2, result);
    }

    @Test
    public void getPrivileges() throws Exception {
        CouncilPrivilege councilPrivilege = new CouncilPrivilege(0);

        assertTrue(councilPrivilege.getPrivileges().getClass().equals(Privilege[].class));
    }

}