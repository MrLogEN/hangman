package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerCreateRoomFailureMessage;

public class CreateRoomFailureCommand implements Command {

    private ServerCreateRoomFailureMessage serverCreateRoomFailureMessage;

    public CreateRoomFailureCommand(ServerCreateRoomFailureMessage serverCreateRoomFailureMessage) {
        this.serverCreateRoomFailureMessage = serverCreateRoomFailureMessage;
    }

    @Override
    public void execute() {
        //TODO
    }
}
