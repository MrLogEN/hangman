package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.messages.Message;

/**
 * Server's response to {@link cz.vse.java.hangman.api.messages.client.request.ClientLoginMessage}
 * when the login failed.
 *
 * @param reason the reason for the failure.
 */
public record ServerLoginFailureMessage(String reason) implements Message {
}
