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
    private final String CONFIGURATION_FILE_PATH = "src/main/resources/configFiles/configuration.json";

    /**
     * Configurator instance. Singleton.
     */
    private static Configurator configurator;

    /**
     * Development cards deck.
     */
    private ArrayList<DevelopmentCard> developmentCards;

    /**
     * Gson object reference.
     */
    private Gson gson;

    /**
     * Effect factory reference.
     */
    private RuntimeTypeAdapterFactory<Effect> effectFactory;

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
        } catch(FileNotFoundException e){
            throw new ConfigurationException(e);
        }
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
    }

    /**
     * This method is called from server to instantiate the singleton.
     */
    public static void loadConfigurations() throws ConfigurationException{
        configurator = new Configurator();
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
    private ArrayList<DevelopmentCard> parseDevelopmentCard() throws FileNotFoundException{
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(effectFactory);;
        gson = builder.create();
        JsonReader reader = new JsonReader(new FileReader(DEVELOPMENT_CARDS_FILE_PATH));
        developmentCards = gson.fromJson(reader, new TypeToken<List<DevelopmentCard>>(){}.getType());
        return developmentCards;
    }

    /**
     * Return a configuration bundle with all configurations got from configuration files.
     * @return configuration bundle.
     */
    public static Configuration getConfiguration(){
        return configuration;
    }

    public static GameManager buildAndGetGame(ArrayList<ServerPlayer> roomPlayers, Configuration configuration){
        return null;
    }

}
