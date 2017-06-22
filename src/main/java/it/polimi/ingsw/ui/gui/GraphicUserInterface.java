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

/**
 * This class manage the graphic user interface of the game
 */
public class GraphicUserInterface extends AbstractUI{

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
        NetworkBoardScreen networkBoardScreen = new NetworkBoardScreen(getClient():: setNetworkSettings);
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
        LoginBoardScreen loginBoardScreen = new LoginBoardScreen(getClient()::loginPlayer);
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

    @Override
    public void choosePersonalTile(List<PersonalBoardTile> personalBoardTileList) {

    }

    @Override
    public void chooseLeaderCards(List<LeaderCard> leaderCards) {

    }

    @Override
    public void notifyGameStarted() {

    }

    @Override
    public void turnScreen(String username, long seconds) {

    }

}
