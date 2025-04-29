package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerTakeGuessFailureMessage;

public class TakeGuessFailureCommand implements Command {

    private ServerTakeGuessFailureMessage serverTakeGuessFailureMessage;

    public TakeGuessFailureCommand(ServerTakeGuessFailureMessage serverTakeGuessFailureMessage) {
        this.serverTakeGuessFailureMessage = serverTakeGuessFailureMessage;
    }

    @Override
    public void execute() {
//TODO
    }
}
