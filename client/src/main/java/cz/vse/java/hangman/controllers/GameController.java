package cz.vse.java.hangman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class GameController {

    @FXML
    private Button readyAndConfirmGuessButton;
    @FXML
    private Label guessedWordLabel;
    @FXML
    private ListView playersListView;
    @FXML
    private ImageView hangmanImageView;
    @FXML
    private Button leaveRoomButton;

/*
    @FXML
    private Button letterAButton;
    @FXML
    private Button letterBButton;
    @FXML
    private Button letterCButton;
    @FXML
    private Button letterDButton;
    @FXML
    private Button letterEButton;
    @FXML
    private Button letterFButton;
    @FXML
    private Button letterGButton;
    @FXML
    private Button letterHButton;
    @FXML
    private Button letterIButton;
    @FXML
    private Button letterJButton;
    @FXML
    private Button letterKButton;
    @FXML
    private Button letterLButton;
    @FXML
    private Button letterMButton;
    @FXML
    private Button letterNButton;
    @FXML
    private Button letterOButton;
    @FXML
    private Button letterPButton;
    @FXML
    private Button letterQButton;
    @FXML
    private Button letterRButton;
    @FXML
    private Button letterSButton;
    @FXML
    private Button letterTButton;
    @FXML
    private Button letterUButton;
    @FXML
    private Button letterVButton;
    @FXML
    private Button letterWButton;
    @FXML
    private Button letterXButton;
    @FXML
    private Button letterYButton;
    @FXML
    private Button letterZButton;
*/


    @FXML
    public void initialize() {
        //TODO
    }

    @FXML
    private void handleLetterClick(ActionEvent actionEvent) {
        Button clickedButton = (Button) actionEvent.getSource();
        String letter = clickedButton.getText();

        //TODO Handle the letter click event

    }

    @FXML
    private void handleLeaveRoomButton(ActionEvent actionEvent) {
        //TODO
    }



    @FXML
    private void handleReadyAndConfirmGuessButton(ActionEvent actionEvent) {
        //TODO This should also work as a comfirm after player clicked word button,
        // however it may not be necessary

    }
}
