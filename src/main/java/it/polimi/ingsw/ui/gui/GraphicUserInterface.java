package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import it.polimi.ingsw.utility.Debugger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import sun.awt.Mutex;

import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * This class manage the graphic user interface of the game
 */
public class GraphicUserInterface extends AbstractUI{

    private Task<Void> task;

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
        Thread thread = new Thread(() -> Application.launch(StartingBoardScreen.class));
        thread.start();
        return;

    }

    /**
     *
     * @throws InterruptedException
     */
    @Override
    public void chooseConnectionType(){
        NetworkBoardScreen networkBoardScreen = new NetworkBoardScreen(getController():: setNetworkSettings);
        Platform.runLater(() -> {
            try {
                networkBoardScreen.start(new Stage());
            } catch (Exception e) {
                Debugger.printDebugMessage(this.getClass().getSimpleName(), e.getMessage());
            }
        });

    }

    @Override
    public void loginScreen(){
        LoginBoardScreen loginBoardScreen = new LoginBoardScreen(getController()::loginPlayer);
        Platform.runLater(()->{
            try {
                loginBoardScreen.start(new Stage());
            }catch (Exception e){
                Debugger.printDebugMessage(this.getClass().getSimpleName(), e.getMessage());
            }
        });
    }

    @Override
    public void joinRoomScreen() {

    }

    @Override
    public void createRoomScreen() {

    }

}
