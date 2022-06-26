package battleship.vessels;

public class Cruiser extends Ship {

    private final String name = "Cruiser";
    private final int productionSize = 3;
    private VesselType type = VesselType.CRUISER;

    public Cruiser() {
    }

    public Cruiser(int rearLetter, int rearNumber, int foreLetter, int foreNumber) {
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
