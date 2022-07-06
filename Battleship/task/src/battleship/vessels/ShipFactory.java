package battleship.vessels;

import java.util.List;

public class ShipFactory {

    public static Ship createShip(VesselType type, List<Integer> coordinates) {
        Ship ship = null;

        int rearLetter = coordinates.get(0);
        int rearNumber = coordinates.get(1);
        int foreLetter = coordinates.get(2);
        int foreNumber = coordinates.get(3);


        switch (type) {
            case AIRCRAFT_CARRIER:
                ship = new AircraftCarrier(rearLetter, rearNumber, foreLetter, foreNumber);
                break;
            case BATTLESHIP:
                ship = new Battleship(rearLetter, rearNumber, foreLetter, foreNumber);
                break;
            case SUBMARINE:
                ship = new Submarine(rearLetter, rearNumber, foreLetter, foreNumber);
                break;
            case CRUISER:
                ship = new Cruiser(rearLetter, rearNumber, foreLetter, foreNumber);
                break;
            case DESTROYER:
                ship = new Destroyer(rearLetter, rearNumber, foreLetter, foreNumber);
                break;
        }
        return ship;
    }
}