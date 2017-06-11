package it.polimi.ingsw.ui;

import it.polimi.ingsw.cli.Debugger;
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
 * ClientInterface which contains respectively the game related functions and the client actions
 */
public class Game implements UiController, ClientInterface {

    private String username;

    private AbstractUI userInterface;

    private BufferedReader keyboard= new BufferedReader(new InputStreamReader(System.in));

    private PrintWriter console=new PrintWriter(new OutputStreamWriter(System.out));

    private AbstractClient client;

    /**
     * This is the main method; after choosing the interface, it will start the game
     * @throws IOException if the reading fails
     */
    public  Game(String ui) throws IllegalAccessException {
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

    public void start(){
        userInterface.showNetworkMenu();
    }

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
    public void joinRoom(){
    }

    @Override
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


class Starter{
    public static void main(String args[]) throws IOException, IllegalAccessException {
        Game myGame = new Game(chooseInterface());
        myGame.start();
    }

    private static String chooseInterface(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            Debugger.printStandardMessage("Select the user interface you would like to play Lorenzo Il Magnifico:");
            Debugger.printStandardMessage("--> Type '1' to use CLI");
            Debugger.printStandardMessage("--> Type '2' to use GUI");
            int choice = scanner.nextInt();
            switch (choice){
                case 1: return new String("CLI");
                case 2: return new String("GUI");
                default: Debugger.printDebugMessage("MainClass.java", "You typed a not valid option. Retry.");
            }
        }
    }
}

