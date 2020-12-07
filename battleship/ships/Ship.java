package battleship.ships;

import java.util.HashSet;
import java.util.Set;

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
