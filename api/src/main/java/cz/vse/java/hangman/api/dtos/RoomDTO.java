package cz.vse.java.hangman.api.dtos;

import java.util.Set;

public record RoomDTO(String name, int maxPlayers, Set<PlayerDTO> playerDTOSet, PlayerDTO leader, GameDTO gameDTO) {
}
