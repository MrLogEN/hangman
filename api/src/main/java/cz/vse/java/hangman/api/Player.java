package cz.vse.java.hangman.api;

public class Player {
    private String name;
    private ConnectingStatus connectingStatus;

    public ConnectingStatus getConnectingStatus() {
        return connectingStatus;
    }

    public enum ConnectingStatus {
        CONNECTED,
        DISCONNECTED,
        RECONNECTING
    }
    public Player(String name) {
        this.name = name;
        this.connectingStatus = ConnectingStatus.CONNECTED;
    }

    public String getName() {
        return name;
    }

    public void setConnectingStatus(ConnectingStatus connectingStatus) {
        this.connectingStatus = connectingStatus;
    }

}
