package cz.vse.java.hangman.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.server.response.ServerLeaveRoomSuccessMessage;

public class LeaveRoomSuccessCommand implements Command {

    private ServerLeaveRoomSuccessMessage serverLeaveRoomSuccessMessage;

    public LeaveRoomSuccessCommand(ServerLeaveRoomSuccessMessage serverLeaveRoomSuccessMessage) {
        this.serverLeaveRoomSuccessMessage = serverLeaveRoomSuccessMessage;
    }

    @Override
    public void execute() {
//TODO
    }
}
