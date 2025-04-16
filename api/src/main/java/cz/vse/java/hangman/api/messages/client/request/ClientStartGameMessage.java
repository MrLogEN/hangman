package cz.vse.java.hangman.api.messages.client.request;

import cz.vse.java.hangman.api.messages.Message;

/**
 * Message sent by the client to the server to start a game in a room.
 * The player who request the game start must be the owner of the room.
 * @param playerName The owner of the room.
 * @param roomName The room to start the game in.
 */
public record ClientStartGameMessage(String playerName, String roomName) implements Message {
}
