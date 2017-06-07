package it.polimi.ingsw.rmiServer;

import it.polimi.ingsw.server.AbstractPlayer;
import it.polimi.ingsw.rmiClient.RMIClientInterface;

/*package-local*/ class RMIPlayer extends AbstractPlayer {

    /**
     * RMI client interface obtained
     */
    private transient RMIClientInterface RMIClientInterface;

    /**
     * Class constructor, allow to get RMIClientInterface from client.
     * @param RMIClientInterface
     */
    /*package-local*/ RMIPlayer(RMIClientInterface RMIClientInterface){
        this.RMIClientInterface = RMIClientInterface;
    }

}
