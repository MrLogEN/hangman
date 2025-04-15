package cz.vse.java.hangman.api;

/**
 * Class representing a player in the game.
 */
public class Player {
    private String name;
    private ConnectingStatus connectingStatus;

    public ConnectingStatus getConnectingStatus() {
        return connectingStatus;
    }

    /**
     * Enum representing the connection status of a player.
     * This function is used on the server to determine
     * if the player is connected, disconnected or reconnecting.
     */
    public enum ConnectingStatus {
        CONNECTED,
        DISCONNECTED,
        RECONNECTING
    }

    public Player(String name) {
        this.name = name;
        this.connectingStatus = ConnectingStatus.CONNECTED;
    }

    /**
     * Getter for the name of the player.
     * @return the name of the player.
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the {@link ConnectingStatus} of the player.
     * @param connectingStatus the new connecting status of the player.
     */
    public void setConnectingStatus(ConnectingStatus connectingStatus) {
        this.connectingStatus = connectingStatus;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
