package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.messages.Message;

/**
 * Message sent by the server to the client when the client requests to create a room but the request fails.
 * @param reason The reason why the request failed.
 */
public record ServerCreateRoomFailureMessage(String reason) implements Message {
}
