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
    private static final int GRID_HGAP = 10;
    private static final int GRID_VGAP = 10;
    private final static int INSETS = 20;
    private final static int IMAGE_WIDTH = 80;
    private final static int IMAGE_HEIGHT = 115;
    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;

    /**
     * Data related to the player
     */
    private int coinsValue;
    private int woodValue;
    private int stonesValue;
    private int servantsValue;

    /**
     * Main gui PersonalBoardStage objects
     */
    private BackgroundImage background;

    PersonalBoardStage(Player player) {

        this.coinsValue = player.getPersonalBoard().getValuables().getResources().get(ResourceType.COIN);
        this.woodValue = player.getPersonalBoard().getValuables().getResources().get(ResourceType.WOOD);
        this.stonesValue = player.getPersonalBoard().getValuables().getResources().get(ResourceType.STONE);
        this.servantsValue = player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT);

        AnchorPane root = new AnchorPane();
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(BACK_WIDTH, BACK_HEIGHT);
        gridPane.setHgap(GRID_HGAP);
        gridPane.setVgap(GRID_VGAP);
        BackgroundSize size = new BackgroundSize(BACK_WIDTH, BACK_WIDTH, false, false, true, false);
        Image image = new Image("images/PersonalBoardStageCover.jpg");
        background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        root.setBackground(new Background(background));

        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j < 4; j++) {
                StringBuilder path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                int id;
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setPercentWidth(20);
                if(j==0 && player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).size()!=0) {
                    id = player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE).get(i).getId();
                    path.append(id);
                    path.append(".png");
                    ImageView card = new ImageView(new Image(path.toString()));
                    card.autosize();
                    gridPane.add(card, i, j);
                }else if(j== 1 && player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).size()!=0) {
                    id = player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).get(i).getId();
                    path.append(id);
                    path.append(".png");
                    ImageView card1 = new ImageView(new Image(path.toString()));
                    card1.autosize();
                    gridPane.add(card1, i, j);
                }else if (j ==2 && player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).size()!=0) {
                    id = player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW).get(i).getId();
                    path.append(id);
                    path.append(".png");
                    ImageView card2 = new ImageView(new Image(path.toString()));
                    card2.autosize();
                    gridPane.add(card2, i, j);
                }else if( j== 3 && player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).size()!=0) {
                    id = player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).get(i).getId();
                    path.append(id);
                    path.append(".png");
                    ImageView card3 = new ImageView(new Image(path.toString()));
                    card3.autosize();
                    gridPane.add(card3, i, j);
                }else{
                    ImageView nullImage = new ImageView(new Image("images/coverCard.jpg"));
                    nullImage.setFitWidth(IMAGE_WIDTH);
                    nullImage.setFitHeight(IMAGE_HEIGHT);
                    gridPane.add(nullImage, i, j);
                }
            }
        }

        Label coins = new Label(new Integer(coinsValue).toString());
        coins.setTextFill(Color.RED);
        gridPane.add(coins, 0, 5);
        Label wood = new Label(new Integer(woodValue).toString());
        wood.setTextFill(Color.RED);
        gridPane.add(wood, 1, 5);
        Label stones = new Label(new Integer(stonesValue).toString());
        stones.setTextFill(Color.RED);
        gridPane.add(stones, 2, 5);
        Label servants = new Label(new Integer(servantsValue).toString());
        servants.setTextFill(Color.RED);
        gridPane.add(servants, 3, 5);

        AnchorPane.setTopAnchor(gridPane, 10.0);
        AnchorPane.setLeftAnchor(gridPane, 10.0);
        AnchorPane.setRightAnchor(gridPane, 10.0);
        AnchorPane.setBottomAnchor(gridPane, 10.0);
        root.getChildren().addAll(gridPane);
        Scene scene = new Scene(root);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setScene(scene);
    }
}
