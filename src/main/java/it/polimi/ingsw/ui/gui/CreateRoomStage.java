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
    private MainBoardStage.CallbackInterface mainboardCallback;

    private  CallbackInterface callback;

    CreateRoomStage(CallbackInterface callback, MainBoardStage.CallbackInterface mainboardCallback){
        this.callback = callback;
        this.mainboardCallback = mainboardCallback;
        BorderPane root = new BorderPane();
        Label head = new Label("There are no room available, create a new room");
        head.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(VBOX_SPACING);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Label players = new Label("Number of Players: ");
        players.setAlignment(Pos.CENTER);
        TextField numberText = new TextField();
        numberText.setAlignment(Pos.CENTER);

        Button create = new Button("CREATE ROOM");
        create.setOnAction(event -> {
            if(!numberText.getText().equals("")){
                nPlayers = Integer.parseInt(numberText.getText());
                if(nPlayers<5 && nPlayers>1){
                    createNewRoom();
                    Platform.setImplicitExit(false);
                    setVisible(false);
                }else {
                    this.mainboardCallback.showGameException("Your data are not valid");
                }
            }else{
                this.mainboardCallback.showGameException("Your data are not valid");
            }
        });

        Button clear = new Button("CLEAR");
        clear.setOnAction(e -> {
            numberText.clear();
        });

        Button exit = new Button("EXIT");
        exit.setOnAction(event -> this.hide());

        grid.add(players, 0,0);
        grid.add(numberText, 1, 0);

        HBox buttons = new HBox(HBOX_SPACING);
        buttons.getChildren().addAll(create, exit, clear);
        buttons.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(head, grid, buttons);
        vBox.setAlignment(Pos.CENTER);
        root.setCenter(vBox);
        root.setMargin(vBox, new Insets(INSETS));
        root.autosize();
        Scene scene = new Scene(root);
        this.setScene(scene);
    }

    private void createNewRoom(){
        this.callback.createRoom(nPlayers);
    }

    @FunctionalInterface
    interface CallbackInterface{
       public void createRoom(int maxPlayer);
    }

}

