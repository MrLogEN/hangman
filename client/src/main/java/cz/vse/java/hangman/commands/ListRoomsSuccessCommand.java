package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.dtos.RoomDTO;
import cz.vse.java.hangman.api.messages.server.response.ServerListRoomsSuccessMessage;
import cz.vse.java.hangman.controllers.ControllerRegistry;
import cz.vse.java.hangman.controllers.RoomsController;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ListRoomsSuccessCommand implements Command {

    private final ServerListRoomsSuccessMessage serverListRoomsSuccessMessage;

    private final RoomsController roomsController;

    private final Set<RoomDTO> roomDTOS;

    public ListRoomsSuccessCommand(ServerListRoomsSuccessMessage serverListRoomsSuccessMessage) {
        this.serverListRoomsSuccessMessage = serverListRoomsSuccessMessage;
        this.roomsController = ControllerRegistry.getInstance().getRoomsController();
        this.roomDTOS =  serverListRoomsSuccessMessage.roomDTOS();
    }

    private static final Logger logger = LoggerFactory.getLogger(ListRoomsSuccessCommand.class);
    @Override
    public void execute() {
        logger.info("Received list of rooms from server: {} rooms", roomDTOS.size());

        Platform.runLater(() -> {
            try {
                roomsController.clearRooms();
                for (RoomDTO room : roomDTOS) {
                    roomsController.addRoom(room);
                }
                logger.info("Successfully updated room list on UI.");
            } catch (Exception e) {
                logger.error("Failed to update rooms on UI: {}", e.getMessage(), e);
            }
        });




//TODO
    }
}
