package com.darren.crossword;

import static com.google.common.base.Preconditions.checkArgument;

/**
 */
public class LineCharacter {
    private Line line;
    private int characterPosition;

    public LineCharacter(Line line, int characterPosition) {
        this.line = line;
        this.characterPosition =  characterPosition;
        checkArgument(characterPosition >=0 && characterPosition < this.line.getLength());
    }

    public Line getLine() {
        return line;
    }

    public int getCharacterPosition() {
        return characterPosition;
    }

    public String getCharacter() {
         return line.getWord().substring(characterPosition, characterPosition+1);
    }

    @Override
    public String toString() {
        return "LineCharacter{" +
                "line=" + line +
                ", characterPosition=" + characterPosition +
                '}';
    }
}
