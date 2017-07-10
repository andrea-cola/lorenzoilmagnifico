package it.polimi.ingsw.rmiserver;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.model.ClientUpdatePacket;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.rmiclient.RMIClientInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class extends {@link ServerPlayer} (server side abstraction of the player).
 * This class is built to communicate with the client.
 */
/*package-local*/ class RMIServerPlayer extends ServerPlayer {

    /**
     * RMI client interface obtained
     */
    private transient RMIClientInterface rmiClientInterface;

    /**
     * Class constructor.
     * @param rmiClientInterface remote interface to send information to the client.
     */
    /*package-local*/ RMIServerPlayer(RMIClientInterface rmiClientInterface){
        this.rmiClientInterface = rmiClientInterface;
    }


    @Override
    public void ping() throws RemoteException{
        rmiClientInterface.ping();
    }

    /**
     * Send to the client the game info
     * @param game game info
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void sendGameInfo(Game game) throws NetworkException {
        try {
            rmiClientInterface.sendGame(game);
        } catch(RemoteException e){
            throw new NetworkException(e);
        }
    }

    /**
     * Send to the client the personal board tiles
     * @param personalBoardTiles the personal board tiles available
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void sendPersonalTile(ArrayList<PersonalBoardTile> personalBoardTiles) throws NetworkException{
        try{
            rmiClientInterface.sendPersonalTiles(personalBoardTiles);
        } catch (RemoteException e){
            throw new NetworkException(e);
        }
    }

    /**
     * Send to the client the leader cards
     * @param leaderCards the leader cards deck
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void sendLeaderCards(ArrayList<LeaderCard> leaderCards) throws NetworkException {
        try{
            rmiClientInterface.sendLeaderCards(leaderCards);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    /**
     * Notifies to the client that the turn started
     * @param username the username of the player that is performing the turn
     * @param seconds the time available for the player to perform the turn
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifyTurnStarted(String username, long seconds) throws NetworkException{
        try{
            rmiClientInterface.notifyTurnStarted(username, seconds);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    /**
     * Send to the client the game model updates
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws NetworkException {
        try{
            rmiClientInterface.sendGameModelUpdate(clientUpdatePacket);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    /**
     * Send to the client the support for the church answer
     * @param flag this flag is used to check if the player supports the church or not
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void supportForTheChurch(boolean flag) throws NetworkException {
        try{
            rmiClientInterface.supportForTheChurch(flag);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    /**
     * Notifies to the client the end of the game
     * @param ranking the players final ranking
     * @throws NetworkException if error occurs during network communication
     */
    @Override
    public void notifyEndGame(ServerPlayer[] ranking) throws NetworkException {
        try{
            rmiClientInterface.notifyEndGame(ranking);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }
}
