package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerStartGameSuccessMessage;

public class StartGameSuccessCommand implements Command {

    private ServerStartGameSuccessMessage serverStartGameSuccessMessage;

    public StartGameSuccessCommand(ServerStartGameSuccessMessage serverStartGameSuccessMessage) {
        this.serverStartGameSuccessMessage = serverStartGameSuccessMessage;
    }

    @Override
    public void execute() {
//TODO
    }
}
