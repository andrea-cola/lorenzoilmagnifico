package it.polimi.ingsw.model;

import java.awt.*;
import java.io.Serializable;
import java.util.Map;

/**
 * This class contains all data about every player and all methods to handle its operations.
 */
public class Player implements Serializable{

    private PersonalBoard personalBoard;

    /**
     * SocketPlayer nickname.
     */
    private String nickname;

    /**
     * SocketPlayer color.
     */
    private PlayerColor color;

    /**
     * Online flag show if the player is online or not.
     */
    private boolean onlineFlag;

    /**
     * Class constructor.
     */
    protected Player(){
        this.personalBoard = new PersonalBoard();

    }

    /**
     * Set player personal board.
     * @param personalBoard
     */
    public void setPersonalBoard(PersonalBoard personalBoard){
        this.personalBoard = personalBoard;
    }

    /**
     * Get player personal board.
     * @return
     */
    public PersonalBoard getPersonalBoard(){
        return this.personalBoard;
    }

    /**
     * Method to set player nickname.
     * @param nickname of the player
     */
    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     * Method to get player nickname.
     * @return
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * Method to set player color.
     * @param color
     */
    public void setColor(PlayerColor color){
        this.color = color;
    }

    /**
     * Method to get player color.
     * @return
     */
    public PlayerColor getColor(){
        return color;
    }

    /**
     * Method to set player online flag.
     * @param onlineFlag
     */
    public void setOnlineFlag(boolean onlineFlag){
        this.onlineFlag = onlineFlag;
    }

    /**
     * Method to get player online flag.
     * @return
     */
    public boolean getOnlineFlag(){
        return onlineFlag;
    }


}
