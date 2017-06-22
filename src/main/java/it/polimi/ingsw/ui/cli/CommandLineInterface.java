package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import it.polimi.ingsw.utility.Debugger;

import java.io.*;
import java.util.List;

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

    /**
     * Constructor
     * @param controller
     */
    public CommandLineInterface(UiController controller){
        super(controller);
        System.out.println(TITLE);
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

    public void chooseDoubleCost(){
        System.out.println("Devi scegliere il costo.");
    }

    @Override
    public void chooseCouncilPrivilege(Player player, List<Effect> privilegeList) {
        System.out.println("Devi scegliere il privilegio.");
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
        MainBoard mainBoard = getClient().getGameModel().getMainBoard();
        Player player = getClient().getPlayer();
        try {
            Tower tower = mainBoard.getTower(towerIndex);
            TowerCell cell = tower.getTowerCell(cellIndex);
            // verify tower can be used.
            tower.familyMemberCanBePlaced(player, familyMemberColor);
            // verify cell can be used.
            cell.familyMemberCanBePlaced(player, familyMemberColor);
            // verify player can buy the card
            cell.developmentCardCanBeBuyedBy(player);
            if(cell.getTowerCellImmediateEffect() != null)
                cell.getTowerCellImmediateEffect().runEffect(player, this);
            DevelopmentCard pickedUpCard = cell.getDevelopmentCard();
            pickedUpCard.payCost(player, this);

            if(pickedUpCard.getImmediateEffect() != null)
                pickedUpCard.getImmediateEffect().runEffect(player, this);

            cell.setPlayerNicknameInTheCell(player.getUsername());
            player.getPersonalBoard().addCard(pickedUpCard);

            Debugger.printDebugMessage("Card picked up with success!");
            gameScreen = new TurnScreen(this, true);
        } catch (IndexOutOfBoundsException | GameException e) {
            Debugger.printDebugMessage("You can't place your familiar in this place. Please retry.");
        }
    }

    @Override
    public void setFamilyMember(FamilyMemberColor familyMemberColor){
        MainBoard mainBoard = getClient().getGameModel().getMainBoard();
        CouncilPalace councilPalace = mainBoard.getCouncilPalace();
        Player player = getClient().getPlayer();
        try {
            councilPalace.familyMemberCanBePlaced(player, familyMemberColor);
            councilPalace.fifoAddPlayer(player);
            councilPalace.getImmediateEffect().runEffect(player, this);
        } catch (GameException e){
            Debugger.printDebugMessage("Error while placing your family member in council palace. Please retry.");
        }

    }

    @Override
    public void notifyEndTurn() {
        getClient().endTurn();
    }


}
