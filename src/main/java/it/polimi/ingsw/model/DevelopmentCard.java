package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.EffectFinalPoints;
import it.polimi.ingsw.model.effects.EffectSimple;

/**
 * This class represent the abstraction of the development card.
 */
public class DevelopmentCard {

    /**
     * Name of the card.
     */
    private String name;

    /**
     * Card ID.
     */
    private int id;

    /**
     * Game period of the card.
     */
    private int period;

    /**
     * Color of the card. The color specifies also the type.
     */
    private DevelopmentCardColor cardColor;

    /**
     * Cost of the card in points and resources.
     */
    private PointsAndResources cost;

    /**
     * This flag indicates if the player have to choose one requisite
     * among those available.
     */
    private boolean multipleRequisiteSelectionEnabled;

    /**
     * Amount of military points required.
     */
    private int militaryPointsRequired;

    /**
     * Immediate effect of the card.
     */
    private Effect immediateEffect;

    /**
     * Permanent effect of the card.
     */
    private Effect permanentEffect;

    /**
     * Class constructor.
     */
    public DevelopmentCard(){

    }

    ///////WARNING: These methods are used just to create the cards for JSON, delete them after their creation
    public void setId(Integer id){
        this.id = id;
    }

    public void setPeriod(Integer period){
        this.period = period;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCardColor(DevelopmentCardColor color){
        this.cardColor = color;
    }

    public void setImmediateEffect(Effect effect){
        this.immediateEffect = effect;
    }

    public void setPermanentEffect(Effect effect){
        this.permanentEffect = effect;
    }

    public void setCost(PointsAndResources cost){
        this.cost = cost;
    }

    public void setCost(PointType type, Integer value){
        this.cost.increase(type, value);
    }

    public void setMultipleRequisiteSelectionEnabled(boolean flag){
        this.multipleRequisiteSelectionEnabled = flag;
    }

    public void setMilitaryPointsRequired(Integer pointsRequired){
        this.militaryPointsRequired = pointsRequired;
    }
    ///////

    /**
     * Get the card ID.
     * @return the id.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Get the period.
     * @return the period.
     */
    public int getPeriod(){
        return this.period;
    }

    /**
     * Get the effectType of the card.
     * @return the effectType.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get the color of the card.
     * @return the color.
     */
    public DevelopmentCardColor getColor(){
        return  this.cardColor;
    }

    /**
     * Get the cost to pick up the card.
     * @return the cost.
     */
    public PointsAndResources getCost(){
        return this.cost;
    }

    /**
     * Get the immediate effect of the card.
     * @return the immediate effect.
     */
    public Effect getImmediateEffect(){
        return this.immediateEffect;
    }

    /**
     * Get the permanent effect of the card.
     * @return
     */
    public Effect getPermanentEffect(){
        return this.permanentEffect;
    }

    /**
     * Get the flag of multiple requisite selection.
     * @return a boolean flag.
     */
    public boolean getMultipleRequisiteSelectionEnabled(){
        return this.multipleRequisiteSelectionEnabled;
    }

    /**
     * Get the military points required.
     * @return amount of military points.
     */
    public int getMilitaryPointsRequired(){
        return this.militaryPointsRequired;
    }
}
