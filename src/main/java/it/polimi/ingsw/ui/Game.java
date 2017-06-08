package it.polimi.ingsw.ui;

import it.polimi.ingsw.client.ClientInterface;

import java.io.*;

/**
 * This is the basic game class that runs the main function; it implements the two interfaces UiController and
 * ClientInterface which contains respectively the game related functions and the client actions
 */
public class Game implements UiController, ClientInterface {

    private AbstractUI userInterface;
    private BufferedReader keyboard= new BufferedReader(new InputStreamReader(System.in));
    private PrintWriter console=new PrintWriter(new OutputStreamWriter(System.out));

    /**
     * This is the main method; after choosing the interface, it will start the game
     * @throws IOException if the reading fails
     */
    public void Game(){
        console.println("Before beginning choose the user interface which you prefer play with");
        while(true) {
            console.println("CLI-> Command Line Interface");
            console.println("GUI-> Graphic User Interface");
            try {
                String choice = keyboard.readLine();
                if (choice.equals("CLI")) {
                    userInterface = new CommandLineInterface(this);
                } else if (choice.equals("GUI")) {
                    userInterface = new GraphicUserInterface(this);
                } else {
                    throw new IllegalArgumentException("The interface chosen is not valid");
                }
                userInterface.showNetworkMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void setNetworkSettings(NetworkType networkType, String address, int port) throws IOException {
        userInterface.showLoginMenu();
    }

    @Override
    public void loginPlayer(String nickname, String password){
        console.println("We are trying to log you, mr. " +nickname);
    }

    @Override
    public boolean socket() {
        return false;
    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public boolean existPlayer() {
        return false;
    }

    @Override
    public void setRoom(){}

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


class Starter{
    public static void main(String args[]) throws IOException {
       Game myGame=new Game();
    }
}

