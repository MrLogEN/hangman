package cz.vse.java.hangman.api.dtos;

import cz.vse.java.hangman.api.Game;
import cz.vse.java.hangman.api.Player;

import java.util.LinkedList;
import java.util.Set;

public record GameDTO(Set<Character> guessedLetters,
                      Set<Character> availableLetters,
                      char[] wordProgress,
                      int maxAttempts,
                      int wrongAttempts,
                      PlayerDTO currentPlayer,
                      LinkedList<Player> players,
                      Game.GameState gameState) {

    public static GameDTO fromGame(Game game) {
        return new GameDTO(
                game.getGuessedLetters(),
                game.getAvailableLetters(),
                game.getWordProgress(),
                game.getMaxAttempts(),
                game.getWrongAttempts(),
                PlayerDTO.fromPlayer(game.getCurrentPlayer()),
                game.getPlayers(),
                game.getGameState()
        );
    }
}
