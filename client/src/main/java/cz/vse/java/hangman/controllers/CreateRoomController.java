package cz.vse.java.hangman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CreateRoomController {

    @FXML
    private TextField roomNameTextField;
    @FXML
    private ComboBox<Integer> roomCapacityComboBox;


    @FXML
    private Button cancelButton;
    @FXML
    private Button createRoomButton;



    @FXML
    public void initialize() {
        setComboBox(10);

        //TODO
    }
    @FXML
    private void handleCreateRoomButtonClick(ActionEvent actionEvent) {
        //TODO
    }

    @FXML
    private void handleCancelButtonClick(ActionEvent actionEvent) {
        //TODO
    }

    private void setComboBox(int maxCapacity) {
        for (int i = 2; i <= maxCapacity; i++) {
            roomCapacityComboBox.getItems().add(i);
        }
        roomCapacityComboBox.getSelectionModel().select(0);
        roomCapacityComboBox.setEditable(false);
    }
}
