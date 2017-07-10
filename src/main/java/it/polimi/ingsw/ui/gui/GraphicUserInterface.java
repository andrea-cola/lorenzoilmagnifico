package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.LEPicoDellaMirandola;
import it.polimi.ingsw.ui.AbstractUserInterface;
import it.polimi.ingsw.ui.UserInterface;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.server.ServerPlayer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import javafx.scene.control.Label;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import java.util.List;
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

    private static final int FRAME_HEIGHT = 800;
    private static final int FRAME_WIDTH = 1000;

    private final Lock lock = new ReentrantLock();
    private JFrame mainFrame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private StartingStage startingStage;
    private ChooseConnectionStage chooseConnectionStage;
    private LoginStage loginStage;
    private JoinRoomStage joinRoomStage;
    private CreateRoomStage createRoomStage;
    private ChoosePersonalBoardTileStage choosePersonalBoardTileStage;
    private ChooseLeaderCardStage chooseLeaderCardStage;
    private JFrame leaderFrame;

    private MainBoardStage mainBoardStage;

    private boolean usedMember;
    /**
     * Constructor
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
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        addStagesToPanel();
        showStartingStage();
    }

    private void addStagesToPanel(){
        startingStage = new StartingStage();
        mainPanel.add(startingStage, START);
        chooseConnectionStage = new ChooseConnectionStage(getClient()::setNetworkSettings, this);
        mainPanel.add(chooseConnectionStage, CONNECTION);
        joinRoomStage = new JoinRoomStage(getClient()::joinRoom);
        mainPanel.add(joinRoomStage, JOIN_ROOM);
        createRoomStage = new CreateRoomStage(getClient()::createRoom, this);
        mainPanel.add(createRoomStage, CREATE_ROOM);
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
        cardLayout.show(mainPanel, START);
        while (startingStage.getFinished()) {
            lock.lock();
        }
    }

    @Override
    public void chooseConnectionType() {
        lock.lock();
        cardLayout.show(mainPanel, CONNECTION);
        if (chooseConnectionStage.getFinished()) {
            lock.unlock();
            loginScreen();
        }
    }

    @Override
    public void loginScreen() {
        loginStage = new LoginStage(getClient()::loginPlayer, this);
        mainPanel.add(loginStage, LOGIN);
        cardLayout.show(mainPanel, LOGIN);
    }

    @Override
    public void joinRoomScreen() {
        cardLayout.show(mainPanel, JOIN_ROOM);
    }

    @Override
    public void createRoomScreen() {
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Lorenzo il Magnifico");
                alert.setHeaderText("Welcome!");
                alert.setContentText("The game is now started");
                alert.showAndWait();
            }
        });
    }

    @Override
    public void turnScreen(String username, long seconds) {
        if (username.equals(getClient().getUsername())) {
            if (this.getClient().getMoveMessages() != null) {
                Platform.runLater(new Runnable() {
                    List<String> messages;
                    @Override
                    public void run() {
                        messages = getClient().getMoveMessages();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Notification");
                        alert.setHeaderText("Moves of the other player");
                        for (String message : messages)
                            alert.setContentText(message);
                        alert.showAndWait();
                    }
                });
            }
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
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Excommunication Choice");
                    alert.setHeaderText("Excommunication Choice");
                    alert.setContentText("Do you want to be excommunicated?");
                    ButtonType yes = new ButtonType("YES", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(yes, no);
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent()) {
                        if (result.get() == yes) {
                            getClient().notifyExcommunicationChoice(true);
                        } else {
                            getClient().notifyExcommunicationChoice(false);
                        }
                    }
                }
            });
        }else{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Excommunication");
                    alert.setHeaderText("Excommunication Information");
                    alert.setContentText("You have been excommunicated!");
                    alert.showAndWait();
                }
            });
        }
        updateMainBoard();
    }

    @Override
    public void notifyEndGame(ServerPlayer[] ranking) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("End Game");
                alert.setHeaderText("Player points");
                GridPane points = new GridPane();
                points.setAlignment(Pos.CENTER);
                points.setHgap(10);
                points.setVgap(10);
                points.setPadding(new Insets(20, 150, 10, 10));
                for (int i = 0; i <ranking.length ; i++) {
                    points.add(new Label(ranking[i].getUsername() + ": "), 0, i);
                    points.add(new Label("" + ranking[i].getPersonalBoard().getValuables().getPoints().get(PointType.VICTORY).toString()), 1, i);
                }
                alert.getDialogPane().setContent(points);
                alert.showAndWait();
            }
        });
    }

    @Override
    public void showPersonalBoardStage(Player player) {
        SwingUtilities.invokeLater(() -> {
            JFrame jframe = new JFrame();
            jframe.setResizable(false);
            jframe.add(new PersonalBoardStage(player), BorderLayout.CENTER);
            jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jframe.pack();
            jframe.setVisible(true);

        });
    }

    @Override
    public void showPersonalTileBoardStage(Player player) {
        SwingUtilities.invokeLater(() -> {
            JFrame jframe = new JFrame();
            jframe.add(new PersonalTileBoardStage(player), BorderLayout.CENTER);
            jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jframe.pack();
            jframe.setVisible(true);
        });
    }

    @Override
    public void showLeaderCards(Player player) {
        SwingUtilities.invokeLater(() -> {
            JFrame leaderFrame = new JFrame();
            leaderFrame.add(new LeaderCardStage(this, player), BorderLayout.CENTER);
            leaderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            leaderFrame.pack();
            leaderFrame.setVisible(true);
        });
    }

    @Override
    public void showGameException(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Warning Information");
                alert.setContentText(message);
                alert.showAndWait();
            }
        });
    }

    @Override
    public void chooseCouncilPrivilegeForLeader(Player player) {
        ArrayList<Privilege> privilegesChosen = new ArrayList<>();

        Dialog<Pair<String, String>> dialog = new Dialog();
        dialog.setTitle("Council privilege choice");
        dialog.setHeaderText("Choose a council privilege for Leader Card");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
        ChoiceBox<String> choicesLeader = new ChoiceBox<>();
        int k = 0;
        for (LeaderCard leaderCard : leaderCards){
            choicesLeader.getItems().add(k, leaderCard.getLeaderCardName());
            k++;
        }
        choicesLeader.getSelectionModel().selectFirst();
        ChoiceBox<String> choicesPrivilege = new ChoiceBox<>();
        choicesPrivilege.getItems().add("1 wood & 1 servant");
        choicesPrivilege.getItems().add("2 servants");
        choicesPrivilege.getItems().add("2 coins");
        choicesPrivilege.getItems().add("2 military points");
        choicesPrivilege.getItems().add("2 faiths points");
        choicesPrivilege.getSelectionModel().selectFirst();
        grid.add(choicesLeader, 0, 1);
        grid.add(choicesPrivilege, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton-> {
            if (dialogButton == ButtonType.OK)
                return new Pair<>(choicesLeader.getValue(), choicesPrivilege.getValue());
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        if(result.isPresent()) {
            int choiceLeader = 0;
            int i = 0;
            for(LeaderCard leaderCard : leaderCards) {
                if (leaderCard.getLeaderCardName().equals(result.get().getKey()))
                    choiceLeader = i;
                i++;
            }
            CouncilPrivilege councilPrivilege = new CouncilPrivilege(5);
            Privilege[] privileges = councilPrivilege.getPrivileges();
            int choicePrivilege = 0;
            for(int j = 0; j<choicesPrivilege.getItems().size() ; j++)
                if(choicesPrivilege.getValue().equals(choicesPrivilege.getItems().get(j)))
                    choicePrivilege = j;
            Privilege privilege = councilPrivilege.getPrivileges()[choicePrivilege];
            getClient().getGameModel().discardLeaderCard(player, choiceLeader, privilege);
            updateMainBoard();
        }
    }

    @Override
    public void notifyEndTurnStage() {
        getClient().endTurn();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText("Your turn is ended, it takes the next one.");
                alert.showAndWait();
            }
        });
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
            leaderFrame.dispose();
        } catch (GameException e) {
            showGameException(e.getError().toString());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege) {
        System.out.println("CIAONE");
        ArrayList<Privilege> privilegesChosen = new ArrayList<>();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Council Privilege");
        alert.setHeaderText("Choose " + councilPrivilege.getNumberOfCouncilPrivileges() + " council privileges");
        HBox content = new HBox(20);
        content.setPadding(new Insets(20, 150, 10, 10));
        CheckBox checkBox = new CheckBox("1 wood & 1 servant");
        CheckBox checkBox1 = new CheckBox("2 servants");
        CheckBox checkBox2 = new CheckBox("2 coins");
        CheckBox checkBox3 = new CheckBox("2 military points");
        CheckBox checkBox4 = new CheckBox("2 faiths points");
        System.out.println("CIAONE 2");
        content.getChildren().addAll(checkBox, checkBox1, checkBox2, checkBox3, checkBox4);
        alert.getDialogPane().setContent(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == ButtonType.OK) {
                if (checkBox.isSelected())
                    privilegesChosen.add(councilPrivilege.getPrivileges()[0]);
                if (checkBox1.isSelected())
                    privilegesChosen.add(councilPrivilege.getPrivileges()[1]);
                if (checkBox2.isSelected())
                    privilegesChosen.add(councilPrivilege.getPrivileges()[2]);
                if (checkBox3.isSelected())
                    privilegesChosen.add(councilPrivilege.getPrivileges()[3]);
                if (checkBox4.isSelected())
                    privilegesChosen.add(councilPrivilege.getPrivileges()[4]);
            }

        privilegesChosen = new ArrayList<>(privilegesChosen.subList(0, councilPrivilege.getNumberOfCouncilPrivileges()));
        if (getClient().getPlayerTurnChoices().containsKey(reason)) {
            ArrayList<Privilege> arrayList = (ArrayList<Privilege>) getClient().getPlayerTurnChoices().get(reason);
            arrayList.addAll(privilegesChosen);
        }else
            getClient().setPlayerTurnChoices(reason, privilegesChosen);
        updateMainBoard();
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
        updateMainBoard();
        return choice;
    }

    @Override
    public int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableToEarn) {
        List<String> choices = new ArrayList<>();
        for(int i = 0; i < valuableToPay.length; i++){
            choices.add(valuableToPay[i] + " -> " + valuableToEarn[i]);
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(valuableToPay[0] +" -> " + valuableToEarn[0] , choices);
        dialog.setTitle("Choose double cost");
        dialog.setHeaderText("Choose between two costs.");
        dialog.setContentText("Choose your letter:");
        Optional<String> result = dialog.showAndWait();
        int choice = 1;
        if(result.isPresent()) {
            for(int j = 0; j < choices.size(); j++)
                if(choices.get(j).equals(result.get()))
                    choice = j;
        }
        getClient().setPlayerTurnChoices(card, choice);
        updateMainBoard();
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
        updateMainBoard();
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
                if (tower.getColor().equals(developmentCardColor))
                    for (TowerCell towerCell : tower.getTowerCells())
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
        updateMainBoard();
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
        updateMainBoard();
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
            updateMainBoard();
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