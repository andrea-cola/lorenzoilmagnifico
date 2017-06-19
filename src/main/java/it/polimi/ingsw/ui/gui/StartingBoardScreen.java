package it.polimi.ingsw.ui.gui;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This is the board which the game starts with
 */
public class StartingBoardScreen extends Application{

    public static StartingBoardScreen startingBoardScreen =null;



    /**
     * The start function inherited by Application represents the structure of the Starting board
     * @param primaryStage is the main container of the application
     * @throws Exception if the main method is not allocated
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StartingBoardScreen");
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
        button.setOnAction(event -> task.playFromStart());

        Label welcome= new Label("WELCOME");
        welcome.setId("welcome");
        Label loading= new Label("Loading");
        loading.setId("loading");
        vBox.getChildren().addAll(welcome, loading, bar, button);
        scene.getStylesheets().add(StartingBoardScreen.class.getResource("StartingBoardScreen").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
