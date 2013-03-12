package com.darren.crossword;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

/**
 */
public class CrosswordTest {

    @Test
    public void testPass() {
        Line line1 = new Line("1",3,"");
        Line line2 = new Line("2",3,"");
        LineIntersection lineIntersection = new LineIntersection(new LineCharacter(line1,1), new LineCharacter(line2,0));
        Crossword crossword = new Crossword(Arrays.asList(line1, line2), Arrays.asList(lineIntersection));
        line1.setWord("RAT");
        line2.setWord("ANT");
        Assert.assertTrue(crossword.isCrosswordSolved());
    }

    @Test
    public void testPass_1() {
        Line line1 = new Line("1",3,"");
        Line line2 = new Line("2",3,"");
        LineIntersection lineIntersection = new LineIntersection(new LineCharacter(line1,1), new LineCharacter(line2,0));
        Crossword crossword = new Crossword(Arrays.asList(line1, line2), Arrays.asList(lineIntersection));
        line1.setWord("APO");
        line2.setWord("PUS");
        Assert.assertTrue(crossword.isCrosswordSolved());
    }

    @Test
    public void testFail() {
        Line line1 = new Line("1",3,"");
        Line line2 = new Line("2",3,"");
        LineIntersection lineIntersection = new LineIntersection(new LineCharacter(line1,1), new LineCharacter(line2,0));
        Crossword crossword = new Crossword(Arrays.asList(line1, line2), Arrays.asList(lineIntersection));
        line1.setWord("JUB");
        line2.setWord("PUS");
        Assert.assertFalse(crossword.isCrosswordSolved());
    }

}
