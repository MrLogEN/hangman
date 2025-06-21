package cz.vse.java.hangman;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ClientHandler implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try(ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())
        ) {
            Thread messageWriter = new Thread(new MessageWriter(outputStream));
            Thread messageListener = new Thread(new MessageListener(inputStream));
            messageWriter.start();
            messageListener.start();
        }
        catch (IOException e) {
            logger.error("There was an error handling a client.", e);
        }
    }
}
