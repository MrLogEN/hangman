package cz.vse.java.hangman.api.messages.client.request;

import cz.vse.java.hangman.api.dtos.PlayerDTO;
import cz.vse.java.hangman.api.messages.Message;

/**
 * Message sent by the client to the server to request to create a room.
 * @param owner The player who owns the room.
 * @param roomName The name of the room to create. Must be unique.
 * @param capacity Maximum capacity of the room. Must be greater than 0.
 */
public record ClientCreateRoomMessage(PlayerDTO owner, String roomName, int capacity) implements Message {
}
