package cz.vse.java.hangman.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import cz.vse.java.hangman.api.Player;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.server.commands.CommandWorkerFactory;


/**
 * This class handles incoming connection to {@link Server}
 */
public class ClientHandler {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final RoomManager roomManager;
    private final CommandWorkerFactory commandWorkerFactory;
    private final MessageHandler messageHandler;
    private final Socket socket;
    private final Server server;
    private Player player;
    private Thread messageListener;
    private Thread messageWriter;
    private BlockingQueue<Message> messageQueue;

    public ClientHandler(
        Socket socket,
        RoomManager roomManager,
        CommandWorkerFactory commandWorkerFactory,
        Server server
    ) {
        this.socket = socket;
        this.roomManager = roomManager;
        this.commandWorkerFactory = commandWorkerFactory;
        this.server = server;
        messageQueue = new LinkedBlockingQueue<>();
        messageHandler = new MessageHandler(roomManager, this);
        logger.debug("Listening for client's messages.");
    }

    /**
     * Associates {@link ClientHandler} instance with a {@link Player}.
     *
     * @param player to associate client with
     */
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

    /**
     * Stops the client
     */
    public void stopClient(){
        if(messageListener != null) {
            messageListener.interrupt();
        }
        if(messageWriter != null) {
            messageWriter.interrupt();
        }
        try {
            socket.close();
            logger.info("Client stopped and socket closed");
        }
        catch(IOException e) {
            logger.error("Failed to close client socket", e);
        }
        roomManager.handlePlayerDisconnect(player, this);
        server.removeClient(this);
        logger.info("Client stopped");

    }

    /**
     * Adds a message to message queue.
     * The queue is being handler by {@link MessageWriter}
     *
     * @param message to be sent to the client
     */
    public void addMessageToQueue(Message message) {
        messageQueue.offer(message);
    }

    /**
     * Starts listening for incoming messages 
     * and writing outgoing messages.
     */
    public void startClient() {
        try {
            messageWriter = new Thread(new MessageWriter(socket, this, roomManager, messageQueue));
            messageListener = new Thread(new MessageListener(socket, this,roomManager, messageHandler, commandWorkerFactory));
            messageWriter.start();
            messageListener.start();
        }
        catch (Exception e) {
            logger.error("There was an error handling a client.", e);
        }
    }
}
