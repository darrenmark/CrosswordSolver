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
        List<LineSolutions> allLineSolutions = getLineSolutions();
        for (LineSolutions lineSolutions : allLineSolutions) {
            lineSolutions.solve();
        }
        for (LineSolutions lineSolutions : allLineSolutions) {
            System.out.println(lineSolutions.allowedWords().size() + "  " + lineSolutions.getLine().getId() + lineSolutions.allowedWords());
        }
        populateResult(createStack(allLineSolutions), crossword.makeCopy());
        return result;
    }

    private Stack<LineSolutions> createStack(List<LineSolutions> lineSolutions) {
        Stack<LineSolutions> stack = new Stack<LineSolutions>();
        for(int i = lineSolutions.size() -1; i >= 0; i--) {
            stack.push(lineSolutions.get(i));
        }
        return stack;
    }

    private void populateResult(Stack<LineSolutions> solutions, Crossword crossword1) {
        if (result.size() >= maxSolution) {
            return;
        }
        if (solutions.isEmpty() && crossword1.isCrosswordSolved()) {
            result.add(crossword1.makeCopy());
        }
        if (solutions.isEmpty() ) {
            return;
        }
        LineSolutions lineSolutions = solutions.pop();
        for(PartialSolution partialSolution: lineSolutions.getPartialSolutions()) {
            crossword1.getLine(lineSolutions.getLine().getId()).setWord(partialSolution.getWord());
            if(crossword1.isPartiallySolved()) {
                populateResult(solutions, crossword1);
            }
        }
        solutions.push(lineSolutions);
    }

    private List<LineSolutions> getLineSolutions() {
        List<LineSolutions> result = new ArrayList<LineSolutions>();
        for (Line line : crossword.getLines()) {
            result.add(new LineSolutions(crossword, dictionary, line, result));
        }
        return result;
    }
}