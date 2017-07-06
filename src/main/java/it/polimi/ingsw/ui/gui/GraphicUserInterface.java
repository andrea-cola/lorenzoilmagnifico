package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * This class manage the graphic user interface of the game
 */
public class GraphicUserInterface extends AbstractUI {

    private final static String START = "startinStage";
    private final static String CONNECTION = "connnectionStage";
    private final static String LOGIN = "loginStage";
    private final static String JOIN_ROOM = "joinRoomStage";
    private final static String CREATE_ROOM = "createRoomStage";
    private final static String PERSONAL_TILE = "personalTileStage";
    private final static String LEADER_CARD = "leaderCardStage";

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

    /**
     * Constructor
     *
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

    private void buildGUI() {
        /*
        chooseConnectionStage = new ChooseConnectionStage(getController()::setNetworkSettings);
        System.out.println("building...");
        mainPanel.add(chooseConnectionStage, CONNECTION);

        mainPanel.add(loginStage, LOGIN);
        mainPanel.add(joinRoomStage, CREATE_ROOM);
        mainPanel.add(createRoomStage, JOIN_ROOM );
        mainPanel.add(choosePersonalBoardTileStage, PERSONAL_TILE);
        mainPanel.add(chooseLeaderCardStage, LEADER_CARD );
        */
    }

    private void showStartingStage() {
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
        mainPanel.add(choosePersonalBoardTileStage, PERSONAL_TILE);
        cardLayout.show(mainPanel, PERSONAL_TILE);
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

    }

    @Override
    public void turnScreen(String username, long seconds) {

    }

    @Override
    public void notifyUpdate(String message) {

    }

    @Override
    public void supportForTheChurch(boolean flag) {

    }

    @Override
    public void notifyEndGame(ServerPlayer[] ranking) {

    }

}