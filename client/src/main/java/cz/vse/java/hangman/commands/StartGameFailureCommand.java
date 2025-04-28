package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerStartGameFailureMessage;

public class StartGameFailureCommand implements Command {

    private ServerStartGameFailureMessage serverStartGameFailureMessage;

    public StartGameFailureCommand(ServerStartGameFailureMessage serverStartGameFailureMessage) {
        this.serverStartGameFailureMessage = serverStartGameFailureMessage;
    }

    @Override
    public void execute() {

    }
}
