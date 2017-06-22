package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.model.FamilyMemberColor;

public class TurnScreen extends GameScreen {

    private GameCallback callback;

    private boolean familiarPlaced;

    TurnScreen(GameCallback callback, boolean moveDone){
        super(callback);
        this.callback = callback;
        this.familiarPlaced = moveDone;

        if(!moveDone) {
            addOption("set-fam-tower", "'set-fam-tower [familiar color] [tower index] [cell index]' to place family member on the tower.", this::setFamilyMemberInTower);
            addOption("set-fam-council", "'set-fam-council [familiar color]' to place family member in the council.", this::setFamilyMemberInCouncil);

        }
        addOption("end-turn", "ends up your turn.", parameters -> callback.notifyEndTurn());
    }

    private void setFamilyMemberInTower(String[] parameters) throws WrongCommandException{
        boolean flag = false;
        if(parameters.length == 3){
            for(FamilyMemberColor color : FamilyMemberColor.values()){
                if(color.toString().toLowerCase().equals(parameters[0].toLowerCase())){
                    int column = Integer.parseInt(parameters[1]) - 1;
                    int row = Integer.parseInt(parameters[2]) - 1;
                    callback.setFamilyMemberInTower(column, row, color);
                    flag = true;
                }
            }
            if(!flag)
                throw new WrongCommandException();
        }
        else{
            throw new WrongCommandException();
        }
    }

    private void setFamilyMemberInCouncil(String[] parameters) throws WrongCommandException{
        boolean flag = false;
        if(parameters.length == 1){
            for(FamilyMemberColor color : FamilyMemberColor.values()){
                if(color.toString().toLowerCase().equals(parameters[0].toLowerCase())){
                    callback.setFamilyMember(color);
                    flag = true;
                }
            }
            if(!flag)
                throw new WrongCommandException();
        }
        else{
            throw new WrongCommandException();
        }
    }

}
