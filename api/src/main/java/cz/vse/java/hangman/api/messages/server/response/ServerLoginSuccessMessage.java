package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.dtos.PlayerDTO;
import cz.vse.java.hangman.api.messages.Message;

/**
 * Server's response when the client successfully logs in.
 * @param playerDTO the player data transfer object containing the player's information.
 */
public record ServerLoginSuccessMessage(PlayerDTO playerDTO) implements Message {
}
