package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerJoinRoomFailureMessage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command to handle the failed joining of a room.
 * This command is executed when the server sends a message to the client indicating that joining the room failed.
 * The command displays an error message to the user.
 */
public class JoinRoomFailureCommand implements Command {

    private ServerJoinRoomFailureMessage serverJoinRoomFailureMessage;
    private static final Logger logger = LoggerFactory.getLogger(JoinRoomFailureCommand.class);

    public JoinRoomFailureCommand(ServerJoinRoomFailureMessage serverJoinRoomFailureMessage) {
        this.serverJoinRoomFailureMessage = serverJoinRoomFailureMessage;
    }

    /**
     * Displays an error message to the user indicating that joining the room failed.
     * The message is displayed in a pop-up alert dialog.
     */

    @Override
    public void execute() {


        logger.error("Failed to join room: {}", serverJoinRoomFailureMessage.reason());

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setHeaderText("Nepodařilo se připojit do místnosti.");
            alert.setContentText(serverJoinRoomFailureMessage.reason());
            alert.showAndWait();
        });
    }
}
