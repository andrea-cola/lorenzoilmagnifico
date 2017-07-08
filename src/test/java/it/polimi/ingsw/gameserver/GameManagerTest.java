package it.polimi.ingsw.gameserver;

import com.sun.org.apache.bcel.internal.generic.NEW;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.ExcommunicationEffectLoseVictoryPoints;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.ServerInterface;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.socketclient.SocketClient;
import it.polimi.ingsw.socketserver.SocketServerPlayer;
import it.polimi.ingsw.utility.Configuration;
import it.polimi.ingsw.client.*;
import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class GameManagerTest {
    @Test
    public void getLeaderCards() throws Exception {
        ServerPlayer player = new ServerPlayer() {
            @Override
            public void sendGameInfo(Game game) throws NetworkException {

            }

            @Override
            public void sendPersonalTile(ArrayList<PersonalBoardTile> personalBoardTiles) throws NetworkException {

            }

            @Override
            public void sendLeaderCards(ArrayList<LeaderCard> leaderCards) throws NetworkException {

            }

            @Override
            public void notifyTurnStarted(String username, long seconds) throws NetworkException {

            }

            @Override
            public void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws NetworkException {

            }

            @Override
            public void supportForTheChurch(boolean flag) throws NetworkException {

            }

            @Override
            public void notifyEndGame(ServerPlayer[] ranking) throws NetworkException {

            }
        };

        ArrayList<ServerPlayer> list = new ArrayList<>();
        list.add(player);

        Configurator.loadConfigurations();
        Configuration configuration = new Configuration(Configurator.getConfiguration().getWaitingTime(),
                Configurator.getConfiguration().getMoveWaitingTime(),
                Configurator.getConfiguration().getVictoryPointsForGreenCards(),
                Configurator.getConfiguration().getVictoryPointsForBlueCards(),
                Configurator.getConfiguration().getVictoryPointsBonusForFaith(),
                Configurator.getConfiguration().getMainBoard(),
                Configurator.getConfiguration().getPersonalBoard(),
                Configurator.getConfiguration().getPersonalBoardTiles());
        GameManager gameManager = new GameManager(list,
                configuration,
                Configurator.getDevelopmentCards(),
                Configurator.getLeaderCards(),
                Configurator.getExcommunicationCards());

        assertTrue(gameManager.getLeaderCards().get(0).getClass().equals(LeaderCard.class));

    }

    @Test
    public void setExcommunicationCards() throws Exception {
        ServerPlayer player = new ServerPlayer() {
            @Override
            public void sendGameInfo(Game game) throws NetworkException {

            }

            @Override
            public void sendPersonalTile(ArrayList<PersonalBoardTile> personalBoardTiles) throws NetworkException {

            }

            @Override
            public void sendLeaderCards(ArrayList<LeaderCard> leaderCards) throws NetworkException {

            }

            @Override
            public void notifyTurnStarted(String username, long seconds) throws NetworkException {

            }

            @Override
            public void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws NetworkException {

            }

            @Override
            public void supportForTheChurch(boolean flag) throws NetworkException {

            }

            @Override
            public void notifyEndGame(ServerPlayer[] ranking) throws NetworkException {

            }
        };

        ArrayList<ServerPlayer> list = new ArrayList<>();
        list.add(player);

        Configurator.loadConfigurations();
        Configuration configuration = new Configuration(Configurator.getConfiguration().getWaitingTime(),
                Configurator.getConfiguration().getMoveWaitingTime(),
                Configurator.getConfiguration().getVictoryPointsForGreenCards(),
                Configurator.getConfiguration().getVictoryPointsForBlueCards(),
                Configurator.getConfiguration().getVictoryPointsBonusForFaith(),
                Configurator.getConfiguration().getMainBoard(),
                Configurator.getConfiguration().getPersonalBoard(),
                Configurator.getConfiguration().getPersonalBoardTiles());
        GameManager gameManager = new GameManager(list,
                configuration,
                Configurator.getDevelopmentCards(),
                Configurator.getLeaderCards(),
                Configurator.getExcommunicationCards());

        gameManager.setExcommunicationCards();

        assertFalse(gameManager.getGameModel().getMainBoard().getVatican().getExcommunicationCards()[0] == null);
    }

    @Test
    public void setupMainBoard() throws Exception {
        ServerPlayer player = new ServerPlayer() {
            @Override
            public void sendGameInfo(Game game) throws NetworkException {

            }

            @Override
            public void sendPersonalTile(ArrayList<PersonalBoardTile> personalBoardTiles) throws NetworkException {

            }

            @Override
            public void sendLeaderCards(ArrayList<LeaderCard> leaderCards) throws NetworkException {

            }

            @Override
            public void notifyTurnStarted(String username, long seconds) throws NetworkException {

            }

            @Override
            public void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws NetworkException {

            }

            @Override
            public void supportForTheChurch(boolean flag) throws NetworkException {

            }

            @Override
            public void notifyEndGame(ServerPlayer[] ranking) throws NetworkException {

            }
        };

        ArrayList<ServerPlayer> list = new ArrayList<>();
        list.add(player);

        Configurator.loadConfigurations();
        Configuration configuration = new Configuration(Configurator.getConfiguration().getWaitingTime(),
                Configurator.getConfiguration().getMoveWaitingTime(),
                Configurator.getConfiguration().getVictoryPointsForGreenCards(),
                Configurator.getConfiguration().getVictoryPointsForBlueCards(),
                Configurator.getConfiguration().getVictoryPointsBonusForFaith(),
                Configurator.getConfiguration().getMainBoard(),
                Configurator.getConfiguration().getPersonalBoard(),
                Configurator.getConfiguration().getPersonalBoardTiles());
        GameManager gameManager = new GameManager(list,
                configuration,
                Configurator.getDevelopmentCards(),
                Configurator.getLeaderCards(),
                Configurator.getExcommunicationCards());

        assertTrue(gameManager.getGameModel().getMainBoard().getTower(1).getTowerCell(1).getDevelopmentCard() == null);
        gameManager.setupMainBoard(1,1);
        assertFalse(gameManager.getGameModel().getMainBoard().getTower(1).getTowerCell(1).getDevelopmentCard() == null);
    }

    @Test
    public void mainboardTurnReset() throws Exception {
        ServerPlayer player = new ServerPlayer() {
            @Override
            public void sendGameInfo(Game game) throws NetworkException {

            }

            @Override
            public void sendPersonalTile(ArrayList<PersonalBoardTile> personalBoardTiles) throws NetworkException {

            }

            @Override
            public void sendLeaderCards(ArrayList<LeaderCard> leaderCards) throws NetworkException {

            }

            @Override
            public void notifyTurnStarted(String username, long seconds) throws NetworkException {

            }

            @Override
            public void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws NetworkException {

            }

            @Override
            public void supportForTheChurch(boolean flag) throws NetworkException {

            }

            @Override
            public void notifyEndGame(ServerPlayer[] ranking) throws NetworkException {

            }
        };

        ArrayList<ServerPlayer> list = new ArrayList<>();
        list.add(player);

        Configurator.loadConfigurations();
        Configuration configuration = new Configuration(Configurator.getConfiguration().getWaitingTime(),
                Configurator.getConfiguration().getMoveWaitingTime(),
                Configurator.getConfiguration().getVictoryPointsForGreenCards(),
                Configurator.getConfiguration().getVictoryPointsForBlueCards(),
                Configurator.getConfiguration().getVictoryPointsBonusForFaith(),
                Configurator.getConfiguration().getMainBoard(),
                Configurator.getConfiguration().getPersonalBoard(),
                Configurator.getConfiguration().getPersonalBoardTiles());
        GameManager gameManager = new GameManager(list,
                configuration,
                Configurator.getDevelopmentCards(),
                Configurator.getLeaderCards(),
                Configurator.getExcommunicationCards());

        assertTrue(gameManager.getGameModel().getMainBoard().getTower(1).getTowerCell(1).getDevelopmentCard() == null);
        gameManager.setupMainBoard(1,1);
        assertFalse(gameManager.getGameModel().getMainBoard().getTower(1).getTowerCell(1).getDevelopmentCard() == null);
        gameManager.mainboardTurnReset();
        assertTrue(gameManager.getGameModel().getMainBoard().getTower(1).getTowerCell(1).getDevelopmentCard() == null);
    }

    @Test
    public void personalBoardsTurnReset() throws Exception {
    }

    @Test
    public void createGameInstance() throws Exception {

    }

    @Test
    public void getGameModel() throws Exception {
    }

    @Test
    public void getStartOrder() throws Exception {
    }

    @Test
    public void getInformationChoicesHandler() throws Exception {
    }

    @Test
    public void setInformationChoicesHandler() throws Exception {
    }

    @Test
    public void finalControlsForPeriod() throws Exception {
    }

    @Test
    public void applySupportChoice() throws Exception {
    }

    @Test
    public void calculateFinalPoints() throws Exception {
        ServerPlayer player = new ServerPlayer() {
            @Override
            public void sendGameInfo(Game game) throws NetworkException {

            }

            @Override
            public void sendPersonalTile(ArrayList<PersonalBoardTile> personalBoardTiles) throws NetworkException {

            }

            @Override
            public void sendLeaderCards(ArrayList<LeaderCard> leaderCards) throws NetworkException {

            }

            @Override
            public void notifyTurnStarted(String username, long seconds) throws NetworkException {

            }

            @Override
            public void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws NetworkException {

            }

            @Override
            public void supportForTheChurch(boolean flag) throws NetworkException {

            }

            @Override
            public void notifyEndGame(ServerPlayer[] ranking) throws NetworkException {

            }
        };

        ArrayList<ServerPlayer> list = new ArrayList<>();
        list.add(player);

        Configurator.loadConfigurations();
        Configuration configuration = new Configuration(Configurator.getConfiguration().getWaitingTime(),
                Configurator.getConfiguration().getMoveWaitingTime(),
                Configurator.getConfiguration().getVictoryPointsForGreenCards(),
                Configurator.getConfiguration().getVictoryPointsForBlueCards(),
                Configurator.getConfiguration().getVictoryPointsBonusForFaith(),
                Configurator.getConfiguration().getMainBoard(),
                Configurator.getConfiguration().getPersonalBoard(),
                Configurator.getConfiguration().getPersonalBoardTiles());
        GameManager gameManager = new GameManager(list,
                configuration,
                Configurator.getDevelopmentCards(),
                Configurator.getLeaderCards(),
                Configurator.getExcommunicationCards());

        player.getPersonalBoard().getValuables().increase(PointType.VICTORY, 25);

        ExcommunicationEffectLoseVictoryPoints effect = new ExcommunicationEffectLoseVictoryPoints();
        PointsAndResources valuables = new PointsAndResources();
        valuables.increase(PointType.VICTORY, 5);
        effect.setValuablesOwnedIndex(valuables);
        effect.runEffect(player);

        assertEquals(20, (int)player.getPersonalBoard().getValuables().getPoints().get(PointType.VICTORY));
        gameManager.calculateFinalPoints();
    }

}