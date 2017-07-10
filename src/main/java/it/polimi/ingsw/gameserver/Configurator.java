package it.polimi.ingsw.gameserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exceptions.ConfigurationException;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.utility.Configuration;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a singleton. It loads all configurations from the files.
 */
public class Configurator {

    /**
     * Path strings constants.
     */
    private static final String DC_FILEPATH = "src/main/resources/configFiles/developmentCards.json";
    private static final String LC_FILEPATH = "src/main/resources/configFiles/leaderCards.json";
    private static final String C_FILEPATH = "src/main/resources/configFiles/configuration.json";
    private static final String EX_FILEPATH ="src/main/resources/configFiles/excommunicationCards.json";
    private static final String EFFECT_FIELD = "effectType";

    /**
     * Configuration bundle.
     */
    private static Configuration configuration;

    /**
     * Development cards deck.
     */
    private static ArrayList<DevelopmentCard> developmentCards;

    /**
     * Leader cards deck.
     */
    private static ArrayList<LeaderCard> leaderCards;

    /**
     * Excommunication cards deck
     */
    private static ArrayList<ExcommunicationCard> excommunicationCards;

    /**
     * Gson object reference.
     */
    private static Gson gson = new Gson();

    /**
     * Effect factory reference.
     */
    private static RuntimeTypeAdapterFactory<Effect> effectFactory;

    /**
     * Effect factory reference.
     */
    private static RuntimeTypeAdapterFactory<LeaderEffect> leaderEffectFactory;

    /**
     * Effect factory reference.
     */
    private static RuntimeTypeAdapterFactory<ExcommunicationEffect> excommunicationEffectFactory;

    /**
     * Class constructor.
     */
    private Configurator() throws ConfigurationException{
        try {
            loadRuntimeTypeAdapterFactory();
            parseConfiguration();
            parseDevelopmentCard();
            parseLeaderCard();
            parseExcommunicationCard();
        } catch(FileNotFoundException e){
            throw new ConfigurationException(e);
        }
    }

    /**
     * This method is called from server to instantiate the singleton.
     */
    public static void loadConfigurations() throws ConfigurationException{
        new Configurator();
    }

    /**
     * Load all types of effect.
     */
    private static void loadRuntimeTypeAdapterFactory(){
        effectFactory = RuntimeTypeAdapterFactory.of(Effect.class, EFFECT_FIELD)
                .registerSubtype(EffectSimple.class, "EffectSimple")
                .registerSubtype(EffectCardBonus.class, "EffectCardBonus")
                .registerSubtype(EffectChooseCard.class, "EffectChooseCard")
                .registerSubtype(EffectFinalPoints.class, "EffectFinalPoints")
                .registerSubtype(EffectHarvestProductionBonus.class, "EffectHarvestProductionBonus")
                .registerSubtype(EffectHarvestProductionExchange.class, "EffectHarvestProductionExchange")
                .registerSubtype(EffectHarvestProductionSimple.class, "EffectHarvestProductionSimple")
                .registerSubtype(EffectMultiplicator.class, "EffectMultiplicator")
                .registerSubtype(EffectNoBonus.class, "EffectNoBonus");

        leaderEffectFactory = RuntimeTypeAdapterFactory.of(LeaderEffect.class, EFFECT_FIELD)
                .registerSubtype(LECesareBorgia.class, "LECesareBorgia")
                .registerSubtype(LEDiceBonus.class, "LEDiceBonus")
                .registerSubtype(LEDiceValueSet.class, "LEDiceValueSet")
                .registerSubtype(LEFamilyMemberBonus.class, "LEFamilyMemberBonus")
                .registerSubtype(LEFilippoBrunelleschi.class, "LEFilippoBrunelleschi")
                .registerSubtype(LEHarvestProductionSimple.class, "LEHarvestProductionSimple")
                .registerSubtype(LELorenzoDeMedici.class, "LELorenzoDeMedici")
                .registerSubtype(LELudovicoAriosto.class, "LELudovicoAriosto")
                .registerSubtype(LEMultiplicator.class, "LEMultiplicator")
                .registerSubtype(LENeutralBonus.class, "LENeutralBonus")
                .registerSubtype(LEPicoDellaMirandola.class, "LEPicoDellaMirandola")
                .registerSubtype(LESimple.class, "LESimple")
                .registerSubtype(LESistoIV.class, "LESistoIV");

        excommunicationEffectFactory = RuntimeTypeAdapterFactory.of(ExcommunicationEffect.class, EFFECT_FIELD)
                .registerSubtype(ExcommunicationEffectDevCard.class, "ExcommunicationEffectDevCard")
                .registerSubtype(ExcommunicationEffectDevCardLoseVictoryPoints.class, "ExcommunicationEffectDevCardLoseVictoryPoints")
                .registerSubtype(ExcommunicationEffectDiceMalus.class, "ExcommunicationEffectDiceMalus")
                .registerSubtype(ExcommunicationEffectHarvestProduction.class, "ExcommunicationEffectHarvestProduction")
                .registerSubtype(ExcommunicationEffectLoseVictoryPoints.class, "ExcommunicationEffectLoseVictoryPoints")
                .registerSubtype(ExcommunicationEffectMarket.class, "ExcommunicationEffectMarket")
                .registerSubtype(ExcommunicationEffectNoVictoryPoints.class, "ExcommunicationEffectNoVictoryPoints")
                .registerSubtype(ExcommunicationEffectNumberOfSlaves.class, "ExcommunicationEffectNumberOfSlaves")
                .registerSubtype(ExcommunicationEffectSimple.class, "ExcommunicationEffectSimple")
                .registerSubtype(ExcommunicationEffectSkipFirstTurn.class, "ExcommunicationEffectSkipFirstTurn");
    }

    /**
     * Main parsing method. This method calls all needed method to parseConfiguration the file.
     */
    private static void parseConfiguration() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(effectFactory);
        gson = builder.create();
        JsonReader reader = new JsonReader(new FileReader(C_FILEPATH));
        configuration = gson.fromJson(reader, Configuration.class);
    }

    /**
     * Parse development cards from appropriate json file.
     * @return array of cards.
     * @throws FileNotFoundException if file is not found.
     */
    private static void parseDevelopmentCard() throws FileNotFoundException{
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(effectFactory);
        gson = builder.create();
        JsonReader reader = new JsonReader(new FileReader(DC_FILEPATH));
        developmentCards = gson.fromJson(reader, new TypeToken<List<DevelopmentCard>>(){}.getType());
    }

    private static void parseLeaderCard() throws FileNotFoundException{
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(leaderEffectFactory);
        gson = builder.create();
        JsonReader reader = new JsonReader(new FileReader(LC_FILEPATH));
        leaderCards = gson.fromJson(reader, new TypeToken<List<LeaderCard>>(){}.getType());
    }

    private static void parseExcommunicationCard() throws FileNotFoundException{
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(excommunicationEffectFactory);
        gson = builder.create();
        JsonReader reader = new JsonReader(new FileReader(EX_FILEPATH));
        excommunicationCards = gson.fromJson(reader, new TypeToken<List<ExcommunicationCard>>(){}.getType());
    }

    /**
     * Return a configuration bundle with all configurations got from configuration files.
     * @return configuration bundle.
     */
    public static Configuration getConfiguration(){
        return configuration;
    }

    /**
     * Return all leader cards.
     * @return array list of leader cards.
     */
    public static ArrayList<LeaderCard> getLeaderCards(){
        return leaderCards;
    }

    /**
     * Gets all development cards
     * @return all development cards
     */
    /*package-local*/ static ArrayList<DevelopmentCard> getDevelopmentCards(){
        return developmentCards;
    }

    /**
     * Gets all excommunication cards
     * @return all excommunication cards
     */
    /*package-local*/ static ArrayList<ExcommunicationCard> getExcommunicationCards(){
        return excommunicationCards;
    }

    /*package-local*/ static GameManager buildAndGetGame(ArrayList<ServerPlayer> roomPlayers, Configuration configuration){
        return new GameManager(roomPlayers, configuration, developmentCards, leaderCards, excommunicationCards);
    }

}