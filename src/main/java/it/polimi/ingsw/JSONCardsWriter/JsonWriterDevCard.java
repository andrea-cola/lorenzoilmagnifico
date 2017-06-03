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
        writer.name("militaryPointsRequired").value(card.getMilitaryPointsRequired());
        writer.name("wood").value(card.getCost().getResources().get(ResourceType.WOOD));
        writer.name("coin").value(card.getCost().getResources().get(ResourceType.COIN));
        writer.name("stone").value(card.getCost().getResources().get(ResourceType.STONE));
        writer.name("servant").value(card.getCost().getResources().get(ResourceType.SERVANT));
        writer.name("military").value(card.getCost().getPoints().get(PointType.MILITARY));

        writer.endObject();
    }

    private void writeImmediateEffect(JsonWriter writer, EffectSimple immediateEffect) throws IOException{

        writer.beginObject();

        writer.name("immediateEffectType").value(immediateEffect.getClass().getSimpleName());
        writer.name("effect");
        writeEffect(writer, immediateEffect);

        writer.endObject();
    }

    private void writeEffect(JsonWriter writer, EffectSimple immediateEffect) throws IOException {
        writer.beginObject();
        writer.name("resources");
        writeBonusResources(writer, immediateEffect);
        writer.name("numberOfCouncilPrivileges").value(immediateEffect.getCouncilPrivilege().getNumberOfCouncilPrivileges());
        writer.endObject();
    }


    private void writeBonusResources(JsonWriter writer, EffectSimple immediateEffect) throws IOException{
        writer.beginObject();
        writer.name("wood").value(immediateEffect.getValuable().getResources().get(ResourceType.WOOD));
        writer.name("coin").value(immediateEffect.getValuable().getResources().get(ResourceType.COIN));
        writer.name("stone").value(immediateEffect.getValuable().getResources().get(ResourceType.STONE));
        writer.name("servant").value(immediateEffect.getValuable().getResources().get(ResourceType.SERVANT));
        writer.name("faith").value(immediateEffect.getValuable().getPoints().get(PointType.FAITH));
        writer.name("military").value(immediateEffect.getValuable().getPoints().get(PointType.MILITARY));
        writer.name("victory").value(immediateEffect.getValuable().getPoints().get(PointType.VICTORY));
        writer.endObject();
    }

    private void writePermanentEffect(JsonWriter writer, EffectFinalPoints permanentEffect) throws IOException{
        String permanentEffectType = permanentEffect.getClass().getSimpleName();

        writer.beginObject();

        writer.name("permanentEffectType").value(permanentEffectType);
        writer.name("effect");
        writeEffect(writer, permanentEffect);

        writer.endObject();
    }

    private void writeEffect(JsonWriter writer, EffectFinalPoints permanentEffect) throws IOException{
        writer.beginObject();
        writer.name("resources");
        writeBonusResources(writer, permanentEffect);
        writer.endObject();
    }

    private void writeBonusResources(JsonWriter writer, EffectFinalPoints permanentEffect) throws IOException{
        writer.beginObject();
        writer.name("victory").value(permanentEffect.getFinalVictoryPoints().getPoints().get(PointType.VICTORY));
        writer.endObject();
    }
}
