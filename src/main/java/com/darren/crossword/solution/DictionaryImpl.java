package com.darren.crossword.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 */
public class DictionaryImpl implements Dictionary {
    private Map<Integer, Set<String>> data = new HashMap<Integer, Set<String>>();

    public Set<String> getAvailableWords(int length) {
        return data.get(length);
    }

    private void addWord(String word) {
        if(!data.containsKey(word.length())) {
            data.put(word.length(), new HashSet<String>());
        }
        data.get(word.length()).add(word.toUpperCase());
    }

    public void load() {
        data.clear();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(ClassLoader.class.getResourceAsStream("/dictionary.dat")));
            for(String temp = br.readLine(); temp != null; temp = br.readLine()) {
                addWord(temp);
            }
            br.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
