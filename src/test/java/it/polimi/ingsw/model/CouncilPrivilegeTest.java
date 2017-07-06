package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lorenzo on 06/07/17.
 */
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


    @Test
    public void chooseCouncilPrivilege() throws Exception {

    }

}