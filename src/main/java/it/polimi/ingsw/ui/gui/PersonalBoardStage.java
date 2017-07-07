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

    /**
     * Constants
     */
    private static final int BACK_WIDTH = 582;
    private static final int BACK_HEIGHT = 700;
    private static final int GRID_HGAP = 5;
    private static final int GRID_VGAP = 5;
    private static final int GRID_X = 60;
    private static final int GRID_Y = 10;
    private final static int INSETS = 20;
    private final static int IMAGE_WIDTH = 100;
    private final static int IMAGE_HEIGHT = 150;
    private final static int WIDTH = 800;
    private final static int HEIGHT = 800;

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
        this.setScene(scene);
    }
}
