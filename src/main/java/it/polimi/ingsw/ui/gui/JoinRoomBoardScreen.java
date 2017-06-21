package it.polimi.ingsw.ui.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 */
public class JoinRoomBoardScreen extends Application{

    private JoinRoomBoardCallback callback;

    private final static int HBOX_SPACING = 20;
    private final static int INSETS = 20;

    JoinRoomBoardScreen(JoinRoomBoardCallback callback){
        this.callback = callback;
    }

    /**
     * The main entry point for all JavaFX applications.
     * @param primaryStage the primary stage for this application, onto which
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JoinRoom");
        BorderPane root = new BorderPane();
        HBox hBox = new HBox(HBOX_SPACING);

        Button join = new Button("JOIN ROOM");
        join.setOnAction(event -> {
            joinRoom();
            primaryStage.close();
        });

        Button exit = new Button("EXIT");
        exit.setOnAction((event -> primaryStage.close()));

        hBox.getChildren().addAll(join, exit);
        hBox.setAlignment(Pos.CENTER);

        root.setCenter(hBox);
        root.setMargin(hBox, new Insets(INSETS));
        root.autosize();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void joinRoom(){
        this.callback.joinRoom();
    }

    @FunctionalInterface
    interface JoinRoomBoardCallback{
        void joinRoom();
    }
}
