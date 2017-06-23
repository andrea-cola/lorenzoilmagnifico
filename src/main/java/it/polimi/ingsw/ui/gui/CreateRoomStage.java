package it.polimi.ingsw.ui.gui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
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


public class CreateRoomStage extends JFXPanel{

    private final static int HBOX_SPACING = 30;
    private final static int VBOX_SPACING = 30;
    private final static int INSETS = 20;

    private int nPlayers;

    private  CallbackInterface callback;

    CreateRoomStage(CallbackInterface callback){
        this.callback = callback;
        BorderPane root = new BorderPane();
        Label head = new Label("There are no room available, create a new room");
        head.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(VBOX_SPACING);
        GridPane grid = new GridPane();
        Label players = new Label("Number of Players: ");
        TextField numberText = new TextField();
        Label message = new Label("Your data are invalid");
        message.setAlignment(Pos.CENTER);
        message.setVisible(false);

        Button create = new Button("CREATE ROOM");
        create.setOnAction(event -> {
            if(!numberText.getText().equals("")){
                nPlayers = Integer.parseInt(numberText.getText());
                if(nPlayers<5 && nPlayers>1){
                    createNewRoom();
                    Platform.setImplicitExit(false);
                    setVisible(false);
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
        exit.setOnAction(event -> this.hide());

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
        this.setScene(scene);
    }

    private void createNewRoom(){
        System.out.println("N PLAYERS = " + nPlayers);
        this.callback.createRoom(nPlayers);
    }

    @FunctionalInterface
    interface CallbackInterface{
       public void createRoom(int maxPlayer);
    }

}

