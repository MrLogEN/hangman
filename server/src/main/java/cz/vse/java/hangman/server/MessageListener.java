package cz.vse.java.hangman.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cz.vse.java.hangman.api.commands.Command;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapper;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapperDeserializer;
import cz.vse.java.hangman.server.commands.CommandWorker;
import cz.vse.java.hangman.server.commands.CommandWorkerFactory;

import org.slf4j.Logger;

/**
 * Handles listening for messages and their
 * distribution to worker threads.
 */
public class MessageListener implements Runnable{
    private final Logger logger = LoggerFactory.getLogger(MessageListener.class);
    private final Socket socket;
    private final ClientHandler clientHandler;
    private final RoomManager roomManager;
    private final MessageHandler messageHandler;
    private final CommandWorkerFactory workerFactory;
    private final List<Thread> activeWorkers = Collections.synchronizedList(new ArrayList<>());
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(MessageWrapper.class, new MessageWrapperDeserializer())
        .create();

    public MessageListener(
        Socket socket,
        ClientHandler clientHandler,
        RoomManager roomManager,
        MessageHandler messageHandler,
        CommandWorkerFactory workerFactory
    ) {
        this.socket = socket;
        this.clientHandler = clientHandler;
        this.roomManager = roomManager;
        this.messageHandler = messageHandler;
        this.workerFactory = workerFactory;
    }

    @Override
    public void run() {

        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());){
            while(!Thread.currentThread().isInterrupted()) {
                logger.debug("Trying to read a message");
                Object o = inputStream.readObject();
                logger.debug("Received object: {}", o);
                if(o instanceof String message) {
                    try {
                        MessageWrapper wrapper = gson.fromJson(message, MessageWrapper.class);
                        Command command = messageHandler.fromMessage(wrapper.unwrap());
                        Thread workerThread = workerFactory.newThread(new CommandWorker(command, activeWorkers));
                        workerThread.start();

                        logger.info("Received and deserialized message: {}", message);
                    }
                    catch(Exception ex) {
                        logger.error("Failed to deserialize message: {}", ex);
                    }
                }
                else {
                    logger.warn("Received unsupported object type: {}", o.getClass());
                }
            }
        }
        catch (SocketException e) {
            logger.error("Socket closed when reading the input stream: {}", e.getMessage(), e);
        }
        catch (IOException e) {
            logger.error("I/O error occurred while reading from input stream", e);
        }
        catch (ClassNotFoundException e){
            logger.error("Class not found while reading object from input stream", e);
        }
        finally {
            synchronized(activeWorkers) {
                for(Thread thread: activeWorkers) {
                    thread.interrupt();
                    logger.debug("CommandWorkers interrupted: {}", thread.getName());
                }
                activeWorkers.clear();
                logger.debug("All workers of current thread have been cleared.");
            }
            clientHandler.stopClient();
        }
    }
}
