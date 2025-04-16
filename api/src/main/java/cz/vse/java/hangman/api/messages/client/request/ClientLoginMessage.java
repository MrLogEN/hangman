package cz.vse.java.hangman.api.messages.client.request;

import cz.vse.java.hangman.api.messages.Message;

/**
 * <p>
 * A message sent by the client to the server to log in.
 * </p>
 *
 * <p>
 * Server responds with {@link cz.vse.java.hangman.api.messages.server.response.ServerLoginSuccessMessage}
 * or {@link cz.vse.java.hangman.api.messages.server.response.ServerLoginFailureMessage}.
 * </p>
 * @param name the name of the player to use for login.
 */
public record ClientLoginMessage(String name) implements Message {
}
