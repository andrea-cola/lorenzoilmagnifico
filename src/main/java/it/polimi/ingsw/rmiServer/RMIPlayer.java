package it.polimi.ingsw.rmiServer;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.rmiClient.RMIClientInterface;

import java.rmi.RemoteException;

/**
 * This class extends {@link ServerPlayer} (server side abstraction of the player).
 * This class is built to communicate with the client.
 */
/*package-local*/ class RMIPlayer extends ServerPlayer {

    /**
     * RMI client interface obtained
     */
    private transient RMIClientInterface rmiClientInterface;

    /**
     * Class constructor.
     * @param rmiClientInterface remote interface to send information to the client.
     */
    /*package-local*/ RMIPlayer(RMIClientInterface rmiClientInterface){
        this.rmiClientInterface = rmiClientInterface;
    }

    public void ping() throws RemoteException{
        rmiClientInterface.ping();
    }

    @Override
    public void sendGameInfo(Game game) throws NetworkException {
        try {
            rmiClientInterface.setGameInfo(game);
        } catch(RemoteException e){
            throw new NetworkException(e);
        }
    }
}
