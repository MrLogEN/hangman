package cz.vse.java.hangman.server.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.client.request.ClientTakeGuessMessage;
import cz.vse.java.hangman.api.messages.server.ServerMessageFactory;
import cz.vse.java.hangman.server.ClientHandler;
import cz.vse.java.hangman.server.RoomManager;

public class TakeGuessCommand implements Command{

    private final RoomManager roomManager;
    private final ClientTakeGuessMessage message;
    private final ClientHandler handler;

    public TakeGuessCommand(
        ClientTakeGuessMessage message,
        RoomManager roomManager,
        ClientHandler handler
    ) {
        this.roomManager = roomManager;
        this.message = message;
        this.handler = handler;
    }

    @Override
    public void execute() {
        Message response = null;
        if(handler.getPlayer() == null) {
            response = ServerMessageFactory.createServerTakeGuessFailureMessage("You must be logged");
        }
    }

}
