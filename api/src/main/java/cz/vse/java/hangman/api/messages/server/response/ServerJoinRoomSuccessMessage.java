package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.dtos.RoomDTO;
import cz.vse.java.hangman.api.messages.Message;

public record ServerJoinRoomSuccessMessage(RoomDTO room) implements Message {
}
