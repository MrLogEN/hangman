package cz.vse.java.hangman.api.messages.client.request;

import cz.vse.java.hangman.api.dtos.PlayerDTO;
import cz.vse.java.hangman.api.messages.Message;

/**
 * Message sent by the client to the server to request a list of rooms.
 * @param playerDTO The {@link PlayerDTO} object representing the player. A request of a player that is not logged in
 *                  will be rejected by the server.
 */
public record ClientListRoomsMessage(PlayerDTO playerDTO) implements Message {
}
