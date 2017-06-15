package it.polimi.ingsw.ui;

import it.polimi.ingsw.utility.Debugger;
import it.polimi.ingsw.client.AbstractClient;
import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.rmiClient.RMIClient;
import it.polimi.ingsw.socketClient.SocketClient;
import it.polimi.ingsw.ui.cli.CommandLineInterface;
import it.polimi.ingsw.ui.cli.NetworkType;
import it.polimi.ingsw.ui.gui.GraphicUserInterface;

import java.io.*;
import java.util.Scanner;

/**
 * This is the basic game class that runs the main function; it implements the two interfaces UiController and
 * ClientInterface which contains respectively the game related functions and the client actions.
 */
public class LorenzoIlMagnifico implements UiController, ClientInterface {

    private String username;

    //private UiHandler uiHandler;

    private AbstractUI userInterface;

    private BufferedReader keyboard= new BufferedReader(new InputStreamReader(System.in));

    private PrintWriter console=new PrintWriter(new OutputStreamWriter(System.out));

    private AbstractClient client;

    /**
     * This is the main method; after choosing the interface, it will start the game.
     * @throws IOException if the reading fails
     */
    public LorenzoIlMagnifico(String ui) throws IllegalAccessException {
        switch (ui){
            case "CLI":
                userInterface= new CommandLineInterface(this);
                break;
            case "GUI":
                userInterface= new GraphicUserInterface(this);
                break;
            default:
                throw new IllegalAccessException();
        }
    }

    /**
     * The start function initializes the network menu
     */
    public void start(){
        userInterface.showNetworkMenu();
    }

    /**
     * It sets the network settings
     * @param networkType remote method interface or socket
     * @param address network address
     * @param port network port
     */
    @Override
    public void setNetworkSettings(NetworkType networkType, String address, int port) {
        switch (networkType){
            case SOCKET:
                client= new SocketClient(this, address, port);
                break;
            case RMI:
                client= new RMIClient(this, address, port);
                break;
            default:
                throw new IllegalArgumentException();
        }
        userInterface.showLoginMenu();
    }

    @Override
    public void loginPlayer(String username, String password) {
        console.println("We are trying to log you, mr. " + username);
        try{
            client.loginPlayer(username, password);
            this.username=username;
            joinRoom();
        }catch (NetworkException e) {
            Debugger.printDebugMessage(this.getClass().getName(), e);
        }
    }

    @Override
    public void joinRoom(){    }

    public void setRoom(int maxPlayer){


    }


    @Override
    public boolean socket() {
        return false;
    }

    @Override
    public String getNickname() {
        return username;
    }

    @Override
    public boolean existPlayer() {
        return false;
    }

    public void setRoom() {

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
