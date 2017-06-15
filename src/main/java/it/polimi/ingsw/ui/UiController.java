package it.polimi.ingsw.ui;

import it.polimi.ingsw.ui.cli.NetworkType;

import java.io.IOException;

/**
 * Interface implemented by the LorenzoIlMagnifico class, contains all functions related to the LorenzoIlMagnifico that need to
 * be updated in real time and notified for every change.
 */
public interface UiController {
    /**
     * It allows the user to set network options
     * @param networkType
     * @param address
     * @param port
     */
    void setNetworkSettings(NetworkType networkType, String address, int port);

    /**
     * It allows the user to login to the game
     * @param nickname
     */
    void loginPlayer(String nickname, String password);

    boolean socket();

    String getNickname();

    boolean existPlayer();

    void setRoom(int maxPlayer);

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
