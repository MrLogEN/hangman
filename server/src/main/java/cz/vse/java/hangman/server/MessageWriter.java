package cz.vse.java.hangman.server;

import java.io.ObjectOutputStream;

public class MessageWriter implements Runnable {
    private final ObjectOutputStream objectOutputStream;
    private final ClientHandler clientHandler;
    private final RoomManager roomManager;

    public MessageWriter(
        ObjectOutputStream objectOutputStream,
        ClientHandler clientHandler,
        RoomManager roomManager
    ) {
        this.objectOutputStream = objectOutputStream;
        this.clientHandler = clientHandler;
        this.roomManager = roomManager;
    }

    @Override
    public void run() {

    }
}
