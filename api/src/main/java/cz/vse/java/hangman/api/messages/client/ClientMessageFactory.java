package cz.vse.java.hangman.api.messages.client;

import cz.vse.java.hangman.api.dtos.PlayerDTO;
import cz.vse.java.hangman.api.dtos.RoomDTO;
import cz.vse.java.hangman.api.messages.client.request.*;

public class ClientMessageFactory {
    private ClientMessageFactory(){}

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
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.client.request.ClientListRoomsMessage}.
     * @param playerDTO the player who requests the list of rooms.
     * @return a new instance of {@link ClientListRoomsMessage}.
     */
    public static ClientListRoomsMessage createClientListRoomsMessage(PlayerDTO playerDTO) {
        return new ClientListRoomsMessage(playerDTO);
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
     * Creates an instance of {@link cz.vse.java.hangman.api.messages.client.request.ClientStartGameMessage}.
     * @param playerDTO the sender, must be the owner of the room.
     * @param roomDTO the {@link RoomDTO} object representing the room.
     * @return a new instance of {@link ClientStartGameMessage}.
     */
    public static ClientStartGameMessage createClientStartGameMessage(PlayerDTO playerDTO, RoomDTO roomDTO) {
        return new ClientStartGameMessage(playerDTO, roomDTO);
    }

    public static ClientTakeGuessMessage createClientTakeGuessMessage(char guess) {
        return new ClientTakeGuessMessage(guess);
    }

}
