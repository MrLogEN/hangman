package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.request.ServerSyncClientRoomMessage;
import cz.vse.java.hangman.controllers.Registry;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Command to synchronize the client room with the server room.
 * This command is executed when the server sends a message to the client to synchronize the room.
 * The command updates the room state on the client side based on the information received from the server.
 */
public class SyncClientRoomCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SyncClientRoomCommand.class);
    private ServerSyncClientRoomMessage serverSyncClientRoomMessage;


    public SyncClientRoomCommand(ServerSyncClientRoomMessage serverSyncClientRoomMessage) {
        this.serverSyncClientRoomMessage = serverSyncClientRoomMessage;
    }

    /**
     * Uses update method in GameController to update the room state
     */
    @Override
    public void execute() {
        Platform.runLater(  () -> {
            Registry.getInstance().getGameController().setRoomDTO(serverSyncClientRoomMessage.roomDto());
             logger.info("Synchronizing room with new RoomDTO");
        });
    }
}
