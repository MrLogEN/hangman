package cz.vse.java.hangman;


import cz.vse.java.hangman.controllers.ControllerRegistry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public final class Client extends Application {


    private static ClientHandler clientHandler;
    private static final Logger logger = LoggerFactory.getLogger(Client.class);


    /**
     * Entry point for Client application.
     * @param args
     */
    public static void main(final String[] args) {


   /*     clientHandler = new ClientHandler();
        try {
            logger.info("Connecting to server.");
            clientHandler.connect("localhost", 12345);
        } catch (Exception e) {
            logger.error("Failed to connect to server: {}", e.getMessage());
            return;
        }



    */




        logger.info("Launching JavaFX application.");
        launch(args);


    }


    @Override
    public void start(Stage stage) throws Exception {

        logger.info("Building JavaFX UI.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login.fxml"));
        Scene scene = new Scene(loader.load());
        ControllerRegistry.getInstance().setLoginController(loader.getController());

        if (ControllerRegistry.getInstance().getLoginController() == loader.getController()) {
            logger.info("LoginController successfully saved to ControllerRegistry.");
        } else {
            logger.error("LoginController unsussessfully saved to ControllerRegistry.");
        }


        ControllerRegistry.getInstance().getLoginController().setClientHandler(clientHandler);
        if (ControllerRegistry.getInstance().getLoginController().getClientHandler() == clientHandler){
            logger.info("ClientHandler successfully saved to LoginController.");
        } else {
            logger.error("ClientHandler unsussessfully saved to LoginController.");
        }

        ControllerRegistry.getInstance().setPrimaryStage(stage);
        if (ControllerRegistry.getInstance().getPrimaryStage() == stage) {
            logger.info("PrimaryStage successfully saved to ControllerRegistry.");
        } else {
            logger.error("PrimaryStage unsussessfully saved to ControllerRegistry.");
        }

        stage.setTitle("Šibenice");
        stage.setScene(scene);
        stage.show();
        logger.info("Showing login stage.");


    }
}
