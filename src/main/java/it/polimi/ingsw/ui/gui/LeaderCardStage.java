package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utility.Printer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/*package-local*/ class LeaderCardStage extends JFXPanel {

    private MainBoardStage.CallbackInterface callback;

    private static final int GRID_VGAP = 20;
    private static final int GRID_HGAP = 20;
    private static final int IMAGE_WIDTH = 200;
    private static final int IMAGE_HEIGHT = 310;
    private static final int HEIGHT = 500;
    private static final int WIDTH = 1000;
    private static final int INSETS = 20;

    private Integer servants = 0;
    private int servantValue;

    LeaderCardStage(MainBoardStage.CallbackInterface callback, Player player){
        this.callback = callback;
        BorderPane root = new BorderPane();
        GridPane pane = new GridPane();
        pane.setPrefSize(WIDTH, HEIGHT);
        pane.setVgap(GRID_VGAP);
        pane.setHgap(GRID_HGAP);
        servantValue = player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT);

        for (int i = 0; i < player.getPersonalBoard().getLeaderCards().size() ; i++) {
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
            label.setAlignment(Pos.CENTER);
            pane.add(image, i, 0);
            pane.add(label, i, 1);
            Button button = new Button("ACTIVE");
            button.setAlignment(Pos.CENTER);
            button.setOnAction(event ->{
                button.setVisible(false);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Active Leader");
                        alert.setHeaderText("How many servants do you want to use to activate the Leader?");
                        Button minus = new Button("-");
                        Label number = new Label(servants.toString());
                        Button plus = new Button("+");
                        HBox content = new HBox();
                        content.setAlignment(Pos.CENTER);
                        content.setSpacing(20);
                        content.getChildren().addAll(minus, number, plus);
                        alert.getDialogPane().setContent(content);
                        plus.setOnMouseClicked(e -> {
                            if(servants<servantValue) {
                                servants++;
                                minus.setVisible(true);
                                number.setText(Integer.toString(servants));
                            }else
                                plus.setVisible(false);
                        });
                        minus.setOnMouseClicked(e -> {
                            if(servants>0){
                                servants--;
                                plus.setVisible(true);
                                number.setText(Integer.toString(servants));
                            }else
                                minus.setVisible(false);
                        });
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.isPresent()) {
                            if (result.get() == ButtonType.OK)
                                callback.activeLeaderCard(name, servants);
                        }
                    }
                });
            });
            if(player.getPersonalBoard().getLeaderCards().get(i).getLeaderEffectActive())
                button.setVisible(false);
            pane.add(button, i, 2);
        }
        root.setCenter(pane);
        root.setMargin(pane, new Insets(INSETS));
        Scene scene = new Scene(root);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setScene(scene);
    }
}
