package com.darren.crossword.solution;

import com.darren.crossword.Line;

/**
 */
public class LineWord {
    private Line line;
    private String word;

    public LineWord(Line line, String word) {
        this.line = line;
        this.word = word;
    }

    public Line getLine() {
        return line;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return "LineWord{" +
                "line=" + line +
                ", word='" + word + '\'' +
                '}';
    }
}
