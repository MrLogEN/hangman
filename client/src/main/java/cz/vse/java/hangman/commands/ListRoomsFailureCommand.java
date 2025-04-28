package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerListRoomsFailureMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListRoomsFailureCommand implements Command {
    private ServerListRoomsFailureMessage serverListRoomsFailureMessage;

    public ListRoomsFailureCommand(ServerListRoomsFailureMessage serverListRoomsFailureMessage) {
        this.serverListRoomsFailureMessage = serverListRoomsFailureMessage;
    }

    private static final Logger logger = LoggerFactory.getLogger(ListRoomsFailureCommand.class);
    @Override
    public void execute() {

    }
}
