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
    public String getName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Player getLeader() {
        return leader;
    }

    public void setLeader(Player player) {
        this.leader = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void addPlayer(Player player) {
        synchronized(players) {
            players.add(player);
        } 
    }

    public Player getPlayer(String playerName) {
        for (Player player : players) {
            if(player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    public Player getPlayer(Player player) {
        for (Player p: players) {
            if(p.equals(player)) {
                return p;
            }
        }
        return null;
    }

    public void removePlayer(String playerName) {
        Player dummy = new Player(playerName);
        players.remove(dummy);
    }
    public void removePlayer(Player player) {
        players.remove(player);
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
