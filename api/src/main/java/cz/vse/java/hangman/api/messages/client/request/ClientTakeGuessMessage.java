package cz.vse.java.hangman.api.messages.client.request;

import cz.vse.java.hangman.api.messages.Message;

/**
 * This message is sent by the client to the server when the user takes a guess.
 * @param guess
 */
public record ClientTakeGuessMessage(char guess) implements Message {
}
