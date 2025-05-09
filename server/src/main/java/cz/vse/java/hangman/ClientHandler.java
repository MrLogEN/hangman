package cz.vse.java.hangman;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
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
            // handle exception
        }
    }
}
