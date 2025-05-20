package cz.vse.java.hangman;


import cz.vse.java.hangman.controllers.Registry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for the Hangman client application.
 * This class is responsible for launching the JavaFX application and establishing a connection to the server via ClientHandler.
 */

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public final class Client extends Application {


    private static ClientHandler clientHandler;
    private static final Logger logger = LoggerFactory.getLogger(Client.class);


    /**
     * Entry point for Client application.
     * @param args
     */
    public static void main(final String[] args) {


        clientHandler = new ClientHandler();
        try {
            logger.info("Connecting to server.");
            clientHandler.connect("localhost", 12345);
        } catch (Exception e) {
            logger.error("Failed to connect to server: {}", e.getMessage());
            return;
        }

        logger.info("Launching JavaFX application.");
        launch(args);


    }

/**
     * Starts the JavaFX application.
     * @param stage The primary stage for this application.
     * @throws Exception If an error occurs during startup.
     */
    @Override
    public void start(Stage stage) throws Exception {
//
//        logger.info("Building JavaFX UI.");
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login.fxml"));
//        Scene scene = new Scene(loader.load());
//        Registry.getInstance().setLoginController(loader.getController());
//      //  logger.info("Setting up login controller {}", Registry.getInstance().getLoginController());
//
//
//
//
//        Registry.getInstance().setClientHandler(clientHandler);
//      //  logger.info("Setting up client handler {}", Registry.getInstance().getClientHandler());
//
//        Registry.getInstance().getLoginController().setClientHandler(clientHandler);
//      //  logger.info("Setting up client handler in login controller {}", Registry.getInstance().getLoginController().getClientHandler());
//
//
//        Registry.getInstance().setPrimaryStage(stage);
//
//
//
//        stage.setTitle("Hangman");
//        stage.setScene(scene);
//        stage.show();
//        logger.info("Showing login stage.");

        try {
            logger.info("Building JavaFX UI.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login.fxml"));
            Scene scene = new Scene(loader.load());
            Registry.getInstance().setLoginController(loader.getController());

            Registry.getInstance().setClientHandler(clientHandler);
            Registry.getInstance().getLoginController().setClientHandler(clientHandler);
            Registry.getInstance().setPrimaryStage(stage);

            stage.setTitle("Hangman");
            stage.setScene(scene);
            stage.show();
            logger.info("Showing login stage.");
        } catch (Exception e) {
            logger.error("Failed to start JavaFX application: {}", e.getMessage(), e);
            throw e;
        }

    }
}
