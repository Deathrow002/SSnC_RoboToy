# Toy Robot Simulator

## Description
This application simulates a toy robot moving on a square tabletop, of dimensions 5 units x 5 units. The robot can be placed on the table, moved, and rotated. It will ignoring any movement that would cause it to fall to destruction.

## Getting Started

### Build
To compile the source code, open a terminal in the project root and run:
`javac -d bin src/*.java`
(Or list all files if using a shell like PowerShell without glob expansion: `Get-ChildItem src/*.java | ForEach-Object { javac -d bin $_.FullName }`)

### Run
To run the application with interactive input:
`java -cp bin App`

To run with a file input (e.g., the provided test data):
`java -cp bin App test_input.txt`

## Testing
A custom unit test runner is included to verify the application logic.

### Running Tests
To compile and run the tests:
1. Compile the test runner:
   `javac -d bin src/*.java`
   (Or: `javac -d bin src/TestRunner.java src/App.java src/Direction.java src/Position.java src/Robot.java src/Table.java`)

2. Run the test runner:
   `java -cp bin TestRunner`

Expected output:
`	ext
Running tests...
[PASS] Position
[PASS] Direction
[PASS] Table
[PASS] Robot
--------------------------------------------------
Tests completed. Passed: 4, Failed: 0
` 

## Logic & Design
- `App.java`: Main entry point, handles input parsing.
- `Robot.java`: Handles the robot's movement and state logic.
- `Table.java`: Validates positions on the 5x5 grid.
- `Position.java`: Represents coordinates (X, Y).
- `Direction.java`: Enum for orientation (NORTH, EAST, SOUTH, WEST).

## Test Data
A sample input file `test_input.txt` is provided in the project root.
Output matches the challenge requirements:
- Example a: `0,1,NORTH`
- Example b: `0,0,WEST`
- Example c: `3,3,NORTH`

Input can be from a file, or from standard input, as the developer chooses. Provide test data to exercise the application.
Constraints: The toy robot must not fall off the table during movement.
This also includes the initial placement of the toy robot.
Any move that would cause the robot to fall must be ignored.
