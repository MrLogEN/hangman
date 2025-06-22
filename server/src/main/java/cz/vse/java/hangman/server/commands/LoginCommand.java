package cz.vse.java.hangman.server.commands;

import cz.vse.java.hangman.api.Player;
import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.client.request.ClientLoginMessage;
import cz.vse.java.hangman.api.messages.server.ServerMessageFactory;
import cz.vse.java.hangman.api.messages.server.response.ServerLoginFailureMessage;
import cz.vse.java.hangman.api.messages.server.response.ServerLoginSuccessMessage;
import cz.vse.java.hangman.server.ClientHandler;
import cz.vse.java.hangman.server.RoomManager;

public class LoginCommand implements Command {

    private final RoomManager roomManager;
    private final ClientLoginMessage message;
    private final ClientHandler handler;

    public LoginCommand(
        ClientLoginMessage message,
        RoomManager roomManager,
        ClientHandler handler
    ) {
        this.roomManager = roomManager;
        this.message = message;
        this.handler = handler;
    }


    @Override
    public void execute() {
        String playerName = message.name();
        Player player = roomManager.findPlayer(playerName);
        Message response;
        if(player != null) {
            response = ServerMessageFactory.createServerLoginFailureMessage("Player " + playerName + " is alredy logged in.");
        }
        else {
            player = new Player(playerName);
            roomManager.addNewPlayer(player);
            handler.setPlayer(player);
            response = ServerMessageFactory.createServerLoginSuccessMessage(player);
        }

        handler.addMessageToQueue(response);
    }
}
