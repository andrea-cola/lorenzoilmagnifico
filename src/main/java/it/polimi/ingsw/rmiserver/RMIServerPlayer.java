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

    @Override
    public void sendGameInfo(Game game) throws NetworkException {
        try {
            rmiClientInterface.sendGame(game);
        } catch(RemoteException e){
            throw new NetworkException(e);
        }
    }

    @Override
    public void sendPersonalTile(ArrayList<PersonalBoardTile> personalBoardTiles) throws NetworkException{
        try{
            rmiClientInterface.sendPersonalTiles(personalBoardTiles);
        } catch (RemoteException e){
            throw new NetworkException(e);
        }
    }

    @Override
    public void sendLeaderCards(ArrayList<LeaderCard> leaderCards) throws NetworkException {
        try{
            rmiClientInterface.sendLeaderCards(leaderCards);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void notifyTurnStarted(String username, long seconds) throws NetworkException{
        try{
            rmiClientInterface.notifyTurnStarted(username, seconds);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws NetworkException {
        try{
            rmiClientInterface.sendGameModelUpdate(clientUpdatePacket);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void supportForTheChurch(boolean flag) throws NetworkException {
        try{
            rmiClientInterface.supportForTheChurch(flag);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void notifyEndGame(ServerPlayer[] ranking) throws NetworkException {
        try{
            rmiClientInterface.notifyEndGame(ranking);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }
}
