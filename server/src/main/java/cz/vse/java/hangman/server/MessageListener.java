package cz.vse.java.hangman.server;

import java.io.ObjectInputStream;

public class MessageListener implements Runnable{
    private final ObjectInputStream inputStream;
    private final ClientHandler clientHandler;
    private final RoomManager roomManager;

    public MessageListener(ObjectInputStream inputStream, ClientHandler clientHandler, RoomManager roomManager) {
        this.inputStream = inputStream;
        this.clientHandler = clientHandler;
        this.roomManager = roomManager;
    }

    @Override
    public void run() {

    }
}
