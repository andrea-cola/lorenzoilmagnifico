package it.polimi.ingsw.model;

/**
 * This class represents the personal board abstraction.
 */
public class PersonalBoard {

    private static final int MAX_NUMER_OF_CARD_PER_TYPE = 6;

    /**
     * Contains all values of points and resources.
     */
    private PointsAndResources valuables;

    /**
     * Military points required to pick up a green card and place it in a specific position of the territory card array.
     */
    private static int[] greenCardsMilitaryPointsRequirements = new int[MAX_NUMER_OF_CARD_PER_TYPE];

    /**
     * Array of cards, divided per types.
     */
    private DevelopmentCard[] territoryCards = new DevelopmentCard[MAX_NUMER_OF_CARD_PER_TYPE];
    private DevelopmentCard[] buildingCards = new DevelopmentCard[MAX_NUMER_OF_CARD_PER_TYPE];
    private DevelopmentCard[] characterCards = new DevelopmentCard[MAX_NUMER_OF_CARD_PER_TYPE];
    private DevelopmentCard[] ventureCards = new DevelopmentCard[MAX_NUMER_OF_CARD_PER_TYPE];

    /**
     * Personal board tile choosen by the player.
     */
    private PersonalBoardTile personalBoardTile;

    public void setGreenCardsMilitaryPointsRequirements(int[] array){
        greenCardsMilitaryPointsRequirements = array;
    }

    /**
     * Get military points needed to place a card in a specific position.
     * @param index of position.
     * @return military points needed.
     */
    public int getCardsMilitaryPointsRequirements(int index){
        return greenCardsMilitaryPointsRequirements[index];
    }

    /**
     * Get points and resources.
     * @return points and resources.
     */
    public PointsAndResources getValuables(){
        return this.valuables;
    }

    /**
     * Set territory card.
     * @param atIndex of array.
     * @param withCard to set.
     */
    public void setTerritoryCards(int atIndex, DevelopmentCard withCard){
        this.territoryCards[atIndex] = withCard;
    }

    /**
     * Get a specific territory card from the array.
     * @param atIndex of array.
     * @return a territory card.
     */
    public DevelopmentCard getTerritoryCard(int atIndex){
        return this.territoryCards[atIndex];
    }

    /**
     * Set a building card.
     * @param atIndex of array.
     * @param withCard to set.
     */
    public void setBuildingCards(int atIndex, DevelopmentCard withCard){
        this.buildingCards[atIndex] = withCard;
    }

    /**
     * Get a specific building card from the array.
     * @param atIndex of array.
     * @return a building card.
     */
    public DevelopmentCard getBuildingCard(int atIndex){
        return this.buildingCards[atIndex];
    }

    /**
     * Set a character card.
     * @param atIndex of array.
     * @param withCard to set.
     */
    public void setCharacterCards(int atIndex, DevelopmentCard withCard){
        this.characterCards[atIndex] = withCard;
    }

    /**
     * Get a specific character card from the array.
     * @param atIndex of array.
     * @return a character card.
     */
    public DevelopmentCard getCharacterCard(int atIndex){
        return this.characterCards[atIndex];
    }

    /**
     * Set a venture card.
     * @param atIndex of array.
     * @param withCard to set.
     */
    public void setVentureCards(int atIndex, DevelopmentCard withCard){
        this.ventureCards[atIndex] = withCard;
    }

    /**
     * Get a specific venture card from the array.
     * @param atIndex of array.
     * @return a venture card.
     */
    public DevelopmentCard getVentureCard(int atIndex){
        return this.ventureCards[atIndex];
    }

}
