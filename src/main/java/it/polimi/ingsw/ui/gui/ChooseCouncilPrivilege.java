package it.polimi.ingsw.ui.gui;


import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.model.CouncilPrivilege;
import it.polimi.ingsw.model.InformationCallback;
import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.Privilege;

import javax.swing.*;
import java.util.ArrayList;

public class ChooseCouncilPrivilege extends JPanel{

    private ArrayList<Privilege> privilegeArrayList;
    private MainBoardStage.CallbackInterface callback;
    private CouncilPrivilege councilPrivilege;
    private String reason;

    ChooseCouncilPrivilege(String reason, CouncilPrivilege councilPrivilege) throws GameException {
        this.councilPrivilege = councilPrivilege;
        this.reason = reason;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException |
                UnsupportedLookAndFeelException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        String[] privil = {"1 WOOD & 1 SERVANT", "2 SERVANTS", "2 COINS", "2 MILITARY POINTS","1 FAITH POINTS"};
        JCheckBox[] checkBox = new JCheckBox[councilPrivilege.getNumberOfCouncilPrivileges()];
        for (int i = 0; i <checkBox.length; i++) {
            checkBox[i] = new JCheckBox(privil[i]);
            panel.add(checkBox[i]);
        }
        ImageIcon image = new ImageIcon("images/councilPrivileges.jpeg");
        panel.add(new JLabel(image));

        int result = JOptionPane.showConfirmDialog(null, panel , "Choose" + councilPrivilege.getNumberOfCouncilPrivileges() +" Privilege", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
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

    private void setPrivileges(String reason, ArrayList choice) {
        Privilege[] privileges = councilPrivilege.getPrivileges();
        for (int i = 0; i < choice.size(); i++) {
            privilegeArrayList.add(privileges[i]);
            privileges[i].setNotAvailablePrivilege();
        }
        this.callback.setCouncilPrivileges(reason, privilegeArrayList);
    }

}
