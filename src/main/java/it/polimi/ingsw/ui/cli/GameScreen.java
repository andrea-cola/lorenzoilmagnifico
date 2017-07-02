package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.FamilyMemberColor;

/*package-local*/ class GameScreen extends BasicGameScreen {

    private GameCallback callback;

    GameScreen(GameCallback callback){
        this.callback = callback;
        printScreenTitle("BASE MENU");
        addOption("mainboard", "'mainboard' show game main board", parameters -> callback.showMainBoard());
        addOption("boards", "'boards' show player personal boards", parameters -> callback.showPersonalBoards());
    }

    public interface GameCallback {

        void showMainBoard();

        void showPersonalBoards();

        void setFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex);

        void setFamilyMemberInCouncil(FamilyMemberColor familyMember, int servants);

        void setFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int marketIndex);

        void setFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants);

        void setFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants);

        void setFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants);

        void setFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants);

        void activateLeader(String leaderName, int servants);

        void discardLeader(String leaderName);

        void notifyEndTurn();

    }

}
