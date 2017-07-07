package it.polimi.ingsw.rmiserver;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.server.ServerAbstract;
import it.polimi.ingsw.utility.Printer;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.exceptions.ServerException;
import it.polimi.ingsw.rmiclient.RMIClientInterface;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.server.ServerInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class extends {@link ServerAbstract} to create an RMI server.
 */
public class RMIServerAbstract extends ServerAbstract implements RMIServerInterface {

    /**
     * Name of RMI server interface.
     */
    private static final String RMI_SERVER_INTERFACE_NAME = "RMIServerInterface";

    /**
     * RMI registry.
     */
    private Registry registry;

    /**
     * Logged users cache.
     */
    private HashMap<String, Boolean> activeUsers;

    /**
     * Class constructor.
     * @param serverInterface controller of the server.
     */
    public RMIServerAbstract(ServerInterface serverInterface){
        super(serverInterface);
        activeUsers = new HashMap<>();
    }

    /**
     * Runnable used to check periodically if a RMI client is connected.
     */
    private Runnable checkClientsConnection = () -> {
        for (Object o : activeUsers.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            ServerPlayer serverPlayer = getPlayer((String) pair.getKey());
            try {
                serverPlayer.ping();
            } catch (RemoteException e) {
                Printer.printDebugMessage("RMIServerAbstract.java", "Connection with the client is down.");
                getServer().disableUser(serverPlayer);
                activeUsers.remove(pair.getKey());
            }
        }
    };

    /**
     * Start the RMI Server.
     * @param port to use for the communication.
     * @throws IOException if errors occur during starting proceedings.
     */
    public void startServer(int port) throws ServerException{
        registry = createOrLoadRegistry(port);
        publishObject(port);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(checkClientsConnection, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Create a registry if it not already exists.
     * @param port to use.
     * @return a registry.
     * @throws ServerException if error occurs during create/load of the registry.
     */
    private Registry createOrLoadRegistry(int port) throws ServerException{
        try{
            return LocateRegistry.createRegistry(port);
        }catch(RemoteException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "RMI registry already exists.");
        }
        try{
            return LocateRegistry.getRegistry(port);
        }catch(RemoteException e){
            Printer.printDebugMessage(this.getClass().getSimpleName(), "RMI registry cannot be loaded.");
        }
        throw new ServerException("[" + this.getClass().getSimpleName() + "] : RMI interface cannot be loaded.");
    }

    /**
     * Publish the RMI server interface on the registry.
     * @param port to use.
     * @throws ServerException if errors occur.
     */
    private void publishObject(int port) throws ServerException{
        try {
            registry.rebind(RMI_SERVER_INTERFACE_NAME, this);
            UnicastRemoteObject.exportObject(this, port);
        }catch(RemoteException e){
            throw new ServerException("[" + this.getClass().getSimpleName() + "] : Fail during server interface loading. RMI Server is not working.");
        }
    }

    /**
     * Method to get the player who has the identifier passed as parameter.
     * @param username of the player.
     * @return the player.
     */
    private ServerPlayer getPlayer(String username){
        return getServer().getUser(username);
    }

    /**
     * Player login method.
     * @param username provided by the client to login.
     * @param password provided by the client to login.
     * @param rmiPlayer is trying to login.
     * @return a unique identifier of the player.
     * @throws IOException if errors occur during login proceedings.
     */
    @Override
    public void loginPlayer(String username, String password, RMIClientInterface rmiPlayer) throws IOException{
        getServer().loginPlayer(new RMIServerPlayer(rmiPlayer), username, password);
        activeUsers.put(username, true);
    }

    /**
     * Player sign in method.
     * @param username provided by the client to sign in.
     * @param password provided by the client to sign in.
     * @throws IOException
     */
    @Override
    public void signInPlayer(String username, String password) throws IOException {
        getServer().signInPlayer(username, password);
    }

    /**
     * Remote method to join the player to the first game room
     * @param username which is making the request
     * @throws RoomException if the server is not reachable
     */
    @Override
    public void joinFirstRoom(String username) throws RoomException {
        getServer().joinRoom(getPlayer(username));
    }

    /**
     * Create a new room.
     * @param username to get the player.
     * @param maxPlayersNumber allowed in the room.
     * @return the configuration number.
     */
    @Override
    public void createNewRoom(String username, int maxPlayersNumber) throws RoomException{
        getServer().createNewRoom(getPlayer(username), maxPlayersNumber);
    }

    @Override
    public void notifyPersonalBoardChoice(String username, PersonalBoardTile personalBoardTile) {
        getPlayer(username).getPersonalBoard().setPersonalBoardTile(personalBoardTile);
        getPlayer(username).getRoom().onPersonalTilesChosen();
    }

    @Override
    public void notifyLeaderCardChoice(String username, LeaderCard leaderCard) {
        getPlayer(username).getPersonalBoard().setLeaderCard(leaderCard);
        getPlayer(username).getRoom().onLeaderCardChosen();
    }

    @Override
    public void setFamilyMemberInTower(String username, FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex, HashMap<String, Object> playerChoices) throws RemoteException {
        getPlayer(username).getRoom().setFamilyMemberInTower(getPlayer(username), familyMemberColor, servants, towerIndex, cellIndex, playerChoices);
    }

    @Override
    public void setFamilyMemberInCouncil(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException {
        getPlayer(username).getRoom().setFamilyMemberInCouncil(getPlayer(username), familyMemberColor, servants, playerChoices);
    }

    @Override
    public void setFamilyMemberInMarket(String username, FamilyMemberColor familyMemberColor, int servants, int marketIndex, HashMap<String, Object> playerChoices) throws RemoteException {
        getPlayer(username).getRoom().setFamilyMemberInMarket(getPlayer(username), familyMemberColor, servants, marketIndex, playerChoices);
    }

    @Override
    public void setFamilyMemberInHarvestSimple(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException {
        getPlayer(username).getRoom().setFamilyMemberInHarvestSimple(getPlayer(username), familyMemberColor, servants, playerChoices);
    }

    @Override
    public void setFamilyMemberInHarvestExtended(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException {
        getPlayer(username).getRoom().setFamilyMemberInHarvestExtended(getPlayer(username), familyMemberColor, servants, playerChoices);
    }

    @Override
    public void setFamilyMemberInProductionSimple(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException {
        getPlayer(username).getRoom().setFamilyMemberInProductionSimple(getPlayer(username), familyMemberColor,servants, playerChoices);
    }

    @Override
    public void setFamilyMemberInProductionExtended(String username, FamilyMemberColor familyMemberColor, int servants, HashMap<String, Object> playerChoices) throws RemoteException {
        getPlayer(username).getRoom().setFamilyMemberInProductionExtended(getPlayer(username), familyMemberColor, servants, playerChoices);
    }

    @Override
    public void activateLeaderCard(String username, int leaderCardIndex, int servants, HashMap<String, Object> playerChoices) throws RemoteException {
        getPlayer(username).getRoom().activateLeader(getPlayer(username), leaderCardIndex, servants, playerChoices);
    }

    @Override
    public void discardLeader(String username, int leaderCardIndex, HashMap<String, Object> playerChoices) {
        getPlayer(username).getRoom().discardLeader(getPlayer(username), leaderCardIndex, playerChoices);
    }

    @Override
    public void notifySupportForTheChurch(String username, boolean choice) throws RemoteException {
        getPlayer(username).getRoom().onSupportToTheChurchChoice(getPlayer(username), choice);
    }

    @Override
    public void endTurn(String username) {
        getPlayer(username).getRoom().endTurn(getPlayer(username));
    }
}