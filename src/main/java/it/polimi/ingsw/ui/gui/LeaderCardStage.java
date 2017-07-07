package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
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
import java.awt.event.ActionListener;


public class LeaderCardStage extends JFXPanel {

    private MainBoardStage.CallbackInterface callback;

    private final static int GRID_VGAP = 20;
    private final static int GRID_HGAP = 20;
    private final static int IMAGE_WIDTH = 200;
    private final static int IMAGE_HEIGHT = 310;
    private final static int HEIGHT = 500;
    private final static int WIDTH = 1000;
    private final static int INSETS = 20;

    private Integer servants = new Integer(0);
    private int servantValue;


    LeaderCardStage(MainBoardStage.CallbackInterface callback, Player player){
        this.callback = callback;
        BorderPane root = new BorderPane();
        GridPane pane = new GridPane();
        pane.setPrefSize(WIDTH, HEIGHT);
        pane.setVgap(GRID_VGAP);
        pane.setHgap(GRID_HGAP);
        servantValue = player.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT);

        for (int i = 0; i <player.getPersonalBoard().getLeaderCards().size() ; i++) {
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
                            JLabel number = new JLabel(new Integer(servants).toString());
                            JButton minus = new JButton("-");
                            JButton plus = new JButton("+");
                            plus.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(java.awt.event.ActionEvent e) {
                                    if(servants<servantValue) {
                                        servants++;
                                        minus.setVisible(true);
                                        number.setText(new Integer(servants).toString());
                                    }else
                                        plus.setVisible(false);
                                }

                            });
                            minus.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(java.awt.event.ActionEvent e) {
                                    if(servants>0){
                                        servants--;
                                        plus.setVisible(true);
                                        number.setText(new Integer(servants).toString());
                                    }else
                                        minus.setVisible(false);
                                }
                            });
                            panel.add(comboBox);
                            panel.add(minus);
                            panel.add(number);
                            panel.add(plus);
                            int result = JOptionPane.showConfirmDialog(null, panel, "Leader Actions", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                            switch (result){
                                case JOptionPane.OK_OPTION:
                                    if(comboBox.getSelectedItem()!= null) {
                                        String choice = (String) comboBox.getSelectedItem();
                                        leaderAction(choice, name);
                                        JOptionPane.getRootFrame().dispose();
                                    }
                            }
                        }
                    });
                }
            });
            pane.add(button, i, 2);
        }
        root.setCenter(pane);
        root.setMargin(pane, new Insets(INSETS));
        Scene scene = new Scene(root);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setScene(scene);
    }

    private void leaderAction(String choice, String leaderCardName){
        switch (choice){
            case "DROP LEADER":
                this.callback.discardLeader(leaderCardName);
                break;
            case "ACTIVE LEADER":
                this.callback.activeLeaderCard(leaderCardName, servants);
                break;
        }
    }
}
