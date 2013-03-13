package com.darren.crossword;

import com.google.common.collect.Constraint;
import com.google.common.collect.Constraints;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 *
 */
public class Crossword {
    private List<Line> lines = Constraints.constrainedList(new ArrayList<Line>(), new Constraint<Line>() {
        public Line checkElement(Line line) {
            for(Line existingLine: lines) {
                checkArgument(!existingLine.getId().equals(line.getId()),"Line with id %s already exists",line.getId());
            }
            return line;
        }
    });
    private List<LineIntersection> lineIntersections = Constraints.constrainedList(new ArrayList<LineIntersection>(),
            new Constraint<LineIntersection>() {
                public LineIntersection checkElement(LineIntersection lineIntersection) {
                    checkArgument(instanceExists(lineIntersection.getLineCharacter1().getLine()));
                    checkArgument(instanceExists(lineIntersection.getLineCharacter2().getLine()));
                    return lineIntersection;
                }
            });

    private boolean instanceExists(Line line) {
        for(Line line1: lines) {
            if(line1 == line) {
                return true;
            }
        }
        return false;
    }

    public Crossword(List<Line> lines, List<LineIntersection> lineIntersections) {
        this.lines.addAll(lines);
        checkArgument(lines.size() > 0 , "The puzzle must have at least one line");
        this.lineIntersections.addAll(lineIntersections);
    }

    public boolean isCrosswordSolved() {
        for(LineIntersection lineIntersection: lineIntersections) {
            if(!lineIntersection.isIntersectionValid()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPartiallySolved() {
        for(LineIntersection lineIntersection: lineIntersections) {
            if(!lineIntersection.isPartiallyValid()) {
                return false;
            }
        }
        return true;
    }


    public List<Line> getIntersectingLines(Line line) {
        List<Line> result = new ArrayList<Line>();
        for(LineIntersection lineIntersection: getLineIntersections(line)) {
            if(lineIntersection.getLineCharacter1().getLine() == line) {
                result.add(lineIntersection.getLineCharacter2().getLine());
            }
            if(lineIntersection.getLineCharacter2().getLine() == line) {
                result.add(lineIntersection.getLineCharacter1().getLine());
            }
        }
        return result;
    }

    public List<LineIntersection> getLineIntersections(Line line) {
        List<LineIntersection> result = new ArrayList<LineIntersection>();
        for(LineIntersection lineIntersection: lineIntersections) {
            if(lineIntersection.isPresent(line)) {
                result.add(lineIntersection);
            }
        }
        return result;
    }

    public List<Line> getLines() {
        return lines;
    }

    public Crossword makeCopy() {
        List<Line> lines = new ArrayList<Line>();
        for(Line line: this.lines) {
            lines.add( line.makeCopy());
        }
        List<LineIntersection> intersections = new ArrayList<LineIntersection>();
        for(LineIntersection lineIntersection: lineIntersections) {
            LineCharacter lineCharacter1 = lineIntersection.getLineCharacter1();
            LineCharacter lineCharacter2 = lineIntersection.getLineCharacter2();
            LineCharacter newLineCharacter1 = new LineCharacter(findLine(lines,lineCharacter1.getLine().getId()), lineCharacter1.getCharacterPosition());
            LineCharacter newLineCharacter2 = new LineCharacter(findLine(lines,lineCharacter2.getLine().getId()), lineCharacter2.getCharacterPosition());
            intersections.add(new LineIntersection(newLineCharacter1, newLineCharacter2));
        }
        return new Crossword(lines, intersections);
    }

    public Line getLine(String id) {
        return findLine(lines, id);
    }

    private Line findLine(List<Line> lines, String id) {
        for(Line line: lines) {
            if(line.getId().equals(id)) {
                return line;
            }
        }
        throw new IllegalArgumentException("No line found with id " + id);
    }

    @Override
    public String toString() {
        return "Crossword{" +
                "lines=" + lines +
                ", lineIntersections=" + lineIntersections +
                '}';
    }
}
