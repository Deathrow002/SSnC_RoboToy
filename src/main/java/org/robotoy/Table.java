package org.robotoy;

public class Table {
    private final int width;
    private final int height;

    public Table(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isValidPosition(Position position) {
        return position.getX() >= 0 && position.getX() < width &&
                position.getY() >= 0 && position.getY() < height;
    }
}

