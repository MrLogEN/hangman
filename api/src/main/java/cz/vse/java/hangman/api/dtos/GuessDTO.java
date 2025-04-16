package cz.vse.java.hangman.api.dtos;

import cz.vse.java.hangman.api.Guess;

/**
 * This record is used to return the guess that was taken back to the player that took the guess.
 * @param letter the letter that was guessed.
 * @param guessType the type of the guess ({@link Guess.GuessType}).
 */
public record GuessDTO(char letter, Guess.GuessType guessType) {

    public static GuessDTO fromGuess(Guess guess) {
        return new GuessDTO(guess.getLetter(), guess.getGuessType());
    }
}
