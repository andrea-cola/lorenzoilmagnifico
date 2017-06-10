package it.polimi.ingsw.ui.gui.NetworkBoard;

import it.polimi.ingsw.ui.cli.NetworkType;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class NetworkBoard extends Application{

    public NetworkBoardCallback callback;


    public static final CountDownLatch latch= new CountDownLatch(1);

    public static NetworkBoard networkBoard= null;

    /**
     * It is called by the graphic user interface to wait and then continue its functions
     * after the end of start function
     * @return the network board
     */
    public static NetworkBoard waitFor(){
        try{
            latch.await();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return networkBoard;
    }

    /**
     * Contractor for the network board
     * @param callback for setting the network option
     */
    NetworkBoard(NetworkBoardCallback callback){
        this.callback=callback;
        this.launch(this.getClass());
    }

    /**
     * The start function inherited by Application represents the structure of the Network board
     * @param primaryStage is the main container of the application
     * @throws Exception if the main method is not allocated
     */
    @Override
    public void start(Stage primaryStage) throws Exception {


    }

    public void doConnect(){

    }

}

/**
 * This callback interface represents the main network context function
 */
@FunctionalInterface
interface NetworkBoardCallback {
    /**
     * It set the network settings previously decided by the user
     * @param networkType is the type of network
     * @param address is the network address
     * @param port is the network port
     */
    void setNetworkSettings(NetworkType networkType, String address, int port);
}
