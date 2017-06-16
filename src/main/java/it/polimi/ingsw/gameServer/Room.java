package it.polimi.ingsw.gameServer;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.utility.Configuration;
import it.polimi.ingsw.utility.Debugger;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.server.ServerPlayer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represent a game room.
 */
public class Room {

    /**
     * Minimum number of player before game can start.
     */
    private static final int MIN_PLAYER_TO_START = 2;

    /**
     * Constant that indicate the time to start immediately the game.
     */
    private static final long IMMEDIATE_START_TIME = 0L;

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

    /**
     * Class constructor.
     * Set max number of player in the room.
     * Set the room in open state.
     * Add player in the list.
     * Set room configuration.
     */
    public Room(ServerPlayer serverPlayer, int number, Configuration configuration){
        players = new ArrayList<>();
        roomOpen = true;
        maxPlayerNumber = number;
        players.add(serverPlayer);
        configureGame(configuration);
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
     * @param time before execute the task.
     */
    private void startTimer(long time){
        resetTimer();
        startGameTimer = new Timer();
        startGameTimer.schedule(new GameHandler(), time);
    }

    /**
     * Method to reset start game timer.
     */
    private void resetTimer(){
        startGameTimer.cancel();
        startGameTimer.purge();
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
                    startTimer(IMMEDIATE_START_TIME);
                }
                else if(players.size() == MIN_PLAYER_TO_START)
                    startTimer(maxWaitingTimeBeforeStart);
            }
            else
                throw new RoomException();
        }
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
            sendGameSession();
        }

        /**
         * Close the room and get the game manager from configurator.
         */
        private void setupBeforeStartGame(){
            lockRoom();
            gameManager = Configurator.buildAndGetGame(players, roomConfiguration);
            Debugger.printDebugMessage("[Room] : Room closed. Session created. Lorenzo Il Magnifico is starting...");
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

        /**
         * Send to each player some information contained in the game manager.
         */
        private void sendGameSession(){
            for(ServerPlayer player : players)
                try{
                    player.sendGameInfo(gameManager);
                } catch(NetworkException e){
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), "Player offline.");
                }
        }

    }

}
