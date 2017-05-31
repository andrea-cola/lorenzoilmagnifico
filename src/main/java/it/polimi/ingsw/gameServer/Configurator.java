package it.polimi.ingsw.gameServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.*;
import sun.misc.Perf;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lorenzo on 27/05/17.
 */
public class Configurator {

    private static Configurator configurator;

    private Configurator(){

    }

    public static Configurator setupConfigurator(){
        if (configurator == null){
            configurator = new Configurator();
        }
        return configurator;
    }

    public ArrayList<DevelopmentCard> parseDevelopmentCard() throws FileNotFoundException{
        RuntimeTypeAdapterFactory<ImmediateEffect> immediateEffectFactory = RuntimeTypeAdapterFactory.of(ImmediateEffect.class, "immediateEffectType");
        immediateEffectFactory.registerSubtype(ImmediateEffectSimple.class, "immediateEffectSimple");


        RuntimeTypeAdapterFactory<PermanentEffect> permanentEffectFactory = RuntimeTypeAdapterFactory.of(PermanentEffect.class, "permanentEffectType");
        permanentEffectFactory.registerSubtype(PermanentEffectHarvestProductionSimple.class, "permanentEffectHarvestProductionSimple");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(immediateEffectFactory);
        builder.registerTypeAdapterFactory(permanentEffectFactory);

        Gson gson = builder.create();

        JsonReader reader = new JsonReader(new FileReader("../git/src/main/java/it/polimi/ingsw/gameServer/DevelopmentCards"));

        ArrayList<DevelopmentCard> developmentCards = gson.fromJson(reader, new TypeToken<List<DevelopmentCard>>(){}.getType());

        return developmentCards;

    }
}
