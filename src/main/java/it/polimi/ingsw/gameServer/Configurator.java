package it.polimi.ingsw.gameServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.cli.Debugger;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a singleton. It loads configurations from the file.
 */
public class Configurator {

    /**
     * Path strings constants.
     */
    private final String DEVELOPMENT_CARDS_FILE_PATH = "src/main/resources/configFiles/developmentCards.json";
    private final String PLAYER_COLORS_FILE_PATH = "src/main/resources/configFiles/colors.json";
    private final String TIMES_FILE_PATH = "src/main/resources/configFiles/times.json";

    /**
     * Configurator instance. Singleton.
     */
    private static Configurator configurator;

    /**
     * Players colors.
     */
    private String[] playerColors;

    /**
     * Game main board.
     */
    private MainBoard mainBoard;

    /**
     * Array of all personal boards available.
     */
    private ArrayList<PersonalBoard> personalBoards;

    /**
     * Dice colors.
     */
    private ArrayList<String> diceColors;

    /**
     * Development cards deck.
     */
    private ArrayList<DevelopmentCard> developmentCards;

    /**
     * Array of times.
     */
    private long[] times;

    /**
     * Gson object reference.
     */
    private Gson gson;

    /**
     * Class constructor.
     */
    private Configurator(){
        Debugger.printDebugMessage("Loading configuration files...");
        try {
            parse();
        } catch(FileNotFoundException e){
            Debugger.printDebugMessage(this.getClass().getSimpleName(), "Error during parsing proceedings.", e);
        }
    }

    /**
     * Main parsing method. This method calls all needed method to parse the file.
     */
    private void parse() throws FileNotFoundException{
        gson = new Gson();
        parseColor();
        parseDevelopmentCard();
        parseTimeConfiguration();
        parseMainBoard();
        parsePersonalBoards();
        parseLeaderCards();
        parseExcommunicationCards();
    }

    /**
     * Parse colors from configuration file.
     */
    private void parseColor() throws FileNotFoundException{
        playerColors = gson.fromJson(new FileReader(PLAYER_COLORS_FILE_PATH), String[].class);
    }

    /**
     * Parse times from configuration file.
     * @throws FileNotFoundException
     */
    private void parseTimeConfiguration() throws FileNotFoundException{
        times = gson.fromJson(new FileReader(TIMES_FILE_PATH), long[].class);
    }

    private void parseMainBoard(){

    }

    private void parsePersonalBoards(){

    }

    private void parseLeaderCards(){

    }

    private void parseExcommunicationCards(){

    }

    /**
     * Parse development cards from appropriate json file.
     * @return array of cards.
     * @throws FileNotFoundException if file is not found.
     */
    private ArrayList<DevelopmentCard> parseDevelopmentCard() throws FileNotFoundException{

        RuntimeTypeAdapterFactory<Effect> effectFactory = RuntimeTypeAdapterFactory.of(Effect.class, "effectType")
                    .registerSubtype(EffectSimple.class, "EffectSimple")
                    .registerSubtype(EffectCardBonus.class, "EffectCardBonus")
                    .registerSubtype(EffectChooseCard.class, "EffectChooseCard")
                    .registerSubtype(EffectFinalPoints.class, "EffectFinalPoints")
                    .registerSubtype(EffectHarvestProductionBonus.class, "EffectHarvestProductionBonus")
                    .registerSubtype(EffectHarvestProductionExchange.class, "EffectHarvestProductionExchange")
                    .registerSubtype(EffectHarvestProductionSimple.class, "EffectHarvestProductionSimple")
                    .registerSubtype(EffectMultiplicator.class, "EffectMultiplicator")
                    .registerSubtype(EffectNoBonus.class, "EffectNoBonus");

        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(effectFactory);;
        gson = builder.create();
        JsonReader reader = new JsonReader(new FileReader(DEVELOPMENT_CARDS_FILE_PATH));
        developmentCards = gson.fromJson(reader, new TypeToken<List<DevelopmentCard>>(){}.getType());

        return developmentCards;

    }

}
