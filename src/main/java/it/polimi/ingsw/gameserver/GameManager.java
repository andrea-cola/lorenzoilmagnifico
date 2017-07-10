package it.polimi.ingsw.gameserver;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.utility.Configuration;

import java.util.*;

/*package-local*/ class GameManager{

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

    /**
     * Callback object.
     */
    private InformationChoicesHandler informationChoicesHandler;

    /**
     * Victory points for green cards assigned at the end of the game
     */
    private int[] victoryPointsForGreenCards;

    /**
     * Victory points for blue cards assigned at the end of the game
     */
    private int[] victoryPointsForBlueCards;

    /**
     * Victory points for faith points assigned at the end of the game
     */
    private int[] victoryPointsBonusForFaith;

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
        setupFinalPoints();
        setupPlayers();
        setupDecks(developmentCards);
    }

    /*package-local*/ List<LeaderCard> getLeaderCards(){
        return this.leaderCards;
    }

    /**
     * Method to setup the arrays with final points amount for green cards, blue cards and faith
     */
    private void setupFinalPoints(){
        this.victoryPointsForGreenCards = this.configuration.getVictoryPointsForGreenCards();
        this.victoryPointsForBlueCards = this.configuration.getVictoryPointsForBlueCards();
        this.victoryPointsBonusForFaith = this.configuration.getVictoryPointsBonusForFaith();
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
        orderDevelopmentCards(yellowDeck);
        orderDevelopmentCards(greenDeck);
        orderDevelopmentCards(blueDeck);
        orderDevelopmentCards(purpleDeck);
    }

    /**
     * This method orders card per id.
     * @param list of all card.
     */
    private void orderDevelopmentCards(ArrayList<DevelopmentCard> list) {
        Collections.sort(list, Comparator.comparingInt(DevelopmentCard::getId));
        Collections.shuffle(list.subList(0, 8));
        Collections.shuffle(list.subList(8, 16));
        Collections.shuffle(list.subList(16, 24));
    }

    /**
     * Setup development card decks by period
     * @param deck the cards deck
     * @param period the current period
     * @return a deck containing the cards of the period
     */
    private ArrayList<DevelopmentCard> deckForPeriod(ArrayList<DevelopmentCard> deck, int period){
        int limitDown = (period - 1) * CARD_PER_DECK;
        int limitTop = (period - 1) * CARD_PER_DECK + CARD_PER_DECK;
        return new ArrayList<>(deck.subList(limitDown, limitTop));
    }

    /**
     * setup a development card deck by period turn
     * @param deck dack containing all the development cards
     * @param turn the current turn
     * @return a deck containing the cards of the turn
     */
    private ArrayList<DevelopmentCard> deckForTurn(ArrayList<DevelopmentCard> deck, int turn){
        int limitDown = (turn - 1) * 4;
        int limitTop = (turn - 1) * 4 + 4;
        return new ArrayList<>(deck.subList(limitDown, limitTop));
    }

    /**
     *  Method to set excommunication cards
     */
    /*package-local*/ void setExcommunicationCards() {
        chooseExcommunicationCards();
    }

    /**
     * Method to setup towers' cards
     * @param period the period to set the proper cards
     * @param turn the turn to get from the deck of the period the proper cards
     */
    /*package-local*/ void setupMainBoard(int period, int turn){
        //setup towers' cards
        this.game.getMainBoard().setTower(0, deckForTurn(deckForPeriod(this.greenDeck, period), turn));
        this.game.getMainBoard().setTower(1, deckForTurn(deckForPeriod(this.blueDeck, period), turn));
        this.game.getMainBoard().setTower(2, deckForTurn(deckForPeriod(this.yellowDeck, period), turn));
        this.game.getMainBoard().setTower(3, deckForTurn(deckForPeriod(this.purpleDeck, period), turn));
    }

    /**
     * Method to reset the mainboard state at the end of each turn
     */
    /*package-local*/ void mainboardTurnReset(){
        for(Tower tower : this.game.getMainBoard().getTowers())
            for(TowerCell towerCell : tower.getTowerCells()){
                towerCell.setPlayerNicknameInTheCell(null);
                towerCell.setDevelopmentCard(null);
            }
        this.game.getMainBoard().getCouncilPalace().resetFifo();
        for(MarketCell marketCell : this.game.getMainBoard().getMarket().getMarketCells())
            marketCell.setEmpty();
        this.game.getMainBoard().getProduction().reset();
        this.game.getMainBoard().getHarvest().reset();
        this.game.getMainBoard().getProductionExtended().reset();
        this.game.getMainBoard().getHarvestExtended().reset();
        throwDices();
    }

    /**
     * Method to reset the personal board at the end of each turn
     * @param configuration the configuration of the personal board
     */
    /*package-local*/ void personalBoardsTurnReset(Configuration configuration){
        for(Player player : this.players)
            player.getPersonalBoard().turnReset(configuration);
    }

    /**
     * Method to setup the excommunication cards for the period
     */
    private void chooseExcommunicationCards() {
        Collections.shuffle(this.excommunicationCards);
        ExcommunicationCard[] excommunicationCardsDeck = new ExcommunicationCard[3];
        int period = 1;
        for (ExcommunicationCard card : this.excommunicationCards){
            if (card.getPeriod() == period){
                excommunicationCardsDeck[period - 1] = new ExcommunicationCard();
                excommunicationCardsDeck[period - 1] = card;
                period++;
            }
        }
        this.game.getMainBoard().getVatican().setExcommunicationCards(excommunicationCardsDeck);
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
            player.getPersonalBoard().getValuables().increase(ResourceType.COIN, i);
            this.game.getPlayersMap().put(player.getUsername(), player);
            i++;
        }
    }


    /**
     * This method sorts players randomly. This is the game order.
     */
    private void randomPlayerSorting(){
        Collections.shuffle(this.players);
    }

    /**
     * Get game instance.
     * @return game instance.
     */
    /*package-local*/ void createGameInstance(){
        setupMainBoard(1, 1);
        throwDices();
    }

    /**
     *Method to get the game model
     * @return game
     */
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
        personalBoard.getValuables().increase(ResourceType.WOOD, this.configuration.getPersonalBoard().getValuables().getResources().get(ResourceType.WOOD));
        personalBoard.getValuables().increase(ResourceType.STONE, this.configuration.getPersonalBoard().getValuables().getResources().get(ResourceType.STONE));
        personalBoard.getValuables().increase(ResourceType.SERVANT, this.configuration.getPersonalBoard().getValuables().getResources().get(ResourceType.SERVANT));
        personalBoard.getValuables().increase(ResourceType.COIN, this.configuration.getPersonalBoard().getValuables().getResources().get(ResourceType.COIN));
        personalBoard.getValuables().increase(PointType.VICTORY, this.configuration.getPersonalBoard().getValuables().getPoints().get(PointType.VICTORY));
        personalBoard.getValuables().increase(PointType.MILITARY, this.configuration.getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY));
        personalBoard.getValuables().increase(PointType.FAITH, this.configuration.getPersonalBoard().getValuables().getPoints().get(PointType.FAITH));
        personalBoard.setGreenCardsMilitaryPointsRequirements(this.configuration.getPersonalBoard().getGreenCardsMilitaryPointsRequirements());
        FamilyMember familyMember = new FamilyMember();
        personalBoard.setFamilyMember(familyMember);
        return personalBoard;
    }

    /**
     * Throw dices and set value in each personal board.
     */
    private void throwDices(){
        this.game.getDices().setValues();
        for(Player player : this.players)
            player.getPersonalBoard().getFamilyMember().setMembers(this.game.getDices().getValues());
    }

    /**
     * Get the callback object
     * @return information choices handler
     */
    /*package-local*/ InformationChoicesHandler getInformationChoicesHandler(){
        return this.informationChoicesHandler;
    }

    /**
     * Set the callback object
     * @param playerChoices The choices of the player
     */
    /*package-local*/ void setInformationChoicesHandler(Map<String, Object> playerChoices){
        this.informationChoicesHandler.setDecisions(playerChoices);
    }

    /**
     * This method does the final controls at the end of every period
     * @param period the current period
     * @param player the current player
     * @return
     */
    /*package-private*/ boolean finalControlsForPeriod(int period, ServerPlayer player){
        int faithPointsRequired = this.game.getMainBoard().getVatican().getExcommunicationCheckPoint(period);

        if (player.getPersonalBoard().getValuables().getPoints().get(PointType.FAITH) <= faithPointsRequired) {
            excommunicationForPlayer(player, period);
            return false;
        }
        return true;
    }

    /**
     * This method manages the support to the church
     * @param player the current player
     * @param flag this flag is used to check if the player has supported the church or not
     */
    /*package-private*/ void applySupportChoice(ServerPlayer player, boolean flag){
        if(!flag){
            player.getPersonalBoard().getValuables().increase(PointType.VICTORY, this.victoryPointsBonusForFaith[player.getPersonalBoard().getValuables().getPoints().get(PointType.FAITH)-1]);
            if(player.getPersonalBoard().getLeaderCardWithName("Sisto IV") != null && player.getPersonalBoard().getLeaderCardWithName("Sisto IV").getLeaderEffectActive())
                player.getPersonalBoard().getLeaderCardWithName("Sisto IV").getEffect().runEffect(player, this.informationChoicesHandler);
            player.getPersonalBoard().getValuables().decrease(PointType.FAITH, player.getPersonalBoard().getValuables().getPoints().get(PointType.FAITH));
        } else {
            excommunicationForPlayer(player, this.game.getAge());
        }
    }

    /**
     * This method runs an excommunication for the player based on the current excommunication card on the mainBoard
     * @param player the current player
     * @param period the current period
     */
    private void excommunicationForPlayer(Player player, int period){
        ExcommunicationCard excommunicationCard = this.game.getMainBoard().getVatican().getExcommunicationCard(period - 1);
        excommunicationCard.getEffect().runEffect(player);
        player.getPersonalBoard().addExcommunicationCard(excommunicationCard);
    }

    /**
     * This method calculates the final points for each player at the end of the game
     */
    /*package-local*/ void calculateFinalPoints(){

        Map<ServerPlayer, Integer> militaryPointsRanking = new HashMap<>();

        for (ServerPlayer player : this.players){

            militaryPointsRanking.put(player, player.getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY));

            loseVictoryPoints(player);

            greenCardsFinalPoints(player);

            blueCardsFinalPoints(player);

            purpleCardsFinalPoints(player);

            earnVictoryPointsFromResources(player);

            loseVictoryPointsFromResources(player);

            loseVictoryPointsFromMilitaryPoints(player);

            loseVictoryPointsFromYellowCardsResources(player);
        }
        assignVictoryPointsBasedOnMilitaryRanking(militaryPointsRanking);
    }

    /**
     * This method decrease player's victory points
     * @param player the current player
     */
    private void loseVictoryPoints(Player player){
        int finalVictoryIndexMalus = player.getPersonalBoard().getExcommunicationValues().getFinalPointsIndexMalus().get(PointType.VICTORY);
        if (finalVictoryIndexMalus > 0){

            int gameVictoryPoints = player.getPersonalBoard().getValuables().getPoints().get(PointType.VICTORY);

            int victoryPointsToLose = gameVictoryPoints/finalVictoryIndexMalus;

            player.getPersonalBoard().getValuables().decrease(PointType.VICTORY, victoryPointsToLose);
        }
    }

    /**
     * This method gives some victory points to the player based on the number of green cards
     * @param player the current player
     */
    private void greenCardsFinalPoints(Player player){
        if (player.getPersonalBoard().getExcommunicationValues().getDevelopmentCardGetFinalPoints().get(DevelopmentCardColor.GREEN)){
            int numberOfGreenCards = player.getPersonalBoard().getCards(DevelopmentCardColor.GREEN).size();
            if (numberOfGreenCards > 0){
                int finalPointsBonus = this.victoryPointsForGreenCards[numberOfGreenCards - 1];
                player.getPersonalBoard().getValuables().increase(PointType.VICTORY, finalPointsBonus);
            }
        }
    }

    /**
     * This method gives some victory points to the player based on the number of blue cards
     * @param player the current player
     */
    private void blueCardsFinalPoints(Player player){
        if (player.getPersonalBoard().getExcommunicationValues().getDevelopmentCardGetFinalPoints().get(DevelopmentCardColor.BLUE)){
            int numberOfBlueCards = player.getPersonalBoard().getCards(DevelopmentCardColor.BLUE).size();
            if (numberOfBlueCards > 0){
                int finalPointsBonus = this.victoryPointsForBlueCards[numberOfBlueCards - 1];
                player.getPersonalBoard().getValuables().increase(PointType.VICTORY, finalPointsBonus);
            }
        }
    }

    /**
     * This method gives some victory points to the player based on the number of purple cards
     * @param player the current player
     */
    private void purpleCardsFinalPoints(Player player){
        if (player.getPersonalBoard().getExcommunicationValues().getDevelopmentCardGetFinalPoints().get(DevelopmentCardColor.PURPLE)){
            for (DevelopmentCard card : player.getPersonalBoard().getCards(DevelopmentCardColor.PURPLE)){
                card.getPermanentEffect().runEffect(player, informationChoicesHandler);
            }
        }
    }

    /**
     * This method converts the player's final resources in victory points
     * @param player the current player
     */
    private void earnVictoryPointsFromResources(Player player){
        int totalResources = 0;
        for (Map.Entry<ResourceType, Integer> entry: player.getPersonalBoard().getValuables().getResources().entrySet()) {
            totalResources += entry.getValue();
        }
        int victoryPoints = totalResources/5;
        player.getPersonalBoard().getValuables().increase(PointType.VICTORY, victoryPoints);
    }

    /**
     * In case of excommunication, this method decreases the player's victory points from the final resources
     * @param player the current player
     */
    private void loseVictoryPointsFromResources(Player player){
        for (Map.Entry<ResourceType, Integer> entry: player.getPersonalBoard().getValuables().getResources().entrySet()) {
            int finalResourcesIndexMalus = player.getPersonalBoard().getExcommunicationValues().getFinalResourcesIndexMalus().get(entry.getKey());
            if (finalResourcesIndexMalus > 0){
                int victoryPointsToLose = entry.getValue()/finalResourcesIndexMalus;
                player.getPersonalBoard().getValuables().decrease(PointType.VICTORY, victoryPointsToLose);
            }
        }
    }

    /**
     * In case of excommunication, this method decreases the player's victory points from the military points
     * @param player the current player
     */
    private void loseVictoryPointsFromMilitaryPoints(Player player){
        int finalMilitaryPointsIndexMalus = player.getPersonalBoard().getExcommunicationValues().getFinalPointsIndexMalus().get(PointType.MILITARY);
        if (finalMilitaryPointsIndexMalus > 0){
            int victoryPointsToLose = player.getPersonalBoard().getValuables().getPoints().get(PointType.MILITARY)/finalMilitaryPointsIndexMalus;
            player.getPersonalBoard().getValuables().decrease(PointType.VICTORY, victoryPointsToLose);
        }
    }

    /**
     * In case of excommunication, this method decreases the player's victory points from the yellow cards cost he owns
     * @param player the current player
     */
    private void loseVictoryPointsFromYellowCardsResources(Player player){
        EnumMap<ResourceType, Integer> totalCardResourcesCost = new EnumMap<>(ResourceType.class);
        for (DevelopmentCard card: player.getPersonalBoard().getCards(DevelopmentCardColor.YELLOW)){
            for (Map.Entry<ResourceType, Integer> entry : card.getCost().getResources().entrySet()){
                totalCardResourcesCost.put(entry.getKey(), entry.getValue());
            }
        }

        for (Map.Entry<ResourceType, Integer> entry : totalCardResourcesCost.entrySet()){
            int finalResourcesDevCardIndexMalus = player.getPersonalBoard().getExcommunicationValues().getFinalResourcesDevCardIndexMalus().get(entry.getKey());
            if (finalResourcesDevCardIndexMalus > 0){
                player.getPersonalBoard().getValuables().decrease(PointType.VICTORY, entry.getValue()/finalResourcesDevCardIndexMalus);
            }
        }
    }

    /**
     * This method calculates the final ranking for victory points and gives some extra victory points to the players
     * @param militaryPointsRanking hashmap containing all the players and their military points value
     */
    private void assignVictoryPointsBasedOnMilitaryRanking(Map<ServerPlayer, Integer> militaryPointsRanking){
        Map<ServerPlayer, Integer> result = new LinkedHashMap<>();
        militaryPointsRanking.entrySet().stream()
                .sorted(Map.Entry.<ServerPlayer, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        int firstMilitaryPointsValue = 0;
        int secondMilitaryPointsValue = 0;
        int i = 1;
        for (Map.Entry<ServerPlayer, Integer> entry : result.entrySet()){
            if (firstMilitaryPointsValue == 0){
                firstMilitaryPointsValue = entry.getValue();
                (entry.getKey()).getPersonalBoard().getValuables().increase(PointType.VICTORY, 5);
            }else{
                if(firstMilitaryPointsValue == entry.getValue()){
                    (entry.getKey()).getPersonalBoard().getValuables().increase(PointType.VICTORY, 5);
                    break;
                }else{
                    if (secondMilitaryPointsValue == 0){
                        secondMilitaryPointsValue = entry.getValue();
                        System.out.println(secondMilitaryPointsValue);
                        (entry.getKey()).getPersonalBoard().getValuables().increase(PointType.VICTORY, 2);
                    }else{
                        if (secondMilitaryPointsValue == entry.getValue()){
                            (entry.getKey()).getPersonalBoard().getValuables().increase(PointType.VICTORY, 2);
                        }
                        break;
                    }
                }
            }
        }
    }
}
