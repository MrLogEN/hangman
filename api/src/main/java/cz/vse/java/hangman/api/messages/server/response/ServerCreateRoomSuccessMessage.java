package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.dtos.RoomDTO;
import cz.vse.java.hangman.api.messages.Message;

/**
 * Message sent by the server to the client when the client successfully creates a room.
 * @param roomDTO The room that was created.
 */
public record ServerCreateRoomSuccessMessage(RoomDTO roomDTO) implements Message {
}
