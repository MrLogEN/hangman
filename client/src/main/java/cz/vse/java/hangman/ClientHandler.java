package cz.vse.java.hangman;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.vse.java.hangman.commands.ClientCommandFactory;
import cz.vse.java.hangman.api.messages.Message;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapper;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapperDeserializer;
import cz.vse.java.hangman.api.messages.serialization.MessageWrapperSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson;
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);


    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        gson = new GsonBuilder()
                .registerTypeAdapter(MessageWrapper.class, new MessageWrapperDeserializer())
                .registerTypeAdapter(MessageWrapper.class, new MessageWrapperSerializer())
                .create();

        new Thread(this).start();
    }

    private void listen() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                MessageWrapper wrapper = gson.fromJson(line, MessageWrapper.class);
                ClientCommandFactory commandFactory = new ClientCommandFactory();
                commandFactory.fromMessage(wrapper.message()).execute();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Message message) {
        MessageWrapper wrapper = new MessageWrapper(message, message.getClass());
        String json = gson.toJson(wrapper);
        logger.info("Sending message: {} to server", json);
        out.println(json);
    }


    public void simulateIncomingMessage(Message message) {
        ClientCommandFactory commandFactory = new ClientCommandFactory();
        commandFactory.fromMessage(message).execute();
    }


    @Override
    public void run() {
        listen();
    }
}
