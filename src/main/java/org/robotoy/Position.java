package org.robotoy;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position getNextPosition(Direction direction) {
        return switch (direction) {
            case NORTH -> new Position(this.x, this.y + 1);
            case EAST -> new Position(this.x + 1, this.y);
            case SOUTH -> new Position(this.x, this.y - 1);
            case WEST -> new Position(this.x - 1, this.y);
            default -> this;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}