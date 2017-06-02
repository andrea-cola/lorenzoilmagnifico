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

    private static final String CARD_OUTPUT_FILE = "../Documents/Card.json";

    public static void main(String[] args){
        JsonWriterDevCard writerDevCard = new JsonWriterDevCard();

        DevelopmentCard card = new DevelopmentCard();

        //standard attributes
        card.setName("Support to the pope");
        card.setId(96);
        card.setCardColor(DevelopmentCardColor.PURPLE);
        card.setPeriod(3);

        //requisite
        card.setMultipleRequisiteSelectionEnabled(true);
        card.setMilitaryPointsRequired(10);

        PointsAndResources valuables = new PointsAndResources();
        valuables.increase(ResourceType.WOOD, 3);
        valuables.increase(ResourceType.COIN, 4);
        valuables.increase(ResourceType.STONE, 3);
        valuables.increase(ResourceType.SERVANT, 0);
        valuables.increase(PointType.MILITARY, 5);
        card.setCost(valuables);




        //effects
        //IMMEDIATE
        EffectSimple immediateEffect = new EffectSimple();

        PointsAndResources immediateValuablesBonus = new PointsAndResources();
        immediateValuablesBonus.increase(ResourceType.WOOD, 0);
        immediateValuablesBonus.increase(ResourceType.COIN, 0);
        immediateValuablesBonus.increase(ResourceType.STONE, 0);
        immediateValuablesBonus.increase(ResourceType.SERVANT, 0);
        immediateValuablesBonus.increase(PointType.FAITH, 2);
        immediateValuablesBonus.increase(PointType.MILITARY, 0);
        immediateValuablesBonus.increase(PointType.VICTORY, 0);
        immediateEffect.setValuable(immediateValuablesBonus);
        card.setImmediateEffect(immediateEffect);

        CouncilPrivilege councilPrivilege = new CouncilPrivilege();
        councilPrivilege.setNumberOfCouncilPrivileges(0);
        immediateEffect.setCouncilPrivilege(councilPrivilege);




        //PERMANENT
        EffectFinalPoints permanentEffect = new EffectFinalPoints();

        PointsAndResources victoryPoints = new PointsAndResources();
        victoryPoints.increase(PointType.VICTORY, 10);
        permanentEffect.setFinalVictoryPoints(victoryPoints);
        card.setPermanentEffect(permanentEffect);


        try {
            OutputStream outputFileCard = new FileOutputStream(CARD_OUTPUT_FILE);
            writerDevCard.writeJsonStream(outputFileCard, card);
        }catch (IOException e){
            System.out.print(e.getMessage());
        }

    }
}
