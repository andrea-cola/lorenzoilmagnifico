package it.polimi.ingsw.JSONCardsWriter;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.MainBoard;

import java.io.*;
import java.util.Timer;

public class Writer {

    public static void main(String[] args){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        MainBoard mainBoard = new MainBoard();

        String tmp = gson.toJson(mainBoard);

        try{
            FileOutputStream os=new FileOutputStream("src/main/resources/configFiles/mainboard.json",true);
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os));
            bw.append(tmp);
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
