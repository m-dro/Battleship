package battleship.helpers;

import battleship.ships.*;

public class Gamefield {
    /**
     * Gamefield representing the sea with ships using 2D char array[row][column].
     */
    private char[][] gameField = {
            {'A', '~','~','~','~','~','~','~','~','~','~'},
            {'B', '~','~','~','~','~','~','~','~','~','~'},
            {'C', '~','~','~','~','~','~','~','~','~','~'},
            {'D', '~','~','~','~','~','~','~','~','~','~'},
            {'E', '~','~','~','~','~','~','~','~','~','~'},
            {'F', '~','~','~','~','~','~','~','~','~','~'},
            {'G', '~','~','~','~','~','~','~','~','~','~'},
            {'H', '~','~','~','~','~','~','~','~','~','~'},
            {'I', '~','~','~','~','~','~','~','~','~','~'},
            {'J', '~','~','~','~','~','~','~','~','~','~'}
    };

    /**
     * Types of ships included in the game.
     */
    private final Ship[] ships = {
            new AircraftCarrier(),
            new Battleship(),
            new Submarine(),
            new Cruiser(),
            new Destroyer()
    };

    private CoordinateManager manager;

    public Gamefield(CoordinateManager manager) {
        this.manager = manager;
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

    public void markGamefield(Ship ship) {
        String start = ship.getStartCoordinates();
        String end = ship.getEndCoordinates();
        String coordinates =  start + " " + end;

        if (manager.isHorizontal(coordinates)) {
            int row = manager.parseRow(coordinates, 1);
            int startColumn = manager.parseColumn(coordinates, 1);
            int endColumn = manager.parseColumn(coordinates, 2);

            for (int i = startColumn; i <= endColumn; i++) {
                gameField[row][i] = 'O';
            }
        } else { // if not horizontal, it must be vertical :)
            int column = manager.parseColumn(coordinates, 1);
            int startRow = manager.parseRow(coordinates, 1);
            int endRow = manager.parseRow(coordinates, 2);

            for (int i = startRow; i <= endRow; i++) {
                gameField[i][column] = 'O';
            }
        }
    }
}
