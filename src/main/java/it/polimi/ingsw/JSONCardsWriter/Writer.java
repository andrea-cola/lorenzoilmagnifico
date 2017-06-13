package it.polimi.ingsw.JSONCardsWriter;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Timer;

public class Writer {

    public static void main(String[] args){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String[] time = new String[]{"red", "yellow", "green", "blue"};

        String tmp = gson.toJson(time);

        try{
            FileOutputStream os=new FileOutputStream("src/main/resources/configFiles/colors.json",true);
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os));
            bw.append(tmp);
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
