package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.Effect;

import java.util.List;

public interface InformationCallback {

    void chooseCouncilPrivilege(Player player, List<Effect> privilegeList);

    void chooseDoubleCost();

}
