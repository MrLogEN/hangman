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

/**
 * Generator of a random czeck word
 */
public class EnglishWordGenerator implements WordGenerator{

    private static final Logger logger = LoggerFactory.getLogger(EnglishWordGenerator.class);
    private static List<String> cachedWords;

    @Override
    public String generateWord() {
        if (cachedWords == null) {
            cachedWords = loadWords();
        }
        String word = cachedWords.get(new Random().nextInt(cachedWords.size())).toUpperCase();
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
        try (InputStream inputStream = EnglishWordGenerator.class.getResourceAsStream("/english_nouns.txt");
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
        for (char c = 'A'; c <= 'Z'; c++) {
            list.add(c);
        }
        return list;
    }


}
