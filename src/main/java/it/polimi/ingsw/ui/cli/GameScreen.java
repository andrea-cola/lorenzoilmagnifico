package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.Privilege;

import java.util.List;

public class GameScreen extends BasicGameScreen {

    private GameCallback callback;

    public GameScreen(GameCallback callback){
        this.callback = callback;
        printScreenTitle("BASE MENU");
        addOption("show-mainboard", "show game main board", parameters -> callback.showMainBoard());
        addOption("show-personalboard", "'show-personalboard [username]' show your personal board", parameters -> callback.showPersonalBoards());
    }

    public interface GameCallback {

        void showMainBoard();

        void showPersonalBoards();

        void setFamilyMemberInTower(int towerIndex, int cellIndex, FamilyMemberColor familyMemberColor);

        void setFamilyMemberInCouncil(FamilyMemberColor familyMember);

        void notifyEndTurn();

        void setPrivilege(List<Privilege> privilege);

    }

}
