package org.robotoy;

public class Robot {
    private Position currentPosition;
    private Direction currentDirection;
    private final Table table;

    public Robot(Table table) {
        this.table = table;
    }

    public boolean place(Position position, Direction direction) {
        if (table.isValidPosition(position)) {
            this.currentPosition = position;
            this.currentDirection = direction;
            return true;
        }
        return false;
    }

    public void move() {
        if (currentPosition != null) {
            Position nextPosition = currentPosition.getNextPosition(currentDirection);
            if (table.isValidPosition(nextPosition)) {
                currentPosition = nextPosition;
            }
        }
    }

    public void left() {
        if (currentDirection != null) {
            currentDirection = currentDirection.left();
        }
    }

    public void right() {
        if (currentDirection != null) {
            currentDirection = currentDirection.right();
        }
    }

    public String report() {
        if (currentPosition != null) {
            return currentPosition.getX() + "," + currentPosition.getY() + "," + currentDirection;
        }
        return null;
    }
}