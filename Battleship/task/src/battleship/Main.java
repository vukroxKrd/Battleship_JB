package battleship;

import battleship.exceptions.IncorrectShipSizeException;
import battleship.exceptions.ShipCoordinatesOutTheBoardException;
import battleship.exceptions.ShipPlacedTooCloseToOtherShipException;
import battleship.exceptions.WrongShipLocationException;
import battleship.utils.NavigationUtils;
import battleship.vessels.*;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Field field = Field.getInstance();

        ShipFactory factory = new ShipFactory();
        Fleet fleet = new Fleet();

        field.printBattleField();

        for (Ship draft : fleet.draftFleet) {
            Ship placedShip = null;
            boolean repeat = false;

            do {
                try {
                    List<Integer> coordinates = factory.requestCoordinates(draft, repeat);
                    repeat = false;
                    Ship ship = factory.createShip(draft.getType(), coordinates);
                    placedShip = field.placeShipOnMap(ship);
                } catch (ShipCoordinatesOutTheBoardException | IncorrectShipSizeException | WrongShipLocationException e) {
                    repeat = true;
                    System.out.println(e.getMessage());
                    continue;
                }

                if (fleet.fleet.isEmpty()) {
                    fleet.fleet.add(placedShip);
                } else {


//                    repeat = fleet.fleet.stream()
//                            .anyMatch(addedShip -> addedShip.getEnclosedFields().containsValue(finalPlacedShip.getEnclosedFields().values().stream().filter();)
//                                    finalPlacedShip.getEnclosedFields().containsValue(addedShip) || finalPlacedShip.getCoordinates().containsValue(addedShip));
                    try {
                        for (Ship nextShip : fleet.fleet) {
                            for (Coordinate enclosedOfAlreadyAddedShip : nextShip.getEnclosedFields().values()) {
                                for (Coordinate coordinatesOfPlacedShips : nextShip.getCoordinates().values()) {
                                    for (Coordinate coordinateUnit : placedShip.getCoordinates().values()) {
                                        if (coordinateUnit.equals(enclosedOfAlreadyAddedShip) || coordinateUnit.equals(coordinatesOfPlacedShips)) {
                                            String[][] battleField = field.getBattleField();
                                            for (Coordinate coordinate : placedShip.getCoordinates().values()) {
                                                battleField[NavigationUtils.letterNumberMap.get(coordinate.getLetter())][coordinate.getNumber()] = String.valueOf('~');
                                            }
                                            field.setBattleField(battleField);
                                            throw new ShipPlacedTooCloseToOtherShipException("\nError! You placed it too close to another one. Try again:\n");
                                        }
                                    }
                                }
                            }
                        }
                    } catch (ShipPlacedTooCloseToOtherShipException e) {
                        repeat = true;
                        System.out.println(e.getMessage());
                    }

                    if (!repeat) {
                        fleet.fleet.add(placedShip);
                    }
                }
            }
            while (repeat);

//
//            Map<Integer, CoordinateUnit> coordsPlaced = placedShip.getCoordinates();
//            Map<Integer, EnclosedField> enclosedFields = placedShip.getEnclosedFields();
//
//            System.out.println("\n Printing coordinates: ");
//            coordsPlaced.entrySet().forEach(entry -> {
//                System.out.println(entry.getKey() + " " + entry.getValue());
//            });
//
//            System.out.println("\n Printing enclosed fields: ");
//            System.out.println(enclosedFields.size());
//            enclosedFields.entrySet().forEach(entry -> {
//                System.out.println(entry.getKey() + " " + entry.getValue());
//            });

            field.printBattleField();
        }
//        System.out.println("Final disposition is: ");
//        field.printBattleField();
    }
}
