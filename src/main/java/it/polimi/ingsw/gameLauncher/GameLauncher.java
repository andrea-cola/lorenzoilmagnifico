package it.polimi.ingsw.gameLauncher;

import it.polimi.ingsw.cli.Debugger;
import it.polimi.ingsw.client.AbstractClient;
import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.rmiClient.RMIClient;
import it.polimi.ingsw.socketClient.SocketClient;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;

public class GameLauncher implements ClientInterface, UiController {

    /**
     * Abstract ui controller.
     */
    private AbstractUI ui;

    /**
     * Abstract communication client controller.
     */
    private AbstractClient client;

    /**
     * Player username.
     */
    private String playerUsername;

    /**
     * Player password.
     */
    private String playerPassword;

    /**
     * Class constructor.
     * @param ui controller type.
     */
    GameLauncher(String ui){
        if(ui.equals("cli")){
            // ui = new CLI();
        }
        else if(ui.equals("gui")){
            // ui = new GUI();
        }
        else{
            // throw exception
        }
    }

    @Override
    public void setNetworkSetting() {

    }

    @Override
    public boolean socket() {
        return false;
    }

    @Override
    public void loginPlayer(String username, String password) {

    }

    @Override
    public String getNickname() {
        return playerUsername;
    }

    @Override
    public boolean existPlayer() {
        return false;
    }

    @Override
    public void setRoom() {

    }

    @Override
    public void joinRoom() {

    }

    @Override
    public boolean simpleGame() {
        return false;
    }

    @Override
    public void setPlayers() {

    }

    @Override
    public void setMainBoard() {

    }

    @Override
    public void setTower() {

    }

    @Override
    public void setCouncilPalace() {

    }

    @Override
    public void setMarket() {

    }

    @Override
    public void setHarverst() {

    }

    @Override
    public void setProduction() {

    }

    @Override
    public void setDice() {

    }

    @Override
    public void setPlayerBoard() {

    }

    @Override
    public void setPersonalBonusTile() {

    }

    @Override
    public void setDevelopmentCard() {

    }

    @Override
    public void setLeaderCard() {

    }

    @Override
    public boolean occupiedActionSpace() {
        return false;
    }

    @Override
    public boolean occupiedTower() {
        return false;
    }
}
