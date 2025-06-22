package cz.vse.java.hangman.server;

import cz.vse.java.hangman.api.Room;
import cz.vse.java.hangman.api.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class RoomManager {

    private static final Logger logger = LoggerFactory.getLogger(RoomManager.class);
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final Map<String, Player> unassignedPlayers = new ConcurrentHashMap<>();

    public synchronized void addNewPlayer(Player player) {

        if(unassignedPlayers.containsKey(player.getName()) || playerIsInRoom(player)) {
            logger.warn("Player {} alredy exists");
            return;
        }
        unassignedPlayers.put(player.getName(), player);
        logger.info("Player {} joined the server");
    }

    public void removePlayer(String name) {
        unassignedPlayers.remove(name);
    }

    public synchronized void addPlayerToRoom(String roomName, String playerName) {
        Room room = rooms.get(roomName);
        Player player = unassignedPlayers.get(playerName);

        if (room == null || player == null) {
            logger.warn("Invalid room or player: {} {}", roomName, playerName);
            return;
        }
        room.addPlayer(player);
        unassignedPlayers.remove(playerName);
        logger.info("Player {} joined room {}", playerName, roomName);

    }

    public Collection<Player> getAllUnassignedPlayers() {
        return Collections.unmodifiableCollection(unassignedPlayers.values());
    }

    public void createRoom(String roomName, String owner, int capacity) {
        if(rooms.containsKey(roomName)) {
            logger.warn("Room with the name {} alredy exists", roomName);
            return;
        }
        if(capacity < 1) {
            logger.warn("Capacity cannot be lower than 1");
            return;
        }
        Player player = unassignedPlayers.get(owner);
        if(player == null) {
            logger.warn("Player {} does is not available or does not exist.", owner);
        }

        Room room = new Room(roomName, capacity, player);

        rooms.put(room.getName(), room);
    }

    public Room getRoom(String roomName) {
        return rooms.get(roomName);
    }

    public Collection<Room> getAllRooms() {
        return Collections.unmodifiableCollection(rooms.values());
    }

    public boolean playerIsInRoom(Player player) {
        return rooms.values().stream()
            .anyMatch(room -> room.getPlayers().contains(player));
    }

    public synchronized Player findPlayer(String name) {
        Player player = unassignedPlayers.get(name);
        if(player == null) {
            for (Room room: rooms.values()) {
                for (Player p: room.getPlayers()) {
                    if(p.getName().equals(name)) {
                        player = p;
                    }
                }
            }
        }
        return player;
    }

}
