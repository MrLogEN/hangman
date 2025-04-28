package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerCreateRoomSuccessMessage;

public class CreateRoomSuccessCommand implements Command {

    private ServerCreateRoomSuccessMessage serverCreateRoomSuccessMessage;

    public CreateRoomSuccessCommand(ServerCreateRoomSuccessMessage serverCreateRoomSuccessMessage) {
        this.serverCreateRoomSuccessMessage = serverCreateRoomSuccessMessage;
    }

    @Override
    public void execute() {

    }
}
