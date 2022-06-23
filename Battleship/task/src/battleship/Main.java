package battleship;

import battleship.vessels.AircraftCarrier;
import battleship.vessels.Ship;

public class Main {

    public static void main(String[] args) {
        Field field = Field.getInstance();
        field.printBattleField();

        String[] rear = {"I", "2"};
        String[] fore = {"I", "5"};
        Ship aircraftCarrier = new AircraftCarrier(rear, fore);

        String[] rear1 = {"A", "6"};
        String[] fore1 = {"D", "6"};
        Ship aircraftCarrier1 = new AircraftCarrier(rear1, fore1);

        String[] rear2 = {"A", "10"};
        String[] fore2 = {"C", "10"};
        Ship borderShip1 = new AircraftCarrier(rear2, fore2);

        String[] rear3 = {"H", "10"};
        String[] fore3 = {"J", "10"};
        Ship borderShip2 = new AircraftCarrier(rear3, fore3);

        field.placeShipOnMap(aircraftCarrier);
        field.placeShipOnMap(aircraftCarrier1);
        field.placeShipOnMap(borderShip1);
        field.placeShipOnMap(borderShip2);
        field.printBattleField();
    }
}
