package com.darren.crossword.solution;

import com.darren.crossword.Crossword;
import com.darren.crossword.Line;
import com.darren.crossword.LineIntersection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void updateWordsAndPartialSolutions(Set<String> applicableWords) {
        this.applicableWords.retainAll(applicableWords);
        for(PartialSolution partialSolution: new ArrayList<PartialSolution>(partialSolutions)) {
            if(!this.applicableWords.contains(partialSolution.getWord())) {
                partialSolutions.remove(partialSolution);
            }
        }
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
        updateApplicableWords();
    }

    private void addPartialSolution(PartialSolution solution) {
        if(solution.isValidSolution(line, crossword)) {
            partialSolutions.add(solution);
        }
    }

    private void updateApplicableWords() {
        for(Line line1: crossword.getIntersectingLines(line)) {
            findLineSolution(line1).updateWordsAndPartialSolutions(allowedWordsFor(line1));
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
}
