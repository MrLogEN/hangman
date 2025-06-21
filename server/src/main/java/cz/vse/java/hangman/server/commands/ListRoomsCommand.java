package cz.vse.java.hangman.server.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.client.request.ClientListRoomsMessage;
import cz.vse.java.hangman.server.ClientHandler;
import cz.vse.java.hangman.server.RoomManager;

public class ListRoomsCommand implements Command{

    private final RoomManager roomManager;
    private final ClientListRoomsMessage message;
    private final ClientHandler handler;

    public ListRoomsCommand(
        RoomManager roomManager,
        ClientListRoomsMessage message,
        ClientHandler handler
    ){
        this.roomManager = roomManager;
        this.message = message;
        this.handler = handler;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}
