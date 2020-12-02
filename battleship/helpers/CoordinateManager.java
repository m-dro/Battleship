package battleship.helpers;

import battleship.ships.Ship;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoordinateManager {
    private Ship[] ships;
    private char[][] gameField;

    public CoordinateManager(Gamefield gamefield) {
        this.ships = ships;
        this.gameField = gameField;
    }

    /**
     * Displays the game field by printing each row.
     */
    public void showGameField(){
        String firstRow = "  1 2 3 4 5 6 7 8 9 10"; // top row are numbers
        System.out.println(firstRow);

        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                System.out.print(gameField[i][j] + " ");
            }
            System.out.println(); // move to new line
        }

    }

    /**
     * Asks the user to enter coordinates for their ships, one by one.
     */
    public void placeShips() {
        Ship ship;
        int size;
        String name;
        String coordinates;

        for (int i = 0; i < ships.length; i++) {
            ship = ships[i];
            name = ship.getName();
            size = ship.getSize();

            System.out.printf("Enter the coordinates of the %s (%d cells)\n", name, size);
            coordinates = readCoordinates(size);

            setShipCoordinates(ship, coordinates);
            showGameField();
        }
    }

    /**
     * Reads coordinates from standard input.
     *
     * @param shipSize
     * @return
     */
    public String readCoordinates(int shipSize) {
        Scanner scanner = new Scanner(System.in);
        String coordinates = scanner.nextLine();
        coordinates = sanitizeCoordinates(coordinates);
        if (areValid(coordinates, shipSize)) {
            return coordinates;
        } else {
            return readCoordinates(shipSize);
        }

    }

    public boolean areValid(String coordinates, int shipSize) {
        if (!haveValidFormat(coordinates)){
            System.out.println("Error! Wrong format. Please use letters A-J and numbers 1-10:");
            return false;
        }
        if (!haveCorrectSize(coordinates, shipSize)){
            System.out.println("Error! Wrong length of the Submarine! Try again:\n");
            return false;
        }
        if (!areNotTooClose(coordinates)){
            System.out.println("Error! You placed it too close to another one. Try again:\n");
            return false;
        }
        return true;
    }

    public boolean haveValidFormat(String coordinates) {
        Pattern pattern = Pattern.compile("([ABCDEFGHIJ](10|[0-9])\\s?){2}");
        Matcher matcher = pattern.matcher(coordinates);
        return matcher.find();
    }

    public boolean haveCorrectSize(String coordinates, int size) {
        char firstLetter = coordinates.charAt(0);
        int firstNumber = parseColumn(coordinates, 1);
        // character at index 2 is the space separating the coordinates
        char secondLetter;
        if (firstNumber == 10) {
            secondLetter = coordinates.charAt(4);
        } else {
            secondLetter = coordinates.charAt(3);
        }

        int secondNumber = parseColumn(coordinates, 2);
        int length;

        if(isHorizontal(coordinates)) { // the ship is placed horizontally (same row)
            length = Math.abs(secondNumber - firstNumber) + 1;
            return length == size; // uses Math.abs because coordinates can be given either from left to right or from right to left
        }
        if(isVertical(coordinates)) { // the ship is placed vertically (same column)
            length = Math.abs(secondLetter - firstLetter) + 1;
            return length == size;
        };
        return false;
    }

    public boolean areNotTooClose(String coordinates) {
        if(isHorizontal(coordinates)) {
            return checkProximityHorizontal(coordinates);
        } else {
            return checkProximityVertical(coordinates);
        }
    }


    public String sanitizeCoordinates(String coordinates) {
        String sanitizedCoordinates = coordinates.strip().toUpperCase().replaceAll("\\s+", " ");

        int column1 = parseColumn(sanitizedCoordinates, 1);
        int column2 = parseColumn(sanitizedCoordinates, 2);
        int row1 = parseRow(sanitizedCoordinates, 1);
        int row2 = parseRow(sanitizedCoordinates, 2);
//        System.out.println("col1: " + column1 + "\ncol2: " + column2 + "\nrow1: " + row1 + "\nrow2: " + row2);
        if (row1 == row2) {
            if (column1 > column2) {
                int temp = column1;
                column1 = column2;
                column2 = temp;
            }
        }
        if (column1 == column2) {
            if (row1 > row2) {
                int temp = row1;
                row1 = row2;
                row2 = temp;
            }
        }

        sanitizedCoordinates = "" + gameField[row1][0] + column1 + " " + gameField[row2][0] + column2;
//        System.out.println("SANITIZED: " + sanitizedCoordinates);
        return sanitizedCoordinates;
    }


    /**
     * Checks coordinates of new ships (to be placed horizontally)
     * to make sure that there aren't already any ships close by
     * (ships cannot touch one another).
     * Method starts checking from left upper corner (row - 1, col - 1)
     * and ends checking in right lower corner (row + 1, col + 1)
     *
     * @param coordinates coordinates of new ship
     * @return true if new coordinates are not too close to any previous ship,
     *         false if new coordinates are too close to some previous ship
     */
    public boolean checkProximityHorizontal(String coordinates) {
        // row, startColumn & endColumn describe the position of the new ship
        int row = numericalCoordinate(coordinates.substring(0, 2));
        int startColumn = Integer.parseInt(coordinates.substring(1, 3).trim());
        int endColumn = Integer.parseInt(coordinates.substring(4));
        // vertical & horizontal coordinates describe area to be checked around the ship
        int startVertical;
        int endVertical;
        int startHorizontal;
        int endHorizontal;
        boolean available = true;

        // making sure that the area is within the game field
        if (row > 0) {
            startVertical = row - 1;
        } else {
            startVertical = row;
        }
        if (startColumn > 1) {
            startHorizontal = startColumn - 1;
        } else {
            startHorizontal = startColumn;
        }

        if (row < 9) {
            endVertical = row + 1;
        } else {
            endVertical = row;
        }
        if (endColumn < 10) {
            endHorizontal = endColumn + 1;
        } else {
            endHorizontal = endColumn;
        }

        /*
        Actual checking takes place within two loops (rows & columns)
        If any 'O' is found in the search area, this means that there is
        another ship too close to the new ship.
         */
        for (int i = startVertical; i <= endVertical; i++) {
            for (int j = startHorizontal; j <= endHorizontal; j++) {
                if (gameField[i][j] == 'O') {
                    available = false;
                    break;
                }
            }
            if (!available) break;
        }

        return available;
    }

    public boolean checkProximityVertical(String coordinates) {
        // row, startColumn & endColumn describe the position of the new ship
        int column = parseColumn(coordinates, 1);
        int startRow = parseRow(coordinates, 1);
        int endRow = parseRow(coordinates, 2);
        // vertical & horizontal coordinates describe area to be checked around the ship
        int startVertical;
        int endVertical;
        int startHorizontal;
        int endHorizontal;
        boolean available = true;

        // making sure that the area is within the game field
        if (column > 1) {
            startHorizontal = column - 1;
        } else {
            startHorizontal = column;
        }
        if (startRow > 0) {
            startVertical = startRow - 1;
        } else {
            startVertical = startRow;
        }

        if (column < 10) {
            endHorizontal = column + 1;
        } else {
            endHorizontal = column;
        }
        if (endRow < 9) {
            endVertical = endRow + 1;
        } else {
            endVertical = endRow;
        }

        /*
        Actual checking takes place within two loops (rows & columns)
        If any 'O' is found in the search area, this means that there is
        another ship too close to the new ship.
         */
        for (int i = startVertical; i <= endVertical; i++) {
            for (int j = startHorizontal; j <= endHorizontal; j++) {
                if (gameField[i][j] == 'O') {
                    available = false;
                    break;
                }
            }
            if (!available) break;
        }

        return available;
    }

    public void setShipCoordinates(Ship ship, String coordinates) {
        String startCoordinate = coordinates.substring(0, 3).trim();
        String endCoordinate = coordinates.substring(startCoordinate.length() + 1).trim();

        ship.setStartCoordinates(startCoordinate);
        ship.setEndCoordinates(endCoordinate);

        markGamefield(ship);
    }

    public void markGamefield(Ship ship) {
        String start = ship.getStartCoordinates();
        String end = ship.getEndCoordinates();
        String coordinates =  start + " " + end;

        if (isHorizontal(coordinates)) {
            int row = parseRow(coordinates, 1);
            int startColumn = parseColumn(coordinates, 1);
            int endColumn = parseColumn(coordinates, 2);

            for (int i = startColumn; i <= endColumn; i++) {
                gameField[row][i] = 'O';
            }
        } else { // if not horizontal, it must be vertical :)
            int column = parseColumn(coordinates, 1);
            int startRow = parseRow(coordinates, 1);
            int endRow = parseRow(coordinates, 2);

            for (int i = startRow; i <= endRow; i++) {
                gameField[i][column] = 'O';
            }
        }
    }

    public boolean isHorizontal(String coordinates) {
        int firstLetter = parseRow(coordinates, 1);
        int secondLetter = parseRow(coordinates, 2);

        return (firstLetter == secondLetter);
    }

    public boolean isVertical(String coordinates) {
        char firstNumber = coordinates.charAt(1);
        int secondNumber = coordinates.charAt(4);

        return firstNumber == secondNumber;
    }

    public int numericalCoordinate(String coordinate) {
        char c = coordinate.charAt(0);
        int numberToDeduct = 65; // so that A is represented as 0
        return (int) c - numberToDeduct;
    }

    /**
     * Parses ship coordinates to get first or second column as a number.
     *
     * @param coordinates ship coordinates
     * @param number of the column (1 for column in first coordinate, 2 for column in second coordinate)
     * @return
     */
    public int parseColumn(String coordinates, int number) {
        if (number == 1 || number == 2) {
            Pattern pattern = Pattern.compile("\\w{1}(\\d{1,3})\\s*\\w{1}(\\d{1,3})");
            Matcher matcher = pattern.matcher(coordinates);
            matcher.matches();
            int column = Integer.parseInt(matcher.group(number));
            return column;
        }
        System.out.println("Wrong column number. Please use 1 for column in first coordinate, or 2 for column in second coordinate");
        return -1;
    }

    public int parseRow(String coordinates, int number) {
        if (number == 1 || number == 2) {
            Pattern pattern = Pattern.compile("(\\w{1})\\d{1,3}\\s*(\\w{1})\\d{1,3}");
            Matcher matcher = pattern.matcher(coordinates);
            matcher.matches();
            String rowLetter = matcher.group(number);
            int rowNumber = numericalCoordinate(rowLetter);
            return rowNumber;
        }
        System.out.println("Wrong row number. Please use 1 for row in first coordinate, or 2 for row in second coordinate");
        return -1;
    }
}