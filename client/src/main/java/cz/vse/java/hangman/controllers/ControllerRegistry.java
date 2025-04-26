package cz.vse.java.hangman.controllers;


import cz.vse.java.hangman.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerRegistry {
    private static ControllerRegistry instance;

    private LoginController loginController;
    private RoomsController roomsController;
    private CreateRoomController createRoomController;
    private GameController gameController;

    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private ControllerRegistry() {

    }
    public static ControllerRegistry getInstance() {
        if (instance == null) {
            instance = new ControllerRegistry();
            logger.info("Initializing ControllerRegistry");
        }
        return instance;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public RoomsController getRoomsController() {
        return roomsController;
    }

    public void setRoomsController(RoomsController roomsController) {
        this.roomsController = roomsController;
    }

    public CreateRoomController getCreateRoomController() {
        return createRoomController;
    }

    public void setCreateRoomController(CreateRoomController createRoomController) {
        this.createRoomController = createRoomController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
