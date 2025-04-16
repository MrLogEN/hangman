package cz.vse.java.hangman.api.messages.server.response;

import cz.vse.java.hangman.api.messages.Message;

public record ServerStartGameFailureMessage(String reason) implements Message {
}
