package cz.vse.java.hangman.api.dtos;

import cz.vse.java.hangman.api.Room;
import cz.vse.java.hangman.api.Player;

import java.util.HashSet;
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
        Set<PlayerDTO> players = new HashSet<>();
        for(Player p: room.getPlayers()) {
            players.add(PlayerDTO.fromPlayer(p));
        }
        if (room.getGame() != null && room.getLeader() != null) {
            return new RoomDTO(
                room.getName(),
                room.getMaxPlayers(), 
                players,
                PlayerDTO.fromPlayer(room.getLeader()),
                GameDTO.fromGame(room.getGame()));
        } 
        else if(room.getGame() == null && room.getLeader() != null) {
            return new RoomDTO(
                room.getName(),
                room.getMaxPlayers(), 
                players,
                PlayerDTO.fromPlayer(room.getLeader()),
                null);
        }
        else if(room.getGame() != null && room.getLeader() == null) {
            return new RoomDTO(
                room.getName(),
                room.getMaxPlayers(), 
                players,
                null,
                GameDTO.fromGame(room.getGame()));
        }
        else {
            return new RoomDTO(
                room.getName(),
                room.getMaxPlayers(), 
                players,
                null,
                null);

        }
    }
}
