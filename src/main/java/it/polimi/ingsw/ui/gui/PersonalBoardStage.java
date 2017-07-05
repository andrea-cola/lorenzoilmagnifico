package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.DevelopmentCardColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
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

    PersonalBoardStage(Player player) {

        AnchorPane root = new AnchorPane();
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(BACK_WIDTH, BACK_HEIGHT);
        gridPane.setHgap(GRID_HGAP);
        gridPane.setVgap(GRID_VGAP);
        BackgroundSize size = new BackgroundSize(BACK_WIDTH, BACK_WIDTH, false, false, true, false);
        Image image = new Image("images/PersonalBoardStageCover.jpg");
        background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        root.setBackground(new Background(background));

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(20);

        StringBuilder path = new StringBuilder();
        path.append("images/developmentCard/devcards_f_en_c_");
        int index = 0;
        ImageView card;
        for (int j = 0; j < 4; j++) {
            switch (j) {
                case 0:
                    for (DevelopmentCard tmp : player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE)) {
                        path.append(tmp.getId());
                        index++;
                        path.append(".png");
                        card = new ImageView(new Image(path.toString()));
                        card.setFitWidth(IMAGE_WIDTH);
                        card.setFitHeight(IMAGE_HEIGHT);
                        gridPane.add(card, index, j);
                    }
                    break;
                case 1:
                    for (DevelopmentCard tmp : player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE)) {
                        path.append(tmp.getId());
                        index++;
                        path.append(".png");
                        card = new ImageView(new Image(path.toString()));
                        card.setFitWidth(IMAGE_WIDTH);
                        card.setFitHeight(IMAGE_HEIGHT);
                        gridPane.add(card, index, j);
                    }
                    break;
                case 2:
                    for (DevelopmentCard tmp : player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW)) {
                        path.append(tmp.getId());
                        index++;
                        path.append(".png");
                        card = new ImageView(new Image(path.toString()));
                        card.setFitWidth(IMAGE_WIDTH);
                        card.setFitHeight(IMAGE_HEIGHT);
                        gridPane.add(card, index, j);
                    }
                    break;
                case 3:
                    for (DevelopmentCard tmp : player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN)) {
                        path.append(tmp.getId());
                        index++;
                        path.append(".png");
                        card = new ImageView(new Image(path.toString()));
                        card.setFitWidth(IMAGE_WIDTH);
                        card.setFitHeight(IMAGE_HEIGHT);
                        gridPane.add(card, index, j);
                    }
                    break;
            }
        }

        gridPane.relocate(GRID_X, GRID_Y);
        root.getChildren().addAll(gridPane);
        Scene scene = new Scene(root);

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setScene(scene);
    }
}
