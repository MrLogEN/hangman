package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerJoinRoomSuccessMessage;

public class JoinRoomSuccessCommand implements Command {

    private ServerJoinRoomSuccessMessage serverJoinRoomSuccessMessage;

    public JoinRoomSuccessCommand(ServerJoinRoomSuccessMessage serverJoinRoomSuccessMessage) {
        this.serverJoinRoomSuccessMessage = serverJoinRoomSuccessMessage;
    }

    @Override
    public void execute() {
        
    }
}
