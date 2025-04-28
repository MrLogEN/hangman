package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerLoginSuccessMessage;
import cz.vse.java.hangman.controllers.ControllerRegistry;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginSuccessCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessCommand.class);

    private ServerLoginSuccessMessage serverloginSuccessMessage;

    public LoginSuccessCommand(ServerLoginSuccessMessage serverloginSuccessMessage) {
        this.serverloginSuccessMessage = serverloginSuccessMessage;
    }

    @Override
    public void execute() {
        logger.info("Login successful. Proceeding to the next screen.");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/rooms.fxml"));
            Scene scene = new Scene(loader.load());
            ControllerRegistry.getInstance().setRoomsController(loader.getController());
            if (ControllerRegistry.getInstance().getRoomsController() == loader.getController()) {
                logger.info("RoomsController successfully saved to ControllerRegistry.");
            } else {
                logger.error("Failed to save RoomsController to ControllerRegistry.");
            }





            ControllerRegistry.getInstance().getRoomsController().setClientHandler(ControllerRegistry.getInstance().getLoginController().getClientHandler());
        //TODO IF


            logger.info("Setting up and loading room selection screen.");
            ControllerRegistry.getInstance().getPrimaryStage().setScene(scene);

       ControllerRegistry.getInstance().getRoomsController().setPlayerDTO(serverloginSuccessMessage.playerDTO());
       logger.info("Saving PlayerDTO into RoomsController.");

        } catch (IOException e) {
            logger.error("Failed to load the next screen: {}", e.getMessage());
            throw new RuntimeException(e);

        }






    }
}
