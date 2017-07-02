package it.polimi.ingsw.ui.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 * This is the Graphic User Interface Personal Board class
 */
public class PersonalBoardStage extends Application{
    /**
     * Constants
     */
    private static final int BACK_WIDTH = 150;
    private static final int BACK_HEIGHT = 200;

    /**
     * Data related to the player
     */
    private String username;
    private int coinsValue;
    private int woodValue;
    private int stonesValue;
    private int servantsValue;

    /**
     * Main gui PersonalBoardStage objects
     */
    private BackgroundImage background;

    /**
     * Constructor
     * @param username
     */
    public PersonalBoardStage(String username){
        this.username = username;
    }

    public void setData(int coinsValue, int woodValue, int stonesValue, int servantsValue){
        this.coinsValue = coinsValue;
        this.woodValue = woodValue;
        this.stonesValue = stonesValue;
        this.servantsValue = servantsValue;
    }

    /**
     * The main entry point for all JavaFX applications
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PersonalBoardStage");
        Group root = new Group();
        GridPane gridPane = new GridPane();
        BackgroundSize size = new BackgroundSize(BACK_WIDTH, BACK_WIDTH, false, false, true, false);
        Image image = new Image("images/PersonalBoardCover.jpg");
        background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        gridPane.setBackground( new Background(background));

        Label coins = new Label("Coins = " + coinsValue);
        gridPane.add(coins, 0, 6);
        Label wood = new Label("Wood = " + woodValue);
        gridPane.add(wood, 1, 6);
        Label stones = new Label("Stones = " + stonesValue);
        gridPane.add(stones, 2, 6);
        Label servants = new Label(" Servants = " + servantsValue);
        gridPane.add(servants, 3, 6);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
