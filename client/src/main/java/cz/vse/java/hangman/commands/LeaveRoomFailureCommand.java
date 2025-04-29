package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerLeaveRoomFailureMessage;

public class LeaveRoomFailureCommand implements Command {

    private ServerLeaveRoomFailureMessage serverLeaveRoomFailureMessage;

    public LeaveRoomFailureCommand(ServerLeaveRoomFailureMessage serverLeaveRoomFailureMessage) {
        this.serverLeaveRoomFailureMessage = serverLeaveRoomFailureMessage;
    }

    @Override
    public void execute() {
//TODO
    }
}
