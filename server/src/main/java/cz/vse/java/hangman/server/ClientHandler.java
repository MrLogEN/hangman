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

public class ClientHandler {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final RoomManager roomManager;
    private final CommandWorkerFactory commandWorkerFactory;
    private final MessageHandler messageHandler;
    private final Socket socket;
    private Player player;
    private Thread messageListener;
    private Thread messageWriter;
    private BlockingQueue<Message> messageQueue;

    public ClientHandler(
        Socket socket,
        RoomManager roomManager,
        MessageHandler messageHandler,
        CommandWorkerFactory commandWorkerFactory
    ) {
        this.socket = socket;
        this.roomManager = roomManager;
        this.commandWorkerFactory = commandWorkerFactory;
        this.messageHandler = messageHandler; 
        messageQueue = new LinkedBlockingQueue<>();
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

    public void stopClient(){
        if(messageListener != null) {
            messageListener.interrupt();
        }
        if(messageWriter != null) {
            messageWriter.interrupt();
        }
        logger.info("Client stopped");

    }

    public void addMessageToQueue(Message message) {
        messageQueue.offer(message);
    }

    public void startClient() {
        try(ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())
    ) {
            messageWriter = new Thread(new MessageWriter(outputStream, this, roomManager, messageQueue));
            messageListener = new Thread(new MessageListener(inputStream, this, roomManager, messageHandler, commandWorkerFactory));
            messageWriter.start();
            messageListener.start();
        }
        catch (IOException e) {
            logger.error("There was an error handling a client.", e);
        }
    }
}
