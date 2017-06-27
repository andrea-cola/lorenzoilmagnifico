package it.polimi.ingsw.rmiClient;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.client.AbstractClient;
import it.polimi.ingsw.client.ClientInterface;
import it.polimi.ingsw.exceptions.ConnectionException;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.rmiServer.RMIServerInterface;

/**
 * This class extends {@link AbstractClient} class to create a network connection based on RMI.
 */
public class RMIClient extends AbstractClient implements RMIClientInterface {

    /**
     * Name of RMI client interface.
     */
    private static final String RMI_CLIENT_INTERFACE_NAME = "RMIClientInterface";

    /**
     * Name of RMI server interface.
     */
    private static final String RMI_SERVER_INTERFACE_NAME = "RMIServerInterface";

    /**
     * RMI server interface.
     */
    private RMIServerInterface server;

    /**
     * Unique player id.
     */
    private String playerID;

    /**
     * Class constructor.
     * @param clientInterface client controller.
     * @param address of the server.
     * @param port of the server.
     */
    public RMIClient(ClientInterface clientInterface, String address, int port){
        super(clientInterface, address, port);
    }

    /**
     * This method locates the RMI registry created or loaded by the RMI server,
     * then gets the RMI server interface reference and in the end RMI client
     * interface is published on the registry.
     * @throws ConnectionException if errors occur during connection initialization.
     */
    @Override
    public void connectToServer() throws ConnectionException{
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(getAddress(), getPort());
            server = (RMIServerInterface) registry.lookup(RMI_SERVER_INTERFACE_NAME);
            UnicastRemoteObject.exportObject(this, 0);
            registry.rebind(RMI_CLIENT_INTERFACE_NAME, this);
        }catch(RemoteException | NotBoundException e){
            throw new ConnectionException(e);
        }
    }

    /**
     * Method to loginPlayer user on a server.
     * @param username for the login.
     * @param password for the login.
     * @throws NetworkException if errors occur during login.
     */
    @Override
    public void loginPlayer(String username, String password) throws NetworkException{
        try {
            playerID = server.loginPlayer(username, password, this);
        } catch(LoginException e) {
            throw e;
        } catch(IOException e){
            e.printStackTrace();
            throw new NetworkException();
        }
    }

    /**
     * Method to sign in a user on a server.
     * @param username for the sign in.
     * @param password for the sign in.
     * @throws NetworkException if errors occur during sign in.
     */
    @Override
    public void signInPlayer(String username, String password) throws NetworkException{
        try {
            server.signInPlayer(username, password);
        } catch(IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Method to join the first game room
     * @throws NetworkException
     */
    @Override
    public void joinRoom() throws RoomException, NetworkException {
        try{
            server.joinFirstRoom(playerID);
        }catch(RoomException e) {
            throw new RoomException();
        }catch(IOException e){
            throw new NetworkException();
        }
    }

    /**
     * Try to create a new room on server side.
     * @param maxPlayersNumber that should be accepted in this new room.
     * @throws NetworkException if server is not reachable or something went wrong.
     * @return configuration bundle that contains all default configurations.
     */
    @Override
    public void createNewRoom(int maxPlayersNumber) throws RoomException, NetworkException {
        try {
            server.createNewRoom(playerID, maxPlayersNumber);
        } catch (RoomException e){
            throw e;
        }catch (IOException e){
            throw new NetworkException();
        }
    }

    @Override
    public void sendPersonalTiles(List<PersonalBoardTile> personalBoardTileList) throws RemoteException {
        getClient().choosePersonalBoardTile(personalBoardTileList);
    }

    @Override
    public void notifyPersonalBoardTileChoice(PersonalBoardTile personalBoardTile) throws NetworkException{
        try {
            server.notifyPersonalBoardChoice(playerID, personalBoardTile);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void sendLeaderCards(List<LeaderCard> leaderCards) throws RemoteException {
        getClient().chooseLeaderCards(leaderCards);
    }

    @Override
    public void notifyLeaderCardChoice(LeaderCard leaderCard) throws NetworkException {
        try {
            server.notifyLeaderCardChoice(this.playerID, leaderCard);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void notifySetFamilyMemberInTower(FamilyMemberColor familyMemberColor, int servants, int towerIndex, int cellIndex, Map<String, Object> playerChoices) throws NetworkException {
        try {
            server.setFamilyMemberInTower(playerID, familyMemberColor, servants, towerIndex, cellIndex, playerChoices);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void notifySetFamilyMemberInCouncil(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws NetworkException {
        try {
            server.setFamilyMemberInCouncil(playerID, familyMemberColor, servants, playerChoices);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void notifySetFamilyMemberInMarket(FamilyMemberColor familyMemberColor, int servants, int marketIndex, Map<String, Object> playerChoices) throws NetworkException {
        try {
            server.setFamilyMemberInMarket(playerID, familyMemberColor, servants, marketIndex, playerChoices);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void notifySetFamilyMemberInHarvestSimple(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws NetworkException {
        try {
            server.setFamilyMemberInHarvestSimple(playerID, familyMemberColor, servants, playerChoices);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void notifySetFamilyMemberInProductionSimple(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws NetworkException {
        try {
            server.setFamilyMemberInProductionSimple(playerID, familyMemberColor, servants, playerChoices);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void notifySetFamilyMemberInHarvestExtended(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws NetworkException {
        try {
            server.setFamilyMemberInHarvestExtended(playerID, familyMemberColor, servants, playerChoices);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void notifySetFamilyMemberInProductionExtended(FamilyMemberColor familyMemberColor, int servants, Map<String, Object> playerChoices) throws NetworkException {
        try {
            server.setFamilyMemberInProductionExtended(playerID, familyMemberColor, servants, playerChoices);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void notifyActivateLeader(int leaderCardIndex, Map<String, Object> playerChoices) throws NetworkException {
        try {
            server.activateLeaderCard(playerID, leaderCardIndex, playerChoices);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void notifyDiscardLeader(int leaderCardIndex, Map<String, Object> playerChoices) throws NetworkException {
        try {
            server.discardLeader(playerID, leaderCardIndex, playerChoices);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }

    @Override
    public void sendGame(Game game) throws RemoteException {
        getClient().setGameModel(game);
    }

    @Override
    public String ping() throws RemoteException {
        return playerID;
    }

    @Override
    public void notifyTurnStarted(String username, long seconds) throws RemoteException {
        getClient().notifyTurnStarted(username, seconds);
    }

    @Override
    public void sendGameModelUpdate(ClientUpdatePacket clientUpdatePacket) throws RemoteException {
        getClient().notifyModelUpdate(clientUpdatePacket);
    }

    @Override
    public void endTurn() throws NetworkException{
        try {
            server.endTurn(this.playerID);
        } catch (RemoteException e){
            throw new NetworkException();
        }
    }
}