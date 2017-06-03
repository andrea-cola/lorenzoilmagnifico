package it.polimi.ingsw.model;

import java.util.*;

public class Game {

    private MainBoard mainBoard;

    private Player[] players;

    private Queue turn;

    private FamilyMember[] familyMembers = new FamilyMember[FamilyMemberColor.values().length];

    private ArrayList<DevelopmentCard> deck = new ArrayList<>();

    private static Game game;

    /**
     * Class constructor, note that this method gets called once due to the Singleton pattern
     */
    private Game(){
        this.setupFamilyMembers();
    }

    /**
     *  Lazy instantiation of the game
     */
    public static Game setupGame(){
        if(game == null){
            game = new Game();
        }
        return game;
    }

    /**
     * This method instantiates the Players of the game
     * @param numberOfPlayers
     */
    public void setupPlayers(Integer numberOfPlayers){
        this.players = new Player[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++){
            this.players[i] = new Player();
        }
    }

    /**
     * This method instantiates the mainBoard if it has not been initialized yet
     */
    public void setupMainBoard(){
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
     * This method instantiates 4 development cards decks (one for each color)
     */
    public void setupDevelopmentCardsDeck(ArrayList<DevelopmentCard> deck){
        this.deck = deck;
    }


    /**
     * This method sets the board for a new round (for example it puts new cards inside every tower)
     */
    public void setNewRound(int period){
        this.setNewFamilyMembersValue();

        int towerIndex = 0;
        for (DevelopmentCardColor cardColor: DevelopmentCardColor.values()){
            this.mainBoard.setTower(towerIndex, chooseCards(this.deck, cardColor, period, towerIndex));
            towerIndex++;
        }

    }


    private ArrayList<DevelopmentCard> chooseCards(ArrayList<DevelopmentCard> deck, DevelopmentCardColor color, int period, int towerIndex){
        ArrayList<DevelopmentCard> cards = new ArrayList<>();

        for (DevelopmentCard card: deck){
            if (card.getColor() == color && card.getPeriod() == period){
                //add the card to an array list of cards that will be send to a tower
                cards.add(card);
                //remove from the deck the cards you have already added to the towers
                deck.remove(card);
            }

            if(cards.size() == this.mainBoard.getTower(towerIndex).getNumberOfTowerCells()){
                break;
            }
        }
        return cards;
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
