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
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * This is the Graphic User Interface Personal Board class
 */
public class PersonalBoardStage extends JFXPanel{
    /**
     * Constants
     */
    private static final int BACK_WIDTH = 150;
    private static final int BACK_HEIGHT = 200;
    private static final int GRID_HGAP = 10;
    private static final int GRID_VGAP = 10;
    private final static int INSETS = 20;

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

        BorderPane root = new BorderPane();
        root.setPrefSize(BACK_WIDTH, BACK_HEIGHT);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(GRID_HGAP);
        gridPane.setVgap(GRID_VGAP);
        BackgroundSize size = new BackgroundSize(BACK_WIDTH, BACK_WIDTH, false, false, true, false);
        Image image = new Image("images/PersonalBoardStageCover.jpg");
        background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        gridPane.setBackground(new Background(background));

        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <5; j++) {
                StringBuilder path = new StringBuilder();
                path.append("images/developmentCard/devcards_f_en_c_");
                int id;
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
                    Label nothing = new Label("Nothing to show");
                    gridPane.add(nothing, i, j);
                }
            }
        }

        Label coins = new Label("Coins = " + coinsValue);
        gridPane.add(coins, 0, 5);
        Label wood = new Label("Wood = " + woodValue);
        gridPane.add(wood, 1, 5);
        Label stones = new Label("Stones = " + stonesValue);
        gridPane.add(stones, 2, 5);
        Label servants = new Label(" Servants = " + servantsValue);
        gridPane.add(servants, 3, 5);

        root.setCenter(gridPane);
        root.setMargin(gridPane, new Insets(INSETS));
        Scene scene = new Scene(root);
        this.setScene(scene);
    }

}
