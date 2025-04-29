package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerLoginFailureMessage;
import cz.vse.java.hangman.controllers.ControllerRegistry;
import cz.vse.java.hangman.controllers.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFailureCommand implements Command {
   private final LoginController loginController = ControllerRegistry.getInstance().getLoginController();
    private static final Logger logger = LoggerFactory.getLogger(LoginFailureCommand.class);
  private ServerLoginFailureMessage serverLoginFailureMessage;

    public LoginFailureCommand(ServerLoginFailureMessage serverLoginFailureMessage) {
        this.serverLoginFailureMessage = serverLoginFailureMessage;
    }




    @Override
    public void execute() {

        //TODO nepodarilo se napojit na server (asi switch s duvodama na zaklade stringu co prijde)
            logger.info("Login failed. Username already exists.");
            loginController.getMessageLabel().setVisible(true);
            loginController.getMessageLabel().setText("Uživatelské jméno již existuje.");
            logger.info("Waiting for user to enter a new username.");
    }
}
