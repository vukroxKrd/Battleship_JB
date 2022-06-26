package battleship;

import battleship.exceptions.IncorrectShipSizeException;
import battleship.exceptions.ShipCoordinatesOutTheBoardException;
import battleship.exceptions.WrongShipLocationException;
import battleship.vessels.CoordinateUnit;
import battleship.vessels.EnclosedField;
import battleship.vessels.Ship;
import battleship.vessels.ShipFactory;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Field field = Field.getInstance();

        ShipFactory factory = new ShipFactory();
        Fleet fleet = new Fleet();

        for (Ship draft : fleet.draftFleet) {
            Ship placedShip = null;
            boolean repeat = false;
            do {
                repeat = false;

                try {
                    List<Integer> coordinates = factory.requestCoordinates(draft);
                    Ship ship = factory.createShip(draft.getType(), coordinates);
                    placedShip = field.placeShipOnMap(ship);
                } catch (ShipCoordinatesOutTheBoardException | IncorrectShipSizeException | WrongShipLocationException e) {
                    repeat = true;
                    e.printStackTrace();
                }

            } while (repeat);

//            if (!fleet.fleet.isEmpty()) {
//                fleet.fleet.stream()
//                        .flatMap(aShip -> aShip.getEnclosedFields()
//                                .containsValue())
//            }
            fleet.fleet.add(placedShip);

            Map<Integer, CoordinateUnit> coordsPlaced = placedShip.getCoordinates();
            Map<Integer, EnclosedField> enclosedFields = placedShip.getEnclosedFields();

            System.out.println("\n Printing coordinates: ");
            coordsPlaced.entrySet().forEach(entry -> {
                System.out.println(entry.getKey() + " " + entry.getValue());
            });

            System.out.println("\n Printing enclosed fields: ");
            System.out.println(enclosedFields.size());
            enclosedFields.entrySet().forEach(entry -> {
                System.out.println(entry.getKey() + " " + entry.getValue());
            });

            field.printBattleField();
        }
        System.out.println("Final disposition is: ");
        field.printBattleField();
    }
}
