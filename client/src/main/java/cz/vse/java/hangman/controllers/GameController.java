package cz.vse.java.hangman.controllers;

import cz.vse.java.hangman.ClientHandler;
import cz.vse.java.hangman.api.dtos.GameDTO;
import cz.vse.java.hangman.api.dtos.PlayerDTO;
import cz.vse.java.hangman.api.dtos.RoomDTO;
import cz.vse.java.hangman.api.messages.client.ClientMessageFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller for the actual game screen handling both game and starting game.
 */
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    @FXML
    private Label rightOrWrongLabel;
    @FXML
    private Button startAndConfirmGuessButton;
    @FXML
    private Label guessedWordLabel;
    @FXML
    private ListView<String> playersListView;
    @FXML
    private Pane hangmanPane;
    @FXML
    private Button leaveRoomButton;

    private List<ImageView> hangmanStages = new ArrayList<>();

    private ClientHandler clientHandler;

    private RoomDTO roomDTO;
    private PlayerDTO playerDTO;

    @FXML
    private GridPane letterGridPane;

    private int guessNumber;

    private boolean gameStarted;
    private boolean isLeader;

    private char guessedLetter;

    private GameDTO currentGameDTO;

   private Set<Character> guessedLetters;

   private Set<Character> correctLetters;

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


    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        logger.info("Initializing GameController: {}", this);

        guessedLetters = new HashSet<>();
        correctLetters = new HashSet<>();
        guessNumber = 0;
        gameStarted = false;

        if (!isLeader) {
            startAndConfirmGuessButton.setVisible(false);
        }


    }

    /**
     * Handle letter click.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handleLetterClick(ActionEvent actionEvent) {
        Button clickedButton = (Button) actionEvent.getSource();
        String letter = clickedButton.getText();
        guessedLetter = letter.charAt(0);


        for (Node node : letterGridPane.getChildren()) {
            if (node instanceof Button button && !button.isDisabled()) {
                button.setStyle("");
            }
        }


        clickedButton.setDisable(true);
        clickedButton.setStyle("-fx-background-color: #90EE90;");

        logger.info("Selected letter: {}", guessedLetter);
    }

    /**
     * Handle leave room button.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handleLeaveRoomButton(ActionEvent actionEvent) {

        clientHandler.send(ClientMessageFactory.createClientLeaveRoomMessage(playerDTO.name(), roomDTO.name()));
  logger.info("Sending leave room message.");

    }


    /**
     * Handle start and confirm guess button.
     *
     * @param actionEvent the action event
     */

    @FXML
    private void handleStartAndConfirmGuessButton(ActionEvent actionEvent) {

        if (!gameStarted && isLeader) {
            clientHandler.send(ClientMessageFactory.createClientStartGameMessage(playerDTO.name(), roomDTO.name()));
            logger.info("Sending start game message to the server");
        } else if (gameStarted) {
            clientHandler.send(ClientMessageFactory.createClientTakeGuessMessage(guessedLetter));
            logger.info("Sending guess: {} to the server", guessedLetter);
        }


    }


    /**
     * Update player list.
     */
    public void updatePlayerList() {
        playersListView.getItems().clear();
        for (PlayerDTO player : roomDTO.playerDTOSet()) {
            playersListView.getItems().add(player.name());
            logger.info("Adding player to list: {}", player.name());

        }

    }

    /**
     * Update hangman image based on progression of the game.
     *
     * @param guessNumber the guess number
     */
    public void updateHangmanImage(int guessNumber) {
        String imagePath = "/images/hangman/stage" + guessNumber + ".png";
        ImageView newImage = new ImageView(getClass().getResource(imagePath).toExternalForm());
        hangmanStages.add(newImage);


        hangmanPane.getChildren().clear();
        for (ImageView image : hangmanStages) {
            hangmanPane.getChildren().add(image);
            logger.info("Adding hangman image: {}", imagePath);

        }
    }


    /**
     * Sets game started and performs associated logic with starting or ending game.
     *
     * @param gameStarted the game started
     */
    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;

        if (!gameStarted) {
            guessNumber = 0;
            hangmanStages.clear();
            hangmanPane.getChildren().clear();
            guessedWordLabel.setText("");
        logger.info("Game has not started yet. Resetting game state.");

            for (Node node : letterGridPane.getChildren()) {
                if (node instanceof Button button) {
                    button.setDisable(true);
                    button.setStyle("");
                }
            }
        } else {
            for (Node node : letterGridPane.getChildren()) {
                if (node instanceof Button button) {
                    button.setDisable(false);
                    button.setStyle("");
                }
            }
            logger.info("Game has started. Enabling letter buttons.");
        }
    }


    /**
     * Update game status.
     */
    public void updateGameStatus() {
        if (currentGameDTO == null) {
            logger.warn("updateGameStatus called but currentGameDTO is null");
            return;
        }




        //TODO update player list kdo hraje

        char[] progress = currentGameDTO.wordProgress();
        StringBuilder wordBuilder = new StringBuilder();
        for (char c : progress) {
            wordBuilder.append(c).append(" ");
        }
        guessedWordLabel.setText(wordBuilder.toString().trim());
        logger.info("Updating guessed word label: {}", guessedWordLabel.getText());


        guessedLetters.clear();
        guessedLetters.addAll(currentGameDTO.guessedLetters());
        correctLetters.clear();
        for (char c : progress) {
            if (c != '_') {
                correctLetters.add(Character.toUpperCase(c));
            }
        }
        logger.info("Updating guessed letters: {}", guessedLetters);

        for (Node node : letterGridPane.getChildren()) {
            if (node instanceof Button button) {
                String text = button.getText().toUpperCase();
                if (text.length() == 1) {
                    char letter = text.charAt(0);
                    if (guessedLetters.contains(letter)) {
                        button.setDisable(true);
                        if (correctLetters.contains(letter)) {
                            button.setStyle("-fx-background-color: #90EE90;"); // Green
                        } else {
                            button.setStyle("-fx-background-color: #FF7F7F;"); // Red
                        }
                    } else {
                        button.setDisable(false);
                        button.setStyle("");
                    }
                }
            }
        }
        logger.info("Updating letter buttons based on guessed letters.");

        updateHangmanImage(currentGameDTO.wrongAttempts());
logger.info("Updating hangman image to stage: {}", currentGameDTO.wrongAttempts());
        }

    /**
     * Gets right or wrong label.
     *
     * @return the right or wrong label
     */
    public Label getRightOrWrongLabel() {
        return rightOrWrongLabel;
    }


    /**
     * Sets current game dto and updates status of the game.
     *
     * @param currentGameDTO the current game dto
     */
    public void setCurrentGameDTO(GameDTO currentGameDTO) {
        this.currentGameDTO = currentGameDTO;
        updateGameStatus();
        logger.info("Setting current game DTO: {} and updating game", currentGameDTO);
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
     * Gets room dto.
     *
     * @return the room dto
     */
    public RoomDTO getRoomDTO() {
        return roomDTO;
    }

    /**
     * Sets room dto.
     *
     * @param roomDTO the room dto
     */
    public void setRoomDTO(RoomDTO roomDTO) {
        this.roomDTO = roomDTO;
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
     * Sets leader.
     *
     * @param leader the leader
     */
    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    @Override
    public String toString() {
        return "GameController{" +
                "roomDTO=" + roomDTO +
                ", playerDTO=" + playerDTO +
                ", guessNumber=" + guessNumber +
                ", gameStarted=" + gameStarted +
                ", isLeader=" + isLeader +
                ", clientHandler=" + clientHandler +
                '}';
    }

}

