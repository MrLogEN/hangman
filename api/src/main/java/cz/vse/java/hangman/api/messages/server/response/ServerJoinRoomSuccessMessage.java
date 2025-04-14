package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.dtos.RoomDTO;

public record ServerJoinRoomSuccessMessage(RoomDTO room) {
}
