package it.polimi.ingsw.controller.model;

import java.awt.*;
import java.io.Serializable;

/**
 * This class contains all data about every player and all methods to handle its operations.
 */
public class Player implements Serializable{

    /**
     * Player nickname.
     */
    private String nickname;

    /**
     * Player color.
     */
    private Color color;

    /**
     * Online flag show if the player is online or not.
     */
    private boolean onlineFlag;

    /**
     * Class constructor.
     */
    protected Player(){
        super();
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
     * Method to set player color.
     * @param color
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Method to get player color.
     * @return
     */
    public Color getColor(){
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
