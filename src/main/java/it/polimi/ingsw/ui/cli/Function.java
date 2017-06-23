package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;

interface Function {

    void doAction(String[] parameters) throws WrongCommandException;

}
