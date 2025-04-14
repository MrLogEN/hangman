package cz.vse.java.hangman.api.dtos;

import cz.vse.java.hangman.api.Room;

import java.util.Set;

public record RoomDTO(String name, int maxPlayers, Set<PlayerDTO> playerDTOSet, PlayerDTO leader, GameDTO gameDTO) {
    public static RoomDTO fromRoom(Room room) {
        //#TODO: implement this method
        return null;
    }
}
