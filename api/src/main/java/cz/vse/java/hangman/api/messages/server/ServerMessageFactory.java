package cz.vse.java.hangman.api.messages.server;

import cz.vse.java.hangman.api.Game;
import cz.vse.java.hangman.api.Player;
import cz.vse.java.hangman.api.Room;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapper;
import cz.vse.java.hangman.api.messages.server.response.*;
import cz.vse.java.hangman.api.dtos.*;

import java.util.HashSet;
import java.util.Set;

/**
 * This class provides methods for easy creation of concrete instances of {@link Message}
 * wrapped in {@link MessageWrapper}
 *
 */
public class ServerMessageFactory {


    private ServerMessageFactory() {
        // Prevent instantiation
    }

    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerLoginFailureMessage}.
     *
     * @param reason the reason for the failure.
     * @return a new instance of {@link ServerLoginFailureMessage}.
     */
    public static ServerLoginFailureMessage createServerLoginFailureMessage(String reason) {
        return new ServerLoginFailureMessage(reason);
    }


    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerLoginSuccessMessage}.
     * @param player the player who successfully logged in.
     * @return a new instance of {@link ServerLoginSuccessMessage}.
     */
    public static ServerLoginSuccessMessage createServerLoginSuccessMessage(Player player) {
            PlayerDTO playerDTO = PlayerDTO.fromPlayer(player);
            return new ServerLoginSuccessMessage(playerDTO);
    }


    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerLoginSuccessMessage}.
     * @param rooms the set of {@link Room} objects representing the rooms.
     * @return a new instance of {@link ServerListRoomsSuccessMessage}.
     */
    public static ServerListRoomsSuccessMessage createServerListRoomsSuccessMessage(Set<Room> rooms) {
        Set<RoomDTO> roomDtos = new HashSet<>();

        rooms.forEach(r -> {
            roomDtos.add(RoomDTO.fromRoom(r));
        });

        return new ServerListRoomsSuccessMessage(roomDtos);
    }

    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerListRoomsFailureMessage}.
     * @param reason the reason for the failure.
     * @return a new instance of {@link ServerListRoomsFailureMessage}.
     */
    public static ServerListRoomsFailureMessage createServerListRoomsFailureMessage(String reason) {
        return new ServerListRoomsFailureMessage(reason);
    }


    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerJoinRoomSuccessMessage}.
     * @param room the {@link Room} object representing the room.
     * @return a new instance of {@link ServerJoinRoomSuccessMessage}.
     */
    public static ServerJoinRoomSuccessMessage createServerJoinRoomSuccessMessage(Room room) {
        return new ServerJoinRoomSuccessMessage(RoomDTO.fromRoom(room));
    }

    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerJoinRoomFailureMessage}.
     * @param reason the reason for the failure.
     * @return a new instance of {@link ServerJoinRoomFailureMessage}.
     */
    public static ServerJoinRoomFailureMessage createServerJoinRoomFailureMessage(String reason) {
        return new ServerJoinRoomFailureMessage(reason);
    }


    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerCreateRoomSuccessMessage}.
     * @param room the {@link Room} object representing the room.
     * @return a new instance of {@link ServerCreateRoomSuccessMessage}.
     */
    public static ServerCreateRoomSuccessMessage createServerCreateRoomSuccessMessage(Room room) {
        return new ServerCreateRoomSuccessMessage(RoomDTO.fromRoom(room));
    }

    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerCreateRoomFailureMessage}.
     * @param reason the reason for the failure.
     * @return a new instance of {@link ServerCreateRoomFailureMessage}.
     */
    public static ServerCreateRoomFailureMessage createServerCreateRoomFailureMessage(String reason) {
        return new ServerCreateRoomFailureMessage(reason);
    }

    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerStartGameFailureMessage}.
     * @param reason the reason for the failure.
     * @return a new instance of {@link ServerStartGameFailureMessage}.
     */
    public static ServerStartGameFailureMessage createServerStartGameFailureMessage(String reason) {
        return new ServerStartGameFailureMessage(reason);
    }

    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerStartGameSuccessMessage}.
     * @param game the {@link Game} object representing the game.
     * @return a new instance of {@link ServerStartGameSuccessMessage}.
     */
    public static ServerStartGameSuccessMessage createServerStartGameSuccessMessage(Game game) {
        GameDTO gameDTO = GameDTO.fromGame(game);
        return new ServerStartGameSuccessMessage(gameDTO);
    }
}
