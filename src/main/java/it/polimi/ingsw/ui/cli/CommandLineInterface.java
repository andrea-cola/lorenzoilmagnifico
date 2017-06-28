package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import it.polimi.ingsw.utility.Debugger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the command line interface of the game.
 */
public class  CommandLineInterface extends AbstractUI implements GameScreen.GameCallback, InformationCallback {

    private class ConsoleListener extends Thread{

        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = true;

        @Override
        public void run(){
            while(exit){
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

        public void stopRunning(){
            exit = false;
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

    private ConsoleListener consoleListener;

    /**
     * Constructor
     * @param controller
     */
    public CommandLineInterface(UiController controller) {
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
        consoleListener = new ConsoleListener();
        consoleListener.start();
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
    public void notifyUpdate(String message) {
        Debugger.printStandardMessage(message);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege) {
        consoleListener.stopRunning();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        gameScreen = null;
        ArrayList<Privilege> privilegeArraysList = new ArrayList<>();
        Privilege[] privileges = councilPrivilege.getPrivileges();

        System.out.println("[ COUNCIL PRIVILEGE CHOICE ]\nYou need to choose " + councilPrivilege.getNumberOfCouncilPrivileges() + " times.");
        for(int k = 0; k < councilPrivilege.getNumberOfCouncilPrivileges(); k++) {
            for (int i = 0; i < privileges.length; i++)
                if (privileges[i].isAvailable())
                    System.out.println((i + 1) + " -> " + privileges[i].getValuables().toString());
            int key = 0;
            do {
                try {
                    key = Integer.parseInt(r.readLine());
                } catch (ClassCastException | IOException e) {
                    key = 1;
                }
            } while (key < 1 || key > privileges.length || !privileges[key-1].isAvailable());
            key = key - 1;
            privilegeArraysList.add(privileges[key]);
            privileges[key].setNotAvailablePrivilege();
        }
        if(getClient().getPlayerTurnChoices().containsKey(reason)) {
            ArrayList<Privilege> arrayList = (ArrayList<Privilege>)getClient().getPlayerTurnChoices().get(reason);
            arrayList.addAll(privilegeArraysList);
        } else
            getClient().setPlayerTurnChoices(reason, privilegeArraysList);
        consoleListener = new ConsoleListener();
        consoleListener.start();
        gameScreen = new TurnScreen(this, true);
        return privilegeArraysList;
    }

    @Override
    public int chooseDoubleCost(PointsAndResources cost, int militaryPointsToPay, int militaryPointsNeeded){
        consoleListener.stopRunning();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        gameScreen = null;
        int key = 1;
        if(militaryPointsNeeded < getClient().getPlayer().getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY)){
            getClient().setPlayerTurnChoices("double-cost", key);
        } else {
            System.out.println("[ DOUBLE COST CHOICE ]\nYour card has a double cost, you need to choose what to pay.");
            System.out.println("1 -> " + cost.toString());
            System.out.println("2 -> Military points needed = " + militaryPointsNeeded + " Military points to pay =" + militaryPointsToPay);
            do {
                try {
                    key = Integer.parseInt(r.readLine());
                } catch (ClassCastException e) {
                    key = 0;
                } catch (IOException e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while reading from keyboard.");
                }
            } while (key < 1 || key > 2);
            getClient().setPlayerTurnChoices("double-cost", key);
        }
        consoleListener = new ConsoleListener();
        consoleListener.start();
        gameScreen = new TurnScreen(this, true);
        return key;
    }

    @Override
    public int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableEarned) {
        consoleListener.stopRunning();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        gameScreen = null;
        int key = 1;

        System.out.println("[ CHOOSE EXCHANGE EFFECT ]\nChoose an effect among those proposal.");
        for(int i = 0; i < valuableToPay.length; i++)
            System.out.println((i+1) + " -> " + valuableToPay[i].toString() + " ==> " + valuableEarned[i].toString());
        do {
            try {
                key = Integer.parseInt(r.readLine());
            } catch (ClassCastException e) {
                key = 0;
            } catch (IOException e) {
                Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error while reading from keyboard.");
            }
        } while (key < 1 || key > valuableToPay.length);
        key = key - 1;
        getClient().setPlayerTurnChoices(card + ":double", key);
        consoleListener = new ConsoleListener();
        consoleListener.start();
        gameScreen = new TurnScreen(this, true);
        return key;
    }

    @Override
    public int choosePickUpDiscounts(String reason, List<PointsAndResources> discounts) {
        consoleListener.stopRunning();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        gameScreen = null;
        int key = 1;
        System.out.println("[ CHOOSE DISCOUNTS ]\nChoose a discounts among those proposal.");
        for(int i = 0; i < discounts.size(); i++)
            System.out.println((i+1) + " -> " + discounts.get(i).toString() + " ==> " + discounts.get(i).toString());
        do {
            try {
                key = Integer.parseInt(r.readLine());
            } catch (ClassCastException | IOException e) {
                key = 0;
            }
        } while (key < 1 || key > discounts.size());
        key = key - 1;
        getClient().setPlayerTurnChoices(reason, key);
        consoleListener = new ConsoleListener();
        consoleListener.start();
        gameScreen = new TurnScreen(this, true);
        return key;
    }

    @Override
    public DevelopmentCard chooseNewCard(String reason, DevelopmentCardColor[] developmentCardColors, int diceValue, PointsAndResources discount) {
        consoleListener.stopRunning();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        gameScreen = null;
        MainBoard mainBoard = getClient().getGameModel().getMainBoard();
        DevelopmentCard card;
        ArrayList<DevelopmentCard> selectable = new ArrayList<>();
        System.out.println("[ CHOOSE NEW CARD ]\nYou can pick up a new card. Choose among those proposal.");

        int i = 1;
        System.out.println("0 -> to choose no card.");
        for(DevelopmentCardColor developmentCardColor : developmentCardColors)
            for(Tower tower : mainBoard.getTowers())
                if(tower.getColor().equals(developmentCardColor))
                    for(TowerCell towerCell : tower.getTowerCells())
                        if(towerCell.getPlayerNicknameInTheCell() == null && towerCell.getMinFamilyMemberValue() <= diceValue
                                && isSelectable(towerCell, discount)) {
                            System.out.println(i + " -> " + towerCell.getDevelopmentCard().toString());
                            selectable.add(towerCell.getDevelopmentCard());
                            i++;
                        }
        System.out.println("You have also a discount on cost of the card -> " + discount.toString());
        int key = 0;
        do {
            try {
                key = Integer.parseInt(r.readLine());
            } catch (ClassCastException | IOException e) {
                key = 0;
            }
        } while (key < 0 || key > i);
        key = key - 1;
        card = selectable.get(key);
        for (Tower tower : mainBoard.getTowers())
            for (TowerCell towerCell : tower.getTowerCells())
                if (towerCell.getDevelopmentCard().getName().equals(card.getName())) {
                    card.payCost(getClient().getPlayer(), this);
                    towerCell.setPlayerNicknameInTheCell(getClient().getUsername());
                    if(towerCell.getTowerCellImmediateEffect() != null)
                        towerCell.getTowerCellImmediateEffect().runEffect(getClient().getPlayer(), this);
                }
        getClient().setPlayerTurnChoices(reason, card);
        consoleListener = new ConsoleListener();
        consoleListener.start();
        gameScreen = new TurnScreen(this, true);
        return card;
    }

    @Override
    public boolean supportForTheChurch() {
        consoleListener.stopRunning();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        gameScreen = null;
        System.out.println("[ SUPPORT FOR THE CHURCH ]\nDo you want to support the church?");
        System.out.println("-> 1 : NO\n-> 2: YES");
        boolean choice;
        int key = 0;
        do {
            try {
                key = Integer.parseInt(r.readLine());
            } catch (ClassCastException | IOException e) {
                key = 1;
            }
        } while (key < 0 || key > 2);
        if(key == 1)
            choice = false;
        else
            choice = true;
        consoleListener = new ConsoleListener();
        consoleListener.start();
        gameScreen = new GameScreen(this);
        return choice;
    }

    private boolean isSelectable(TowerCell cell, PointsAndResources discount){
        try{
            cell.developmentCardCanBeBuyed(getClient().getPlayer(), discount);
            return true;
        } catch (GameException e){
            return false;
        }
    }

    @Override
    public void showMainBoard(){
        Game game = getClient().getGameModel();
        System.out.println(game.toString());
    }

    @Override
    public void showPersonalBoards() {
        Game game = getClient().getGameModel();
        for(String username : game.getPlayersUsername())
            System.out.println(game.getPlayer(username).getPersonalBoard().toString());
    }

    @Override
    public void setFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex) {
        Player player = getClient().getPlayer();
        try{
            getClient().getGameModel().pickupDevelopmentCardFromTower(player, familyMemberColor, servants, towerIndex, cellIndex, this);
            getClient().notifySetFamilyMemberInTower(familyMemberColor, servants, towerIndex, cellIndex);
        } catch (IndexOutOfBoundsException | GameException e) {
            Debugger.printDebugMessage("You can't place your familiar in this place. Please retry.");
        }
    }

    @Override
    public void setFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants){
        Player player = getClient().getPlayer();
        try {
            getClient().getGameModel().placeFamilyMemberInsideCouncilPalace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInCouncil(familyMemberColor, servants);
        } catch (GameException e){
            Debugger.printDebugMessage("Error while placing your family member in council palace. Please retry.");
        }
    }

    @Override
    public void setFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int marketIndex) {
        Player player = getClient().getPlayer();
        try{
            getClient().getGameModel().placeFamilyMemberInsideMarket(player,familyMemberColor,marketIndex, servants, this);
            getClient().notifySetFamilyMemberInMarket(familyMemberColor, servants, marketIndex);
        } catch (GameException e){
            Debugger.printDebugMessage("Error while placing your family member in the market. Please retry.");
        }
    }

    @Override
    public void setFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants) {
        Player player = getClient().getPlayer();
        try {
            getClient().getGameModel().placeFamilyMemberInsideProductionSimpleSpace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInProductionSimple(familyMemberColor, servants);
        } catch (GameException e){
            Debugger.printDebugMessage("Error while placing your family member in production simple space. Please retry.");
        }
    }

    @Override
    public void setFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants) {
        Player player = getClient().getPlayer();
        try{
            getClient().getGameModel().placeFamilyMemberInsideHarvestSimpleSpace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInHarvestSimple(familyMemberColor, servants);
        } catch (GameException e){
            Debugger.printDebugMessage("Error while placing your family member in harvest simple space. Please retry.");
        }
    }

    @Override
    public void setFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants) {
        Player player = getClient().getPlayer();
        try{
            getClient().getGameModel().placeFamilyMemberInsideProductionExtendedSpace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInProductionExtended(familyMemberColor, servants);
        } catch (GameException e){
            Debugger.printDebugMessage("Error while placing your family member in production extended space. Please retry.");
        }
    }

    @Override
    public void setFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants) {
        Player player = getClient().getPlayer();
        try{
            getClient().getGameModel().placeFamilyMemberInsideHarvestExtendedSpace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInHarvestExtended(familyMemberColor, servants);
        } catch (GameException e){
            Debugger.printDebugMessage("Error while placing your family member in harvest extended space. Please retry.");
        }
    }

    @Override
    public void activateLeader(String leaderName, int servants) {
        Player player = getClient().getPlayer();
        try{
            int i = 0;
            List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
            for(LeaderCard leaderCard : leaderCards){
                if(leaderCard.getLeaderCardName().toLowerCase().equals(leaderName.toLowerCase())) {
                    getClient().getGameModel().activateLeaderCard(player, i, servants, this);
                    getClient().notifyActivateLeader(i, servants);
                }
                i++;
            }
        } catch (GameException e){
            Debugger.printDebugMessage("Error while activate you leader card. Please retry.");
        }
    }

    @Override
    public void discardLeader(String leaderName) {
        Player player = getClient().getPlayer();
        int i = 0;
        List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
        for(LeaderCard leaderCard : leaderCards){
            if(leaderCard.getLeaderCardName().toLowerCase().equals(leaderName.toLowerCase())) {
                getClient().getGameModel().discardLeaderCard(player, i, this);
                getClient().notifyDiscardLeader(i);
            }
            i++;
        }
    }

    @Override
    public void notifyEndTurn() {
        getClient().endTurn();
    }

}

