package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.PersonalBoardTile;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class ChoosePersonalBoardTileStage extends Application {

    private CallbackInterface callback;

    private List<PersonalBoardTile> personalBoardTileList;

    ChoosePersonalBoardTileStage(CallbackInterface callback, List<PersonalBoardTile> personalBoardTileList){
        this.callback = callback;
        this.personalBoardTileList = personalBoardTileList;
    }

    /**
     * The main entry point for all JavaFX applications.
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ChoosePersonalBoardTileStage");
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();


    }


    @FunctionalInterface
    interface CallbackInterface{
        void sendPersonalBoardTileChoice(PersonalBoardTile personalBoardTile);
    }

}
