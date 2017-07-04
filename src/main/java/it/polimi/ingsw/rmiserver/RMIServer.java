package it.polimi.ingsw.rmiserver;

import it.polimi.ingsw.model.FamilyMemberColor;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PersonalBoardTile;
import it.polimi.ingsw.utility.Debugger;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.exceptions.ServerException;
import it.polimi.ingsw.rmiclient.RMIClientInterface;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.server.AbstractServer;
import it.polimi.ingsw.server.ServerInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class extends {@link AbstractServer} to create an RMI server.
 */
public class RMIServer extends AbstractServer implements RMIServerInterface {

    /**
     * Name of RMI server interface.
     */
    private static final String RMI_SERVER_INTERFACE_NAME = "RMIServerInterface";

    /**
     * RMI registry.
     */
    private Registry registry;

    /**
     * Client interface.
     */
    private RMIClientInterface rmiClientInterface;

    /**
     * Logged users cache.
     */
    private HashMap<String, String> userCache;

    /**
     * Class constructor.
     * @param serverInterface controller of the server.
     */
    public RMIServer(ServerInterface serverInterface){
        super(serverInterface);
        userCache = new HashMap<>();
    }

    /**
     * Runnable used to check periodically if a RMI client is connected.
     */
    private Runnable checkClientsConnection = () -> {
        Iterator iterator = userCache.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry pair = (Map.Entry) iterator.next();
            ServerPlayer serverPlayer = getPlayer(pair.getKey().toString());
            try {
                serverPlayer.ping();
            } catch(RemoteException e){
                Debugger.printDebugMessage("RMIServer.java", "Connection with the client is down.");
                getServer().disableUser(serverPlayer);
                userCache.remove(pair.getKey());
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
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "RMI registry already exists.");
        }
        try{
            return LocateRegistry.getRegistry(port);
        }catch(RemoteException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "RMI registry cannot be loaded.");
        }
        throw new ServerException("[" + this.getClass().getSimpleName() + "] : RMI interface cannot be loaded.");
    }

    /**
     * Publish the RMI server interface on the registry.
     * @param port to use.
     * @throws IOException if errors occur.
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
     * @param uniqueID of the player.
     * @return the player.
     */
    public ServerPlayer getPlayer(String uniqueID){
        return getServer().getUser(userCache.get(uniqueID));
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
    public String loginPlayer(String username, String password, RMIClientInterface rmiPlayer) throws IOException{
        getServer().loginPlayer(new RMIPlayer(rmiPlayer), username, password);
        String uniqueID = UUID.randomUUID().toString();
        userCache.put(uniqueID, username);
        return uniqueID;
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
     * @param id to get the player from the cache.
     * @param maxPlayersNumber allowed in the room.
     * @return the configuration number.
     */
    @Override
    public void createNewRoom(String id, int maxPlayersNumber) throws RoomException{
        getServer().createNewRoom(getPlayer(id), maxPlayersNumber);
    }

    @Override
    public void notifyPersonalBoardChoice(String playerID, PersonalBoardTile personalBoardTile) {
        getPlayer(playerID).getPersonalBoard().setPersonalBoardTile(personalBoardTile);
        getPlayer(playerID).getRoom().onPersonalTilesChosen();
    }

    @Override
    public void notifyLeaderCardChoice(String playerID, LeaderCard leaderCard) {
        getPlayer(playerID).getPersonalBoard().setLeaderCard(leaderCard);
        getPlayer(playerID).getRoom().onLeaderCardChosen();
    }

    @Override
    public void setFamilyMemberInTower(String playerID, FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex, Map<String, Object> playerChoices) throws RemoteException {
        getPlayer(playerID).getRoom().setFamilyMemberInTower(getPlayer(playerID), familyMemberColor, servants, towerIndex, cellIndex, playerChoices);
    }

    @Override
    public void setFamilyMemberInCouncil(String playerID, FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws RemoteException {
        getPlayer(playerID).getRoom().setFamilyMemberInCouncil(getPlayer(playerID), familyMemberColor, servants, playerChoices);
    }

    @Override
    public void setFamilyMemberInMarket(String playerID, FamilyMemberColor familyMemberColor, int servants, int marketIndex, Map<String, Object> playerChoices) throws RemoteException {
        getPlayer(playerID).getRoom().setFamilyMemberInMarket(getPlayer(playerID), familyMemberColor, servants, marketIndex, playerChoices);
    }

    @Override
    public void setFamilyMemberInHarvestSimple(String playerID, FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws RemoteException {
        getPlayer(playerID).getRoom().setFamilyMemberInHarvestSimple(getPlayer(playerID), familyMemberColor, servants, playerChoices);
    }

    @Override
    public void setFamilyMemberInHarvestExtended(String playerID, FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws RemoteException {
        getPlayer(playerID).getRoom().setFamilyMemberInHarvestExtended(getPlayer(playerID), familyMemberColor, servants, playerChoices);
    }

    @Override
    public void setFamilyMemberInProductionSimple(String playerID, FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws RemoteException {
        getPlayer(playerID).getRoom().setFamilyMemberInProductionSimple(getPlayer(playerID), familyMemberColor,servants, playerChoices);
    }

    @Override
    public void setFamilyMemberInProductionExtended(String playerID, FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws RemoteException {
        getPlayer(playerID).getRoom().setFamilyMemberInProductionExtended(getPlayer(playerID), familyMemberColor, servants, playerChoices);
    }

    @Override
    public void activateLeaderCard(String playerID, int leaderCardIndex, int servants, Map<String, Object> playerChoices) throws RemoteException {
        getPlayer(playerID).getRoom().activateLeader(getPlayer(playerID), leaderCardIndex, servants, playerChoices);
    }

    @Override
    public void discardLeader(String playerID, int leaderCardIndex, Map<String, Object> playerChoices) {
        getPlayer(playerID).getRoom().discardLeader(getPlayer(playerID), leaderCardIndex, playerChoices);
    }

    @Override
    public void notifySupportForTheChurch(String playerID, boolean choice) throws RemoteException {
        getPlayer(playerID).getRoom().onSupportToTheChurchChoice(getPlayer(playerID), choice);
    }

    @Override
    public void endTurn(String playerID) {
        getPlayer(playerID).getRoom().endTurn(getPlayer(playerID));
    }
}