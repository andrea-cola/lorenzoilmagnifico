package it.polimi.ingsw.JSONCardsWriter;

import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.model.DevelopmentCard;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by lorenzo on 30/05/17.
 */
public class JsonWriterImmediateEffect {

    public JsonWriterImmediateEffect(){}

    public void writeJsonStream(OutputStream out, DevelopmentCard card) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("    ");

        writeCommonParameters(writer, card);

        writer.close();
    }

    private void writeCommonParameters(JsonWriter writer, DevelopmentCard card) throws IOException{
        writer.beginObject();



        writer.endObject();
    }
}
