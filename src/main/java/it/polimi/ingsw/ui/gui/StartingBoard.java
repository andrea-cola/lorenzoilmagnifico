package it.polimi.ingsw.ui.gui;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.CountDownLatch;

/**
 * This is the board which the game starts with
 */
public class StartingBoard extends Application {

    public static final CountDownLatch latch= new CountDownLatch(1);

    public static StartingBoard startingBoard=null;

    /**
     * It is called by the graphic user interface to wait and then continue its functions
     * after the end of start function
     * @return the starting board
     */
    public static StartingBoard waitFor(){
        try{
            latch.await();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return startingBoard;
    }

    /**
     * The start function inherited by Application represents the structure of the Starting board
     * @param primaryStage is the main container of the application
     * @throws Exception if the main method is not allocated
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StartingBoard");
        VBox vBox= new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        Scene scene= new Scene(vBox, 300, 500);

        ProgressBar bar= new ProgressBar();
        Timeline task= new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(bar.progressProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(8),
                        new KeyValue(bar.progressProperty(),1)
                ));

        Button button= new Button("START");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                task.playFromStart();
            }
        });

        Label welcome= new Label("WELCOME");
        welcome.setId("welcome");
        Label loading= new Label("Loading");
        loading.setId("loading");
        vBox.getChildren().addAll(welcome, loading, bar, button);
        scene.getStylesheets().add(StartingBoard.class.getResource("StartingBoard").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
