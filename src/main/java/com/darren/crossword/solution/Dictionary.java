package com.darren.crossword.solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 */
public interface Dictionary {
    Set<String> getAvailableWords(int length);
}
