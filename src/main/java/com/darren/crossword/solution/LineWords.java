package com.darren.crossword.solution;

import com.darren.crossword.Crossword;
import com.darren.crossword.Line;

import java.util.*;


/**
 */
public class LineWords {
    private Line line;
    private Set<String> applicableWords = new HashSet<String>();

    public LineWords(Line line,Dictionary dictionary) {
        this.line = line;
        applicableWords.addAll(dictionary.getAvailableWords(line.getLength()));
    }
    public Line getLine() {
        return line;
    }

    public Set<String> getApplicableWords() {
        return applicableWords;
    }
}
