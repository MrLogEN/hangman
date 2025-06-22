package cz.vse.java.hangman.api.messages.server.request;

import cz.vse.java.hangman.api.dtos.RoomDTO;
import cz.vse.java.hangman.api.messages.Message;

/**
 * This message is sent by the server to all clients in a room/game to update their game state.
 * @param gameDto the game state to be updated. This object can be used for visualization of the game.
 */
public record ServerSyncClientRoomMessage(RoomDTO roomDto) implements Message {
}
