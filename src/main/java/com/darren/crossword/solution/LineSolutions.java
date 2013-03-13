package com.darren.crossword.solution;

import com.darren.crossword.Crossword;
import com.darren.crossword.Line;
import com.darren.crossword.LineIntersection;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 */
public class LineSolutions {
    private Line line;
    private List<LineSolutions> allLineSolutions;
    private Crossword crossword;
    private Set<String> applicableWords = new HashSet<String>();
    private List<PartialSolution> partialSolutions = new ArrayList<PartialSolution>();

    public LineSolutions(Crossword crossword, Dictionary dictionary, Line line, List<LineSolutions> allLineSolution) {
        this.crossword = crossword;
        this.line = line;
        this.allLineSolutions = checkNotNull(allLineSolution);
        applicableWords.addAll(dictionary.getAvailableWords(line.getLength()));
    }

    public void updateWordsAndPartialSolutions(Set<String> applicableWords, List<Line> visitedLines) {
        this.applicableWords.retainAll(applicableWords);
        solve(visitedLines);
    }

    public List<String> allowedWords() {
        Set<String> result = new HashSet<String>();
        for(PartialSolution partialSolution: partialSolutions) {
            result.add(partialSolution.getWord());
        }
        return new ArrayList<String>(result);
    }

    public Line getLine() {
        return line;
    }

    public void solve() {
        solve(new ArrayList<Line>());
    }

    public void solve(List<Line> visitedLines) {
        for(String word: applicableWords) {
            PartialSolution solution = new PartialSolution(word);
            for(LineIntersection lineIntersection: crossword.getLineIntersections(line)) {
                for(String crossword: findLineSolution(lineIntersection.getOtherLine(line)).applicableWords) {
                    if(lineIntersection.iSaMatch(line, word, lineIntersection.getOtherLine(line), crossword)) {
                        solution.getIntersectingLines().add(new LineWord(lineIntersection.getOtherLine(line), crossword));
                    }
                }
            }
            addPartialSolution(solution);
        }
        updateApplicableWords(visitedLines);
    }

    private void addPartialSolution(PartialSolution solution) {
        if(solution.isValidSolution(line, crossword)) {
            partialSolutions.add(solution);
        }
    }

    private void updateApplicableWords(List<Line> visitedLines) {
        visitedLines.add(line);
        for(Line line1: crossword.getIntersectingLines(line)) {
            if(!visitedLines.contains(line1)) {
                findLineSolution(line1).updateWordsAndPartialSolutions(allowedWordsFor(line1), visitedLines);
            }
        }
    }

    private Set<String> allowedWordsFor(Line intersectingLine) {
        Set<String> result = new HashSet<String>();
        for(PartialSolution partialSolution: partialSolutions) {
            for(LineWord lineWord: partialSolution.getIntersectingLines()) {
                if(lineWord.getLine() == intersectingLine) {
                    result.add(lineWord.getWord());
                }
            }
        }
        return result;
    }

    private LineSolutions findLineSolution(Line line) {
        for(LineSolutions lineSolutions: allLineSolutions) {
            if(lineSolutions.getLine() == line) {
                return lineSolutions;
            }
        }
        throw new IllegalArgumentException("No line solution found for line " + line);
    }

    public List<PartialSolution> getPartialSolutions() {
        return partialSolutions;
    }
}
