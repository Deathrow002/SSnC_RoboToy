import org.robotoy.App;
import org.robotoy.Direction;
import org.robotoy.Position;
import org.robotoy.Robot;
import org.robotoy.Table;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Running tests...");
        int passed = 0;
        int failed = 0;

        if (runTest("Position", TestRunner::testPosition)) passed++; else failed++;
        if (runTest("Direction", TestRunner::testDirection)) passed++; else failed++;
        if (runTest("Table", TestRunner::testTable)) passed++; else failed++;
        if (runTest("Robot", TestRunner::testRobot)) passed++; else failed++;
        if (runTest("Invalid PLACE", TestRunner::testInvalidPlaceCommand)) passed++; else failed++;
        if (runTest("Invalid Position", TestRunner::testInvalidPositionCommand)) passed++; else failed++;

        System.out.println("--------------------------------------------------");
        System.out.println("Tests completed. Passed: " + passed + ", Failed: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static boolean runTest(String name, Runnable test) {
        try {
            test.run();
            System.out.println("[PASS] " + name);
            return true;
        } catch (RuntimeException e) {
            System.err.println("[FAIL] " + name + ": " + e.getMessage());
            // e.printStackTrace();
            return false;
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new RuntimeException(message + " Expected: " + expected + ", Actual: " + actual);
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) throw new RuntimeException(message);
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) throw new RuntimeException(message);
    }

    private static void assertContains(String text, String expectedSubstring, String message) {
        if (text == null || !text.contains(expectedSubstring)) {
            throw new RuntimeException(message + " Expected to contain: " + expectedSubstring + ", Actual: " + text);
        }
    }

    private static void testPosition() {
        Position p1 = new Position(1, 2);
        assertEquals(1, p1.getX(), "X coordinate should be 1");
        assertEquals(2, p1.getY(), "Y coordinate should be 2");

        Position p2 = new Position(1, 2);
        assertEquals(p1, p2, "Positions with same coordinates should be equal");

        Position p3 = p1.getNextPosition(Direction.NORTH);
        assertEquals(new Position(1, 3), p3, "Moving NORTH from 1,2 should be 1,3");
    }

    private static void testDirection() {
        assertEquals(Direction.WEST, Direction.NORTH.left(), "Left of NORTH should be WEST");
        assertEquals(Direction.EAST, Direction.NORTH.right(), "Right of NORTH should be EAST");
        assertEquals(Direction.SOUTH, Direction.EAST.right(), "Right of EAST should be SOUTH");
        assertEquals(Direction.NORTH, Direction.WEST.right(), "Right of WEST should be NORTH");
    }

    private static void testTable() {
        Table table = new Table(5, 5);
        assertTrue(table.isValidPosition(new Position(0, 0)), "0,0 should be valid");
        assertTrue(table.isValidPosition(new Position(4, 4)), "4,4 should be valid");
        assertFalse(table.isValidPosition(new Position(5, 5)), "5,5 should be invalid");
        assertFalse(table.isValidPosition(new Position(-1, 0)), "-1,0 should be invalid");
    }

    private static void testRobot() {
        Table table = new Table(5, 5);
        Robot robot = new Robot(table);

        // Test initial state
        assertEquals(null, robot.report(), "Report should be null before placement");

        // Test valid placement
        assertTrue(robot.place(new Position(0, 0), Direction.NORTH), "Placement at 0,0 should succeed");
        assertEquals("0,0,NORTH", robot.report(), "Report should match placement");

        // Test movement
        robot.move();
        assertEquals("0,1,NORTH", robot.report(), "Move NORTH from 0,0 should result in 0,1");

        // Test rotation
        robot.left();
        assertEquals("0,1,WEST", robot.report(), "Left turn from NORTH should face WEST");

        // Test invalid move (wall)
        robot.move(); // 0,1 WEST -> -1,1 (invalid)
        assertEquals("0,1,WEST", robot.report(), "Robot should not move off table");

        // Test invalid placement
        assertFalse(robot.place(new Position(5, 5), Direction.NORTH), "Placement off table should fail");
        assertEquals("0,1,WEST", robot.report(), "Robot state should not change on failed placement");

        // Test falling from edges
        robot.place(new Position(4, 4), Direction.NORTH);
        robot.move();
        assertEquals("4,4,NORTH", robot.report(), "Robot should not fall off North edge");

        robot.right(); // EAST
        robot.move();
        assertEquals("4,4,EAST", robot.report(), "Robot should not fall off East edge");
    }

    private static void testInvalidPlaceCommand() {
        String stderr = runAppAndCaptureStderr("PLACE\nREPORT\n");
        assertContains(stderr, "Invalid PLACE command (missing arguments)",
                "App should report missing PLACE arguments");
    }

    private static void testInvalidPositionCommand() {
        String stderr = runAppAndCaptureStderr("PLACE 5,5,NORTH\nREPORT\n");
        assertContains(stderr, "Invalid PLACE command (position out of bounds)",
                "App should report PLACE position out of bounds");
    }

    private static String runAppAndCaptureStderr(String input) {
        InputStream originalIn = System.in;
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setErr(new PrintStream(errContent));
            App.main(new String[0]);
            return errContent.toString();
        } finally {
            System.setIn(originalIn);
            System.setErr(originalErr);
        }
    }
}
