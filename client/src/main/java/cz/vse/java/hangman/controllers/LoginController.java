package cz.vse.java.hangman.controllers;


import cz.vse.java.hangman.ClientHandler;
import cz.vse.java.hangman.api.messages.client.ClientMessageFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  Controller for handling the login in screen and logic
 */
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @FXML
    private Label messageLabel;

    @FXML
    private TextField usernameField;


    private ClientHandler fakeClientHandler = new ClientHandler();


    private ClientHandler clientHandler;


    /**
     * Initialize.
     */
    @FXML
    public void initialize() {

        logger.info("Initializing LoginController: {}", this);

        messageLabel.setVisible(false);
        messageLabel.setStyle("-fx-text-fill: red;");


    }

    /**
     * Handle confirm button action.
     *
     * @param actionEvent the action event
     */

    @FXML
    private void handleConfirmButton(ActionEvent actionEvent) {


        String username = usernameField.getText();

        Platform.runLater(  () -> {

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
              logger.info("Sending message create login message");

            }


        });




    }


    /**
     * Gets client handler.
     *
     * @return the client handler
     */
    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    /**
     * Sets client handler.
     *
     * @param clientHandler the client handler
     */
    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     * Gets message label.
     *
     * @return the message label
     */
    public Label getMessageLabel() {
        return messageLabel;
    }

    @Override
    public String toString() {
        return "LoginController{" +
                "clientHandler=" + clientHandler +
                '}';
    }
}