package cz.vse.java.hangman.api;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private String name;
    private int maxPlayers;
    private Set<Player> players;
    private Player leader;
    private Game game;

    public Room(String name, int maxPlayers, Player leader) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.leader = leader;
        this.game = null;
        players = new HashSet<>();
        players.add(leader);
    }

    public Set<Player> getPlayers() {
        return players;
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
        Room other = (Room) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }


}
