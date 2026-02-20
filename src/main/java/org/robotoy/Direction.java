package org.robotoy;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    public Direction left() {
        int index = (this.ordinal() - 1 + 4) % 4; // +4 to handle negative result of -1
        return values()[index];
    }

    public Direction right() {
        int index = (this.ordinal() + 1) % 4;
        return values()[index];
    }
}

