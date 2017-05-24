package it.polimi.ingsw.ui;

/**
 * Interface created implemented by the game class, contains all functions related to the game that need to
 * be updated in real time and notified for every change.
 */
public interface uiController {

    void setNetworkSetting();

    boolean socket();

    void loginPlayer(String nickname);

    String getNickname();

    boolean existPlayer();

    void setRoom();

    void joinRoom();

    boolean simpleGame();

    void setPlayers();

    void setMainBoard();

    void setTower();

    void setCouncilPalace();

    void setMarket();

    void setHarverst();

    void setProduction();

    void setDice();

    void setPlayerBoard();

    void setPersonalBonusTile();

    void setDevelopmentCard();

    void setLeaderCard();

    boolean occupiedActionSpace();

    boolean occupiedTower();

    /*void chooseLeaderCard();

    void chooseActionSpace();

    void chooseExcommunicationAction();

    void chooseTower();

    void chooseTowerCell();

    void setUpLeaderCard();

    */
}
