package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.model.CouncilPrivilege;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Privilege;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.utility.Debugger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ChooseCouncilPrivilegeScreen extends GameScreen{

    private GameCallback callback;

    private Privilege[] privileges;

    private CouncilPrivilege councilPrivilege;

    ChooseCouncilPrivilegeScreen(GameScreen.GameCallback callback, CouncilPrivilege councilPrivilege){
        super(callback);
        this.callback = callback;
        this.privileges = councilPrivilege.getPrivileges();
        this.councilPrivilege = councilPrivilege;

        System.out.println("PRIVILEGES AVAILABLE");
        for(Privilege privilege : privileges)
            System.out.println(privilege);
        addOption("gift", "'gift [number] [number] ....' to choose your privileges.", this::setPrivilege);
        System.out.println("\nYou have " + councilPrivilege.getNumberOfCouncilPrivileges() + " choices, so enter "
                + councilPrivilege.getNumberOfCouncilPrivileges() + " numbers.");
    }

    private void setPrivilege(String[] arguments) throws WrongCommandException{
        if(arguments.length == councilPrivilege.getNumberOfCouncilPrivileges()){
            try {
                ArrayList<Privilege> privilegesChosen = new ArrayList<>();
                for(int i = 0; i < arguments.length; i++){
                    int index = Integer.parseInt(arguments[i]) - 1;
                    privilegesChosen.add(privileges[index]);
                }
                callback.setPrivilege(privilegesChosen);
            } catch (IndexOutOfBoundsException e){
                throw new WrongCommandException();
            }
        } else
            throw new WrongCommandException();
    }

}
