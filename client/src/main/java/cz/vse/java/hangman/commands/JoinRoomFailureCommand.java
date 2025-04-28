package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerJoinRoomFailureMessage;

public class JoinRoomFailureCommand implements Command {

    private ServerJoinRoomFailureMessage serverJoinRoomFailureMessage;

    public JoinRoomFailureCommand(ServerJoinRoomFailureMessage serverJoinRoomFailureMessage) {
        this.serverJoinRoomFailureMessage = serverJoinRoomFailureMessage;
    }

    @Override
    public void execute() {

    }
}
