package com.darren.crossword.solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 */
public class DummyDictionary implements Dictionary {
    private Map<Integer, Set<String>> data = new HashMap<Integer, Set<String>>();

    public Set<String> getAvailableWords(int length) {
        return data.get(length);
    }

    public void addWord(String word) {
        if(!data.containsKey(word.length())) {
            data.put(word.length(), new HashSet<String>());
        }
        data.get(word.length()).add(word.toUpperCase());
    }
}
