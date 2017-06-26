package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.Player;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;


import javax.swing.*;
import java.awt.*;


public class LeaderCardsStage extends JFXPanel {


    private final static int GRID_VGAP = 20;
    private final static int GRID_HGAP = 20;
    private final static int IMAGE_WIDTH = 200;
    private final static int IMAGE_HEIGHT = 310;

    LeaderCardsStage(Player player){
        GridPane pane = new GridPane();
        pane.setVgap(GRID_VGAP);
        pane.setHgap(GRID_HGAP);

        for (int i = 0; i <4 ; i++) {
            StringBuilder path = new StringBuilder();
            path.append("images/leaderCards/");
            String name = player.getPersonalBoard().getLeaderCards().get(i).getLeaderCardName();
            String[] parts = name.split(" ");
            if(parts.length > 0) {
                path.append(parts[0]);
                for (int j = 1; j < parts.length; j++)
                    path.append("+" + parts[j].toLowerCase());
            }
            path.append(".jpg");
            ImageView image = new ImageView(new Image(path.toString()));
            image.setFitWidth(IMAGE_WIDTH);
            image.setFitHeight(IMAGE_HEIGHT);
            image.autosize();
            Label label = new Label(name);
            pane.add(image, i, 0);
            pane.add(label, i, 1);
        }

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

}
