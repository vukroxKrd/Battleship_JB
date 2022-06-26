package battleship.vessels;

public class AircraftCarrier extends Ship {

    private final String name = "Aircraft Carrier";
    private final int productionSize = 5;
    private VesselType type = VesselType.AIRCRAFT_CARRIER;

    public AircraftCarrier() {
        super();
    }

    public AircraftCarrier(int rearLetter, int rearNumber, int foreLetter, int foreNumber) {
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
