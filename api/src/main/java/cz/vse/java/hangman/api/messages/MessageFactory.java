package cz.vse.java.hangman.api.messages;

import cz.vse.java.hangman.api.Game;
import cz.vse.java.hangman.api.Player;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapper;
import cz.vse.java.hangman.api.messages.client.request.*;
import cz.vse.java.hangman.api.messages.client.response.*;
import cz.vse.java.hangman.api.messages.server.request.*;
import cz.vse.java.hangman.api.messages.server.response.*;
import cz.vse.java.hangman.api.dtos.*;

import java.util.Set;

/**
 * This class provides methods for easy creation of concrete instances of {@link Message}
 * wrapped in {@link MessageWrapper}
 *
 */
public class MessageFactory {


    private MessageFactory() {
        // Prevent instantiation
    }
    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.client.request.ClientLoginMessage}.
     *
     * @param name the name of the player to use for login.
     * @return a new instance of {@link ClientLoginMessage}.
     */
    public static ClientLoginMessage createClientLoginMessage(String name) {
        return new ClientLoginMessage(name);
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
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.client.request.ClientListRoomsMessage}.
     * @param playerDTO the player who requests the list of rooms.
     * @return a new instance of {@link ClientListRoomsMessage}.
     */
    public static ClientListRoomsMessage createClientListRoomsMessage(PlayerDTO playerDTO) {
        return new ClientListRoomsMessage(playerDTO);
    }

    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerLoginSuccessMessage}.
     * @param roomDTOS the set of {@link RoomDTO} objects representing the rooms.
     * @return a new instance of {@link ServerListRoomsSuccessMessage}.
     */
    public static ServerListRoomsSuccessMessage createServerListRoomsSuccessMessage(Set<RoomDTO> roomDTOS) {
        return new ServerListRoomsSuccessMessage(roomDTOS);
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
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.client.request.ClientJoinRoomMessage}.
     * @param player the player who wants to join the room.
     * @param roomName the name of the room to join.
     * @return a new instance of {@link ClientJoinRoomMessage}.
     */
    public static ClientJoinRoomMessage createClientJoinRoomMessage(PlayerDTO player, String roomName) {
        return new ClientJoinRoomMessage(player, roomName);
    }

    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerJoinRoomSuccessMessage}.
     * @param roomDTO the {@link RoomDTO} object representing the room.
     * @return a new instance of {@link ServerJoinRoomSuccessMessage}.
     */
    public static ServerJoinRoomSuccessMessage createServerJoinRoomSuccessMessage(RoomDTO roomDTO) {
        return new ServerJoinRoomSuccessMessage(roomDTO);
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
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.client.request.ClientCreateRoomMessage}.
     * @param player the owner of the room.
     * @param roomName a unique name of the room.
     * @param capacity the maximum number of players in the room.
     * @return a new instance of {@link ClientCreateRoomMessage}.
     */
    public static ClientCreateRoomMessage createClientCreateRoomMessage(
            PlayerDTO player,
            String roomName,
            int capacity
    ) {
        return new ClientCreateRoomMessage(player, roomName, capacity);
    }

    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerCreateRoomSuccessMessage}.
     * @param roomDTO the {@link RoomDTO} object representing the room.
     * @return a new instance of {@link ServerCreateRoomSuccessMessage}.
     */
    public static ServerCreateRoomSuccessMessage createServerCreateRoomSuccessMessage(RoomDTO roomDTO) {
        return new ServerCreateRoomSuccessMessage(roomDTO);
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
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.client.request.ClientStartGameMessage}.
     * @param playerDTO the sender, must be the owner of the room.
     * @param roomDTO the {@link RoomDTO} object representing the room.
     * @return a new instance of {@link ClientStartGameMessage}.
     */
    public static ClientStartGameMessage createClientStartGameMessage(PlayerDTO playerDTO, RoomDTO roomDTO) {
        return new ClientStartGameMessage(playerDTO, roomDTO);
    }

    /**
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.server.response.ServerStartGameFailureMessage}.
     * @param reason the reason for the failure.
     * @return a new instance of {@link ServerStartGameFailureMessage}.
     */
    public static ServerStartGameFailureMessage createServerStartGameFailureMessage(String reason) {
        return new ServerStartGameFailureMessage(reason);
    }

    public static ServerStartGameSuccessMessage createServerStartGameSuccessMessage(Game game) {
        GameDTO gameDTO = GameDTO.fromGame(game);
        return new ServerStartGameSuccessMessage(gameDTO);
    }
}
