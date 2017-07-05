package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.CouncilPrivilege;
import it.polimi.ingsw.model.Privilege;
import javafx.scene.control.Label;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ChooseCouncilPrivilege extends JPanel{

    private ArrayList<Privilege> privilegeArrayList;
    private MainBoardStage.CallbackInterface callback;
    private CouncilPrivilege councilPrivilege;
    private String reason;

    ChooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege, MainBoardStage.CallbackInterface callback) throws GameException {
        this.councilPrivilege = councilPrivilege;
        this.reason = reason;
        this.callback = callback;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException |
                UnsupportedLookAndFeelException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String[] privil = {"1 WOOD & 1 SERVANT", "2 SERVANTS", "2 COINS", "2 MILITARY POINTS","1 FAITH POINTS"};
        JCheckBox[] checkBox = new JCheckBox[privil.length];
        for (int i = 0; i <checkBox.length; i++) {
            checkBox[i] = new JCheckBox(privil[i]);
            this.add(checkBox[i]);
        }

        int result = JOptionPane.showConfirmDialog(null, this , "Choose " + councilPrivilege.getNumberOfCouncilPrivileges() +" Privilege", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            JOptionPane.getRootFrame().dispose();
            ArrayList<Integer> arrayList = new ArrayList();
            int j = 0;
            for (int i = 0; i < checkBox.length; i++) {
                if (checkBox[i].isSelected())
                    j++;
            }
            if (j > councilPrivilege.getNumberOfCouncilPrivileges())
                throw new GameException();
            else {
                for (int i = 0; i < councilPrivilege.getNumberOfCouncilPrivileges(); i++) {
                    if (checkBox[i].isSelected()) {
                        arrayList.add(i);
                    }
                }
                setPrivileges(reason, arrayList);
            }
        }
    }

    private void setPrivileges(String reason, ArrayList<Integer> choice) {
        Privilege[] privileges = councilPrivilege.getPrivileges();
        privilegeArrayList = new ArrayList<>();
        for (Integer tmp : choice) {
            int j = tmp;
            privilegeArrayList.add(privileges[j]);
            privileges[j].setNotAvailablePrivilege();
        }
        this.callback.setCouncilPrivileges(reason, privilegeArrayList);
    }

}
