package com.darren.crossword.solution;

import com.darren.crossword.Crossword;
import com.darren.crossword.Line;
import com.darren.crossword.LineCharacter;
import com.darren.crossword.LineIntersection;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

/**
 */
public class CrosswordSolverTest {

    @Test
    public void test() throws IOException {
        Line line1 = new Line("1",3,"");
        Line line2 = new Line("2",3,"");
        LineIntersection lineIntersection = new LineIntersection(new LineCharacter(line1,1), new LineCharacter(line2,0));
        Crossword crossword = new Crossword(Arrays.asList(line1,line2), Arrays.asList(lineIntersection));
        CrosswordSolver crosswordSolver = new CrosswordSolver(crossword, getDictionary("RAT","ANT", "NOTHING"));
        List<Crossword> crosswordList = crosswordSolver.solve();
        Assert.assertThat(crosswordList.size(),is(equalTo(1)));
    }

    @Test
    public void testFailure() throws IOException {
        Line line1 = new Line("1",3,"");
        Line line2 = new Line("2",3,"");
        Line line3 = new Line("3",6,"");

        LineIntersection lineIntersection1 = new LineIntersection(new LineCharacter(line1,1), new LineCharacter(line2,0));
        LineIntersection lineIntersection2 = new LineIntersection(new LineCharacter(line2,2), new LineCharacter(line3,2));
        Crossword crossword = new Crossword(Arrays.asList(line1,line2, line3), Arrays.asList(lineIntersection1, lineIntersection2));
        CrosswordSolver crosswordSolver = new CrosswordSolver(crossword, getDictionary("JUB", "PUS"));
        List<Crossword> crosswordList = crosswordSolver.solve();
        Assert.assertThat(crosswordList.size(),is(equalTo(0)));
    }

    @Test
    public void test_1() throws IOException {
        Line line1 = new Line("1",3,"");
        Line line2 = new Line("2",3,"");
        Line line3 = new Line("3",6,"");
        LineIntersection lineIntersection1 = new LineIntersection(new LineCharacter(line1,1), new LineCharacter(line2,0));
        LineIntersection lineIntersection2 = new LineIntersection(new LineCharacter(line2,2), new LineCharacter(line3,2));
        Crossword crossword = new Crossword(Arrays.asList(line1,line2, line3), Arrays.asList(lineIntersection1, lineIntersection2));
        CrosswordSolver crosswordSolver = new CrosswordSolver(crossword, getDictionary1());
        List<Crossword> crosswordList = crosswordSolver.solve();
        System.out.println("###########################33");
        for(Crossword crossword1: crosswordList) {
            System.out.println(crossword1.isCrosswordSolved() + " " + crossword1);
        }
        Assert.assertThat(crosswordList.size(),is(equalTo(1)));
    }



    private Dictionary getDictionary(String... words) {
        DummyDictionary dictionary = new DummyDictionary();
        for(String word: words) {
            dictionary.addWord(word);
        }
        return dictionary;
    }

    private Dictionary getDictionary1() throws IOException {
        DictionaryImpl dictionary = new DictionaryImpl();
        dictionary.load();
        return dictionary;
    }
}
