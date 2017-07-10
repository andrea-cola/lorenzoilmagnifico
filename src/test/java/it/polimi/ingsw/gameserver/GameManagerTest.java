package it.polimi.ingsw.gameserver;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.*;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.utility.Configuration;
import org.junit.Test;


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

        assertFalse(gameManager.getGameModel().getMainBoard().getVatican().getExcommunicationCard(0) == null);
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

        gameManager.createGameInstance();

        assertTrue(gameManager.getGameModel().getMainBoard().getTower(1).getTowerCell(1).getDevelopmentCard() != null);
        assertTrue(gameManager.getGameModel().getDices().getValues().get(FamilyMemberColor.BLACK) >=1 &&
                gameManager.getGameModel().getDices().getValues().get(FamilyMemberColor.BLACK) <= 6);
    }

    @Test
    public void getGameModel() throws Exception {
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

        assertTrue(gameManager.getGameModel().getClass().equals(Game.class));
    }

    @Test
    public void finalControlsForPeriod() throws Exception {
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

        player.getPersonalBoard().getValuables().increase(PointType.FAITH, 10);

        assertTrue(gameManager.finalControlsForPeriod(1, player));

        player.getPersonalBoard().getValuables().decrease(PointType.FAITH, 9);

        assertFalse(gameManager.finalControlsForPeriod(1, player));
    }
}