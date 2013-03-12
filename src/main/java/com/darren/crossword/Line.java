package com.darren.crossword;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 */
public class Line {
    private String id;
    private int length;
    private String hint;
    private String word;

    public Line(String id, int length, String hint) {
        this.id = id;
        this.length = length;
        this.hint = hint;
    }

    Line(String id, int length, String hint, String word) {
        this(id, length, hint);
        this.word = word;
    }

    public int getLength() {
        return length;
    }

    public String getHint() {
        return hint;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        checkArgument(word.length() == length);
        this.word = word;
    }

    public boolean isWordSet() {
        return word != null;
    }

    Line makeCopy() {
        return new Line(id, length, hint, word);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (length != line.length) return false;
        if (id != null ? !id.equals(line.id) : line.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + length;
        return result;
    }

    @Override
    public String toString() {
        return "Line{" +
                "id='" + id + '\'' +
                ", length=" + length +
                ", hint='" + hint + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
