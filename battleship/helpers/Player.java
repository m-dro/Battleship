package battleship.helpers;

import battleship.ships.*;

/**
 * Course: JetBrains Academy, Java Developer Track
 * Project: Battleship
 * Purpose: A console-based program to simulate the classic game Battleships.
 *
 * Player represents a player who is playing the game.
 * This class holds references to:
 * <ul>
 *     <li>player's name</li>
 *     <li>player's gamefield</li>
 *     <li>player's ships</li>
 * </ul>
 *
 * @author Mirek Drozd
 * @version 1.1
 */
public class Player {
    private String name;
    private Gamefield gamefield = new Gamefield();
    private final Ship[] ships = {
            new AircraftCarrier(),
            new Battleship(),
            new Submarine(),
            new Cruiser(),
            new Destroyer()
    };
    private CoordinateManager manager = new CoordinateManager(gamefield, ships);

    public Player (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Gamefield getGamefield() {
        return gamefield;
    }


    public Ship[] getShips() {
        return ships;
    }

    public CoordinateManager getManager() {
        return manager;
    }
}
