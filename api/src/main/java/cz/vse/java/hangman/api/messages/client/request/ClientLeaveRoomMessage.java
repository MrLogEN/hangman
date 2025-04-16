package cz.vse.java.hangman.api.messages.client.request;

import cz.vse.java.hangman.api.messages.Message;

/**
 * A client's request to leaver a room.
 * @param player the player's name that is trying to leave the room.
 * @param room the room's name the player is trying to leave.
 */
public record ClientLeaveRoomMessage(String player, String room) implements Message { }

