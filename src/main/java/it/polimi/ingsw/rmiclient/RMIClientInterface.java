package it.polimi.ingsw.rmiclient;

import it.polimi.ingsw.model.ClientUpdatePacket;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.server.ServerPlayer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This class represents the RMI client interface used to remote method invocation
 * from the server to the client.
 */
public interface RMIClientInterface extends Remote{

    String ping() throws RemoteException;

    /**
     * Send from the server to the client the game
     * @param game the game
     * @throws RemoteException if error occurs during network communication
     */
    void sendGame(Game game) throws RemoteException;

    /**
     * Send from the server to the client the personal tiles
     * @param personalBoardTileList the personal tiles available
     * @throws RemoteException if error occurs during network communication
     */
    void sendPersonalTiles(List<PersonalBoardTile> personalBoardTileList) throws RemoteException;

    /**
     * Send from the server to the client the leader cards
     * @param leaderCards the leader cards deck
     * @throws RemoteException if error occurs during network communication
     */
    void sendLeaderCards(List<LeaderCard> leaderCards) throws RemoteException;

    /**
     * Notifies the client the turn started
     * @param username the username of the player that has to perform the turn
     * @param seconds time available to perform the turn
     * @throws RemoteException if error occurs during network communication
     */
    void notifyTurnStarted(String username, long seconds) throws RemoteException;

    /**
     * Send from the server to the client the game model update
     * @throws RemoteException if error occurs during network communication
     */
    void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws RemoteException;

    /**
     * Send from the server to the client the support for the church state
     * @param flag to check if the player has supported the church or not
     * @throws RemoteException if error occurs during network communication
     */
    void supportForTheChurch(boolean flag) throws RemoteException;

    /**
     * Notifies the client the game is finished
     * @param ranking the final ranking
     * @throws RemoteException if error occurs during network communication
     */
    void notifyEndGame(ServerPlayer[] ranking) throws RemoteException;

}
