package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.model.MainBoard;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseServantStage extends JPanel{

    private int servants = 0;

    ChooseServantStage() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException |
                UnsupportedLookAndFeelException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.add(new JLabel("Decide how many servants do you want to spend"));

        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                servants++;
            }
        });
        this.add(plus);
        JLabel number = new JLabel(new Integer(servants).toString());
        this.add(number);
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                servants--;
            }
        });

        this.add(minus);

        int result = JOptionPane.showConfirmDialog(null, this, "Choose Servants", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (result) {
            case JOptionPane.OK_OPTION:
        }
    }

}
