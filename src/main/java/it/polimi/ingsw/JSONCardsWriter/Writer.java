package it.polimi.ingsw.JSONCardsWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.EffectHarvestProductionSimple;
import it.polimi.ingsw.utility.Configuration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Writer {

    public static void main(String[] args){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        long wait1 = 60000L;
        long wait2 = 30000L;

        PersonalBoard personalBoard = new PersonalBoard();
        PointsAndResources pointsAndResources = new PointsAndResources();
        pointsAndResources.increase(ResourceType.WOOD,2);
        pointsAndResources.increase(ResourceType.SERVANT, 3);
        pointsAndResources.increase(ResourceType.STONE, 2);
        personalBoard.setValuables(pointsAndResources);

        List<PersonalBoardTile> personalBoardTiles = new ArrayList<>();

        PersonalBoardTile personal = new PersonalBoardTile();
        EffectHarvestProductionSimple effectHarvestProductionSimple = new EffectHarvestProductionSimple();
        effectHarvestProductionSimple.setDiceActionValue(1);
        effectHarvestProductionSimple.setActionType(ActionType.PRODUCTION);
        CouncilPrivilege councilPrivilege = new CouncilPrivilege();
        councilPrivilege.setNumberOfCouncilPrivileges(0);
        effectHarvestProductionSimple.setCouncilPrivilege(councilPrivilege);
        PointsAndResources pointsAndResources2 = new PointsAndResources();
        pointsAndResources2.increase(ResourceType.SERVANT, 1);
        pointsAndResources2.increase(ResourceType.COIN, 2);
        effectHarvestProductionSimple.setValuable(pointsAndResources2);

        EffectHarvestProductionSimple effectHarvestProductionSimple2 = new EffectHarvestProductionSimple();
        effectHarvestProductionSimple2.setDiceActionValue(1);
        effectHarvestProductionSimple2.setActionType(ActionType.PRODUCTION);
        CouncilPrivilege councilPrivilege2 = new CouncilPrivilege();
        councilPrivilege2.setNumberOfCouncilPrivileges(0);
        effectHarvestProductionSimple2.setCouncilPrivilege(councilPrivilege2);
        PointsAndResources pointsAndResources3 = new PointsAndResources();
        pointsAndResources3.increase(ResourceType.SERVANT, 1);
        pointsAndResources3.increase(ResourceType.COIN, 2);
        effectHarvestProductionSimple2.setValuable(pointsAndResources3);
        personal.setProductionEffect(effectHarvestProductionSimple);
        personal.setHarvestEffect(effectHarvestProductionSimple2);
        personalBoardTiles.add(personal);


        PersonalBoardTile personal2 = new PersonalBoardTile();
        EffectHarvestProductionSimple effectHarvestProductionSimple3 = new EffectHarvestProductionSimple();
        effectHarvestProductionSimple3.setDiceActionValue(1);
        effectHarvestProductionSimple3.setActionType(ActionType.PRODUCTION);
        CouncilPrivilege councilPrivilege3 = new CouncilPrivilege();
        councilPrivilege3.setNumberOfCouncilPrivileges(0);
        effectHarvestProductionSimple3.setCouncilPrivilege(councilPrivilege3);
        PointsAndResources pointsAndResources4 = new PointsAndResources();
        pointsAndResources4.increase(ResourceType.SERVANT, 1);
        pointsAndResources4.increase(PointType.MILITARY, 2);
        effectHarvestProductionSimple3.setValuable(pointsAndResources4);

        EffectHarvestProductionSimple effectHarvestProductionSimple4 = new EffectHarvestProductionSimple();
        effectHarvestProductionSimple4.setDiceActionValue(1);
        effectHarvestProductionSimple4.setActionType(ActionType.PRODUCTION);
        CouncilPrivilege councilPrivilege4 = new CouncilPrivilege();
        councilPrivilege4.setNumberOfCouncilPrivileges(0);
        effectHarvestProductionSimple4.setCouncilPrivilege(councilPrivilege4);
        PointsAndResources pointsAndResources5 = new PointsAndResources();
        pointsAndResources5.increase(ResourceType.SERVANT, 1);
        pointsAndResources5.increase(ResourceType.COIN, 2);
        effectHarvestProductionSimple4.setValuable(pointsAndResources5);
        personal2.setProductionEffect(effectHarvestProductionSimple);
        personal2.setHarvestEffect(effectHarvestProductionSimple4);
        personalBoardTiles.add(personal2);

        Configuration configuration = new Configuration(wait1, wait2, new MainBoard(), personalBoard, personalBoardTiles);

        String tmp = gson.toJson(configuration);

        try{
            FileOutputStream os=new FileOutputStream("src/main/resources/configFiles/configuration.json",true);
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os));
            bw.append(tmp);
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
