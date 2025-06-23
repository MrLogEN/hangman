package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerTakeGuessSuccessMessage;
import cz.vse.java.hangman.controllers.Registry;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the guess on guessing client.
 */
public class TakeGuessSuccessCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(TakeGuessSuccessCommand.class);
    private ServerTakeGuessSuccessMessage serverTakeGuessSuccessMessage;

    /**
     * Instantiates a new Take guess success command.
     *
     * @param serverTakeGuessSuccessMessage the server take guess success message
     */
    public TakeGuessSuccessCommand(ServerTakeGuessSuccessMessage serverTakeGuessSuccessMessage) {
        this.serverTakeGuessSuccessMessage = serverTakeGuessSuccessMessage;
    }
    /**
     * Updates label based on GuessType
     */
    @Override
    public void execute() {

        Platform.runLater(  () -> {

            switch (serverTakeGuessSuccessMessage.guessDTO().guessType()) {
                case CORRECT:
                    Registry.getInstance().getGameController().getRightOrWrongLabel().setText(serverTakeGuessSuccessMessage.guessDTO().letter() + " je správně!");
                    logger.info("Letter: {} guess was correct: {}", serverTakeGuessSuccessMessage.guessDTO().letter(), serverTakeGuessSuccessMessage.guessDTO().guessType());
                    break;

                case INCORRECT:
                    logger.info("Letter: {} guess was incorrect: {}", serverTakeGuessSuccessMessage.guessDTO().letter(), serverTakeGuessSuccessMessage.guessDTO().guessType());
                    Registry.getInstance().getGameController().getRightOrWrongLabel().setText(serverTakeGuessSuccessMessage.guessDTO().letter() + " je špatně!");
                    break;

                case ALREADY_GUESSED:
                    logger.info("Letter: {} guess was already guessed: {}", serverTakeGuessSuccessMessage.guessDTO().letter(), serverTakeGuessSuccessMessage.guessDTO().guessType());
                    Registry.getInstance().getGameController().getRightOrWrongLabel().setText(serverTakeGuessSuccessMessage.guessDTO().letter() + " už bylo uhodnuto!");
                    break;

                default: logger.error("Unknown error occurred while taking a guess: {}", serverTakeGuessSuccessMessage.guessDTO().guessType());
                    break;
            }

        });

    }
}
