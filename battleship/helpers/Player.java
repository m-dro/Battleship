package battleship.helpers;

import battleship.ships.*;

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
