package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.dtos.RoomDTO;
import cz.vse.java.hangman.api.messages.Message;

import java.util.Set;

/**
 * Message sent by the server to the client when the client requests a list of rooms.
 * The message contains a set of {@link RoomDTO} objects representing the rooms.
 *
 * @param roomDTOS Set of {@link RoomDTO} objects representing the rooms.
 */
public record ServerListRoomsSuccessMessage(Set<RoomDTO> roomDTOS) implements Message {
}
