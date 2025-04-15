package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.messages.Message;

/**
 * This message is returned to the client when the request
 * to leave a room has been successful.
 */
public record ServerLeaveRoomSuccessMessage() implements Message {
}
