package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.dtos.GameDTO;
import cz.vse.java.hangman.api.messages.Message;

/**
 * This message is sent by the server to all client that are part of a room
 * when the game starts successfully.
 */
public record ServerStartGameSuccessMessage(GameDTO gameDTO) implements Message {
}
