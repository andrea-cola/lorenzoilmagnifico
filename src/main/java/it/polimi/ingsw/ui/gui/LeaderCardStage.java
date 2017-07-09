package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utility.Printer;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

import javax.swing.*;
import java.awt.*;

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


    LeaderCardStage(MainBoardStage.CallbackInterface callback, Player player, Button leaderCardButton){
        leaderCardButton.setDisable(true);
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
            Button button = new Button("INTERACT");
            button.setAlignment(Pos.CENTER);
            button.setOnAction(event ->{
                    button.setVisible(false);
                    EventQueue.invokeLater(() -> {
                        try{
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        }catch (IllegalAccessException | InstantiationException |
                                UnsupportedLookAndFeelException | ClassNotFoundException e) {
                            Printer.printDebugMessage(this.getClass().getSimpleName(), e.getMessage());
                        }
                        JPanel panel = new JPanel();
                        panel.add(new JLabel("Choose what you want to do: "));
                        DefaultComboBoxModel model = new DefaultComboBoxModel();
                        model.addElement("DROP LEADER");
                        model.addElement("ACTIVE LEADER");
                        JComboBox<String> comboBox = new JComboBox<>(model);
                        JLabel number = new JLabel(servants.toString());
                        JButton minus = new JButton("-");
                        JButton plus = new JButton("+");
                        plus.addActionListener(e -> {
                            if(servants<servantValue) {
                                servants++;
                                minus.setVisible(true);
                                number.setText(Integer.toString(servants));
                            }else
                                plus.setVisible(false);
                        });
                        minus.addActionListener(e -> {
                            if(servants>0){
                                servants--;
                                plus.setVisible(true);
                                number.setText(Integer.toString(servants));
                            }else
                                minus.setVisible(false);
                        });
                        panel.add(comboBox);
                        panel.add(minus);
                        panel.add(number);
                        panel.add(plus);
                        int result = JOptionPane.showConfirmDialog(null, panel, "Leader Actions", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (result == JOptionPane.OK_OPTION) {
                            if (comboBox.getSelectedItem() != null) {
                                String choice = (String) comboBox.getSelectedItem();
                                leaderAction(choice, name);
                                JOptionPane.getRootFrame().dispose();
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

    private void leaderAction(String choice, String leaderCardName){
        if (choice.equals("DROP LEADER")) {
            this.callback.discardLeader(leaderCardName);
        } else if (choice.equals("ACTIVE LEADER")) {
            this.callback.activeLeaderCard(leaderCardName, servants);
        }
    }
}
