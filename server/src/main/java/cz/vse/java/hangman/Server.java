package cz.vse.java.hangman;

import javax.net.SocketFactory;
import java.io.IOException;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    /**
     *  Entry point for Server application.
     *  @param args
     */
    public static void main(final String[] args) {
        int port = 8080; // Default port
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            while(true) {

                Socket socket = serverSocket.accept();
                Thread clientHandlerThread = new Thread(new ClientHandler(socket));
                clientHandlerThread.start();
            }

        }
        catch (IOException e) {
            //log
            // exit
        }


    }
}
