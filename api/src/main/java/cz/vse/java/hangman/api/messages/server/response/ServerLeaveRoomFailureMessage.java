package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.messages.Message;

/**
 * This message is a server response to Client's request to leave a room,
 * but the request for some reason fails.
 * @param reason the reason why the request failed.
 */
public record ServerLeaveRoomFailureMessage(String reason) implements Message {
}
