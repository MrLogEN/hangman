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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientHandler class is responsible for managing the connection to the server.
 * It handles sending and receiving messages to and from the server.
 * It also deserializes incoming messages and executes the appropriate commands based on the message type.
 */
public class ClientHandler implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
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
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        logger.info("Input stream initialized.");
        out = new PrintWriter(socket.getOutputStream(), true);
        logger.info("Output stream initialized.");

        gson = new GsonBuilder()
                .registerTypeAdapter(MessageWrapper.class, new MessageWrapperDeserializer())
                .registerTypeAdapter(MessageWrapper.class, new MessageWrapperSerializer())
                .create();
        logger.info("Gson {} initialized.", gson);

        new Thread(this).start();
        logger.info("ClientHandler thread started.");
    }

    /**
     * Method that listens for incoming messages from the server, deserializes and executes incoming messages.
     *
     */
    private void listen() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                logger.info("Received message: {}", line);
                MessageWrapper wrapper = gson.fromJson(line, MessageWrapper.class);
                logger.info("Deserialized message: {}", wrapper);
                ClientCommandFactory commandFactory = new ClientCommandFactory();
                commandFactory.fromMessage(wrapper.message()).execute();
                logger.info("Executing command for message: {}", wrapper.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error while reading from server: {}", e.getMessage());
        }
    }

    /**
     * Method to send messages to the server.
     *
     * @param message the message
     */
    public void send(Message message) {
        MessageWrapper wrapper = new MessageWrapper(message, message.getClass());
        String json = gson.toJson(wrapper);
        logger.info("Sending message: {} to server", json);
        out.println(json);
    }



    /**
     * Method that starts the listening process.
     */
    @Override
    public void run() {
        listen();
    }
}
