package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utility.Printer;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.IOException;

/**
 * This is the Graphic User Interface Personal Board class
 */
/*package-local*/ class PersonalBoardStage extends JFXPanel{

    private static final int WIDTH = 500;
    private static final int HEIGHT = 600;

    /**
     * Main gui PersonalBoardStage objects
     */
    private AnchorPane root;

    PersonalBoardStage(Player player) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("fxml/personalBoard.fxml"));
            root = loader.load();
            PersonalBoardStageController controller = loader.getController();
            controller.initData(player);
        } catch (IOException e) {
            Printer.printDebugMessage(this.getClass().getSimpleName());
        }
        Scene scene = new Scene(root);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setScene(scene);
    }
}
