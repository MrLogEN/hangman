package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.messages.Message;

/**
 * This message represents a guess that failed for whatever reason.
 * @param reason the reason why the guess failed.
 */
public record ServerTakeGuessFailureMessage(String reason) implements Message {
}
