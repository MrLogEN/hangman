package cz.vse.java.hangman.server;

import java.util.HashSet;
import java.util.Set;

import cz.vse.java.hangman.api.WordGenerator;

public class StaticWordGenerator implements WordGenerator{

    private final String word;

    public StaticWordGenerator() {
        word = "hangman";
    }

    @Override
    public String generateWord() {
        return word;
    }

    @Override
    public Set<Character> getCharset() {
        var set = new HashSet<Character>();
        for(Character c: word.toCharArray()){
            set.add(c);
        }
        return new HashSet<>();
    }


}
