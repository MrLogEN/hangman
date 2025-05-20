package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerLeaveRoomSuccessMessage;
import cz.vse.java.hangman.controllers.Registry;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Command to handle the successful leaving of a room.
 * This command is executed when the server sends a message to the client indicating that the player has successfully left the room.
 * The command updates the application state and transitions to the room selection screen.
 */

public class LeaveRoomSuccessCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(LeaveRoomSuccessCommand.class);
    private ServerLeaveRoomSuccessMessage serverLeaveRoomSuccessMessage;

    public LeaveRoomSuccessCommand(ServerLeaveRoomSuccessMessage serverLeaveRoomSuccessMessage) {
        this.serverLeaveRoomSuccessMessage = serverLeaveRoomSuccessMessage;
    }


    /**
     * Handles the successful leaving of a room by updating the application state and transitioning to the room selection screen.
     */
    @Override
    public void execute() {
        Platform.runLater(  () -> {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/rooms.fxml"));
                Scene scene = new Scene(loader.load());
                Registry.getInstance().setRoomsController(loader.getController());



                logger.info("Setting up RoomsController {}.", Registry.getInstance().getRoomsController());


                Registry.getInstance().getRoomsController().setClientHandler(Registry.getInstance().getClientHandler());
                Registry.getInstance().getRoomsController().setPlayerDTO(Registry.getInstance().getPlayerDTO());
                Registry.getInstance().getRoomsController().initData();

                Registry.getInstance().getPrimaryStage().setScene(scene);
                logger.info("Setting up and loading room selection screen {}.", scene);




            } catch (IOException e) {
                logger.error("Failed to load the next screen: {}", e.getMessage());
                throw new RuntimeException(e);

            }


        });







    }
}
