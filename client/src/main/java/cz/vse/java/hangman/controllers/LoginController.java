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


    private ClientHandler fakeClientHandler = new ClientHandler();


    private ClientHandler clientHandler;


    @FXML
    public void initialize() {
        logger.info("Initializing LoginController screen");
        messageLabel.setVisible(false);
        messageLabel.setStyle("-fx-text-fill: red;");


    }

    @FXML
    private void handleConfirmButton(ActionEvent actionEvent) {


        String username = usernameField.getText();

        logger.info("Checking, if the username requirements.");
        if (username.isEmpty()) {
            logger.info("Username field is empty.");
            messageLabel.setText("Zadejte platné uživatelské jméno.");
            messageLabel.setVisible(true);

        } else if (username.trim().isEmpty()) {
            logger.info("Username contains only spaces.");
            messageLabel.setText("Uživatelské jméno nemůže obsahovat pouze mezery.");
            messageLabel.setVisible(true);
        }  else if (username.length() >15) {
            logger.info("Username field is too long.");
            messageLabel.setText("Uživatelské jméno je příliš dlouhé (16+).");
            messageLabel.setVisible(true);
        }
       else  {
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