package battleship.helpers;

import java.util.Arrays;

/**
 * Course: JetBrains Academy, Java Developer Track
 * Project: Battleship
 * Purpose: A console-based program to simulate the classic game Battleships.
 *
 * Gamefield represents the sea with ships using 2D char array[row][column].
 *
 * There are two types of gamefields:
 * 1) the gamefield on which the player has their own ships marked (openGamefield), and
 * 2) the gamefield on which the player marks shots fired at the other player's gameifled (coveredGamefield).
 *
 * Initially, both gamefields look the same.
 *
 * Ships are marked with 'O', and any unexplored areas are marked with '~' as the fog of war.
 *
 * @author Mirek Drozd
 * @version 1.1
 */
public class Gamefield {
    /**
     * This gamefield represents the player's gamefield. This is where their own ships will be marked.
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

    /**
     * This gamefield represents the opponent's gameifled.
     * This is where any shots fired by the player will be marked either as hits ('X') or as misses ('M').
     */
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
