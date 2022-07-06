package battleship;

import battleship.exceptions.IncorrectShipSizeException;
import battleship.exceptions.ShipCoordinatesOutTheBoardException;
import battleship.exceptions.ShipPlacedTooCloseToOtherShipException;
import battleship.exceptions.WrongShipLocationException;
import battleship.player.Player;
import battleship.player.Shot;
import battleship.utils.CoordinatesRequestor;
import battleship.utils.NavigationUtils;
import battleship.vessels.Coordinate;
import battleship.vessels.CoordinateUnit;
import battleship.vessels.Ship;
import battleship.vessels.ShipFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Game {

    private Player playerOne;
    private Player playerTwo;
    private Field field;

    public Game(Player playerOne, Player playerTwo, Field field) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.field = field;
    }

    public void play(Field field, Player playerOne, Player playerTwo) {
        var player = placeAllShipsOnTheMap(playerOne, field);
        var opponent = player;

        System.out.println("The game starts!");
        takeActionOnOpponent(player, opponent, field);

    }

    public Player placeAllShipsOnTheMap(Player player, Field field) {

        var fleet = player.getPlayerFleet();

        field.printBattleField();

        for (Ship draft : Field.draftFleet) {
            Ship placedShip = null;
            boolean repeat = false;

            do {
                try {
                    List<Integer> coordinates = CoordinatesRequestor.requestCoordinates(draft, repeat);
                    repeat = false;
                    Ship ship = ShipFactory.createShip(draft.getType(), coordinates);
                    placedShip = field.placeShipOnMap(ship);
                } catch (ShipCoordinatesOutTheBoardException | IncorrectShipSizeException | WrongShipLocationException e) {
                    repeat = true;
                    System.out.println(e.getMessage());
                    continue;
                }
                if (fleet.getShips().isEmpty()) {
                    fleet.getShips().add(placedShip);
                } else {
                    try {
                        for (Ship nextShip : fleet.getShips()) {
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
                        fleet.getShips().add(placedShip);
                    }
                }
            }
            while (repeat);

            player.setPlayerFleet(fleet);
            field.printBattleField();
        }
        return player;
    }

    public void takeActionOnOpponent(Player player, Player opponent, Field field) {

        field.printBattleField();

        Shot shot = player.produceShot();
        Map<Integer, Shot> allShots = player.getShots();

        Player.Fleet fleetOfOpponent = opponent.getPlayerFleet();
        List<Ship> shipsOfOpponent = fleetOfOpponent.getShips();
        String[][] bField = field.getBattleField();

        Optional<CoordinateUnit> anyCoordinate = shipsOfOpponent.stream()
                .map(Ship::getCoordinates)
                .map(Map::values)
                .flatMap(Collection::stream)
                .filter(coordinate -> {
                    Coordinate c1 = coordinate;
                    Coordinate c2 = shot;

                    return c1.equals(c2);
                })
                .findAny();

        anyCoordinate.ifPresentOrElse(
                (coordinate) -> {
                    coordinate.setHit(true);
                    fleetOfOpponent.findShipByCoordinate(coordinate).ifPresent(ship -> ship.changeHitToTrueWhenTheShipWasShot(coordinate));
                    opponent.setPlayerFleet(fleetOfOpponent);

                    shot.setStrike(true);
                    allShots.values().stream().filter(shot::equals).forEach(shot1 -> shot1.setStrike(true));
                    player.setShots(allShots);

                    bField[NavigationUtils.letterNumberMap.get(coordinate.getLetter())][coordinate.getNumber()] = String.valueOf('X');

                    field.setBattleField(bField);
                    field.printBattleField();
                    System.out.println("You hit a ship!");
                },
                () -> {
                    System.out.println(shot);
                    bField[NavigationUtils.letterNumberMap.get(shot.getLetter())][shot.getNumber()] = String.valueOf('M');
                    field.setBattleField(bField);
                    field.printBattleField();
                    System.out.println("You missed!");
                }
        );
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
