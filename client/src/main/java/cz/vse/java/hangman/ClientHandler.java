package cz.vse.java.hangman;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapper;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapperDeserializer;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapperSerializer;
import cz.vse.java.hangman.commands.ClientCommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ClientHandler class is responsible for managing the connection to the server.
 * It handles sending and receiving messages to and from the server.
 * It also deserializes incoming messages and executes the appropriate commands based on the message type.
 */
public class ClientHandler {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Gson gson;
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);


    /**
     * Method that connects client to server.
     *
     * @param host the host
     * @param port the port
     * @throws IOException the io exception
     */
    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        logger.info("Connected to server {}", socket);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        logger.info("ObjectOutputStream initialized.");
        in = new ObjectInputStream(socket.getInputStream());
        logger.info("ObjectInputStream initialized.");

        gson = new GsonBuilder()
                .registerTypeAdapter(MessageWrapper.class, new MessageWrapperDeserializer())
                .registerTypeAdapter(MessageWrapper.class, new MessageWrapperSerializer())
                .create();
        logger.info("Gson {} initialized.", gson);

        new Thread(this::listen).start();
        logger.info("ClientHandler listen thread started.");
        new Thread(this::writeLoop).start();
        logger.info("ClientHandler write thread started.");
    }

    /**
     * Method that listens for incoming messages from the server, deserializes and executes incoming messages.
     *
     */
    private void listen() {
        try {
            Object obj;
            while ((obj = in.readObject()) != null) {
                if (obj instanceof String line) {
                    logger.info("Received message: {}", line);
                    MessageWrapper wrapper = gson.fromJson(line, MessageWrapper.class);
                    logger.info("Deserialized message: {}", wrapper);
                    ClientCommandFactory commandFactory = new ClientCommandFactory();
                    commandFactory.fromMessage(wrapper.message()).execute();
                    logger.info("Executing command for message: {}", wrapper.message());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("Error while reading from server: {}", e.getMessage());
        }
    }

    private final java.util.concurrent.BlockingQueue<Message> outgoingMessages = new java.util.concurrent.LinkedBlockingQueue<>();

    public void send(Message message) {
        outgoingMessages.offer(message);
    }

    private void writeLoop() {
        try {
            while (true) {
                Message message = outgoingMessages.take();
                MessageWrapper wrapper = new MessageWrapper(message, message.getClass());
                String json = gson.toJson(wrapper);
                logger.info("Sending message: {} to server", json);
                out.writeObject(json);
                out.flush();
            }
        } catch (InterruptedException | IOException e) {
            logger.error("Error in writeLoop: {}", e.getMessage());
        }
    }
}
