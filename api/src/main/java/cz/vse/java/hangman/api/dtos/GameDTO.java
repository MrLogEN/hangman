package cz.vse.java.hangman.api.dtos;

import cz.vse.java.hangman.api.Game;

import java.util.Set;

public record GameDTO(Set<Character> guessedLetters,
                      Set<Character> availableLetters,
                      char[] wordProgress,
                      int maxAttempts,
                      int wrongAttempts,
                      PlayerDTO currentPlayer,
                      Game.GameState gameState) { }
