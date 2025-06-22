package cz.vse.java.hangman.server.commands;

import cz.vse.java.hangman.api.Player;
import cz.vse.java.hangman.api.Room;
import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.client.request.ClientJoinRoomMessage;
import cz.vse.java.hangman.api.messages.client.request.ClientLeaveRoomMessage;
import cz.vse.java.hangman.api.messages.server.ServerMessageFactory;
import cz.vse.java.hangman.server.ClientHandler;
import cz.vse.java.hangman.server.MessageHandler;
import cz.vse.java.hangman.server.RoomManager;

public class JoinRoomCommand implements Command{

    private final RoomManager roomManager;
    private final ClientJoinRoomMessage message;
    private final ClientHandler handler;

    public JoinRoomCommand(
        ClientJoinRoomMessage message,
        RoomManager roomManager,
        ClientHandler handler
    ){
        this.roomManager = roomManager;
        this.message = message;
        this.handler = handler;
    }

    @Override
    public synchronized void execute() {
        Message response = null;
        Player player = handler.getPlayer();
        if(player == null) {
            response = ServerMessageFactory.createServerJoinRoomFailureMessage("You must be logged in to be able to join a room.");
            handler.addMessageToQueue(response);
            return;
        }

        if(roomManager.playerIsInRoom(player)) {
            response = ServerMessageFactory.createServerJoinRoomFailureMessage("You are already in a room.");
            handler.addMessageToQueue(response);
            return;
        }

        Room room = roomManager.getRoom(message.roomName());
        if(room == null) {
            response = ServerMessageFactory
            .createServerJoinRoomFailureMessage("The room with the name: " + message.roomName() + " doesn't exit.");
            handler.addMessageToQueue(response);
            return;
        }
        int actualSize = room.getPlayers().size();
        int maxSize = room.getMaxPlayers();

        if(actualSize >= maxSize) {
            response = ServerMessageFactory
            .createServerJoinRoomFailureMessage("The room is already full " + actualSize + "/" + maxSize + ".");
            handler.addMessageToQueue(response);
            return;
        }

        response = ServerMessageFactory.createServerJoinRoomSuccessMessage(room);

        handler.addMessageToQueue(response);
        roomManager.notifyAllFromRoom(room, ServerMessageFactory.createServerSyncClientRoomMessage(room));
    }

}
