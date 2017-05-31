package it.polimi.ingsw.JSONCardsWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.model.*;
import sun.plugin2.message.Message;
import sun.plugin2.message.Serializer;

import java.io.*;
import java.util.List;

/**
 * Created by lorenzo on 29/05/17.
 */
public class JsonWriterDevCard {

    public JsonWriterDevCard(){ }

    public void writeJsonStream(OutputStream out, DevelopmentCard card) throws IOException{
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("    ");

        writeCommonParameters(writer, card);

        writer.close();
    }

    private void writeCommonParameters(JsonWriter writer, DevelopmentCard card) throws IOException{
        writer.beginObject();

        writer.name("name").value(card.getName());
        writer.name("id").value(card.getId());
        writer.name("color").value(card.getColor().name());
        writer.name("period").value(card.getPeriod());

        writer.name("cost");
        writeCost(writer, card);

        writer.name("immediateEffect");
        writeImmediateEffect(writer, card.getImmediateEffect());

        writer.name("permanentEffect");
        writePermanentEffect(writer, card.getPermanentEffect());

        writer.endObject();
    }

    private void writeCost(JsonWriter writer,  DevelopmentCard card) throws IOException{
        writer.beginObject();

        writer.name("multipleRequisiteSelectionEnabled").value(card.getMultipleRequisiteSelectionEnabled());
        writer.name("wood").value(card.getRequisite().getResources().get(0).getAmount());
        writer.name("coin").value(card.getRequisite().getResources().get(1).getAmount());
        writer.name("stone").value(card.getRequisite().getResources().get(2).getAmount());
        writer.name("servant").value(card.getRequisite().getResources().get(3).getAmount());

        writer.name("militaryPointsRequired").value(card.getRequisite().getPoints().get(0).getAmount());
        writer.name("militaryPointsCost").value(card.getRequisite().getPoints().get(1).getAmount());

        writer.endObject();
    }

    private void writeImmediateEffect(JsonWriter writer, ImmediateEffect immediateEffect) throws IOException{

        writer.beginObject();

        writer.name("immediateEffectType").value(immediateEffect.getClass().getSimpleName());

        writer.endObject();

    }

    private void writePermanentEffect(JsonWriter writer, PermanentEffect permanentEffect) throws IOException{
        String permanentEffectType = permanentEffect.getClass().getSimpleName();

        writer.beginObject();

        writer.name("permanentEffectType").value(permanentEffectType);

        writer.endObject();
    }


}
