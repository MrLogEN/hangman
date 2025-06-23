package cz.vse.java.hangman.controllers;


import cz.vse.java.hangman.ClientHandler;
import cz.vse.java.hangman.api.dtos.PlayerDTO;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registry of important constants for the entire Client game cycle.
 * Singleton class that holds references to all controllers and other important constants.
 */
public class Registry {
  private Stage primaryStage;

    private static Registry instance;

    private LoginController loginController;
    private RoomsController roomsController;
    private CreateRoomController createRoomController;
    private GameController gameController;

    private ClientHandler clientHandler;

    private PlayerDTO playerDTO;

    private static final Logger logger = LoggerFactory.getLogger(Registry.class);
    private Registry() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Registry getInstance() {
        if (instance == null) {
            instance = new Registry();
            logger.info("Initializing ControllerRegistry");
        }
        return instance;
    }

    /**
     * Gets login controller.
     *
     * @return the login controller
     */
    public LoginController getLoginController() {
        return loginController;
    }

    /**
     * Sets login controller.
     *
     * @param loginController the login controller
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * Gets rooms controller.
     *
     * @return the rooms controller
     */
    public RoomsController getRoomsController() {
        return roomsController;
    }

    /**
     * Sets rooms controller.
     *
     * @param roomsController the rooms controller
     */
    public void setRoomsController(RoomsController roomsController) {
        this.roomsController = roomsController;
    }

    /**
     * Gets create room controller.
     *
     * @return the create room controller
     */
    public CreateRoomController getCreateRoomController() {
        return createRoomController;
    }

    /**
     * Sets create room controller.
     *
     * @param createRoomController the create room controller
     */
    public void setCreateRoomController(CreateRoomController createRoomController) {
        this.createRoomController = createRoomController;
    }

    /**
     * Gets game controller.
     *
     * @return the game controller
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Sets game controller.
     *
     * @param gameController the game controller
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Gets primary stage.
     *
     * @return the primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets primary stage.
     *
     * @param primaryStage the primary stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
}
