package cz.vse.java.hangman.api.dtos;

import cz.vse.java.hangman.api.Room;

import java.util.Set;

/**
 * This class maps the {@link Room} class to a DTO.
 * @param name the name of the room.
 * @param maxPlayers the maximum number of players in the room.
 * @param playerDTOSet the set of players in the room.
 * @param leader the leader of the room.
 * @param gameDTO the game that is being played in the room.
 */
public record RoomDTO(String name, int maxPlayers, Set<PlayerDTO> playerDTOSet, PlayerDTO leader, GameDTO gameDTO) {
    public static RoomDTO fromRoom(Room room) {
        //#TODO: implement this method
        return null;
    }
}
