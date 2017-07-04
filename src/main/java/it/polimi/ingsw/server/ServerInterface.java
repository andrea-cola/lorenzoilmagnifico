package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;

import java.util.Map;

public interface ServerInterface {

    /**
     * Login the player to server then put username and remote player reference in the user cache (Hashmap).
     *
     * @param player   is trying to login.
     * @param username of the player is trying to login.
     * @param password of the player is trying to login.
     * @throws LoginException if errors occur during login.
     */
    void loginPlayer(ServerPlayer player, String username, String password) throws LoginException;

    /**
     * Sign in the player to server.
     *
     * @param username of the player is trying to sign in.
     * @param password of the player is trying to sign in.
     * @throws LoginException if errors occur during sign in.
     */
    void signInPlayer(String username, String password) throws LoginException;

    /**
     * Method to get remote player reference from the user cache.
     *
     * @param username of the remote player.
     * @return remote player that corresponds to username provided.
     */
    ServerPlayer getUser(String username);

    /**
     * Method used to join a player into a room.
     *
     * @param serverPlayer who would join in a room.
     * @throws RoomException if error occurs.
     */
    void joinRoom(ServerPlayer serverPlayer) throws RoomException;

    /**
     * Create a new room and return the configuration bundle.
     *
     * @param serverPlayer is creating new room.
     * @param maxPlayers   allowed in the room.
     * @return configuration bundle.
     */
    void createNewRoom(ServerPlayer serverPlayer, int maxPlayers) throws RoomException;

    void disableUser(ServerPlayer player);

}