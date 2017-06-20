package it.polimi.ingsw.lorenzo;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import it.polimi.ingsw.ui.cli.LoginSignInScreen;
import it.polimi.ingsw.utility.Configuration;
import it.polimi.ingsw.utility.Debugger;
import it.polimi.ingsw.client.AbstractClient;
import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.rmiClient.RMIClient;
import it.polimi.ingsw.socketClient.SocketClient;
import it.polimi.ingsw.ui.cli.CommandLineInterface;
import it.polimi.ingsw.ui.cli.ConnectionType;
import it.polimi.ingsw.ui.gui.GraphicUserInterface;

import java.io.*;

/**
 * This is the basic game class that runs the main function; it implements the two interfaces UiController and
 * ClientInterface which contains respectively the game related functions and the client actions.
 */
/*package-local*/ class LorenzoIlMagnifico implements UiController, ClientInterface {

    private String username;

    private AbstractUI userInterface;

    private AbstractClient client;

    /**
     * Represent game model.
     */
    private Game game;

    /**
     * Class constructor. A new user interface is created.
     * @param ui index of the preferred interface.
     */
    public LorenzoIlMagnifico(int ui) throws InterruptedException {
        switch (ui){
            case 1:
                userInterface = new CommandLineInterface(this);
                break;
            case 2:
                userInterface = new GraphicUserInterface(this);
                break;
        }
    }

    /**
     * The start function initializes the network menu
     */
    public void start(){
        userInterface.chooseConnectionType();
    }

    /**
     * It sets the network settings.
     * @param connectionType remote method interface or socket.
     * @param address network address.
     * @param port network port.
     */
    @Override
    public void setNetworkSettings(ConnectionType connectionType, String address, int port) throws ConnectionException {
        System.out.print("CIAONE");
        switch (connectionType){
            case SOCKET:
                client = new SocketClient(this, address, port);
                break;
            case RMI:
                client = new RMIClient(this, address, port);
                break;
            default:
                throw new IllegalArgumentException();
        }
        client.connectToServer();
        userInterface.loginScreen();
    }


    @Override
    public void loginPlayer(String username, String password, boolean flag) {
        try{
            if(flag)
                client.signInPlayer(username, password);
            client.loginPlayer(username, password);
            this.username = username;
            userInterface.joinRoomScreen();
        }catch (LoginException e) {
            Debugger.printDebugMessage(this.getClass().getSimpleName(), e.getError().toString());
            userInterface.loginScreen();
        }catch (NetworkException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Cannot send request.");
        }
    }

    @Override
    public void joinRoom(){
        try{
            client.joinRoom();
        }catch (RoomException e) {
            Debugger.printStandardMessage("No rooms available. Create new one.");
            userInterface.createRoomScreen();
        } catch (NetworkException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Cannot send request.");
        }
    }

    @Override
    public void createRoom(int maxPlayers) {
        try {
            client.createNewRoom(maxPlayers);
        } catch (RoomException e) {
            Debugger.printDebugMessage("You're added in another room created by other player meanwhile.");
        } catch (NetworkException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Cannot send request.");
        }
    }

    @Override
    public void setGameModel(Game game) {
        this.game = game;
        printGame();
        Debugger.printDebugMessage("Game started.");
    }

    public void printGame(){
        System.out.println("Mainboard");
        try {
            System.out.println(this.game.getMainBoard().getTower(1).getTowerCell(0).getDevelopmentCard().getName());
            System.out.println(this.game.getMainBoard().getTower(1).getTowerCell(1).getDevelopmentCard().getName());
            System.out.println(this.game.getMainBoard().getTower(1).getTowerCell(2).getDevelopmentCard().getName());
            System.out.println(this.game.getMainBoard().getTower(1).getTowerCell(3).getDevelopmentCard().getName());
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

}