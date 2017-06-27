package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

import javax.swing.*;
import java.awt.*;



public class LeaderCardsStage extends JFXPanel {

    private MainBoardStage.CallbackInterface callback;

    private final static int GRID_VGAP = 20;
    private final static int GRID_HGAP = 20;
    private final static int IMAGE_WIDTH = 200;
    private final static int IMAGE_HEIGHT = 310;


    LeaderCardsStage(MainBoardStage.CallbackInterface callback, Player player){
        this.callback = callback;
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

            Button button = new Button("INTERACT");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                            }catch (IllegalAccessException | InstantiationException |
                                    UnsupportedLookAndFeelException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            JPanel panel = new JPanel();
                            panel.add(new JLabel("Choose what you want to do: "));
                            DefaultComboBoxModel model = new DefaultComboBoxModel();
                            model.addElement("DROP LEADER");
                            model.addElement("ACTIVE LEADER");
                            JComboBox<String> comboBox = new JComboBox<String>(model);
                            panel.add(comboBox);
                            int result = JOptionPane.showConfirmDialog(null, panel, "Leader Actions", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                            switch (result){
                                case JOptionPane.OK_OPTION:
                                    if(comboBox.getSelectedItem()!= null) {
                                        String choice = (String) comboBox.getSelectedItem();
                                        leaderAction(choice, name);
                                    }
                            }
                        }
                    });
                }
            });
            pane.add(button, i, 2);
        }
        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private void leaderAction(String choice, String leaderCardName){
        switch (choice){
            case "DROP LEADER":
                this.callback.activeLeaderCard(leaderCardName);
                break;
            case "ACTIVE LEADER":
                this.callback.discardLeader(leaderCardName);
                break;
        }
    }
}
