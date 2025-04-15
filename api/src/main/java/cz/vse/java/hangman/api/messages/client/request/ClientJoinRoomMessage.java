package cz.vse.java.hangman.api.messages.client.request;

import cz.vse.java.hangman.api.dtos.PlayerDTO;
import cz.vse.java.hangman.api.messages.Message;

/**
 * Message sent by the client to the server to request to join a room.
 * @param player The player who wants to join the room.
 * @param roomName The name of the room to join.
 */
public record ClientJoinRoomMessage(String player, String roomName) implements Message {
}
