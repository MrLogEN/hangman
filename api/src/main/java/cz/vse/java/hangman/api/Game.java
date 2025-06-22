package cz.vse.java.hangman.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import cz.vse.java.hangman.api.Guess.GuessType;

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
    private List<Character> uniqueLetters;
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
        this.id = UUID.randomUUID();
        this.room = room;
        this.wordGenerator = wordGenerator;
        String generatedWord = wordGenerator.generateWord();
        word = generatedWord.toCharArray();
        this.wrongAttempts = 0;
        this.attempts = 0;
        this.gameState = GameState.PLAYING;
        this.guessedLetters = new HashSet<>();
        this.availableLetters = new HashSet<Character>(wordGenerator.getAlphabet());
        this.players = new LinkedList<>(room.getPlayers());
        this.currentPlayerIndex = 0;
        this.uniqueLetters = new ArrayList<>();
        for (char c : word) {
            if (!uniqueLetters.contains(c)) {
                uniqueLetters.add(c);
            }
        }
        this.maxAttempts = 3 * uniqueLetters.size();
    }

    public UUID getId() {
        return id;
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public Set<Character> getAvailableLetters() {
        return availableLetters;
    }
    public void setAvailableLetters(Set<Character> availableLetters) {
        this.availableLetters = availableLetters;
    }

    public void removePlayer(Player player) {
        Player current = getCurrentPlayer();
        players.remove(player);
        if(players.get(currentPlayerIndex) == null) {
            nextPlayer();
        }
        if(players.size() < 1) {
            gameState = GameState.LOSE;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Game other = (Game) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
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

    public Guess takeGuess(char letter){
        if (gameState == GameState.LOSE || gameState == GameState.WIN) {
            throw new IllegalStateException("Game is already finished");
        }

        Guess g = null; 
        if(availableLetters.contains(letter) && uniqueLetters.contains(letter) && !guessedLetters.contains(letter)){
            g = new Guess(letter, GuessType.CORRECT);
            availableLetters.remove(letter);
            guessedLetters.add(letter);
            attempts++;
        }
        else if(!availableLetters.contains(letter) && uniqueLetters.contains(letter) && guessedLetters.contains(letter)) {
            g = new Guess(letter, GuessType.ALREADY_GUESSED);
            attempts++;
            wrongAttempts++;
        }
        else {
            g = new Guess(letter, GuessType.INCORRECT);
            availableLetters.remove(letter);
            guessedLetters.add(letter);
            attempts++;
            wrongAttempts++;
        }


        if (wrongAttempts >= maxAttempts) {
            gameState = GameState.LOSE;
        }
        else if (wordComplete()){
            gameState = GameState.WIN;
        }
        else {
            nextPlayer();
        }

        return g;
    }

    public void nextPlayer(){
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
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
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
