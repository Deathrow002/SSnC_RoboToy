package org.robotoy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) {
        Table table = new Table(5, 5);
        Robot robot = new Robot(table);

        printGuide();

        try (BufferedReader reader = createReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                processCommand(line.trim(), robot, table);
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }

    private static BufferedReader createReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    private static void printGuide() {
        System.out.println("Please Insert Command");
        System.out.println("Commands: PLACE X,Y,F | MOVE | LEFT | RIGHT | REPORT | EXIT");
    }

    private static void printPlaceGuide() {
        System.out.println("PLACE format: PLACE X,Y,F (example: PLACE 0,0,NORTH)");
    }

    private static void processCommand(String commandLine, Robot robot, Table table) {
        String[] parts = commandLine.trim().split("\\s+", 2);
        if (parts.length == 0) return;

        String command = parts[0].toUpperCase();

        try {
            switch (command) {
                case "PLACE":
                    if (parts.length > 1) {
                        String[] params = parts[1].split(",");
                        if (params.length == 3) {
                            int x = Integer.parseInt(params[0].trim());
                            int y = Integer.parseInt(params[1].trim());
                            Direction direction = Direction.valueOf(params[2].trim().toUpperCase());
                            Position position = new Position(x, y);
                            if (table.isValidPosition(position)) {
                                robot.place(position, direction);
                            } else {
                                System.err.println("Invalid PLACE command (position out of bounds)");
                                printGuide();
                            }
                        } else {
                            System.err.println("Invalid PLACE command (invalid format)");
                            printGuide();
                        }
                    }
                    else {
                        System.err.println("Invalid PLACE command (missing arguments)");
                        printGuide();
                    }
                    break;
                case "MOVE":
                    robot.move();
                    break;
                case "LEFT":
                    robot.left();
                    break;
                case "RIGHT":
                    robot.right();
                    break;
                case "REPORT":
                    String report = robot.report();
                    if (report != null) {
                        System.out.println(report);
                    }
                    break;
                case "EXIT":
                    String reportLast = robot.report();
                    if (reportLast != null) {
                        System.out.println(reportLast);
                    }
                    System.exit(0);
                    break;
                default:
                    System.err.println("Invalid command: " + commandLine);
                    printGuide();
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid command: " + commandLine);
            if ("PLACE".equals(command)) {
                printPlaceGuide();
            } else {
                printGuide();
            }
        }
    }

}