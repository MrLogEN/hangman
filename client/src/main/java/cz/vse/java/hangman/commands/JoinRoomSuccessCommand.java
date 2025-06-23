package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerJoinRoomSuccessMessage;
import cz.vse.java.hangman.controllers.Registry;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Command to handle the successful joining of a room.
 * This command is executed when the server sends a message to the client indicating that the player has successfully joined a room.
 * The command updates the application state and transitions to the game screen.
 */

public class JoinRoomSuccessCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(JoinRoomSuccessCommand.class);
    private ServerJoinRoomSuccessMessage serverJoinRoomSuccessMessage;

    public JoinRoomSuccessCommand(ServerJoinRoomSuccessMessage serverJoinRoomSuccessMessage) {
        this.serverJoinRoomSuccessMessage = serverJoinRoomSuccessMessage;
    }

    /**
     * Handles the successful joining of a room by updating the application state and transitioning to the game screen.
     */

    @Override
    public void execute() {
        Platform.runLater(  () -> {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/game.fxml"));
                Scene scene = new Scene(loader.load());
                Registry.getInstance().setGameController(loader.getController());



                logger.info("Setting up GameController {}.", Registry.getInstance().getGameController());


                Registry.getInstance().getGameController().setClientHandler(Registry.getInstance().getClientHandler());
                Registry.getInstance().getGameController().setPlayerDTO(Registry.getInstance().getPlayerDTO());
                Registry.getInstance().getGameController().setRoomDTO(serverJoinRoomSuccessMessage.room());



                Registry.getInstance().getPrimaryStage().setScene(scene);
                logger.info("Setting up and loading game screen {}.", scene);


                Registry.getInstance().getGameController().updatePlayerList();

            } catch (IOException e) {
                logger.error("Failed to load the next screen: {}", e.getMessage());
                throw new RuntimeException(e);

            }

        });






    }
}
