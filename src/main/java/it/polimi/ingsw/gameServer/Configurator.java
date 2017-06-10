package it.polimi.ingsw.gameServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.cli.Debugger;
import it.polimi.ingsw.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is a singleton. It loads configurations from the file.
 */
public class Configurator {

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
        parseTimeConfiguration();
        parseMainBoard();
        parsePersonalBoards();
        parseDevelopmentCard();
        parseLeaderCards();
        parseExcommunicationCards();
    }

    /**
     * Method to parse colors from configuration file.
     */
    private void parseColor() throws FileNotFoundException{
        playerColors = gson.fromJson(new FileReader("src/main/resources/configFiles/colors.json"), String[].class);
    }

    private void parseTimeConfiguration(){

    }

    private void parseMainBoard(){

    }

    private void parsePersonalBoards(){

    }

    private void parseLeaderCards(){

    }

    private void parseExcommunicationCards(){

    }

    public ArrayList<DevelopmentCard> parseDevelopmentCard() throws FileNotFoundException{
        RuntimeTypeAdapterFactory<Effect> effectFactory = RuntimeTypeAdapterFactory.of(Effect.class, "effectType");
        effectFactory.registerSubtype(EffectSimple.class, "EffectSimple");
        effectFactory.registerSubtype(EffectFinalPoints.class, "EffectFinalPoints");
        effectFactory.registerSubtype(EffectHarvestProductionSimple.class, "EffectHarvestProductionSimple");


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(effectFactory);

        gson = builder.create();

        JsonReader reader = new JsonReader(new FileReader("../git/src/main/java/it/polimi/ingsw/gameServer/DevelopmentCards"));

        ArrayList<DevelopmentCard> developmentCards = gson.fromJson(reader, new TypeToken<List<DevelopmentCard>>(){}.getType());

        return developmentCards;

    }
}
