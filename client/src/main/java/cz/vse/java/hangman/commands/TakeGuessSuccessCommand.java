package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerTakeGuessSuccessMessage;

public class TakeGuessSuccessCommand implements Command {

    private ServerTakeGuessSuccessMessage serverTakeGuessSuccessMessage;

    public TakeGuessSuccessCommand(ServerTakeGuessSuccessMessage serverTakeGuessSuccessMessage) {
        this.serverTakeGuessSuccessMessage = serverTakeGuessSuccessMessage;
    }

    @Override
    public void execute() {

    }
}
