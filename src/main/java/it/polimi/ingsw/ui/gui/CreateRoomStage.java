package it.polimi.ingsw.ui.gui;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class CreateRoomStage extends Application{

    private final static int HBOX_SPACING = 30;
    private final static int VBOX_SPACING = 30;
    private final static int INSETS = 20;

    private int nPlayers;

    private  CallbackInterface callback;

    CreateRoomStage(CallbackInterface callback){
        this.callback = callback;
    }


    /**
     * The main entry point for all JavaFX applications.
     * @param primaryStage the primary stage for this application
     */
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Create Room");
        BorderPane root = new BorderPane();
        Label head = new Label("There are no room avaible, create a new room");
        VBox vBox = new VBox(VBOX_SPACING);
        GridPane grid = new GridPane();
        Label players = new Label("Number of Players :");
        TextField numberText = new TextField();
        Label message = new Label("Your data are invalid");
        message.setVisible(false);

        Button create = new Button("CREATE ROOM");
        create.setOnAction(event -> {
            if(!numberText.getText().equals("")){
                nPlayers = Integer.parseInt(numberText.getText());
                if(nPlayers<5 && nPlayers>1){
                    createNewRoom();
                    primaryStage.close();
                }else {
                    message.setVisible(true);
                }
            }else{
                message.setVisible(true);
            }
        });

        Button clear = new Button("CLEAR");
        clear.setOnAction(e -> {
            numberText.clear();
            message.setVisible(false);
        });

        Button exit = new Button("EXIT");
        exit.setOnAction(event -> primaryStage.close());

        grid.add(players, 0,0);
        grid.add(numberText, 1, 0);

        HBox buttons = new HBox(HBOX_SPACING);
        buttons.getChildren().addAll(create, exit, clear);
        buttons.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(head, grid, buttons, message);
        root.setCenter(vBox);
        root.setMargin(vBox, new Insets(INSETS));
        root.autosize();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createNewRoom(){
        System.out.println(nPlayers);
        this.callback.createRoom(nPlayers);
    }


    @FunctionalInterface
    interface CallbackInterface{
       public void createRoom(int maxPlayer);
    }

}

