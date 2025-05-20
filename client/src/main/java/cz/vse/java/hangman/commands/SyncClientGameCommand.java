package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.request.ServerSyncClientGameMessage;
import cz.vse.java.hangman.controllers.Registry;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cz.vse.java.hangman.api.Game.GameState.LOSE;
import static cz.vse.java.hangman.api.Game.GameState.WIN;


/**
 * Command to synchronize the client game with the server game.
 * This command is executed when the server sends a message to the client to synchronize the game.
 * The command updates the game state on the client side based on the information received from the server.
 */

public class SyncClientGameCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SyncClientGameCommand.class);

    private ServerSyncClientGameMessage serverSyncClientGameMessage;


    public SyncClientGameCommand(SyncClientGameCommand syncClientGameCommand) {
        this.serverSyncClientGameMessage = syncClientGameCommand.serverSyncClientGameMessage;
    }

    /**
     * Uses update method in GameController to update the game state
     */
    @Override
    public void execute() {


        Platform.runLater(  () -> {
            Registry.getInstance().getGameController().setCurrentGameDTO(serverSyncClientGameMessage.gameDto());

            if (serverSyncClientGameMessage.gameDto().gameState() == WIN || serverSyncClientGameMessage.gameDto().gameState() == LOSE) {
                Registry.getInstance().getGameController().setGameStarted(false);
            }
            logger.info("Synchronizing game with new GameDTO: {}", serverSyncClientGameMessage.gameDto());


        });




    }
}
