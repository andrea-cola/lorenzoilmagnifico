package it.polimi.ingsw.ui.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * This is the Graphic User Interface personal tile board
 */
public class PersonalTileBoardScreen extends Application{

    private String username;

    /**
     * Constructor
     * @param username player
     */
    public PersonalTileBoardScreen(String username){
        this.username = username;
    }



    /**
     * The main entry point for all JavaFX applications
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PersonalTileBoardScreen");

        Group root = new Group();

        root.getChildren().addAll(new ImageView(new Image("")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
