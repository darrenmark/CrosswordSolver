package com.darren.crossword.solution;

import com.darren.crossword.Crossword;
import com.darren.crossword.Line;
import com.google.common.base.Preconditions;
import com.google.common.collect.Constraint;
import com.google.common.collect.Constraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
        List<LineWords> allLineSolutions = getLineSolutions();
        populateResult(createStack(allLineSolutions), crossword.makeCopy());
        return result;
    }

    private Stack<LineWords> createStack(List<LineWords> lineSolutions) {
        Stack<LineWords> stack = new Stack<LineWords>();
        for(int i = lineSolutions.size() -1; i >= 0; i--) {
            stack.push(lineSolutions.get(i));
        }
        return stack;
    }

    private void populateResult(Stack<LineWords> solutions, Crossword crossword1) {
        if (result.size() >= maxSolution) {
            return;
        }
        if (solutions.isEmpty() && crossword1.isCrosswordSolved()) {
            result.add(crossword1.makeCopy());
        }
        if (solutions.isEmpty() ) {
            return;
        }
        LineWords lineSolutions = solutions.pop();
        for(String word: lineSolutions.getApplicableWords()) {
            crossword1.getLine(lineSolutions.getLine().getId()).setWord(word);
            if(crossword1.isPartiallySolved()) {
                populateResult(solutions, crossword1);
            }
            crossword1.getLine(lineSolutions.getLine().getId()).removeWord();
        }
        solutions.push(lineSolutions);
    }

    private List<LineWords> getLineSolutions() {
        List<LineWords> result = new ArrayList<LineWords>();
        for (Line line : crossword.getLines()) {
            result.add(new LineWords(line, dictionary));
        }
        return result;
    }
}