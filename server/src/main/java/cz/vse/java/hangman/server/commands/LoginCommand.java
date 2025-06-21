package cz.vse.java.hangman.server.commands;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.client.request.ClientLoginMessage;
import cz.vse.java.hangman.server.RoomManager;

public class LoginCommand implements Command {

    private final RoomManager roomManager;
    private final ClientLoginMessage message;

    public LoginCommand(ClientLoginMessage message, RoomManager roomManager) {
        this.roomManager = new RoomManager();
        this.message = message;
    }


    @Override
    public void execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
