package battleship.vessels;

public class Submarine extends Ship {

    private final String name = "Submarine";
    private int productionSize = 3;
    private VesselType type = VesselType.SUBMARINE;

    public Submarine() {
    }

    public Submarine(int rearLetter, int rearNumber, int foreLetter, int foreNumber) {
        super(rearLetter, rearNumber, foreLetter, foreNumber);
    }

    public int getProductionSize() {
        return productionSize;
    }

    @Override
    public String getName() {
        return name;
    }

    public VesselType getType() {
        return type;
    }
}
