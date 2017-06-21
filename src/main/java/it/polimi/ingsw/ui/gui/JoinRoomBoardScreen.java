package it.polimi.ingsw.ui.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 */
public class JoinRoomBoardScreen extends Application{

    private JoinRoomBoardCallback callback;
    private static boolean finished = false;

    private final static int HBOX_SPACING = 20;

    public JoinRoomBoardScreen(JoinRoomBoardCallback callback){
        this.callback = callback;
    }

    /**
     * The main entry point for all JavaFX applications.
     * @param primaryStage the primary stage for this application, onto which
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JoinRoom");
        Group root = new Group();
        HBox hBox = new HBox(HBOX_SPACING);
        Button join = new Button("JOIN ROOM");
        join.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                joinRoom();
                setFinished(true);
                synchronized (this){
                    try {
                        wait(1003);
                        primaryStage.close();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Button exit = new Button("EXIT");
        exit.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        }));


        hBox.getChildren().addAll(join, exit);
        root.getChildren().addAll(hBox);
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    private void joinRoom(){
        this.callback.joinRoom();;
    }

    public void setFinished(boolean flag){
        finished = flag;
    }
    public boolean getFinished(){
        return finished;
    }

    @FunctionalInterface
    interface JoinRoomBoardCallback{
        void joinRoom();
    }
}
