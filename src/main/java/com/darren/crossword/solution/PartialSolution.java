package com.darren.crossword.solution;

import com.darren.crossword.Crossword;
import com.darren.crossword.Line;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 */
public class PartialSolution {
    private String word;
    private Set<LineWord> intersectingLines = new HashSet<LineWord>();

    public PartialSolution(String word) {
        this.word = checkNotNull(word);
        this.intersectingLines.addAll(intersectingLines);
    }

    public String getWord() {
        return word;
    }

    public Set<LineWord> getIntersectingLines() {
        return intersectingLines;
    }

    boolean isValidSolution(Line line, Crossword crossword) {
        for(Line line1: crossword.getIntersectingLines(line)) {
           if(!intersectionLineExits(line1)) {
               return false;
           }
        }
        return true;
    }

    private boolean intersectionLineExits(Line line) {
        for(LineWord lineWord: intersectingLines) {
            if(lineWord.getLine() == line) {
                return true;
            }
        }
        return false;
    }

    public List<String> allowedWords(Line line) {
        List<String> result = new ArrayList<String>();
        for(LineWord lineWord: intersectingLines) {
            if(lineWord.getLine() == line) {
                result.add(lineWord.getWord());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "PartialSolution{" +
                "word='" + word + '\'' +
                ", intersectingLines=" + intersectingLines +
                '}';
    }
}
