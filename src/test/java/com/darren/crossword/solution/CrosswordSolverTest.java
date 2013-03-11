package com.darren.crossword.solution;

import com.darren.crossword.Crossword;
import com.darren.crossword.Line;
import com.darren.crossword.LineCharacter;
import com.darren.crossword.LineIntersection;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

/**
 */
public class CrosswordSolverTest {

    @Test
    public void test() {
        Line line1 = new Line("1",3,"");
        Line line2 = new Line("2",3,"");
        LineIntersection lineIntersection = new LineIntersection(new LineCharacter(line1,1), new LineCharacter(line2,0));
        Crossword crossword = new Crossword(Arrays.asList(line1,line2), Arrays.asList(lineIntersection));
        CrosswordSolver crosswordSolver = new CrosswordSolver(crossword, getDictionary());
        List<Crossword> crosswordList = crosswordSolver.solve();
        Assert.assertThat(crosswordList.size(),is(equalTo(1)));
    }

    private Dictionary getDictionary() {
        Dictionary dictionary = new Dictionary();
        dictionary.addWord("RAT");
        dictionary.addWord("ANT");
        dictionary.addWord("NOTHING");
        return dictionary;
    }
}
