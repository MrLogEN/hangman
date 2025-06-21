package cz.vse.java.hangman.server;

public class App {

    /**
     *  Entry point for Server application.
     *  @param args
     */
    public static void main(String[] args) {
        RoomManager roomManager = new RoomManager();
        Server server = new Server(roomManager);
        server.start(8080);
    }
}
