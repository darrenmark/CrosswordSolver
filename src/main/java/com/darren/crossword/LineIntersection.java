package com.darren.crossword;

/**
 */
public class LineIntersection {
    private LineCharacter lineCharacter1;
    private LineCharacter lineCharacter2;

    public LineIntersection(LineCharacter lineCharacter1, LineCharacter lineCharacter2) {
        this.lineCharacter1 = lineCharacter1;
        this.lineCharacter2 = lineCharacter2;
    }

    public LineCharacter getLineCharacter1() {
        return lineCharacter1;
    }

    public LineCharacter getLineCharacter2() {
        return lineCharacter2;
    }

    public boolean isIntersectionValid() {
        if(lineCharacter1.getLine().isWordSet() && lineCharacter2.getLine().isWordSet()) {
            return lineCharacter1.getCharacter().equals(lineCharacter2.getCharacter());
        }
        return false;
    }

    public boolean isPartiallyValid() {
        if(!lineCharacter1.getLine().isWordSet() || !lineCharacter2.getLine().isWordSet()) {
            return true;
        }
        if(lineCharacter1.getLine().isWordSet() && lineCharacter2.getLine().isWordSet()) {
            return lineCharacter1.getCharacter().equals(lineCharacter2.getCharacter());
        }
        return false;
    }

    public boolean iSaMatch(Line line1, String word1, Line line2, String word2) {
        return word1.charAt(getLineCharacter(line1).getCharacterPosition()) == word2.charAt(getLineCharacter(line2).getCharacterPosition());
    }

    public boolean isPresent(Line line) {
        return getLineCharacter1().getLine() == line || getLineCharacter2().getLine() == line;
    }

    public Line getOtherLine(Line line) {
        if(getLineCharacter1().getLine() == line) {
            return getLineCharacter2().getLine();
        }
        if(getLineCharacter2().getLine() == line) {
            return getLineCharacter1().getLine();
        }
        throw new IllegalArgumentException("Line " + line + " does not intersect");
    }

    public LineCharacter getLineCharacter(Line line) {
        if(getLineCharacter1().getLine() == line) {
            return getLineCharacter1();
        }
        if(getLineCharacter2().getLine() == line) {
            return getLineCharacter2();
        }
        throw new IllegalArgumentException("Line " + line + " does not intersect");

    }

    @Override
    public String toString() {
        return "LineIntersection{" +
                "lineCharacter1=" + lineCharacter1 +
                ", lineCharacter2=" + lineCharacter2 +
                ", isValid=" + isIntersectionValid() +
                '}';
    }
}
