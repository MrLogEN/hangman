package cz.vse.java.hangman.server;

import java.io.IOException;
import java.io.ObjectInputStream;
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

public class MessageListener implements Runnable{
    private final Logger logger = LoggerFactory.getLogger(MessageListener.class);
    private final ObjectInputStream inputStream;
    private final ClientHandler clientHandler;
    private final RoomManager roomManager;
    private final MessageHandler messageHandler;
    private final CommandWorkerFactory workerFactory;
    private final List<Thread> activeWorkers = Collections.synchronizedList(new ArrayList<>());
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(MessageWrapper.class, new MessageWrapperDeserializer())
        .create();

    public MessageListener(
        ObjectInputStream inputStream,
        ClientHandler clientHandler,
        RoomManager roomManager,
        MessageHandler messageHandler,
        CommandWorkerFactory workerFactory
    ) {
        this.inputStream = inputStream;
        this.clientHandler = clientHandler;
        this.roomManager = roomManager;
        this.messageHandler = messageHandler;
        this.workerFactory = workerFactory;
    }

    @Override
    public void run() {

        try {
            while(!Thread.currentThread().isInterrupted()) {
                Object o = inputStream.readObject();
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
                }
                activeWorkers.clear();
            }
            try {
                inputStream.close();
                logger.info("Input stream closed successfully");
            }
            catch (IOException e) {
                logger.error("Failed to close input stream", e);
            }
        }
    }
}
