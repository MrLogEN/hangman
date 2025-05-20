package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerLoginSuccessMessage;
import cz.vse.java.hangman.controllers.Registry;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Command to handle the successful login of a user.
 * This command is executed when the server sends a message to the client indicating that the login was successful.
 * The command updates the application state and transitions to the next screen.
 */

public class LoginSuccessCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessCommand.class);

    private ServerLoginSuccessMessage serverloginSuccessMessage;

    public LoginSuccessCommand(ServerLoginSuccessMessage serverloginSuccessMessage) {
        this.serverloginSuccessMessage = serverloginSuccessMessage;
    }

    /**
     * Handles the successful login by updating the application state and transitioning to the next screen.
     *
     */
    @Override
    public void execute() {
        logger.info("Login successful. Proceeding to the next screen.");
        Registry.getInstance().setPlayerDTO(serverloginSuccessMessage.playerDTO());
        logger.info("Saving PlayerDTO into RoomsController {}.", Registry.getInstance().getPlayerDTO());


        Platform.runLater(() -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/rooms.fxml"));
                Scene scene = new Scene(loader.load());



                Registry.getInstance().setRoomsController(loader.getController());
                logger.info("Setting up RoomsController {}.", Registry.getInstance().getRoomsController());


                Registry.getInstance().getRoomsController().setClientHandler(Registry.getInstance().getClientHandler());
                Registry.getInstance().getRoomsController().setPlayerDTO(Registry.getInstance().getPlayerDTO());



                Registry.getInstance().getRoomsController().initData();

                logger.info("Setting up and loading room selection screen.");
                Registry.getInstance().getPrimaryStage().setScene(scene);





            } catch (IOException e) {
                logger.error("Failed to load the next screen: {}", e.getMessage());
                throw new RuntimeException(e);

            }


        });
    }
}