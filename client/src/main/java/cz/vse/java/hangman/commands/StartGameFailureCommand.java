package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerStartGameFailureMessage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the failure of starting a game on the client side.
 * This command is executed when the server sends a message to the client indicating that the game failed to start.
 * The command displays an error message to the user.
 */
public class StartGameFailureCommand implements Command {

    private ServerStartGameFailureMessage serverStartGameFailureMessage;
    private static final Logger logger = LoggerFactory.getLogger(StartGameFailureCommand.class);

    public StartGameFailureCommand(ServerStartGameFailureMessage serverStartGameFailureMessage) {
        this.serverStartGameFailureMessage = serverStartGameFailureMessage;
    }

    /**
     * Displays an error message to the user indicating that the game failed to start.
     */
    @Override
    public void execute() {


        logger.error("Failed to start a game: {}", serverStartGameFailureMessage.reason());

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setHeaderText("Nepodařilo se spustit hru.");
            alert.setContentText(serverStartGameFailureMessage.reason());
            alert.showAndWait();
        });

    }
}
