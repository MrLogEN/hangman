package cz.vse.java.hangman.server.commands;

import cz.vse.java.hangman.api.Game;
import cz.vse.java.hangman.api.Player;
import cz.vse.java.hangman.api.Room;
import cz.vse.java.hangman.api.WordGenerator;
import cz.vse.java.hangman.api.Game.GameState;
import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.client.request.ClientStartGameMessage;
import cz.vse.java.hangman.api.messages.server.ServerMessageFactory;
import cz.vse.java.hangman.server.ClientHandler;
import cz.vse.java.hangman.server.RoomManager;
import cz.vse.java.hangman.server.StaticWordGenerator;

public class StartGameCommand implements Command{

    private final RoomManager roomManager;
    private final ClientStartGameMessage message;
    private final ClientHandler handler;
    private final WordGenerator generator;

    public StartGameCommand(
        ClientStartGameMessage message,
        RoomManager roomManager,
        ClientHandler handler
    ){
        this.roomManager = roomManager;
        this.message = message;
        this.handler = handler;
        this.generator = new StaticWordGenerator();
    }

    @Override
    public void execute() {
        Message response = null;
        String playerName = message.playerName();
        String roomName = message.roomName();

        Room room = roomManager.getRoom(roomName);
        Player player = handler.getPlayer();
        if(player == null) {
            response = ServerMessageFactory.createServerStartGameFailureMessage("You must be logged in.");
            handler.addMessageToQueue(response);
            return;
        }

        if(room == null) {
            response = ServerMessageFactory.createServerStartGameFailureMessage("The room doesn't exist.");
            handler.addMessageToQueue(response);
            return;
        }

        if(!room.getLeader().equals(player)) {
            response = ServerMessageFactory.createServerStartGameFailureMessage("You must be the room's leader to start the game.");
            handler.addMessageToQueue(response);
            return;
        }

        Game game = room.getGame();
        if(game != null || game.getGameState() == GameState.PLAYING) {
            response = ServerMessageFactory.createServerStartGameFailureMessage("The game is alredy running.");
            handler.addMessageToQueue(response);
            return;
        }

        //TODO: create real generator
        game = new Game(room, 10, generator);

        roomManager.startGame(room, game);

        response = ServerMessageFactory.createServerStartGameSuccessMessage(game);
        handler.addMessageToQueue(response);

        roomManager.notifyAllFromRoom(room, ServerMessageFactory.createServerSyncClientGameMessage(game));
    }

}
