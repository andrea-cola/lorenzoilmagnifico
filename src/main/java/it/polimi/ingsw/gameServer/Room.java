package it.polimi.ingsw.gameServer;

import it.polimi.ingsw.exceptions.GameException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utility.Configuration;
import it.polimi.ingsw.utility.Debugger;
import it.polimi.ingsw.exceptions.RoomException;
import it.polimi.ingsw.server.ServerPlayer;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * This class represent a game room.
 */
public class Room {

    private static final int MIN_PLAYER_TO_START = 2;
    private static final long IMMEDIATE_START_TIME = 0L;
    private static final int LEADER_CARD_PER_PLAYER = 4;
    private static final int AGES = 3;
    private static final int TURNS_PER_AGE = 2;

    /**
     * Room identifier.
     */
    private int roomID;

    /**
     * Mutex object to synchronize room access.
     */
    private static final Object MUTEX = new Object();

    /**
     * Max player allowed in the room.
     */
    private final int maxPlayerNumber;

    /**
     * Timer used to run a task after a specified amount of time.
     */
    private Timer startGameTimer;

    /**
     * Configurator.
     */
    private Configuration roomConfiguration;

    /**
     * Maximum time before game starts.
     */
    private long maxWaitingTimeBeforeStart;

    /**
     * Player max time to make a move.
     */
    private long maxMoveWaitingTime;

    /**
     * Flag that indicates if the room is open.
     * True -> open. False -> closed.
     */
    private boolean roomOpen;

    /**
     * Instance of the server game.
     */
    private GameManager gameManager;

    /**
     * List of all players that have joined the room.
     */
    private ArrayList<ServerPlayer> players;

    /**
     * Countdown latch used as semaphore.
     */
    private CountDownLatch countDownLatch;

    private PlayerTurn playerTurn;

    private ClientUpdatePacket clientUpdatePacket;

    /**
     * Class constructor.
     * Set max number of player in the room.
     * Set the room in open state.
     * Add player in the list.
     * Set room configuration.
     */
    public Room(int id, ServerPlayer serverPlayer, int number, Configuration configuration){
        players = new ArrayList<>();
        roomOpen = true;
        maxPlayerNumber = number;
        players.add(serverPlayer);
        configureGame(configuration);
        roomID = id;
    }

    /**
     * Get room id.
     * @return room id.
     */
    public int getRoomID(){
        return this.roomID;
    }

    /**
     * Get the configuration from the server and configure the room.
     * @param configuration bundle.
     */
    private void configureGame(Configuration configuration){
        this.roomConfiguration = configuration;
        maxWaitingTimeBeforeStart = configuration.getWaitingTime();
        maxMoveWaitingTime = configuration.getMoveWaitingTime();
    }

    /**
     * Method to start the timer. At the end of set time, a task is executed.
     * @param time before run the task.
     */
    private void startTimer(long time){
        startGameTimer = new Timer();
        startGameTimer.schedule(new GameHandler(), time);
    }

    /**
     * Method to reset start game timer.
     */
    private void resetTimer(){
        if(startGameTimer != null) {
            startGameTimer.cancel();
            startGameTimer.purge();
        }
    }

    public void rejoinRoom(ServerPlayer serverPlayer){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getUsername().equals(serverPlayer.getUsername()))
                players.set(i, serverPlayer);
        }
        Debugger.printDebugMessage(serverPlayer.getUsername() + " has rejoined the previous room.");
        //serverPlayer.sendGame(gameManager.game);
        //__________________________________
    }

    public void setFamilyMemberInTower(ServerPlayer player, FamilyMemberColor familyMemberColor, int servants,
                                       int towerIndex, int cellIndex, Map<String, Object> playerChoices){
        gameManager.setInformationChoicesHandler(playerChoices);
        try {
            Game game = gameManager.getGameModel();
            game.pickupDevelopmentCardFromTower(player, familyMemberColor, servants, towerIndex, cellIndex, gameManager.getInformationChoicesHandler());
            String message = player.getUsername() + " set a family member in " + game.getMainBoard().getTower(towerIndex).getColor().toString().toLowerCase()
                    + " tower and picked up " + game.getMainBoard().getTower(towerIndex).getTowerCell(cellIndex).getDevelopmentCard().getName() + ".";
            clientUpdatePacket = new ClientUpdatePacket(game, message);
        } catch (GameException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Cannot set tower in the same way of the client.");
        }
    }

    public void setFamilyMemberInCouncil(ServerPlayer player, FamilyMemberColor familyMemberColor, int servants,
                                         Map<String, Object> playerChoices){
        gameManager.setInformationChoicesHandler(playerChoices);
        try {
            Game game = gameManager.getGameModel();
            game.placeFamilyMemberInsideCouncilPalace(player, familyMemberColor, servants, gameManager.getInformationChoicesHandler());
            String message = player.getUsername() + " set a family member in council palace and get one of its privileges.";
            clientUpdatePacket = new ClientUpdatePacket(game, message);
        } catch (GameException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Cannot set council palace in the same way of the client.");
        }
    }

    public void setFamilyMemberInMarket(ServerPlayer player, FamilyMemberColor familyMemberColor, int servants,
                                        int marketCell, Map<String, Object> playerChoices){
        gameManager.setInformationChoicesHandler(playerChoices);
        try{
            gameManager.getGameModel().placeFamilyMemberInsideMarket(player, familyMemberColor, servants, marketCell, gameManager.getInformationChoicesHandler());
            String message = player.getUsername() + " set a family member in market cell #" + marketCell + " and get its benefits";
            clientUpdatePacket = new ClientUpdatePacket(gameManager.getGameModel(), message);
        } catch (GameException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Cannot set market in the same way of the client.");
        }
    }

    public void setFamilyMemberInHarvestSimple(ServerPlayer player, FamilyMemberColor familyMemberColor, int servants,
                                               Map<String, Object> playerChoices){
        gameManager.setInformationChoicesHandler(playerChoices);
        try{
            gameManager.getGameModel().placeFamilyMemberInsideHarvestSimpleSpace(player, familyMemberColor, servants, gameManager.getInformationChoicesHandler());
            String message = player.getUsername() + " set a family member in harvest area simple.";
            clientUpdatePacket = new ClientUpdatePacket(gameManager.getGameModel(), message);
        } catch (GameException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Cannot set harvest simple area in the same way of the client.");
        }
    }

    public void setFamilyMemberInProductionSimple(ServerPlayer player, FamilyMemberColor familyMemberColor, int servants,
                                                  Map<String, Object> playerChoices){
        gameManager.setInformationChoicesHandler(playerChoices);
        try{
            gameManager.getGameModel().placeFamilyMemberInsideProductionSimpleSpace(player, familyMemberColor, servants, gameManager.getInformationChoicesHandler());
            String message = player.getUsername() + " set a family member in production area simple.";
            clientUpdatePacket = new ClientUpdatePacket(gameManager.getGameModel(), message);
        } catch (GameException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Cannot set production simple area in the same way of the client.");
        }
    }

    public void setFamilyMemberInHarvestExtended(ServerPlayer player, FamilyMemberColor familyMemberColor, int servants,
                                                 Map<String, Object> playerChoices){
        gameManager.setInformationChoicesHandler(playerChoices);
        try{
            gameManager.getGameModel().placeFamilyMemberInsideHarvestExtendedSpace(player, familyMemberColor, servants, gameManager.getInformationChoicesHandler());
            String message = player.getUsername() + " set a family member in harvest area extended.";
            clientUpdatePacket = new ClientUpdatePacket(gameManager.getGameModel(), message);
        } catch (GameException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Cannot set harvest extended area in the same way of the client.");
        }
    }

    public void setFamilyMemberInProductionExtended(ServerPlayer player, FamilyMemberColor familyMemberColor, int servants,
                                                    Map<String, Object> playerChoices){
        gameManager.setInformationChoicesHandler(playerChoices);
        try{
            gameManager.getGameModel().placeFamilyMemberInsideProductionExtendedSpace(player, familyMemberColor, servants, gameManager.getInformationChoicesHandler());
            String message = player.getUsername() + " set a family member in production area extended.";
            clientUpdatePacket = new ClientUpdatePacket(gameManager.getGameModel(), message);
        } catch (GameException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Cannot set production extended area in the same way of the client.");
        }
    }

    /**
     * Join a player in the room. Using MUTEX concurrent access is managed.
     * @param serverPlayer who would access the room.
     * @throws RoomException if the room is full or closed.
     */
    public void joinRoom(ServerPlayer serverPlayer) throws RoomException{
        synchronized (MUTEX){
            if(roomOpen){
                players.add(serverPlayer);
                if(players.size() == maxPlayerNumber){
                    roomOpen = false;
                    resetTimer();
                    startTimer(IMMEDIATE_START_TIME);
                    Debugger.printDebugMessage("Room #" + this.roomID + " starts in " + IMMEDIATE_START_TIME + " seconds.");
                }
                else if(players.size() == MIN_PLAYER_TO_START) {
                    startTimer(maxWaitingTimeBeforeStart);
                    Debugger.printDebugMessage("Room #" + this.roomID + " starts in " + maxWaitingTimeBeforeStart/1000 + " seconds.");
                }
            }
            else
                throw new RoomException();
        }
    }

    public boolean userAlreadyJoined(ServerPlayer serverPlayer){
        for(ServerPlayer player : players)
            if(player.getUsername().equals(serverPlayer.getUsername()))
                return true;
        return false;
    }

    public void onPersonalTilesChosen(){
        countDownLatch.countDown();
    }

    public void onLeaderCardChosen() {
        countDownLatch.countDown();
    }

    public void endTurn(ServerPlayer player) {
        if(playerTurn.currentPlayer().getUsername().equals(player.getUsername()))
            playerTurn.stopTimer();
    }

    private void updateAllClients(Player player){
        if(player != null && clientUpdatePacket != null){
            for(ServerPlayer serverPlayer : players){
                if(!serverPlayer.getUsername().equals(player.getUsername())){
                    try {
                        serverPlayer.sendGameModelUpdate(clientUpdatePacket);
                    } catch (NetworkException e){
                        Debugger.printDebugMessage(this.getClass().getSimpleName(), player.getUsername() + " won't receive updates this turn.");
                    }
                }
            }
        }
    }

    /**
     * This class is used to manage the room during the game.
     */
    private class GameHandler extends TimerTask {

        Player previousPlayer;
        /**
         * This method is executed when the time is expired. At first, it closes the room.
         * Then start the game.
         */
        @Override
        public void run(){
            previousPlayer = null;
            setupBeforeStartGame();
            sendGameModel();
            Debugger.printDebugMessage("Game starts in room #" + getRoomID());
            startGameSession();
        }

        private void startGameSession(){
            for(int age = 1; age <= AGES; age++){
                for(int turn = 1; turn <= TURNS_PER_AGE; turn++){
                    turnSetup(age, turn);
                    checkExcommunication(age, turn);
                    for(int move = 1; move <= FamilyMemberColor.values().length; move++) {
                        System.out.println("Ages: " + age + " Turn: " + turn + " move: " + move);
                        for (ServerPlayer player : players) {
                            updateAllClients(previousPlayer);
                            Debugger.printStandardMessage("Turn of: " + player.getUsername());
                            playerTurn = new PlayerTurn(player);
                            notifyTurnStarted(player);
                            playerTurn.startTimer(maxMoveWaitingTime);
                            previousPlayer = player;
                        }
                    }
                }
            }
        }

        private void turnSetup(int age, int turn){
            if(!(turn == 1) && !(age == 1)) {
                getNewOrder();
                gameManager.personalBoardsTurnReset();
                gameManager.mainboardTurnReset();
                gameManager.setupMainBoard(age, turn);
            }
        }

        private void checkExcommunication(int age, int turn){
            if(turn % 2 == 0)
                System.out.println("Dobbiamo controllare la scomunica.");
        }

        /**
         * Get fifo list from council palace and set new order.
         */
        private void getNewOrder(){
            List<Player> p = new ArrayList<>(gameManager.getGameModel().getMainBoard().getCouncilPalace().getNewOrder());
            ArrayList<ServerPlayer> newOrder = new ArrayList<>();
            for(Player player : p){
                for(ServerPlayer serverPlayer : players){
                    if(serverPlayer.getUsername().equals(player.getUsername()))
                        newOrder.add(serverPlayer);
                }
            }
            for(ServerPlayer serverPlayer : players)
                if(!newOrder.contains(serverPlayer))
                    newOrder.add(serverPlayer);
            players = newOrder;
        }

        private void notifyTurnStarted(ServerPlayer player){
            for(ServerPlayer p : players)
                try {
                    p.notifyTurnStarted(player.getUsername(), maxMoveWaitingTime);
                } catch (NetworkException e){
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), "Player offline.");
                }
        }

        /**
         * Close the room and get the game manager from configurator.
         */
        private void setupBeforeStartGame(){
            synchronized (MUTEX){
                roomOpen = false;
            }
            Debugger.printDebugMessage("[Room #" + roomID + "] : Room closed.");

            gameManager = Configurator.buildAndGetGame(players, roomConfiguration);
            players = gameManager.getStartOrder();

            personalTilesChoice(roomConfiguration.getPersonalBoardTiles());
            leaderCardsChoice(Configurator.getLeaderCards());

            gameManager.createGameInstance();
        }

        private void personalTilesChoice(ArrayList<PersonalBoardTile> personalBoardTileList){
            ArrayList<PersonalBoardTile> personalBoardtiles = new ArrayList<>();
            personalBoardtiles.addAll(personalBoardTileList);

            for(int i = players.size() - 1; i >= 0; i--) {
                countDownLatch = new CountDownLatch(1);
                try {
                    players.get(i).sendPersonalTile(personalBoardtiles);
                    countDownLatch.await();
                    for(int j = 0; j < personalBoardtiles.size(); j++)
                        if (personalBoardtiles.get(j).getPersonalBoardID() == players.get(i).getPersonalBoard().getPersonalBoardTile().getPersonalBoardID())
                            personalBoardtiles.remove(j);
                } catch (NetworkException | InterruptedException e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), "Player offline.");
                }
            }
        }

        private void leaderCardsChoice(ArrayList<LeaderCard> leaderCards){
            countDownLatch = new CountDownLatch(players.size());
            ArrayList<ServerPlayer> playersOrder = new ArrayList<>();
            playersOrder.addAll(players);
            ArrayList<LeaderCard> cards = new ArrayList<>();
            cards.addAll(leaderCards);
            Collections.shuffle(cards);
            cards = new ArrayList<>(cards.subList(0, (players.size() * LEADER_CARD_PER_PLAYER)));

            for(int i = 0; i < LEADER_CARD_PER_PLAYER; i++) {
                try {
                    sendLeaderCards(cards, playersOrder);
                    countDownLatch.await();
                    removeChosenLeaderCards(cards);
                } catch (NetworkException | InterruptedException e) {
                    e.printStackTrace();
                }
                playersOrder.add(playersOrder.remove(0));
                countDownLatch = new CountDownLatch(players.size());
            }
        }

        private void removeChosenLeaderCards(ArrayList<LeaderCard> leaderCards){
            for(ServerPlayer player : players){
                int i = player.getPersonalBoard().getLeaderCards().size() - 1;
                LeaderCard lc = player.getPersonalBoard().getLeaderCards().get(i);
                for(int j = 0; j < leaderCards.size(); j++)
                    if(leaderCards.get(j).getLeaderCardName().equals(lc.getLeaderCardName()))
                        leaderCards.remove(j);
            }
        }

        private void sendLeaderCards(List<LeaderCard> leaderCards, List<ServerPlayer> serverPlayers) throws NetworkException{
            int cardNumberPerPlayer = leaderCards.size() / serverPlayers.size();
            int index = 0;

            for(ServerPlayer player : serverPlayers){
                player.sendLeaderCards(new ArrayList<>(leaderCards.subList(index * cardNumberPerPlayer, index * cardNumberPerPlayer + cardNumberPerPlayer)));
                index++;
            }
        }

        private void sendGameModel(){
            for(ServerPlayer player : players) {
                try {
                    player.sendGameInfo(gameManager.getGameModel());
                } catch (NetworkException e) {
                    Debugger.printDebugMessage(this.getClass().getSimpleName(), "Player offline.");
                }
            }
        }

    }

}