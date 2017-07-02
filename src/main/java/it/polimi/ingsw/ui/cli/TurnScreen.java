package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;
import it.polimi.ingsw.model.FamilyMemberColor;

import java.util.Arrays;

/*package-local*/ class TurnScreen extends GameScreen {

    private GameCallback callback;

    TurnScreen(GameCallback callback, boolean moveDone){
        super(callback);
        this.callback = callback;

        addOption("run-leader", "'run-leader [servants] [name-with-spaces]' to activate a leader card.", this::activateLeader);
        addOption("discard-leader", "'discard-leader [name-with-spaces]' to discard a leader card.", this::discardLeader);
        if(!moveDone) {
            addOption("set-fam-tower", "'set-fam-tower [familiar color] [servants] [tower index] [cell index]' to place family member on the tower.", this::setFamilyMemberInTower);
            addOption("set-fam-council", "'set-fam-council [familiar color] [servants]' to place family member in the council.", this::setFamilyMemberInCouncil);
            addOption("set-fam-market", "'set-fam-market [familiar color] [servants] [market index]' to place a family member in the market.", this::setFamilyMemberInMarket);
            addOption("set-fam-production-simple", "'set-fam-production-simple [familiar color] [servants]' to activate production.", this::setFamilyMemberProductionSimple);
            addOption("set-fam-harvest-simple", "'set-fam-harvest-simple [familiar color] [servants]' to activate harvest.", this::setFamilyMemberInHarvestSimple);
            addOption("set-fam-production-extended", "'set-fam-production-extended [familiar color] [servants]' to activate production extended.", this::setFamilyMemberInProductionExtended);
            addOption("set-fam-harvest-extended", "'set-fam-harvest-extended [familiar color] [servants]' to activate production extended.", this::setFamilyMemberInHarvestExtended);
        }
        addOption("end-turn", "ends up your turn.", parameters -> callback.notifyEndTurn());
    }

    private void setFamilyMemberInTower(String[] parameters) throws WrongCommandException{
        if(parameters.length == 4) {
            checkParameters(Arrays.copyOfRange(parameters, 1, parameters.length));
            for (FamilyMemberColor color : FamilyMemberColor.values())
                if (color.toString().toLowerCase().equals(parameters[0].toLowerCase())) {
                    int servants = Integer.parseInt(parameters[1]);
                    int column = Integer.parseInt(parameters[2]) - 1;
                    int row = Integer.parseInt(parameters[3]) - 1;
                    callback.setFamilyMemberInTower(color, servants, column, row);
                    return;
                }
        }
        throw new WrongCommandException();
    }

    private void setFamilyMemberInCouncil(String[] parameters) throws WrongCommandException{
        if(parameters.length == 2) {
            checkParameters(Arrays.copyOfRange(parameters, 1, parameters.length));
            for (FamilyMemberColor color : FamilyMemberColor.values())
                if (color.toString().toLowerCase().equals(parameters[0].toLowerCase())) {
                    callback.setFamilyMemberInCouncil(color, Integer.parseInt(parameters[1]));
                    return;
                }
        }
        throw new WrongCommandException();
    }

    private void setFamilyMemberInMarket(String[] parameters) throws WrongCommandException{
        if(parameters.length == 3) {
            checkParameters(Arrays.copyOfRange(parameters,1, parameters.length));
            for (FamilyMemberColor color : FamilyMemberColor.values())
                if (color.toString().toLowerCase().equals(parameters[0].toLowerCase())) {
                    callback.setFamilyMemberInMarket(color, Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]));
                    return;
                }
        }
        throw new WrongCommandException();
    }

    private void setFamilyMemberProductionSimple(String[] parameters) throws WrongCommandException{
        if(parameters.length == 2) {
            checkParameters(Arrays.copyOfRange(parameters,1, parameters.length));
            for (FamilyMemberColor color : FamilyMemberColor.values())
                if (color.toString().toLowerCase().equals(parameters[0].toLowerCase())) {
                    callback.setFamilyMemberInProductionSimple(color, Integer.parseInt(parameters[1]));
                    return;
                }
        }
        throw new WrongCommandException();
    }

    private void setFamilyMemberInHarvestSimple(String[] parameters) throws WrongCommandException{
        if(parameters.length == 2) {
            checkParameters(Arrays.copyOfRange(parameters,1, parameters.length));
            for (FamilyMemberColor color : FamilyMemberColor.values())
                if (color.toString().toLowerCase().equals(parameters[0].toLowerCase())) {
                    callback.setFamilyMemberInHarvestSimple(color, Integer.parseInt(parameters[1]));
                    return;
                }
        }
        throw new WrongCommandException();
    }

    private void setFamilyMemberInProductionExtended(String[] parameters) throws WrongCommandException{
        if(parameters.length == 2) {
            checkParameters(Arrays.copyOfRange(parameters,1, parameters.length));
            for (FamilyMemberColor color : FamilyMemberColor.values())
                if (color.toString().toLowerCase().equals(parameters[0].toLowerCase())) {
                    callback.setFamilyMemberInProductionExtended(color, Integer.parseInt(parameters[1]));
                    return;
                }
        }
        throw new WrongCommandException();
    }

    private void setFamilyMemberInHarvestExtended(String[] parameters) throws WrongCommandException{
        if(parameters.length == 1) {
            checkParameters(Arrays.copyOfRange(parameters,1, parameters.length));
            for (FamilyMemberColor color : FamilyMemberColor.values())
                if (color.toString().toLowerCase().equals(parameters[0].toLowerCase())) {
                    callback.setFamilyMemberInHarvestExtended(color, Integer.parseInt(parameters[1]));
                    return;
                }
        }
        throw new WrongCommandException();
    }

    private void activateLeader(String[] parameters) throws WrongCommandException{
        if(parameters.length > 2){
            int servants = Integer.parseInt(parameters[0]);
            if(servants < 0)
                throw new WrongCommandException();
            StringBuilder stringBuilder = new StringBuilder(parameters[1]);
            for(int i = 2; i < parameters.length; i++)
                stringBuilder.append(" " + parameters[i]);
            callback.activateLeader(stringBuilder.toString(), servants);
            return;
        }
        throw new WrongCommandException();
    }

    private void discardLeader(String[] parameters) throws WrongCommandException{
        if(parameters.length > 1){
            StringBuilder stringBuilder = new StringBuilder(parameters[0]);
            for(int i = 1; i < parameters.length; i++)
                stringBuilder.append(" " + parameters[i]);
            callback.discardLeader(stringBuilder.toString());
            return;
        }
        throw new WrongCommandException();
    }

    private void checkParameters(String[] parameters) throws WrongCommandException{
        for(String s : parameters)
            if(Integer.parseInt(s) < 0)
                throw new WrongCommandException();
    }
}