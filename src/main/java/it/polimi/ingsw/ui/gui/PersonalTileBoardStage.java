package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.Player;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.awt.*;

/**
 * This is the Graphic User Interface personal tile board
 */
/*package-local*/ class PersonalTileBoardStage extends JFXPanel{

    private static final int INSETS = 20;
    private static final int HEIGHT = 700;
    private static final int WIDTH = 100;

    /**
     * Constructor
     */
    PersonalTileBoardStage(Player player){
        BorderPane root = new BorderPane();
        StringBuilder path = new StringBuilder();
        path.append("images/personalTile/personalbonustile_");
        path.append(player.getPersonalBoard().getPersonalBoardTile().getPersonalBoardID());
        path.append(".png");
        ImageView image = new ImageView(new Image(path.toString()));
        image.autosize();
        root.setCenter(image);
        root.setMargin(image, new Insets(INSETS));
        Scene scene = new Scene(root);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setScene(scene);
    }
}
