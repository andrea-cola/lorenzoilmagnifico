package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.Status;
import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import javafx.application.Application;

import static javafx.application.Application.launch;

/**
 * This class manage the graphic user interface of the game
 */
public class GraphicUserInterface extends AbstractUI{

    public GraphicUserInterface(UiController controller){
        super(controller);
        new Thread(()->Application.launch(StartingBoard.class)).start();
        StartingBoard.waitFor();
    }

    @Override
    public void chooseConnectionType() {
        NetworkBoard networkBoard= new NetworkBoard((networkType, address, port)->getController().setNetworkSettings(networkType, address, port));
        new Thread(()-> Application.launch(NetworkBoard.class)).start();
        networkBoard.waitFor();
    }

    @Override
    public void loginScreen() {
        new Thread(()-> Application.launch(LoginBoard.class)).start();
    }

    @Override
    public void joinRoomScreen() {

    }

    @Override
    public void createRoomScreen() {

    }

}
