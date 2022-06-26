package battleship.vessels;

public class Battleship extends Ship {

    private final String name = "Battleship";
    private final int productionSize = 4;
    private VesselType type = VesselType.BATTLESHIP;


    public Battleship() {
    }

    public Battleship(int rearLetter, int rearNumber, int foreLetter, int foreNumber) {
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
