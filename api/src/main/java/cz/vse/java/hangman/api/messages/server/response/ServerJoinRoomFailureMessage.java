package cz.vse.java.hangman.api.messages.server.response;

/**
 * Message sent by the server to the client when the client requests to join a room but the request fails.
 * @param reason
 */
public record ServerJoinRoomFailureMessage(String reason) {
}
