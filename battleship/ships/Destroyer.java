package battleship.ships;

public class Destroyer extends Ship {
    private final String name = "Destroyer";
    private final int size = 2;
    private String startCoordinates;
    private String endCoordinates;

    public Destroyer() {
    }

    public Destroyer(String name, int size) {
        super(name, size);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getStartCoordinates() {
        return startCoordinates;
    }

    @Override
    public void setStartCoordinates(String startCoordinates) {
        this.startCoordinates = startCoordinates;
    }

    @Override
    public String getEndCoordinates() {
        return endCoordinates;
    }

    @Override
    public void setEndCoordinates(String endCoordinates) {
        this.endCoordinates = endCoordinates;
    }
}
