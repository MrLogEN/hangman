package cz.vse.java.hangman.server.commands;

import cz.vse.java.hangman.api.Room;
import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.client.request.ClientCreateRoomMessage;
import cz.vse.java.hangman.api.messages.server.ServerMessageFactory;
import cz.vse.java.hangman.server.ClientHandler;
import cz.vse.java.hangman.server.RoomManager;

public class CreateRoomCommand implements Command{

    private final RoomManager roomManager;
    private final ClientCreateRoomMessage message;
    private final ClientHandler handler;

    public CreateRoomCommand(
        ClientCreateRoomMessage message,
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
            response = ServerMessageFactory.createServerCreateRoomFailureMessage("You must be logged in to create a room.");
            handler.addMessageToQueue(response);
            return;
        }
        if(roomManager.getRoom(message.roomName()) != null) {
            response = ServerMessageFactory
                .createServerCreateRoomFailureMessage("The room with the name: " + message.roomName() + " already exits.");
            handler.addMessageToQueue(response);
            return;
        }

        Room result = roomManager.createRoom(message.roomName(), message.owner(), message.capacity());
        if(result == null) {
            response = ServerMessageFactory.createServerCreateRoomFailureMessage("The capacity is probably too low, capacity must be >= 1.");
        }
        else {
            response = ServerMessageFactory.createServerCreateRoomSuccessMessage(result);
        }

        handler.addMessageToQueue(response);
    }

}
