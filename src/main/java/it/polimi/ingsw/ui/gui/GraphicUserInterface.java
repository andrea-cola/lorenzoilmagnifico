package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import javafx.application.Platform;

import javax.swing.*;
import javax.swing.border.Border;
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
    private LeaderCardsStage leaderCardsStage;

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
    public void showPersonalBoardStage(Player player) {
        System.out.println("showing " + player.getUsername() + " personal board...");
        SwingUtilities.invokeLater(() -> {
            JFrame jframe = new JFrame();
            jframe.setSize(100, 100);
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
            jframe.setSize(100, 100);
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
            jframe.setSize(100, 100);
            jframe.add(new LeaderCardsStage(this, player), BorderLayout.CENTER);
            jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            jframe.pack();
            jframe.setVisible(true);
        });
    }

    @Override
    public void showGameException() {
        JOptionPane.showMessageDialog(null, "Your data are not valid");
    }

    @Override
    public void notifyEndTurnStage() {
        System.out.println("showing ending turn");
        JOptionPane.showMessageDialog(null, "Your turn is ended, it takes the next one.");
    }

    @Override
    public void activeLeaderCard(String leaderName) {
        Player player = getClient().getPlayer();
        try {
            int i = 0;
            List<LeaderCard> leaderCards = player.getPersonalBoard().getLeaderCards();
            for (LeaderCard leaderCard : leaderCards) {
                if (leaderCard.getLeaderCardName().toLowerCase().equals(leaderName.toLowerCase()))
                    getClient().getGameModel().activateLeaderCard(player, i, this);
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
    public ArrayList<Privilege> chooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege) {

        return null;
    }

    @Override
    public int chooseDoubleCost(PointsAndResources pointsAndResources, int militaryPointsGiven, int militaryPointsNeeded) {
        return 0;
    }

    @Override
    public int chooseExchangeEffect(String card, PointsAndResources[] valuableToPay, PointsAndResources[] valuableEarned) {
        return 0;
    }

    @Override
    public int choosePickUpDiscounts(String reason, List<PointsAndResources> discounts) {
        return 0;
    }

    @Override
    public DevelopmentCard chooseNewCard(String reason, DevelopmentCardColor[] developmentCardColors, int diceValue, PointsAndResources discount) {
        return null;
    }

    @Override
    public void notifyUpdate(String message) {

    }
}
