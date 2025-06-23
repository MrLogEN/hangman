package cz.vse.java.hangman.controllers;

import cz.vse.java.hangman.ClientHandler;
import cz.vse.java.hangman.api.Player;
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



    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        logger.info("Initializing GameController: {}", this);
        logger.info(String.valueOf(isLeader));
        guessedLetters = new HashSet<>();
        correctLetters = new HashSet<>();
        guessNumber = 0;
        gameStarted = false;

        for (Node node : letterGridPane.getChildren()) {
            if (node instanceof Button button) {
                button.setDisable(true);
                button.setStyle("");
            }
        }

        updateStartAndConfirmButtonText();
    }


    /**
     * Updates the text of the start and confirm button based on the game state.
     */
    private void updateStartAndConfirmButtonText() {
        if (isLeader) {
            if (!gameStarted) {
                startAndConfirmGuessButton.setText("Start");
            } else {
                startAndConfirmGuessButton.setText("Potvrdit");
            }
        } else {
            startAndConfirmGuessButton.setText("Potvrdit");
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

        for (Node node : letterGridPane.getChildren()) {
            if (node instanceof Button button) {
                String text = button.getText().toUpperCase();
                if (!guessedLetters.contains(text.charAt(0))) {
                    button.setDisable(false);
                    button.setStyle("");
                }
            }
        }

        clickedButton.setDisable(true);
        clickedButton.setStyle("-fx-background-color: #90EE90;");
        guessedLetter = clickedButton.getText().charAt(0);
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
        String imagePath = "/images/stage" + guessNumber + ".png";
        java.net.URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl == null) {
            logger.error("Hangman image not found: {} or there are no guesses yet", imagePath);
            return;
        }
        ImageView newImage = new ImageView(imageUrl.toExternalForm());

        newImage.setFitWidth(hangmanPane.getPrefWidth());
        newImage.setFitHeight(hangmanPane.getPrefHeight());
        newImage.setPreserveRatio(true);
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

        for (Node node : letterGridPane.getChildren()) {
            if (node instanceof Button button) {
                button.setDisable(!gameStarted);
                button.setStyle("");
            }
        }

        updateStartAndConfirmButtonText();
        if (!gameStarted) {
            guessNumber = 0;
            hangmanStages.clear();
            hangmanPane.getChildren().clear();
            guessedWordLabel.setText("");
            logger.info("Game has not started yet. Resetting game state.");
        } else {
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





        playersListView.getItems().clear();
        PlayerDTO currentPlayer = currentGameDTO.currentPlayer();
        for (Player player : currentGameDTO.players()) {
            String playerName = player.getName();
            playersListView.getItems().add(playerName);
        }

        playersListView.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (currentPlayer != null && item.equals(currentPlayer.name())) {
                        setStyle("-fx-text-fill: green;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });


        char[] progress = currentGameDTO.wordProgress();
        StringBuilder wordBuilder = new StringBuilder();
        for (char c : progress) {
            if (c == '_' || c == '\u0000' || c == 0) {
                wordBuilder.append(" _ ");
            } else {
                wordBuilder.append(c).append(" ");
            }
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
                            button.setStyle("-fx-background-color: #90EE90;");
                        } else {
                            button.setStyle("-fx-background-color: #FF7F7F;");
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
        updateRoom();

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
        if (startAndConfirmGuessButton != null) {
            startAndConfirmGuessButton.setVisible(true);
            updateStartAndConfirmButtonText();
        }
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


    /**
     * Updates the room view with the current players.
     */
    private void updateRoom(){
        if (currentGameDTO == null) {
            logger.info("updateRoom called but currentGameDTO is null");
            return;
        }
        playersListView.getItems().clear();
        PlayerDTO currentPlayer = currentGameDTO.currentPlayer();
        for (Player player : currentGameDTO.players()) {
            String playerName = player.getName();
            playersListView.getItems().add(playerName);
        }

        playersListView.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (currentPlayer != null && item.equals(currentPlayer.name())) {
                        setStyle("-fx-text-fill: green;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

    }
}
