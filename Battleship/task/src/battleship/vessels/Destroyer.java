package battleship.vessels;

public class Destroyer extends Ship {

    private final String name = "Destroyer";
    private final int productionSize = 2;
    private VesselType type = VesselType.DESTROYER;

    public Destroyer() {
    }

    public Destroyer(int rearLetter, int rearNumber, int foreLetter, int foreNumber) {
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
