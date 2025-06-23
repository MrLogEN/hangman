package cz.vse.java.hangman.controllers;

import cz.vse.java.hangman.ClientHandler;
import cz.vse.java.hangman.api.dtos.PlayerDTO;
import cz.vse.java.hangman.api.messages.client.ClientMessageFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for pop up UI to create room.
 */
public class CreateRoomController {

    private static final Logger logger = LoggerFactory.getLogger(CreateRoomController.class);
    @FXML
    private TextField roomNameTextField;
    @FXML
    private ComboBox<Integer> roomCapacityComboBox;

    @FXML
    private Label errorLabel;


    @FXML
    private Button cancelButton;
    @FXML
    private Button createRoomButton;


    private ClientHandler clientHandler;

    private PlayerDTO playerDTO;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        logger.info("Initializing CreateRoomController: {}", this);
        setComboBox(10);



    }
    @FXML
    private void handleCreateRoomButtonClick(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            String roomName = roomNameTextField.getText();

            if (roomName == null || roomName.trim().isEmpty()) {
                errorLabel.setText("Zadejte název místnosti");
                errorLabel.setStyle("-fx-text-fill: red;");
                errorLabel.setVisible(true);
                logger.info("Room name is empty");
                return;

            }

            if (roomName.length() > 20) {
                errorLabel.setText("Název místnosti je příliš dlouhý");
                errorLabel.setStyle("-fx-text-fill: red;");
                logger.info("Room name is too long");
                errorLabel.setVisible(true);
                return;
            }

            int capacity = roomCapacityComboBox.getValue();
            logger.info("Creating room: {} with capacity: {}", roomName, capacity);

            clientHandler.send(ClientMessageFactory.createClientCreateRoomMessage(
                    playerDTO.name(),
                    roomName,
                    capacity
            ));

            closeWindow();
        });
    }


    /**
     * Handle cancel button click.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handleCancelButtonClick(ActionEvent actionEvent) {
        Platform.runLater(this::closeWindow);
        logger.info("Cancel button clicked, closing CreateRoomController");
    }


    /**
     * Sets up the combo box with values from 2 to maxCapacity.
     *
     * @param maxCapacity the max capacity
     */
    private void setComboBox(int maxCapacity) {
        for (int i = 2; i <= maxCapacity; i++) {
            roomCapacityComboBox.getItems().add(i);
        }
        roomCapacityComboBox.getSelectionModel().select(0);
        roomCapacityComboBox.setEditable(false);
        logger.info("Setting up room capacity combo box with values from 2 to {}", maxCapacity);
    }


    /**
     * Close window.
     */
    public void closeWindow() {

        Platform.runLater( () -> {

            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            logger.info("Closing CreateRoomController window");

        });


    }

    /**
     * Gets player dto.
     *
     * @return the player dto
     */
    public PlayerDTO getPlayerDTO() {
        return playerDTO;
    }

    /**
     * Sets player dto.
     *
     * @param playerDTO the player dto
     */
    public void setPlayerDTO(PlayerDTO playerDTO) {
        this.playerDTO = playerDTO;
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
     * Sets error label.
     *
     * @param text the text
     */
    public void setErrorLabel(String text) {
        errorLabel.setText(text);
        errorLabel.setStyle("-fx-text-fill: red;");

    }


    @Override
    public String toString() {
        return "CreateRoomController{" +
                "playerDTO=" + playerDTO +
                ", clientHandler=" + clientHandler +
                '}';
    }
}
