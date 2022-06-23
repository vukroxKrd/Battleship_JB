package battleship.vessels;

public class AircraftCarrier extends Ship {

    private int size = 5;

    public AircraftCarrier(String[] rear, String[] fore) {
        super(rear, fore);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }
}
