package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerLeaveRoomFailureMessage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the failure of leaving a room on the client side.
 * This command is executed when the server sends a message to the client indicating that leaving the room failed.
 * The command displays an error message to the user.
 */

public class LeaveRoomFailureCommand implements Command {

    private ServerLeaveRoomFailureMessage serverLeaveRoomFailureMessage;
    private static final Logger logger = LoggerFactory.getLogger(LeaveRoomFailureCommand.class);


    public LeaveRoomFailureCommand(ServerLeaveRoomFailureMessage serverLeaveRoomFailureMessage) {
        this.serverLeaveRoomFailureMessage = serverLeaveRoomFailureMessage;
    }

    /**
     * Displays an error message to the user indicating that leaving the room failed.
     * The message is displayed in a pop-up alert dialog.
     */

    @Override
    public void execute() {

        logger.error("Failed to leave room: {}", serverLeaveRoomFailureMessage.reason());

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setHeaderText("Nepodařilo se opustit místnost");
            alert.setContentText(serverLeaveRoomFailureMessage.reason());
            alert.showAndWait();
        });


    }
}
