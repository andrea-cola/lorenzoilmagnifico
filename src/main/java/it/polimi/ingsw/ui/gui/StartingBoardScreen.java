package it.polimi.ingsw.ui.gui;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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


/**
 * This is the board which the game starts with
 */
public class StartingBoardScreen extends Application {
    /**
     * Constants
     */
    private final static int VBOX_SPACING = 20;
    private final static int LOADING_FONT_DIM = 20;
    private final static int WELCOME_FONT_DIM = 40;
    private final static int INSETS = 20;

    private static boolean finished = false;


    /**
     * The start function inherited by Application represents the structure of the Starting board
     * @param primaryStage is the main container of the application
     * @throws Exception if the main method is not allocated
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StartingBoardScreen");
        BorderPane root = new BorderPane();
        VBox vBoxStart = new VBox(VBOX_SPACING);
        vBoxStart.setAlignment(Pos.CENTER);

        root.setCenter(vBoxStart);
        root.setMargin(vBoxStart, new Insets(INSETS));
        root.autosize();
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
                synchronized (this) {
                    try {
                        primaryStage.close();
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        });

        Label welcome= new Label("WELCOME");
        welcome.setFont(new Font(WELCOME_FONT_DIM));

        ImageView image = new ImageView(new Image("images/StartingBoardCover.jpg"));
        vBoxStart.getChildren().addAll(welcome, image, loading, bar, button);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static boolean getFinished(){
        return finished;
    }

    public static void setFinished(boolean flag){
        finished=flag;
    }
}
