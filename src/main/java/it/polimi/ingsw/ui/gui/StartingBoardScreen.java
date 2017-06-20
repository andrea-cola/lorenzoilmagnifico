package it.polimi.ingsw.ui.gui;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

/**
 * This is the board which the game starts with
 */
public class StartingBoardScreen extends Application{
    /**
     * Constants
     */
    private final static int VBOX_SPACING = 20;
    private final static int BACK_WIDTH = 100;
    private final static int BACK_HEIGHT = 150;
    private final static int LOADING_FONT = 20;
    private final static int WELCOME_FONT = 40;

    /**
     * The start function inherited by Application represents the structure of the Starting board
     * @param primaryStage is the main container of the application
     * @throws Exception if the main method is not allocated
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StartingBoardScreen");
        Group root = new Group();

        VBox vBoxStart = new VBox(VBOX_SPACING);
        vBoxStart.setAlignment(Pos.CENTER);
        root.getChildren().addAll(vBoxStart);
        Scene scene= new Scene(root);

        ProgressBar bar= new ProgressBar();
        Timeline task= new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(bar.progressProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(3),
                        new KeyValue(bar.progressProperty(),1)
                ));

        Label loading= new Label("Loading");
        loading.setTextFill(Color.DARKBLUE);
        loading.setFont(new Font(LOADING_FONT));

        loading.setVisible(false);
        Button button= new Button("START");
        button.setOnAction(event -> {
            task.playFromStart();
            loading.setVisible(true);
            task.setOnFinished(event1 -> {
                primaryStage.close();
            });
        });


        Label welcome= new Label("WELCOME");
        welcome.setFont(new Font(WELCOME_FONT));

        ImageView image = new ImageView(new Image("images/StartingBoardCover.jpg"));
        vBoxStart.getChildren().addAll(welcome, image,  loading, bar, button);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }



}
