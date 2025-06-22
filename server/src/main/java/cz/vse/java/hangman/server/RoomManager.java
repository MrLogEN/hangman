package cz.vse.java.hangman.server;

import cz.vse.java.hangman.api.Room;
import cz.vse.java.hangman.api.Game.GameState;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.server.ServerMessageFactory;
import cz.vse.java.hangman.api.Game;
import cz.vse.java.hangman.api.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomManager {

    private static final Logger logger = LoggerFactory.getLogger(RoomManager.class);
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final Map<String, Player> unassignedPlayers = new ConcurrentHashMap<>();
    private final Map<String, Player> playersInRooms = new ConcurrentHashMap<>();
    private final Map<Room, Set<ClientHandler>> handlers = new ConcurrentHashMap<>();

    public synchronized void addNewPlayer(Player player) {

        if (unassignedPlayers.containsKey(player.getName()) || playerIsInRoom(player)) {
            logger.warn("Player {} alredy exists");
            return;
        }
        unassignedPlayers.put(player.getName(), player);
        logger.info("Player {} joined the server");
    }

    public synchronized void removePlayer(String name) {
        unassignedPlayers.remove(name);
    }

    public synchronized void addPlayerToRoom(String roomName, String playerName) {
        Room room = rooms.get(roomName);
        Player player = unassignedPlayers.get(playerName);

        if (room == null || player == null) {
            logger.warn("Invalid room: {} or player: {}", roomName, playerName);
            return;
        }
        if (room.getMaxPlayers() >= room.getPlayers().size()) {
            logger.warn(
                    "The room: {} is alredy full: {}/{}",
                    roomName, room.getPlayers().size(), room.getMaxPlayers());
            return;
        }
        room.addPlayer(player);
        unassignedPlayers.remove(playerName);
        playersInRooms.put(player.getName(), player);
        logger.info("Player {} joined room {}", playerName, roomName);
    }

    public synchronized boolean removePlayerFromRoom(String playerName, String roomName) {
        Room room = rooms.get(roomName);
        if(room == null) {
            logger.info("The room: {} does not exist", roomName);
            return false;
        }
        Player player = room.getPlayer(playerName);

        if(player == null) {
            logger.info("The player: {} does not exist", playerName);
            return false;
        }

        room.removePlayer(player);
        playersInRooms.remove(playerName);
        unassignedPlayers.put(playerName, player);
        if(room.getLeader().equals(player)) {
            for (Player p: room.getPlayers()) {
                room.setLeader(p);
                logger.info("Player: {} has been promoted to leader of room: {}", p.getName(), roomName);
                break;
            }
        }
        logger.info("Player: {} removed from room: {}", playerName, roomName);
        if(room.getPlayers().size() < 1) {
            rooms.remove(room.getName());
            logger.info("Room: {} has been removed.", roomName);
        }
        return true;
    }

    public synchronized Collection<Player> getAllUnassignedPlayers() {
        return Collections.unmodifiableCollection(unassignedPlayers.values());
    }

    public synchronized Room createRoom(String roomName, String owner, int capacity) {
        if (rooms.containsKey(roomName)) {
            logger.warn("Room with the name {} alredy exists", roomName);
            return null;
        }
        if (capacity < 1) {
            logger.warn("Capacity cannot be lower than 1");
            return null;
        }
        Player player = unassignedPlayers.get(owner);
        if (player == null) {
            logger.warn("Player {} is not available or does not exist.", owner);
            return null;
        }

        Room room = new Room(roomName, capacity, player);
        unassignedPlayers.remove(player.getName());
        playersInRooms.put(player.getName(), player);

        rooms.put(room.getName(), room);
        return room;
    }

    public synchronized Room getRoom(String roomName) {
        return rooms.get(roomName);
    }

    public synchronized Collection<Room> getAllRooms() {
        return Collections.unmodifiableCollection(rooms.values());
    }

    public synchronized boolean playerIsInRoom(Player player) {
        return playersInRooms.containsKey(player.getName());
    }

    public synchronized Player findPlayer(String name) {
        Player player = unassignedPlayers.get(name);
        if (player == null) {
            player = playersInRooms.get(name);
        }
        return player;
    }

    public synchronized void notifyAllFromRoom(Room room, Message message) {
        Set<ClientHandler> clients = handlers.get(room);
        for(ClientHandler h: clients) {
            h.addMessageToQueue(message);
        }
    }

    public synchronized void takeGuess(Room room, char letter) {
        Game game = room.getGame();
        if(game == null){
            return;
        }
        game.takeGuess(letter);
    }

    public synchronized void startGame(Room room, Game game) {
        room.setGame(game);
    }

    public synchronized Player currentPlayer(Room room) {
        Game game = room.getGame();
        if(game == null) {
            return null;
        }

        if(game.getGameState() != GameState.PLAYING) {
            return null;
        }

        return game.getCurrentPlayer();
    }

}
