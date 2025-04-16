package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.dtos.GuessDTO;
import cz.vse.java.hangman.api.messages.Message;

/**
 * This message is sent back to the user who took the guess.
 * @param guessDTO the guess that was taken.
 */
public record ServerTakeGuessSuccessMessage(GuessDTO guessDTO) implements Message {
}
