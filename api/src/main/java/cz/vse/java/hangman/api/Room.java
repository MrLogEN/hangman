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


}
