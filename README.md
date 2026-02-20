# Toy Robot Simulator

## Overview
This application simulates a toy robot moving on a 5x5 tabletop.

- The robot can be placed, moved, and rotated.
- Any command that would move the robot off the table is ignored.
- Commands are read from standard input.

## Project Layout

```text
src/
   main/java/org/robotoy/
      App.java
      Direction.java
      Position.java
      Robot.java
      Table.java
   test/java/
      TestRunner.java
```

## Build
From the project root:

```bash
mvn compile
```

## Run
Run the simulator in interactive mode:

```bash
mvn -q compile
java -cp target/classes org.robotoy.App
```

## Testing
This project includes a custom test runner (`TestRunner`).

```bash
mvn -q test-compile
java -cp "target/classes;target/test-classes" TestRunner
```

Current test coverage includes:
- Position
- Direction
- Table
- Robot
- Invalid PLACE (missing arguments)
- Invalid Position (`PLACE 5,5,NORTH`)

The test runner prints per-test pass/fail lines and a final summary.

## Commands
- `PLACE X,Y,F` where `F` is one of `NORTH`, `EAST`, `SOUTH`, `WEST`
- `MOVE`
- `LEFT`
- `RIGHT`
- `REPORT`
- `EXIT`

## Rules
- The robot must be placed on the table before movement commands take effect.
- `PLACE` positions outside the table bounds (e.g., beyond `4,4`) are rejected.
- Malformed commands produce an error message to stderr.
- Any move that would cause the robot to fall is ignored.

## Error Messages
- `Invalid PLACE command (missing arguments)`
- `Invalid PLACE command (position out of bounds)`
- `Invalid command: <input>` for malformed commands (for example invalid direction values)

## Example Output
- Example A: `0,1,NORTH`
- Example B: `0,0,WEST`
- Example C: `3,3,NORTH`
