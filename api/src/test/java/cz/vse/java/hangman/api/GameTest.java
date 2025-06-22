package cz.vse.java.hangman.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {

	private WordGenerator wordGenerator;

	@BeforeEach
	public void setup() {
		wordGenerator = new DummyWordGenerator();
	}

	@Test
	public void takeGuess_shouldTakeSuccessfulGuess() {
		Player player = new Player("Player1");
		Room room = new Room("Room", 3, player);
		Game game = new Game(room, 10, wordGenerator);

		Guess guess = game.takeGuess('a');
		Assertions.assertEquals(Guess.GuessType.CORRECT, guess.getGuessType());
		Assertions.assertTrue(game.getGuessedLetters().contains('a'));
		Assertions.assertFalse(game.getAvailableLetters().contains('a'));
		Assertions.assertEquals(1, game.getAttempts());
		Assertions.assertEquals(0, game.getWrongAttempts());
	}

	@Test
	public void takeGuess_shouldHandleAlreadyGuessedLetter() {
		Player player = new Player("Player1");
		Room room = new Room("Room", 3, player);
		Game game = new Game(room, 10, wordGenerator);

		game.takeGuess('a');
		Guess guess = game.takeGuess('a');
		Assertions.assertEquals(Guess.GuessType.ALREADY_GUESSED, guess.getGuessType());
		Assertions.assertEquals(1, game.getWrongAttempts());
		Assertions.assertEquals(2, game.getAttempts());
	}

	@Test
	public void takeGuess_shouldHandleIncorrectGuess() {
		Player player = new Player("Player1");
		Room room = new Room("Room", 3, player);
		Game game = new Game(room, 10, wordGenerator);

		Guess guess = game.takeGuess('z');
		Assertions.assertEquals(Guess.GuessType.INCORRECT, guess.getGuessType());
		Assertions.assertTrue(game.getGuessedLetters().contains('z'));
		Assertions.assertEquals(1, game.getWrongAttempts());

	}

}
