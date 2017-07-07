package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class contains all data about every player and all methods to handle its operations.
 */
public class Player implements Serializable{

    /**
     * SocketPlayer username.
     */
    private String username;

    /**
     * SocketPlayer color.
     */
    private PlayerColor color;

    /**
     * Player personal board.
     */
    private PersonalBoard personalBoard;

    public Player(){
        this.personalBoard = new PersonalBoard();
    }

    /**
     * Method to set player username.
     * @param username of the player
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Method to get player username.
     * @return
     */
    public String getUsername(){
        return username;
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
     * Print all information about the player.
     * @return string.
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.username.toUpperCase() + "  ###############################################\n");

        stringBuilder.append(personalBoard.toString());

        for(int i = 0; i < this.username.length(); i++)
            stringBuilder.append("#");
        stringBuilder.append("#################################################\n");
        return stringBuilder.toString();
    }

    public String toStringSmall(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.username.toUpperCase() + "  ###############################################\n");
        stringBuilder.append(personalBoard.toStringSmall() + "\n");
        for(int i = 0; i < this.username.length(); i++)
            stringBuilder.append("#");
        stringBuilder.append("#################################################\n");
        return stringBuilder.toString();
    }

}
