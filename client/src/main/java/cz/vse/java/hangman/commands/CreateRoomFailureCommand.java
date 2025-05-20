package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerCreateRoomFailureMessage;
import cz.vse.java.hangman.controllers.CreateRoomController;
import cz.vse.java.hangman.controllers.Registry;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command to handle the failure of creating a room.
 * This command is executed when the server sends a message to the client indicating that the player has failed to create a room.
 * The command updates the application state and displays an error message.
 */
public class CreateRoomFailureCommand implements Command {

    private ServerCreateRoomFailureMessage serverCreateRoomFailureMessage;
    private static final Logger logger = LoggerFactory.getLogger(CreateRoomFailureCommand.class);
    private CreateRoomController createRoomController;


    public CreateRoomFailureCommand(ServerCreateRoomFailureMessage serverCreateRoomFailureMessage) {
        this.serverCreateRoomFailureMessage = serverCreateRoomFailureMessage;
       createRoomController = Registry.getInstance().getCreateRoomController();
    }

    /**
     * Handles the failure of creating a room by updating the application state and displaying an error message.
     * It sets the error label in the CreateRoomController to inform the user about the failure.
     */

    @Override
    public void execute() {

        Platform.runLater(  () -> {

            logger.info("Create room failed: {}", serverCreateRoomFailureMessage.reason());
            createRoomController.setErrorLabel(serverCreateRoomFailureMessage.reason());


        });




    }
}
