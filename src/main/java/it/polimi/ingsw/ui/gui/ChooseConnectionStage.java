package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.ui.ConnectionType;
import it.polimi.ingsw.utility.Printer;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;

/**
 * This is the Graphic User Interface board for network settings
 */
public class ChooseConnectionStage extends JFXPanel{
    /**
     * Constants
     */
    private final static int VBOX_SPACING = 10;
    private final static int GRID_H_GAP = 10;
    private final static int GRID_V_GAP = 10;
    private final static int HBOX_SPACING = 10;
    private final static int IMAGE_WIDTH = 450;
    private final static int IMAGE_HEIGHT = 500;
    private final static int INSETS = 20;
    private final static String FONT = "Arial : arial";
    private static boolean finished = false;



    private CallbackInterface callback;

    /**
     * Connection data
     */
    private String type;
    private int port;
    private String address;
    private MainBoardStage.CallbackInterface mainboardCallback;

    /**
     * Constructor for the ChooseConnectionStage
     *
     * @param callback
     */
    ChooseConnectionStage(CallbackInterface callback, MainBoardStage.CallbackInterface mainboardCallback) {
        this.callback = callback;
        this.mainboardCallback = mainboardCallback;

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

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
        labelAddr.setStyle(FONT);

        Button connect = new Button("CONNECT");
        TextField text = new TextField();
        text.setAlignment(Pos.CENTER);

        connect.setOnAction(event -> {
            if (!text.getText().equals("") && choiceBox.getValue()!= null) {
                type = (String) choiceBox.getValue();
                address = text.getText();
                doConnect();
                setFinished(true);
                setVisible(false);
            }else{
                this.mainboardCallback.showGameException("Your data are not valid");
            }
        });

        ImageView imageView = new ImageView("images/ChooseConnectionStageCover.png");
        imageView.setFitHeight(IMAGE_HEIGHT);
        imageView.setFitWidth(IMAGE_WIDTH);
        imageView.autosize();

        Button clear = new Button("CLEAR");
        clear.setOnAction(e -> {
            text.clear();
            choiceBox.setValue(null);
        });

        Button exit = new Button("EXIT");
        exit.setOnAction(event -> this.hide());

        grid.add(labelNetwork, 0, 0);
        grid.add(choiceBox, 1, 0);
        grid.add(labelAddr, 0, 1);
        grid.add(text, 1, 1);
        grid.setAlignment(Pos.CENTER_LEFT);

        BorderPane pane = new BorderPane();
        pane.setCenter(grid);
        pane.setMargin(grid, new Insets(INSETS));
        pane.autosize();
        hBox1.getChildren().addAll(imageView, pane);
        hBox2.getChildren().addAll(connect, exit, clear);

        vBox.getChildren().addAll(hBox1, hBox2);
        BorderPane root = new BorderPane();
        root.setCenter(vBox);
        Scene scene = new Scene(root);
        this.setScene(scene);
    }


    private void doConnect() {
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
            this.mainboardCallback.showGameException("Error during connection");

        }
    }

    public static boolean getFinished(){
        return finished;
    }

    public static void setFinished(boolean flag){
        finished=flag;
    }

    /**
     * This callback interface represents the main network context function
     */
    @FunctionalInterface
    interface CallbackInterface {
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