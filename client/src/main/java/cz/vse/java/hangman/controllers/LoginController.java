package cz.vse.java.hangman.controllers;


import cz.vse.java.hangman.ClientHandler;
import cz.vse.java.hangman.api.messages.client.ClientMessageFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @FXML
    private Label messageLabel;

    @FXML
    private TextField usernameField;



    private ClientHandler clientHandler = new ClientHandler();


    @FXML
    public void initialize() {
        logger.info("Initializing LoginController screen");
        messageLabel.setVisible(false);


    }

    @FXML
    private void handleConfirmButton(ActionEvent actionEvent) {





        String username = usernameField.getText();

        logger.info("Checking, if the username field is empty.");
        if (username.isEmpty()) {
            logger.error("Username field is empty.");
            messageLabel.setText("Prosím, zadejte uživatelské jméno.");
        } else  {
                clientHandler.send(ClientMessageFactory.createClientLoginMessage(username));

        }






    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}