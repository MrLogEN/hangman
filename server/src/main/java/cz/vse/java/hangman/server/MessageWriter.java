package cz.vse.java.hangman.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapper;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapperSerializer;

public class MessageWriter implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MessageWriter.class);
    private final ObjectOutputStream objectOutputStream;
    private final ClientHandler clientHandler;
    private final RoomManager roomManager;
    private final BlockingQueue<Message> messageQueue;
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(MessageWrapper.class, new MessageWrapperSerializer())
        .create();

    public MessageWriter(
        ObjectOutputStream objectOutputStream,
        ClientHandler clientHandler,
        RoomManager roomManager,
        BlockingQueue<Message> messageQueue
    ) {
        this.objectOutputStream = objectOutputStream;
        this.clientHandler = clientHandler;
        this.roomManager = roomManager;
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()) {
                Message message = messageQueue.take();
                MessageWrapper wrapper = MessageWrapper.wrap(message);
                String json = gson.toJson(wrapper);
                objectOutputStream.writeObject(json);
                objectOutputStream.flush();
            }

        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("MessageWriter thread interrupted: ", e);
        }
        catch (IOException e) {
            logger.error("Error writing to ObjectOutputStream: ", e);
            Thread.currentThread().interrupt();
        }

    }
}
