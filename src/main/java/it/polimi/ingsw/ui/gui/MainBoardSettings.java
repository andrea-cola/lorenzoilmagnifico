package it.polimi.ingsw.ui.gui;


public interface MainBoardSettings{

    void showExtraProduction();

    void showExtraHarvest();

    void showExtraMarket();

    void setDevelopmentCardInTowerCell(int tower, int towerCell);

    void setExcommunicationCard(int period, int row);

    void setServants();

}
