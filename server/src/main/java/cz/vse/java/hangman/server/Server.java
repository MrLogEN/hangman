package cz.vse.java.hangman.server;

import javax.net.SocketFactory;
import java.io.IOException;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vse.java.hangman.server.commands.CommandWorkerFactory;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final RoomManager roomManager;
    private final CommandWorkerFactory workerFactory;
    private final List<ClientHandler> clients;

    public Server(){
        this.roomManager = new RoomManager();
        this.workerFactory = new CommandWorkerFactory();
        this.clients = new LinkedList<>();
    }

    public synchronized void removeClient(ClientHandler handler) {
        clients.remove(handler);
    }

    public void start(int port){
        logger.info("Starting server on port: {}", port);
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            logger.info("The server has started and is waining for connections.");
            while(true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket, roomManager, workerFactory, this);
                clients.add(client);
                client.startClient();
            }

        }
        catch (IOException e) {
            logger.error("An error occured while trying to listen on port: {}", port, e);
        }
        finally{
            for(ClientHandler client: clients) {
                client.stopClient();
            }
        }
    }
}
