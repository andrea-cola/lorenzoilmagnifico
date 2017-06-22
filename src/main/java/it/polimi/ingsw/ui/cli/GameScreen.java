package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.FamilyMemberColor;

public class GameScreen extends BasicGameScreen {

    private GameCallback callback;

    GameScreen(GameCallback callback){
        this.callback = callback;
        printScreenTitle("BASE MENU");
        addOption("show-mainboard", "show game main board", parameters -> callback.showMainBoard());
        addOption("show-personalboard", "'show-personalboard [username]' show your personal board", parameters -> callback.showPersonalBoards());
    }

    public interface GameCallback {

        void showMainBoard();

        void showPersonalBoards();

        void setFamilyMemberInTower(int towerIndex, int cellIndex, FamilyMemberColor familyMemberColor);

        void setFamilyMember(FamilyMemberColor familyMember);

        void notifyEndTurn();

    }

}
