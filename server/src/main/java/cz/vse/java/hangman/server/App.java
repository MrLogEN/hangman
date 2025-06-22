package cz.vse.java.hangman.server;

import cz.vse.java.hangman.server.commands.CommandWorkerFactory;

public class App {

    /**
     *  Entry point for Server application.
     *  @param args
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.start(8080);
    }
}
