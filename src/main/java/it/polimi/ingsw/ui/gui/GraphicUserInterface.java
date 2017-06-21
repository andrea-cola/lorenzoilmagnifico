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
        StartingStage startingStage = new StartingStage();
        Thread thread = new Thread(() -> Application.launch(startingStage.getClass()));
        thread.start();
        do {
            lock.lock();
        }while(startingStage.getFinished()!=true);
        lock.unlock();
        return;
    }

    /**
     * @throws InterruptedException
     */
    @Override
    public void chooseConnectionType() {
        lock.lock();
        ChooseConnectionStage chooseConnectionStage = new ChooseConnectionStage(getController()::setNetworkSettings);
        Runnable thread = () -> {
            try {
                chooseConnectionStage.start(new Stage());
            } catch (Exception e) {
                Debugger.printDebugMessage(GraphicUserInterface.this.getClass().getSimpleName(), e.getMessage());
            }
        };
        Platform.runLater(thread);
        if (chooseConnectionStage.getFinished() == true) {
            lock.unlock();
            loginScreen();
        }
    }

    @Override
    public void loginScreen(){
        LoginStage loginStage = new LoginStage(getController()::loginPlayer);
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                try {
                    loginStage.start(new Stage());
                } catch (Exception e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), e.getMessage());
                }
            }
        });
    }

    @Override
    public void joinRoomScreen() {
        JoinRoomStage joinRoomStage = new JoinRoomStage(getController()::joinRoom);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    joinRoomStage.start(new Stage());
                } catch (Exception e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), e.getMessage());
                }
            }
        });
    }

    @Override
    public void createRoomScreen() {
        CreateRoomStage createRoomStage = new CreateRoomStage(getController()::createRoom);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    createRoomStage.start(new Stage());
                } catch (Exception e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), e.getMessage());
                }
            }
        });
    }

    @Override
    public void choosePersonalTile(List<PersonalBoardTile> personalBoardTileList) {
        ChoosePersonalBoardTileStage choosePersonalBoardTileStage = new ChoosePersonalBoardTileStage(getController()::sendPersonalBoardTileChoice, personalBoardTileList);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    choosePersonalBoardTileStage.start( new Stage());
                } catch (Exception e ){
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), e.getMessage());
                }
            }
        });
    }



    @Override
    public void chooseLeaderCards(List<LeaderCard> leaderCards) {

    }
}
