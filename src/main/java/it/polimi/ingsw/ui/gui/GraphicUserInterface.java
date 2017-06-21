package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import it.polimi.ingsw.utility.Debugger;
import javafx.application.Application;
import javafx.application.Platform;

import javafx.stage.Stage;
import java.util.List;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * This class manage the graphic user interface of the game
 */
public class GraphicUserInterface extends AbstractUI{

    private final Lock lock = new ReentrantLock();

    /**
     * Constructor
     * @param controller
     * @throws InterruptedException
     */
    public GraphicUserInterface(UiController controller) throws InterruptedException {
        super(controller);
        welcomeBoard();
    }

    public void welcomeBoard(){
        StartingBoardScreen startingBoardScreen = new StartingBoardScreen();
        Thread thread = new Thread(() -> Application.launch(startingBoardScreen.getClass()));
        thread.start();
        do {
            lock.lock();
        }while(startingBoardScreen.getFinished()!=true);
        lock.unlock();
        return;
    }

    /**
     * @throws InterruptedException
     */
    @Override
    public void chooseConnectionType() {
        lock.lock();
        ChooseConnectionBoardScreen chooseConnectionBoardScreen = new ChooseConnectionBoardScreen(getController()::setNetworkSettings);
        Runnable thread = () -> {
            try {
                chooseConnectionBoardScreen.start(new Stage());
            } catch (Exception e) {
                Debugger.printDebugMessage(GraphicUserInterface.this.getClass().getSimpleName(), e.getMessage());
            }
        };
        Platform.runLater(thread);
        if (chooseConnectionBoardScreen.getFinished() == true) {
            lock.unlock();
            loginScreen();
        }
    }

    @Override
    public void loginScreen(){
        LoginBoardScreen loginBoardScreen = new LoginBoardScreen(getController()::loginPlayer);
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                try {
                    loginBoardScreen.start(new Stage());
                } catch (Exception e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), e.getMessage());
                }
            }
        });
    }

    @Override
    public void joinRoomScreen() {
        JoinRoomBoardScreen joinRoomBoardScreen = new JoinRoomBoardScreen(getController()::joinRoom);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    joinRoomBoardScreen.start(new Stage());
                } catch (Exception e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), e.getMessage());
                }
            }
        });
    }

    @Override
    public void createRoomScreen() {
        CreateRoomBoardScreen createRoomBoardScreen = new CreateRoomBoardScreen(getController()::createRoom);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    createRoomBoardScreen.start(new Stage());
                } catch (Exception e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), e.getMessage());
                }
            }
        });
    }

    @Override
    public void choosePersonalTile(List<PersonalBoardTile> personalBoardTileList) {

    }



    @Override
    public void chooseLeaderCards(List<LeaderCard> leaderCards) {

    }
}
