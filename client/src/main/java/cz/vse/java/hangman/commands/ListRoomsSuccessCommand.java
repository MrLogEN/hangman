package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.dtos.RoomDTO;
import cz.vse.java.hangman.api.messages.server.response.ServerListRoomsSuccessMessage;
import cz.vse.java.hangman.controllers.Registry;
import cz.vse.java.hangman.controllers.RoomsController;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Command to handle the successful listing of rooms.
 * This command is executed when the server sends a message to the client indicating that the room listing was successful.
 * The command updates the room list on the client side based on the information received from the server.
 */

public class ListRoomsSuccessCommand implements Command {

    private final ServerListRoomsSuccessMessage serverListRoomsSuccessMessage;

    private final RoomsController roomsController;

    private final Set<RoomDTO> roomDTOS;

    public ListRoomsSuccessCommand(ServerListRoomsSuccessMessage serverListRoomsSuccessMessage) {
        this.serverListRoomsSuccessMessage = serverListRoomsSuccessMessage;
        this.roomsController = Registry.getInstance().getRoomsController();
        this.roomDTOS =  serverListRoomsSuccessMessage.roomDTOS();
    }

    /**
     * Handles the successful listing of rooms by updating the room list on the client side.
     * The method clears the existing room list and adds the new rooms received from the server.
     */

    private static final Logger logger = LoggerFactory.getLogger(ListRoomsSuccessCommand.class);
    @Override
    public void execute() {
        logger.info("Received list of rooms from server: {} rooms", roomDTOS.size());

        Platform.runLater(() -> {
            try {
                roomsController.clearRooms();
                for (RoomDTO room : roomDTOS) {
                    roomsController.addRoom(room);
                    logger.info("Added room to UI: {}", room);

                }
                roomsController.addJoinButtonToTable();
                logger.info("Successfully updated room list on UI.");

            } catch (Exception e) {
                logger.error("Failed to update rooms on UI: {}", e.getMessage(), e);
            }
        });




//TODO
    }
}
