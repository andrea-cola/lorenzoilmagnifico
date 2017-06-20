package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.ui.cli.ConnectionType;
import it.polimi.ingsw.utility.Debugger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;

import java.util.concurrent.CountDownLatch;

/**
 * This is the Graphic User Interface board for network settings
 */
public class NetworkBoardScreen extends Application {
    /**
     * Constants
     */
    private final static int VBOX_SPACING = 10;
    private final static int STAGE_WIDTH = 800;
    private final static int STAGE_HEIGHT = 800;
    private final static int GRID_H_GAP = 10;
    private final static int GRID_V_GAP = 10;
    private final static int HBOX_SPACING = 10;

    private NetworkBoardCallback callback;

    /**
     * Connection data
     */
    private String type;
    private int port;
    private String address;

    /**
     * Constructor for the NetworkBoardScreen
     * @param callback
     */
    public NetworkBoardScreen(NetworkBoardCallback callback) {
        this.callback = callback;
    }

    /**
     * The start function inherited by Application represents the structure of the Network board
     * @param primaryStage is the main container of the application
     * @throws Exception if the main method is not allocated
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("NetworkBoardScreen");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Label title = new Label("NETWORK PREFERENCES");
        title.setAlignment(Pos.CENTER);
        title.setStyle("Ubuntu");

        HBox hBox1 = new HBox(HBOX_SPACING);
        hBox1.setAlignment(Pos.CENTER);
        HBox hBox2 = new HBox(HBOX_SPACING);
        hBox2.setAlignment(Pos.CENTER);
        GridPane grid = new GridPane();
        grid.setVgap(GRID_V_GAP);
        grid.setHgap(GRID_H_GAP);

        Label labelNetwork = new Label("Network Type");
        labelNetwork.setAlignment(Pos.CENTER);
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().add("RMI");
        choiceBox.getItems().add("SOCKET");


        Label labelAddr = new Label("Address");
        labelAddr.setAlignment(Pos.CENTER);
        labelAddr.setStyle("Ubuntu");

        TextField text = new TextField();
        text.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(new Image("images/NetworkBoardCover.png"));

        Button connect = new Button("CONNECT");
        connect.setOnAction(event -> {
            if(text.getText()!= null && choiceBox.getValue()!=null){
                type = (String) choiceBox.getValue();
                address = text.getText();
                System.out.print("ADDRESS: " + address + " & TYPE: " + type + "\n");
                doConnect();
                primaryStage.close();
            }
        });

        Button exit = new Button("EXIT");
        exit.setOnAction(event -> primaryStage.close());

        grid.add(labelNetwork, 0, 0);
        grid.add(choiceBox, 1, 0);
        grid.add(labelAddr, 0, 1);
        grid.add(text, 1, 1);
        grid.setAlignment(Pos.CENTER_LEFT);

        hBox1.getChildren().addAll(imageView, grid);
        hBox2.getChildren().addAll(connect, exit);

        vBox.getChildren().addAll(title, hBox1, hBox2);
        vBox.autosize();
        Group root = new Group();
        root.getChildren().addAll(vBox);
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setMinWidth(STAGE_WIDTH);
        primaryStage.setMinHeight(STAGE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void doConnect() {
        ConnectionType connectionType;
        switch (type) {
            case "RMI":
                connectionType = ConnectionType.RMI;
                port = 3032;
                break;
            case "SOCKET":
                connectionType = ConnectionType.SOCKET;
                port = 3031;
                break;
            default:
                connectionType = ConnectionType.SOCKET;
                port = 3031;
                break;
        }
        try {
            this.callback.setNetworkSettings(connectionType, address, port);
        } catch (ConnectionException e) {
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error during connection.");
        }
    }

    /**
     * This callback interface represents the main network context function
     */
    @FunctionalInterface
    interface NetworkBoardCallback {
        /**
         * It set the network settings previously decided by the user
         *
         * @param connectionType is the type of network
         * @param address        is the network address
         * @param port           is the network port
         */
        void setNetworkSettings(ConnectionType connectionType, String address, int port) throws ConnectionException;
    }
}