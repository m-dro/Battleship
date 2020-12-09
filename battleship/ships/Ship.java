package battleship.ships;

import java.util.HashSet;
import java.util.Set;

/**
 * Course: JetBrains Academy, Java Developer Track
 * Project: Battleship
 * Purpose: A console-based program to simulate the classic game Battleships.
 *
 * Ship (as abstract class) represents ships used in the game.
 *
 * Each ship has:
 * <ul>
 *     <li>name</li>
 *     <li>size</li>
 *     <li>startCoordinates</li>
 *     <li>endCoordinates</li>
 *     <li>set of all the coordinates that make up the ship</li>
 * </ul>
 *
 * @author Mirek Drozd
 * @version 1.1
 */
public abstract class Ship {
    private String name;
    private int size;
    private String startCoordinates;
    private String endCoordinates;
    private Set<String> coordinates = new HashSet<>();

    public Ship() {
    }

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }


    public String getStartCoordinates() {
        return startCoordinates;
    }

    public void setStartCoordinates(String startCoordinates) {
        this.startCoordinates = startCoordinates;
    }

    public String getEndCoordinates() {
        return endCoordinates;
    }

    public void setEndCoordinates(String endCoordinates) {
        this.endCoordinates = endCoordinates;
    }

    public Set<String> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Set<String> coordinates) {
        this.coordinates = coordinates;
    }
}
