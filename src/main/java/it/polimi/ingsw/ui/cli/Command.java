package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;

@FunctionalInterface
 interface Command {

    void run(String[] arguments) throws WrongCommandException;

}
