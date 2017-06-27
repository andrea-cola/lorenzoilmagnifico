package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the main board abstraction.
 */
public class MainBoard implements Serializable{

    private static final int NUMBER_OF_TOWERS = 4;
    private static final int NUMBER_OF_TOWER_CELLS = 4;
    private static final int NUMBER_OF_MARKET_CELLS = 4;

    /**
     * Array of towers.
     */
    private Tower[] towers;

    /**
     * Vatican reference.
     */
    private Vatican vatican;

    /**
     * Council palace reference.
     */
    private CouncilPalace councilPalace;

    /**
     * Market reference.
     */
    private Market market;

    /**
     * Harvest reference.
     */
    private ActionSpace harvest;

    /**
     * Production reference.
     */
    private ActionSpace production;

    /**
     * Harvest extended reference.
     */
    private ActionSpaceExtended harvestExtended;

    /**
     * Production extended reference.
     */
    private ActionSpaceExtended productionExtended;


    /**
     * Constructor for the main board
     */
    public MainBoard(MainBoard configuration) {
        this.towers = new Tower[configuration.towers.length];
        for(int i = 0; i < configuration.towers.length; i++) {
            int cellPerTower = configuration.towers[i].getTowerCells().length;
            this.towers[i] = new Tower(cellPerTower);
            for(int j = 0; j < cellPerTower; j++){
                this.towers[i].getTowerCell(j).setMinFamilyMemberValue(configuration.towers[i].getTowerCell(j).getMinFamilyMemberValue());
                this.towers[i].getTowerCell(j).setTowerCellImmediateEffect(configuration.towers[i].getTowerCell(j).getTowerCellImmediateEffect());
            }
        }

        this.harvest = new ActionSpace(ActionType.HARVEST, configuration.getHarvest().getActionSpaceEffect());
        this.production = new ActionSpace(ActionType.PRODUCTION, configuration.getProduction().getActionSpaceEffect());

        this.harvestExtended = new ActionSpaceExtended(ActionType.HARVEST, configuration.getHarvestExtended().getDiceValueMalus(), configuration.getHarvestExtended().getEffect());
        this.productionExtended = new ActionSpaceExtended(ActionType.PRODUCTION, configuration.getProductionExtended().getDiceValueMalus(), configuration.getProductionExtended().getEffect());

        this.councilPalace = new CouncilPalace(configuration.getCouncilPalace().getMinFamilyMemberDiceValue(), configuration.getCouncilPalace().getImmediateEffect());

        this.market = new Market(configuration.getMarket().getMarketCells().length, configuration.getMarket().getMarketCells());
        this.vatican = new Vatican(configuration.getVatican().getExcommunicationCard(), configuration.getVatican().getVictoryPointsBonusArray());
    }

    /**
     * Set card in the tower. This method is used at the beginning of all ages.
     * @param index of the tower.
     * @param cards to be set.
     */
    public void setTower(int index, ArrayList<DevelopmentCard> cards){
        int cell = 0;
        for (DevelopmentCard card : cards){
            this.towers[index].setTowerCell(cell, card);
            cell++;
        }
    }

    public void setVatican(ExcommunicationCard card){
        this.vatican.setExcommunicationCard(card);
    }

    /**
     * This method returns a specific tower of the mainBoard
     * @param index of the tower.
     * @return a specific tower.
     */
    public Tower getTower(int index){
        return this.towers[index];
    }

    public Tower[] getTowers(){
        return this.towers;
    }

    public Vatican getVatican(){
        return this.vatican;
    }

    public CouncilPalace getCouncilPalace(){
        return this.councilPalace;
    }

    public ActionSpace getHarvest(){
        return this.harvest;
    }

    public ActionSpace getProduction(){
        return this.production;
    }

    public ActionSpaceExtended getHarvestExtended(){
        return this.harvestExtended;
    }

    public ActionSpaceExtended getProductionExtended(){
        return this.productionExtended;
    }

    public Market getMarket(){
        return this.market;
    }

    public int getNumberOfTowers(){
        return NUMBER_OF_TOWERS;
    }

    public int getNumberOfTowerCells(){
        return NUMBER_OF_TOWER_CELLS;
    }

    public int getNumberOfMarketCells(){
        return NUMBER_OF_MARKET_CELLS;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int j = 0; j < towers.length; j++){
            stringBuilder.append("[TOWER " + j + " " + towers[j].getColor() + "]\n");
            for(int i = towers.length - 1; i >= 0; i--){
                stringBuilder.append("<" + i +"> ");
                if(towers[j].getTowerCell(i).getPlayerNicknameInTheCell() != null)
                    stringBuilder.append(towers[j].getTowerCell(i).getPlayerNicknameInTheCell());
                stringBuilder.append("\n");
                stringBuilder.append("Dice value: " + towers[j].getTowerCell(i).getMinFamilyMemberValue() + "\n");
                stringBuilder.append("Cell effect: " + towers[j].getTowerCell(i).getTowerCellImmediateEffect().toString() + "\n");
                stringBuilder.append("Card: " + towers[j].getTowerCell(i).getDevelopmentCard().toString() + "\n");
            }
        }
        stringBuilder.append("\n\n[COUNCIL PALACE]\n");
        stringBuilder.append("Dice value: " + councilPalace.getMinFamilyMemberDiceValue() + "\n");
        stringBuilder.append("Effect: " + councilPalace.getImmediateEffect() + "\n");
        stringBuilder.append("Player in council yet:" );
        for(Player player : councilPalace.getNewOrder())
            stringBuilder.append(" " + player.getUsername());

        // inserire vaticano

        stringBuilder.append("\n\n[MARKET]\n");
        for(int i = 0; i < market.getMarketCells().length; i++){
            stringBuilder.append("<" + i +"> ");
            if(!market.getMarketCell(i).isEmpty())
                stringBuilder.append(" ALREADY USED");
            stringBuilder.append("\n");
            stringBuilder.append("Dice value: " + market.getMarketCell(i).getMinFamilyMemberValue() + "\n");
            stringBuilder.append("Effect: " + market.getMarketCell(i).getMarketCellImmediateEffect().toString() + "\n");
        }
        stringBuilder.append("\n\n[HARVEST SIMPLE]\n");
        stringBuilder.append("Status: ");
        if(harvest.getPlayerUsername() != null)
            stringBuilder.append(harvest.getPlayerUsername() + "\n");
        else
            stringBuilder.append(" free\n");
        stringBuilder.append("Dice value: " + harvest.getActionSpaceEffect().getDiceActionValue());

        stringBuilder.append("\n\n[PRODUCTION SIMPLE]\n");
        stringBuilder.append("Status: ");
        if(production.getPlayerUsername() != null)
            stringBuilder.append(production.getPlayerUsername() + "\n");
        else
            stringBuilder.append(" free\n");
        stringBuilder.append("Dice value: " + production.getActionSpaceEffect().getDiceActionValue());

        stringBuilder.append("\n\n[HARVEST EXTENDED]\n");
        stringBuilder.append("Dice value: " + harvestExtended.getEffect().getDiceActionValue() + "\n");
        stringBuilder.append("Malus value: " + harvestExtended.getDiceValueMalus() + "\n");

        stringBuilder.append("\n\n[PRODUCTION EXTENDED]\n");
        stringBuilder.append("Dice value: " + productionExtended.getEffect().getDiceActionValue() + "\n");
        stringBuilder.append("Malus value: " + productionExtended.getDiceValueMalus() + "\n");
        return stringBuilder.toString();
    }

}