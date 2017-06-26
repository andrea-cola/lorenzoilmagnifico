package it.polimi.ingsw.JSONCardsWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.*;

import java.io.*;

public class Writer {

    public static void main(String[] args){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ExcommunicationCard excommunicationCard = new ExcommunicationCard();

        excommunicationCard.setCardID(17);
        excommunicationCard.setPeriod(3);

        ExcommunicationEffectNoVictoryPoints effect = new ExcommunicationEffectNoVictoryPoints();
        effect.setDevelopmentCardColor(DevelopmentCardColor.GREEN);
        excommunicationCard.setEffect(effect);

        String tmp = gson.toJson(excommunicationCard);

        try{
            FileOutputStream os = new FileOutputStream("src/main/resources/configFiles/excommunicationCards.json",true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.append(tmp + ",\n");
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
