package cz.vse.java.hangman.server;

import javax.net.SocketFactory;
import java.io.IOException;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final RoomManager roomManager;

    public Server(RoomManager roomManager){
        this.roomManager = roomManager;
    }

    public void start(int port){
        logger.info("Starting server on port: {}", port);
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            logger.info("The server has started and is waining for connections.");
            while(true) {
                Socket socket = serverSocket.accept();
                Thread clientHandlerThread = new Thread(new ClientHandler(socket, roomManager));
                clientHandlerThread.start();
            }

        }
        catch (IOException e) {
            logger.error("An error occured while trying to listen on port: {}", port, e);
        }
    }
}
