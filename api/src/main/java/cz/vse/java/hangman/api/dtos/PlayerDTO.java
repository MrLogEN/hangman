package cz.vse.java.hangman.api.dtos;

import cz.vse.java.hangman.api.Player;

/**
 * Data Transfer Object for Player.
 * @param name
 */
public record PlayerDTO(String name) {

    /**
     * Creates a PlayerDTO from a Player object.
     * @param player an instance of {@link Player} to map to a DTO.
     * @return a new {@link PlayerDTO} object based on the given {@link Player} object.
     */
    public static PlayerDTO fromPlayer(Player player) {
        return new PlayerDTO(player.getName());
    }
}
