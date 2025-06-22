package cz.vse.java.hangman.server.commands;

import java.util.HashSet;
import java.util.Set;

import cz.vse.java.hangman.api.Room;
import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.client.request.ClientListRoomsMessage;
import cz.vse.java.hangman.api.messages.server.ServerMessageFactory;
import cz.vse.java.hangman.server.ClientHandler;
import cz.vse.java.hangman.server.RoomManager;

public class ListRoomsCommand implements Command{

    private final RoomManager roomManager;
    private final ClientListRoomsMessage message;
    private final ClientHandler handler;

    public ListRoomsCommand(
        ClientListRoomsMessage message,
        RoomManager roomManager,
        ClientHandler handler
    ){
        this.roomManager = roomManager;
        this.message = message;
        this.handler = handler;
    }

    @Override
    public void execute() {
        Message response = null;
        if(handler.getPlayer() == null) {
            response = ServerMessageFactory.createServerListRoomsFailureMessage("Client must be logged in");
        }

        Set<Room> rooms = null; 
        synchronized (roomManager.getAllRooms()) {
            rooms = new HashSet<>(roomManager.getAllRooms());
        }
        if(rooms == null) {
            rooms = new HashSet<>();
        }
        
        response = ServerMessageFactory.createServerListRoomsSuccessMessage(rooms);

        handler.addMessageToQueue(response);
    }

}
