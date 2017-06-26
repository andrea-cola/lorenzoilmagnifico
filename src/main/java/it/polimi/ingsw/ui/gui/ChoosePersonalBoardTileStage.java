package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.PersonalBoardTile;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.List;

public class ChoosePersonalBoardTileStage extends JFXPanel {

    private CallbackInterface callback;

    private List<PersonalBoardTile> personalBoardTileList;

    private final static int VBOX_SPACING = 20;
    private final static int HBOX_SPACING = 20;
    private final static int INSETS = 20;
    private final static int IMAGE_WIDTH = 50;
    private final static int IMAGE_HEIGHT = 300;

    private int choice;

    ChoosePersonalBoardTileStage(CallbackInterface callback, List<PersonalBoardTile> personalBoardTileList){
        this.callback = callback;
        this.personalBoardTileList = personalBoardTileList;

        GridPane grid = new GridPane();
        grid.setHgap(HBOX_SPACING);
        grid.setVgap(VBOX_SPACING);
        ChoiceBox<Integer> choiceBox = new ChoiceBox<>();

        for (int i = 0; i < personalBoardTileList.size(); i++) {
            Label tile = new Label("TILE ID =" + personalBoardTileList.get(i).getPersonalBoardID());
            tile.setAlignment(Pos.CENTER);
            grid.add(tile, i, 1);
            choiceBox.getItems().add(personalBoardTileList.get(i).getPersonalBoardID());
            StringBuilder path = new StringBuilder();
            path.append("images/personalbonustile_");
            path.append(personalBoardTileList.get(i).getPersonalBoardID());
            path.append(".png");
            Image im = new Image(path.toString());
            ImageView image = new ImageView(im);
            image.autosize();
            grid.add(image,i,0);
        }

        Label message = new Label("Your data are invalid");
        message.setVisible(false);

        Button enter = new Button("ENTER");
        enter.setOnAction(event -> {
            if(choiceBox.getValue()!= null){
                choice = choiceBox.getValue();
                choosePersonalTile();
                setVisible(false);
            }else{
                message.setVisible(true);
            }
        });

        Label head = new Label("Choose your Personal Tile:");

        VBox buttons = new VBox(VBOX_SPACING);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(head, choiceBox, enter, message);

        HBox hBox = new HBox(HBOX_SPACING);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(grid, buttons);

        BorderPane root = new BorderPane();
        root.setCenter(hBox);
        root.setMargin(hBox, new Insets(INSETS));
        root.autosize();
        Scene scene = new Scene(root);
        this.setScene(scene);
    }

    private void choosePersonalTile() {
        for (PersonalBoardTile tmp : personalBoardTileList) {
            if (choice == tmp.getPersonalBoardID()) {
                System.out.println("id chosen :" + tmp.getPersonalBoardID());
                this.callback.sendPersonalBoardTileChoice(tmp);
            }
        }
    }

    @FunctionalInterface
    interface CallbackInterface{
        void sendPersonalBoardTileChoice(PersonalBoardTile personalBoardTile);
    }

}
