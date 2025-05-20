package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerCreateRoomSuccessMessage;
import cz.vse.java.hangman.controllers.CreateRoomController;
import cz.vse.java.hangman.controllers.Registry;
import cz.vse.java.hangman.controllers.RoomsController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Command to handle the successful creation of a room.
 * This command is executed when the server sends a message to the client indicating that the player has successfully created a room.
 * The command updates the application state and transitions to the game screen.
 */
public class CreateRoomSuccessCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CreateRoomSuccessCommand.class);
    private ServerCreateRoomSuccessMessage serverCreateRoomSuccessMessage;
    private CreateRoomController createRoomController;
    private RoomsController roomsController;



    public CreateRoomSuccessCommand(ServerCreateRoomSuccessMessage serverCreateRoomSuccessMessage) {
        this.serverCreateRoomSuccessMessage = serverCreateRoomSuccessMessage;
        createRoomController = Registry.getInstance().getCreateRoomController();
        roomsController = Registry.getInstance().getRoomsController();
    }


    /**
     * Handles the successful creation of a room by updating the application state and transitioning to the game screen.
     * It closes the create room window and opens the game screen with the room details.
     */
    @Override
    public void execute() {


        Platform.runLater(  () -> {
            logger.info("Create room success: {}", serverCreateRoomSuccessMessage.roomDTO());
            createRoomController.closeWindow();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/game.fxml"));
                Scene scene = new Scene(loader.load());
                Registry.getInstance().setGameController(loader.getController());
                Registry.getInstance().getGameController().setClientHandler(Registry.getInstance().getClientHandler());


                Registry.getInstance().getGameController().setRoomDTO(serverCreateRoomSuccessMessage.roomDTO());
                logger.info("Setting up roomDTO: {} in GameController.", Registry.getInstance().getGameController().getRoomDTO());

                Registry.getInstance().getGameController().setPlayerDTO(Registry.getInstance().getPlayerDTO());
                logger.info("Setting up PlayerDTO: {} in GameController.", Registry.getInstance().getGameController().getPlayerDTO());


                if (serverCreateRoomSuccessMessage.roomDTO().leader().name().equals(Registry.getInstance().getPlayerDTO().name())) {
                    Registry.getInstance().getGameController().setLeader(true);
                } else {
                    Registry.getInstance().getGameController().setLeader(false);
                }

                Registry.getInstance().getPrimaryStage().setScene(scene);

            } catch (IOException e) {
                logger.error("Failed to load the game screen: {}", e.getMessage());
                throw new RuntimeException(e);

            }



        });





    }
}
