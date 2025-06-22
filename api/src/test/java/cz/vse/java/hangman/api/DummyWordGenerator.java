package cz.vse.java.hangman.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.vse.java.hangman.api.WordGenerator;

public class DummyWordGenerator implements WordGenerator{

    private final String word;

    public DummyWordGenerator() {
        word = "hangman";
    }

    @Override
    public String generateWord() {
        return word;
    }

    @Override
    public Set<Character> getCharset(String word) {
        var set = new HashSet<Character>();
        for(Character c: word.toCharArray()){
            set.add(c);
        }
        return new HashSet<>();
    }

    @Override
    public List<Character> getAlphabet() {
        var list = new ArrayList<Character>();
        for(char c = 'a'; c <= 'z'; c++) {
            list.add(c);
        }
        return list;
    }


}
