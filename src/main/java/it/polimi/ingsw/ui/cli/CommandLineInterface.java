package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.LEPicoDellaMirandola;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import it.polimi.ingsw.utility.Printer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class manages the command line interface of the game.
 */
public class CommandLineInterface extends AbstractUI implements GameScreen.GameCallback, ExcommunicationScreen.ExcommunicationCallback, InformationCallback {

    private class ConsoleListener extends Thread{

        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = true;

        @Override
        public void run(){
            while(exit){
                try {
                    pausePoint();
                    String read = r.readLine();
                    if (gameScreen != null){
                        String[] readParts = read.split(" ");
                        if(readParts.length > 0)
                            gameScreen.sendParameters(readParts);
                        else
                            throw new WrongCommandException();
                    }
                } catch (WrongCommandException e){
                    Printer.printDebugMessage(this.getClass().getSimpleName(), this.getId() + "Wrong command, please retry.");
                } catch (IOException e){
                    Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while reading from keyboard.");
                    break;
                }
            }
        }

        synchronized void pausePoint() {
            while (needToPause) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while pausing keyboard listener.");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private boolean needToPause = false;

    private BasicScreen screen;

    private BasicGameScreen gameScreen;

    private boolean moveDone;

    private synchronized void pause() {
        needToPause = true;
    }

    private synchronized void unpause() {
        needToPause = false;
        this.notifyAll();
    }

    public CommandLineInterface(UiController controller) {
        super(controller);
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
        Printer.printStandardMessage("Room join OK.");
        screen = null;
    }

    @Override
    public void createRoomScreen(){
        screen = new CreateRoomScreen(getClient()::createRoom);
        Printer.printStandardMessage("Room creation OK.");
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
        ConsoleListener consoleListener = new ConsoleListener();
        consoleListener.start();
        screen = null;
        Printer.printStandardMessage("Game started.");
    }

    @Override
    public void turnScreen(String username, long seconds) {
        if(username.equals(getClient().getUsername())){
            moveDone = false;
            if(!getClient().getPlayer().getPersonalBoard().getExcommunicationValues().getSkipFirstTurn()
                    && getClient().getGameModel().getTurn() == 1)
                gameScreen = new TurnScreen(this, moveDone);
            else{
                Printer.printStandardMessage("You lost this turn because of the excommunicaiton you got.");
                this.notifyEndTurn();
            }
        }
        else {
            Printer.printStandardMessage("Turn of " + username + ". Please wait for " + seconds + " seconds.");
            gameScreen = new GameScreen(this);
        }
    }

    @Override
    public void notifyUpdate(String message) {
        Printer.printStandardMessage(message);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege) {
        pause();
        gameScreen = null;
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Privilege> privilegeArraysList = new ArrayList<>();
        Privilege[] privileges = councilPrivilege.getPrivileges();

        Printer.printInformationMessage("[ COUNCIL PRIVILEGE CHOICE ]\nYou need to choose " + councilPrivilege.getNumberOfCouncilPrivileges() + " times.");
        for(int k = 0; k < councilPrivilege.getNumberOfCouncilPrivileges(); k++) {
            for (int i = 0; i < privileges.length; i++)
                if (privileges[i].isAvailable())
                    Printer.printInformationMessage((i + 1) + " -> " + privileges[i].getValuables().toString());
            int key = 0;
            do {
                try {
                    key = Integer.parseInt(r.readLine());
                } catch (ClassCastException | NumberFormatException | IOException e) {
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
        } else {
            getClient().setPlayerTurnChoices(reason, privilegeArraysList);
        }
        unpause();
        return privilegeArraysList;
    }

    @Override
    public int chooseDoubleCost(PointsAndResources cost, int militaryPointsToPay, int militaryPointsNeeded){
        pause();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        gameScreen = null;
        int key = 1;
        if(militaryPointsNeeded < getClient().getPlayer().getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY)){
            getClient().setPlayerTurnChoices("double-cost", key);
        } else {
            Printer.printInformationMessage("[ DOUBLE COST CHOICE ]\nYour card has a double cost, you need to choose what to pay.");
            Printer.printInformationMessage("1 -> " + cost.toString());
            Printer.printInformationMessage("2 -> Military points needed = " + militaryPointsNeeded + " Military points to pay =" + militaryPointsToPay);
            do {
                try {
                    key = Integer.parseInt(r.readLine());
                } catch (ClassCastException | NumberFormatException | IOException e) {
                    key = 0;
                }
            } while (key < 1 || key > 2);
            getClient().setPlayerTurnChoices("double-cost", key);
        }
        unpause();
        return key;
    }

    @Override
    public int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableEarned) {
        pause();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        gameScreen = null;
        int key = 1;

        Printer.printInformationMessage("[ CHOOSE EXCHANGE EFFECT ]\nChoose an effect among those proposal.");
        for(int i = 0; i < valuableToPay.length; i++)
            Printer.printInformationMessage((i+1) + " -> " + valuableToPay[i].toString() + " ==> " + valuableEarned[i].toString());
        do {
            try {
                key = Integer.parseInt(r.readLine());
            } catch (ClassCastException | NumberFormatException | IOException e) {
                key = 0;
            }
        } while (key < 1 || key > valuableToPay.length);
        key = key - 1;
        getClient().setPlayerTurnChoices(card, key);
        unpause();
        return key;
    }

    @Override
    public int choosePickUpDiscounts(String reason, List<PointsAndResources> discounts) {
        pause();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        gameScreen = null;
        int key = 1;
        Printer.printInformationMessage("[ CHOOSE DISCOUNTS ]\nChoose a discounts among those proposal.");
        for(int i = 0; i < discounts.size(); i++)
            Printer.printInformationMessage((i+1) + " -> " + discounts.get(i).toString() + " ==> " + discounts.get(i).toString());
        do {
            try {
                key = Integer.parseInt(r.readLine());
            } catch (ClassCastException | NumberFormatException | IOException e) {
                key = 0;
            }
        } while (key < 1 || key > discounts.size());
        key = key - 1;
        getClient().setPlayerTurnChoices(reason, key);
        unpause();
        return key;
    }

    @Override
    public DevelopmentCard chooseNewCard(String reason, DevelopmentCardColor[] developmentCardColors, int diceValue, PointsAndResources discount) {
        pause();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        gameScreen = null;

        MainBoard mainBoard = getClient().getGameModel().getMainBoard();
        DevelopmentCard card = null;
        ArrayList<DevelopmentCard> selectable = new ArrayList<>();
        Printer.printInformationMessage("[ CHOOSE NEW CARD ]\nYou can pick up a new card. Choose between those proposal.");

        int i = 1;
        Printer.printInformationMessage("0 -> to choose no card.");
        for(DevelopmentCardColor developmentCardColor : developmentCardColors) {
            int newDiceValue = diceValue + getClient().getPlayer().getPersonalBoard().getDevelopmentCardColorDiceValueBonus().get(developmentCardColor)
                    - getClient().getPlayer().getPersonalBoard().getExcommunicationValues().getDevelopmentCardDiceMalus().get(developmentCardColor);
            for (Tower tower : mainBoard.getTowers())
                if (tower.getColor().equals(developmentCardColor))
                    for (TowerCell towerCell : tower.getTowerCells())
                        if (towerCell.getPlayerNicknameInTheCell() == null && towerCell.getMinFamilyMemberValue() <= newDiceValue
                                && isSelectable(towerCell, discount)) {
                            Printer.printInformationMessage(i + " -> " + towerCell.getDevelopmentCard().toString());
                            selectable.add(towerCell.getDevelopmentCard());
                            i++;
                        }
        }
        if(!discount.toString().equals(""))
            Printer.printInformationMessage("You have also a discount on cost of the card -> " + discount.toString());
        int key = 0;
        do {
            try {
                key = Integer.parseInt(r.readLine());
            } catch (ClassCastException | NumberFormatException | IOException e) {
                key = 0;
            }
        } while (key < 0 || key > i);
        if(key > 0) {
            key = key - 1;
            card = selectable.get(key);
            try {
                for (Tower tower : mainBoard.getTowers())
                    for (TowerCell towerCell : tower.getTowerCells())
                        if (towerCell.getDevelopmentCard().getName().equals(card.getName())) {
                            LeaderCard leaderCard = getClient().getPlayer().getPersonalBoard().getLeaderCardWithName("Pico della Mirandola");
                            int devCardCoinsCost = card.getCost().getResources().get(ResourceType.COIN);
                            if (leaderCard != null && leaderCard.getLeaderEffectActive()) {
                                //decrease card price
                                if (devCardCoinsCost >= 3)
                                    card.getCost().decrease(ResourceType.COIN, ((LEPicoDellaMirandola) leaderCard.getEffect()).getMoneyDiscount());
                                else
                                    card.getCost().decrease(ResourceType.COIN, devCardCoinsCost);
                            }
                            card.payCost(getClient().getPlayer(), this);
                            towerCell.setPlayerNicknameInTheCell(getClient().getUsername());
                            if (towerCell.getTowerCellImmediateEffect() != null)
                                towerCell.getTowerCellImmediateEffect().runEffect(getClient().getPlayer(), this);
                        }
            } catch (GameException e){
                Printer.printStandardMessage(e.getError().toString());
            }
        }
        getClient().setPlayerTurnChoices(reason, card);
        unpause();
        return card;
    }

    @Override
    public LeaderCard copyAnotherLeaderCard(String reason) {
        pause();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        List<LeaderCard> leaderCards = new ArrayList<>();
        for(Map.Entry pair : getClient().getGameModel().getPlayersMap().entrySet())
            if(!pair.getKey().equals(getClient().getUsername()))
                leaderCards.addAll(((Player)pair.getValue()).getPersonalBoard().getLeaderCards());
        Printer.printInformationMessage("[ CHOOSE NEW CARD ]\nYou can copy the effect of another leader card. Choose between those proposal.");
        for(int i = 0; i < leaderCards.size(); i++)
            Printer.printInformationMessage( (i+1) + " -> " + leaderCards.get(i).getLeaderCardName());
        int key = 0;
        do {
            try {
                key = Integer.parseInt(r.readLine());
            } catch (ClassCastException | NumberFormatException | IOException e) {
                key = 1;
            }
        } while (key < 1 || key > leaderCards.size());
        key--;
        getClient().getPlayerTurnChoices().put(reason, leaderCards.get(key));
        unpause();
        return leaderCards.get(key);
    }

    @Override
    public FamilyMemberColor choiceLeaderDice(String reason) {
        pause();
        int i = 0;
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        Printer.printInformationMessage("[ CHOOSE NEW CARD ]\nYou can choose a color between those proposals.");
        for(FamilyMemberColor familyMemberColor : FamilyMemberColor.values()) {
            Printer.printInformationMessage( (i+1) + " -> " + (FamilyMemberColor.values())[i]);
            i++;
        }
        int key = 0;
        do {
            try {
                key = Integer.parseInt(r.readLine());
            } catch (ClassCastException | NumberFormatException | IOException e) {
                key = 1;
            }
        } while (key < 1 || key > FamilyMemberColor.values().length);
        key--;
        getClient().getPlayerTurnChoices().put(reason, FamilyMemberColor.values()[key]);
        unpause();
        return FamilyMemberColor.values()[key];
    }

    @Override
    public void supportForTheChurch(boolean flag) {
        if(flag){
            gameScreen = new ExcommunicationScreen(this);
        } else {
            Printer.printStandardMessage("You have been excommunicated!!");
        }
    }

    @Override
    public void notifyEndGame(ServerPlayer[] ranking) {
        for(int i  = 0; i < ranking.length; i++){
            Printer.printStandardMessage((i+1) + " -> " + ranking[i].getUsername() + " : " + ranking[i].getPersonalBoard().getValuables().getPoints().get(PointType.VICTORY));
        }
        Printer.printStandardMessage("Game ended.");
    }

    @Override
    public void showMainBoard(){
        Game game = getClient().getGameModel();
        Printer.printInformationMessage(game.toString());
    }

    @Override
    public void showPersonalBoards() {
        Game game = getClient().getGameModel();
        for(String username : game.getPlayersUsername())
            if(username.equals(getClient().getUsername()))
                Printer.printInformationMessage(game.getPlayer(username).toString());
            else
                Printer.printInformationMessage(game.getPlayer(username).toStringSmall());
    }

    @Override
    public void setFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex) {
        Player player = getClient().getPlayer();
        try{
            moveDone = true;
            getClient().getGameModel().pickupDevelopmentCardFromTower(player, familyMemberColor, servants, towerIndex, cellIndex, this);
            getClient().notifySetFamilyMemberInTower(familyMemberColor, servants, towerIndex, cellIndex);
        } catch (GameException e) {
            moveDone = false;
            Printer.printDebugMessage("You can't place your familiar in this place. " + e.getError());
        } catch (IndexOutOfBoundsException e){
            moveDone = false;
            Printer.printDebugMessage("You can't place your familiar in this place. Wrong coordinates. " + e.getMessage());
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    @Override
    public void setFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants){
        Player player = getClient().getPlayer();
        try {
            moveDone = true;
            getClient().getGameModel().placeFamilyMemberInsideCouncilPalace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInCouncil(familyMemberColor, servants);
        } catch (GameException e){
            moveDone = false;
            Printer.printDebugMessage("Error while placing your family member in council palace. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    @Override
    public void setFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int marketIndex) {
        Player player = getClient().getPlayer();
        try{
            moveDone = true;
            getClient().getGameModel().placeFamilyMemberInsideMarket(player,familyMemberColor,marketIndex, servants, this);
            getClient().notifySetFamilyMemberInMarket(familyMemberColor, servants, marketIndex);
        } catch (GameException e){
            moveDone = false;
            Printer.printDebugMessage("Error while placing your family member in the market. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    @Override
    public void setFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants) {
        Player player = getClient().getPlayer();
        try {
            moveDone = true;
            getClient().getGameModel().placeFamilyMemberInsideProductionSimpleSpace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInProductionSimple(familyMemberColor, servants);
        } catch (GameException e){
            moveDone = false;
            Printer.printDebugMessage("Error while placing your family member in production simple space. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    @Override
    public void setFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants) {
        Player player = getClient().getPlayer();
        try{
            moveDone = true;
            getClient().getGameModel().placeFamilyMemberInsideHarvestSimpleSpace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInHarvestSimple(familyMemberColor, servants);
        } catch (GameException e){
            moveDone = false;
            Printer.printDebugMessage("Error while placing your family member in harvest simple space. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    @Override
    public void setFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants) {
        Player player = getClient().getPlayer();
        try{
            moveDone = true;
            getClient().getGameModel().placeFamilyMemberInsideProductionExtendedSpace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInProductionExtended(familyMemberColor, servants);
        } catch (GameException e){
            moveDone = false;
            Printer.printDebugMessage("Error while placing your family member in production extended space. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    @Override
    public void setFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants) {
        Player player = getClient().getPlayer();
        try{
            moveDone = true;
            getClient().getGameModel().placeFamilyMemberInsideHarvestExtendedSpace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInHarvestExtended(familyMemberColor, servants);
        } catch (GameException e){
            moveDone = false;
            Printer.printDebugMessage("Error while placing your family member in harvest extended space. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    @Override
    public void discardLeader(String leaderName) {
        Player player = getClient().getPlayer();
        int i = 0;
        List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
        for(LeaderCard leaderCard : leaderCards){
            if(leaderCard.getLeaderCardName().toLowerCase().equals(leaderName.toLowerCase()))
                break;
            i++;
        }
        getClient().getGameModel().discardLeaderCard(player, i, this);
        getClient().notifyDiscardLeader(i);
        gameScreen = new TurnScreen(this, moveDone);
    }

    @Override
    public void notifyExcommunicationChoice(boolean choice) {
        getClient().notifyExcommunicationChoice(choice);
    }

    @Override
    public void notifyEndTurn() {
        getClient().endTurn();
    }

    @Override
    public void activateLeader(String leaderName, int servants) {
        Player player = getClient().getPlayer();
        try{
            int i = 0;
            List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
            for(LeaderCard leaderCard : leaderCards){
                if(leaderCard.getLeaderCardName().toLowerCase().equals(leaderName.toLowerCase()))
                    break;
                i++;
            }
            getClient().getGameModel().activateLeaderCard(player, i, servants, this);
            getClient().notifyActivateLeader(i, servants);
        } catch (GameException e){
            Printer.printDebugMessage("Error while activate you leader card. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    private boolean isSelectable(TowerCell cell, PointsAndResources discount){
        try{
            cell.checkResourcesToBuyTheCard(getClient().getPlayer(), discount);
            return true;
        } catch (GameException e){
            return false;
        }
    }

}

