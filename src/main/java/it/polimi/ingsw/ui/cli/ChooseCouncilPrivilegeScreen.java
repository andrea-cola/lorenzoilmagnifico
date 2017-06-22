package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.utility.Debugger;

import java.io.IOException;
import java.util.List;

public class ChooseCouncilPrivilegeScreen extends BasicScreen{

    private ICallback callback;

    private List<Effect> councilPrivilegeEffects;

    ChooseCouncilPrivilegeScreen(ICallback callback, Player player, List<Effect> councilPrivilegeEffects){

        this.callback = callback;

    }

    @FunctionalInterface
    public interface ICallback{

        void sendLeaderCardsChoose();

    }
}
