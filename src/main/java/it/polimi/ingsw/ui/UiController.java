package it.polimi.ingsw.ui;

import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.ui.ConnectionType;

import java.util.List;
import java.util.Map;

/**
 * Interface implemented by the Game class, contains all functions related to the Game that need to
 * be updated in real time and notified for every change.
 */
public interface UiController {

    String getUsername();

    Player getPlayer();

    Game getGameModel();

    void setPlayerTurnChoices(String operation, Object choice);

    void setNetworkSettings(ConnectionType connectionType, String address, int port) throws ConnectionException;

    void loginPlayer(String nickname, String password, boolean flag);

    void joinRoom();

    void createRoom(int maxPlayers);

    void sendPersonalBoardTileChoice(PersonalBoardTile personalBoardTile);

    void notifyLeaderCardChoice(LeaderCard leaderCard);

    void notifySetFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex);

    void notifySetFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants);

    void notifySetFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int cellIndex);

    void notifySetFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants);

    void notifySetFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants);

    void notifySetFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants);

    void notifySetFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants);

    Map<String, Object> getPlayerTurnChoices();

    void endTurn();
}