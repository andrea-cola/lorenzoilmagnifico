package it.polimi.ingsw.gameServer;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.utility.Configuration;
import it.polimi.ingsw.utility.Debugger;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.server.ServerPlayer;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * This class represent a game room.
 */
public class Room {

    private static final int MIN_PLAYER_TO_START = 2;
    private static final long IMMEDIATE_START_TIME = 0L;
    private static final int LEADER_CARD_PER_PLAYER = 4;

    /**
     * Room identifier.
     */
    private int roomID;

    /**
     * Mutex object to synchronize room access.
     */
    private static final Object MUTEX = new Object();

    /**
     * Max player allowed in the room.
     */
    private final int maxPlayerNumber;

    /**
     * Timer used to run a task after a specified amount of time.
     */
    private Timer startGameTimer;

    /**
     * Configurator.
     */
    private Configuration roomConfiguration;

    /**
     * Maximum time before game starts.
     */
    private long maxWaitingTimeBeforeStart;

    /**
     * Player max time to make a move.
     */
    private long maxMoveWaitingTime;

    /**
     * Flag that indicates if the room is open.
     * True -> open. False -> closed.
     */
    private boolean roomOpen;

    /**
     * Instance of the server game.
     */
    private GameManager gameManager;

    /**
     * List of all players that have joined the room.
     */
    private ArrayList<ServerPlayer> players = new ArrayList();

    private Game game;

    private CountDownLatch countDownLatch;

    /**
     * Class constructor.
     * Set max number of player in the room.
     * Set the room in open state.
     * Add player in the list.
     * Set room configuration.
     */
    public Room(int id, ServerPlayer serverPlayer, int number, Configuration configuration){
        players = new ArrayList<>();
        roomOpen = true;
        maxPlayerNumber = number;
        players.add(serverPlayer);
        configureGame(configuration);
        roomID = id;
    }

    /**
     * Get room id.
     * @return room id.
     */
    public int getRoomID(){
        return this.roomID;
    }

    /**
     * Get the configuration from the server and configure the room.
     * @param configuration bundle.
     */
    private void configureGame(Configuration configuration){
        this.roomConfiguration = configuration;
        maxWaitingTimeBeforeStart = configuration.getWaitingTime();
        maxMoveWaitingTime = configuration.getWaitingTime();
    }

    /**
     * Method to start the timer. At the end of set time, a task is executed.
     * @param time before run the task.
     */
    private void startTimer(long time){
        startGameTimer = new Timer();
        startGameTimer.schedule(new GameHandler(), time);
    }

    /**
     * Method to reset start game timer.
     */
    private void resetTimer(){
        if(startGameTimer != null) {
            startGameTimer.cancel();
            startGameTimer.purge();
        }
    }

    public void rejoinRoom(ServerPlayer serverPlayer){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getNickname().equals(serverPlayer.getNickname()))
                players.set(i, serverPlayer);
        }
        Debugger.printDebugMessage(serverPlayer.getNickname() + " has rejoined the previous room.");
        //serverPlayer.sendGame(gameManager.game);
        //__________________________________
    }

    /**
     * Join a player in the room. Using MUTEX concurrent access is managed.
     * @param serverPlayer who would access the room.
     * @throws RoomException if the room is full or closed.
     */
    public void joinRoom(ServerPlayer serverPlayer) throws RoomException{
        synchronized (MUTEX){
            if(roomOpen){
                players.add(serverPlayer);
                if(players.size() == maxPlayerNumber){
                    roomOpen = false;
                    resetTimer();
                    startTimer(IMMEDIATE_START_TIME);
                    Debugger.printDebugMessage("Room #" + this.roomID + " starts in " + IMMEDIATE_START_TIME + " seconds.");
                }
                else if(players.size() == MIN_PLAYER_TO_START) {
                    startTimer(maxWaitingTimeBeforeStart);
                    Debugger.printDebugMessage("Room #" + this.roomID + " starts in " + maxWaitingTimeBeforeStart/1000 + " seconds.");
                }
            }
            else
                throw new RoomException();
        }
    }

    public boolean userAlreadyJoined(ServerPlayer serverPlayer){
        for(ServerPlayer player : players)
            if(player.getNickname().equals(serverPlayer.getNickname()))
                return true;
        return false;
    }

    public void onPersonalTilesChosen(){
        countDownLatch.countDown();
    }

    /**
     * This class is used to manage the room during the game.
     */
    private class GameHandler extends TimerTask {

        /**
         * This method is executed when the time is expired. At first, it closes the room.
         * Then start the game.
         */
        @Override
        public void run(){
            setupBeforeStartGame();
            //sendGameSession();
            //Debugger.printDebugMessage("Game starts in room #" + getRoomID());
        }

        /**
         * Close the room and get the game manager from configurator.
         */
        private void setupBeforeStartGame(){
            lockRoom();

            Debugger.printDebugMessage("[Room #" + roomID + "] : Room closed.");
            gameManager = Configurator.buildAndGetGame(players, roomConfiguration);
            players = gameManager.getStartOrder();

            Debugger.printStandardMessage("[Room #" + roomID + "] : Players are choosing personal tiles.");
            personalTilesChoice(roomConfiguration.getPersonalBoardTiles());

            Debugger.printStandardMessage("[Room #" + roomID + "] : Players are choosing leader cardss.");
            //leaderCardsChoice(Configurator.getLeaderCards());

            //game = gameManager.getGameInstance();
        }

        private void leaderCardsChoice(ArrayList<LeaderCard> leaderCards){
            ArrayList<LeaderCard> cards = new ArrayList<>();
            cards.addAll(leaderCards);


            Collections.shuffle(leaderCards);
            for(int i = 0; i < LEADER_CARD_PER_PLAYER; i++){

            }
        }

        private void personalTilesChoice(ArrayList<PersonalBoardTile> personalBoardTileList){
            ArrayList<PersonalBoardTile> tmp = new ArrayList<>();
            tmp.addAll(personalBoardTileList);

            for(int i = players.size() - 1; i >= 0; i--) {
                countDownLatch = new CountDownLatch(1);
                try {
                    players.get(i).sendPersonalTile(tmp);
                    countDownLatch.await();
                    for(int j = 0; j < tmp.size(); j++)
                        if (tmp.get(j).getPersonalBoardID() == players.get(i).getPersonalBoard().getPersonalBoardTile().getPersonalBoardID())
                            tmp.remove(j);
                } catch (NetworkException | InterruptedException e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), "Player offline.");
                }
            }
        }

        /**
         * Send to each player information contained in game model.
         */
        private void sendGameSession(){
            for(ServerPlayer player : players) {
                try {
                    player.sendGameInfo(game);
                } catch (NetworkException e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), "Player offline.");
                }
            }
        }

        /**
         * Close the room.
         * Mutex is held to avoid concurrency problems.
         */
        private void lockRoom(){
            synchronized (MUTEX){
                roomOpen = false;
            }
        }

    }

}
