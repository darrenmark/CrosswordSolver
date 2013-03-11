package com.darren.crossword.solution;

import com.darren.crossword.Crossword;
import com.darren.crossword.Line;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class CrosswordSolver {
    private Crossword crossword;
    private Dictionary dictionary;
    List<Crossword> result = new ArrayList<Crossword>();


    public CrosswordSolver(Crossword crossword, Dictionary dictionary) {
        this.crossword = crossword;
        this.dictionary = dictionary;
    }

    public List<Crossword> solve() {
        result.clear();
        List<LineSolutions> allLineSolutions = getLineSolutions();
        for (LineSolutions lineSolutions : allLineSolutions) {
            lineSolutions.solve();
        }
        populateResult(allLineSolutions);
        return result;
    }

    private void populateResult(List<LineSolutions> allLineSolutions) {
        LineSolutions solutions = allLineSolutions.get(0);
        for (String word : solutions.allowedWords()) {
            Crossword crossword1 = crossword.makeCopy();
            crossword1.getLine(solutions.getLine().getId()).setWord(word);
            List<LineSolutions> lineSolutions = new ArrayList<LineSolutions>(allLineSolutions);
            lineSolutions.remove(solutions);
            populate(crossword1, lineSolutions);
        }
    }

    private void populate(Crossword crossword1, List<LineSolutions> remainingSolutions) {
        if (remainingSolutions.size() == 0 && crossword1.isCrosswordSolved()) {
            result.add(crossword1);
        }
        for (LineSolutions solutions : remainingSolutions) {
            for (String word : solutions.allowedWords()) {
                crossword1.getLine(solutions.getLine().getId()).setWord(word);
                List<LineSolutions> lineSolutions = new ArrayList<LineSolutions>(remainingSolutions);
                lineSolutions.remove(solutions);
                populate(crossword1, lineSolutions);
            }
        }
    }

    private List<LineSolutions> getLineSolutions() {
        List<LineSolutions> result = new ArrayList<LineSolutions>();
        for (Line line : crossword.getLines()) {
            result.add(new LineSolutions(crossword, dictionary, line, result));
        }
        return result;
    }
}