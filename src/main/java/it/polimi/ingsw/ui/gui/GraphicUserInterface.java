package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import it.polimi.ingsw.utility.Debugger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.awt.Mutex;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

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

    /**
     *
     */
    public void welcomeBoard(){

        StartingBoardScreen startingBoardScreen = new StartingBoardScreen();
        Thread thread = new Thread(() -> Application.launch(startingBoardScreen.getClass()));
        thread.start();
        do {
            lock.lock();
        }while(startingBoardScreen.getFinished()!=true);
        lock.unlock();
        thread.stop();
        return;
    }

    /**
     * @throws InterruptedException
     */
    @Override
    public void chooseConnectionType() {
        lock.lock();
        NetworkBoardScreen networkBoardScreen = new NetworkBoardScreen(getController()::setNetworkSettings);
        Platform.runLater(() -> {
            try {
                networkBoardScreen.start(new Stage());
            } catch (Exception e) {
                Debugger.printDebugMessage(this.getClass().getSimpleName(), e.getMessage());
            }
        });
        do{
            lock.unlock();
        }while(!networkBoardScreen.getFinished());
        loginScreen();
    }

    @Override
    public void loginScreen(){
        lock.lock();
        LoginBoardScreen loginBoardScreen = new LoginBoardScreen(getController()::loginPlayer);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    loginBoardScreen.start(new Stage());
                } catch (Exception e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), e.getMessage());
                }
            }
        });
        do{
            lock.unlock();
        }while(!loginBoardScreen.getFinished());
    }

    @Override
    public void joinRoomScreen() {
        lock.lock();
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
        do{

        }while(!joinRoomBoardScreen.getFinished());
    }

    @Override
    public void createRoomScreen() {

    }

    @Override
    public void choosePersonalTile(List<PersonalBoardTile> personalBoardTileList) {

    }



}
