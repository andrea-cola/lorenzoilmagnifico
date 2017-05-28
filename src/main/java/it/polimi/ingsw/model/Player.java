package it.polimi.ingsw.model;

import java.awt.*;
import java.io.Serializable;

/**
 * This class contains all data about every player and all methods to handle its operations.
 */
public class Player implements Serializable{

    private String nickname;

    private PlayerColor color;

    private boolean onlineFlag;

    protected Player(){

    }

    /**
     * Method to set player nickname.
     * @param nickname
     */
    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     * Method to get player nickname.
     * @param nickname
     * @return
     */
    public String getNickname(String nickname){
        return nickname;
    }

    /**
     * Method to get player color.
     * @return
     */
    public PlayerColor getColor(){
        return this.color;
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
