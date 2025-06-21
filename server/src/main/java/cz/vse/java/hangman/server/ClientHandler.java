package cz.vse.java.hangman.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import cz.vse.java.hangman.api.Player;

public class ClientHandler implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final RoomManager roomManager;
    private Player player;
    private Socket socket;

    public ClientHandler(Socket socket, RoomManager roomManager) {
        this.socket = socket;
        this.roomManager = roomManager;
    }

    public void setPlayer(Player player) {
        synchronized(player) {
            this.player = player;
        }
    }

    public Player getPlayer() {
        synchronized(player) {
            return player;
        }
    }

    @Override
    public void run() {

        try(ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())
        ) {
            Thread messageWriter = new Thread(new MessageWriter(outputStream, this, roomManager));
            Thread messageListener = new Thread(new MessageListener(inputStream, this, roomManager));
            messageWriter.start();
            messageListener.start();
        }
        catch (IOException e) {
            logger.error("There was an error handling a client.", e);
        }
    }
}
