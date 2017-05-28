package it.polimi.ingsw.model;

import java.util.*;

/**
 * Created by lorenzo on 23/05/17.
 */
public class Game {

    private MainBoard mainBoard;

    private Player[] players;

    private Queue turn;

    private FamilyMember[] familyMembers = new FamilyMember[FamilyMemberColor.values().length];

    private ArrayList<DevelopmentCard> developmentCardsDeck = new ArrayList<DevelopmentCard>();

    private static Game game;

    /**
     * Class constructor, note that this method gets called once due to the Singleton pattern
     */
    private Game(Integer numberOfPlayers){
        this.setupMainBoard();
        this.setupPlayers(numberOfPlayers);
        this.setupFamilyMembers();
    }

    /**
     *  Lazy instantiation of the game
     */
    public static Game setupGame(Integer numberOfPlayers){
        if(game == null){
            game = new Game(numberOfPlayers);
        }
        return game;
    }

    /**
     * This method instantiates the Players of the game
     * @param numberOfPlayers
     */
    private void setupPlayers(Integer numberOfPlayers){
        this.players = new Player[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++){
            this.players[i] = new Player();
        }
    }

    /**
     * This method instantiates the mainBoard if it has not been initialized yet
     */
    private void setupMainBoard(){
        this.mainBoard = MainBoard.setupMainBoard();
    }

    /**
     * This method instantiates the family members
     */
    private void setupFamilyMembers(){
        int i = 0;
        for(FamilyMemberColor color : FamilyMemberColor.values()){
            this.familyMembers[i] = new FamilyMember(color);
            i++;
        }
    }

    /**
     * This method instantiates the development cards decks
     */
    public void setupDevelopmentCardsDeck(ArrayList<DevelopmentCard> deck){
        this.developmentCardsDeck = deck;
    }

    /**
     * This method sets the board for a new round (for example it puts new cards inside every tower)
     */
    public void setNewRound(){
        this.setNewFamilyMembersValue();
        this.setNewCardsForTowers();
    }

     /**
     * This method assigns random values to every familiar at the beginning of each turn
     */
    private void setNewFamilyMembersValue(){
        Random rand = new Random();

        for(int i = 0; i < this.familyMembers.length; i++){
            int randomValue = rand.nextInt(6) + 1;
            this.familyMembers[i].setFamilyMemberValue(randomValue);
        }
    }

    /**
     * This method sets new cards for the towers
     */
    private void setNewCardsForTowers(){

    }

    public FamilyMember getFamilyMember(int index){
        return this.familyMembers[index];
    }

    public void placeFamilyMember(){

    }


    public void getUpdateState(){

    }

    private void updateResources(){

    }

    private  void updatePoints(){

    }
}
