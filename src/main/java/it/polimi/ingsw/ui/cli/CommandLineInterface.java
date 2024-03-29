package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.LEPicoDellaMirandola;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.ui.AbstractUserInterface;
import it.polimi.ingsw.ui.UserInterface;
import it.polimi.ingsw.utility.Printer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class manages the command line interface of the game.
 */
public class CommandLineInterface extends AbstractUserInterface implements GameScreen.GameCallback, ExcommunicationScreen.ExcommunicationCallback, InformationCallback {

    /**
     * Keyboard input handler.
     */
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
                    Printer.printDebugMessage(this.getClass().getSimpleName(), "Wrong command, please retry.");
                } catch (IOException e){
                    Printer.printDebugMessage(this.getClass().getSimpleName(), "Error while reading from keyboard.");
                    break;
                }
            }
        }

        /**
         * Stop the listener.
         */
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

    /**
     * Object used to handle thread concurrency.
     */
    private boolean needToPause = false;

    /**
     * Screen used to handle start up process.
     */
    private BasicScreen screen;

    /**
     * Screen used to handle game logic.
     */
    private BasicGameScreen gameScreen;

    /**
     * Flag that indicates if the player has already done a move.
     */
    private boolean moveDone;

    /**
     * Class constructor.
     * @param controller ui.
     */
    public CommandLineInterface(UserInterface controller) {
        super(controller);
    }

    /**
     * Pause keyboard listener thread.
     */
    private synchronized void pause() {
        needToPause = true;
    }

    /**
     * Wake up keyboard listener thread.
     */
    private synchronized void wakeUp() {
        needToPause = false;
        this.notifyAll();
    }

    /**
     * Choose connection type.
     */
    @Override
    public void chooseConnectionType(){
        screen = new ChooseConnectionScreen(getClient()::setNetworkSettings);
    }

    /**
     * Run login screen.
     */
    @Override
    public void loginScreen() {
        screen = new LoginSignInScreen(getClient()::loginPlayer);
    }

    /**
     * Run join room screen.
     */
    @Override
    public void joinRoomScreen() {
        screen = new JoinRoomScreen(getClient()::joinRoom);
        Printer.printStandardMessage("Room join OK.");
        screen = null;
    }

    /**
     * Run room create screen.
     */
    @Override
    public void createRoomScreen(){
        screen = new CreateRoomScreen(getClient()::createRoom);
        Printer.printStandardMessage("Room creation OK.");
        screen = null;
    }

    /**
     * Run choose personal tile method.
     * @param personalBoardTileList to choose.
     */
    @Override
    public void choosePersonalTile(List<PersonalBoardTile> personalBoardTileList) {
        screen = new ChoosePersonalBoardTileScreen(getClient()::sendPersonalBoardTileChoice, personalBoardTileList);
    }

    /**
     * Run choose leader cards method.
     * @param leaderCards to choose.
     */
    @Override
    public void chooseLeaderCards(List<LeaderCard> leaderCards) {
        screen = new ChooseLeaderCardsScreen(getClient()::notifyLeaderCardChoice, leaderCards);
    }

    /**
     * Notify game started.
     */
    @Override
    public void notifyGameStarted() {
        ConsoleListener consoleListener = new ConsoleListener();
        consoleListener.start();
        screen = null;
        Printer.printStandardMessage("Game started.");
    }

    /**
     * Run turn screen.
     * @param username of the player has turn token.
     * @param seconds of the turn.
     */
    @Override
    public void turnScreen(String username, long seconds) {
        Printer.printStandardMessage("\n\n[MOVES IN THE LAST TURN]");
        if(this.getClient().getMoveMessages() != null)
            this.getClient().getMoveMessages().forEach(Printer::printStandardMessage);
        if(username.equals(getClient().getUsername())){
            moveDone = false;
            if(getClient().getPlayer().getPersonalBoard().getExcommunicationValues().getSkipFirstMove()
                    && getClient().getGameModel().getMove() == 1){
                Printer.printStandardMessage("You lost this turn because of the excommunication you got.");
                this.notifyEndTurn();
            }
            else{
                gameScreen = new TurnScreen(this, moveDone);
            }
        }
        else {
            Printer.printStandardMessage("Turn of " + username + ". Please wait for " + seconds + " seconds.");
            gameScreen = new GameScreen(this);
        }
    }

    /**
     * Choose council privilege.
     * @param reason of the choice.
     * @param councilPrivilege chosen.
     * @return list of privileges.
     */
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
        wakeUp();
        return privilegeArraysList;
    }

    /**
     * Choose double cost
     * @param cost chosen
     * @param militaryPointsToPay
     * @param militaryPointsNeeded
     * @return choice.
     */
    @Override
    public int chooseDoubleCost(PointsAndResources cost, int militaryPointsToPay, int militaryPointsNeeded){
        pause();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        gameScreen = null;
        int key;
        Printer.printInformationMessage("[ DOUBLE COST CHOICE ]\nYour card has a double cost, you need to choose what to pay.");
        Printer.printInformationMessage("1 -> " + cost.toString());
        Printer.printInformationMessage("2 -> Military points needed = " + militaryPointsNeeded + " Military points to pay =" + militaryPointsToPay);
        do {
            try {
                key = Integer.parseInt(r.readLine());
            } catch (ClassCastException | NumberFormatException | IOException e) {
                key = 1;
            }
        } while (key < 1 || key > 2);
        getClient().setPlayerTurnChoices("double-cost", key);
        wakeUp();
        return key;
    }

    /**
     * Choose exchange effect to apply
     * @param card selected
     * @param valuableToPay array
     * @param valuableEarned array
     * @return choice.
     */
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
        wakeUp();
        return key;
    }

    /**
     * Choose discount.
     * @param reason of the choice.
     * @param discounts chosen.
     * @return choice.
     */
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
        wakeUp();
        return key;
    }

    /**
     * Choose new card.
     * @param reason of the choice.
     * @param developmentCardColors available.
     * @param diceValue of the choice.
     * @param discount on the card color.
     * @return card choice.
     */
    @Override
    public DevelopmentCard chooseNewCard(String reason,  DevelopmentCardColor[] developmentCardColors, int diceValue, PointsAndResources discount) {
        pause();
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        gameScreen = null;

        MainBoard mainBoard = getClient().getGameModel().getMainBoard();
        DevelopmentCard card = null;
        ArrayList<DevelopmentCard> selectable = new ArrayList<>();
        System.out.println("[ CHOOSE NEW CARD ]\nYou can pick up a new card. Choose among those proposal.");
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
        }
        getClient().setPlayerTurnChoices(reason, card);
        wakeUp();
        return card;
    }

    /**
     * Handle Lorenzo De Medici effect.
     * @param reason of the choice.
     * @return card choice.
     */
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
        wakeUp();
        return leaderCards.get(key);
    }

    /**
     * Handle Montefeltro effect.
     * @param reason of the choice.
     * @return family member color choice.
     */
    @Override
    public FamilyMemberColor choiceLeaderDice(String reason) {
        pause();
        int i = 0;
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        Printer.printInformationMessage("[ CHOOSE NEW CARD ]\nYou can choose a color between those proposals.");
        for(FamilyMemberColor familyMemberColor : FamilyMemberColor.values()) {
            Printer.printInformationMessage( (i+1) + " -> " + familyMemberColor);
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
        wakeUp();
        return FamilyMemberColor.values()[key];
    }

    /**
     * Handle excommunication logic on the client.
     * @param flag that indicates the excommunication status.
     */
    @Override
    public void supportForTheChurch(boolean flag) {
        if(flag){
            gameScreen = new ExcommunicationScreen(this);
        } else {
            Printer.printStandardMessage("You have been excommunicated!!");
        }
    }

    /**
     * Notify game ended.
     * @param ranking of the game.
     */
    @Override
    public void notifyEndGame(ServerPlayer[] ranking) {
        for(int i  = 0; i < ranking.length; i++){
            Printer.printStandardMessage((i+1) + " -> " + ranking[i].getUsername() + " : " + ranking[i].getPersonalBoard().getValuables().getPoints().get(PointType.VICTORY));
        }
        Printer.printStandardMessage("Game ended.");
    }

    /**
     * Show main board.
     */
    @Override
    public void showMainBoard(){
        Game game = getClient().getGameModel();
        Printer.printInformationMessage(game.toString());
    }

    /**
     * Show personal boards.
     */
    @Override
    public void showPersonalBoards() {
        Game game = getClient().getGameModel();
        for(String username : game.getPlayersUsername())
            if(username.equals(getClient().getUsername()))
                Printer.printInformationMessage(game.getPlayer(username).toString());
            else
                Printer.printInformationMessage(game.getPlayer(username).toStringSmall());
    }

    /**
     * Set family member in tower.
     * @param familyMemberColor placed.
     * @param servants chosen.
     * @param towerIndex of tower.
     * @param cellIndex of cell in the tower.
     */
    @Override
    public void setFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex) {
        Player player = getClient().getPlayer();
        try{
            getClient().getPlayerTurnChoices().clear();
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

    /**
     * Set family member in council.
     * @param familyMemberColor placed.
     * @param servants chosen.
     */
    @Override
    public void setFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants){
        Player player = getClient().getPlayer();
        try {
            getClient().getPlayerTurnChoices().clear();
            moveDone = true;
            getClient().getGameModel().placeFamilyMemberInsideCouncilPalace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInCouncil(familyMemberColor, servants);
        } catch (GameException e){
            moveDone = false;
            Printer.printDebugMessage("Error while placing your family member in council palace. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    /**
     * Set family member in market.
     * @param familyMemberColor placed.
     * @param servants chosen.
     * @param marketIndex in the market.
     */
    @Override
    public void setFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int marketIndex) {
        Player player = getClient().getPlayer();
        try{
            getClient().getPlayerTurnChoices().clear();
            moveDone = true;
            getClient().getGameModel().placeFamilyMemberInsideMarket(player,familyMemberColor,marketIndex, servants, this);
            getClient().notifySetFamilyMemberInMarket(familyMemberColor, servants, marketIndex);
        } catch (GameException e){
            moveDone = false;
            Printer.printDebugMessage("Error while placing your family member in the market. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    /**
     * Set family member in production simple.
     * @param familyMemberColor placed.
     * @param servants chosen.
     */
    @Override
    public void setFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants) {
        Player player = getClient().getPlayer();
        try {
            getClient().getPlayerTurnChoices().clear();
            moveDone = true;
            getClient().getGameModel().placeFamilyMemberInsideProductionSimpleSpace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInProductionSimple(familyMemberColor, servants);
        } catch (GameException e){
            moveDone = false;
            Printer.printDebugMessage("Error while placing your family member in production simple space. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    /**
     * Set family member in harvest simple.
     * @param familyMemberColor placed.
     * @param servants chosen.
     */
    @Override
    public void setFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants) {
        Player player = getClient().getPlayer();
        try{
            getClient().getPlayerTurnChoices().clear();
            moveDone = true;
            getClient().getGameModel().placeFamilyMemberInsideHarvestSimpleSpace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInHarvestSimple(familyMemberColor, servants);
        } catch (GameException e){
            moveDone = false;
            Printer.printDebugMessage("Error while placing your family member in harvest simple space. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    /**
     * Set family member in production extended.
     * @param familyMemberColor placed.
     * @param servants chosen.
     */
    @Override
    public void setFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants) {
        Player player = getClient().getPlayer();
        try{
            getClient().getPlayerTurnChoices().clear();
            moveDone = true;
            getClient().getGameModel().placeFamilyMemberInsideProductionExtendedSpace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInProductionExtended(familyMemberColor, servants);
        } catch (GameException e){
            moveDone = false;
            Printer.printDebugMessage("Error while placing your family member in production extended space. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    /**
     * Set family member in harvest extended.
     * @param familyMemberColor placed.
     * @param servants chosen.
     */
    @Override
    public void setFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants) {
        Player player = getClient().getPlayer();
        try{
            getClient().getPlayerTurnChoices().clear();
            moveDone = true;
            getClient().getGameModel().placeFamilyMemberInsideHarvestExtendedSpace(player, familyMemberColor, servants, this);
            getClient().notifySetFamilyMemberInHarvestExtended(familyMemberColor, servants);
        } catch (GameException e){
            moveDone = false;
            Printer.printDebugMessage("Error while placing your family member in harvest extended space. Please retry.");
        }
        gameScreen = new TurnScreen(this, moveDone);
    }

    /**
     * Discard leader card.
     */
    @Override
    public void discardLeader(String leaderName) {
        Player player = getClient().getPlayer();
        int i = 0;
        List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
        for(LeaderCard leaderCard : leaderCards){
            if(leaderCard.getLeaderCardName().equalsIgnoreCase(leaderName))
                break;
            i++;
        }
        getClient().getGameModel().discardLeaderCard(player, i, this);
        getClient().notifyDiscardLeader(i);
        gameScreen = new TurnScreen(this, moveDone);
    }

    /**
     * Notify excommunicaiton choice.
     * @param choice about excommunication.
     */
    @Override
    public void notifyExcommunicationChoice(boolean choice) {
        getClient().notifyExcommunicationChoice(choice);
    }

    /**
     * Notify end turn.
     */
    @Override
    public void notifyEndTurn() {
        getClient().endTurn();
    }

    /**
     * Activate leader.
     */
    @Override
    public void activateLeader(String leaderName, int servants) {
        Player player = getClient().getPlayer();
        try{
            int i = 0;
            List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
            for(LeaderCard leaderCard : leaderCards){
                if(leaderCard.getLeaderCardName().equalsIgnoreCase(leaderName))
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

    /**
     * Determinates if a card can be taken or not.
     * @param cell of the tower.
     * @param discount applied.
     * @return flag.
     */
    private boolean isSelectable(TowerCell cell, PointsAndResources discount){
        if (cell.getDevelopmentCard().getMultipleRequisiteSelectionEnabled()){
            if (getClient().getPlayer().getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY) >= cell.getDevelopmentCard().getMilitaryPointsRequired()){
                return true;
            }
            for (Map.Entry<ResourceType, Integer> entry : cell.getDevelopmentCard().getCost().getResources().entrySet()) {
                if (cell.getDevelopmentCard().getCost().getResources().get(entry.getKey()) - discount.getResources().get(entry.getKey())
                        > getClient().getPlayer().getPersonalBoard().getValuables().getResources().get(entry.getKey())) {
                    return false;
                }
            }
        } else {
            for (Map.Entry<ResourceType, Integer> entry : cell.getDevelopmentCard().getCost().getResources().entrySet()) {
                if (cell.getDevelopmentCard().getCost().getResources().get(entry.getKey()) - discount.getResources().get(entry.getKey())
                        > getClient().getPlayer().getPersonalBoard().getValuables().getResources().get(entry.getKey())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Show game exceptions.
     * @param message error to be shown.
     */
    @Override
    public void showGameException(String message) {
        Printer.printDebugMessage(message);
    }
}

