package it.polimi.ingsw.utility;

import it.polimi.ingsw.model.MainBoard;
import it.polimi.ingsw.model.PersonalBoard;
import it.polimi.ingsw.model.PersonalBoardTile;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents the configuration packet. Built by Configurator
 * and use by server and client to configure the game.
 */
public class Configuration implements Serializable{

    /**
     * Time before game starts.
     */
    private long waitingTime;

    /**
     * Time during which the user has to make his move.
     */
    private long moveWaitingTime;

    /**
     * Main board parsed from file.
     */
    private MainBoard mainBoard;

    /**
     * Personal board parsed from file.
     */
    private PersonalBoard personalBoard;

    /**
     * Array of all possibile configuration fo personal board tile.
     */
    private List<PersonalBoardTile> personalBoardTiles;

    /**
     * Class constructor.
     * @param waitingTime before start game.
     * @param moveWaitingTime before change turn.
     */
    public Configuration(long waitingTime, long moveWaitingTime,
                         MainBoard mainBoard, PersonalBoard personalBoard,
                         List<PersonalBoardTile> personalBoardTiles) {
        this.waitingTime = waitingTime;
        this.moveWaitingTime = moveWaitingTime;
        this.mainBoard = mainBoard;
        this.personalBoard = personalBoard;
        this.personalBoardTiles = personalBoardTiles;
    }

    /**
     * Get the max waiting time before game stars.
     * @return long waiting time.
     */
    public long getWaitingTime(){
        return this.waitingTime;
    }

    /**
     * Get the max waiting time during which the user has to make his move.
     * @return long waiting time.
     */
    public long getMoveWaitingTime(){
        return this.moveWaitingTime;
    }

    /**
     * Return main board from configuration file.
     * @return main board.
     */
    public MainBoard getMainBoard(){
        return this.mainBoard;
    }

    /**
     * Get personal board model from configuration file.
     * @return personal board.
     */
    public PersonalBoard getPersonalBoard(){
        return this.personalBoard;
    }

    /**
     * Get all possibile personal board setup from configuration file.
     * @return list of possibile personal board.
     */
    public List<PersonalBoardTile> getPersonalBoardTiles(){
        return this.personalBoardTiles;
    }
}