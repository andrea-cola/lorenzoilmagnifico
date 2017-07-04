package it.polimi.ingsw.ui.cli;

import it.polimi.ingsw.exceptions.WrongCommandException;

/*package-local*/ class ExcommunicationScreen extends BasicGameScreen{

    private ExcommunicationCallback callback;

    /*package-local*/ ExcommunicationScreen(ExcommunicationCallback callback) {
        this.callback = callback;
        printScreenTitle("DO YOU WANT TO BE EXCOMMUNICATED?");
        addOption("ex", "'ex [yes/no]' to get the excommunication.", this::getExcommunicationChoice);
    }

    private void getExcommunicationChoice(String[] parameters) throws WrongCommandException{
        if(parameters.length == 1){
            if(parameters[0].equals("yes")) {
                this.callback.notifyExcommunicationChoice(true);
                return;
            }
            if(parameters[0].equals("no")) {
                this.callback.notifyExcommunicationChoice(false);
                return;
            }
        }
        throw new WrongCommandException();
    }

    public interface ExcommunicationCallback{

        void notifyExcommunicationChoice(boolean choice);

    }

}
