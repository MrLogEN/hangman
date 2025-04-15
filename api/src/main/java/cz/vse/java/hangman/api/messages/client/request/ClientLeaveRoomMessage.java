package cz.vse.java.hangman.api.messages.client.request;

import cz.vse.java.hangman.api.dtos.PlayerDTO;
import cz.vse.java.hangman.api.dtos.RoomDTO;
import cz.vse.java.hangman.api.messages.Message;

/**
 * A client's request to leaver a room.
 * @param player the player that is trying to leave the room.
 * @param room the room the player is trying to leave.
 */
public record ClientLeaveRoomMessage(PlayerDTO player, RoomDTO room) implements Message { }

