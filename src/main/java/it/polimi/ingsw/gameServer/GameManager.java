package it.polimi.ingsw.gameServer;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.utility.Configuration;

import java.util.*;

/*package-local*/ class GameManager{

    private static final int INITIAL_COINS = 5;
    private static final int CARD_PER_DECK = 8;

    /**
     * Game instance.
     */
    private Game game;

    /**
     * Development cards yellow deck.
     */
    private ArrayList<DevelopmentCard> yellowDeck;

    /**
     * Development cards green deck.
     */
    private ArrayList<DevelopmentCard> greenDeck;

    /**
     * Development cards blue deck.
     */
    private ArrayList<DevelopmentCard> blueDeck;

    /**
     * Development cards purple deck.
     */
    private ArrayList<DevelopmentCard> purpleDeck;

    /**
     * Leader cards deck.
     */
    private ArrayList<LeaderCard> leaderCards;

    /**
     * Excommunication cards
     */
    private ArrayList<ExcommunicationCard> excommunicationCards;

    /**
     * Player of the game.
     */
    private ArrayList<ServerPlayer> players;

    /**
     * Game configuration.
     */
    private Configuration configuration;

    private InformationChoicesHandler informationChoicesHandler;

    /**
     * Victory points for green cards assigned at the end of the game
     */
    private static int[] victoryPointsForGreenCards;

    /**
     * Victory points for blue cards assigned at the end of the game
     */
    private static int[] victoryPointsForBlueCards;

    /**
     * Class constructor.
     * @param players of the room.
     * @param configuration of the game.
     * @param developmentCards deck.
     */
    /*package-local*/ GameManager(ArrayList<ServerPlayer> players, Configuration configuration, ArrayList<DevelopmentCard> developmentCards, ArrayList<LeaderCard> leaderCards, ArrayList<ExcommunicationCard> excommunicationCards){
        this.players = players;
        this.configuration = configuration;
        this.leaderCards = leaderCards;
        this.excommunicationCards = excommunicationCards;
        this.informationChoicesHandler = new InformationChoicesHandler();
        this.game = new Game(configuration.getMainBoard(), this.players);
        victoryPointsForGreenCards = configuration.getVictoryPointsForGreenCards();
        victoryPointsForBlueCards = configuration.getVictoryPointsForBlueCards();
        setupPlayers();
        setupDecks(developmentCards);
    }

    /**
     * Setup and mix development card decks by color
     * @param deck
     */
    private void setupDecks(ArrayList<DevelopmentCard> deck){
        this.yellowDeck = new ArrayList<>();
        this.greenDeck = new ArrayList<>();
        this.blueDeck = new ArrayList<>();
        this.purpleDeck = new ArrayList<>();

        for (DevelopmentCard card : deck){
            switch (card.getColor()){
                case YELLOW:
                    this.yellowDeck.add(card);
                    Collections.shuffle(this.yellowDeck);
                    break;
                case GREEN:
                    this.greenDeck.add(card);
                    Collections.shuffle(this.greenDeck);
                    break;
                case BLUE:
                    this.blueDeck.add(card);
                    Collections.shuffle(this.blueDeck);
                    break;
                case PURPLE:
                    this.purpleDeck.add(card);
                    Collections.shuffle(this.purpleDeck);
                    break;
            }
        }
        orderList(yellowDeck);
        orderList(greenDeck);
        orderList(blueDeck);
        orderList(purpleDeck);
    }

    /**
     * This method orders card per id.
     * @param list of all card.
     */
    private void orderList(ArrayList<DevelopmentCard> list) {
        Collections.sort(list, new Comparator<DevelopmentCard>() {
            @Override
            public int compare(DevelopmentCard card1, DevelopmentCard card2) {
                return card1.getId() - card2.getId();
            }
        });
    }

    /**
     * Setup development card decks by period
     * @param deck
     * @param period
     * @return
     */
    private ArrayList<DevelopmentCard> deckForPeriod(ArrayList<DevelopmentCard> deck, int period){
        int limitDown = (period - 1) * CARD_PER_DECK;
        int limitTop = (period - 1) * CARD_PER_DECK + CARD_PER_DECK;

        ArrayList<DevelopmentCard> deckPeriod = new ArrayList<>(deck.subList(limitDown, limitTop));
        return deckPeriod;
    }

    /**
     * setup a development card deck by period turn
     * @param deck
     * @param turn
     * @return
     */
    private ArrayList<DevelopmentCard> deckForTurn(ArrayList<DevelopmentCard> deck, int turn){
        int limitDown = (turn - 1) * 4;
        int limitTop = (turn - 1) * 4 + 4;
        Collections.shuffle(deck);
        for(DevelopmentCard developmentCard : deck)
            System.out.println(developmentCard.getId() + " " + developmentCard.getPeriod());
        ArrayList<DevelopmentCard> deckTurn = new ArrayList<>(deck.subList(limitDown, limitTop));
        return deckTurn;
    }

    /**
     * Method to setup towers' cards
     * @param period
     * @param turn
     */
    public void setupMainBoard(int period, int turn){
        //setup towers' cards
        this.game.getMainBoard().setTower(0, deckForTurn(deckForPeriod(this.greenDeck, period), turn));
        this.game.getMainBoard().setTower(1, deckForTurn(deckForPeriod(this.blueDeck, period), turn));
        this.game.getMainBoard().setTower(2, deckForTurn(deckForPeriod(this.yellowDeck, period), turn));
        this.game.getMainBoard().setTower(3, deckForTurn(deckForPeriod(this.purpleDeck, period), turn));

        //setup excommunication cards
        this.game.getMainBoard().setVatican(chooseExcommunicationCardForPeriod(period));
    }

    public void mainboardTurnReset(){
        for(Tower tower : game.getMainBoard().getTowers())
            for(TowerCell towerCell : tower.getTowerCells()){
                towerCell.setPlayerNicknameInTheCell(null);
                towerCell.setDevelopmentCard(null);
            }
        game.getMainBoard().getCouncilPalace().resetFifo();
        for(MarketCell marketCell : game.getMainBoard().getMarket().getMarketCells())
            marketCell.setEmpty();
        game.getMainBoard().getProduction().reset();
        game.getMainBoard().getHarvest().reset();
        game.getMainBoard().getProductionExtended().reset();
        game.getMainBoard().getHarvestExtended().reset();
    }

    public void personalBoardsTurnReset(){
        for(Player player : players){
            player.getPersonalBoard().turnReset();
        }
    }

    /**
     * Method to setup the excommunication cards for the period
     * @return
     */
    private ExcommunicationCard chooseExcommunicationCardForPeriod(int period) {
        Collections.shuffle(this.excommunicationCards);
        for (ExcommunicationCard card : this.excommunicationCards){
            if (card.getPeriod() == period)
                return card;
        }
        return null;
    }

    /**
     * Method to setup player color, personal board and increase coin amount following game rules.
     * Then the player is added to Game players map.
     */
    private void setupPlayers(){
        int i = 0;

        randomPlayerSorting();

        Map<String, PlayerColor> colors = PlayerColor.getHashMap();
        Iterator iterator = colors.entrySet().iterator();

        for(ServerPlayer player : players){
            Map.Entry pair = (Map.Entry) iterator.next();
            player.setColor((PlayerColor)pair.getValue());
            player.setPersonalBoard(createNewPersonalBoard());
            player.getPersonalBoard().getValuables().increase(ResourceType.COIN, INITIAL_COINS + i);
            this.game.getPlayersMap().put(player.getUsername(), player);
            i++;
        }
    }


    /**
     * This method sorts players randomly. This is the game order.
     */
    private void randomPlayerSorting(){
        Collections.shuffle(players);
    }

    /**
     * Get game instance.
     * @return game instance.
     */
    /*package-local*/ void createGameInstance(){
        setupMainBoard(1, 1);
        throwDices();
    }

    /*package-local*/ Game getGameModel(){
        return this.game;
    }

    /**
     * Get array list that represent order player.
     * @return players array list.
     */
    /*package-local*/ ArrayList<ServerPlayer> getStartOrder(){
        return players;
    }

    /**
     * Method to initialize a new personal board.
     * @return a personal board.
     */
    private PersonalBoard createNewPersonalBoard(){
        PersonalBoard personalBoard = new PersonalBoard();
        personalBoard.getValuables().increase(ResourceType.WOOD, 2);
        personalBoard.getValuables().increase(ResourceType.STONE, 2);
        personalBoard.getValuables().increase(ResourceType.SERVANT, 3);
        personalBoard.setGreenCardsMilitaryPointsRequirements(configuration.getPersonalBoard().getGreenCardsMilitaryPointsRequirements());
        FamilyMember familyMember = new FamilyMember();
        personalBoard.setFamilyMember(familyMember);
        return personalBoard;
    }

    /**
     * Throw dices and set value in each personal board.
     */
    private void throwDices(){
        game.getDices().setValues();
        for(Player player : players)
            player.getPersonalBoard().getFamilyMember().setMembers(game.getDices().getValues());
    }

    /*package-local*/ InformationChoicesHandler getInformationChoicesHandler(){
        return this.informationChoicesHandler;
    }

    /*package-local*/ void setInformationChoicesHandler(Map<String, Object> playerChoices){
        this.informationChoicesHandler.setDecisions(playerChoices);
    }


    /**
     * This method calculates the final points for each player at the end of the game
     */
    public void calculateFinalPoints(InformationCallback informationCallback){

        //ciclo sui punti acquisiti durante la partita
        //scomunica id 18
        //calcola chi ha piu punti militari


        //ciclo per assegnare i punti finali
        for (ServerPlayer player : this.players){

            //check if the player has to lose victory points
            int finalVictoryIndexMalus = player.getPersonalBoard().getExcommunicationValues().getFinalPointsIndexMalus().get(PointType.VICTORY);
            if (finalVictoryIndexMalus > 0){
                //the victory points reached during the game
                int gameVictoryPoints = player.getPersonalBoard().getValuables().getPoints().get(PointType.VICTORY);
                //the victory points to lose
                int victoryPointsToLose = gameVictoryPoints/finalVictoryIndexMalus;
                //decrease victory points
                player.getPersonalBoard().getValuables().decrease(PointType.VICTORY, victoryPointsToLose);
            }


            //green cards final points
            if (player.getPersonalBoard().getExcommunicationValues().getDevelopmentCardGetFinalPoints().get(DevelopmentCardColor.GREEN)){
                int numberOfGreenCards = player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).size();
                if (numberOfGreenCards > 0){
                    int finalPointsBonus = victoryPointsForGreenCards[numberOfGreenCards - 1];
                    player.getPersonalBoard().getValuables().increase(PointType.VICTORY, finalPointsBonus);
                }
            }

            //blue cards final points
            if (player.getPersonalBoard().getExcommunicationValues().getDevelopmentCardGetFinalPoints().get(DevelopmentCardColor.BLUE)){
                int numberOfBlueCards = player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).size();
                if (numberOfBlueCards > 0){
                    int finalPointsBonus = victoryPointsForGreenCards[numberOfBlueCards - 1];
                    player.getPersonalBoard().getValuables().increase(PointType.VICTORY, finalPointsBonus);
                }
            }

            //purple cards final points
            if (player.getPersonalBoard().getExcommunicationValues().getDevelopmentCardGetFinalPoints().get(DevelopmentCardColor.PURPLE)){
                for (DevelopmentCard card : player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE)){
                    card.getPermanentEffect().runEffect(player, informationCallback);
                }
            }

            //get victory points from resources
            for (Map.Entry<ResourceType, Integer> entry: player.getPersonalBoard().getValuables().getResources().entrySet()) {
                int victoryPoints = entry.getValue()/5;
                player.getPersonalBoard().getValuables().increase(PointType.VICTORY, victoryPoints);
            }

            //lose victory points from resources
            for (Map.Entry<ResourceType, Integer> entry: player.getPersonalBoard().getValuables().getResources().entrySet()) {
                int finalResourcesIndexMalus = player.getPersonalBoard().getExcommunicationValues().getFinalResourcesIndexMalus().get(entry.getKey());
                if (finalResourcesIndexMalus > 0){
                    int victoryPointsToLose = entry.getValue()/finalResourcesIndexMalus;
                    player.getPersonalBoard().getValuables().decrease(PointType.VICTORY, victoryPointsToLose);
                }
            }

            //lose victory points from military points
            int finalMilitaryPointsIndexMalus = player.getPersonalBoard().getExcommunicationValues().getFinalPointsIndexMalus().get(PointType.MILITARY);
            if (finalMilitaryPointsIndexMalus > 0){
                int victoryPointsToLose = player.getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY)/finalMilitaryPointsIndexMalus;
                player.getPersonalBoard().getValuables().decrease(PointType.VICTORY, victoryPointsToLose);
            }

        }
    }

}
