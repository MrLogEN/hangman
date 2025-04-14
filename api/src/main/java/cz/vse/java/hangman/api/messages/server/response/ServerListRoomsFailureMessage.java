package cz.vse.java.hangman.api.messages.server.response;

/**
 * Message sent by the server to the client when the client requests a list of rooms but the request fails.
 * @param reason The reason why the request failed.
 */
public record ServerListRoomsFailureMessage(String reason) {
}
