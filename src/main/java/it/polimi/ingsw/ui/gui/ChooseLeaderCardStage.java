package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.model.LeaderCard;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ChooseLeaderCardStage extends JFXPanel{

    /**
     * Constants
     */
    private final static int VBOX_SPACING = 10;
    private final static int HBOX_SPACING = 10;
    private final static int INSETS = 20;
    private final static int IMAGE_WIDTH = 200;
    private final static int IMAGE_HEIGHT = 310;

    private CallbackInterface callback;

    private List<LeaderCard> leaderCardList;
    private String myName;
    /**
     * Constructor
     * @param callback
     * @param leaderCardList
     */
    ChooseLeaderCardStage(CallbackInterface callback, List<LeaderCard> leaderCardList){
        this.callback = callback;
        this.leaderCardList = leaderCardList;

        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        grid.setVgap(VBOX_SPACING);
        grid.setHgap(HBOX_SPACING);
        int i=0;
        for (LeaderCard tmp : leaderCardList) {
            String name = tmp.getLeaderCardName();
            System.out.println(tmp.getLeaderCardName());

            String[] parts = name.split(" ");
            StringBuilder str = new StringBuilder();
            if(parts.length > 0) {
                str.append("images/cards/");
                str.append(parts[0]);
                for (int j = 1; j < parts.length; j++)
                    str.append("+" + parts[j].toLowerCase());
            }
            str.append(".jpg");

            Image im = new Image(str.toString());
            ImageView image = new ImageView(im);
            image.setFitWidth(IMAGE_WIDTH);
            image.setFitHeight(IMAGE_HEIGHT);
            image.autosize();
            grid.add(image, i, 0);
            Button nameButton = new Button(name);
            nameButton.setAlignment(Pos.CENTER);
            grid.add(nameButton,i,1);
            i++;
            nameButton.setOnAction(event -> {
                myName = nameButton.getText();
                sendLeader();
                setVisible(false);
            });
        }

        VBox vBox = new VBox(VBOX_SPACING);
        Label label = new Label("Choose your Leaders Cards");
        vBox.getChildren().addAll(label, grid);
        root.setCenter(vBox);
        root.setMargin(vBox, new Insets(INSETS));
        root.autosize();
        Scene scene = new Scene(root);
        this.setScene(scene);
    }

    private void sendLeader(){
        for(LeaderCard tmp : leaderCardList){
            if(myName == tmp.getLeaderCardName()){
                System.out.println("NAME = " + myName);
                this.callback.sendLeaderCardChoise(tmp);
            }
        }
    }

    @FunctionalInterface
    public interface CallbackInterface{
        void sendLeaderCardChoise(LeaderCard leaderCard);
    }
}
