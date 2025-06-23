package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerStartGameSuccessMessage;
import cz.vse.java.hangman.controllers.GameController;
import cz.vse.java.hangman.controllers.Registry;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command to handle the successful start of a game.
 * This command is executed when the server sends a message to the client indicating that the game has started successfully.
 * The command updates the game state on the client side based on the information received from the server.
 */

public class StartGameSuccessCommand implements Command {

    private ServerStartGameSuccessMessage serverStartGameSuccessMessage;
    private static final Logger logger = LoggerFactory.getLogger(StartGameSuccessCommand.class);

    private GameController gameController;

    public StartGameSuccessCommand(ServerStartGameSuccessMessage serverStartGameSuccessMessage) {
        this.serverStartGameSuccessMessage = serverStartGameSuccessMessage;
        this.gameController = Registry.getInstance().getGameController();
    }



    /**
     * Uses methods in GameController to start the game and update
     */
    @Override
    public void execute() {

        logger.info("Starting game with new GameDTO: {}", serverStartGameSuccessMessage.gameDTO());
        Platform.runLater(  () -> {

            gameController.setCurrentGameDTO(serverStartGameSuccessMessage.gameDTO());
            gameController.setGameStarted(true);


        });





    }
}
