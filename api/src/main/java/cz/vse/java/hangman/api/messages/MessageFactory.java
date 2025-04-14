package cz.vse.java.hangman.api.messages;

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
}
