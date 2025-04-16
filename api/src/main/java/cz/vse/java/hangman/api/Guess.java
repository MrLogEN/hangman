package cz.vse.java.hangman.api;

public class Guess {
    public Guess(char letter, GuessType guessType) {
        this.letter = letter;
        this.guessType = guessType;
    }

    public GuessType getGuessType() {
        return guessType;
    }

    public char getLetter() {
        return letter;
    }

    public enum GuessType {
        CORRECT,
        INCORRECT,
        ALREADY_GUESSED
    }

    private char letter;
    private GuessType guessType;
}
