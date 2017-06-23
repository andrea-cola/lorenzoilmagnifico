package it.polimi.ingsw.ui.gui;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 */
public class JoinRoomStage extends JFXPanel{

    private CallbackInterface callback;

    private final static int HBOX_SPACING = 20;
    private final static int INSETS = 20;

    JoinRoomStage(CallbackInterface callback){
        this.callback = callback;
        BorderPane root = new BorderPane();
        HBox hBox = new HBox(HBOX_SPACING);

        Button join = new Button("JOIN ROOM");
        join.setOnAction((ActionEvent event) -> {
            joinRoom();
            setVisible(false);
        });

        Button exit = new Button("EXIT");
        exit.setOnAction((event -> this.hide()));

        hBox.getChildren().addAll(join, exit);
        hBox.setAlignment(Pos.CENTER);

        root.setCenter(hBox);
        root.setMargin(hBox, new Insets(INSETS));
        root.autosize();
        Scene scene = new Scene(root);
        this.setScene(scene);
    }

    private void joinRoom(){
        this.callback.joinRoom();
    }

    @FunctionalInterface
    interface CallbackInterface {
        void joinRoom();
    }
}
