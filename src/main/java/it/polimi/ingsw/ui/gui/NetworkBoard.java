package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.ui.cli.NetworkType;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.CountDownLatch;

/**
 * This is the board where the network options are taken and then passed to the main game class
 */
public class NetworkBoard extends Application {

    private NetworkBoardCallback callback;

    private static final CountDownLatch latch = new CountDownLatch(1);

    private static NetworkBoard networkBoard = null;

    private String type;
    private int port;
    private String address;

    /**
     * It is called by the graphic user interface to wait and then continue its functions
     * after the end of start function
     *
     * @return the network board
     */
    public static NetworkBoard waitFor() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return networkBoard;
    }

    /**
     * Contructor for the NetworkBoard
     *
     * @param callback
     */
    public NetworkBoard(NetworkBoardCallback callback) {
        this.callback = callback;
    }

    /**
     * The start function inherited by Application represents the structure of the Network board
     *
     * @param primaryStage is the main container of the application
     * @throws Exception if the main method is not allocated
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("NetworkBoard");
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        Label choose = new Label("NETWORK PREFERENCES");
        choose.setAlignment(Pos.CENTER);
        choose.setStyle("Ubuntu");
        choose.setMaxSize(20, 10);

        HBox hBox1 = new HBox(10);
        hBox1.setAlignment(Pos.CENTER);
        HBox hBox2 = new HBox(10);
        hBox2.setAlignment(Pos.CENTER);
        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setAlignment(Pos.CENTER);

        Label labelNetwork = new Label("Network Type");
        labelNetwork.setAlignment(Pos.CENTER);
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().add("RMI");
        choiceBox.getItems().add("SOCKET");
        type = (String) choiceBox.getValue();

        Label labelAddr = new Label("Address");
        labelAddr.setAlignment(Pos.CENTER);
        labelAddr.setStyle("Ubuntu");
        TextField text = new TextField();
        text.setAlignment(Pos.CENTER);
        address = text.getText();

        ImageView imageView = new ImageView(new Image("cover2.png"));
        Button connect = new Button("CONNECT");
        connect.setOnAction(event -> doConnect());

        if (address.isEmpty()) {
            connect.setDisable(true);
        }

        Button exit = new Button("EXIT");
        exit.setOnAction(event -> primaryStage.close());

        grid.add(labelNetwork, 0, 0);
        grid.add(choiceBox, 1, 0);
        grid.add(labelAddr, 0, 1);
        grid.add(text, 1, 1);


        hBox1.getChildren().addAll(imageView, grid);
        hBox2.getChildren().addAll(connect, exit);
        vBox.getChildren().addAll(choose, hBox1, hBox2);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void doConnect() {
        NetworkType networkType;
        switch (type) {
            case "RMI":
                networkType = NetworkType.RMI;
                port = 3032;
                break;
            case "SOCKET":
                networkType = NetworkType.SOCKET;
                port = 3031;
                break;
            default:
                networkType = NetworkType.SOCKET;
                port = 3031;
                break;
        }
        this.callback.setNetworkSettings(networkType, address, port);
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
    * @param address     is the network address
    * @param port        is the network port
    */
    void setNetworkSettings(NetworkType networkType, String address, int port);
 }