package it.polimi.ingsw.JSONCardsWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.*;

import java.io.*;

public class Writer {

    public static void main(String[] args){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        LeaderCard leaderCard = new LeaderCard();
        leaderCard.setLeaderCardName("Ludovico III Gonzaga");
        leaderCard.setLeaderCardDescription("shalala");
        PointsAndResources pointsAndResources = new PointsAndResources();
        pointsAndResources.increase(ResourceType.COIN, 0);
        pointsAndResources.increase(ResourceType.WOOD, 0);
        pointsAndResources.increase(ResourceType.SERVANT, 15);
        pointsAndResources.increase(ResourceType.STONE, 0);
        leaderCard.setPointsAndResourcesRequisites(pointsAndResources);
        LESimple leSimple = new LESimple();
        leSimple.setNumberOfCouncilPrivileges(1);
        leaderCard.setEffect(leSimple);

        String tmp = gson.toJson(leaderCard);

        try{
            FileOutputStream os = new FileOutputStream("src/main/resources/configFiles/leaderCards.json",true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.append(tmp + ",\n");
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
