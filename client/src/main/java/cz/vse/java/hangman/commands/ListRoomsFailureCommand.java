package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerListRoomsFailureMessage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the failure of listing rooms on the client side.
 * This command is executed when the server sends a message to the client indicating that the room listing failed.
 * The command displays an error message to the user.
 */

public class ListRoomsFailureCommand implements Command {
    private final ServerListRoomsFailureMessage serverListRoomsFailureMessage;
    private static final Logger logger = LoggerFactory.getLogger(ListRoomsFailureCommand.class);
    public ListRoomsFailureCommand(ServerListRoomsFailureMessage serverListRoomsFailureMessage) {
        this.serverListRoomsFailureMessage = serverListRoomsFailureMessage;
    }


    /**
     * Displays an error message to the user indicating that the room listing failed.
     * The message is displayed in a pop-up alert dialog.
     */
    @Override
    public void execute() {

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba při načítání místností");
            alert.setHeaderText(null);
            alert.setContentText(serverListRoomsFailureMessage.reason());
            alert.showAndWait();
        });

        logger.warn("Failed to load rooms: {}", serverListRoomsFailureMessage.reason());



    }
}
