package it.polimi.ingsw.rmiServer;

import it.polimi.ingsw.server.AbstractPlayer;
import it.polimi.ingsw.rmiClient.RmiClientInterface;

public class RmiPlayer extends AbstractPlayer {

    /**
     * RMI client interface obtained
     */
    private transient RmiClientInterface rmiClientInterface;

    /**
     * Class constructor, allow to get rmiClientInterface from client.
     * @param rmiClientInterface
     */
    /*package-local*/ RmiPlayer(RmiClientInterface rmiClientInterface){
        this.rmiClientInterface = rmiClientInterface;
    }

}
