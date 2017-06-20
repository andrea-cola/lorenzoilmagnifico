package it.polimi.ingsw.gameServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exceptions.ConfigurationException;
import it.polimi.ingsw.server.ServerPlayer;
import it.polimi.ingsw.utility.Configuration;
import it.polimi.ingsw.utility.Debugger;
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
    private final String DEVELOPMENT_CARDS_FILE_PATH = "src/main/resources/configFiles/developmentCards.json";
    private final String LEADER_CARDS_FILE_PATH = "src/main/resources/configFiles/leaderCards.json";
    private final String CONFIGURATION_FILE_PATH = "src/main/resources/configFiles/configuration.json";

    /**
     * Configurator instance. Singleton.
     */
    private static Configurator configurator;

    /**
     * Development cards deck.
     */
    private static ArrayList<DevelopmentCard> developmentCards;

    /**
     * Development cards deck.
     */
    private static ArrayList<LeaderCard> leaderCards;

    /**
     * Gson object reference.
     */
    private Gson gson;

    /**
     * Effect factory reference.
     */
    private RuntimeTypeAdapterFactory<Effect> effectFactory;

    /**
     * Effect factory reference.
     */
    private RuntimeTypeAdapterFactory<LeaderEffect> leaderEffectFactory;

    /**
     * Configuration bundle.
     */
    private static Configuration configuration;

    /**
     * Class constructor.
     */
    private Configurator() throws ConfigurationException{
        Debugger.printDebugMessage("Loading configuration files...");
        try {
            gson = new Gson();
            loadRuntimeTypeAdapterFactory();
            parseConfiguration();
            parseDevelopmentCard();
            parseLeaderCard();
        } catch(FileNotFoundException e){
            throw new ConfigurationException(e);
        }
    }

    /**
     * This method is called from server to instantiate the singleton.
     */
    public static void loadConfigurations() throws ConfigurationException{
        configurator = new Configurator();
    }

    /**
     * Load all types of effect.
     */
    private void loadRuntimeTypeAdapterFactory(){
        effectFactory = RuntimeTypeAdapterFactory.of(Effect.class, "effectType")
                .registerSubtype(EffectSimple.class, "EffectSimple")
                .registerSubtype(EffectCardBonus.class, "EffectCardBonus")
                .registerSubtype(EffectChooseCard.class, "EffectChooseCard")
                .registerSubtype(EffectFinalPoints.class, "EffectFinalPoints")
                .registerSubtype(EffectHarvestProductionBonus.class, "EffectHarvestProductionBonus")
                .registerSubtype(EffectHarvestProductionExchange.class, "EffectHarvestProductionExchange")
                .registerSubtype(EffectHarvestProductionSimple.class, "EffectHarvestProductionSimple")
                .registerSubtype(EffectMultiplicator.class, "EffectMultiplicator")
                .registerSubtype(EffectNoBonus.class, "EffectNoBonus");

        leaderEffectFactory = RuntimeTypeAdapterFactory.of(LeaderEffect.class, "effectType")
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

    }

    /**
     * Main parsing method. This method calls all needed method to parseConfiguration the file.
     */
    private void parseConfiguration() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(effectFactory);
        gson = builder.create();
        JsonReader reader = new JsonReader(new FileReader(CONFIGURATION_FILE_PATH));
        configuration = gson.fromJson(reader, Configuration.class);
    }

    /**
     * Parse development cards from appropriate json file.
     * @return array of cards.
     * @throws FileNotFoundException if file is not found.
     */
    private void parseDevelopmentCard() throws FileNotFoundException{
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(effectFactory);;
        gson = builder.create();
        JsonReader reader = new JsonReader(new FileReader(DEVELOPMENT_CARDS_FILE_PATH));
        developmentCards = gson.fromJson(reader, new TypeToken<List<DevelopmentCard>>(){}.getType());
    }

    private void parseLeaderCard() throws FileNotFoundException{
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(leaderEffectFactory);
        gson = builder.create();
        JsonReader reader = new JsonReader(new FileReader(LEADER_CARDS_FILE_PATH));
        leaderCards = gson.fromJson(reader, new TypeToken<List<LeaderCard>>(){}.getType());
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

    /*package-local*/ static GameManager buildAndGetGame(ArrayList<ServerPlayer> roomPlayers, Configuration configuration){
        GameManager gameManager = new GameManager(roomPlayers, configuration, developmentCards, leaderCards);
        return gameManager;
    }

}