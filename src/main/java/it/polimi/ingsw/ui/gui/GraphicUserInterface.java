package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.ui.AbstractUI;
import it.polimi.ingsw.ui.UiController;
import javafx.application.Application;

/**
 * This class manage the graphic user interface of the game
 */
public class GraphicUserInterface extends AbstractUI{

    public GraphicUserInterface(UiController controller) throws InterruptedException {
        super(controller);
        Thread thread =new Thread(()->Application.launch(StartingBoardScreen.class));
        thread.start();
        thread.join();
    }

    @Override
    public void chooseConnectionType() throws InterruptedException {
        NetworkBoardScreen networkBoardScreen = new NetworkBoardScreen((networkType, address, port)->getController().setNetworkSettings(networkType, address, port));
        Thread thread = new Thread(()-> Application.launch(NetworkBoardScreen.class));
        thread.start();
        thread.join();
    }

    @Override
    public void loginScreen() throws InterruptedException {
        Thread thread = new Thread(()-> Application.launch(LoginBoardScreen.class));
        thread.start();
        thread.join();
    }

    @Override
    public void joinRoomScreen() {

    }

    @Override
    public void createRoomScreen() {

    }

}
