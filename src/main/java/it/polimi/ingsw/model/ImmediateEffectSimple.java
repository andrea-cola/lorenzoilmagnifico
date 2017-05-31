package it.polimi.ingsw.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by lorenzo on 23/05/17.
 */
public class ImmediateEffectSimple extends ImmediateEffect{

    private CouncilPrivilege councilPrivilege;

    public ImmediateEffectSimple(){}

    public void runEffect(){
        System.out.print("run immediate effect simple");
    }

    public void setCouncilPrivilege(CouncilPrivilege privilege){
        this.councilPrivilege = councilPrivilege;
    }

    public CouncilPrivilege getCouncilPrivilege(){
        return this.getCouncilPrivilege();
    }


}
