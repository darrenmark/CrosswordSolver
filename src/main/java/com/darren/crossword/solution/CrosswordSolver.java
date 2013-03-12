package com.darren.crossword.solution;

import com.darren.crossword.Crossword;
import com.darren.crossword.Line;
import com.google.common.base.Preconditions;
import com.google.common.collect.Constraint;
import com.google.common.collect.Constraints;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class CrosswordSolver {
    private Crossword crossword;
    private Dictionary dictionary;
    private int maxSolution = 10;
    List<Crossword> result = Constraints.constrainedList(new ArrayList<Crossword>(), new Constraint<Crossword>() {
        public Crossword checkElement(Crossword crossword) {
            Preconditions.checkArgument(crossword.isCrosswordSolved(), "Crossword %s is not solved", crossword);
            return crossword;
        }
    });


    public CrosswordSolver(Crossword crossword, Dictionary dictionary) {
        this.crossword = crossword;
        this.dictionary = dictionary;
    }

    public List<Crossword> solve(int maxSolution) {
        this.maxSolution = maxSolution;
        return solve();
    }

    public List<Crossword> solve() {
        result.clear();
        List<LineSolutions> allLineSolutions = getLineSolutions();
        for (LineSolutions lineSolutions : allLineSolutions) {
            lineSolutions.solve();
        }
        for (LineSolutions lineSolutions : allLineSolutions) {
            System.out.println(lineSolutions.getLine().getId() + lineSolutions.allowedWords());
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
        if (result.size() >= maxSolution) {
            return;
        }
        if (remainingSolutions.isEmpty() && crossword1.isCrosswordSolved()) {
            result.add(crossword1.makeCopy());
        }
        if (remainingSolutions.isEmpty()) {
            return;
        }
        for (String word : remainingSolutions.get(0).allowedWords()) {
            crossword1.getLine(remainingSolutions.get(0).getLine().getId()).setWord(word);
            List<LineSolutions> lineSolutions = new ArrayList<LineSolutions>(remainingSolutions);
            lineSolutions.remove(remainingSolutions.get(0));
            populate(crossword1, lineSolutions);
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