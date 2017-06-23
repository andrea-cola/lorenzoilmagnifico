package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import it.polimi.ingsw.utility.Debugger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * This class manages the command line interface of the game.
 */

public class CommandLineInterface extends AbstractUI implements GameScreen.GameCallback, InformationCallback {

    private class ConsoleListener extends Thread{

        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        @Override
        public void run(){
            while(true){
                try {
                    String read = r.readLine();
                    if (gameScreen != null){
                        String[] readParts = read.split(" ");
                        if(readParts.length > 0)
                            gameScreen.sendParameters(readParts);
                        else
                            throw new WrongCommandException();
                    }
                } catch (WrongCommandException e){
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), "Wrong command, please retry.");
                } catch (IOException e){
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while reading from keyboard.");
                    break;
                }
            }
        }
    }

    /**
     * Title printed on the shell
     */
    private static final String TITLE = "\n\n\n\n\n\n     __     _____     ______     ________  __      ___ _________  ______\n" +
                                        "    /  /   /     \\   /  _   \\   /  ______//  \\    /  //_____  _/ /      \\ \n" +
                                        "   /  /   /   _   \\ /  (_)  /  /  /___   /    \\  /  / _____/ /  /   _    \\ \n" +
                                        "  /  /   /   (_)  //  __   /  /   ___/  /  /\\  \\/  / /_  ___/  /   (_)   / \n" +
                                        " /  /___ \\       //  /  \\ \\  /   /____ /  /  \\    /   / /______\\        / \n" +
                                        " \\______/ \\_____//__/    \\_\\/________//__/    \\__/   /________/ \\______/ \n" +
                                        "                                                                              \n" +
                                        "                         _  _     _   __           __    __  __             \n" +
                                        "                 / /    / \\/ \\   /_\\ / __ /\\  / / /_  / /   /  \\              \n"+
                                        "                / /__  /      \\ /   \\\\__//  \\/ / /   /  \\__ \\__/              \n\n\n";

    private BasicScreen screen;

    private BasicGameScreen gameScreen;

    private List<Privilege> privileges;

    /**
     * Constructor
     * @param controller
     */
    public CommandLineInterface(UiController controller){
        super(controller);
        System.out.println(TITLE);
        privileges = new ArrayList<>();
    }

    @Override
    public void chooseConnectionType(){
        screen = new ChooseConnectionScreen(getClient()::setNetworkSettings);
    }

    @Override
    public void loginScreen() {
        screen = new LoginSignInScreen(getClient()::loginPlayer);
    }

    @Override
    public void joinRoomScreen() {
        screen = new JoinRoomScreen(getClient()::joinRoom);
        Debugger.printStandardMessage("Room join OK.");
        screen = null;
    }

    @Override
    public void createRoomScreen(){
        screen = new CreateRoomScreen(getClient()::createRoom);
        Debugger.printStandardMessage("Room creation OK.");
        screen = null;
    }

    @Override
    public void choosePersonalTile(List<PersonalBoardTile> personalBoardTileList) {
        screen = new ChoosePersonalBoardTileScreen(getClient()::sendPersonalBoardTileChoice, personalBoardTileList);
    }

    @Override
    public void chooseLeaderCards(List<LeaderCard> leaderCards) {
        screen = new ChooseLeaderCardsScreen(getClient()::notifyLeaderCardChoice, leaderCards);
    }

    @Override
    public void notifyGameStarted() {
        new ConsoleListener().start();
        screen = null;
        Debugger.printStandardMessage("Game started.");
    }

    @Override
    public void turnScreen(String username, long seconds) {
        if(username.equals(getClient().getUsername())){
            gameScreen = new TurnScreen(this, false);
        }
        else {
            System.out.println("Turn of " + username + ". Please wait for " + seconds + " seconds.");
            gameScreen = new GameScreen(this);
        }
    }

    @Override
    public void chooseCouncilPrivilege(CouncilPrivilege councilPrivilege) {
        gameScreen = new ChooseCouncilPrivilegeScreen(this, councilPrivilege);
    }

    public void chooseDoubleCost(){
        System.out.println("Devi scegliere il costo.");
    }

    @Override
    public void showMainBoard(){
        Game game = getClient().getGameModel();
        System.out.println("[TOWERS]");
        int i = 0;
        for(Tower tower : game.getMainBoard().getTowers()){
            int j = 0;
            System.out.println("<" + tower.getColor().toString() + ">");
            for(TowerCell towerCell : game.getMainBoard().getTower(i).getTowerCells()){
                System.out.println("Dice value: " + towerCell.getMinFamilyMemberValue());
                System.out.println("Card: " + towerCell.getDevelopmentCard().toString() + " ");
                if(towerCell.getPlayerNicknameInTheCell() == null || towerCell.getPlayerNicknameInTheCell().equals(""))
                    System.out.println("Cell is free");
                else
                    System.out.println("Cell is busy. " + towerCell.getPlayerNicknameInTheCell());
                j++;
            }
            i++;
        }
    }

    @Override
    public void showPersonalBoards() {
        Game game = getClient().getGameModel();
        for(String username : game.getPlayersUsername()){
            System.out.println("[ PERSONAL BOARD : " + username + " ]");
            System.out.println(game.getPlayer(username).getPersonalBoard().toString());
        }
    }

    @Override
    public void setFamilyMemberInTower(int towerIndex, int cellIndex, FamilyMemberColor familyMemberColor) {
        Player player = getClient().getPlayer();
        try{
            getClient().getGameModel().pickupDevelopmentCardFromTower(player, familyMemberColor, towerIndex, cellIndex, this);
        } catch (IndexOutOfBoundsException | GameException e) {
            Debugger.printDebugMessage("You can't place your familiar in this place. Please retry.");
        }
    }

    @Override
    public void setFamilyMemberInCouncil(FamilyMemberColor familyMemberColor){
        Player player = getClient().getPlayer();
        try {
            getClient().getGameModel().placeFamilyMemberInsideCouncilPalace(player, familyMemberColor, this);
        } catch (GameException e){
            Debugger.printDebugMessage("Error while placing your family member in council palace. Please retry.");
        }
        System.out.println("Scelta chiusa");
    }

    @Override
    public void notifyEndTurn() {
        for(Privilege privilege : privileges){
            System.out.println(privilege.toString());
        }
        getClient().endTurn();
    }

    @Override
    public void setPrivilege(List<Privilege> privilege) {
        this.privileges = privilege;
        gameScreen = new TurnScreen(this, true);
    }

}
