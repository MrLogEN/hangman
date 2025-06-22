package cz.vse.java.hangman.server.commands;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import cz.vse.java.hangman.api.Guess;
import cz.vse.java.hangman.api.Player;
import cz.vse.java.hangman.api.Room;
import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.dtos.GuessDTO;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.client.request.ClientTakeGuessMessage;
import cz.vse.java.hangman.api.messages.server.ServerMessageFactory;
import cz.vse.java.hangman.server.ClientHandler;
import cz.vse.java.hangman.server.RoomManager;

public class TakeGuessCommand implements Command{

    private static final Logger logger = LoggerFactory.getLogger(TakeGuessCommand.class);
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
        Player player = handler.getPlayer();
        if(player == null) {
            response = ServerMessageFactory.createServerTakeGuessFailureMessage("You must be logged in.");
            handler.addMessageToQueue(response);
            return;
        }

        Room room = roomManager.getPlayersRoom(player);
        logger.info("The room {}",room);
        if(room == null) {
            response = ServerMessageFactory.createServerTakeGuessFailureMessage("You must be in a room with a game running to take a guess.");
            handler.addMessageToQueue(response);
            return;
        }

        if(!roomManager.isGameRunning(room)) {
            response = ServerMessageFactory.createServerTakeGuessFailureMessage("A game must be running to take a guess.");
            handler.addMessageToQueue(response);
            return;
        }
        Player currentPlayer = roomManager.currentPlayer(room);
        if(!player.equals(currentPlayer)){
            response = ServerMessageFactory.createServerTakeGuessFailureMessage("It's not your turn.");
            handler.addMessageToQueue(response);
            return;
        }

        Guess guess = roomManager.takeGuess(room, message.guess());
        if(guess == null) {
            response = ServerMessageFactory.createServerTakeGuessFailureMessage("There is no game running.");
            handler.addMessageToQueue(response);
            return;
        }

        response = ServerMessageFactory.createServerTakeGuessSuccessMessage(guess);
        handler.addMessageToQueue(response);
        roomManager.notifyAllFromRoom(room, ServerMessageFactory.createServerSyncClientGameMessage(roomManager.getRoomGame(room)));
    }

}
