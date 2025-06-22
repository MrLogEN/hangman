package cz.vse.java.hangman.server.commands;

import cz.vse.java.hangman.api.Player;
import cz.vse.java.hangman.api.Room;
import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.client.request.ClientLeaveRoomMessage;
import cz.vse.java.hangman.api.messages.server.ServerMessageFactory;
import cz.vse.java.hangman.server.ClientHandler;
import cz.vse.java.hangman.server.RoomManager;

public class LeaveRoomCommand implements Command{

    private final RoomManager roomManager;
    private final ClientLeaveRoomMessage message;
    private final ClientHandler handler;

    public LeaveRoomCommand(
        ClientLeaveRoomMessage message,
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
        String playerName = message.player();
        String roomName = message.room();

        Player player = handler.getPlayer();
        if(player == null) {
            response = ServerMessageFactory.createServerLeaveRoomFailureMessage("You must be logged in and part of a room to be able to leave one.");
            handler.addMessageToQueue(response);
            return;
        }

        Room room = roomManager.getRoom(roomName);
        if(room == null) {
            response = ServerMessageFactory.createServerLeaveRoomFailureMessage("Room:" + roomName + " doesn't exist.");
            handler.addMessageToQueue(response);
            return;
        }

        if(room.getPlayer(player) == null) {
            response = ServerMessageFactory.createServerLeaveRoomFailureMessage("You are not part of the room.");
            handler.addMessageToQueue(response);
            return;
        }

        roomManager.removePlayerFromRoom(playerName, roomName);
        roomManager.notifyAllFromRoom(room, ServerMessageFactory.createServerSyncClientRoomMessage(room));
    }

}
