package cz.vse.java.hangman.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import cz.vse.java.hangman.api.WordGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CzechWordGenerator implements WordGenerator{

    private static final Logger logger = LoggerFactory.getLogger(CzechWordGenerator.class);
    private static List<String> cachedWords;

    @Override
    public String generateWord() {
        if (cachedWords == null) {
            cachedWords = loadWords();
        }
        String word = cachedWords.get(new Random().nextInt(cachedWords.size()));
        logger.debug("Generated word: {}", word);
        return word;
    }

    @Override
    public Set<Character> getCharset(String word) {
        return word.chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.toSet());
    }

    private static List<String> loadWords() {
        try (InputStream inputStream = CzechWordGenerator.class.getResourceAsStream("/czech_nouns.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            List<String> words = reader.lines().collect(Collectors.toList());
            if (words.isEmpty()) {
                throw new IllegalStateException("Word list is empty");
            }
            logger.debug("Loaded word: {}", words);
            return words;
        } catch (IOException e) {
            logger.error("Failed to load words", e);
            throw new RuntimeException("Failed to load words from resources", e);
        }

    }

    @Override
    public List<Character> getAlphabet() {
        var list = new ArrayList<Character>();
        char[] czechAlphabet = {'a', 'á', 'b', 'c', 'č', 'd', 'ď', 'e', 'é', 'ě', 'f', 'g', 'h', 'i', 'í', 'j', 'k', 'l', 'm', 'n', 'ň', 'o', 'ó', 'p', 'q', 'r', 'ř', 's', 'š', 't', 'ť', 'u', 'ú', 'ů', 'v', 'w', 'x', 'y', 'ý', 'z', 'ž'};
        for (char c : czechAlphabet) {
            list.add(c);
        }
        return list;
    }


}
