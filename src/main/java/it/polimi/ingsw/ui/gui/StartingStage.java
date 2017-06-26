package it.polimi.ingsw.ui.gui;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;

import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;


/**
 * This is the board which the game starts with
 */
public class StartingStage extends JFXPanel {
    /**
     * Constants
     */
    private final static int VBOX_SPACING = 20;
    private final static int LOADING_FONT_DIM = 20;
    private final static int WELCOME_FONT_DIM = 40;
    private final static int INSETS = 20;

    private static boolean finished = false;

    StartingStage(){
        BorderPane root = new BorderPane();
        VBox vBoxStart = new VBox(VBOX_SPACING);
        vBoxStart.setAlignment(Pos.CENTER);
        root.setCenter(vBoxStart);
        root.setMargin(vBoxStart, new Insets(INSETS));
        Scene scene= new Scene(root);
        ProgressBar bar= new ProgressBar();
        Timeline task= new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(bar.progressProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(2),
                        new KeyValue(bar.progressProperty(),1)
                ));
        Label loading= new Label("Loading");
        loading.setTextFill(Color.DARKBLUE);
        loading.setFont(new Font(LOADING_FONT_DIM));

        loading.setVisible(false);
        Button button= new Button("START");
        button.setOnAction(event -> {
            task.playFromStart();
            loading.setVisible(true);
            task.setOnFinished(event1 -> {
                setFinished(true);
                setVisible(false);
            });
        });

        Label welcome= new Label("WELCOME");
        welcome.setFont(new Font(WELCOME_FONT_DIM));
        ImageView image = new ImageView(new Image("images/StartingStageCover.jpg"));
        image.autosize();
        vBoxStart.getChildren().addAll(welcome, image, loading, bar, button);
        root.autosize();
        this.setScene(scene);
    }

    public static boolean getFinished(){
        return finished;
    }

    public static void setFinished(boolean flag){
        finished=flag;
    }

}
