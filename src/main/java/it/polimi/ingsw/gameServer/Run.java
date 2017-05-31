package it.polimi.ingsw.gameServer;

/**
 * Created by lorenzo on 28/05/17.
 */
public class Run {
    public static void main(String[] args){
        Room room = new Room();

        room.setupGameConfiguration();


        room.newRound(1);
    }
}
