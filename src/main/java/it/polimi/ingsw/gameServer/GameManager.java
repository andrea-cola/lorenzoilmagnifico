package it.polimi.ingsw.gameServer;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.utility.Configuration;
import sun.applet.Main;
import sun.security.krb5.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/*package-local*/ class GameManager extends Game{

    private static final int INITIAL_COINS = 5;

    /**
     * Development cards yellow deck
     */
    protected ArrayList<DevelopmentCard> yellowDeck;

    /**
     * Development cards green deck
     */
    protected ArrayList<DevelopmentCard> greenDeck;

    /**
     * Development cards blue deck
     */
    protected ArrayList<DevelopmentCard> blueDeck;

    /**
     * Development cards purple deck
     */
    protected ArrayList<DevelopmentCard> purpleDeck;

    /**
     * Player of the game.
     */
    private ArrayList<ServerPlayer> players;

    /**
     * Game configuration.
     */
    private Configuration configuration;

    /**
     * Class constructor.
     * @param players of the room.
     * @param configuration of the game.
     * @param developmentCards deck.
     */
    /*package-local*/ GameManager(ArrayList<ServerPlayer> players, Configuration configuration, ArrayList<DevelopmentCard> developmentCards){
        this.players = players;
        this.configuration = configuration;
        this.mainBoard = configuration.getMainBoard();
        setupPlayers();
        setupDecks(developmentCards);
    }

    private void setupDecks(ArrayList<DevelopmentCard> deck){
        this.yellowDeck = new ArrayList<>();
        this.greenDeck = new ArrayList<>();
        this.blueDeck = new ArrayList<>();
        this.purpleDeck = new ArrayList<>();

        for (DevelopmentCard card : deck){
            switch (card.getColor()){
                case YELLOW:
                    this.yellowDeck.add(card);
                    break;
                case GREEN:
                    this.greenDeck.add(card);
                    break;
                case BLUE:
                    this.blueDeck.add(card);
                    break;
                case PURPLE:
                    this.purpleDeck.add(card);
                    break;
            }
        }
    }

    private ArrayList<DevelopmentCard> deckForPeriod(ArrayList<DevelopmentCard> deck, int period){
        ArrayList<DevelopmentCard> deckPeriod = new ArrayList<>();
        for (DevelopmentCard card : deck){
            if (card.getPeriod() == period){
                deckPeriod.add(card);
            }
        }
        Collections.shuffle(deckPeriod);

        return deckPeriod;
    }

    /**
     * Method to setup player color, personal board and increase coin amount following game rules.
     * Then the player is added to Game players map.
     */
    private void setupPlayers(){
        randomPlayerSorting();
        List<String> colors = PlayerColor.getValues();
        int i = 0;
        for(ServerPlayer player : players){
            Player gamePlayer = player;
            gamePlayer.setColor(PlayerColor.valueOf(colors.get(i)));
            gamePlayer.setPersonalBoard(configuration.getPersonalBoard());
            gamePlayer.getPersonalBoard().getValuables().increase(ResourceType.COIN, INITIAL_COINS + i);
            this.playersMap.put(player.getNickname(), gamePlayer);
            i++;
        }
    }

    /**
     * This method sorts players randomly.
     */
    private void randomPlayerSorting(){
        Collections.shuffle(players);
    }

}
