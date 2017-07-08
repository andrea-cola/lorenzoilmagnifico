package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This is the Graphic User Interface Personal Board class
 */
public class PersonalBoardStage extends JFXPanel{

    private static final int WIDTH = 500;
    private static final int HEIGHT = 600;

    /**
     * Main gui PersonalBoardStage objects
     */
    private BackgroundImage background;
    private AnchorPane root;

    PersonalBoardStage(Player player) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("fxml/personalBoard.fxml"));
            root = (AnchorPane) loader.load();
            PersonalBoardStageController controller = loader.getController();
            controller.initData(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setScene(scene);
    }
}
