package it.polimi.ingsw;

public class RmiPlayer extends AbstractPlayer {

    /**
     * RMI client interface obtained
     */
    private transient RmiClientInterface rmiClientInterface;

    /**
     * Class constructor, allow to get rmiClientInterface from client.
     * @param rmiClientInterface
     */
    public RmiPlayer(RmiClientInterface rmiClientInterface){
        this.rmiClientInterface = rmiClientInterface;
    }

    /**
     * Override Abstract Player methods
     */
}
