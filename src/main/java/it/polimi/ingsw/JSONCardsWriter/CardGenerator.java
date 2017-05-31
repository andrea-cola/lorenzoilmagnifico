package it.polimi.ingsw.JSONCardsWriter;

import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.model.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by lorenzo on 29/05/17.
 */
public class CardGenerator {

    private static final String OUTPUT_FILE = "../Documents/Card.json";

    public static void main(String[] args){
        JsonWriterDevCard writerDevCard = new JsonWriterDevCard();

        DevelopmentCard card = new DevelopmentCard();

        //standard attributes
        card.setName("Sostegno al vescovo");
        card.setId(80);
        card.setCardColor(DevelopmentCardColor.PURPLE);
        card.setPeriod(1);

        //requisite
        card.setMultipleRequisiteSelectionEnabled(false);

        Requisite requisite = new Requisite();

        ArrayList<Resource> resources = new ArrayList<>();
        Resource wood = new Resource(ResourceType.WOOD, 0);
        resources.add(wood);
        Resource coin = new Resource(ResourceType.COIN, 0);
        resources.add(coin);
        Resource stone = new Resource(ResourceType.STONE, 0);
        resources.add(stone);
        Resource servant = new Resource(ResourceType.SERVANT, 0);
        resources.add(servant);
        requisite.setResources(resources);

        ArrayList<Point> points = new ArrayList<>();
        Point militaryPointsRequired = new Point(PointType.MILITARY, 0);
        points.add(militaryPointsRequired);
        Point militaryPointsCost = new Point(PointType.MILITARY, 0);
        points.add(militaryPointsCost);
        requisite.setPoints(points);

        card.setRequisite(requisite);


        //effects
        ImmediateEffect immediateEffect = new ImmediateEffectSimple();
        card.setImmediateEffect(immediateEffect);

        PermanentEffect permanentEffect = new PermanentEffectHarvestProductionSimple();
        card.setPermanentEffect(permanentEffect);

        try {
            OutputStream outputFile = new FileOutputStream(OUTPUT_FILE);
            writerDevCard.writeJsonStream(outputFile, card);
        }catch (IOException e){
            System.out.print(e.getMessage());
        }

    }
}
