package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.Player;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

/**
 * This is the Graphic User Interface personal tile board
 */
public class PersonalTileBoardStage extends JFXPanel{

    private final static int INSETS = 20;
    private final static int VBOX_SPACING = 20;
    private final static int HEIGHT = 700;
    private final static int WIDTH = 100;

    /**
     * Constructor
     */
    PersonalTileBoardStage(Player player){
        BorderPane root = new BorderPane();
        StringBuilder path = new StringBuilder();
        path.append("images/personalTile/personalbonustile_");
        path.append(player.getPersonalBoard().getPersonalBoardTile().getPersonalBoardID());
        path.append(".png");
        System.out.println(path.toString());
        ImageView image = new ImageView(new Image(path.toString()));
        image.autosize();
        root.setCenter(image);
        root.setMargin(image, new Insets(INSETS));
        Scene scene = new Scene(root);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setScene(scene);
    }
}
