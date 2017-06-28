package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import javafx.scene.control.ChoiceBox;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * This class manage the graphic user interface of the game
 */
public class GraphicUserInterface extends AbstractUI implements MainBoardStage.CallbackInterface, InformationCallback{

    private final static String START = "startinStage";
    private final static String CONNECTION = "connnectionStage";
    private final static String LOGIN = "loginStage";
    private final static String JOIN_ROOM = "joinRoomStage";
    private final static String CREATE_ROOM = "createRoomStage";
    private final static String PERS_TILE_CHOICE = "personalTileStage";
    private final static String LEADER_CARD = "leaderCardStage";
    private final static String MAIN_BOARD = "mainBoardStage";

    private final static int FRAME_HEIGHT = 700;
    private final static int FRAME_WIDTH = 700;

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

    /**
     * Constructor
     * @param controller
     * @throws InterruptedException
     */
    public GraphicUserInterface(UiController controller) throws InterruptedException {
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

    private void showStartingStage(){
        startingStage = new StartingStage();
        mainPanel.add(startingStage, START);
        mainFrame.setVisible(true);
        System.out.println("starting...");
        cardLayout.show(mainPanel, START);
        while(startingStage.getFinished() != true){
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
    public void loginScreen(){
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
    }

    @Override
    public void turnScreen(String username, long seconds) {
        System.out.println("staring " + username + " turn");
        mainBoardStage = new MainBoardStage( this, getClient().getPlayer(), getClient().getGameModel());
        mainPanel.add(mainBoardStage, MAIN_BOARD);
        cardLayout.show(mainPanel, MAIN_BOARD);
    }

    @Override
    public void notifyUpdate(String message) {

    }

    @Override
    public boolean supportForTheChurch() {
        boolean choice;
        int key = 0;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                int result = JOptionPane.showConfirmDialog(null, "Do you want to support the church?", "Church support",
                        JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE);
                switch (result){
                    case JOptionPane.YES_OPTION:
                        setChoice(true);
                        break;
                    case JOptionPane.NO_OPTION:
                        setChoice(false);
                        break;
                }
            }
        });
        return getChoice();
    }

    private void setChoice(boolean choice){
        this.choice = choice;
    }

    private boolean getChoice(){
        return this.choice;
    }

    @Override
    public void showPersonalBoardStage(Player player) {
        System.out.println("showing " + player.getUsername() + " personal board...");
        SwingUtilities.invokeLater(() -> {
            JFrame jframe = new JFrame();
            jframe.setSize(FRAME_WIDTH, FRAME_HEIGHT);
            jframe.add(new PersonalBoardStage(player), BorderLayout.CENTER);
            jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            jframe.pack();
            jframe.setVisible(true);
        });
    }

    @Override
    public void showPersonalTileBoardStage(Player player) {
        System.out.println("showing " + player.getUsername() + " personal tile board...");
        SwingUtilities.invokeLater(()-> {
            JFrame jframe = new JFrame();
            jframe.setSize(FRAME_WIDTH, FRAME_HEIGHT);
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
            jframe.setSize(FRAME_WIDTH, FRAME_HEIGHT);
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
                JOptionPane.showMessageDialog(null, "Your data are not valid","Game Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void showChooseServantNumber() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jframe = new JFrame();
                jframe.setSize(FRAME_WIDTH, FRAME_HEIGHT);
                jframe.add(new ChooseServantNumberStage(), BorderLayout.CENTER);
                jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jframe.pack();
                jframe.setVisible(true);
            }
        });
    }

    @Override
    public void notifyEndTurnStage() {
        System.out.println("showing ending turn");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "Your turn is ended, it takes the next one.", "Notification", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }


    @Override
    public void activeLeaderCard(String leaderName) {
        Player player = getClient().getPlayer();
        int servants = 0 ;
        try {
            int i = 0;
            List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
            for (LeaderCard leaderCard : leaderCards) {
                if (leaderCard.getLeaderCardName().toLowerCase().equals(leaderName.toLowerCase()))
                    getClient().getGameModel().activateLeaderCard(player, i, servants, this);
                i++;
            }
        }catch(GameException e){
            showGameException();
        }
    }

    @Override
    public void discardLeader(String leaderName) {
        Player player = getClient().getPlayer();
        int i = 0;
        List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
        for(LeaderCard leaderCard : leaderCards){
            if(leaderCard.getLeaderCardName().toLowerCase().equals(leaderName.toLowerCase()))
                getClient().getGameModel().discardLeaderCard(player, i, this);
            i++;
        }
    }

    @Override
    public void showChooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jframe = new JFrame();
                try {
                    jframe.add(new ChooseCouncilPrivilege(reason, councilPrivilege), BorderLayout.CENTER);
                } catch (GameException e) {
                    showGameException();
                }
                jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jframe.pack();
                jframe.setVisible(true);
            }
        });
    }

    @Override
    public void setCouncilPrivileges(String reason, ArrayList<Privilege> privileges) {
        if(getClient().getPlayerTurnChoices().containsKey(reason)) {
            ArrayList<Privilege> arrayList = (ArrayList<Privilege>)getClient().getPlayerTurnChoices().get(reason);
            arrayList.addAll(privileges);
        } else
            getClient().setPlayerTurnChoices(reason, privileges);
        this.privileges = privileges;
    }

    @Override
    public ArrayList<Privilege> getCouncilPrivileges(){
        return this.privileges;
    }

    @Override
    public ArrayList<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege) {
        showChooseCouncilPrivilege(reason, councilPrivilege);
        return getCouncilPrivileges();
    }

    @Override
    public int chooseDoubleCost(PointsAndResources pointsAndResources, int militaryPointsGiven, int militaryPointsNeeded) {
        int key = 1;
        if(militaryPointsNeeded < getClient().getPlayer().getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY))
            getClient().setPlayerTurnChoices("double-cost", key);
        else {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try{
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    }catch (IllegalAccessException | InstantiationException |
                            UnsupportedLookAndFeelException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    String[] choices= new String[2];
                    choices[0] = "With " + pointsAndResources.toString();
                    choices[1] = "With " + militaryPointsGiven + "but you need " + militaryPointsNeeded;
                    JComboBox choiceBox = new JComboBox(choices);
                    Label title = new Label("You can choose the card payment: ");
                    JPanel panel = new JPanel();
                    panel.add(title);
                    panel.add(choiceBox);
                    int result = JOptionPane.showConfirmDialog(null, panel, "Choose how to pay", JOptionPane.OK_CANCEL_OPTION);
                    if(result== JOptionPane.OK_OPTION){
                        if(choiceBox.getSelectedItem()!=null){
                            String choice = (String) choiceBox.getSelectedItem();
                            if(choice.equals(choices[0])) {
                                setKeyValue(1);
                                getClient().setPlayerTurnChoices("double-cost", getKeyValue());
                            }else if(choice.equals(choices[1])){
                                setKeyValue(2);
                                getClient().setPlayerTurnChoices("double-cost", getKeyValue());
                            }
                        }
                    }
                }
            });
        }
        return getKeyValue();
    }

    private synchronized void setKeyValue(int key){
        this.key = key;
    }

    private synchronized int getKeyValue(){
        return key;
    }


    @Override
    public int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableToEarn) {
        int key = 1;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }catch (IllegalAccessException | InstantiationException |
                        UnsupportedLookAndFeelException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                String[] choices = new String[valuableToPay.length];
                for (int i = 0; i < choices.length ; i++) {
                    choices[i] = valuableToPay[i].toString() + " with " + valuableToEarn;
                }
                JComboBox choiceBox = new JComboBox(choices);
                Label title = new Label("You can exchange: ");
                JPanel panel = new JPanel();
                panel.add(title);
                panel.add(choiceBox);
                int result = JOptionPane.showConfirmDialog(null, panel, "Choose exchange", JOptionPane.OK_CANCEL_OPTION);
                if(result== JOptionPane.OK_OPTION){
                    if(choiceBox.getSelectedItem()!=null){
                        int i = choiceBox.getSelectedIndex();
                        setKeyValue(i);
                        getClient().setPlayerTurnChoices(card + ":double", getKeyValue());
                    }
                }
            }

        });
        return getKeyValue();
    }

    @Override
    public int choosePickUpDiscounts(String reason, List<PointsAndResources> discounts) {
        int key =1;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }catch (IllegalAccessException | InstantiationException |
                        UnsupportedLookAndFeelException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                String[] choices = new String[discounts.size()];
                for(int i=0; i < choices.length; i++){
                    choices[i] = "Discount " + discounts.get(i).toString() + " in " + discounts.get(i);
                }
                JComboBox choiceBox = new JComboBox(choices);
                Label title = new Label("You can choose a discont:");
                JPanel panel = new JPanel();
                panel.add(title);
                panel.add(choiceBox);
                int result = JOptionPane.showConfirmDialog(null, panel, "Choose discount", JOptionPane.OK_CANCEL_OPTION);
                if(result == JOptionPane.OK_OPTION){
                    if (choiceBox.getSelectedItem() != null){
                        int i = choiceBox.getSelectedIndex();
                        setKeyValue(i);
                        getClient().setPlayerTurnChoices(reason, getKeyValue());
                    }
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
            try{
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }catch (IllegalAccessException | InstantiationException |
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
            if(result == JOptionPane.OK_OPTION){
                if(comboBox.getSelectedItem()!= null){
                    int i1 = comboBox.getSelectedIndex();
                    setKeyValue(i1);
                    setDevelopmentCard(devCards.get(getKeyValue()));
                    for (Tower tower : mainBoard.getTowers())
                        for (TowerCell towerCell : tower.getTowerCells())
                            if (towerCell.getDevelopmentCard().getName().equals(getDevelopmentCard().getName())) {
                                getDevelopmentCard().payCost(getClient().getPlayer(), getCallback());
                                towerCell.setPlayerNicknameInTheCell(getClient().getUsername());
                                if(towerCell.getTowerCellImmediateEffect() != null)
                                    towerCell.getTowerCellImmediateEffect().runEffect(getClient().getPlayer(), getCallback());
                            }
                    getClient().setPlayerTurnChoices(reason, getDevelopmentCard());
                }
            }
        });

        return getDevelopmentCard();
    }

    private void setDevelopmentCard(DevelopmentCard card) {
        this.card = card;
    }

    private DevelopmentCard getDevelopmentCard(){
        return this.card;
    }

    private InformationCallback getCallback() {
        return this;
    }



    private boolean isSelectable(TowerCell cell, PointsAndResources discount){
        try{
            cell.developmentCardCanBeBuyed(getClient().getPlayer(), discount);
            return true;
        } catch (GameException e){
            return false;
        }
    }

}
