package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerLoginFailureMessage;
import cz.vse.java.hangman.controllers.LoginController;
import cz.vse.java.hangman.controllers.Registry;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the failure of login on the client side.
 * This command is executed when the server sends a message to the client indicating that the login failed.
 * The command displays an error message to the user.
 */

public class LoginFailureCommand implements Command {
   private final LoginController loginController = Registry.getInstance().getLoginController();
    private static final Logger logger = LoggerFactory.getLogger(LoginFailureCommand.class);
  private ServerLoginFailureMessage serverLoginFailureMessage;

    public LoginFailureCommand(ServerLoginFailureMessage serverLoginFailureMessage) {
        this.serverLoginFailureMessage = serverLoginFailureMessage;
    }


    /**
     * Displays an error message to the user indicating that the login failed.
     * The message is displayed in the login controller's message label.
     */

    @Override
    public void execute() {


            logger.info("Login failed. Username already exists.");
        Platform.runLater(() -> {
            loginController.getMessageLabel().setVisible(true);
            loginController.getMessageLabel().setText(serverLoginFailureMessage.reason());
        });
            logger.info("Waiting for user to enter a new username.");
    }
}
