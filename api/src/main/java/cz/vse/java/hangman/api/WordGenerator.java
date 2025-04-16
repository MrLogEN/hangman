package cz.vse.java.hangman.api;

import java.util.Set;

public interface WordGenerator {
    String generateWord();
    Set<Character> getCharset();
}
