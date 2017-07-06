package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class FamilyMemberTest {
    @Test
    public void setMembers() throws Exception {
        FamilyMember familyMember = new FamilyMember();

        Map<FamilyMemberColor, Integer> map = new HashMap<>();

        map.put(FamilyMemberColor.ORANGE, 2);
        map.put(FamilyMemberColor.WHITE, 3);
        map.put(FamilyMemberColor.BLACK, 9);

        familyMember.setMembers(map);

        int blackResult = familyMember.getMembers().get(FamilyMemberColor.BLACK);
        int whiteResult = familyMember.getMembers().get(FamilyMemberColor.WHITE);
        int orangeResult = familyMember.getMembers().get(FamilyMemberColor.ORANGE);

        assertEquals(9, blackResult);
        assertEquals(2, orangeResult);
        assertEquals(3, whiteResult);
    }

    @Test
    public void getMembers() throws Exception {
    }

    @Test
    public void setFamilyMemberValue() throws Exception {
        FamilyMember familyMember = new FamilyMember();

        familyMember.setFamilyMemberValue(FamilyMemberColor.WHITE, 4);

        int result = familyMember.getMembers().get(FamilyMemberColor.WHITE);

        assertEquals(4, result);
    }

    @Test
    public void increaseFamilyMemberValue() throws Exception {
        FamilyMember familyMember = new FamilyMember();

        familyMember.setFamilyMemberValue(FamilyMemberColor.ORANGE, 10);

        familyMember.increaseFamilyMemberValue(FamilyMemberColor.ORANGE, 1);

        int result = familyMember.getMembers().get(FamilyMemberColor.ORANGE);

        assertEquals(11, result);
    }

    @Test
    public void decreaseFamilyMemberValue() throws Exception {
        FamilyMember familyMember = new FamilyMember();

        familyMember.setFamilyMemberValue(FamilyMemberColor.ORANGE, 5);

        familyMember.decreaseFamilyMemberValue(FamilyMemberColor.ORANGE, 2);

        int result = familyMember.getMembers().get(FamilyMemberColor.ORANGE);

        assertEquals(3, result);
    }
}