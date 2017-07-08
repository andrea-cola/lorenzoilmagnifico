package it.polimi.ingsw.model;

import it.polimi.ingsw.gameserver.Configurator;
import it.polimi.ingsw.model.effects.EffectHarvestProductionSimple;
import it.polimi.ingsw.model.effects.EffectSimple;
import it.polimi.ingsw.utility.Configuration;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainBoardTest {
    @Test
    public void getTower() throws Exception {
        Configurator.loadConfigurations();
        MainBoard mainBoard = new MainBoard(Configurator.getConfiguration().getMainBoard());
        assertTrue(mainBoard.getTower(0).getClass().equals(Tower.class));
    }

    @Test
    public void getTowers() throws Exception {
        Configurator.loadConfigurations();
        MainBoard mainBoard = new MainBoard(Configurator.getConfiguration().getMainBoard());
        assertTrue(mainBoard.getTowers().getClass().equals(Tower[].class));
    }

    @Test
    public void getVatican() throws Exception {
        Configurator.loadConfigurations();
        MainBoard mainBoard = new MainBoard(Configurator.getConfiguration().getMainBoard());
        assertTrue(mainBoard.getVatican().getClass().equals(Vatican.class));
    }

    @Test
    public void getCouncilPalace() throws Exception {
        Configurator.loadConfigurations();
        MainBoard mainBoard = new MainBoard(Configurator.getConfiguration().getMainBoard());
        assertTrue(mainBoard.getCouncilPalace().getClass().equals(CouncilPalace.class));
    }

    @Test
    public void getHarvest() throws Exception {
        Configurator.loadConfigurations();
        MainBoard mainBoard = new MainBoard(Configurator.getConfiguration().getMainBoard());
        assertTrue(mainBoard.getHarvest().getClass().equals(ActionSpace.class));
    }

    @Test
    public void getProduction() throws Exception {
        Configurator.loadConfigurations();
        MainBoard mainBoard = new MainBoard(Configurator.getConfiguration().getMainBoard());
        assertTrue(mainBoard.getProduction().getClass().equals(ActionSpace.class));
    }

    @Test
    public void getHarvestExtended() throws Exception {
        Configurator.loadConfigurations();
        MainBoard mainBoard = new MainBoard(Configurator.getConfiguration().getMainBoard());
        assertTrue(mainBoard.getHarvestExtended().getClass().equals(ActionSpaceExtended.class));
    }

    @Test
    public void getProductionExtended() throws Exception {
        Configurator.loadConfigurations();
        MainBoard mainBoard = new MainBoard(Configurator.getConfiguration().getMainBoard());
        assertTrue(mainBoard.getProductionExtended().getClass().equals(ActionSpaceExtended.class));
    }

    @Test
    public void getMarket() throws Exception {
        Configurator.loadConfigurations();
        MainBoard mainBoard = new MainBoard(Configurator.getConfiguration().getMainBoard());
        assertTrue(mainBoard.getMarket().getClass().equals(Market.class));
    }

    @Test
    public void setTower() throws Exception {

    }

    @Test
    public void setVatican() throws Exception {

    }



}