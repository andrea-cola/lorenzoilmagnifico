package it.polimi.ingsw.ui.gui;

import it.polimi.ingsw.model.InformationCallback;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ChooseServantNumberStage extends JPanel {

    private int servants;
    private MainBoardSettings callback;

    ChooseServantNumberStage(){
        servants = 0;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        JPanel panel = new JPanel();
        JLabel number = new JLabel(new Integer(servants).toString());
        this.add(new JLabel("Decide how many servants do you want to spend"));
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                servants++;
                number.setText(new Integer(servants).toString());
            }
        });

        this.add(plus);
        JLabel message = new JLabel("Not valid number");
        message.setVisible(false);
        this.add(message);
        this.add(number);
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (servants >= 0){
                    servants--;
                    number.setText(new Integer(servants).toString());

                }else
                    message.setVisible(true);
                }
        });
        this.add(minus);
        int result = JOptionPane.showConfirmDialog(null, this, "Choose Servants", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            this.callback.setServantsNumber(servants);
        }
    }

}

