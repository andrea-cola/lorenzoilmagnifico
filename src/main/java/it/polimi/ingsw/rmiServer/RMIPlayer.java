package it.polimi.ingsw.rmiServer;

import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.rmiClient.RMIClientInterface;

/**
 * This class extends {@link ServerPlayer} (server side abstraction of the player).
 * This class is built to communicate with the client.
 */
/*package-local*/ class RMIPlayer extends ServerPlayer {

    /**
     * RMI client interface obtained
     */
    private transient RMIClientInterface RMIClientInterface;

    /**
     * Class constructor.
     * @param RMIClientInterface remote interface to send information to the client.
     */
    /*package-local*/ RMIPlayer(RMIClientInterface RMIClientInterface){
        this.RMIClientInterface = RMIClientInterface;
    }

}
