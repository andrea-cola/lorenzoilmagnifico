package it.polimi.ingsw.gameServer;

import it.polimi.ingsw.cli.Debugger;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.server.ServerPlayer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * This class represent a game room.
 */
public class Room {

    /**
     * Minimum number of player before game can start.
     */
    private static final int MIN_PLAYER_TO_START = 2;

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
     * Timer for player move.
     */
    private Timer moveTimer;

    /**
     * Configurator.
     */
    private Configurator roomConfigurator;

    /**
     * Maximum time before game starts.
     */
    private static long maxWaitingTimeBeforeStart;

    /**
     * Player max time to make a move.
     */
    private int maxMoveWaitingTime;

    /**
     * Flag that indicates if the room is open.
     * True -> open. False -> closed.
     */
    private boolean roomOpen;

    /**
     * Instance of the server game.
     */
    private GameManager gameManager = new GameManager();

    /**
     * List of all players that have joined the room.
     */
    private ArrayList<ServerPlayer> players = new ArrayList<ServerPlayer>();

    /**
     * Class constructor. The max number of players is passed as argument
     * in order to allow in future a game with more than usual number of players.
     */
    public Room(ServerPlayer admin, int mPlayerNumber){
        players = new ArrayList<>();
        roomOpen = true;
        maxPlayerNumber = mPlayerNumber;
        players.add(admin);
        // bisogna assegnare i due max time da file.
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
                    startTimer(0L);
                }
                else if(players.size() == MIN_PLAYER_TO_START)
                    startTimer(maxWaitingTimeBeforeStart);
            }
            else
                throw new RoomException();
        }
    }

    /**
     * Method to start the timer. At the end of set time, a task is executed.
     * @param time before execute the task.
     */
    private void startTimer(long time){
        startGameTimer = new Timer();
        startGameTimer.schedule(new RoomGameHandler(), time);
    }

    /**
     * Method to reset start game timer.
     */
    private void resetTimer(){
        startGameTimer.cancel();
        startGameTimer.purge();
    }

    public void configureGame(){
        try {
            ArrayList<DevelopmentCard> developmentCardsDeck = this.roomConfigurator.parseDevelopmentCard();
            this.gameManager.setupDevelopmentCards(developmentCardsDeck);
        }catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * This class is used to manage the room during the game.
     */
    private class RoomGameHandler extends TimerTask {

        /**
         * This method is executed when the time is expired. At first, it closes the room.
         * Then start the game.
         */
        @Override
        public void run(){
            Debugger.printDebugMessage("Closing and starting the room.");
            synchronized (MUTEX){
                roomOpen = false;
            }
            //gameManager = Configurator.getGameNewInstance(players);
        }

    }

}
