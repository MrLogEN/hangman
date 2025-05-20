package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerTakeGuessFailureMessage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the failure of taking a guess on the guessing client.
 */


public class TakeGuessFailureCommand implements Command {

    private ServerTakeGuessFailureMessage serverTakeGuessFailureMessage;
    private static final Logger logger = LoggerFactory.getLogger(TakeGuessFailureCommand.class);

    public TakeGuessFailureCommand(ServerTakeGuessFailureMessage serverTakeGuessFailureMessage) {
        this.serverTakeGuessFailureMessage = serverTakeGuessFailureMessage;
    }


    /**
     * Create pop up with error message
     */
    @Override
    public void execute() {

        logger.error("Failed to take a guess: {}", serverTakeGuessFailureMessage.reason());

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setHeaderText("Chyba při zadávání písmena.");
            alert.setContentText(serverTakeGuessFailureMessage.reason());
            alert.showAndWait();
        });



    }
}
