package cz.vse.java.hangman.api;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

public class Game {
    public enum GameState{
        PLAYING,
        WIN,
        LOSE
    }

    private UUID id;
    private char[] word;
    private Set<Character> guessedLetters;
    private Set<Character> availableLetters;
    private int maxAttempts;
    private int wrongAttempts;
    private int attempts;
    private Room room;
    private LinkedList<Player> players;
    private int currentPlayerIndex;
    private GameState gameState;

    private WordGenerator wordGenerator;

    public Game(Room room, int maxAttempts, WordGenerator wordGenerator) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("Max attempts must be greater than 0");
        }
        this.id = UUID.randomUUID();
        this.room = room;
        this.wordGenerator = wordGenerator;
        word = wordGenerator.generateWord().toCharArray();
        this.maxAttempts = maxAttempts;
        this.wrongAttempts = 0;
        this.attempts = 0;
        this.gameState = GameState.PLAYING;
        this.guessedLetters = new HashSet<>();
        this.availableLetters = wordGenerator.getCharset();
        this.players = new LinkedList<>(room.getPlayers());
        this.currentPlayerIndex = 0;

    }

    public UUID getId() {
        return id;
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void setAvailableLetters(Set<Character> availableLetters) {
        this.availableLetters = availableLetters;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getWrongAttempts() {
        return wrongAttempts;
    }

    public int getAttempts() {
        return attempts;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Room getRoom() {
        return room;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public void takeGuess(char letter){
        if (gameState == GameState.LOSE || gameState == GameState.WIN) {
            throw new IllegalStateException("Game is already finished");
        }
        if (availableLetters.contains(letter)) {
            guessedLetters.add(letter);
            attempts++;
        } else {
            guessedLetters.add(letter);
            wrongAttempts++;
            attempts++;
        }
        if (wrongAttempts >= maxAttempts) {
            gameState = GameState.LOSE;
            return;
        }
        if (wordComplete()){
            gameState = GameState.WIN;
            return;
        }
        gameState = GameState.PLAYING;
        if(currentPlayerIndex >= players.size() - 1){
            currentPlayerIndex = 0;
        } else {
            currentPlayerIndex++;
        }
    }

    public char[] getWordProgress(){
        char[] wordProgress = new char[word.length];
        for (int i = 0; i < word.length; i++) {
            if (guessedLetters.contains(word[i])) {
                wordProgress[i] = word[i];
            } else {
                wordProgress[i] = '\0';
            }
        }
        return wordProgress;
    }

    private boolean wordComplete(){
        for (char c : word) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }
}
