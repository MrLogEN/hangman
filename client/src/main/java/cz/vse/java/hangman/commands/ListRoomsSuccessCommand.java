package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerListRoomsSuccessMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListRoomsSuccessCommand implements Command {

    private ServerListRoomsSuccessMessage serverListRoomsSuccessMessage;

    public ListRoomsSuccessCommand(ServerListRoomsSuccessMessage serverListRoomsSuccessMessage) {
        this.serverListRoomsSuccessMessage = serverListRoomsSuccessMessage;
    }

    private static final Logger logger = LoggerFactory.getLogger(ListRoomsSuccessCommand.class);
    @Override
    public void execute() {

    }
}
