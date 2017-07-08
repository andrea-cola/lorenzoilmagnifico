package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.LEPicoDellaMirandola;
import it.polimi.ingsw.ui.AbstractUserInterface;
import it.polimi.ingsw.ui.UserInterface;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.server.ServerPlayer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class manage the graphic user interface of the game
 */
public class GraphicUserInterface extends AbstractUserInterface implements MainBoardStage.CallbackInterface, InformationCallback {

    private static final String START = "startinStage";
    private static final String CONNECTION = "connnectionStage";
    private static final String LOGIN = "loginStage";
    private static final String JOIN_ROOM = "joinRoomStage";
    private static final String CREATE_ROOM = "createRoomStage";
    private static final String PERS_TILE_CHOICE = "personalTileStage";
    private static final String LEADER_CARD = "leaderCardStage";
    private static final String MAIN_BOARD = "mainBoardStage";

    private static final int FRAME_HEIGHT = 750;
    private static final int FRAME_WIDTH = 1000;

    private final Lock lock = new ReentrantLock();
    private JFrame mainFrame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private MainBoardStage mainBoardStage;

    private boolean usedMember;
    /**
     * Constructor
     *
     * @param controller
     * @throws InterruptedException
     */
    public GraphicUserInterface(UserInterface controller) throws InterruptedException {
        super(controller);
        mainFrame = new JFrame("Lorenzo Il Magnifico");
        mainFrame.setUndecorated(false);
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setLocation(150, 150);
        mainFrame.add(mainPanel);
        showStartingStage();
    }
    @Override
    public void setUsedMember(boolean flag){
        this.usedMember = flag;
    }

    @Override
    public boolean getUsedMember(){
        return usedMember;
    }

    private void showStartingStage() {
        mainFrame.setResizable(false);
        StartingStage startingStage = new StartingStage();
        mainPanel.add(startingStage, START);
        mainFrame.setVisible(true);
        cardLayout.show(mainPanel, START);
        while (startingStage.getFinished()) {
            lock.lock();
        }
    }

    @Override
    public void chooseConnectionType() {
        lock.lock();
        ChooseConnectionStage chooseConnectionStage = new ChooseConnectionStage(getClient()::setNetworkSettings);
        mainPanel.add(chooseConnectionStage, CONNECTION);
        cardLayout.show(mainPanel, CONNECTION);
        if (chooseConnectionStage.getFinished()) {
            lock.unlock();
            loginScreen();
        }
    }

    @Override
    public void loginScreen() {
        LoginStage loginStage = new LoginStage(getClient()::loginPlayer);
        mainPanel.add(loginStage, LOGIN);
        cardLayout.show(mainPanel, LOGIN);
    }

    @Override
    public void joinRoomScreen() {
        JoinRoomStage joinRoomStage = new JoinRoomStage(getClient()::joinRoom);
        mainPanel.add(joinRoomStage, JOIN_ROOM);
        cardLayout.show(mainPanel, JOIN_ROOM);
    }

    @Override
    public void createRoomScreen() {
        CreateRoomStage createRoomStage = new CreateRoomStage(getClient()::createRoom);
        mainPanel.add(createRoomStage, CREATE_ROOM);
        cardLayout.show(mainPanel, CREATE_ROOM);
    }

    @Override
    public void choosePersonalTile(List<PersonalBoardTile> personalBoardTileList) {
        ChoosePersonalBoardTileStage choosePersonalBoardTileStage = new ChoosePersonalBoardTileStage(getClient()::sendPersonalBoardTileChoice, personalBoardTileList);
        mainPanel.add(choosePersonalBoardTileStage, PERS_TILE_CHOICE);
        cardLayout.show(mainPanel, PERS_TILE_CHOICE);
    }

    @Override
    public void chooseLeaderCards(List<LeaderCard> leaderCards) {
        ChooseLeaderCardStage chooseLeaderCardStage = new ChooseLeaderCardStage(getClient()::notifyLeaderCardChoice, leaderCards);
        mainPanel.add(chooseLeaderCardStage, LEADER_CARD);
        cardLayout.show(mainPanel, LEADER_CARD);
    }

    @Override
    public void notifyGameStarted() {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Lorenzo il Magnifico");
       alert.setHeaderText("Welcome!");
       alert.setContentText("The game is now started");
       alert.showAndWait();
    }

    @Override
    public void turnScreen(String username, long seconds) {
        List<String> messages;
        if (this.getClient().getMoveMessages() != null) {
            messages = this.getClient().getMoveMessages();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Moves of the other player");
            for (String message : messages)
                alert.setContentText(message);
            alert.showAndWait();
        }
        if (username.equals(getClient().getUsername())) {
            mainBoardStage = new MainBoardStage(this, getClient(), this, true, false);
            mainPanel.add(mainBoardStage, MAIN_BOARD);
            cardLayout.show(mainPanel, MAIN_BOARD);
        } else {
            mainBoardStage = new MainBoardStage(this, getClient(), this, false, false);
            mainPanel.add(mainBoardStage, MAIN_BOARD);
            cardLayout.show(mainPanel, MAIN_BOARD);
        }
    }

    @Override
    public void updateMainBoard() {
        mainBoardStage = new MainBoardStage(this, getClient(), this, true, usedMember);
        mainPanel.add(mainBoardStage, MAIN_BOARD);
        cardLayout.show(mainPanel, MAIN_BOARD);
    }

    @Override
    public void supportForTheChurch(boolean flag) {
        if(flag){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Excommunication Choice");
            alert.setHeaderText("Excommunication Choice");
            alert.setContentText("Do you want to be excommunicated?");
            ButtonType yes = new ButtonType("YES");
            ButtonType no = new ButtonType("NO");
            alert.getButtonTypes().addAll(yes, no);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == yes) {
                    getClient().notifyExcommunicationChoice(true);
                } else {
                    getClient().notifyExcommunicationChoice(false);
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Excommunication");
            alert.setHeaderText("Excommunication Information");
            alert.setContentText("You have been excommunicated!");
            alert.showAndWait();
        }
    }

    @Override
    public void notifyEndGame(ServerPlayer[] ranking) {
        EventQueue.invokeLater(() -> {
            GridLayout grid = new GridLayout(0, 2);
            JPanel panel = new JPanel();
            panel.setLayout(grid);
            for(int i = 0; i <ranking.length; i++){
                JLabel name = new JLabel(ranking[i].getUsername());
                panel.add(name);
                JLabel points = new JLabel(ranking[i].getPersonalBoard().getValuables().getPoints().get(PointType.VICTORY).toString());
                panel.add(points);
            }
            JOptionPane.showMessageDialog(null, panel, "End Game", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    @Override
    public void showPersonalBoardStage(Player player) {
        SwingUtilities.invokeLater(() -> {
            JFrame jframe = new JFrame();
            jframe.setResizable(false);
            jframe.add(new PersonalBoardStage(player), BorderLayout.CENTER);
            jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            jframe.pack();
            jframe.setVisible(true);
        });
    }

    @Override
    public void showPersonalTileBoardStage(Player player) {
        SwingUtilities.invokeLater(() -> {
            JFrame jframe = new JFrame();
            jframe.add(new PersonalTileBoardStage(player), BorderLayout.CENTER);
            jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            jframe.pack();
            jframe.setVisible(true);
        });
    }

    @Override
    public void showLeaderCards(Player player) {
        SwingUtilities.invokeLater(() -> {
            JFrame jframe = new JFrame();
            jframe.add(new LeaderCardStage(this, player), BorderLayout.CENTER);
            jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            jframe.pack();
            jframe.setVisible(true);
        });
    }

    @Override
    public void showGameException(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Your data are not valid");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void notifyEndTurnStage() {
        getClient().endTurn();
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Your turn is ended, it takes the next one.", "Notification", JOptionPane.INFORMATION_MESSAGE));
    }

    @Override
    public void activeLeaderCard(String leaderName, int servants) {
        Player player = getClient().getPlayer();
        try {
            int i = 0;
            List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
            for (LeaderCard leaderCard : leaderCards) {
                if (leaderCard.getLeaderCardName().equalsIgnoreCase(leaderName))
                    break;
            }
            getClient().getGameModel().activateLeaderCard(player, i, servants, this);
            getClient().notifyActivateLeader(i, servants);
        } catch (GameException e) {
            showGameException(e.getMessage());
        }
    }

    @Override
    public void discardLeader(String leaderName) {
        Player player = getClient().getPlayer();
        int i = 0;
        List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
        for (LeaderCard leaderCard : leaderCards) {
            if (leaderCard.getLeaderCardName().equalsIgnoreCase(leaderName))
                break;
            i++;
        }
        getClient().getGameModel().discardLeaderCard(player, i, this);
        getClient().notifyDiscardLeader(i);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege) {
        List<String> choices;
        ArrayList<Privilege> privilegesChosen = new ArrayList<>();

        for(int i = 0; i < councilPrivilege.getNumberOfCouncilPrivileges(); i++){
            choices = new ArrayList<>();
            choices.add("1 wood & 1 servant");
            choices.add("2 servants");
            choices.add("2 coins");
            choices.add("2 military points");
            choices.add("2 faiths points");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("1 wood & 1 servant", choices);
            dialog.setTitle("Council privilege choice");
            dialog.setHeaderText("You still have to choose " + (councilPrivilege.getNumberOfCouncilPrivileges() - i)
                    + " council privileges.");
            dialog.setContentText("Choose your privilege:");
            Optional<String> result = dialog.showAndWait();
            int choice = 0;
            if(result.isPresent()) {
                for(int j = 0; j < choices.size(); j++)
                    if(choices.get(j).equals(result.get()))
                        choice = j;
                privilegesChosen.add(councilPrivilege.getPrivileges()[choice]);
            }
        }
        if(getClient().getPlayerTurnChoices().containsKey(reason)) {
            ArrayList<Privilege> arrayList = (ArrayList<Privilege>)getClient().getPlayerTurnChoices().get(reason);
            arrayList.addAll(privilegesChosen);
        } else {
            getClient().setPlayerTurnChoices(reason, privilegesChosen);
        }
        return privilegesChosen;
    }

    @Override
    public int chooseDoubleCost(PointsAndResources pointsAndResources, int militaryPointsGiven, int militaryPointsNeeded) {
        List<String> choices = new ArrayList<>();
        choices.add("Pay in resources");
        choices.add("Pay in military points");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Pay in resources", choices);
        dialog.setTitle("Choose double cost");
        dialog.setHeaderText("Choose between two costs.");
        dialog.setContentText("Choose your letter:");
        Optional<String> result = dialog.showAndWait();
        int choice = 1;
        if(result.isPresent()) {
            for(int j = 0; j < choices.size(); j++)
                if(choices.get(j).equals(result.get()))
                    choice = j + 1;
        }
        getClient().setPlayerTurnChoices("double-cost", choice);
        return choice;
    }

    @Override
    public int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableToEarn) {
        List<String> choices = new ArrayList<>();
        for(int i = 0; i < valuableToPay.length; i++){
            choices.add(valuableToPay[i] + " -> " + valuableToEarn[i]);
        }
        choices.add("Pay in resources");
        choices.add("Pay in military points");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Pay in resources", choices);
        dialog.setTitle("Choose double cost");
        dialog.setHeaderText("Choose between two costs.");
        dialog.setContentText("Choose your letter:");
        Optional<String> result = dialog.showAndWait();
        int choice = 1;
        if(result.isPresent()) {
            for(int j = 0; j < choices.size(); j++)
                if(choices.get(j).equals(result.get()))
                    choice = j + 1;
        }
        getClient().setPlayerTurnChoices(card, choice);
        return choice;
    }

    @Override
    public int choosePickUpDiscounts(String reason, List<PointsAndResources> discounts) {
        List<String> choices = new ArrayList<>();
        for(PointsAndResources discount : discounts)
            choices.add(discount.toString());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Choose pick up discount");
        dialog.setHeaderText("Choose a discount between those proposals.");
        dialog.setContentText("Choose your discount:");
        Optional<String> result = dialog.showAndWait();
        int choice = 0;
        if(result.isPresent()) {
            for(int j = 0; j < choices.size(); j++)
                if(choices.get(j).equals(result.get()))
                    choice = j;
        }
        getClient().setPlayerTurnChoices(reason, choice);
        return choice;
    }

    @Override
    public DevelopmentCard chooseNewCard(String reason, DevelopmentCardColor[] developmentCardColors, int diceValue, PointsAndResources discount) {
        List<String> choices = new ArrayList<>();

        MainBoard mainBoard = getClient().getGameModel().getMainBoard();
        ArrayList<DevelopmentCard> selectable = new ArrayList<>();

        for (DevelopmentCardColor developmentCardColor : developmentCardColors) {
            int newDiceValue = diceValue + getClient().getPlayer().getPersonalBoard().getDevelopmentCardColorDiceValueBonus().get(developmentCardColor) - getClient().getPlayer().getPersonalBoard().getExcommunicationValues().getDevelopmentCardDiceMalus().get(developmentCardColor);
            for (Tower tower : mainBoard.getTowers())
                if (tower.getColor().equals(developmentCardColor)) for (TowerCell towerCell : tower.getTowerCells())
                    if (towerCell.getPlayerNicknameInTheCell() == null && towerCell.getMinFamilyMemberValue() <= newDiceValue && isSelectable(towerCell, discount)) {
                        selectable.add(towerCell.getDevelopmentCard());
                        choices.add(towerCell.getDevelopmentCard().getName());
                    }
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Choose a new card");
        dialog.setHeaderText("Choose a new card between those proposals. You also have a discount: " + discount.toString());
        dialog.setContentText("Choose your new card:");
        Optional<String> result = dialog.showAndWait();
        DevelopmentCard choice = selectable.get(0);
        if(result.isPresent()) {
            for(int j = 0; j < choices.size(); j++)
                if(choices.get(j).equals(result.get()))
                    choice = selectable.get(j);
        }
        for (Tower tower : mainBoard.getTowers())
            for (TowerCell towerCell : tower.getTowerCells())
                if (towerCell.getDevelopmentCard().getName().equals(choice.getName())) {
                    LeaderCard leaderCard = getClient().getPlayer().getPersonalBoard().getLeaderCardWithName("Pico della Mirandola");
                    int devCardCoinsCost = choice.getCost().getResources().get(ResourceType.COIN);
                    if (leaderCard != null && leaderCard.getLeaderEffectActive()) {
                        //decrease card price
                        if (devCardCoinsCost >= 3)
                            choice.getCost().decrease(ResourceType.COIN, ((LEPicoDellaMirandola) leaderCard.getEffect()).getMoneyDiscount());
                        else
                            choice.getCost().decrease(ResourceType.COIN, devCardCoinsCost);
                    }
                    choice.payCost(getClient().getPlayer(), this);
                    towerCell.setPlayerNicknameInTheCell(getClient().getUsername());
                    if (towerCell.getTowerCellImmediateEffect() != null)
                        towerCell.getTowerCellImmediateEffect().runEffect(getClient().getPlayer(), this);
                }
        getClient().setPlayerTurnChoices(reason, choice);
        return choice;
    }

    @Override
    public LeaderCard copyAnotherLeaderCard(String reason) {
        List<LeaderCard> leaderCards = new ArrayList<>();
        List<String> choices = new ArrayList<>();

        for (Map.Entry pair : getClient().getGameModel().getPlayersMap().entrySet())
            if (!pair.getKey().equals(getClient().getUsername()))
                leaderCards.addAll(((Player)pair.getValue()).getPersonalBoard().getLeaderCards());

        for(LeaderCard leaderCard : leaderCards)
            choices.add(leaderCard.getLeaderCardName());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Choose Leader Card");
        dialog.setHeaderText("You have to choose a leader card");
        dialog.setContentText("Choose your leader card");
        Optional<String> result = dialog.showAndWait();
        LeaderCard choice = leaderCards.get(0);
        if(result.isPresent()){
            for(int i = 0; i < choices.size(); i++)
                if(choices.get(i).equals(result.get()))
                    choice = leaderCards.get(i);
        }
        getClient().getPlayerTurnChoices().put(reason, choice);
        return choice;
    }

    @Override
    public FamilyMemberColor choiceLeaderDice(String reason) {
        List<String> choices = new ArrayList<String>() {};
        for (FamilyMemberColor familyMemberColor : FamilyMemberColor.values()) {
            if(!FamilyMemberColor.NEUTRAL.equals(familyMemberColor))
                choices.add(familyMemberColor.toString());
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(FamilyMemberColor.ORANGE.toString(), choices);
        dialog.setTitle("Choose Leader Dice");
        dialog.setHeaderText("You have to choose a family member color");
        dialog.setContentText("Choose your family member");
        Optional<String> result = dialog.showAndWait();
        FamilyMemberColor choice = FamilyMemberColor.ORANGE;
        if(result.isPresent()) {
            for(int j = 0; j < choices.size(); j++)
                if(choices.get(j).equals(result.get()))
                    choice = FamilyMemberColor.values()[j];
            getClient().getPlayerTurnChoices().put(reason, choice);
        }
        return choice;
    }

    private boolean isSelectable(TowerCell cell, PointsAndResources discount) {
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
}