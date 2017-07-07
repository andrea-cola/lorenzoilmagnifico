package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.ui.AbstractUserInterface;
import it.polimi.ingsw.ui.UserInterface;
import javafx.scene.control.ChoiceBox;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.server.ServerPlayer;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.jar.JarEntry;

/**
 * This class manage the graphic user interface of the game
 */
public class GraphicUserInterface extends AbstractUserInterface implements MainBoardStage.CallbackInterface, InformationCallback {

    private final static String START = "startinStage";
    private final static String CONNECTION = "connnectionStage";
    private final static String LOGIN = "loginStage";
    private final static String JOIN_ROOM = "joinRoomStage";
    private final static String CREATE_ROOM = "createRoomStage";
    private final static String PERS_TILE_CHOICE = "personalTileStage";
    private final static String LEADER_CARD = "leaderCardStage";
    private final static String MAIN_BOARD = "mainBoardStage";

    private final static String DATA_NOT_VALID = "Your data are not valid";

    private final static int FRAME_HEIGHT = 700;
    private final static int FRAME_WIDTH = 1000;

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
    private MainBoardStage mainBoardStage;
    private PersonalBoardStage personalBoardStage;
    private PersonalTileBoardStage personalTileBoardStage;
    private LeaderCardStage leaderCardStage;

    private ArrayList<Privilege> privileges;
    private int key;
    private DevelopmentCard card;
    private boolean choice;
    private boolean finished;

    private CountDownLatch countDownLatch;

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

    private void showStartingStage() {
        mainFrame.setResizable(false);
        startingStage = new StartingStage();
        mainPanel.add(startingStage, START);
        mainFrame.setVisible(true);
        System.out.println("starting...");
        cardLayout.show(mainPanel, START);
        while (startingStage.getFinished() != true) {
            lock.lock();
        }
    }

    @Override
    public void chooseConnectionType() {
        lock.lock();
        System.out.println("connection...");
        chooseConnectionStage = new ChooseConnectionStage(getClient()::setNetworkSettings);
        mainPanel.add(chooseConnectionStage, CONNECTION);
        cardLayout.show(mainPanel, CONNECTION);
        if (chooseConnectionStage.getFinished() == true) {
            lock.unlock();
            loginScreen();
        }
    }

    @Override
    public void loginScreen() {
        System.out.println("logging...");
        loginStage = new LoginStage(getClient()::loginPlayer);
        mainPanel.add(loginStage, LOGIN);
        cardLayout.show(mainPanel, LOGIN);
    }

    @Override
    public void joinRoomScreen() {
        System.out.println("joining...");
        joinRoomStage = new JoinRoomStage(getClient()::joinRoom);
        mainPanel.add(joinRoomStage, JOIN_ROOM);
        cardLayout.show(mainPanel, JOIN_ROOM);
    }

    @Override
    public void createRoomScreen() {
        System.out.println("creating room...");
        createRoomStage = new CreateRoomStage(getClient()::createRoom);
        mainPanel.add(createRoomStage, CREATE_ROOM);
        cardLayout.show(mainPanel, CREATE_ROOM);
    }

    @Override
    public void choosePersonalTile(List<PersonalBoardTile> personalBoardTileList) {
        System.out.println("choosing tile...");
        choosePersonalBoardTileStage = new ChoosePersonalBoardTileStage(getClient()::sendPersonalBoardTileChoice, personalBoardTileList);
        mainPanel.add(choosePersonalBoardTileStage, PERS_TILE_CHOICE);
        cardLayout.show(mainPanel, PERS_TILE_CHOICE);
    }

    @Override
    public void chooseLeaderCards(List<LeaderCard> leaderCards) {
        System.out.println("choosing leader...");
        chooseLeaderCardStage = new ChooseLeaderCardStage(getClient()::notifyLeaderCardChoice, leaderCards);
        mainPanel.add(chooseLeaderCardStage, LEADER_CARD);
        cardLayout.show(mainPanel, LEADER_CARD);
    }

    @Override
    public void notifyGameStarted() {
        System.out.println("starting game...");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "The game is started", "Notification", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    @Override
    public void turnScreen(String username, long seconds) {
        System.out.println("staring " + username + " turn");
        if (username.equals(getClient().getUsername())) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(null, "Now it is yout turn", "Notification", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            mainBoardStage = new MainBoardStage(this, getClient(), this, true);
            mainPanel.add(mainBoardStage, MAIN_BOARD);
            cardLayout.show(mainPanel, MAIN_BOARD);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(null, "Please wait " + seconds + " for your turn", "Notification", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            mainBoardStage = new MainBoardStage(this, getClient(), this, false);
            mainPanel.add(mainBoardStage, MAIN_BOARD);
            cardLayout.show(mainPanel, MAIN_BOARD);
        }
    }

    @Override
    public void supportForTheChurch(boolean flag) {
        if (flag) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    int result = JOptionPane.showConfirmDialog(null, "Do you want to support the church?", "Church support",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            setChoice(true);
                            JOptionPane.getRootFrame().dispose();
                            break;
                        case JOptionPane.NO_OPTION:
                            setChoice(false);
                            JOptionPane.getRootFrame().dispose();
                            break;
                    }
                }
            });
            getClient().notifyExcommunicationChoice(getChoice());
        } else {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(null, "You have been excommunicated!!", "Excommunication", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }
    }

    @Override
    public void notifyEndGame(ServerPlayer[] ranking) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
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
            }
        });
    }

    private synchronized void setChoice(boolean choice) {
        this.choice = choice;
    }

    private synchronized boolean getChoice() {
        return this.choice;
    }

    @Override
    public void showPersonalBoardStage(Player player) {
        System.out.println("showing " + player.getUsername() + " personal board...");
        System.out.println(player.toString());
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
        System.out.println("showing " + player.getUsername() + " personal tile board...");
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
        System.out.println("showing " + player.getUsername() + " leader cards...");
        SwingUtilities.invokeLater(() -> {
            JFrame jframe = new JFrame();
            jframe.add(new LeaderCardStage(this, player), BorderLayout.CENTER);
            jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            jframe.pack();
            jframe.setVisible(true);
        });
    }

    @Override
    public void showGameException() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "Your data are not valid", "Game Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void notifyEndTurnStage() {
        System.out.println("showing ending turn");
        getClient().endTurn();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "Your turn is ended, it takes the next one.", "Notification", JOptionPane.INFORMATION_MESSAGE);
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
                if (leaderCard.getLeaderCardName().toLowerCase().equals(leaderName.toLowerCase()))
                    break;
            }
            getClient().getGameModel().activateLeaderCard(player, i, servants, this);
            getClient().notifyActivateLeader(i, servants);
        } catch (GameException e) {
            showGameException();
        }
    }

    @Override
    public void discardLeader(String leaderName) {
        Player player = getClient().getPlayer();
        int i = 0;
        List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
        for (LeaderCard leaderCard : leaderCards) {
            if (leaderCard.getLeaderCardName().toLowerCase().equals(leaderName.toLowerCase()))
                break;
            i++;
        }
        getClient().getGameModel().discardLeaderCard(player, i, this);
        getClient().notifyDiscardLeader(i);
    }

    @Override
    public ArrayList<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (IllegalAccessException | InstantiationException |
                        UnsupportedLookAndFeelException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                ArrayList<Privilege> privilegeArrayList = new ArrayList<>();
                JPanel panel = new JPanel();
                String[] privil = {"1 WOOD & 1 SERVANT", "2 SERVANTS", "2 COINS", "2 MILITARY POINTS", "1 FAITH POINTS"};
                JCheckBox[] checkBox = new JCheckBox[privil.length];
                for (int i = 0; i < checkBox.length; i++) {
                    checkBox[i] = new JCheckBox(privil[i]);
                    panel.add(checkBox[i]);
                }
                System.out.println("CIAONE");
                int result = JOptionPane.showConfirmDialog(null, panel, "Choose " + councilPrivilege.getNumberOfCouncilPrivileges() + " Privilege", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    ArrayList<Integer> choiceList = new ArrayList();
                    int j = 0;
                    for (int i = 0; i < checkBox.length; i++) {
                        if (checkBox[i].isSelected())
                            j++;
                    }
                    if (j != councilPrivilege.getNumberOfCouncilPrivileges())
                        showGameException();
                    else {
                        for (int i = 0; i < checkBox.length; i++) {
                            if (checkBox[i].isSelected())
                                choiceList.add(i);
                        }
                        Privilege[] privileges = councilPrivilege.getPrivileges();
                        for (Integer choice : choiceList) {
                            privilegeArrayList.add(privileges[choice]);
                            privileges[choice].setNotAvailablePrivilege();
                        }
                        setPrivileges(privilegeArrayList);
                    }
                    JOptionPane.getRootFrame().dispose();
                }
                System.out.println("CIAONE 2");
                if (getClient().getPlayerTurnChoices().containsKey(reason)) {
                    ArrayList<Privilege> arrayList = (ArrayList<Privilege>) getClient().getPlayerTurnChoices().get(reason);
                    arrayList.addAll(privilegeArrayList);
                } else
                    getClient().setPlayerTurnChoices(reason, privilegeArrayList);
                System.out.println("CIAONE 3");

            }
        });
        return getPrivileges();
    }

    private synchronized void setPrivileges(ArrayList<Privilege> privileges){
        this.privileges = privileges;
    }

    private synchronized ArrayList<Privilege> getPrivileges(){
        return this.privileges;
    }


    @Override
    public int chooseDoubleCost(PointsAndResources pointsAndResources, int militaryPointsGiven, int militaryPointsNeeded) {
        int key = 1;
        if (militaryPointsNeeded < getClient().getPlayer().getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY))
            getClient().setPlayerTurnChoices("double-cost", key);
        else {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (IllegalAccessException | InstantiationException |
                            UnsupportedLookAndFeelException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    String[] choices = new String[2];
                    choices[0] = "With " + pointsAndResources.toString();
                    choices[1] = "With " + militaryPointsGiven + " military points but you need " + militaryPointsNeeded  + " military points";
                    JComboBox choiceBox = new JComboBox(choices);
                    Label title = new Label("You can choose the card payment: ");
                    JPanel panel = new JPanel();
                    panel.add(title);
                    panel.add(choiceBox);
                    int result = JOptionPane.showConfirmDialog(null, panel, "Choose how to pay", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        if (choiceBox.getSelectedItem() != null) {
                            String choice = (String) choiceBox.getSelectedItem();
                            if (choice.equals(choices[0])) {
                                setKeyValue(1);
                                getClient().setPlayerTurnChoices("double-cost", getKeyValue());
                            } else if (choice.equals(choices[1])) {
                                setKeyValue(2);
                                getClient().setPlayerTurnChoices("double-cost", getKeyValue());
                            }
                            JOptionPane.getRootFrame().dispose();
                        }
                    }
                }
            });
        }
        return getKeyValue();
    }

    private synchronized void setKeyValue(int key) {
        this.key = key;
    }

    private synchronized int getKeyValue() {
        return key;
    }

    @Override
    public int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableToEarn) {
        int key = 1;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (IllegalAccessException | InstantiationException |
                        UnsupportedLookAndFeelException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                String[] choices = new String[valuableToPay.length];
                for (int i = 0; i < choices.length; i++) {
                    choices[i] = valuableToPay[i].toString() + " with " + valuableToEarn;
                }
                JComboBox choiceBox = new JComboBox(choices);
                Label title = new Label("You can exchange: ");
                JPanel panel = new JPanel();
                panel.add(title);
                panel.add(choiceBox);
                int result = JOptionPane.showConfirmDialog(null, panel, "Choose exchange", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    if (choiceBox.getSelectedItem() != null) {
                        int i = choiceBox.getSelectedIndex();
                        setKeyValue(i);
                        getClient().setPlayerTurnChoices(card + ":double", getKeyValue());
                    }
                    JOptionPane.getRootFrame().dispose();
                }
            }
        });
        return getKeyValue();
    }

    @Override
    public int choosePickUpDiscounts(String reason, List<PointsAndResources> discounts) {
        int key = 1;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (IllegalAccessException | InstantiationException |
                        UnsupportedLookAndFeelException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                String[] choices = new String[discounts.size()];
                for (int i = 0; i < choices.length; i++) {
                    choices[i] = "Discount " + discounts.get(i).toString() + " in " + discounts.get(i);
                }
                JComboBox choiceBox = new JComboBox(choices);
                Label title = new Label("You can choose a discont:");
                JPanel panel = new JPanel();
                panel.add(title);
                panel.add(choiceBox);
                int result = JOptionPane.showConfirmDialog(null, panel, "Choose discount", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    if (choiceBox.getSelectedItem() != null) {
                        int i = choiceBox.getSelectedIndex();
                        setKeyValue(i);
                        getClient().setPlayerTurnChoices(reason, getKeyValue());
                    }
                    JOptionPane.getRootFrame().dispose();
                }
            }
        });
        return getKeyValue();
    }

    @Override
    public DevelopmentCard chooseNewCard(String reason, DevelopmentCardColor[] developmentCardColors, int diceValue, PointsAndResources discount) {
        int i = 1;
        ArrayList<DevelopmentCard> devCards = new ArrayList<DevelopmentCard>();
        MainBoard mainBoard = getClient().getGameModel().getMainBoard();
        for (DevelopmentCardColor developmentCardColor : developmentCardColors)
            for (Tower tower : mainBoard.getTowers())
                if (tower.getColor().equals(developmentCardColor))
                    for (TowerCell towerCell : tower.getTowerCells())
                        if (towerCell.getPlayerNicknameInTheCell() == null && towerCell.getMinFamilyMemberValue() <= diceValue && isSelectable(towerCell, discount)) {
                            devCards.add(towerCell.getDevelopmentCard());
                            i++;
                        }
        int key = 0;
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (IllegalAccessException | InstantiationException |
                    UnsupportedLookAndFeelException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            String[] choices = devCards.toArray(new String[devCards.size()]);
            JComboBox comboBox = new JComboBox(choices);
            Label title = new Label("You can pick up a new card among those: ");
            JPanel panel = new JPanel();
            panel.add(title);
            panel.add(comboBox);
            int result = JOptionPane.showConfirmDialog(null, panel, "Choose card", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                if (comboBox.getSelectedItem() != null) {
                    int index = comboBox.getSelectedIndex();
                    setKeyValue(index);
                    setDevelopmentCard(devCards.get(getKeyValue()));
                    for (Tower tower : mainBoard.getTowers())
                        for (TowerCell towerCell : tower.getTowerCells())
                            if (towerCell.getDevelopmentCard().getName().equals(getDevelopmentCard().getName())) {
                                getDevelopmentCard().payCost(getClient().getPlayer(), getCallback());
                                towerCell.setPlayerNicknameInTheCell(getClient().getUsername());
                                if (towerCell.getTowerCellImmediateEffect() != null)
                                    towerCell.getTowerCellImmediateEffect().runEffect(getClient().getPlayer(), getCallback());
                            }
                    getClient().setPlayerTurnChoices(reason, getDevelopmentCard());
                }
                JOptionPane.getRootFrame().dispose();
            }
        });

        return getDevelopmentCard();
    }

    @Override
    public LeaderCard copyAnotherLeaderCard(String reason) {
        List<LeaderCard> leaderCards = new ArrayList<>();
        for (Map.Entry pair : getClient().getGameModel().getPlayersMap().entrySet())
            if (!pair.getKey().equals(getClient().getUsername()))
                leaderCards.addAll(((Player) pair.getValue()).getPersonalBoard().getLeaderCards());

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (IllegalAccessException | InstantiationException |
                        UnsupportedLookAndFeelException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                String[] names = new String[leaderCards.size()];
                int i = 0;
                for (LeaderCard leaderCard : leaderCards) {
                    names[i] = leaderCard.getLeaderCardName().toString();
                    i++;
                }
                JComboBox comboBox = new JComboBox(names);
                JLabel label = new JLabel("Choose a Leader Card to copy");
                JPanel panel = new JPanel();
                panel.add(label);
                panel.add(comboBox);
                int result = JOptionPane.showConfirmDialog(null, panel, "Copy Effect", JOptionPane.OK_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    if (comboBox.getSelectedItem() != null) {
                        int index = comboBox.getSelectedIndex();
                        setKeyValue(index);
                        getClient().getPlayerTurnChoices().put(reason, leaderCards.get(getKeyValue()));
                    }
                    JOptionPane.getRootFrame().dispose();
                }

            }
        });
        return leaderCards.get(getKeyValue());
    }

    @Override
    public FamilyMemberColor choiceLeaderDice(String reason) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                String[] values = new String[FamilyMemberColor.values().length];
                for (FamilyMemberColor familyMemberColor : FamilyMemberColor.values()) {
                    values[i] = FamilyMemberColor.values()[i].toString();
                    i++;
                }
                JComboBox comboBox = new JComboBox(values);
                JLabel label = new JLabel("Choose Leader Dice");
                JPanel panel = new JPanel();
                panel.add(label);
                panel.add(comboBox);
                int result = JOptionPane.showConfirmDialog(null, panel, "Copy Family Mamber Value", JOptionPane.OK_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    if (comboBox.getSelectedItem() != null) {
                        int index = comboBox.getSelectedIndex();
                        setKeyValue(index);
                        getClient().getPlayerTurnChoices().put(reason, FamilyMemberColor.values()[getKeyValue()]);
                    }
                    JOptionPane.getRootFrame().dispose();
                }
            }
        });
        return FamilyMemberColor.values()[getKeyValue()];
    }

    private void setDevelopmentCard(DevelopmentCard card) {
        this.card = card;
    }

    private DevelopmentCard getDevelopmentCard() {
        return this.card;
    }

    private InformationCallback getCallback() {
        return this;
    }

    boolean isFinished() {
        return finished;
    }

    private void setFinished(boolean finished) {
        this.finished = finished;
        return;
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