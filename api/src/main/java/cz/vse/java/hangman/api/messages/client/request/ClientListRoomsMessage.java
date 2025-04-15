package cz.vse.java.hangman.api.messages.client.request;

import cz.vse.java.hangman.api.messages.Message;

/**
 * Message sent by the client to the server to request a list of rooms.
 * @param playerName The player's name who is requesting a list of rooms. A request of a player that is not logged in
 *                  will be rejected by the server.
 */
public record ClientListRoomsMessage(String playerName) implements Message {
}
