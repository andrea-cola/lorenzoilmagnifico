package it.polimi.ingsw.ui;

import java.io.IOException;

/**
 * Interface implemented by the Game class, contains all functions related to the Game that need to
 * be updated in real time and notified for every change.
 */
public interface UiController {

    void setNetworkSetting( ) throws IOException;

    /**
     * @param nickname
     */
    void loginPlayer(String nickname);

    boolean socket();

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
