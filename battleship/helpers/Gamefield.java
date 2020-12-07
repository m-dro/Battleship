package battleship.helpers;

import battleship.ships.*;

import java.util.Arrays;

public class Gamefield {
    /**
     * Gamefield representing the sea with ships using 2D char array[row][column].
     */
    private char[][] openGamefield = {
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

    private char[][] coveredGamefield = new char[openGamefield.length][];

    {
        for (int i = 0; i < openGamefield.length; i++) {
            coveredGamefield[i] = Arrays.copyOf(openGamefield[i], openGamefield[i].length);
        }
    }

    public char[][] getOpenGamefield() {
        return openGamefield;
    }

    public char[][] getCoveredGamefield() {
        return coveredGamefield;
    }


}
